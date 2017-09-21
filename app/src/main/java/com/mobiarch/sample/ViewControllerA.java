package com.mobiarch.sample;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.mobiarch.navigation.PageViewController;
import com.mobiarch.navigation.TabBarController;
import com.mobiarch.navigation.ViewController;

public class ViewControllerA extends ViewController {
    public ViewControllerA() {
        super(R.layout.view_controller_a);

        setTitle("Controller A");
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        Log.d("NAV", "viewDidLoad");

        getView().findViewById(R.id.buttonToPushController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().pushViewController(new ViewControllerB(), true);
            }
        });

        getView().findViewById(R.id.buttonToPushPagerController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewController[] controllerList = {
                        new ViewControllerA(),
                        new ViewControllerB(),
                        new ViewControllerC()
                };

                TabBarController controller = new TabBarController();

                controller.setViewControllers(controllerList);

                getNavigationActivity().pushViewController(controller, true);
            }
        });

        getView().findViewById(R.id.buttonToOpenMaterialShowcase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getNavigationActivity(), MaterialShowcaseActivity.class);
                getNavigationActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        Log.d("NAV", "viewWillAppear");
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
