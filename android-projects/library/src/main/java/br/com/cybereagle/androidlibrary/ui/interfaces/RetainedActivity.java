package br.com.cybereagle.androidlibrary.ui.interfaces;

import android.os.Bundle;

public interface RetainedActivity {

    void callSuperOnCreate(Bundle savedInstanceState);
    void beforeCreate(Bundle savedInstanceState);
    void initializeUnretainedInstanceFields(Bundle savedInstanceState);
    void initializeRetainedInstanceFields(Bundle savedInstanceState);
    void createView(Bundle savedInstanceState);
    void afterCreateView(Bundle savedInstanceState);
    Object getLastCustomNonConfigurationInstance();

}
