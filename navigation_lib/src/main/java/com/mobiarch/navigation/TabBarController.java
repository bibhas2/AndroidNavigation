package com.mobiarch.navigation;

import android.support.design.widget.TabLayout;
import android.view.ViewGroup;

/**
 * This class is similar to UITabBarController of iOS. The tabs
 * are shown on top instead of bottom.
 */
public class TabBarController extends ViewController {
    private PageViewController pageViewController;
    private TabLayout tabLayout;

    public TabBarController() {
        super(R.layout.tab_bar_controller);

        pageViewController = new PageViewController();
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        tabLayout = (TabLayout) getView().findViewById(R.id.tabLayout);
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        ViewGroup container = (ViewGroup) getView().findViewById(R.id.container);

        getNavigationActivity().addToViewHierarchyOfContainer(
                container, pageViewController, null);
        tabLayout.setupWithViewPager(pageViewController.getViewPager());
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();

        ViewGroup container = (ViewGroup) getView().findViewById(R.id.container);

        getNavigationActivity().removeFromViewHierarchyOfContainer(container, pageViewController);
    }

    public void setViewControllers(ViewController[] viewControllers) {
        pageViewController.setViewControllers(viewControllers);
    }

    public PageViewController getPageViewController() {
        return pageViewController;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
