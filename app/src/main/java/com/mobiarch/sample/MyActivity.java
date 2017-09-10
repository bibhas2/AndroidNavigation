package com.mobiarch.sample;

import android.os.Bundle;

import com.mobiarch.navigation.NavigationActivity;

public class MyActivity extends NavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pushViewController(new ViewControllerA(), false);
    }
}
