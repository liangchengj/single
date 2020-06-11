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
    implementation 'com.github.liangchengj:single:2020.6.12.107'
}
```
******
### Usage <e.g.> :
#### AndroidManifest.xml
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
#### activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:id="@+id/hello_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="hello,world"
        android:textColor="@android:color/black"
        android:textSize="30sp"
        android:textStyle="bold"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
#### MainActivity.java
``` java
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
```
