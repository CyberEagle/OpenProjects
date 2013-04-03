package br.com.cybereagle.androidbase.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import br.com.cybereagle.androidbase.EaglePreferences;
import br.com.cybereagle.androidbase.tip.TipManagerFragment;
import br.com.cybereagle.androidbase.tip.TipManagerFragment;
import br.com.cybereagle.androidlibrary.ui.EagleActivity;

public abstract class EagleWithTipsActivity extends EagleActivity {

    protected TipManagerFragment tipManagerFragment;

    protected abstract int[] getTips();

    @Override
    protected void onStart() {
        super.onStart();
        startTipSystem();
    }

    protected void startTipSystem() {
        String preferenceName = "TIP_" + getActivityIdentifier();
        SharedPreferences sharedPreferences = getSharedPreferences(EaglePreferences.NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(preferenceName)) {
            if (sharedPreferences.getBoolean(preferenceName, false)) {
                help();
            }
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(preferenceName, false);
            editor.commit();
            help();
        }
    }

    protected void help() {
        if (tipManagerFragment == null) {
            Bundle arguments = new Bundle();
            arguments.putIntArray(TipManagerFragment.TIP_LAYOUT_IDS_KEY, getTips());
            tipManagerFragment = new TipManagerFragment();
            tipManagerFragment.setArguments(arguments);
        }
        if (!tipManagerFragment.isShowing()) {
            tipManagerFragment.start(this);
        } else {
            tipManagerFragment.stop();
        }
    }
}
