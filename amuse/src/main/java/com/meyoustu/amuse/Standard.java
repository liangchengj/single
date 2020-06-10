package com.meyoustu.amuse;

import android.app.Activity;
import android.content.pm.ShortcutManager;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.meyoustu.amuse.listen.ClickListener;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.N_MR1;
import static com.meyoustu.amuse.listen.ClickListener.RESP_TIME_MILLLIS;
import static java.lang.System.currentTimeMillis;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/10 17:52
 */
interface Standard {

    default Activity getActivity() {
        return (Activity) this;
    }

    default View getDecorView() {
        return getActivity().getWindow().getDecorView();
    }

    @LayoutRes
    int initView();


    default void setDecorViewRadius(int radius) {
        App.setViewRadius(radius, getDecorView());
    }

    default void setViewRadius(int radius, @IdRes int... ids) {
        if (null != ids) {
            if (ids.length != 0) {
                View[] views = new View[ids.length];
                for (int i = 0; i < views.length; i++) {
                    views[i] = getActivity().findViewById(ids[i]);
                }
                App.setViewRadius(radius, views);
            }
        }
    }

    default void setViewOval(@IdRes int... ids) {
        if (null != ids) {
            if (ids.length != 0) {
                View[] views = new View[ids.length];
                for (int i = 0; i < views.length; i++) {
                    views[i] = getActivity().findViewById(ids[i]);
                }
                App.setViewOval(views);
            }
        }
    }


    /* Used for the view press effect and contains the click event of the view. */
    default void effectClick(View v, final ClickListener clickListener) {
        v.setOnTouchListener(new View.OnTouchListener() {
            private long touchDown;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    clickListener.onTouchDown(v, event);
                    touchDown = currentTimeMillis();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    clickListener.onTouchUp(v, event);
                    if (currentTimeMillis() - touchDown <= RESP_TIME_MILLLIS) {
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
    default ShortcutManager getShortCutManager() {
        return SDK_INT >= N_MR1
                ? getActivity().getSystemService(ShortcutManager.class)
                : null;
    }

}
