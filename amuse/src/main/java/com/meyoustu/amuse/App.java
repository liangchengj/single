package com.meyoustu.amuse;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.RemoteViews;


import com.meyoustu.amuse.multidex.MultiDexApp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.graphics.BitmapFactory.decodeResource;
import static android.net.Uri.fromParts;
import static android.net.Uri.parse;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static android.os.Build.VERSION_CODES.O;
import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
import static android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS;
import static android.provider.Settings.EXTRA_APP_PACKAGE;
import static java.lang.System.currentTimeMillis;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 14:37
 * <p>
 * *** This class cannot be an abstract class.
 * *** The constructor of this class cannot be modified with protected or private.
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
        } catch (ClassNotFoundException |
                NoSuchFieldException |
                IllegalArgumentException |
                IllegalAccessException e) {
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
            } else if (classOfT == byte.class ||
                    classOfT == short.class ||
                    classOfT == int.class ||
                    classOfT == long.class ||
                    classOfT == float.class ||
                    classOfT == double.class) {
                return (T) new Integer(-1);
            }
        } else if (classOfT == String.class) {
            return (T) "";
        }
        return null;
    }

    private static <T> T annotatedInvoke(Annotation annotation, Class<T> classOfT)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (T) wrap(classOfT).cast(
                annotation.getClass().getMethod("value").invoke(annotation));
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static <T> T getClassAnnotatedValue(Context ctx,
                                               Class<? extends Annotation> annotation,
                                               Class<T> classOfT) {
        Class<?> clazz = ctx.getClass();
        if (clazz.isAnnotationPresent(annotation)) {
            Annotation a = annotation.cast(clazz.getAnnotation(annotation));
            try {
                return annotatedInvoke(a, classOfT);
            } catch (NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException e) {
                return null;
            }
        }
        return annotatedDefVal(classOfT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static <T> T getFieldAnnotatedValue(Field field,
                                               Class<? extends Annotation> annotation,
                                               Class<T> classOfT) {
        if (field.isAnnotationPresent(annotation)) {
            Annotation a = annotation.cast(field.getAnnotation(annotation));
            try {
                return annotatedInvoke(a, classOfT);
            } catch (NoSuchMethodException |
                    IllegalAccessException |
                    InvocationTargetException e) {
                return null;
            }
        }
        return annotatedDefVal(classOfT);
    }


    public static final void browse(Context context, String url) {
        context.startActivity(new Intent(ACTION_VIEW)
                .addCategory(CATEGORY_BROWSABLE)
                .setData(parse(url)));
    }


    public static final void setViewRadius(final int radius, View... views) {
        if (SDK_INT >= LOLLIPOP) {
            for (View view : views)
                view.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
                        view.setClipToOutline(true);
                    }
                });
        }
    }

    private static boolean sendNotificationCheckOnce;


    public synchronized static final void sendNotification(Context ctx, int id, int importance,
                                                           NotificationCompat.Style style, boolean autoCancel,
                                                           String title, String message, int smallIcon,
                                                           int largeIcon, RemoteViews remoteViews,
                                                           PendingIntent pendingIntent) {
        if (NotificationManagerCompat.from(ctx).areNotificationsEnabled()) {

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(ctx);

            smallIcon = (smallIcon <= 0) ? R.drawable.logo : smallIcon;
            largeIcon = (largeIcon <= 0) ? R.drawable.logo : largeIcon;

            notificationManagerCompat.notify(id, new NotificationCompat.Builder(ctx, String.valueOf(id))
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

            Intent intent = new Intent();

            String pkgName = ctx.getPackageName();

            try {
                if (SDK_INT >= O) {
                    intent.setAction(ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra(EXTRA_APP_PACKAGE, pkgName);
                } else if (SDK_INT > LOLLIPOP) {
                    intent.setAction(ACTION_APP_NOTIFICATION_SETTINGS)
                            .putExtra("app_package", pkgName)
                            .putExtra("app_uid", ctx.getApplicationInfo().uid);
                } else {
                    intent.setAction(ACTION_APPLICATION_DETAILS_SETTINGS)
                            .setData(fromParts("package", pkgName, null));
                }

            } catch (Exception e) {
                intent.setAction(ACTION_APPLICATION_DETAILS_SETTINGS)
                        .setData(fromParts("package", pkgName, null));
            }

            sendNotificationCheckOnce = true;
            ctx.startActivity(intent);
        }
    }


    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static final boolean hasNetWork(Context ctx) {
        if (ActivityCompat.checkSelfPermission(ctx, ACCESS_NETWORK_STATE) !=
                PERMISSION_GRANTED) {
            Toast.showLong(ctx, "Not Found Permission ACCESS_NETWORK_STATE!");
        } else if (((ConnectivityManager) ctx.getSystemService(CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null) {
            return true;
        }
        return false;
    }


    public static final void openApp(Context context, String packageName) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
    }


    // ====== Log Util
    private static String dealMsg(Object msg) {
        return Thread.currentThread().getStackTrace()[7].getMethodName() + "\n" + msg.toString();
    }

    private static String getTag(Context ctx) {
        return ctx.getClass().getName();
    }


    public static void verboseLog(Context ctx, Object msg) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.v(getTag(ctx), dealMsg(msg));
        }
    }

    public static void verboseLog(Context ctx, Object msg, Throwable t) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.v(getTag(ctx), dealMsg(msg), t);
        }
    }

    public static void debugLog(Context ctx, Object msg) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.d(getTag(ctx), dealMsg(msg));
        }
    }

    public static void debugLog(Context ctx, Object msg, Throwable t) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.d(getTag(ctx), dealMsg(msg), t);
        }
    }

    public static void infoLog(Context ctx, Object msg) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.i(getTag(ctx), dealMsg(msg));
        }
    }

    public static void infoLog(Context ctx, Object msg, Throwable t) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.i(getTag(ctx), dealMsg(msg), t);
        }
    }

    public static void warnLog(Context ctx, Object msg) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.w(getTag(ctx), dealMsg(msg));
        }
    }

    public static void warnLog(Context ctx, Object msg, Throwable t) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.w(getTag(ctx), dealMsg(msg), t);
        }
    }

    public static void errorLog(Context ctx, Object msg) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.e(getTag(ctx), dealMsg(msg));
        }
    }

    public static void errorLog(Context ctx, Object msg, Throwable t) {
        if (getDebugValOfBuildConfig(ctx) == true) {
            Log.e(getTag(ctx), dealMsg(msg), t);
        }
    }

    // ====== End Log Util


}
