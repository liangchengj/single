package com.meyoustu.amuse.listen;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created at 2020/6/14 10:16.
 *
 * @author Liangcheng Juves
 */
public interface ClickListener {
  void onTouchDown(View v, MotionEvent event);

  void onTouchUp(View v, MotionEvent event);

  void onClick(View v);

  int RESP_TIME_MILLLIS = 500;
}
