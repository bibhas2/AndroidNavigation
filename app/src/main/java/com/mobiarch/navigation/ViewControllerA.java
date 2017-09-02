package com.mobiarch.navigation;

import android.view.View;

public class ViewControllerA extends ViewController {
    public ViewControllerA() {
        super(R.layout.view_controller_a);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

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

        getNavigationActivity().setTitle("View Controller A");
    }
}
