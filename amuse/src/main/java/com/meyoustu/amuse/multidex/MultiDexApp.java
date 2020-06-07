package com.meyoustu.amuse.multidex;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.annotation.Keep;

@Keep
public class MultiDexApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (!MultiDex.isOptimizeProcess(Utility.getCurProcessName(base))) {
                return;
            }
            MultiDex.install(base);
        }
    }
}
