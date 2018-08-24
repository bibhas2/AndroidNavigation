package com.mobiarch.navigation;

import android.support.v4.view.ViewPager;
import com.mobiarch.navigation.adapter.ViewControllerPagerAdapter;

public class PageViewController extends UIViewController {
    ViewPager viewPager;
    UIViewController viewControllers[];
    ViewControllerPagerAdapter adapter;
    Integer lastPrimaryItem;

    public PageViewController() {
        super(R.layout.page_view_controller);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        viewPager = (ViewPager) getView();
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        if (adapter == null) {
            adapter = new ViewControllerPagerAdapter(getActivity()) {
                @Override
                public UIViewController getItem(int position) {
                    return getViewControllers()[position];
                }

                @Override
                public int getCount() {
                    return getViewControllers().length;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return getItem(position).getTitle();
                }
            };
        }

        /**
         * Setting the adapter every time the view appears means
         * the lifecycle of embedded view controllers will be preserved.
         */
        getViewPager().setAdapter(getAdapter());

        if (lastPrimaryItem != null) {
            getViewPager().setCurrentItem(lastPrimaryItem);
        }
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();

        /**
         * Removing the adapter will remove all currently displayed
         * view controllers from the hierarchy. Which
         * will maintain lifecycle of these view controllers.
         */
        lastPrimaryItem = getViewPager().getCurrentItem();
        getViewPager().setAdapter(null);
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public UIViewController[] getViewControllers() {
        return viewControllers;
    }

    public void setViewControllers(UIViewController[] viewControllers) {
        this.viewControllers = viewControllers;
    }

    public ViewControllerPagerAdapter getAdapter() {
        return adapter;
    }
}
