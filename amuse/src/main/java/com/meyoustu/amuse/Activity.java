package com.meyoustu.amuse;

import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.meyoustu.amuse.annotation.ContentView;
import com.meyoustu.amuse.annotation.Native;
import com.meyoustu.amuse.annotation.res.AAnimation;
import com.meyoustu.amuse.annotation.res.AColor;
import com.meyoustu.amuse.annotation.res.ADimen;
import com.meyoustu.amuse.annotation.res.ADrawable;
import com.meyoustu.amuse.annotation.res.AString;
import com.meyoustu.amuse.annotation.res.AStringArray;
import com.meyoustu.amuse.annotation.res.AView;
import com.meyoustu.amuse.annotation.res.AXml;
import com.meyoustu.amuse.annotation.sysbar.NavigationBarColor;
import com.meyoustu.amuse.annotation.sysbar.StatusBarColor;
import com.meyoustu.amuse.annotation.sysbar.WindowFullScreen;
import com.meyoustu.amuse.listen.ClickListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static android.graphics.Bitmap.createBitmap;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N_MR1;
import static android.os.Build.VERSION_CODES.O;
import static android.provider.SyncStateContract.Columns.DATA;
import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static com.meyoustu.amuse.App.getFieldAnnotatedValue;


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

    protected Activity() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String nativeLibName = getClassAnnotatedValue(Native.class, String.class);
            if (!nativeLibName.isEmpty()) {
                System.loadLibrary(nativeLibName);
            }
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


    protected final int getGrayColor(int grayVal) {
        return Color.rgb(grayVal, grayVal, grayVal);
    }


    protected final void setWindowFullScreen() {
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_FULLSCREEN |
                SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onDecorViewConfig(View v) {
        setDecorViewSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_STABLE);
        try {
            initSysBarByAnnotation();
        } catch (IllegalAccessException e) {
            // Ignore
        }
        int statusBarColor = getWindow().getStatusBarColor();
        int navigationBarColor = getWindow().getNavigationBarColor();

        int oldApiBarColor = getGrayColor(222);

        if (isLightColor(statusBarColor) || statusBarColor == Color.TRANSPARENT) {
            if (SDK_INT >= Build.VERSION_CODES.M) {
                setDecorViewSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
                        SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                getWindow().setStatusBarColor(oldApiBarColor);
            }
        }
        if (isLightColor(navigationBarColor) || navigationBarColor == Color.TRANSPARENT) {
            if (SDK_INT > Build.VERSION_CODES.N) {
                setDecorViewSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR |
                        SYSTEM_UI_FLAG_LAYOUT_STABLE);
            } else {
                getWindow().setNavigationBarColor(oldApiBarColor);
            }
        }
        if (getClass().isAnnotationPresent(WindowFullScreen.class)) {
            setWindowFullScreen();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected @LayoutRes
    int initView() {
        @LayoutRes
        int contentViewVal = getClassAnnotatedValue(ContentView.class, int.class);
        if (contentViewVal != -1) {
            setContentView(contentViewVal);
        }
        return -1;
    }


    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        decorView = getWindow().getDecorView();
//        onDecorViewConfig(decorView);
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
            initMemberByAnnotation();
        } catch (IllegalAccessException e) {
            // Ignore
        }
    }


    protected final void setDecorViewRadius() {
    }


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
                    if (System.currentTimeMillis() - touchDown <= 500) {
                        clickListener.onClick(v);
                    }
                }
                return false;
            }
        });
    }


    protected final ShortcutManager getShortCutManager() {
        return SDK_INT >= N_MR1 ? getSystemService(ShortcutManager.class) : null;
    }

    protected final void setShortCuts(ShortcutInfo... shortcutInfo) {
        ShortcutManager shortcutManager = getShortCutManager();
        if (shortcutManager != null) {
            if (SDK_INT > O && shortcutInfo != null) {
                for (ShortcutInfo info : shortcutInfo) {
                    shortcutManager.addDynamicShortcuts(Arrays.asList(info));
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initMemberByAnnotation()
            throws IllegalAccessException {
        for (Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);

            @IdRes
            int aViewVal = getFieldAnnotatedValue(field, AView.class, int.class);
            if (aViewVal != -1) {
                field.set(this, findViewById(aViewVal));
            }

            @StringRes
            int aStringVal = getFieldAnnotatedValue(field, AString.class, int.class);
            if (aStringVal != -1) {
                field.set(this, getString(aStringVal));
            }

            @ArrayRes
            int aStringArrayVal = getFieldAnnotatedValue(field, AStringArray.class, int.class);
            if (aStringArrayVal != -1) {
                field.set(this, getResources().getStringArray(aStringArrayVal));
            }

            @AnimRes
            int aAnimationVal = getFieldAnnotatedValue(field, AAnimation.class, int.class);
            if (aAnimationVal != -1) {
                field.set(this, getResources().getAnimation(aAnimationVal));
            }

            @DrawableRes
            int aDrawableVal = getFieldAnnotatedValue(field, ADrawable.class, int.class);
            if (aDrawableVal != -1) {
                if (SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    field.set(this, getDrawable(aDrawableVal));
                }
            }

            @ColorRes
            int aColorVal = getFieldAnnotatedValue(field, AColor.class, int.class);
            if (aColorVal != -1) {
                field.set(this, getResColor(aColorVal));
            }

            @DimenRes
            int aDimenVal = getFieldAnnotatedValue(field, ADimen.class, int.class);
            if (aDimenVal != -1) {
                field.set(this, getResources().getDimension(aDimenVal));
            }

            @XmlRes
            int aXmlVal = getFieldAnnotatedValue(field, AXml.class, int.class);
            if (aXmlVal != -1) {
                field.set(this, getResources().getXml(aXmlVal));
            }
        }
    }


    protected final boolean isLightColor(int color) {
        return ColorUtils.calculateLuminance(color) >= 0.5;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initSysBarByAnnotation()
            throws IllegalAccessException {

        @ColorRes
        int statusVal = getClassAnnotatedValue(StatusBarColor.class, int.class);
        if (statusVal != -1) {
            getWindow().setStatusBarColor(getResColor(statusVal));
        }

        @ColorRes
        int navigationVal = getClassAnnotatedValue(NavigationBarColor.class, int.class);
        if (navigationVal != -1) {
            getWindow().setNavigationBarColor(getResColor(navigationVal));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onDecorViewConfig(decorView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
//        onDecorViewConfig(decorView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
//            onDecorViewConfig(decorView);
        }
    }

    protected final Context getContext() {
        return Activity.this;
    }

    protected final float getDisplayDensity() {
        return getResources().getDisplayMetrics().density;
    }

    protected final int getResColor(@ColorRes int id) {
        if (SDK_INT >= Build.VERSION_CODES.M) {
            return getResources().getColor(id, getTheme());
        } else {
            // In order to adapt to the old API.
            return getResources().getColor(id);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected final <T> T getClassAnnotatedValue(Class<? extends Annotation> annotation,
                                                 Class<T> classOfT) {
        return App.getClassAnnotatedValue(this, annotation, classOfT);
    }


    protected final int getDimenToPx(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    protected final int getOrientation() {
        return getResources().getConfiguration().orientation;
    }

    protected final boolean isPortrait() {
        return getOrientation() == ORIENTATION_PORTRAIT;
    }

    protected final boolean isLandScape() {
        return getOrientation() == ORIENTATION_LANDSCAPE;
    }

    protected final int getStatusBarHeight() {
        return isPortrait() ?
                getDimenToPx(getDimenId("status_bar_height", "android")) :
                getDimenToPx(getDimenId("status_bar_height_landscape", "android"));
    }

    protected final int getNavigationBarHeight() {
        return isPortrait() ?
                getDimenToPx(getDimenId("navigation_bar_height", "android")) :
                getDimenToPx(getDimenId("navigation_bar_height_landscape", "android"));
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
        return ((WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE));
    }

    protected final int pxToDp(int px) {
        return (int) (px / getDisplayDensity() + 0.5f);
    }

    protected final int dpToPx(float dp) {
        return (int) (dp * getDisplayDensity() + 0.5f);
    }


    protected final boolean hasPermission(String permission) {
        return ActivityCompat.checkSelfPermission(getContext(), permission) ==
                PERMISSION_GRANTED;
    }

    protected final void chkAndApplyPermissions(@NonNull android.app.Activity activity,
                                                @NonNull String[] permissions,
                                                int reqCode) {
        String append = "";
        for (short i = 0; i < permissions.length; i++) {
            String permission = permissions[0];
            if (!hasPermission(permission) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
            ) {
                append += i + ",";
            }
        }
        if (!append.isEmpty()) {
            append = append.substring(0, append.length() - 1);
            String[] indexIds = append.split(",");
            String[] shouldApplyPermissions = new String[indexIds.length];
            for (short i = 0; i < shouldApplyPermissions.length; i++) {
                shouldApplyPermissions[i] = permissions[Integer.parseInt(indexIds[i])];
            }
            ActivityCompat.requestPermissions(activity, shouldApplyPermissions, reqCode);
        }
    }


    protected final Animation loadAnimation(@AnimRes int id) {
        return AnimationUtils.loadAnimation(this, id);
    }

    protected final boolean isInputMethodShowing() {

        Rect rect = new Rect();

        getDecorView().getWindowVisibleDisplayFrame(rect);

        return getDecorView().getHeight() - rect.bottom > getNavigationBarHeight();
    }

    protected final int getDimenId(String name, String defPackage) {
        return getResources().getIdentifier(name, IDENTIFIER_DIMEN, defPackage);
    }

    protected final int getDimenId(String name) {
        return getDimenId(name, getPackageName());
    }

    @RequiresPermission(READ_EXTERNAL_STORAGE)
    protected final String getImgPathFromURI(Uri uri) {
        String result;
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(uri, null, null,
                    null, null);
        } catch (Throwable t) {
            App.errorLog(getApplicationContext(), "getImgPathFromURI", t);
        }

        if (cursor == null) {
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
                bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);

        if (!flag.isRecycled() && degrees != 0) {
            flag.recycle();
        }

        return flag;

    }


    protected final void verboseLog(Object msg) {
        App.verboseLog(this, msg);
    }

    protected final void debugLog(Object msg) {
        App.debugLog(this, msg);
    }

    protected final void infoLog(Object msg) {
        App.infoLog(this, msg);
    }

    protected final void warnLog(Object msg) {
        App.warnLog(this, msg);
    }

    protected final void errorLog(Object msg) {
        App.errorLog(this, msg);
    }

}
