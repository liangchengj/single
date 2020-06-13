package com.meyoustu.amuse.multidex;

import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;

import com.meyoustu.amuse.BuildConfig;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

final class Monitor {
  private static final boolean enableLog = BuildConfig.DEBUG;

  private static Monitor sMonitor;

  private ScheduledExecutorService mExecutor = Executors.newScheduledThreadPool(1);

  private String mProcessName;

  static void init(Monitor monitor) {
    sMonitor = monitor != null ? monitor : new Monitor();
  }

  static Monitor get() {
    return sMonitor;
  }

  private ScheduledExecutorService getExecutor() {
    return mExecutor;
  }

  String getProcessName() {
    return mProcessName;
  }

  Monitor setExecutor(ScheduledExecutorService executor) {
    mExecutor = executor;
    return this;
  }

  Monitor setProcessName(String processName) {
    mProcessName = processName;
    return this;
  }

  void loadLibrary(String libName) {
    System.loadLibrary(libName);
  }

  void logError(String msg) {
    if (!enableLog) {
      return;
    }
    Log.println(Log.ERROR, Constants.TAG, msg);
  }

  void logWarning(String msg) {
    if (!enableLog) {
      return;
    }
    Log.w(Constants.TAG, msg);
  }

  void logInfo(String msg) {
    if (!enableLog) {
      return;
    }

    // Log.println(Log.INFO, Constants.TAG, msg);
    Log.i(Constants.TAG, msg);
  }

  void logDebug(String msg) {
    if (!enableLog) {
      return;
    }

    // Log.println(Log.DEBUG, Constants.TAG, msg);
    Log.d(Constants.TAG, msg);
  }

  void logError(String msg, Throwable tr) {
    if (!enableLog) {
      return;
    }

    Log.e(Constants.TAG, msg, tr);
  }

  void logWarning(String msg, Throwable tr) {
    if (!enableLog) {
      return;
    }

    Log.w(Constants.TAG, msg, tr);
  }

  boolean isEnableNativeCheckSum() {
    return true;
  }

  void logErrorAfterInstall(String msg, Throwable tr) {
    Log.e(Constants.TAG, msg, tr);
  }

  void reportAfterInstall(long cost, long freeSpace, long reducedSpace, String dexHolderInfo) {
    Log.println(
        Log.INFO,
        Constants.TAG,
        "Cost time: "
            + cost
            + ", free space: "
            + freeSpace
            + ", reduced space: "
            + reducedSpace
            + ", holder: "
            + dexHolderInfo);
  }

  void doBeforeHandleOpt() {
    try {
      Thread.sleep(10_000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  void doAfterInstall(final Runnable optRunnable) {
    Looper.myQueue()
        .addIdleHandler(
            new MessageQueue.IdleHandler() {
              @Override
              public boolean queueIdle() {
                getExecutor().schedule(optRunnable, 5_000, TimeUnit.MILLISECONDS);
                return false;
              }
            });
  }
}
