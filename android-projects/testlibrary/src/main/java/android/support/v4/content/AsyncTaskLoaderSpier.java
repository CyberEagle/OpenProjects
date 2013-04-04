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

package android.support.v4.content;

import android.os.AsyncTask;
import android.os.SystemClock;
import br.com.cybereagle.commonlibrary.util.ReflectionUtils;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.*;

public class AsyncTaskLoaderSpier {

    public static <T extends AsyncTaskLoader> T spy(final T asyncTaskLoader, String observerFieldName){
        final T spyAsyncTaskLoader = Mockito.spy(asyncTaskLoader);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                new AsyncTask<Void, Void, Object>() {
                    @Override
                    protected Object doInBackground(Void... params) {
                        return spyAsyncTaskLoader.loadInBackground();
                    }

                    @Override
                    protected void onPostExecute(Object data) {
                        spyAsyncTaskLoader.mLastLoadCompleteTime = SystemClock.uptimeMillis();
                        spyAsyncTaskLoader.deliverResult(data);
                    }

                    @Override
                    protected void onCancelled(Object data) {
                        spyAsyncTaskLoader.mLastLoadCompleteTime = SystemClock.uptimeMillis();
                        spyAsyncTaskLoader.onCanceled(data);

                        spyAsyncTaskLoader.executePendingTask();
                    }
                }.execute();
                return null;
            }
        }).when(spyAsyncTaskLoader).executePendingTask();

        Field observerField = ReflectionUtils.getInstanceVariable(spyAsyncTaskLoader.getClass(), observerFieldName);
        ReflectionUtils.removeFinalModifier(observerField);

        try {
            ReflectionUtils.set(observerField, spyAsyncTaskLoader, spyAsyncTaskLoader.new ForceLoadContentObserver());
        } catch(InvocationTargetException e) {
            e.printStackTrace();
        }

        return spyAsyncTaskLoader;
    }
}
