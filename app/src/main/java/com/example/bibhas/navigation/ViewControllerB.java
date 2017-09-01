package com.example.bibhas.navigation;

import android.view.View;

public class ViewControllerB extends ViewController {
    public ViewControllerB() {
        super(R.layout.view_controller_b);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        getView().findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().pushViewController(new ViewControllerA(), true);
            }
        });
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        getNavigationActivity().setTitle("View Controller B");
    }
}
