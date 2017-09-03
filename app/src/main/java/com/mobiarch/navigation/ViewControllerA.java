package com.mobiarch.navigation;

import android.util.Log;
import android.view.View;

public class ViewControllerA extends ViewController {
    public ViewControllerA() {
        super(R.layout.view_controller_a);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        Log.d("NAV", "viewDidLoad");

        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().pushViewController(new ViewControllerB(), true);
            }
        });
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        Log.d("NAV", "viewWillAppear");

        getNavigationActivity().setTitle("View Controller A");
    }

    @Override
    public void viewDidAppear() {
        super.viewDidAppear();

        Log.d("NAV", "viewDidAppear");
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();

        Log.d("NAV", "viewWillDisappear");
    }

    @Override
    public void viewDidDisappear() {
        super.viewDidDisappear();

        Log.d("NAV", "viewDidDisappear");
    }
}
