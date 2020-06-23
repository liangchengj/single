package com.meyoustu.amuse;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RemoteViews;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.meyoustu.amuse.annotation.IntelliRes;
import com.meyoustu.amuse.annotation.res.AAnimation;
import com.meyoustu.amuse.annotation.res.AColor;
import com.meyoustu.amuse.annotation.res.ADimen;
import com.meyoustu.amuse.annotation.res.ADrawable;
import com.meyoustu.amuse.annotation.res.AString;
import com.meyoustu.amuse.annotation.res.AStringArray;
import com.meyoustu.amuse.annotation.res.AView;
import com.meyoustu.amuse.annotation.res.AXml;
import com.meyoustu.amuse.multidex.MultiDexApp;
import com.meyoustu.amuse.util.Toast;
import com.meyoustu.amuse.view.InitWithGone;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.LinkedList;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.graphics.BitmapFactory.decodeResource;
import static android.net.Uri.fromParts;
import static android.net.Uri.parse;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.M;
import static android.os.Build.VERSION_CODES.O;
import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
import static android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS;
import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static com.meyoustu.amuse.Activity.IDENTIFIER_ANIMATION;
import static com.meyoustu.amuse.Activity.IDENTIFIER_ID;
import static com.meyoustu.amuse.Activity.PKG_ANDROID;
import static java.lang.System.currentTimeMillis;

/**
 * @author Liangcheng Juves Created at 2020/6/1 14:37
 *     <p>*** This class cannot be an abstract class. *** The constructor of this class cannot be
 *     modified with protected or private.
 */
public class App extends MultiDexApp {

  /**
   * Only valid in debug mode and without confusion.
   *
   * @param ctx The type is "android.content.Context".
   * @return The static Boolean variable "DEBUG" of the BuildConfig class.
   */
  public static final boolean getDebugValOfBuildConfig(Context ctx) {
    try {
      Class<?> clazz = Class.forName(ctx.getApplicationInfo().processName + ".BuildConfig");
      return clazz.getDeclaredField("DEBUG").getBoolean(ctx);
    } catch (ClassNotFoundException
        | NoSuchFieldException
        | IllegalArgumentException
        | IllegalAccessException e) {
      // It may happen that the class cannot be found after confusion;
      // generally, we will introduce the confusion mechanism after we formally package and
      // release it, so it returns "false", which is the same as the value officially
      // released by the "BuildConfig" class without conflict.
      return false;
    }
  }

  private static <T> Class<?> wrap(Class<T> classOfT) {
    if (classOfT == void.class) {
      return Void.class;
    } else if (classOfT == byte.class) {
      return Byte.class;
    } else if (classOfT == short.class) {
      return Short.class;
    } else if (classOfT == int.class) {
      return Integer.class;
    } else if (classOfT == long.class) {
      return Long.class;
    } else if (classOfT == float.class) {
      return Float.class;
    } else if (classOfT == double.class) {
      return Double.class;
    } else if (classOfT == boolean.class) {
      return Boolean.class;
    } else if (classOfT == char.class) {
      return Character.class;
    } else {
      return classOfT;
    }
  }

  public static boolean isPrimitive(Type type) {
    return type instanceof Class<?> && ((Class<?>) type).isPrimitive();
  }

  private static <T> T annotatedDefVal(Class<T> classOfT) {
    if (isPrimitive(classOfT)) {
      if (classOfT == boolean.class) {
        return (T) new Boolean(false);
      } else if (classOfT == char.class) {
        return (T) new Character(' ');
      } else if (classOfT == byte.class
          || classOfT == short.class
          || classOfT == int.class
          || classOfT == long.class
          || classOfT == float.class
          || classOfT == double.class) {
        return (T) new Integer(-1);
      }
    } else if (classOfT == String.class) {
      return (T) "";
    } else if (classOfT == String[].class) {
      return (T) new String[] {""};
    } else if (classOfT.isArray()) {
      return (T) Array.newInstance(classOfT, 0);
    }
    return null;
  }

