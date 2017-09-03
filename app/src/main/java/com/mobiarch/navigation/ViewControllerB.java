package com.mobiarch.navigation;

import android.view.MenuItem;
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

        setOptionMenuResourceId(R.menu.view_controler_b_menu);
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        getNavigationActivity().setTitle("View Controller B");
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