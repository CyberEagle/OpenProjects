/*
 * Copyright 2012 Jeremy Dixon
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

package br.com.cybereagle.androidlibrary.ui.widgets.utils;

import javax.annotation.Nonnull;

import android.widget.Spinner;

/**
 * A listener which is notified of user selection in spinners it is registered
 * to with a strongly typed System view representation.
 *
 * @author Jeremy Dixon [http://www.androidanalyse.com]
 * @param <T>
 *          the System view type for this listener
 */
public interface SpinnerItemSelectedListener<T> {

    /**
     * Called when the user makes a selection on the given {@link Spinner}.
     *
     * @param item
     *          the spinner a selection was made on.
     * @param value
     *          the value which was selected.
     */
    void onItemSelected(@Nonnull Spinner item, @Nonnull T value);
}