  private static <T> T annotatedInvoke(Annotation annotation, Class<T> classOfT)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    return (T) wrap(classOfT).cast(annotation.getClass().getMethod("value").invoke(annotation));
  }

  public static <T> T getClassAnnotatedValue(
      Context ctx, Class<? extends Annotation> annotation, Class<T> classOfT) {
    Class<?> clazz = ctx.getClass();
    if (clazz.isAnnotationPresent(annotation)) {
      Annotation a = annotation.cast(clazz.getAnnotation(annotation));
      try {
        return annotatedInvoke(a, classOfT);
      } catch (Throwable t) {
        // In order to prevent the null pointer exception.
        return annotatedDefVal(classOfT);
      }
    }
    return annotatedDefVal(classOfT);
  }

  public static <T> T getFieldAnnotatedValue(
      Field field, Class<? extends Annotation> annotation, Class<T> classOfT) {
    if (field.isAnnotationPresent(annotation)) {
      Annotation a = annotation.cast(field.getAnnotation(annotation));
      try {
        return annotatedInvoke(a, classOfT);
      } catch (Throwable t) {
        // In order to prevent the null pointer exception.
        return annotatedDefVal(classOfT);
      }
    }
    return annotatedDefVal(classOfT);
  }

  private static String intelliGuessResId(Field field) {
    String name = field.getName();
    char[] cs = name.toCharArray();
    if (cs[0] == 'm' && Character.isUpperCase(cs[1])) {
      name = name.substring(1);
      cs = name.toCharArray();
    }

    LinkedList<Integer> list = new LinkedList<>();
    list.add(0);
    for (int i = 0; i < cs.length; i++) {
      if (Character.isUpperCase(cs[i])) {
        list.add(i);
      }
    }
    list.add(cs.length);

    String append = "";
    for (int i = 0; i < list.size() - 1; i++) {
      int begin = list.get(i);
      int end = list.get(i + 1);
      if (end > begin) {
        append += name.substring(begin, end).toLowerCase() + "_";
      }
    }
    append = append.substring(0, append.length() - 1);
    return append;
  }

  private static boolean typeOfView(Field field) {
    return field.getType() == View.class || field.getType().getSuperclass() == View.class;
  }

  private static boolean typeOfAnimation(Field field) {
    return field.getType() == Animation.class || field.getType().getSuperclass() == Animation.class;
  }

  private static boolean noAnnotated(Field field) {
    return !field.isAnnotationPresent(AAnimation.class)
        && !field.isAnnotationPresent(AColor.class)
        && !field.isAnnotationPresent(ADimen.class)
        && !field.isAnnotationPresent(ADrawable.class)
        && !field.isAnnotationPresent(AString.class)
        && !field.isAnnotationPresent(AStringArray.class)
        && !field.isAnnotationPresent(AView.class)
        && !field.isAnnotationPresent(AXml.class);
  }

  static @IdRes int getResId(Context ctx, String name, String defPkgName) {
    return ctx.getResources().getIdentifier(name, IDENTIFIER_ID, defPkgName);
  }

  static @AnimRes int getAnimId(Context ctx, String name, String defPkgName) {
    return ctx.getResources().getIdentifier(name, IDENTIFIER_ANIMATION, defPkgName);
  }

  /* Initialize the "Field" by checking whether it is annotated. */
  public static <T extends View> void initResMemberByAnnotation(Activity activity)
      throws IllegalAccessException {
    Resources res = activity.getResources();
    if (null == res) {
      return;
    }
    boolean initWithIntelli = activity.getClass().isAnnotationPresent(IntelliRes.class);
    for (Field field : activity.getClass().getDeclaredFields()) {
      field.setAccessible(true);

      if (initWithIntelli) {
        /* ====== Intelli deal view. */
        if (typeOfView(field)) {
          T v = null;
          String guessName = intelliGuessResId(field);
          try {
            if (guessName.startsWith(PKG_ANDROID)) {
              guessName = guessName.replace(PKG_ANDROID + "_", "");
              v =
                  activity.findViewById(
                      getResId(activity, guessName.replace(PKG_ANDROID, ""), PKG_ANDROID));
            } else {
              v = activity.findViewById(getResId(activity, guessName, activity.getPackageName()));
            }
          } catch (Throwable t) {
            try {
              LinkedList<Integer> list = new LinkedList<>();
              list.add(0);
              char[] cs = guessName.toCharArray();
              for (int i = 0; i < cs.length; i++) {
                if (cs[i] == '_') {
                  list.add(i);
                }
              }
              list.add(cs.length);

              for (int i = 0; i < list.size() - 1; i++) {
                int begin = list.get(i);
                int end = list.get(i + 1);
                if (end > begin) {
                  cs[list.get(i + 1) + 1] = Character.toUpperCase(cs[list.get(i + 1) + 1]);
                  cs[list.get(i + 1)] = '\0';
                }
              }

              v =
                  activity.findViewById(
                      getResId(activity, new String(cs), activity.getPackageName()));
            } catch (Throwable tx) {
              if (noAnnotated(field)) {
                continue;
              }
            }
          } finally {
            if (null != v) {
              field.set(activity, v);
              if (field.isAnnotationPresent(InitWithGone.class)) {
                v.setVisibility(View.GONE);
              }
            }
          }
        }
        /* ====== Intelli deal view. */

        /* ====== Intelli deal animation. */
        if (typeOfAnimation(field)) {
          String guessName = intelliGuessResId(field);
          try {
            if (guessName.startsWith(PKG_ANDROID)) {
              guessName = guessName.replace(PKG_ANDROID + "_", "");
              field.set(
                  activity,
                  field
                      .getType()
                      .cast(
                          AnimationUtils.loadAnimation(
                              activity,
                              getAnimId(
                                  activity, guessName.replace(PKG_ANDROID, ""), PKG_ANDROID))));
            } else {
              field.set(
                  activity,
                  field
                      .getType()
                      .cast(
                          AnimationUtils.loadAnimation(
                              activity,
                              getAnimId(activity, guessName, activity.getPackageName()))));
            }
          } catch (Throwable t) {
            try {
              LinkedList<Integer> list = new LinkedList<>();
              list.add(0);
              char[] cs = guessName.toCharArray();
              for (int i = 0; i < cs.length; i++) {
                if (cs[i] == '_') {
                  list.add(i);
                }
              }
              list.add(cs.length);

              for (int i = 0; i < list.size() - 1; i++) {
                int begin = list.get(i);
                int end = list.get(i + 1);
                if (end > begin) {
                  cs[list.get(i + 1) + 1] = Character.toUpperCase(cs[list.get(i + 1) + 1]);
                  cs[list.get(i + 1)] = '\0';
                }
              }

              field.set(
                  activity,
                  field
                      .getType()
                      .cast(
                          AnimationUtils.loadAnimation(
                              activity,
                              getAnimId(activity, new String(cs), activity.getPackageName()))));
            } catch (Throwable tx) {
              if (noAnnotated(field)) {
                continue;
              }
            }
          }
        }
        /* ====== Intelli deal animation. */

        if (noAnnotated(field)) {
          continue;
        }
      }

      @IdRes int aViewVal = getFieldAnnotatedValue(field, AView.class, int.class);
      if (aViewVal != -1) {
        T t = activity.findViewById(aViewVal);
        field.set(activity, t);
        if (field.isAnnotationPresent(InitWithGone.class) && typeOfView(field)) {
          t.setVisibility(View.GONE);
        }
      }

      @StringRes int aStringVal = getFieldAnnotatedValue(field, AString.class, int.class);
      if (aStringVal != -1) {
        field.set(activity, activity.getString(aStringVal));
      }

      @ArrayRes int aStringArrayVal = getFieldAnnotatedValue(field, AStringArray.class, int.class);
      if (aStringArrayVal != -1) {
        field.set(activity, res.getStringArray(aStringArrayVal));
      }

      @AnimRes int aAnimationVal = getFieldAnnotatedValue(field, AAnimation.class, int.class);
      if (aAnimationVal != -1) {
        field.set(
            activity, field.getType().cast(AnimationUtils.loadAnimation(activity, aAnimationVal)));
      }

      @DrawableRes int aDrawableVal = getFieldAnnotatedValue(field, ADrawable.class, int.class);
      if (aDrawableVal != -1) {
        Drawable drawable;
        if (SDK_INT >= LOLLIPOP) {
          drawable = activity.getDrawable(aDrawableVal);
        } else {
          // In order to adapt to the old API.
          drawable = res.getDrawable(aDrawableVal);
        }
        field.set(activity, drawable);
      }

      @ColorRes int aColorVal = getFieldAnnotatedValue(field, AColor.class, int.class);
      if (aColorVal != -1) {
        field.set(activity, getResColor(activity, aColorVal));
      }

      @DimenRes int aDimenVal = getFieldAnnotatedValue(field, ADimen.class, int.class);
      if (aDimenVal != -1) {
        field.set(activity, res.getDimension(aDimenVal));
      }

      @XmlRes int aXmlVal = getFieldAnnotatedValue(field, AXml.class, int.class);
      if (aXmlVal != -1) {
        field.set(activity, res.getXml(aXmlVal));
      }
    }
  }

  /* Get the color from the resource file. */
  static int getResColor(Context ctx, @ColorRes int id) {
    if (SDK_INT >= M) {
      return ctx.getResources().getColor(id, ctx.getTheme());
    } else {
      // In order to adapt to the old API.
      return ctx.getResources().getColor(id);
    }
  }

  /**
   * Call the system browser to open the network connection.
   *
   * @param context Application context object.
   * @param url Network connection url.
   */
  public static final void launchBrowser(Context context, String url) {
    context.startActivity(
        new Intent(ACTION_VIEW).addCategory(CATEGORY_BROWSABLE).setData(parse(url)));
  }

  /**
   * Rounding corners for one or more views can only be used on Android 5.0 or above.
   *
   * @param radius The size of the rounded corners.
   * @param views One or more views.
   */
  public static final void setViewRadius(final int radius, View... views) {
    if (SDK_INT >= LOLLIPOP) {
      if (null != views) {
        for (View view : views) {
          if (null != view) {
            view.setOutlineProvider(
                new ViewOutlineProvider() {
                  @Override
                  public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                    view.setClipToOutline(true);
                  }
                });
          }
        }
      }
    }
  }

  public static final void setViewOval(View... views) {
    if (SDK_INT >= LOLLIPOP) {
      if (null != views) {
        for (View view : views) {
          if (null != view) {
            view.setOutlineProvider(
                new ViewOutlineProvider() {
                  @Override
                  public void getOutline(View view, Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                    view.setClipToOutline(true);
                  }
                });
          }
        }
      }
    }
  }

  private static boolean sendNotificationCheckOnce;

  /**
   * Push system notifications.
   *
   * @param ctx Application context object.
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
  public static final synchronized void sendNotification(
      Context ctx,
      int id,
      int importance,
      NotificationCompat.Style style,
      boolean autoCancel,
      String title,
      String message,
      int smallIcon,
      int largeIcon,
      RemoteViews remoteViews,
      PendingIntent pendingIntent) {

    if (NotificationManagerCompat.from(ctx).areNotificationsEnabled()) {

      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(ctx);

      smallIcon = (smallIcon <= 0) ? R.drawable.logo : smallIcon;
      largeIcon = (largeIcon <= 0) ? R.drawable.logo : largeIcon;

      notificationManagerCompat.notify(
          id,
          new NotificationCompat.Builder(ctx, String.valueOf(id))
              .setAutoCancel(autoCancel)
              .setContentTitle(title)
              .setContentText(message)
              .setWhen(currentTimeMillis())
              .setSmallIcon(smallIcon)
              .setLargeIcon(decodeResource(ctx.getResources(), largeIcon))
              .setCustomContentView(remoteViews)
              .setContentIntent(pendingIntent)
              .setStyle(style)
              .setPriority(importance)
              .build());

    } else if (!sendNotificationCheckOnce) {

      /* Check whether the system blocks push notifications for the application,
      and if so, jump to settings to guide the user to set up. */

      Intent intent = new Intent();
      String pkgName = ctx.getPackageName();

      try {
        if (SDK_INT >= O) {
          intent.setAction(ACTION_APP_NOTIFICATION_SETTINGS).putExtra(EXTRA_APP_PACKAGE, pkgName);
        } else if (SDK_INT > LOLLIPOP) {
          intent
              .setAction(ACTION_APP_NOTIFICATION_SETTINGS)
              .putExtra("app_package", pkgName)
              .putExtra("app_uid", ctx.getApplicationInfo().uid);
        } else {
          intent
              .setAction(ACTION_APPLICATION_DETAILS_SETTINGS)
              .setData(fromParts("package", pkgName, null));
        }

      } catch (Exception e) {
        // In order to adapt to the old API.
        intent
            .setAction(ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(fromParts("package", pkgName, null));
      }

      sendNotificationCheckOnce = true;

      // Jump to settings to guide the user to set up.
      ctx.startActivity(intent);
    }
  }

  /**
   * Check whether the activity or service corresponding to the application context has a network
   * connection.
   *
   * @param ctx Application context object.
   * @return "true" means that the activity or service where the application context is located has
   *     a network connection.
   */
  public static final boolean hasNetWork(Context ctx) {
    if (ActivityCompat.checkSelfPermission(ctx, ACCESS_NETWORK_STATE) != PERMISSION_GRANTED) {
      Toast.showLong(ctx, "Not Found Permission \"ACCESS_NETWORK_STATE\"!");
    } else {
      ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(CONNECTIVITY_SERVICE);
      if (SDK_INT >= M) {
        Network network = cm.getActiveNetwork();
        if (null == network) {
          return false;
        }
        NetworkCapabilities nc = cm.getNetworkCapabilities(network);
        if (null == nc) {
          return false;
        }
        return nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
      } else {
        // In order to adapt to the old API.
        @Deprecated NetworkInfo ni = cm.getActiveNetworkInfo();
        if (null == ni) {
          return false;
        }
        return ni.isConnected();
      }
    }
    return false;
  }

  /**
   * Check if a certain permission has been applied and the user agrees to authorize.
   *
   * @param ctx Application context object.
   * @param permission Permission name.
   * @return "true" means that the user has agreed to authorization, so there is no need to repeat
   *     the application.
   */
  public static final boolean hasApplyPermission(Context ctx, String permission) {
    return ActivityCompat.checkSelfPermission(ctx, permission) == PERMISSION_GRANTED;
  }

  /**
   * Used to check whether a certain permission has been applied for and the user agrees to
   * authorize, if not satisfied, reapply.
   *
   * @param activity Class object of android.app.Activity.
   * @param permissions java.lang.String[] -> The name used to store one or more permissions.
   * @param reqCode Request code for permission.
   */
  public static final void chkAndApplyPermissions(
      @NonNull android.app.Activity activity, @NonNull String[] permissions, int reqCode) {
    String append = "";
    for (short i = 0; i < permissions.length; i++) {
      String permission = permissions[0];
      if (!hasApplyPermission(activity, permission)
          || ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
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

  /**
   * Open the application via ${applicationId} or the application package name.
   *
   * @param ctx Application context object.
   * @param pkgName Application package name.
   */
  public static final void openApp(Context ctx, String pkgName) {
    ctx.startActivity(ctx.getPackageManager().getLaunchIntentForPackage(pkgName));
  }

  // ====== Log Util
  private static String dealMsg(Context ctx, Object msg) {
    StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
    String methodName = stackTraceElements[4].getMethodName();
    String end = "\n" + msg;
    if ("errorLog".equals(methodName)) {
      return stackTraceElements[5].getMethodName() + end;
    } else {
      return methodName + end;
    }
  }

  private static String getTag(Context ctx) {
    return ctx.getClass().getName();
  }

  public static void verboseLog(Context ctx, Object msg) {
    verboseLog(getDebugValOfBuildConfig(ctx), getTag(ctx), dealMsg(ctx, msg));
  }

  public static void verboseLog(Context ctx, Object msg, Throwable t) {
    if (getDebugValOfBuildConfig(ctx) == true) {
      Log.v(getTag(ctx), dealMsg(ctx, msg), t);
    }
  }

  public static void debugLog(Context ctx, Object msg) {
    debugLog(getDebugValOfBuildConfig(ctx), getTag(ctx), dealMsg(ctx, msg));
  }

  public static void debugLog(Context ctx, Object msg, Throwable t) {
    if (getDebugValOfBuildConfig(ctx) == true) {
      Log.d(getTag(ctx), dealMsg(ctx, msg), t);
    }
  }

  public static void infoLog(Context ctx, Object msg) {
    infoLog(getDebugValOfBuildConfig(ctx), getTag(ctx), dealMsg(ctx, msg));
  }

  public static void infoLog(Context ctx, Object msg, Throwable t) {
    if (getDebugValOfBuildConfig(ctx) == true) {
      Log.i(getTag(ctx), dealMsg(ctx, msg), t);
    }
  }

  public static void warnLog(Context ctx, Object msg) {
    warnLog(getDebugValOfBuildConfig(ctx), getTag(ctx), dealMsg(ctx, msg));
  }

  public static void warnLog(Context ctx, Object msg, Throwable t) {
    if (getDebugValOfBuildConfig(ctx) == true) {
      Log.w(getTag(ctx), dealMsg(ctx, msg), t);
    }
  }

  public static void errorLog(Context ctx, Object msg) {
    errorLog(getDebugValOfBuildConfig(ctx), getTag(ctx), dealMsg(ctx, msg));
  }

  public static void errorLog(Context ctx, Object msg, Throwable t) {
    if (getDebugValOfBuildConfig(ctx) == true) {
      Log.e(getTag(ctx), dealMsg(ctx, msg), t);
    }
  }

  // ====== End Log Util

  static native void verboseLog(boolean ifDebug, String tag, Object msg);

  static native void debugLog(boolean ifDebug, String tag, Object msg);

  static native void infoLog(boolean ifDebug, String tag, Object msg);

  static native void warnLog(boolean ifDebug, String tag, Object msg);

  static native void errorLog(boolean ifDebug, String tag, Object msg);
}
