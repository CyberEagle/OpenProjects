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

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * <p>
 * A Util class for making mapping the User view (externalised in strings.xml to
 * support Android i18n) and the System view which can be an enumeration or
 * integer or any value that the system can use to persist or act on the user
 * selected information.
 * </p>
 *
 * <p>
 * <strong>NB:</strong> It is assumed that the provided User and System view
 * collections or arrays are:<br/>
 * 1) The same length as one another.<br/>
 * 2) The same order, in that the array index offset in the User view matches
 * the System view.<br/>
 * </p>
 *
 * @author Jeremy Dixon [http://www.androidanalyse.com]
 */
@Singleton
public class SpinnerUtils {

    /**
     * Create a new Spinner object
     *
     * @param view
     *          the parent view the spinner belongs to (layout defined in XML)
     * @param id
     *          the id of the spinner object
     * @param userValues
     *          the Collection of User values.
     * @param systemValues
     *          the Collection of System values.
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Spinner createNewSpinner(@Nonnull View view, int id,
                                               @Nonnull Collection<String> userValues, @Nonnull Collection<T> systemValues,
                                               @Nonnull SpinnerItemSelectedListener<T> listener) {
        return createNewSpinner(view, id, userValues.toArray(new String[userValues.size()]),
                (T[]) systemValues.toArray(), listener);
    }

    /**
     * Adjust the Spinner with the user values, system values and the listener.
     *
     * @param userValues
     *          the array of User values
     * @param systemValues
     *          the array of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     */
    @SuppressWarnings("unchecked")
    public <T> void setUpSpinner(Spinner spinner,@Nonnull Collection<String> userValues, @Nonnull Collection<T> systemValues,
                                 @Nonnull SpinnerItemSelectedListener<T> listener) {
        setUpSpinner(spinner, userValues.toArray(new String[userValues.size()]), (T[]) systemValues.toArray(), listener);
    }

    /**
     * Create a new Spinner object
     *
     * @param view
     *          the parent view the spinner belongs to (layout defined in XML)
     * @param id
     *          the id of the spinner object
     * @param userValues
     *          the Collection of User values
     * @param systemValues
     *          the array of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     * @return
     */
    public <T> Spinner createNewSpinner(@Nonnull View view, int id,
                                               @Nonnull Collection<String> userValues, @Nonnull T[] systemValues,
                                               @Nonnull SpinnerItemSelectedListener<T> listener) {
        return createNewSpinner(view, id, userValues.toArray(new String[userValues.size()]), systemValues,
                listener);
    }

    /**
     * Adjust the Spinner with the user values, system values and the listener.
     *
     * @param userValues
     *          the array of User values
     * @param systemValues
     *          the array of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     */
    @SuppressWarnings("unchecked")
    public <T> void setUpSpinner(Spinner spinner, @Nonnull Collection<String> userValues, @Nonnull T[] systemValues,
                                        @Nonnull SpinnerItemSelectedListener<T> listener) {
        setUpSpinner(spinner, userValues.toArray(new String[userValues.size()]), systemValues, listener);
    }

    /**
     * Create a new Spinner object
     *
     * @param view
     *          the parent view the spinner belongs to (layout defined in XML)
     * @param id
     *          the id of the spinner object
     * @param userValues
     *          the array of User values
     * @param systemValues
     *          the Collection of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Spinner createNewSpinner(@Nonnull View view, int id, @Nonnull String[] userValues,
                                               @Nonnull Collection<T> systemValues, @Nonnull SpinnerItemSelectedListener<T> listener) {
        return createNewSpinner(view, id, userValues, (T[]) systemValues.toArray(), listener);
    }

    /**
     * Adjust the Spinner with the user values, system values and the listener.
     *
     * @param userValues
     *          the array of User values
     * @param systemValues
     *          the array of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     */
    @SuppressWarnings("unchecked")
    public <T> void setUpSpinner(Spinner spinner, @Nonnull String[] userValues,
                                 @Nonnull Collection<T> systemValues, @Nonnull SpinnerItemSelectedListener<T> listener){
        setUpSpinner(spinner, userValues, (T[]) systemValues.toArray(), listener);
    }

    /**
     * Create a new Spinner object
     *
     * @param view
     *          the parent view the spinner belongs to (layout defined in XML)
     * @param id
     *          the id of the spinner object
     * @param userValues
     *          the array of User values
     * @param systemValues
     *          the array of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     * @return
     */
    public <T> Spinner createNewSpinner(@Nonnull View view, int id, @Nonnull String[] userValues,
                                               @Nonnull T[] systemValues, @Nonnull SpinnerItemSelectedListener<T> listener) {
        Spinner spinner = (Spinner) view.findViewById(id);
        setUpSpinner(spinner, userValues, systemValues, listener);
        return spinner;
    }

    /**
     * Adjust the Spinner with the user values, system values and the listener.
     *
     * @param userValues
     *          the array of User values
     * @param systemValues
     *          the array of System values
     * @param listener
     *          the Listener which receives notification of user selection from
     *          the system view perspective.
     */
    public <T> void setUpSpinner(@Nonnull Spinner spinner, @Nonnull String[] userValues,
                                 @Nonnull T[] systemValues, @Nonnull SpinnerItemSelectedListener<T> listener){
        OnItemSelectedListener mappingListener = new SpinnerUserToSystemViewMapper<T>(systemValues, listener);
        spinner.setOnItemSelectedListener(mappingListener);
        spinner.setAdapter(new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_spinner_item,
                userValues));
        // NB: this check will deliberately fail at runtime and should be picked up
        // during development/testing You could remove this and use assert()
        // (assuming you enable them) if you don't wish to have this check applied
        // in released clients.
        checkArgument(spinner.getAdapter().getCount() == systemValues.length, "System values len: "
                + systemValues.length + " don't match user values: " + userValues.length);
    }

    /**
     * Android {@link OnItemSelectedListener} which maps the User view to the
     * System view using the position in the equally sized arrays.
     *
     * @param <T>
     *          the System view type.
     */
    private static class SpinnerUserToSystemViewMapper<T> implements OnItemSelectedListener {

        @Nonnull
        private final T[] systemValues;

        @Nonnull
        private final SpinnerItemSelectedListener<T> Listener;

        /**
         * Constructor for class.
         *
         * @param systemValues
         *          the array of system values
         * @param Listener
         *          the Listener which will take action on the System value when
         *          selections are made.
         */
        public SpinnerUserToSystemViewMapper(@Nonnull T[] systemValues,
                                             @Nonnull SpinnerItemSelectedListener<T> Listener) {
            this.systemValues = checkNotNull(systemValues, "systemValues");
            this.Listener = checkNotNull(Listener, "Listener");
        }

        /**
         * {@inheritDoc}
         */
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Listener.onItemSelected((Spinner) parent, systemValues[position]);
        }

        /**
         * {@inheritDoc}
         */
        public void onNothingSelected(AdapterView<?> parent) {
            // I don't want to do anything here, if you did you could either:
            // 1) Add onNothingSelected() behaviour to SpinnerItemSelectedListener
            // 2) Make onItemSelected 'value' param @CheckForNull instead of @Nonnull
            // and pass null to it
        }

    }

}