package com.meyoustu.amuse;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.LayoutRes;

import com.meyoustu.amuse.annotation.ContentView;
import com.meyoustu.amuse.listen.ClickListener;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static com.meyoustu.amuse.listen.ClickListener.RESP_TIME_MILLLIS;
import static java.lang.System.currentTimeMillis;

/**
 * Created at 2020/6/17 17:28.
 *
 * @author Liangcheng Juves
 */
abstract class ActivityProvider implements StandardProvider {

  private Activity activity;

  ActivityProvider(Activity activity) {
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
  public void setViewRadius(int radius, int... ids) {
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
  public void setViewOval(int... ids) {
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
  public void effectClick(View v, ClickListener clickListener) {
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

  @Override
  public ShortcutManager getShortCutManager() {
    return SDK_INT >= N_MR1 ? activity.getSystemService(ShortcutManager.class) : null;
  }

  @Override
  public void setShortCuts(ShortcutInfo... shortcutInfo) {
    ShortcutManager shortcutManager = getShortCutManager();
    if (shortcutManager != null) {
      if (SDK_INT >= O && shortcutInfo != null) {
        for (ShortcutInfo info : shortcutInfo) {
          shortcutManager.addDynamicShortcuts(Arrays.asList(info));
        }
      }
    }
  }

  @Override
  public float getDisplayDensity() {
    return 0;
  }

  @Override
  public int getResColor(int id) {
    return 0;
  }

  @Override
  public <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation, Class<T> classOfT) {
    return null;
  }

  @Override
  public int getDimensionPixelSize(int id) {
    return 0;
  }

  @Override
  public int getOrientation() {
    return 0;
  }

  @Override
  public boolean orientationIsPortrait() {
    return false;
  }

  @Override
  public boolean orientationIsLandScape() {
    return false;
  }

  @Override
  public int getStatusBarHeight() {
    return 0;
  }

  @Override
  public int getNavigationBarHeight() {
    return 0;
  }

  @Override
  public ConnectivityManager getConnectivityManager() {
    return null;
  }

  @Override
  public NotificationManager getNotificationManager() {
    return null;
  }

  @Override
  public InputMethodManager getInputMethodManager() {
    return null;
  }

  @Override
  public TelephonyManager getTelephonyManager() {
    return null;
  }

  @Override
  public WifiManager getWifiManager() {
    return null;
  }

  @Override
  public int pxToDp(int px) {
    return 0;
  }

  @Override
  public int dpToPx(float dp) {
    return 0;
  }

  @Override
  public Animation loadAnimation(int id) {
    return null;
  }

  @Override
  public boolean isInputMethodShowing() {
    return false;
  }

  @Override
  public int getIdentifier(String name, String defType, String defPackage) {
    return 0;
  }

  @Override
  public int getId(String name, String defPkgName) {
    return 0;
  }

  @Override
  public int getId(String name) {
    return 0;
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
