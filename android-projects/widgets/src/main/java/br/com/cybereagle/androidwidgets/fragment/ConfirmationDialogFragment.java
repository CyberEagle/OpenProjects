/*
 * Copyright 2013 Cyber Eagle
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.cybereagle.androidwidgets.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import br.com.cybereagle.androidwidgets.R;
import roboguice.fragment.RoboDialogFragment;

public class ConfirmationDialogFragment extends RoboDialogFragment {

	private int icon;
	private int title;
	private int message;
	private int positive;
	private int negative;
	private DialogInterface.OnClickListener positiveClickListener;
	private DialogInterface.OnClickListener negativeClickListener;
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		setRetainInstance(true);
        return new AlertDialog.Builder(getActivity())
        .setIcon(this.icon)
        .setTitle(this.title)
        .setMessage(this.message)
        .setPositiveButton(this.positive, this.positiveClickListener)
        .setNegativeButton(this.negative, this.negativeClickListener)
        .create();
    }
	
	@Override
	public void onDestroyView() {
		if (getDialog() != null && getRetainInstance())
			getDialog().setDismissMessage(null);
		super.onDestroyView();
	}
	
	public static class Builder {
		
		private int icon = android.R.drawable.ic_dialog_alert;
		private int title = R.string.confirmation;
		private int message;
		private int positive = R.string.yes;
		private int negative = R.string.no;
		private DialogInterface.OnClickListener positiveClickListener;
		private DialogInterface.OnClickListener negativeClickListener;
				
		public Builder(int message){
			this.message = message;
		}
		
		public Builder setIcon(int icon) {
			this.icon = icon;
			return this;
		}
		
		public Builder setTitle(int title) {
			this.title = title;
			return this;
		}
		
		public Builder setMessage(int message) {
			this.message = message;
			return this;
		}

		public Builder setPositiveClickListener(DialogInterface.OnClickListener positiveClickListener) {
			this.positiveClickListener = positiveClickListener;
			return this;
		}

		public Builder setNegativeClickListener(DialogInterface.OnClickListener negativeClickListener) {
			this.negativeClickListener = negativeClickListener;
			return this;
		}
		
		public Builder setPositive(int positive){
			this.positive = positive;
			return this;
		}
		
		public Builder setNegative(int negative){
			this.negative = negative;
			return this;
		}
		
		public ConfirmationDialogFragment build(){
			ConfirmationDialogFragment confirmationDialog = new ConfirmationDialogFragment();
			confirmationDialog.icon = this.icon;
			confirmationDialog.title = this.title;
			confirmationDialog.message = this.message;
			confirmationDialog.positive = this.positive;
			confirmationDialog.negative = this.negative;
			confirmationDialog.positiveClickListener = this.positiveClickListener;
			confirmationDialog.negativeClickListener = this.negativeClickListener;
			return confirmationDialog;
		}
		
	}
}
