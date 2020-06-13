package com.meyoustu.amuse.multidex;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

final class Result {
  private static Result result = new Result();

  boolean modified;

  long freeSpaceBefore;

  long freeSpaceAfter;

  String vmLibName;

  boolean isYunOS;

  File dataDir;

  File rootDir;

  File dexDir;

  File optDexDir;

  File zipDir;

  Throwable fatalThrowable;

  List<Throwable> unFatalThrowable = new ArrayList<>();

  List<String> dexInfoList = new ArrayList<>();

  boolean supportFastLoadDex;

  static Result get() {
    if (result != null) {
      return result;
    } else {
      Log.w(Constants.TAG, "Avoid npe, but return a invalid tmp result");
      return new Result();
    }
  }

  private Result() {}

  void setDirs(File dataDir, File rootDir, File dexDir, File optDexDir, File zipDir) {
    this.dataDir = dataDir;
    this.rootDir = rootDir;
    this.dexDir = dexDir;
    this.optDexDir = optDexDir;
    this.zipDir = zipDir;
  }

  void setFatalThrowable(Throwable tr) {
    fatalThrowable = tr;
  }

  void addUnFatalThrowable(Throwable tr) {
    unFatalThrowable.add(tr);
  }

  void addDexInfo(String dexInfo) {
    dexInfoList.add(dexInfo);
  }
}
