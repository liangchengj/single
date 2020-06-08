package com.meyoustu.amuse.single;

import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.meyoustu.amuse.annotation.IntelliRes;
import com.meyoustu.amuse.annotation.Native;
import com.meyoustu.amuse.annotation.res.AColor;
import com.meyoustu.amuse.annotation.res.AString;
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
@IntelliRes
@Native("main")
public class MainActivity extends com.meyoustu.amuse.Activity {

    @InitWithGone
    TextView helloText;

    Animation androidFadeIn;

    @AColor(R.color.accent_color)
    int accentColor;

    @AString(R.string.app_name)
    String appName;


    @Override
    protected int initView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();

        helloText.setText("hello,amuse");
        helloText.setTextColor(accentColor);
        helloText.setText(appName);
        helloText.setText(stringFromJNI());

        helloText.postDelayed(new Runnable() {
            @Override
            public void run() {
                helloText.setVisibility(View.VISIBLE);
                helloText.startAnimation(androidFadeIn);
            }
        }, 500);

        Dialog dialog = new Dialog(this).setMessage(stringFromJNI());
        dialog.show();
    }

    native private String stringFromJNI();
}