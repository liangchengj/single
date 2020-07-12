package com.meyoustu.amuse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.RemoteViews;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.core.app.NotificationCompat;

import com.meyoustu.amuse.listen.ClickListener;

import java.lang.annotation.Annotation;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public abstract class Fragment extends androidx.fragment.app.Fragment implements ActivityConstants {

  /* Construction method.
  Load the dynamic link library by detecting whether it is annotated with "@Native". */
  protected Fragment() {
    activityWrapper = ActivityWrapper.newInstance(getActivity());
    activityWrapper.loadJNILibByAnnotation();
  }

  protected Fragment(@LayoutRes int layoutId) {
    this();
    this.layoutId = layoutId;
  }

  @LayoutRes private int layoutId = -1;

  /* Get the context of the current Activity. */
  protected final Context ctx = getContext();

  private ActivityWrapper activityWrapper;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    onDecorViewConfig(activityWrapper.decorView);
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (layoutId != -1) {
      return inflater.inflate(layoutId, container, false);
    } else {
      @LayoutRes int layoutId = initView();
      if (layoutId != -1) {
        return inflater.inflate(layoutId, container, false);
      }
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override
  public void onResume() {
    super.onResume();
    onDecorViewConfig(activityWrapper.decorView);
    try {
      /* Initialize the "Field" by checking whether it is annotated. */
      App.initResMemberByAnnotation(getActivity());
    } catch (IllegalAccessException e) {
      // Ignore
    }
  }

  @Override
  public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    onDecorViewConfig(activityWrapper.decorView);
  }

  @LayoutRes
  protected int initView() {
    return activityWrapper.initView();
  }

  protected void onDecorViewConfig(View decorView
      /* The current class is not used and is provided to subclasses. */ ) {
    activityWrapper.onDecorViewConfig(decorView);
  }

  protected final void setDecorViewRadius(int radius) {
    activityWrapper.setDecorViewRadius(radius);
  }

  /* Set the Activity window to full screen display. */
  protected final void setWindowFullScreen() {
    activityWrapper.setWindowFullScreen();
  }

  /** Determine whether the system opens the dark theme. */
  protected final boolean isDarkTheme() {
    return activityWrapper.isDarkTheme();
  }

  protected final void setViewRadius(int radius, @IdRes int... ids) {
    activityWrapper.setViewRadius(radius, ids);
  }

  protected final void setViewOval(@IdRes int... ids) {
    activityWrapper.setViewOval(ids);
  }

  /** Used for the view press effect and contains the click event of the view. */
  protected final void effectClick(View v, final ClickListener clickListener) {
    activityWrapper.effectClick(v, clickListener);
  }

  /** @return ShortcutManager -> Used to manage desktop shortcuts. */
  protected final ShortcutManager getShortCutManager() {
    return activityWrapper.getShortCutManager();
  }

  /** Android 8.0 or above can set the desktop icon menu. */
  protected final void setShortCuts(ShortcutInfo... shortcutInfo) {
    activityWrapper.setShortCuts(shortcutInfo);
  }

  /** Used to obtain the screen pixel density. */
  protected final float getDisplayDensity() {
    return activityWrapper.getDisplayDensity();
  }

  /** Get the color from the resource file. */
  @ColorInt
  protected final int getResColor(@ColorRes int id) {
    return activityWrapper.getResColor(id);
  }

  protected final <T> T getClassAnnotatedValue(
      Class<? extends Annotation> annotation, Class<T> classOfT) {
    return activityWrapper.getClassAnnotatedValue(annotation, classOfT);
  }

  @Dimension
  protected final int getDimensionPixelSize(@DimenRes int id) {
    return activityWrapper.getDimensionPixelSize(id);
  }

  protected final int getOrientation() {
    return activityWrapper.getOrientation();
  }

  protected final boolean orientationIsPortrait() {
    return activityWrapper.orientationIsPortrait();
  }

  protected final boolean orientationIsLandScape() {
    return activityWrapper.orientationIsPortrait();
  }

  @Dimension
  protected final int getStatusBarHeight() {
    return activityWrapper.getStatusBarHeight();
  }

  @Dimension
  protected final int getNavigationBarHeight() {
    return activityWrapper.getNavigationBarHeight();
  }

  protected final ConnectivityManager getConnectivityManager() {
    return activityWrapper.getConnectivityManager();
  }

  protected final NotificationManager getNotificationManager() {
    return activityWrapper.getNotificationManager();
  }

  protected final InputMethodManager getInputMethodManager() {
    return activityWrapper.getInputMethodManager();
  }

  protected final TelephonyManager getTelephonyManager() {
    return activityWrapper.getTelephonyManager();
  }

  protected final WifiManager getWifiManager() {
    return activityWrapper.getWifiManager();
  }

  protected final @Dimension int pxToDp(int px) {
    return activityWrapper.pxToDp(px);
  }

  protected final int dpToPx(@Dimension float dp) {
    return activityWrapper.dpToPx(dp);
  }

  protected final Animation loadAnimation(@AnimRes int id) {
    return activityWrapper.loadAnimation(id);
  }

  /** Check whether the input method is displayed. */
  protected final boolean isInputMethodShowing() {
    return activityWrapper.isInputMethodShowing();
  }

  protected final int getIdentifier(String name, String defType, String defPackage) {
    return activityWrapper.getIdentifier(name, defType, defPackage);
  }

  @IdRes
  protected final int getId(String name, String defPkgName) {
    return activityWrapper.getId(name, defPkgName);
  }

  @IdRes
  protected final int getId(String name) {
    return activityWrapper.getId(name);
  }

  @IdRes
  protected final int getIdFromAndroid(String name) {
    return activityWrapper.getIdFromAndroid(name);
  }

  @LayoutRes
  protected final int getLayoutId(String name, String defPkgName) {
    return activityWrapper.getLayoutId(name, defPkgName);
  }

  @LayoutRes
  protected final int getLayoutId(String name) {
    return activityWrapper.getLayoutId(name);
  }

  @LayoutRes
  protected final int getLayoutIdFromAndroid(String name) {
    return activityWrapper.getLayoutIdFromAndroid(name);
  }

  @ArrayRes
  protected final int getArrayId(String name, String defPkgName) {
    return activityWrapper.getArrayId(name, defPkgName);
  }

  @ArrayRes
  protected final int getArrayId(String name) {
    return activityWrapper.getArrayId(name);
  }

  @ArrayRes
  protected final int getArrayIdFromAndroid(String name) {
    return activityWrapper.getArrayIdFromAndroid(name);
  }

  @StringRes
  protected final int getStringId(String name, String defPkgName) {
    return activityWrapper.getStringId(name, defPkgName);
  }

  @StringRes
  protected final int getStringId(String name) {
    return activityWrapper.getStringId(name);
  }

  @StringRes
  protected final int getStringIdFromAndroid(String name) {
    return activityWrapper.getStringIdFromAndroid(name);
  }

  @DimenRes
  protected final int getDimenId(String name, String defPkgName) {
    return activityWrapper.getDimenId(name, defPkgName);
  }

  @DimenRes
  protected final int getDimenId(String name) {
    return activityWrapper.getDimenId(name);
  }

  @DimenRes
  protected final int getDimenIdFromAndroid(String name) {
    return activityWrapper.getDimenIdFromAndroid(name);
  }

  @ColorRes
  protected final int getColorId(String name, String defPkgName) {
    return activityWrapper.getColorId(name, defPkgName);
  }

  @ColorRes
  protected final int getColorId(String name) {
    return activityWrapper.getColorId(name);
  }

  @ColorRes
  protected final int getColorIdFromAndroid(String name) {
    return activityWrapper.getColorIdFromAndroid(name);
  }

  @DrawableRes
  protected final int getDrawableId(String name, String defPkgName) {
    return activityWrapper.getDrawableId(name, defPkgName);
  }

  @DrawableRes
  protected final int getDrawableId(String name) {
    return activityWrapper.getDrawableId(name);
  }

  @DrawableRes
  protected final int getDrawableIdFromAndroid(String name) {
    return activityWrapper.getDrawableIdFromAndroid(name);
  }

  @AnimRes
  protected final int getAnimationId(String name, String defPkgName) {
    return activityWrapper.getAnimationId(name, defPkgName);
  }

  @AnimRes
  protected final int getAnimationId(String name) {
    return activityWrapper.getAnimationId(name);
  }

  @AnimRes
  protected final int getAnimationIdFromAndroid(String name) {
    return activityWrapper.getAnimationIdFromAndroid(name);
  }

  @XmlRes
  protected final int getXmlId(String name, String defPkgName) {
    return activityWrapper.getXmlId(name, defPkgName);
  }

  @XmlRes
  protected final int getXmlId(String name) {
    return activityWrapper.getXmlId(name);
  }

  @XmlRes
  protected final int getXmlIdFromAndroid(String name) {
    return activityWrapper.getXmlIdFromAndroid(name);
  }

  protected final String getImgPathFromURI(Uri uri) {
    return activityWrapper.getImgPathFromURI(uri);
  }

  protected final Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
    return activityWrapper.rotateBitmap(bitmap, degrees);
  }

  /**
   * Used to check whether a certain permission has been applied for and the user agrees to
   * authorize, if not satisfied, reapply.
   *
   * @param reqCode Request code for permission.
   * @param permissions java.lang.String[] -> The name used to store one or more permissions.
   */
  protected final void chkAndApplyPermissions(int reqCode, String... permissions) {
    activityWrapper.chkAndApplyPermissions(reqCode, permissions);
  }

  /**
   * Check if the app has a new version update?
   *
   * @param versionName It is usually the version number obtained from the server.
   * @return "true" indicates that there is a new version of the application and it can be updated.
   */
  protected final boolean appHasNewVersion(String versionName) {
    return activityWrapper.appHasNewVersion(versionName);
  }

  /**
   * Push system notifications.
   *
   * @param id Used to confirm the identity of the notification.
   * @param importance The importance level of the notification.
   * @param style Notification display style.
   * @param autoCancel It is used to set whether to recycle the notification after the notification
   *     is pushed.
   * @param title The title of the notification.
   * @param message The content of the notification.
   * @param smallIcon Used to display a small icon in the upper left corner of the notification bar.
   * @param largeIcon Used to display the large icon in the upper left corner of the notification
   *     bar.
   * @param remoteViews Can be used to customize the view of notification content.
   * @param pendingIntent The intent delivery object included in the notification.
   */
  protected final synchronized void sendNotification(
      int id,
      int importance,
      NotificationCompat.Style style,
      boolean autoCancel,
      String title,
      String message,
      @IdRes int smallIcon,
      @IdRes int largeIcon,
      RemoteViews remoteViews,
      PendingIntent pendingIntent) {
    activityWrapper.sendNotification(
        id,
        importance,
        style,
        autoCancel,
        title,
        message,
        smallIcon,
        largeIcon,
        remoteViews,
        pendingIntent);
  }

  protected final void verboseLog(Object msg) {
    activityWrapper.verboseLog(msg);
  }

  protected final void verboseLog(Object msg, Throwable t) {
    activityWrapper.verboseLog(msg, t);
  }

  protected final void debugLog(Object msg) {
    activityWrapper.debugLog(msg);
  }

  protected final void debugLog(Object msg, Throwable t) {
    activityWrapper.debugLog(msg, t);
  }

  protected final void infoLog(Object msg) {
    activityWrapper.infoLog(msg);
  }

  protected final void infoLog(Object msg, Throwable t) {
    activityWrapper.infoLog(msg, t);
  }

  protected final void warnLog(Object msg) {
    activityWrapper.warnLog(msg);
  }

  protected final void warnLog(Object msg, Throwable t) {
    activityWrapper.warnLog(msg, t);
  }

  protected final void errorLog(Object msg) {
    activityWrapper.errorLog(msg);
  }

  protected final void errorLog(Object msg, Throwable t) {
    activityWrapper.errorLog(msg, t);
  }

  protected final void toastShort(Object msg) {
    activityWrapper.toastShort(msg);
  }

  protected final void toastShort(@StringRes int id) {
    activityWrapper.toastShort(id);
  }

  protected final void toastLong(Object msg) {
    activityWrapper.toastLong(msg);
  }

  protected final void toastLong(@StringRes int id) {
    activityWrapper.toastLong(id);
  }
}
