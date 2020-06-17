package com.meyoustu.amuse;

import android.app.NotificationManager;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.core.graphics.ColorUtils;
import androidx.fragment.app.FragmentActivity;

import com.meyoustu.amuse.annotation.ContentView;
import com.meyoustu.amuse.annotation.DecorViewConfig;
import com.meyoustu.amuse.annotation.Native;
import com.meyoustu.amuse.annotation.sysbar.NavigationBarColor;
import com.meyoustu.amuse.annotation.sysbar.StatusBarColor;
import com.meyoustu.amuse.annotation.sysbar.WindowFullScreen;
import com.meyoustu.amuse.graphics.Color;
import com.meyoustu.amuse.listen.ClickListener;
import com.meyoustu.amuse.util.Toast;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.graphics.Bitmap.createBitmap;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.N;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static android.os.Build.VERSION_CODES.P;
import static android.provider.SyncStateContract.Columns.DATA;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static com.meyoustu.amuse.App.getAnimId;
import static com.meyoustu.amuse.App.getResId;
import static com.meyoustu.amuse.annotation.DecorViewConfig.HIDE_SYS_BARS;
import static com.meyoustu.amuse.listen.ClickListener.RESP_TIME_MILLLIS;
import static java.lang.System.currentTimeMillis;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public abstract class Activity extends FragmentActivity implements ActivityProvider.Impl {



  /* Construction method.
  Load the dynamic link library by detecting whether it is annotated with "@Native". */
  protected Activity() {
    String[] nativeLibNames = getClassAnnotatedValue(Native.class, String[].class);
    if (null != nativeLibNames) {
      for (String nativeLibName : nativeLibNames) {
        if (!nativeLibName.isEmpty()) {
          System.loadLibrary(nativeLibName);
        }
      }
    }
  }

  private int decorViewSystemUiVisibility;

  private void setDecorViewSystemUiVisibility(int visibility) {
    decorViewSystemUiVisibility |= visibility;
    decorView.setSystemUiVisibility(decorViewSystemUiVisibility);
  }

  @LayoutRes private int layoutId = -1;
  private View decorView;

  /* Get the context of the current Activity. */
  protected final Context ctx = this;

  protected final View getDecorView() {
    return decorView;
  }

  protected Activity(@LayoutRes int layoutId) {
    this();
    this.layoutId = layoutId;
  }

  /* Set the Activity window to full screen display. */
  protected final void setWindowFullScreen() {
    if (SDK_INT <= KITKAT) {
      getWindow()
          .setFlags(
              WindowManager.LayoutParams.FLAG_FULLSCREEN,
              WindowManager.LayoutParams.FLAG_FULLSCREEN);
      decorView.postDelayed(
          new Runnable() {
            @Override
            public void run() {
              setWindowFullScreen();
            }
          },
          1500);
    }
    decorView.setSystemUiVisibility(HIDE_SYS_BARS);
  }

  protected void onDecorViewConfig(View decorView
      /*The current class is not used and is provided to subclasses.*/ ) {
    setDecorViewSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE);
    int decorViewConfigVal = getClassAnnotatedValue(DecorViewConfig.class, int.class);
    if (decorViewConfigVal != -1) {
      setDecorViewSystemUiVisibility(decorViewConfigVal);
    } else {
      initSysBarByAnnotation();
      // In order to adaptively initialize the System Bar.
      initSysBar();
      if (getClass().isAnnotationPresent(WindowFullScreen.class)) {
        setWindowFullScreen();
      }
    }
  }

  protected @LayoutRes int initView() {
    @LayoutRes int contentViewVal = getClassAnnotatedValue(ContentView.class, int.class);
    if (contentViewVal != -1) {
      setContentView(contentViewVal);
    }
    return -1;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    decorView = getWindow().getDecorView();
    onDecorViewConfig(Activity.this.decorView);
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

  protected final void setDecorViewRadius(int radius) {
    App.setViewRadius(radius, decorView);
  }

  protected final void setViewRadius(int radius, @IdRes int... ids) {
    if (null != ids) {
      if (ids.length != 0) {
        View[] views = new View[ids.length];
        for (int i = 0; i < views.length; i++) {
          views[i] = findViewById(ids[i]);
        }
        App.setViewRadius(radius, views);
      }
    }
  }

  protected final void setViewOval(@IdRes int... ids) {
    if (null != ids) {
      if (ids.length != 0) {
        View[] views = new View[ids.length];
        for (int i = 0; i < views.length; i++) {
          views[i] = findViewById(ids[i]);
        }
        App.setViewOval(views);
      }
    }
  }

  /* Used for the view press effect and contains the click event of the view. */
  protected final void effectClick(View v, final ClickListener clickListener) {
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
  protected final ShortcutManager getShortCutManager() {
    return SDK_INT >= N_MR1 ? getSystemService(ShortcutManager.class) : null;
  }

  /* Android 8.0 or above can set the desktop icon menu. */
  protected final void setShortCuts(ShortcutInfo... shortcutInfo) {
    ShortcutManager shortcutManager = getShortCutManager();
    if (shortcutManager != null) {
      if (SDK_INT >= O && shortcutInfo != null) {
        for (ShortcutInfo info : shortcutInfo) {
          shortcutManager.addDynamicShortcuts(Arrays.asList(info));
        }
      }
    }
  }

  /**
   * @param color The value of the color.
   * @return "true" means the color is bright.
   */
  protected final boolean isLightColor(@ColorInt int color) {
    return ColorUtils.calculateLuminance(color) >= 0.5;
  }

  // In order to adaptively initialize the System Bar.
  private void initSysBar() {
    if (SDK_INT >= LOLLIPOP) {
      @ColorInt int statusBarColor = getWindow().getStatusBarColor();
      @ColorInt int navigationBarColor = getWindow().getNavigationBarColor();

      @ColorInt int oldApiBarColor = Color.valOf(222);

      if (isLightColor(statusBarColor) || statusBarColor == Color.TRANSPARENT) {
        if (SDK_INT >= M) {
          setDecorViewSystemUiVisibility(
              SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
          getWindow().setStatusBarColor(oldApiBarColor);
        }
      }
      if (isLightColor(navigationBarColor) || navigationBarColor == Color.TRANSPARENT) {
        if (SDK_INT >= N) {
          setDecorViewSystemUiVisibility(
              SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | SYSTEM_UI_FLAG_LAYOUT_STABLE);
        } else {
          getWindow().setNavigationBarColor(oldApiBarColor);
        }
      }
    }
    if (SDK_INT >= P) {
      getWindow().setNavigationBarDividerColor(Color.TRANSPARENT);
    }
  }

  private void initSysBarByAnnotation() {
    @ColorRes int statusVal = getClassAnnotatedValue(StatusBarColor.class, int.class);
    if (statusVal != -1 && SDK_INT >= LOLLIPOP) {
      getWindow().setStatusBarColor(getResColor(statusVal));
    }

    @ColorRes int navigationVal = getClassAnnotatedValue(NavigationBarColor.class, int.class);
    if (navigationVal != -1 && SDK_INT >= LOLLIPOP) {
      getWindow().setNavigationBarColor(getResColor(navigationVal));
    }
  }

  @Override
  public void onConfigurationChanged(@NonNull Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    onDecorViewConfig(decorView);
  }

  @Override
  protected void onResume() {
    super.onResume();
    onDecorViewConfig(decorView);
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      onDecorViewConfig(decorView);
    }
  }

  /* Used to obtain the screen pixel density. */
  protected final float getDisplayDensity() {
    return getResources().getDisplayMetrics().density;
  }

  /* Get the color from the resource file. */
  protected final @ColorInt int getResColor(@ColorRes int id) {
    return App.getResColor(this, id);
  }

  protected final <T> T getClassAnnotatedValue(
      Class<? extends Annotation> annotation, Class<T> classOfT) {
    return App.getClassAnnotatedValue(this, annotation, classOfT);
  }

  protected final @Dimension int getDimensionPixelSize(@DimenRes int id) {
    return getResources().getDimensionPixelSize(id);
  }

  protected final int getOrientation() {
    return getResources().getConfiguration().orientation;
  }

  protected final boolean orientationIsPortrait() {
    return getOrientation() == ORIENTATION_PORTRAIT;
  }

  protected final boolean orientationIsLandScape() {
    return getOrientation() == ORIENTATION_LANDSCAPE;
  }

  protected final @Dimension int getStatusBarHeight() {
    String name = "status_bar_height";
    return orientationIsPortrait()
        ? getDimensionPixelSize(getDimenIdFromAndroid(name))
        : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
  }

  protected final @Dimension int getNavigationBarHeight() {
    String name = "navigation_bar_height";
    return orientationIsPortrait()
        ? getDimensionPixelSize(getDimenIdFromAndroid(name))
        : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
  }

  protected final ConnectivityManager getConnectivityManager() {
    return ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE));
  }

  protected final NotificationManager getNotificationManager() {
    return ((NotificationManager) getSystemService(NOTIFICATION_SERVICE));
  }

  protected final InputMethodManager getInputMethodManager() {
    return ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
  }

  protected final TelephonyManager getTelephonyManager() {
    return ((TelephonyManager) getSystemService(TELEPHONY_SERVICE));
  }

  protected final WifiManager getWifiManager() {
    return ((WifiManager) ((Context) this).getSystemService(WIFI_SERVICE));
  }

  protected final int pxToDp(int px) {
    return (int) (px / getDisplayDensity() + 0.5f);
  }

  protected final int dpToPx(float dp) {
    return (int) (dp * getDisplayDensity() + 0.5f);
  }

  protected final Animation loadAnimation(@AnimRes int id) {
    return AnimationUtils.loadAnimation(this, id);
  }

  /** Check whether the input method is displayed. */
  protected final boolean isInputMethodShowing() {
    Rect rect = new Rect();
    decorView.getWindowVisibleDisplayFrame(rect);
    return decorView.getHeight() - rect.bottom > getNavigationBarHeight();
  }

  protected final int getIdentifier(String name, String defType, String defPackage) {
    return getResources().getIdentifier(name, defType, defPackage);
  }

  protected final @IdRes int getId(String name, String defPkgName) {
    return getResId(this, name, defPkgName);
  }

  protected final @IdRes int getId(String name) {
    return getId(name, getPackageName());
  }

  protected final @IdRes int getIdFromAndroid(String name) {
    return getId(name, PKG_ANDROID);
  }

  protected final @LayoutRes int getLayoutId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_LAYOUT, defPkgName);
  }

  protected final @LayoutRes int getLayoutId(String name) {
    return getLayoutId(name, getPackageName());
  }

  protected final @LayoutRes int getLayoutIdFromAndroid(String name) {
    return getLayoutId(name, PKG_ANDROID);
  }

  protected final @ArrayRes int getArrayId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_ARRAY, defPkgName);
  }

  protected final @ArrayRes int getArrayId(String name) {
    return getArrayId(name, getPackageName());
  }

  protected final @ArrayRes int getArrayIdFromAndroid(String name) {
    return getArrayId(name, PKG_ANDROID);
  }

  protected final @StringRes int getStringId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_STRING, defPkgName);
  }

  protected final @StringRes int getStringId(String name) {
    return getStringId(name, getPackageName());
  }

  protected final @StringRes int getStringIdFromAndroid(String name) {
    return getStringId(name, PKG_ANDROID);
  }

  protected final @DimenRes int getDimenId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_DIMEN, defPkgName);
  }

  protected final @DimenRes int getDimenId(String name) {
    return getDimenId(name, getPackageName());
  }

  protected final @DimenRes int getDimenIdFromAndroid(String name) {
    return getDimenId(name, PKG_ANDROID);
  }

  protected final @ColorRes int getColorId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_COLOR, defPkgName);
  }

  protected final @ColorRes int getColorId(String name) {
    return getColorId(name, getPackageName());
  }

  protected final @ColorRes int getColorIdFromAndroid(String name) {
    return getColorId(name, PKG_ANDROID);
  }

  protected final @DrawableRes int getDrawableId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_DRAWABLE, defPkgName);
  }

  protected final @DrawableRes int getDrawableId(String name) {
    return getDrawableId(name, getPackageName());
  }

  protected final @DrawableRes int getDrawableIdFromAndroid(String name) {
    return getDrawableId(name, PKG_ANDROID);
  }

  protected final @AnimRes int getAnimationId(String name, String defPkgName) {
    return getAnimId(this, name, defPkgName);
  }

  protected final @AnimRes int getAnimationId(String name) {
    return getAnimationId(name, getPackageName());
  }

  protected final @AnimRes int getAnimationIdFromAndroid(String name) {
    return getAnimationId(name, PKG_ANDROID);
  }

  protected final @XmlRes int getXmlId(String name, String defPkgName) {
    return getIdentifier(name, IDENTIFIER_XML, defPkgName);
  }

  protected final @XmlRes int getXmlId(String name) {
    return getXmlId(name, getPackageName());
  }

  protected final @XmlRes int getXmlIdFromAndroid(String name) {
    return getXmlId(name, PKG_ANDROID);
  }

  protected final String getImgPathFromURI(Uri uri) {
    String result;
    Cursor cursor = null;

    try {
      cursor = getContentResolver().query(uri, null, null, null, null);
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

  protected final Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
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
  protected final void chkAndApplyPermissions(int reqCode, String... permissions) {
    App.chkAndApplyPermissions(this, reqCode, permissions);
  }

  /* Use certain rules to parse the application version number. */
  private long parseVersion(String versionName) {
    versionName = versionName.replace("_", "");
    versionName = versionName.replace(".", "");
    versionName = versionName.replace("-", "");
    return Long.parseLong(versionName);
  }

  /**
   * Check if the app has a new version update?
   *
   * @param versionName It is usually the version number obtained from the server.
   * @return "true" indicates that there is a new version of the application and it can be updated.
   */
  protected final boolean appHasNewVersion(String versionName) {
    try {
      PackageInfo pkgInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
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

  protected final void verboseLog(Object msg) {
    App.verboseLog(this, msg);
  }

  protected final void verboseLog(Object msg, Throwable t) {
    App.verboseLog(this, msg, t);
  }

  protected final void debugLog(Object msg) {
    App.debugLog(this, msg);
  }

  protected final void debugLog(Object msg, Throwable t) {
    App.debugLog(this, msg, t);
  }

  protected final void infoLog(Object msg) {
    App.infoLog(this, msg);
  }

  protected final void infoLog(Object msg, Throwable t) {
    App.infoLog(this, msg, t);
  }

  protected final void warnLog(Object msg) {
    App.warnLog(this, msg);
  }

  protected final void warnLog(Object msg, Throwable t) {
    App.warnLog(this, msg, t);
  }

  protected final void errorLog(Object msg) {
    App.errorLog(this, msg);
  }

  protected final void errorLog(Object msg, Throwable t) {
    App.errorLog(this, msg, t);
  }

  protected final void toastShort(Object msg) {
    Toast.showShort(this, msg);
  }

  protected final void toastShort(@StringRes int id) {
    Toast.showShort(this, id);
  }

  protected final void toastLong(Object msg) {
    Toast.showLong(this, msg);
  }

  protected final void toastLong(@StringRes int id) {
    Toast.showLong(this, id);
  }
}
