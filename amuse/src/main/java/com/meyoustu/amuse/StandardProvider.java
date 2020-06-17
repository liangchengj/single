package com.meyoustu.amuse;

import android.app.NotificationManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;

import com.meyoustu.amuse.listen.ClickListener;

import java.lang.annotation.Annotation;

/**
 * Created at 2020/6/17 16:57.
 *
 * @author Liangcheng Juves
 */
interface StandardProvider {

  String IDENTIFIER_DRAWABLE = "drawable";
  String IDENTIFIER_ID = "id";
  String IDENTIFIER_STRING = "string";
  String IDENTIFIER_ARRAY = "array";
  String IDENTIFIER_DIMEN = "dimen";
  String IDENTIFIER_COLOR = "color";
  String IDENTIFIER_LAYOUT = "layout";
  String IDENTIFIER_ANIMATION = "anim";
  String IDENTIFIER_XML = "xml";

  String PKG_ANDROID = "android";

  View getDecorView();

  @LayoutRes
  int initView();

  void setDecorViewRadius(int radius);

  void setViewRadius(int radius, @IdRes int... ids);

  void setViewOval(@IdRes int... ids);

  /** Used for the view press effect and contains the click event of the view. */
  void effectClick(View v, ClickListener clickListener);

  /** @return ShortcutManager -> Used to manage desktop shortcuts. */
  ShortcutManager getShortCutManager();

  /** Android 8.0 or above can set the desktop icon menu. */
  void setShortCuts(ShortcutInfo... shortcutInfo);

  /** Used to obtain the screen pixel density. */
  float getDisplayDensity();

  /** Get the color from the resource file. */
  @ColorInt
  int getResColor(@ColorRes int id);

  <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation, Class<T> classOfT);

  @Dimension
  int getDimensionPixelSize(@DimenRes int id);

  int getOrientation();

  boolean orientationIsPortrait();

  boolean orientationIsLandScape();

  @Dimension
  int getStatusBarHeight();

  @Dimension
  int getNavigationBarHeight();

  ConnectivityManager getConnectivityManager();

  NotificationManager getNotificationManager();

  InputMethodManager getInputMethodManager();

  TelephonyManager getTelephonyManager();

  WifiManager getWifiManager();

  int pxToDp(int px);

  int dpToPx(float dp);

  Animation loadAnimation(@AnimRes int id);

  /** Check whether the input method is displayed. */
  boolean isInputMethodShowing();

  int getIdentifier(String name, String defType, String defPackage);

  @IdRes
  int getId(String name, String defPkgName);

  @IdRes
  int getId(String name);

  @IdRes
  int getIdFromAndroid(String name);

  @LayoutRes
  int getLayoutId(String name, String defPkgName);

  @LayoutRes
  int getLayoutId(String name);

  @LayoutRes
  int getLayoutIdFromAndroid(String name);

  @ArrayRes
  int getArrayId(String name, String defPkgName);

  @ArrayRes
  int getArrayId(String name);

  @ArrayRes
  int getArrayIdFromAndroid(String name);

  @StringRes
  int getStringId(String name, String defPkgName);

  @StringRes
  int getStringId(String name);

  @StringRes
  int getStringIdFromAndroid(String name);

  @DimenRes
  int getDimenId(String name, String defPkgName);

  @DimenRes
  int getDimenId(String name);

  @DimenRes
  int getDimenIdFromAndroid(String name);

  @ColorRes
  int getColorId(String name, String defPkgName);

  @ColorRes
  int getColorId(String name);

  @ColorRes
  int getColorIdFromAndroid(String name);

  @DrawableRes
  int getDrawableId(String name, String defPkgName);

  @DrawableRes
  int getDrawableId(String name);

  @DrawableRes
  int getDrawableIdFromAndroid(String name);

  @AnimRes
  int getAnimationId(String name, String defPkgName);

  @AnimRes
  int getAnimationId(String name);

  @AnimRes
  int getAnimationIdFromAndroid(String name);

  @XmlRes
  int getXmlId(String name, String defPkgName);

  @XmlRes
  int getXmlId(String name);

  @XmlRes
  int getXmlIdFromAndroid(String name);

  String getImgPathFromURI(Uri uri);

  Bitmap rotateBitmap(Bitmap bitmap, int degrees);

  /**
   * Used to check whether a certain permission has been applied for and the user agrees to
   * authorize, if not satisfied, reapply.
   *
   * @param reqCode Request code for permission.
   * @param permissions java.lang.String[] -> The name used to store one or more permissions.
   */
  void chkAndApplyPermissions(int reqCode, String... permissions);

  /**
   * Check if the app has a new version update?
   *
   * @param versionName It is usually the version number obtained from the server.
   * @return "true" indicates that there is a new version of the application and it can be updated.
   */
  boolean appHasNewVersion(String versionName);

  void verboseLog(Object msg);

  void verboseLog(Object msg, Throwable t);

  void debugLog(Object msg);

  void debugLog(Object msg, Throwable t);

  void infoLog(Object msg);

  void infoLog(Object msg, Throwable t);

  void warnLog(Object msg);

  void warnLog(Object msg, Throwable t);

  void errorLog(Object msg);

  void errorLog(Object msg, Throwable t);

  void toastShort(Object msg);

  void toastShort(@StringRes int id);

  void toastLong(Object msg);

  void toastLong(@StringRes int id);
}
