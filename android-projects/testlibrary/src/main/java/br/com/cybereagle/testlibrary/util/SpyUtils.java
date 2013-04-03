package br.com.cybereagle.testlibrary.util;

import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.AsyncTaskLoaderSpier;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class SpyUtils {

    public static <T extends AsyncTaskLoader> T spy(T asyncTaskLoader, String observerFieldName){
        return AsyncTaskLoaderSpier.spy(asyncTaskLoader, observerFieldName);
    }

}
