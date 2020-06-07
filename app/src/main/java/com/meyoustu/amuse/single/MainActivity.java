package com.meyoustu.amuse.single;

import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.meyoustu.amuse.annotation.Native;
import com.meyoustu.amuse.annotation.res.AAnimation;
import com.meyoustu.amuse.annotation.res.AColor;
import com.meyoustu.amuse.annotation.res.AString;
import com.meyoustu.amuse.annotation.res.AView;
import com.meyoustu.amuse.annotation.sysbar.NavigationBarColor;
import com.meyoustu.amuse.annotation.sysbar.StatusBarColor;
import com.meyoustu.amuse.view.Dialog;
import com.meyoustu.amuse.view.InitWithGone;

/**
 * @author Liangcheng Juves
 * Created at 2020/6/1 14:37
 */
@StatusBarColor(android.R.color.white)
@NavigationBarColor(android.R.color.white)
@Native("main")
public class MainActivity extends com.meyoustu.amuse.Activity {

    @AView(R.id.sample_text)
    @InitWithGone
    TextView textView;

    @AColor(R.color.accent_color)
    int accentColor;

    @AString(R.string.app_name)
    String appName;

    @AAnimation(android.R.anim.fade_in)
    Animation fadeIn;

    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        textView.setText("hello,amuse");
        textView.setTextColor(accentColor);
        textView.setText(appName);
        textView.setText(stringFromJNI());

        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.VISIBLE);
                textView.startAnimation(fadeIn);
            }
        }, 500);

        Dialog dialog = new Dialog(this).setMessage(stringFromJNI());
        dialog.show();
    }

    native private String stringFromJNI();
}

