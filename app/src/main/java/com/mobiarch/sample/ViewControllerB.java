package com.mobiarch.sample;

import android.view.MenuItem;
import android.view.View;

import com.mobiarch.navigation.ViewController;

public class ViewControllerB extends ViewController {
    public ViewControllerB() {
        super(R.layout.view_controller_b);

        setTitle("Controller B");
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        getView().findViewById(R.id.buttonToPushController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().pushViewController(new ViewControllerA(), true);
            }
        });

        getView().findViewById(R.id.buttonToPresentController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().presentViewController(new ViewControllerC(), true);
            }
        });

        setOptionMenuResourceId(R.menu.view_controler_b_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popToRoot) {
            getNavigationActivity().popToRootViewController(false);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
