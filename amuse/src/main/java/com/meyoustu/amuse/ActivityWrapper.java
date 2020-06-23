package com.meyoustu.amuse;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.meyoustu.amuse.annotation.ContentView;
import com.meyoustu.amuse.listen.ClickListener;
import com.meyoustu.amuse.listen.EffectClickListener;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static com.meyoustu.amuse.App.getResId;

/**
 * Created at 2020/6/17 17:28.
 *
 * @author Liangcheng Juves
 */
abstract class ActivityWrapper implements ActivityWrapperImpl {

  private Activity activity;

  ActivityWrapper(Activity activity) {
    this.activity = activity;
  }

  @Override
  public View getDecorView() {
    return activity.getWindow().getDecorView();
  }

  @Override
  public @LayoutRes int initView() {
    @LayoutRes int contentViewVal = getClassAnnotatedValue(ContentView.class, int.class);
    if (contentViewVal != -1) {
      activity.setContentView(contentViewVal);
    }
    return -1;
  }

  @Override
  public void setDecorViewRadius(int radius) {
    App.setViewRadius(radius, getDecorView());
  }

  @Override
  public void setViewRadius(int radius, @IdRes int... ids) {
    if (null != ids) {
      if (ids.length != 0) {
        View[] views = new View[ids.length];
        for (int i = 0; i < views.length; i++) {
          views[i] = activity.findViewById(ids[i]);
        }
        App.setViewRadius(radius, views);
      }
    }
  }

  @Override
  public void setViewOval(@IdRes int... ids) {
    if (null != ids) {
      if (ids.length != 0) {
        View[] views = new View[ids.length];
        for (int i = 0; i < views.length; i++) {
          views[i] = activity.findViewById(ids[i]);
        }
        App.setViewOval(views);
      }
    }
  }

  @Override
  public void effectClick(View v, final ClickListener clickListener) {
    clickListener.initialize(v, v.getId());
    v.setOnTouchListener(
        new EffectClickListener() {
          @Override
          public void onTouchDown(View v, int vId) {
            clickListener.onTouchDown(v, vId);
          }

          @Override
          public void onTouchUp(View v, int vId) {
            clickListener.onTouchUp(v, vId);
          }

          @Override
          public void onClick(View v, int vId) {
            clickListener.onClick(v, vId);
          }
        });
  }

  @Override
  public ShortcutManager getShortCutManager() {
    return SDK_INT >= N_MR1 ? activity.getSystemService(ShortcutManager.class) : null;
  }

  @Override
  public void setShortCuts(ShortcutInfo... shortcutInfo) {
    if (null != shortcutInfo) {
      ShortcutManager shortcutManager = getShortCutManager();
      if (null != shortcutManager) {
        if (SDK_INT >= O) {
          for (ShortcutInfo info : shortcutInfo) {
            if (null != info) {
              shortcutManager.addDynamicShortcuts(Arrays.asList(info));
            }
          }
        }
      }
    }
  }

  @Override
  public float getDisplayDensity() {
    return activity.getResources().getDisplayMetrics().density;
  }

  @Override
  public @ColorRes int getResColor(@IdRes int id) {
    return App.getResColor(activity, id);
  }

