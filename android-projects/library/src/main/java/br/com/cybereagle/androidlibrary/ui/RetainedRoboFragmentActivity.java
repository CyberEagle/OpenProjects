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

package br.com.cybereagle.androidlibrary.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.cybereagle.androidlibrary.ui.RoboActionBarActivity;
import br.com.cybereagle.androidlibrary.ui.helper.RetainedActivityHelper;
import br.com.cybereagle.androidlibrary.ui.interfaces.RetainedActivity;
import roboguice.activity.RoboFragmentActivity;

public abstract class RetainedRoboFragmentActivity extends RoboFragmentActivity implements RetainedActivity {

    private RetainedActivityHelper retainedActivityHelper;

    protected RetainedRoboFragmentActivity() {
        this.retainedActivityHelper = new RetainedActivityHelper(this);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
        retainedActivityHelper.onCreate(savedInstanceState);
	}

    public void callSuperOnCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
        return retainedActivityHelper.onRetainCustomNonConfigurationInstance();
	}

    public void beforeCreate(Bundle savedInstanceState){

    }

    public void initializeUnretainedInstanceFields(Bundle savedInstanceState) {

    }

    public void initializeRetainedInstanceFields(Bundle savedInstanceState){

    }

    public void afterCreateView(Bundle savedInstanceState){

    }
}
