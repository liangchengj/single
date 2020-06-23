package com.meyoustu.amuse;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public abstract class Activity extends FragmentActivity implements ActivityConstants {

  /* Construction method.
  Load the dynamic link library by detecting whether it is annotated with "@Native". */
  protected Activity() {
    activityWrapper.loadJNILibByAnnotation();
  }

  @LayoutRes private int layoutId = -1;

  /* Get the context of the current Activity. */
  protected final Context ctx = this;

  private ActivityWrapper activityWrapper;

  protected Activity(@LayoutRes int layoutId) {
    this();
    this.layoutId = layoutId;
  }

  protected void onDecorViewConfig(View decorView
      /*The current class is not used and is provided to subclasses.*/ ) {
    activityWrapper.onDecorViewConfig(decorView);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    activityWrapper = ActivityWrapper.newInstance(this);
    onDecorViewConfig(activityWrapper.decorView);
    super.onCreate(savedInstanceState);
    if (layoutId != -1) {
      setContentView(layoutId);
    } else {
      @LayoutRes int layoutId = initView();
      if (layoutId != -1) {
        setContentView(layoutId);
      }
    }

    try {
      /* Initialize the "Field" by checking whether it is annotated. */
      App.initResMemberByAnnotation(this);
    } catch (IllegalAccessException e) {
      // Ignore
    }
  }

  protected int initView() {
    return activityWrapper.initView();
  }

  @Override
  public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    onDecorViewConfig(activityWrapper.decorView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    onDecorViewConfig(activityWrapper.decorView);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      onDecorViewConfig(activityWrapper.decorView);
    }
  }
}
