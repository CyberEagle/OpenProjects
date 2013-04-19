package br.com.cybereagle.androidlibrary.ui.view;

import android.content.Context;
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

        setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(isItemChecked(position)){
                    setItemChecked(position, false);
                }
                else{
                    setItemChecked(position, true);
                }

                return false;
            }
        });
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
        if (!selectionMode)
            return super.onTouchEvent(ev);
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
                if (startPosition != ListView.INVALID_POSITION)
                    setItemChecked(mItemPosition, !isItemChecked(mItemPosition));
        }

        return true;
    }



    @Override
    public void setItemChecked(int position, boolean value) {
        super.setItemChecked(position, value);
        // boolean r = getAdapter().hasStableIds();
        updateActionMode();
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