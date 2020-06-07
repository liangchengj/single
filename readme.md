# single
The Android framework branch of the Amuse project.
******
******
## To get a Git project into your build:
### Step 1. Add the JitPack repository to your build file
### Add it in your root build.gradle at the end of repositories:
``` gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
### Step 2. Add the dependency
``` gradle
dependencies {
    implementation 'com.github.liangchengj:single:2020.6.7.2112'
}
```
******
### Usage <e.g.> :
``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.meyoustu.amuse.single">

    <application
        android:name="com.meyoustu.amuse.App"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```
``` java
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
```
