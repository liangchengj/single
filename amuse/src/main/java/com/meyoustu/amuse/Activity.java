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
import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorUtils;

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
import static android.provider.SyncStateContract.Columns.DATA;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
import static com.meyoustu.amuse.annotation.DecorViewConfig.HIDE_SYS_BARS;
import static com.meyoustu.amuse.listen.ClickListener.RESP_TIME_MILLLIS;


/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 14:37
 */
public abstract class Activity extends AppCompatActivity {

    public static final String IDENTIFIER_DRAWABLE = "drawable";
    public static final String IDENTIFIER_ID = "id";
    public static final String IDENTIFIER_STRING = "string";
    public static final String IDENTIFIER_ARRAY = "array";
    public static final String IDENTIFIER_DIMEN = "dimen";
    public static final String IDENTIFIER_COLOR = "color";
    public static final String IDENTIFIER_LAYOUT = "layout";

    /* Construction method.
    Load the dynamic link library by detecting whether it is annotated with "@Native". */
    protected Activity() {
        String nativeLibName = getClassAnnotatedValue(Native.class, String.class);
        if (!nativeLibName.isEmpty()) {
            System.loadLibrary(nativeLibName);
        }
    }


    private int decorViewSystemUiVisibility;

    private void setDecorViewSystemUiVisibility(int visibility) {
        decorViewSystemUiVisibility |= visibility;
        decorView.setSystemUiVisibility(decorViewSystemUiVisibility);
    }

