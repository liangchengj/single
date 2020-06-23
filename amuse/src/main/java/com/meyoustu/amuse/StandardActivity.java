package com.meyoustu.amuse;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.meyoustu.amuse.util.Toast;

import java.lang.annotation.Annotation;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.graphics.Bitmap.createBitmap;
import static android.provider.SyncStateContract.Columns.DATA;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_ARRAY;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_COLOR;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_DIMEN;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_DRAWABLE;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_LAYOUT;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_STRING;
import static com.meyoustu.amuse.ActivityConstants.IDENTIFIER_XML;
import static com.meyoustu.amuse.ActivityConstants.PKG_ANDROID;
import static com.meyoustu.amuse.App.getAnimId;
import static com.meyoustu.amuse.App.getResId;

/**
 * Created at 2020/6/14 10:16.
 *
 * <p>Note: Classes that inherit android.app.Activity cannot use AndroidX related libraries.
 *
 * @author Liangcheng Juves
 */
public abstract class StandardActivity extends android.app.Activity {

  /* Construction method.
  Load the dynamic link library by detecting whether it is annotated with "@Native". */
  protected StandardActivity() {
    activityWrapper.loadJNILibByAnnotation();
  }

  protected StandardActivity(@LayoutRes int layoutId) {
    this();
    this.layoutId = layoutId;
  }

  @LayoutRes private int layoutId = -1;

  /* Get the context of the current Activity. */
  protected final Context ctx = this;

