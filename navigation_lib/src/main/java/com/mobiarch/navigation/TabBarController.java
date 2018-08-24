package com.mobiarch.navigation;

import android.support.design.widget.TabLayout;
import android.view.ViewGroup;

/**
 * This class is similar to UITabBarController of iOS. The tabs
 * are shown on top instead of bottom.
 */
public class TabBarController extends UIViewController {
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

        getActivity().addToViewHierarchyOfContainer(
                container, pageViewController, null);
        tabLayout.setupWithViewPager(pageViewController.getViewPager());
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();

        ViewGroup container = (ViewGroup) getView().findViewById(R.id.container);

        getActivity().removeFromViewHierarchyOfContainer(container, pageViewController);
    }

    public void setViewControllers(UIViewController[] viewControllers) {
        pageViewController.setViewControllers(viewControllers);
    }

    public PageViewController getPageViewController() {
        return pageViewController;
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
