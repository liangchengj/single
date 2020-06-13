package com.meyoustu.amuse;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
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
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;

import com.meyoustu.amuse.annotation.ContentView;
import com.meyoustu.amuse.listen.ClickListener;
import com.meyoustu.amuse.util.Toast;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.Context.TELEPHONY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.graphics.Bitmap.createBitmap;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static android.provider.SyncStateContract.Columns.DATA;
import static com.meyoustu.amuse.Activity.IDENTIFIER_ARRAY;
import static com.meyoustu.amuse.Activity.IDENTIFIER_COLOR;
import static com.meyoustu.amuse.Activity.IDENTIFIER_DIMEN;
import static com.meyoustu.amuse.Activity.IDENTIFIER_DRAWABLE;
import static com.meyoustu.amuse.Activity.IDENTIFIER_LAYOUT;
import static com.meyoustu.amuse.Activity.IDENTIFIER_STRING;
import static com.meyoustu.amuse.Activity.IDENTIFIER_XML;
import static com.meyoustu.amuse.Activity.PKG_ANDROID;
import static com.meyoustu.amuse.App.getAnimId;
import static com.meyoustu.amuse.App.getResId;
import static com.meyoustu.amuse.listen.ClickListener.RESP_TIME_MILLLIS;
import static java.lang.System.currentTimeMillis;

/**
 * @author Liangcheng Juves Created at 2020/6/10 17:52
 *     <p>Default interface methods are only supported starting with Android N (--min-api 24):
 *     boolean com.meyoustu.amuse.Standard.appHasNewVersion(java.lang.String)]
 */
@RequiresApi(N)
public interface Standard {

  default View getDecorView() {
    return ((Activity) this).getWindow().getDecorView();
  }

  default @LayoutRes int initView() {
    @LayoutRes int contentViewVal = getClassAnnotatedValue(ContentView.class, int.class);
    if (contentViewVal != -1) {
      ((Activity) this).setContentView(contentViewVal);
    }
    return -1;
  }

  default void setDecorViewRadius(int radius) {
    App.setViewRadius(radius, getDecorView());
  }

  default void setViewRadius(int radius, @IdRes int... ids) {
    if (null != ids) {
      if (ids.length != 0) {
        View[] views = new View[ids.length];
        for (int i = 0; i < views.length; i++) {
          views[i] = ((Activity) this).findViewById(ids[i]);
        }
        App.setViewRadius(radius, views);
      }
    }
  }

  default void setViewOval(@IdRes int... ids) {
    if (null != ids) {
      if (ids.length != 0) {
        View[] views = new View[ids.length];
        for (int i = 0; i < views.length; i++) {
          views[i] = ((Activity) this).findViewById(ids[i]);
        }
        App.setViewOval(views);
      }
    }
  }

