## What is this?

This is a library that brings the UIViewController API from iOS to Android.
A view controller is a direct replacement for an Android Fragment.

## Using this library

To your project's build.gradle add the jitpack.io repo:

```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
```

Then in your app's build.gradle add a dependency for the library:

```
compile 'com.github.bibhas2:AndroidNavigation:1.0'
```
