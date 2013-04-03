package br.com.cybereagle.testlibrary;

import java.io.File;
import java.lang.reflect.Method;

import br.com.cybereagle.testlibrary.shadow.*;
import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricContext;
import org.robolectric.RobolectricTestRunner;

import android.os.Build;

public class EagleTestRunner extends RobolectricTestRunner {

	private static final int SDK_INT = Build.VERSION.SDK_INT;

	public EagleTestRunner(Class<?> testClass) throws InitializationError {
		super(RobolectricContext.bootstrap(EagleTestRunner.class, testClass, new RobolectricContext.Factory() {
			@Override
			public RobolectricContext create() {
				return new RobolectricContext() {

					@Override
					protected AndroidManifest createAppManifest() {
						return new AndroidManifest(new File("./application").exists() ? new File("./application")
								: new File("./"));
					}

				};
			}
		}));

	}

    private void clearDatabase() {
        File db = new File(EagleConstants.TEST_DB_FILE_NAME);
        if(db.exists()){
            db.delete();
        }
    }

    @Override
    protected void bindShadowClasses() {
        Robolectric.bindShadowClass(ShadowSQLiteQueryBuilder.class);
        Robolectric.bindShadowClass(ShadowObservable.class);
        Robolectric.bindShadowClass(ShadowContentObservable.class);
        Robolectric.bindShadowClass(ShadowDataSetObservable.class);
        Robolectric.bindShadowClass(ShadowAbstractCursor.class);
        Robolectric.bindShadowClass(ShadowSQLiteCursor.class);

        Robolectric.bindShadowClass(ShadowAsyncTaskLoader.class);
    }

	@Override
	public void beforeTest(final Method method) {
		final int targetSdkVersion = getRobolectricContext().getAppManifest().getSdkVersion();
		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", targetSdkVersion);
        clearDatabase();
	}

	@Override
	public void afterTest(final Method method) {
		resetStaticState();
	}

	@Override
	public void resetStaticState() {
		super.resetStaticState();
		Robolectric.Reflection.setFinalStaticField(Build.VERSION.class, "SDK_INT", SDK_INT);
	}



}