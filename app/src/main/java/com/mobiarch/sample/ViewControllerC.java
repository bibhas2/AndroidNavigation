package com.mobiarch.sample;

import android.view.MenuItem;
import android.view.View;

import com.mobiarch.navigation.ViewController;

public class ViewControllerC extends ViewController {
    public ViewControllerC() {
        super(R.layout.view_controller_c);

        setTitle("Controller C");
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        getView().findViewById(R.id.buttonToPushController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().pushViewController(new ViewControllerC(), true);
            }
        });

        getView().findViewById(R.id.buttonToPresentController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNavigationActivity().presentViewController(new ViewControllerA(), false);
            }
        });

        setOptionMenuResourceId(R.menu.view_controler_c_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.closeModal) {
            getNavigationActivity().dismissViewController(true);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
