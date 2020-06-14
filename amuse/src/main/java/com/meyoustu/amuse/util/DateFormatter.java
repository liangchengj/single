package com.meyoustu.amuse.util;

import android.os.Build;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public final class DateFormatter {
  private DateFormatter() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      threadLocal =
          new ThreadLocal<DateFormat>() {
            @Nullable
            @Override
            protected DateFormat initialValue() {
              return simpleDateFormat;
            }
          };
    } else {
      threadLocal = null;
    }
  }

  private SimpleDateFormat simpleDateFormat;

  public static DateFormatter format(String text) {
    DateFormatter dateFormatter = new DateFormatter();
    dateFormatter.simpleDateFormat = new SimpleDateFormat(text);
    return dateFormatter;
  }

  public static final String formatNowNormally() {
    return new Date().toString();
  }

  private final ThreadLocal<DateFormat> threadLocal;

  @Override
  public String toString() {
    return threadLocal != null
        ? threadLocal.get().format(new Date())
        : simpleDateFormat.format(new Date());
  }
}
