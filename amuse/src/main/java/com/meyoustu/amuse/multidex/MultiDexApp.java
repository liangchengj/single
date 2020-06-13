package com.meyoustu.amuse.multidex;

import android.app.Application;
import android.content.Context;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class MultiDexApp extends Application {
  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    if (SDK_INT < LOLLIPOP) {
      if (!MultiDex.isOptimizeProcess(Utility.getCurProcessName(base))) {
        return;
      }
      MultiDex.install(base);
    }
  }
}
