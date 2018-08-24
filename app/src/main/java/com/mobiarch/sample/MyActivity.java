package com.mobiarch.sample;

import android.os.Bundle;

import com.mobiarch.navigation.UIActivity;
import com.mobiarch.navigation.UINavigationController;

public class MyActivity extends UIActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRootViewController(
                new SplashScreenController()
        );
    }
}