  /* Used for the view press effect and contains the click event of the view. */
  default void effectClick(View v, final ClickListener clickListener) {
    v.setOnTouchListener(
        new View.OnTouchListener() {
          private long touchDown;

          @Override
          public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
              clickListener.onTouchDown(v, event);
              touchDown = currentTimeMillis();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
              clickListener.onTouchUp(v, event);
              if (currentTimeMillis() - touchDown <= RESP_TIME_MILLLIS) {
                clickListener.onClick(v);
              }
            }
            return false;
          }
        });
  }

  /** @return ShortcutManager -> Used to manage desktop shortcuts. */
  default ShortcutManager getShortCutManager() {
    return SDK_INT >= N_MR1 ? ((Activity) this).getSystemService(ShortcutManager.class) : null;
  }

  /* Android 8.0 or above can set the desktop icon menu. */
  default void setShortCuts(ShortcutInfo... shortcutInfo) {
    ShortcutManager shortcutManager = getShortCutManager();
    if (shortcutManager != null) {
      if (SDK_INT >= O && shortcutInfo != null) {
        for (ShortcutInfo info : shortcutInfo) {
          shortcutManager.addDynamicShortcuts(Arrays.asList(info));
        }
      }
    }
  }

  /* Used to obtain the screen pixel density. */
  default float getDisplayDensity() {
    return ((Activity) this).getResources().getDisplayMetrics().density;
  }

  /* Get the color from the resource file. */
  default @ColorInt int getResColor(@ColorRes int id) {
    return App.getResColor(((Activity) this), id);
  }

  default <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation, Class<T> classOfT) {
    return App.getClassAnnotatedValue(((Activity) this), annotation, classOfT);
  }

  default @Dimension int getDimensionPixelSize(@DimenRes int id) {
    return ((Activity) this).getResources().getDimensionPixelSize(id);
  }

  default int getOrientation() {
    return ((Activity) this).getResources().getConfiguration().orientation;
  }

  default boolean orientationIsPortrait() {
    return getOrientation() == ORIENTATION_PORTRAIT;
  }

  default boolean orientationIsLandScape() {
    return getOrientation() == ORIENTATION_LANDSCAPE;
  }

  default @Dimension int getStatusBarHeight() {
    String name = "status_bar_height";
    return orientationIsPortrait()
        ? getDimensionPixelSize(getDimenIdFromAndroid(name))
        : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
  }

  default @Dimension int getNavigationBarHeight() {
    String name = "navigation_bar_height";
    return orientationIsPortrait()
        ? getDimensionPixelSize(getDimenIdFromAndroid(name))
        : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
  }

  default ConnectivityManager getConnectivityManager() {
    return ((ConnectivityManager) ((Activity) this).getSystemService(CONNECTIVITY_SERVICE));
  }

  default NotificationManager getNotificationManager() {
    return ((NotificationManager) ((Activity) this).getSystemService(NOTIFICATION_SERVICE));
  }

  default InputMethodManager getInputMethodManager() {
    return ((InputMethodManager) ((Activity) this).getSystemService(INPUT_METHOD_SERVICE));
  }

  default TelephonyManager getTelephonyManager() {
    return ((TelephonyManager) ((Activity) this).getSystemService(TELEPHONY_SERVICE));
  }

  default WifiManager getWifiManager() {
    return ((WifiManager) ((Context) this).getSystemService(WIFI_SERVICE));
  }

  default int pxToDp(int px) {
    return (int) (px / getDisplayDensity() + 0.5f);
  }

  default int dpToPx(float dp) {
    return (int) (dp * getDisplayDensity() + 0.5f);
  }

  default Animation loadAnimation(@AnimRes int id) {
    return AnimationUtils.loadAnimation(((Activity) this), id);
  }

  /** Check whether the input method is displayed. */
  default boolean isInputMethodShowing() {
    Rect rect = new Rect();
    getDecorView().getWindowVisibleDisplayFrame(rect);
    return getDecorView().getHeight() - rect.bottom > getNavigationBarHeight();
  }

  default int getIdentifier(String name, String defType, String defPackage) {
    return ((Activity) this).getResources().getIdentifier(name, defType, defPackage);
  }

  default @IdRes int getId(String name, String defPkgName) {
    return getResId(((Activity) this), name, defPkgName);
  }

  default @IdRes int getId(String name) {
    return getId(name, ((Activity) this).getPackageName());
  }

  default @IdRes int getIdFromAndroid(String name) {
    return getId(name, PKG_ANDROID);
  }

  default @LayoutRes int getLayoutId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_LAYOUT, defPkgName);
  }

  default @LayoutRes int getLayoutId(String name) {
    return getLayoutId(name, ((Activity) this).getPackageName());
  }

  default @LayoutRes int getLayoutIdFromAndroid(String name) {
    return getLayoutId(name, PKG_ANDROID);
  }

  default @ArrayRes int getArrayId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_ARRAY, defPkgName);
  }

  default @ArrayRes int getArrayId(String name) {
    return getArrayId(name, ((Activity) this).getPackageName());
  }

  default @ArrayRes int getArrayIdFromAndroid(String name) {
    return getArrayId(name, PKG_ANDROID);
  }

  default @StringRes int getStringId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_STRING, defPkgName);
  }

  default @StringRes int getStringId(String name) {
    return getStringId(name, ((Activity) this).getPackageName());
  }

  default @StringRes int getStringIdFromAndroid(String name) {
    return getStringId(name, PKG_ANDROID);
  }

  default @DimenRes int getDimenId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_DIMEN, defPkgName);
  }

  default @DimenRes int getDimenId(String name) {
    return getDimenId(name, ((Activity) this).getPackageName());
  }

  default @DimenRes int getDimenIdFromAndroid(String name) {
    return getDimenId(name, PKG_ANDROID);
  }

  default @ColorRes int getColorId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_COLOR, defPkgName);
  }

  default @ColorRes int getColorId(String name) {
    return getColorId(name, ((Activity) this).getPackageName());
  }

  default @ColorRes int getColorIdFromAndroid(String name) {
    return getColorId(name, PKG_ANDROID);
  }

  default @DrawableRes int getDrawableId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_DRAWABLE, defPkgName);
  }

  default @DrawableRes int getDrawableId(String name) {
    return getDrawableId(name, ((Activity) this).getPackageName());
  }

  default @DrawableRes int getDrawableIdFromAndroid(String name) {
    return getDrawableId(name, PKG_ANDROID);
  }

  default @AnimRes int getAnimationId(String name, String defPkgName) {
    return getAnimId(((Activity) this), name, defPkgName);
  }

  default @AnimRes int getAnimationId(String name) {
    return getAnimationId(name, ((Activity) this).getPackageName());
  }

  default @AnimRes int getAnimationIdFromAndroid(String name) {
    return getAnimationId(name, PKG_ANDROID);
  }

  default @XmlRes int getXmlId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_XML, defPkgName);
  }

  default @XmlRes int getXmlId(String name) {
    return getXmlId(name, ((Activity) this).getPackageName());
  }

  default @XmlRes int getXmlIdFromAndroid(String name) {
    return getXmlId(name, PKG_ANDROID);
  }

  default String getImgPathFromURI(Uri uri) {
    String result;
    Cursor cursor = null;

    try {
      cursor = ((Activity) this).getContentResolver().query(uri, null, null, null, null);
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

  default Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
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
  default void chkAndApplyPermissions(int reqCode, String... permissions) {
    App.chkAndApplyPermissions(((Activity) this), reqCode, permissions);
  }

  /**
   * Check if the app has a new version update?
   *
   * @param versionName It is usually the version number obtained from the server.
   * @return "true" indicates that there is a new version of the application and it can be updated.
   */
  default boolean appHasNewVersion(String versionName) {
    try {
      versionName = versionName.replace("_", "");
      versionName = versionName.replace(".", "");
      versionName = versionName.replace("-", "");

      PackageInfo pkgInfo =
          ((Activity) this)
              .getPackageManager()
              .getPackageInfo(((Activity) this).getPackageName(), 0);
      if (null == pkgInfo) {
        return false;
      }

      pkgInfo.versionName = versionName.replace("_", "");
      pkgInfo.versionName = versionName.replace(".", "");
      pkgInfo.versionName = versionName.replace("-", "");

      /* Use certain rules to parse the application version number. */
      long versionNow = Long.parseLong(pkgInfo.versionName);
      /* Use certain rules to parse the application version number. */
      long version = Long.parseLong(versionName);

      return version > versionNow;
    } catch (PackageManager.NameNotFoundException | NumberFormatException e) {
      errorLog("appHasNewVersion", e);
      return false;
    }
  }

  default void verboseLog(Object msg) {
    App.verboseLog(((Activity) this), msg);
  }

  default void verboseLog(Object msg, Throwable t) {
    App.verboseLog(((Activity) this), msg, t);
  }

  default void debugLog(Object msg) {
    App.debugLog(((Activity) this), msg);
  }

  default void debugLog(Object msg, Throwable t) {
    App.debugLog(((Activity) this), msg, t);
  }

  default void infoLog(Object msg) {
    App.infoLog(((Activity) this), msg);
  }

  default void infoLog(Object msg, Throwable t) {
    App.infoLog(((Activity) this), msg, t);
  }

  default void warnLog(Object msg) {
    App.warnLog(((Activity) this), msg);
  }

  default void warnLog(Object msg, Throwable t) {
    App.warnLog(((Activity) this), msg, t);
  }

  default void errorLog(Object msg) {
    App.errorLog(((Activity) this), msg);
  }

  default void errorLog(Object msg, Throwable t) {
    App.errorLog(((Activity) this), msg, t);
  }

  default void toastShort(Object msg) {
    Toast.showShort(((Activity) this), msg);
  }

  default void toastShort(@StringRes int id) {
    Toast.showShort(((Activity) this), id);
  }

  default void toastLong(Object msg) {
    Toast.showLong(((Activity) this), msg);
  }

  default void toastLong(@StringRes int id) {
    Toast.showLong(((Activity) this), id);
  }
}