    @LayoutRes
    private int layoutId = -1;
    private View decorView;

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
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
            decorView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setWindowFullScreen();
                }
            }, 1500);
        }
        decorView.setSystemUiVisibility(HIDE_SYS_BARS);
    }


    protected void onDecorViewConfig(View decorView
            /*The current class is not used and is provided to subclasses.*/) {
        setDecorViewSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE);
        int decorViewConfigVal = getClassAnnotatedValue(DecorViewConfig.class, int.class);
        if (decorViewConfigVal != -1) {
            setDecorViewSystemUiVisibility(decorViewConfigVal);
        } else {
            try {
                initSysBarByAnnotation();
            } catch (IllegalAccessException e) {
                // Ignore
            }
            // In order to adaptively initialize the System Bar.
            initSysBar();
            if (getClass().isAnnotationPresent(WindowFullScreen.class)) {
                setWindowFullScreen();
            }
        }
    }

    protected @LayoutRes
    int initView() {
        @LayoutRes
        int contentViewVal = getClassAnnotatedValue(ContentView.class, int.class);
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
            @LayoutRes
            int layoutId = initView();
            if (layoutId != -1) {
                setContentView(layoutId);
            }
        }

        try {
            initResMemberByAnnotation();
        } catch (IllegalAccessException e) {
            // Ignore
        }
    }


    protected final void setDecorViewRadius() {
    }

    /* Used for the view press effect and contains the click event of the view. */
    protected final void effectClick(View v, final ClickListener clickListener) {
        v.setOnTouchListener(new View.OnTouchListener() {
            private long touchDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickListener.onTouchDown(v, event);
                    touchDown = System.currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    clickListener.onTouchUp(v, event);
                    if (System.currentTimeMillis() - touchDown <= RESP_TIME_MILLLIS) {
                        clickListener.onClick(v);
                    }
                }
                return false;
            }
        });
    }

    /**
     * @return ShortcutManager -> Used to manage desktop shortcuts.
     */
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

    /* Initialize the "Field" by checking whether it is annotated. */
    private void initResMemberByAnnotation()
            throws IllegalAccessException {
        App.initResMemberByAnnotation(this);
    }

    /**
     * @param color The value of the color.
     * @return "true" means the color is bright.
     */
    protected final boolean isLightColor(int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }


    // In order to adaptively initialize the System Bar.
    private void initSysBar() {
        if (SDK_INT >= LOLLIPOP) {
            int statusBarColor = getWindow().getStatusBarColor();
            int navigationBarColor = getWindow().getNavigationBarColor();

            int oldApiBarColor = Color.valOf(222);

            if (isLightColor(statusBarColor) || statusBarColor == Color.TRANSPARENT) {
                if (SDK_INT >= M) {
                    setDecorViewSystemUiVisibility(
                            SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                                    SYSTEM_UI_FLAG_LAYOUT_STABLE);
                } else {
                    getWindow().setStatusBarColor(oldApiBarColor);
                }
            }
            if (isLightColor(navigationBarColor) ||
                    navigationBarColor == Color.TRANSPARENT) {
                if (SDK_INT >= N) {
                    setDecorViewSystemUiVisibility(
                            SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR |
                                    SYSTEM_UI_FLAG_LAYOUT_STABLE);
                } else {
                    getWindow().setNavigationBarColor(oldApiBarColor);
                }
            }
        }
    }

    private void initSysBarByAnnotation()
            throws IllegalAccessException {
        @ColorRes
        int statusVal = getClassAnnotatedValue(StatusBarColor.class, int.class);
        if (statusVal != -1 && SDK_INT >= LOLLIPOP) {
            getWindow().setStatusBarColor(getResColor(statusVal));
        }

        @ColorRes
        int navigationVal = getClassAnnotatedValue(NavigationBarColor.class, int.class);
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

    /* Get the context of the current Activity. */
    protected final Context getContext() {
        return Activity.this;
    }

    /* Used to obtain the screen pixel density. */
    protected final float getDisplayDensity() {
        return getResources().getDisplayMetrics().density;
    }

    /* Get the color from the resource file. */
    protected final int getResColor(@ColorRes int id) {
        if (SDK_INT >= M) {
            return getResources().getColor(id, getTheme());
        } else {
            // In order to adapt to the old API.
            return getResources().getColor(id);
        }
    }


    protected final <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation,
                                                 Class<T> classOfT) {
        return App.getClassAnnotatedValue(this, annotation, classOfT);
    }


    protected final int getDimensionPixelSize(int id) {
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

    protected final int getStatusBarHeight() {
        return orientationIsPortrait() ?
                getDimensionPixelSize(getDimenId("status_bar_height", "android")) :
                getDimensionPixelSize(getDimenId("status_bar_height_landscape", "android"));
    }

    protected final int getNavigationBarHeight() {
        return orientationIsPortrait() ?
                getDimensionPixelSize(getDimenId("navigation_bar_height", "android")) :
                getDimensionPixelSize(getDimenId("navigation_bar_height_landscape", "android"));
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

    /**
     * Check whether the input method is displayed.
     */
    protected final boolean isInputMethodShowing() {
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return decorView.getHeight() - rect.bottom > getNavigationBarHeight();
    }

    protected final int getIdentifier(String name, String defType, String defPackage) {
        return getResources().getIdentifier(name, defType, defPackage);
    }

    protected final int getId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_ID, defPkgName);
    }

    protected final int getId(String name) {
        return getId(name, getPackageName());
    }

    protected final int getLayoutId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_LAYOUT, defPkgName);
    }

    protected final int getLayoutId(String name) {
        return getLayoutId(name, getPackageName());
    }

    protected final int getArrayId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_ARRAY, defPkgName);
    }

    protected final int getArrayId(String name) {
        return getArrayId(name, getPackageName());
    }

    protected final int getStringId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_STRING, defPkgName);
    }

    protected final int getStringId(String name) {
        return getStringId(name, getPackageName());
    }

    protected final int getDimenId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_DIMEN, defPkgName);
    }

    protected final int getDimenId(String name) {
        return getDimenId(name, getPackageName());
    }


    protected final int getColorId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_COLOR, defPkgName);
    }

    protected final int getColorId(String name) {
        return getColorId(name, getPackageName());
    }


    protected final int getDrawableId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_DRAWABLE, defPkgName);
    }

    protected final int getDrawableId(String name) {
        return getDrawableId(name, getPackageName());
    }


    protected final String getImgPathFromURI(Uri uri) {
        String result;
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(uri, null, null,
                    null, null);
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

        Bitmap flag = createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        if (!flag.isRecycled() && degrees != 0) {
            flag.recycle();
        }
        return flag;
    }


    /**
     * Used to check whether a certain permission has been applied for and the user agrees to authorize,
     * if not satisfied, reapply.
     *
     * @param permissions java.lang.String[] -> The name used to store one or more permissions.
     * @param reqCode     Request code for permission.
     */
    protected final void chkAndApplyPermissions(@NonNull String[] permissions, int reqCode) {
        App.chkAndApplyPermissions(this, permissions, reqCode);
    }

    /* Use certain rules to parse the application version number. */
    private long parseVersion(String versionName) {
        return Long.parseLong(versionName.replace(".", ""));
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

    protected final void toastShort(@IdRes int id) {
        Toast.showShort(this, id);
    }

    protected final void toastLong(Object msg) {
        Toast.showLong(this, msg);
    }

    protected final void toastLong(@IdRes int id) {
        Toast.showLong(this, id);
    }

}
