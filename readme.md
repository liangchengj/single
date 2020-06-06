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
******
### Step 2. Add the dependency
``` gradle
	dependencies {
	        implementation 'com.github.liangchengj:single:2020.6.5.2050'
	}
```
