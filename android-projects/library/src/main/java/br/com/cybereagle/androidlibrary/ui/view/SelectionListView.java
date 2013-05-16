/*
 * Copyright 2013 Cyber Eagle
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package br.com.cybereagle.androidlibrary.ui.view;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;

public class SelectionListView extends ListView {

    private SherlockFragmentActivity activity;
    ActionMode actionMode;

    private boolean selectionMode = false;
    private int startPosition;

    private SelecionActionModeCallback actionModeCallback;

    /**
     * String like: %d items selected
     */
    private String selectedStringFormat = "%d items selected";

    public SelectionListView(Context context) {
        this( context, null, 0);
    }

    public SelectionListView(Context context, AttributeSet attrs) {
        this( context, attrs, 0);
    }


    public SelectionListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        activity = (SherlockFragmentActivity) context;
    }

    @Override
    public boolean performItemClick(View view, int position, long id) {
        OnItemClickListener mOnItemClickListener = getOnItemClickListener();
        if (mOnItemClickListener != null) {
            playSoundEffect(SoundEffectConstants.CLICK);
            if (view != null)
                view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
            mOnItemClickListener.onItemClick(this, view, position, id);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();
        final int x = (int) ev.getX();
        final int y = (int) ev.getY();

        if (action == MotionEvent.ACTION_DOWN && x < getWidth() / 7) {
            selectionMode = true;
            startPosition = pointToPosition(x, y);
        }
        if (!selectionMode){
            return super.onTouchEvent(ev);
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (pointToPosition(x, y) != startPosition)
                    selectionMode = false;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            default:
                selectionMode = false;
                int mItemPosition = pointToPosition(x, y);
                if (startPosition != ListView.INVALID_POSITION && mItemPosition >= getHeaderViewsCount()){
                    setItemChecked(mItemPosition, !isItemChecked(mItemPosition));
                }

        }

        return true;
    }



    @Override
    public void setItemChecked(int position, boolean value) {
        super.setItemChecked(position, value);
        // boolean r = getAdapter().hasStableIds();
        if(actionModeCallback != null){
            updateActionMode();
        }
    }

    public void updateActionMode() {
        int checkedCount = getCheckItemIds().length;

        if (checkedCount == 0) {
            if (actionMode != null)
                actionMode.finish();
            return;
        }
        if (actionMode == null)
            actionMode = activity.startActionMode(actionModeCallback);

        actionMode.setTitle(String.format(selectedStringFormat, checkedCount));
    }

    public void onRestoreInstanceState (Parcelable state){
        super.onRestoreInstanceState(state);
        updateActionMode();
    }

    public void clearChecked() {
        SparseBooleanArray CItem = getCheckedItemPositions();
        for (int i = 0; i < CItem.size(); i++)
            if (CItem.valueAt(i))
                super.setItemChecked(CItem.keyAt(i), false);
    }

    public SelecionActionModeCallback getActionModeCallback() {
        return actionModeCallback;
    }

    public void setActionModeCallback(SelecionActionModeCallback actionModeCallback) {
        this.actionModeCallback = actionModeCallback;

        setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(isItemChecked(position)){
                    setItemChecked(position, false);
                }
                else{
                    setItemChecked(position, true);
                }

                return true;
            }
        });
    }

    public String getSelectedStringFormat() {
        return selectedStringFormat;
    }

    public void setSelectedStringFormat(String selectedStringFormat) {
        this.selectedStringFormat = selectedStringFormat;
    }

    public static abstract class SelecionActionModeCallback implements ActionMode.Callback{

        private SelectionListView selectionListView;

        protected SelecionActionModeCallback(SelectionListView selectionListView) {
            this.selectionListView = selectionListView;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            selectionListView.actionMode = null;
            selectionListView.clearChecked();
        }
    }
}