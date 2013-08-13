package br.com.cybereagle.androidlibrary.ui.helper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import br.com.cybereagle.androidlibrary.annotation.Retained;
import br.com.cybereagle.androidlibrary.ui.interfaces.RetainedActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class RetainedActivityHelperTest {

    private TestRetainedActivity testRetainedActivity;
    private RetainedActivityHelper retainedActivityHelper;

    @Before
    public void setUp() throws Exception {
        testRetainedActivity = new TestRetainedActivity();
        retainedActivityHelper = new RetainedActivityHelper(testRetainedActivity);
        testRetainedActivity.retainedActivityHelper = retainedActivityHelper;
    }

    @Test
    public void shouldRetainOnlyAnnotatedFieldsOnConfigurationChanges(){
        shadowOf(testRetainedActivity).create();

        assertEquals(-1, testRetainedActivity.retainedCounter);
        assertEquals(-1, testRetainedActivity.unretainedCounter);

        testRetainedActivity.retainedCounter++;
        testRetainedActivity.unretainedCounter++;

        assertEquals(0, testRetainedActivity.retainedCounter);
        assertEquals(0, testRetainedActivity.unretainedCounter);

        shadowOf(testRetainedActivity).recreate();

        assertEquals(0, testRetainedActivity.retainedCounter);
        assertEquals(-1, testRetainedActivity.unretainedCounter);
    }

    private static class TestRetainedActivity extends FragmentActivity implements RetainedActivity {

        private RetainedActivityHelper retainedActivityHelper;

        @Retained
        int retainedCounter;

        int unretainedCounter;

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

        @Override
        public void beforeCreate(Bundle savedInstanceState) {

        }

        @Override
        public void initializeUnretainedInstanceFields(Bundle savedInstanceState) {
            unretainedCounter = -1;
        }

        @Override
        public void initializeRetainedInstanceFields(Bundle savedInstanceState) {
            retainedCounter = -1;
        }

        @Override
        public void createView(Bundle savedInstanceState) {

        }

        @Override
        public void afterCreateView(Bundle savedInstanceState) {

        }

    }

}
