package com.meyoustu.amuse.multidex;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Keep;

@Keep
public class MultiDexApp extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (!MultiDex.isOptimizeProcess(Utility.getCurProcessName(base))) {
            return;
        }

        MultiDex.install(base);
    }
}