  private ActivityWrapper activityWrapper;

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

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      onDecorViewConfig(activityWrapper.decorView);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    onDecorViewConfig(activityWrapper.decorView);
  }

  @Override
  public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    onDecorViewConfig(activityWrapper.decorView);
  }

  @LayoutRes
  int initView() {
    return activityWrapper.initView();
  }

  void onDecorViewConfig(View decorView
      /*The current class is not used and is provided to subclasses.*/ ) {
    activityWrapper.onDecorViewConfig(decorView);
  }

  void setDecorViewRadius(int radius) {
    activityWrapper.setDecorViewRadius(radius);
  }

  /* Set the Activity window to full screen display. */
  void setWindowFullScreen() {
    activityWrapper.setWindowFullScreen();
  }

  void setViewRadius(int radius, @IdRes int... ids) {
    activityWrapper.setViewRadius(radius, ids);
  }

  void setViewOval(@IdRes int... ids) {
    activityWrapper.setViewOval(ids);
  }

  /** Used for the view press effect and contains the click event of the view. */
  void effectClick(View v, final ClickListener clickListener) {
    activityWrapper.effectClick(v, clickListener);
  }

  /** @return ShortcutManager -> Used to manage desktop shortcuts. */
  ShortcutManager getShortCutManager() {
    return activityWrapper.getShortCutManager();
  }

  /** Android 8.0 or above can set the desktop icon menu. */
  void setShortCuts(ShortcutInfo... shortcutInfo) {
    activityWrapper.setShortCuts(shortcutInfo);
  }

  /** Used to obtain the screen pixel density. */
  float getDisplayDensity() {
    return activityWrapper.getDisplayDensity();
  }

  /** Get the color from the resource file. */
  @ColorInt
  int getResColor(@ColorRes int id) {
    return activityWrapper.getResColor(id);
  }

  <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation, Class<T> classOfT) {
    return activityWrapper.getClassAnnotatedValue(annotation, classOfT);
  }

  @Dimension
  int getDimensionPixelSize(@DimenRes int id) {
    return activityWrapper.getDimensionPixelSize(id);
  }

  int getOrientation() {
    return activityWrapper.getOrientation();
  }

  boolean orientationIsPortrait() {
    return activityWrapper.orientationIsPortrait();
  }

  boolean orientationIsLandScape() {
    return activityWrapper.orientationIsPortrait();
  }

  @Dimension
  int getStatusBarHeight() {
    return activityWrapper.getStatusBarHeight();
  }

  @Dimension
  int getNavigationBarHeight() {
    return activityWrapper.getNavigationBarHeight();
  }

  ConnectivityManager getConnectivityManager() {
    return activityWrapper.getConnectivityManager();
  }

  NotificationManager getNotificationManager() {
    return activityWrapper.getNotificationManager();
  }

  InputMethodManager getInputMethodManager() {
    return activityWrapper.getInputMethodManager();
  }

  TelephonyManager getTelephonyManager() {
    return activityWrapper.getTelephonyManager();
  }

  WifiManager getWifiManager() {
    return activityWrapper.getWifiManager();
  }

  int pxToDp(int px) {
    return activityWrapper.pxToDp(px);
  }

  int dpToPx(@Dimension float dp) {
    return activityWrapper.dpToPx(dp);
  }

  Animation loadAnimation(@AnimRes int id) {
    return activityWrapper.loadAnimation(id);
  }

  /** Check whether the input method is displayed. */
  boolean isInputMethodShowing() {
    return activityWrapper.isInputMethodShowing();
  }

  int getIdentifier(String name, String defType, String defPackage) {
    return activityWrapper.getIdentifier(name, defType, defPackage);
  }

  @IdRes
  int getId(String name, String defPkgName) {
    return activityWrapper.getId(name, defPkgName);
  }

  @IdRes
  int getId(String name) {
    return activityWrapper.getId(name);
  }

  @IdRes
  int getIdFromAndroid(String name) {
    return activityWrapper.getIdFromAndroid(name);
  }

  @LayoutRes
  int getLayoutId(String name, String defPkgName) {
    return activityWrapper.getLayoutId(name, defPkgName);
  }

  @LayoutRes
  int getLayoutId(String name) {
    return activityWrapper.getLayoutId(name);
  }

  @LayoutRes
  int getLayoutIdFromAndroid(String name) {
    return activityWrapper.getLayoutIdFromAndroid(name);
  }

  @ArrayRes
  int getArrayId(String name, String defPkgName) {
    return activityWrapper.getArrayId(name, defPkgName);
  }

  @ArrayRes
  int getArrayId(String name) {
    return activityWrapper.getArrayId(name);
  }

  @ArrayRes
  int getArrayIdFromAndroid(String name) {
    return activityWrapper.getArrayIdFromAndroid(name);
  }

  @StringRes
  int getStringId(String name, String defPkgName) {
    return activityWrapper.getStringId(name, defPkgName);
  }

  @StringRes
  int getStringId(String name) {
    return activityWrapper.getStringId(name);
  }

  @StringRes
  int getStringIdFromAndroid(String name) {
    return activityWrapper.getStringIdFromAndroid(name);
  }

  @DimenRes
  int getDimenId(String name, String defPkgName) {
    return activityWrapper.getDimenId(name, defPkgName);
  }

  @DimenRes
  int getDimenId(String name) {
    return activityWrapper.getDimenId(name);
  }

  @DimenRes
  int getDimenIdFromAndroid(String name) {
    return activityWrapper.getDimenIdFromAndroid(name);
  }

  @ColorRes
  int getColorId(String name, String defPkgName) {
    return activityWrapper.getColorId(name, defPkgName);
  }

  @ColorRes
  int getColorId(String name) {
    return activityWrapper.getColorId(name);
  }

  @ColorRes
  int getColorIdFromAndroid(String name) {
    return getColorId(name, PKG_ANDROID);
  }

  @DrawableRes
  int getDrawableId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_DRAWABLE, defPkgName);
  }

  @DrawableRes
  int getDrawableId(String name) {
    return getDrawableId(name, activity.getPackageName());
  }

  @DrawableRes
  int getDrawableIdFromAndroid(String name) {
    return getDrawableId(name, PKG_ANDROID);
  }

  @AnimRes
  int getAnimationId(String name, String defPkgName) {
    return getAnimId(activity, name, defPkgName);
  }

  @AnimRes
  int getAnimationId(String name) {
    return getAnimationId(name, activity.getPackageName());
  }

  @AnimRes
  int getAnimationIdFromAndroid(String name) {
    return getAnimationId(name, PKG_ANDROID);
  }

  @XmlRes
  int getXmlId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_XML, defPkgName);
  }

  @XmlRes
  int getXmlId(String name) {
    return getXmlId(name, activity.getPackageName());
  }

  @XmlRes
  int getXmlIdFromAndroid(String name) {
    return getXmlId(name, PKG_ANDROID);
  }

  String getImgPathFromURI(Uri uri) {
    String result;
    Cursor cursor = null;

    try {
      cursor = activity.getContentResolver().query(uri, null, null, null, null);
    } catch (Throwable t) {
      errorLog("getImgPathFromURI", t);
    }

    if (null == cursor) {
      result = uri.getPath();
    } else {
      cursor.moveToFirst();
      result = cursor.getString(cursor.getColumnIndex(DATA));
      cursor.close();
    }

    return result;
  }

  Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degrees);

    Bitmap flag = createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

    if (!flag.isRecycled() && degrees != 0) {
      flag.recycle();
    }
    return flag;
  }

  /**
   * Used to check whether a certain permission has been applied for and the user agrees to
   * authorize, if not satisfied, reapply.
   *
   * @param reqCode Request code for permission.
   * @param permissions java.lang.String[] -> The name used to store one or more permissions.
   */
  void chkAndApplyPermissions(int reqCode, String... permissions) {
    App.chkAndApplyPermissions(activity, reqCode, permissions);
  }

  /**
   * Check if the app has a new version update?
   *
   * @param versionName It is usually the version number obtained from the server.
   * @return "true" indicates that there is a new version of the application and it can be updated.
   */
  boolean appHasNewVersion(String versionName) {
    try {
      PackageInfo pkgInfo =
          activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
      if (null == pkgInfo) {
        return false;
      }
      long versionNow = parseVersion(pkgInfo.versionName);
      long version = parseVersion(versionName);
      return version > versionNow;
    } catch (PackageManager.NameNotFoundException | NumberFormatException e) {
      errorLog("appHasNewVersion", e);
      return false;
    }
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
  synchronized void sendNotification(
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
    App.sendNotification(
        activity,
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

  void verboseLog(Object msg) {
    App.verboseLog(activity, msg);
  }

  void verboseLog(Object msg, Throwable t) {
    App.verboseLog(activity, msg, t);
  }

  void debugLog(Object msg) {
    App.debugLog(activity, msg);
  }

  void debugLog(Object msg, Throwable t) {
    App.debugLog(activity, msg, t);
  }

  void infoLog(Object msg) {
    App.infoLog(activity, msg);
  }

  void infoLog(Object msg, Throwable t) {
    App.infoLog(activity, msg, t);
  }

  void warnLog(Object msg) {
    App.warnLog(activity, msg);
  }

  void warnLog(Object msg, Throwable t) {
    App.warnLog(activity, msg, t);
  }

  void errorLog(Object msg) {
    App.errorLog(activity, msg);
  }

  void errorLog(Object msg, Throwable t) {
    App.errorLog(activity, msg, t);
  }

  void toastShort(Object msg) {
    Toast.showShort(activity, msg);
  }

  void toastShort(@StringRes int id) {
    Toast.showShort(activity, id);
  }

  void toastLong(Object msg) {
    Toast.showLong(activity, msg);
  }

  void toastLong(@StringRes int id) {
    Toast.showLong(activity, id);
  }
}
