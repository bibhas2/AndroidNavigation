package com.mobiarch.sample;

import android.view.MenuItem;
import android.view.View;

import com.mobiarch.navigation.UIViewController;

public class ViewControllerC extends UIViewController {
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
                getNavigationController().pushViewController(new ViewControllerC(), true);
            }
        });

        getView().findViewById(R.id.buttonToPresentController).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().presentViewController(new ViewControllerA(), false);
            }
        });

        setOptionMenuResourceId(R.menu.view_controler_c_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.closeModal) {
            getActivity().dismissViewController(true);

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
