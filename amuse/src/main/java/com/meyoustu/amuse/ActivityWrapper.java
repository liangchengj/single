package com.meyoustu.amuse;

import android.app.Activity;
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
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.core.app.NotificationCompat;

import com.meyoustu.amuse.annotation.ContentView;
import com.meyoustu.amuse.annotation.DecorViewConfig;
import com.meyoustu.amuse.annotation.Native;
import com.meyoustu.amuse.annotation.sysbar.NavigationBarColor;
import com.meyoustu.amuse.annotation.sysbar.StatusBarColor;
import com.meyoustu.amuse.annotation.sysbar.WindowFullScreen;
import com.meyoustu.amuse.graphics.Color;
import com.meyoustu.amuse.listen.ClickListener;
import com.meyoustu.amuse.listen.EffectClickListener;
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
import static com.meyoustu.amuse.annotation.DecorViewConfig.HIDE_SYS_BARS;
import static com.meyoustu.amuse.graphics.Color.isLightColor;

/**
 * Created at 2020/6/17 17:28.
 *
 * @author Liangcheng Juves
 */
abstract class ActivityWrapper {

    private Activity activity;
    private Window window;
    View decorView;

    private ActivityWrapper(Activity activity) {
        this.activity = activity;
    }

    static ActivityWrapper newInstance(Activity activity) {
        return new ActivityWrapper(activity) {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }

    /**
     * Load the dynamic link library by detecting whether it is annotated with "@Native".
     */
    void loadJNILibByAnnotation() {
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

    // In order to adaptively initialize the System Bar.
    private void initSysBar() {
        if (!isDarkTheme()) {
            if (SDK_INT >= LOLLIPOP) {
                @ColorInt int statusBarColor = window.getStatusBarColor();
                @ColorInt int navigationBarColor = window.getNavigationBarColor();

                @ColorInt int oldApiBarColor = Color.initWith(222);

                if (isLightColor(statusBarColor) || statusBarColor == Color.TRANSPARENT) {
                    if (SDK_INT >= M) {
                        setDecorViewSystemUiVisibility(
                                SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    } else {
                        window.setStatusBarColor(oldApiBarColor);
                    }
                }
                if (isLightColor(navigationBarColor) || navigationBarColor == Color.TRANSPARENT) {
                    if (SDK_INT >= N) {
                        setDecorViewSystemUiVisibility(
                                SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR | SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    } else {
                        window.setNavigationBarColor(oldApiBarColor);
                    }
                }
            }
        }
        if (SDK_INT >= P) {
            window.setNavigationBarDividerColor(Color.TRANSPARENT);
        }
    }

    private void initSysBarByAnnotation() {
        if (!isDarkTheme()) {
            @ColorRes int statusVal = getClassAnnotatedValue(StatusBarColor.class, int.class);
            if (statusVal != -1 && SDK_INT >= LOLLIPOP) {
                window.setStatusBarColor(getResColor(statusVal));
            }

            @ColorRes int navigationVal = getClassAnnotatedValue(NavigationBarColor.class, int.class);
            if (navigationVal != -1 && SDK_INT >= LOLLIPOP) {
                window.setNavigationBarColor(getResColor(navigationVal));
            }
        }
    }

    /* Use certain rules to parse the application version number. */
    private long parseVersion(String versionName) {
        versionName = versionName.replace("_", "");
        versionName = versionName.replace(".", "");
        versionName = versionName.replace("-", "");
        return Long.parseLong(versionName);
    }

    @LayoutRes
    int initView() {
        @LayoutRes int contentViewVal = getClassAnnotatedValue(ContentView.class, int.class);
        if (contentViewVal != -1) {
            activity.setContentView(contentViewVal);
        }
        return -1;
    }

    void onDecorViewConfig(View decorView
            /* The current class is not used and is provided to "activity"'s subclasses. */) {
        window = activity.getWindow();
        this.decorView = window.getDecorView();
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

    void setDecorViewRadius(int radius) {
        App.setViewRadius(radius, decorView);
    }

    /* Set the Activity window to full screen display. */
    void setWindowFullScreen() {
        if (SDK_INT <= KITKAT) {
            activity
                    .getWindow()
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

    /**
     * Determine whether the system opens the dark theme.
     */
    boolean isDarkTheme() {
        return (activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }

    void setViewRadius(int radius, @IdRes int... ids) {
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

    void setViewOval(@IdRes int... ids) {
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

    /**
     * Used for the view press effect and contains the click event of the view.
     */
    void effectClick(View v, final ClickListener clickListener) {
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

    /**
     * @return ShortcutManager -> Used to manage desktop shortcuts.
     */
    ShortcutManager getShortCutManager() {
        return SDK_INT >= N_MR1 ? activity.getSystemService(ShortcutManager.class) : null;
    }

    /**
     * Android 8.0 or above can set the desktop icon menu.
     */
    void setShortCuts(ShortcutInfo... shortcutInfo) {
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

    /**
     * Used to obtain the screen pixel density.
     */
    float getDisplayDensity() {
        return activity.getResources().getDisplayMetrics().density;
    }

    /**
     * Get the color from the resource file.
     */
    @ColorInt
    int getResColor(@ColorRes int id) {
        return App.getResColor(activity, id);
    }

    <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation, Class<T> classOfT) {
        return App.getClassAnnotatedValue(activity, annotation, classOfT);
    }

    @Dimension
    int getDimensionPixelSize(@DimenRes int id) {
        return activity.getResources().getDimensionPixelSize(id);
    }

    int getOrientation() {
        return activity.getResources().getConfiguration().orientation;
    }

    boolean orientationIsPortrait() {
        return getOrientation() == ORIENTATION_PORTRAIT;
    }

    boolean orientationIsLandScape() {
        return getOrientation() == ORIENTATION_LANDSCAPE;
    }

    @Dimension
    int getStatusBarHeight() {
        String name = "status_bar_height";
        return orientationIsPortrait()
                ? getDimensionPixelSize(getDimenIdFromAndroid(name))
                : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
    }

    @Dimension
    int getNavigationBarHeight() {
        String name = "navigation_bar_height";
        return orientationIsPortrait()
                ? getDimensionPixelSize(getDimenIdFromAndroid(name))
                : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
    }

    ConnectivityManager getConnectivityManager() {
        return ((ConnectivityManager) activity.getSystemService(CONNECTIVITY_SERVICE));
    }

    NotificationManager getNotificationManager() {
        return ((NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE));
    }

    InputMethodManager getInputMethodManager() {
        return ((InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE));
    }

    TelephonyManager getTelephonyManager() {
        return ((TelephonyManager) activity.getSystemService(TELEPHONY_SERVICE));
    }

    WifiManager getWifiManager() {
        return ((WifiManager) ((Context) activity).getSystemService(WIFI_SERVICE));
    }

    @Dimension
    int pxToDp(int px) {
        return (int) (px / getDisplayDensity() + 0.5f);
    }

    int dpToPx(@Dimension float dp) {
        return (int) (dp * getDisplayDensity() + 0.5f);
    }

    Animation loadAnimation(@AnimRes int id) {
        return AnimationUtils.loadAnimation(activity, id);
    }

    /**
     * Check whether the input method is displayed.
     */
    boolean isInputMethodShowing() {
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return decorView.getHeight() - rect.bottom > getNavigationBarHeight();
    }

    int getIdentifier(String name, String defType, String defPackage) {
        return activity.getResources().getIdentifier(name, defType, defPackage);
    }

    @IdRes
    int getId(String name, String defPkgName) {
        return getResId(activity, name, defPkgName);
    }

    @IdRes
    int getId(String name) {
        return getId(name, activity.getPackageName());
    }

    @IdRes
    int getIdFromAndroid(String name) {
        return getId(name, PKG_ANDROID);
    }

    @LayoutRes
    int getLayoutId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_LAYOUT, defPkgName);
    }

    @LayoutRes
    int getLayoutId(String name) {
        return getLayoutId(name, activity.getPackageName());
    }

    @LayoutRes
    int getLayoutIdFromAndroid(String name) {
        return getLayoutId(name, PKG_ANDROID);
    }

    @ArrayRes
    int getArrayId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_ARRAY, defPkgName);
    }

    @ArrayRes
    int getArrayId(String name) {
        return getArrayId(name, activity.getPackageName());
    }

    @ArrayRes
    int getArrayIdFromAndroid(String name) {
        return getArrayId(name, PKG_ANDROID);
    }

    @StringRes
    int getStringId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_STRING, defPkgName);
    }

    @StringRes
    int getStringId(String name) {
        return getStringId(name, activity.getPackageName());
    }

    @StringRes
    int getStringIdFromAndroid(String name) {
        return getStringId(name, PKG_ANDROID);
    }

    @DimenRes
    int getDimenId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_DIMEN, defPkgName);
    }

    @DimenRes
    int getDimenId(String name) {
        return getDimenId(name, activity.getPackageName());
    }

    @DimenRes
    int getDimenIdFromAndroid(String name) {
        return getDimenId(name, PKG_ANDROID);
    }

    @ColorRes
    int getColorId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_COLOR, defPkgName);
    }

    @ColorRes
    int getColorId(String name) {
        return getColorId(name, activity.getPackageName());
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
     * @param reqCode     Request code for permission.
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
