package com.example.bibhas.navigation;

import android.view.MenuItem;
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

        setOptionMenuResourceId(R.menu.view_controler_a_menu);
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        getNavigationActivity().setTitle("View Controller A");
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
