package com.meyoustu.amuse.multidex;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;

import androidx.annotation.Keep;

import java.io.File;
import java.util.StringTokenizer;

@Keep
public class MultiDex {
    public static Result install(Context context) {
        return install(context, null);
    }

    public static Result install(Context context, Monitor monitor) {
        Monitor.init(monitor);

        monitor = Monitor.get();

        monitor.logInfo("MultiDex is installing ...");

        if (isVMCapable(System.getProperty("java.vm.version"))) {
            monitor.logInfo("MultiDex support library is disabled for VM capable");
            return null;
        }

        if (Build.VERSION.SDK_INT < Constants.MIN_SDK_VERSION) {
            monitor.logInfo("MultiDex installation failed. SDK " + Build.VERSION.SDK_INT +
                    " is unsupported. Min SDK version is " + Constants.MIN_SDK_VERSION + ".");
            return null;
        }

        Result result = Result.get();
        try {
            ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo == null) {
                throw new RuntimeException("ApplicationInfo is NULL.");
            }

            File sourceDir = new File(applicationInfo.sourceDir);

            String processName = monitor.getProcessName();
            if (processName == null) {
                processName = Utility.getCurProcessName(context);
            }
            if (Utility.isOptimizeProcess(processName)) {
                // Force use dex bytes in opt process.
                // But a better way is avoid calling install(), then go to opt service directly.
                new DexInstallProcessor().doInstallationInOptProcess(context, sourceDir);
                return null;
            } else {
                new DexInstallProcessor().doInstallation(context, sourceDir, result);
            }

        } catch (Throwable e) {
            monitor.logError("MultiDex installation failure", e);
            result.setFatalThrowable(e);
        }
        monitor.logInfo("install done");

        return result;
    }

    public static boolean isOptimizeProcess(String processName) {
        return Utility.isOptimizeProcess(processName);
    }

    /**
     * Identifies if the current VM has a native support for multidex, meaning there is no need for
     * additional installation by this library.
     *
     * @return true if the VM handles multidex
     */
    private static boolean isVMCapable(String versionString) {
        boolean isCapable = false;
        if (versionString != null) {
            StringTokenizer tokenizer = new StringTokenizer(versionString, ".");
            String majorToken = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
            String minorToken = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : null;
            if (majorToken != null && minorToken != null) {
                try {
                    int major = Integer.parseInt(majorToken);
                    int minor = Integer.parseInt(minorToken);
                    isCapable = (major > Constants.VM_WITH_MULTIDEX_VERSION_MAJOR) ||
                            ((major == Constants.VM_WITH_MULTIDEX_VERSION_MAJOR) &&
                                    (minor >= Constants.VM_WITH_MULTIDEX_VERSION_MINOR));
                } catch (NumberFormatException e) {
                    // let isCapable be false
                }
            }
        }
        Monitor.get().logInfo("VM with version " + versionString +
                (isCapable ? " has support" : " does not have support"));
        return isCapable;
    }

}
