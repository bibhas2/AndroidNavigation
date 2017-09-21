package com.mobiarch.sample;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.mobiarch.navigation.NavigationActivity;
import com.mobiarch.navigation.PageViewController;
import com.mobiarch.navigation.ViewController;
import com.mobiarch.navigation.adapter.ViewControllerPagerAdapter;

/**
 * A controller that shows how to use the ViewControllerPagerAdapter.
 * This is similar to the UIPageViewController class in iOS.
 */
public class SimpleTabController extends ViewController {
    ViewPager viewPager;
    ViewController[] controllerList = {
            new ViewControllerA(),
            new ViewControllerB(),
            new ViewControllerC()
    };
    PageViewController pageViewController;

    public SimpleTabController() {
        super(R.layout.simple_tab_controller);

        pageViewController = new PageViewController();
        pageViewController.setViewControllers(controllerList);
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        ViewGroup container = (ViewGroup) getView().findViewById(R.id.container);

        getNavigationActivity().addToViewHierarchyOfContainer(
                container, pageViewController, null);

        TabLayout tabs = (TabLayout) getView().findViewById(R.id.tabs);

        tabs.setupWithViewPager(pageViewController.getViewPager());
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();

        ViewGroup container = (ViewGroup) getView().findViewById(R.id.container);

        getNavigationActivity().removeFromViewHierarchyOfContainer(container, pageViewController);
    }
}
