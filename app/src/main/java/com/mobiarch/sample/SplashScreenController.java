package com.mobiarch.sample;

import com.mobiarch.navigation.UINavigationController;
import com.mobiarch.navigation.UIViewController;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenController extends UIViewController {
    Timer timer;

    public SplashScreenController() {
        super(R.layout.splash_screen);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(() -> {
                    getActivity().setRootViewController(new UINavigationController(new ViewControllerA()));

                    timer.cancel();
                });
            }
        }, 2300);
    }
}
