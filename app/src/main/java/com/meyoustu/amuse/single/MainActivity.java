package com.meyoustu.amuse.single;

import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.Keep;

import com.meyoustu.amuse.annotation.IntelliRes;
import com.meyoustu.amuse.annotation.Native;
import com.meyoustu.amuse.annotation.res.AColor;
import com.meyoustu.amuse.annotation.res.AString;
import com.meyoustu.amuse.annotation.sysbar.NavigationBarColor;
import com.meyoustu.amuse.annotation.sysbar.StatusBarColor;
import com.meyoustu.amuse.content.SharedPrefs;
import com.meyoustu.amuse.view.InitWithGone;

/**
 * Created at 2020/6/1 14:37.
 *
 * @author Liangcheng Juves
 */
/** The default value is {@code Color.WHITE}. */
@StatusBarColor
/** The default value is {@code Color.WHITE}. */
@NavigationBarColor
/** Use this annotation to assign a value to a member by judging its name. */
@IntelliRes
@Native({"amuse", "main"})
/**
 * If you have enabled obfuscation, please use this annotation.
 *
 * @see build.gradle {@code minifyEnabled true}
 */
@Keep
public class MainActivity extends com.meyoustu.amuse.Activity {

  /** After using this annotation to initialize the View, it will be made invisible. */
  @InitWithGone TextView helloText;

  Animation androidFadeIn;

  @AColor(R.color.accent_color)
  int accentColor;

  @AString(R.string.app_name)
  String appName;

  /** Or use the class annotation "@ContentView(R.layout.activity_main)" to initialize the view. */
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

    helloText.postDelayed(
        new Runnable() {
          @Override
          public void run() {
            helloText.setVisibility(View.VISIBLE);
            helloText.startAnimation(androidFadeIn);
          }
        },
        500);
    helloText.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //                MainFragment mainFragment = new MainFragment();
          }
        });

    SharedPrefs sharedPrefs = SharedPrefs.initialize(ctx);
    sharedPrefs.putNumber("Test", 1);
    helloText.setText(String.valueOf(sharedPrefs.getInt("Test", 1)));

    //        Dialog dialog = new Dialog(this).setMessage(stringFromJNI());
    //        dialog.show();
    //
    //        helloText.postDelayed(new Runnable() {
    //            @Override
    //            public void run() {
    //                MainFragment mainFragment = new MainFragment();
    //                FragmentManager fm = getSupportFragmentManager();
    //                FragmentTransaction ft = fm.beginTransaction();
    //                ft.add(R.id.main_frag, mainFragment, "MAIN");
    //                ft.commit();
    //            }
    //        }, 1000);

  }

  private native String stringFromJNI();
}
