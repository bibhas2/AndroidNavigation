package com.example.bibhas.navigation;

import android.os.Bundle;

public class MyActivity extends NavigationActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pushViewController(new ViewControllerA(), true);
    }
}
