package com.meyoustu.amuse.listen;

import android.view.MotionEvent;
import android.view.View;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static java.lang.System.currentTimeMillis;

/**
 * Created at 2020/6/23 15:45.
 *
 * @author Liangcheng Juves
 */
public abstract class EffectClickListener implements ClickListener, View.OnTouchListener {

  private long touchDown;

  @Override
  public void initialize(View v, int vId) {
    // Do nothing.
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
    if (event.getAction() == ACTION_DOWN) {
      touchDown = currentTimeMillis();
      onTouchDown(v, v.getId());
    } else if (event.getAction() == ACTION_UP) {
      onTouchUp(v, v.getId());
      if (currentTimeMillis() - touchDown <= RESP_TIME_MILLLIS) {
        onClick(v, v.getId());
      }
    }
    return true;
  }

  public static long RESP_TIME_MILLLIS = 500;

  public static void setRespTimeMilllis(long respTimeMilllis) {
    RESP_TIME_MILLLIS = respTimeMilllis;
  }
}
