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

package br.com.cybereagle.androidlibrary.fork.fedorvlasov.lazylist;

import android.graphics.Bitmap;

public class DefaultPhotosLoader implements PhotosLoader{

    @Override
    public void loadPhoto(ImageLoader imageLoader, PhotoToLoad photoToLoad) {
        try {
            if (imageLoader.viewReused(photoToLoad)){
                return;
            }

            Bitmap bitmap = imageLoader.getBitmap(photoToLoad.getUrl());
            imageLoader.getMemoryCache().put(photoToLoad.getUrl(), bitmap);
            if (imageLoader.viewReused(photoToLoad)){
                return;
            }

            imageLoader.displayBitmap(photoToLoad, bitmap);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
