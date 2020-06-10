package com.meyoustu.amuse;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;

import com.meyoustu.amuse.util.Toast;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
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

/**
 * @author Liangcheng Juves
 * Created at 2020/6/7 12:47
 * <p>
 * Note: Classes that inherit android.app.Activity cannot use AndroidX related libraries.
 */
public abstract class StandardActivity extends android.app.Activity {

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
        } catch (PackageManager.NameNotFoundException
                | NumberFormatException e) {
            errorLog("appHasNewVersion", e);
            return false;
        }
    }


    /**
     * Used to check whether a certain permission has been applied for and the user agrees to authorize,
     * if not satisfied, reapply.
     *
     * @param reqCode     Request code for permission.
     * @param permissions java.lang.String[] -> The name used to store one or more permissions.
     */
    protected final void chkAndApplyPermissions(int reqCode, String... permissions) {
        App.chkAndApplyPermissions(this, reqCode, permissions);
    }


    protected final int getIdentifier(String name, String defType, String defPackage) {
        return getResources().getIdentifier(name, defType, defPackage);
    }

    protected final @IdRes
    int getId(String name, String defPkgName) {
        return getResId(this, name, defPkgName);
    }

    protected final @IdRes
    int getId(String name) {
        return getId(name, getPackageName());
    }

    protected final @IdRes
    int getIdFromAndroid(String name) {
        return getId(name, PKG_ANDROID);
    }

    protected final @LayoutRes
    int getLayoutId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_LAYOUT, defPkgName);
    }

    protected final @LayoutRes
    int getLayoutId(String name) {
        return getLayoutId(name, getPackageName());
    }

    protected final @LayoutRes
    int getLayoutIdFromAndroid(String name) {
        return getLayoutId(name, PKG_ANDROID);
    }

    protected final @ArrayRes
    int getArrayId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_ARRAY, defPkgName);
    }

    protected final @ArrayRes
    int getArrayId(String name) {
        return getArrayId(name, getPackageName());
    }

    protected final @ArrayRes
    int getArrayIdFromAndroid(String name) {
        return getArrayId(name, PKG_ANDROID);
    }

    protected final @StringRes
    int getStringId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_STRING, defPkgName);
    }

    protected final @StringRes
    int getStringId(String name) {
        return getStringId(name, getPackageName());
    }

    protected final @StringRes
    int getStringIdFromAndroid(String name) {
        return getStringId(name, PKG_ANDROID);
    }

    protected final @DimenRes
    int getDimenId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_DIMEN, defPkgName);
    }

    protected final @DimenRes
    int getDimenId(String name) {
        return getDimenId(name, getPackageName());
    }

    protected final @DimenRes
    int getDimenIdFromAndroid(String name) {
        return getDimenId(name, PKG_ANDROID);
    }


    protected final @ColorRes
    int getColorId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_COLOR, defPkgName);
    }

    protected final @ColorRes
    int getColorId(String name) {
        return getColorId(name, getPackageName());
    }

    protected final @ColorRes
    int getColorIdFromAndroid(String name) {
        return getColorId(name, PKG_ANDROID);
    }

    protected final @DrawableRes
    int getDrawableId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_DRAWABLE, defPkgName);
    }

    protected final @DrawableRes
    int getDrawableId(String name) {
        return getDrawableId(name, getPackageName());
    }

    protected final @DrawableRes
    int getDrawableIdFromAndroid(String name) {
        return getDrawableId(name, PKG_ANDROID);
    }


    protected final @AnimRes
    int getAnimationId(String name, String defPkgName) {
        return getAnimId(this, name, defPkgName);
    }

    protected final @AnimRes
    int getAnimationId(String name) {
        return getAnimationId(name, getPackageName());
    }

    protected final @AnimRes
    int getAnimationIdFromAndroid(String name) {
        return getAnimationId(name, PKG_ANDROID);
    }

    protected final @XmlRes
    int getXmlId(String name, String defPkgName) {
        return getIdentifier(name, IDENTIFIER_XML, defPkgName);
    }

    protected final @XmlRes
    int getXmlId(String name) {
        return getXmlId(name, getPackageName());
    }

    protected final @XmlRes
    int getXmlIdFromAndroid(String name) {
        return getXmlId(name, PKG_ANDROID);
    }


    private View decorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        decorView = getWindow().getDecorView();
        super.onCreate(savedInstanceState);
    }

    /**
     * Check whether the input method is displayed.
     */
    protected final boolean isInputMethodShowing() {
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        return decorView.getHeight() - rect.bottom > getNavigationBarHeight();
    }


    protected final @Dimension
    int getDimensionPixelSize(@DimenRes int id) {
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

    protected final @Dimension
    int getStatusBarHeight() {
        String name = "status_bar_height";
        return orientationIsPortrait()
                ? getDimensionPixelSize(getDimenIdFromAndroid(name))
                : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
    }

    protected final @Dimension
    int getNavigationBarHeight() {
        String name = "navigation_bar_height";
        return orientationIsPortrait()
                ? getDimensionPixelSize(getDimenIdFromAndroid(name))
                : getDimensionPixelSize(getDimenIdFromAndroid(name + "_landscape"));
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