  @Override
  public <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation, Class<T> classOfT) {
    return App.getClassAnnotatedValue(activity, annotation, classOfT);
  }

  @Override
  public @Dimension int getDimensionPixelSize(@DimenRes int id) {
    return activity.getResources().getDimensionPixelSize(id);
  }

  @Override
  public int getOrientation() {
    return activity.getResources().getConfiguration().orientation;
  }

  @Override
  public boolean orientationIsPortrait() {
    return getOrientation() == ORIENTATION_PORTRAIT;
  }

  @Override
  public boolean orientationIsLandScape() {
    return getOrientation() == ORIENTATION_LANDSCAPE;
  }

  @Override
  public @Dimension int getStatusBarHeight() {
    String name = "status_bar_height";
    return orientationIsPortrait()
        ? getDimensionPixelSize(getDimenIdFromAndroid(name))
        : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
  }

  @Override
  public @Dimension int getNavigationBarHeight() {
    String name = "navigation_bar_height";
    return orientationIsPortrait()
        ? getDimensionPixelSize(getDimenIdFromAndroid(name))
        : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
  }

  @Override
  public ConnectivityManager getConnectivityManager() {
    return ((ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE));
  }

  @Override
  public NotificationManager getNotificationManager() {
    return ((NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE));
  }

  @Override
  public InputMethodManager getInputMethodManager() {
    return ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE));
  }

  @Override
  public TelephonyManager getTelephonyManager() {
    return ((TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE));
  }

  @Override
  public WifiManager getWifiManager() {
    return ((WifiManager) ((Context) activity).getSystemService(WIFI_SERVICE));
  }

  @Override
  public int pxToDp(int px) {
    return (int) (px / getDisplayDensity() + 0.5f);
  }

  @Override
  public int dpToPx(@Dimension float dp) {
    return (int) (dp * getDisplayDensity() + 0.5f);
  }

  @Override
  public Animation loadAnimation(@AnimRes int id) {
    return AnimationUtils.loadAnimation(activity, id);
  }

  @Override
  public boolean isInputMethodShowing() {
    Rect rect = new Rect();
    getDecorView().getWindowVisibleDisplayFrame(rect);
    return getDecorView().getHeight() - rect.bottom > getNavigationBarHeight();
  }

  @Override
  public int getIdentifier(String name, String defType, String defPackage) {
    return activity.getResources().getIdentifier(name, defType, defPackage);
  }

  @Override
  public @IdRes int getId(String name, String defPkgName) {
    return getResId(activity, name, defPkgName);
  }

  @Override
  public int getId(String name) {
    return getId(name, activity.getPackageName());
  }

  @Override
  public int getIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getLayoutId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getLayoutId(String name) {
    return 0;
  }

  @Override
  public int getLayoutIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getArrayId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getArrayId(String name) {
    return 0;
  }

  @Override
  public int getArrayIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getStringId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getStringId(String name) {
    return 0;
  }

  @Override
  public int getStringIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getDimenId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getDimenId(String name) {
    return 0;
  }

  @Override
  public int getDimenIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getColorId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getColorId(String name) {
    return 0;
  }

  @Override
  public int getColorIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getDrawableId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getDrawableId(String name) {
    return 0;
  }

  @Override
  public int getDrawableIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getAnimationId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getAnimationId(String name) {
    return 0;
  }

  @Override
  public int getAnimationIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public int getXmlId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getXmlId(String name) {
    return 0;
  }

  @Override
  public int getXmlIdFromAndroid(String name) {
    return 0;
  }

  @Override
  public String getImgPathFromURI(Uri uri) {
    return null;
  }

  @Override
  public Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
    return null;
  }

  @Override
  public void chkAndApplyPermissions(int reqCode, String... permissions) {}

  @Override
  public boolean appHasNewVersion(String versionName) {
    return false;
  }

  @Override
  public void verboseLog(Object msg) {}

  @Override
  public void verboseLog(Object msg, Throwable t) {}

  @Override
  public void debugLog(Object msg) {}

  @Override
  public void debugLog(Object msg, Throwable t) {}

  @Override
  public void infoLog(Object msg) {}

  @Override
  public void infoLog(Object msg, Throwable t) {}

  @Override
  public void warnLog(Object msg) {}

  @Override
  public void warnLog(Object msg, Throwable t) {}

  @Override
  public void errorLog(Object msg) {}

  @Override
  public void errorLog(Object msg, Throwable t) {}

  @Override
  public void toastShort(Object msg) {}

  @Override
  public void toastShort(int id) {}

  @Override
  public void toastLong(Object msg) {}

  @Override
  public void toastLong(int id) {}
}

interface ActivityWrapperImpl {
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

  int dpToPx(@Dimension float dp);

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
