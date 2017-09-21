package com.mobiarch.navigation;

import android.support.v4.view.ViewPager;
import com.mobiarch.navigation.adapter.ViewControllerPagerAdapter;

public class PageViewController extends ViewController {
    ViewPager viewPager;
    ViewController viewControllers[];
    ViewControllerPagerAdapter adapter;

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
            adapter = new ViewControllerPagerAdapter(getNavigationActivity()) {
                @Override
                public ViewController getItem(int position) {
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

            viewPager.setAdapter(getAdapter());
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public ViewController[] getViewControllers() {
        return viewControllers;
    }

    public void setViewControllers(ViewController[] viewControllers) {
        this.viewControllers = viewControllers;
    }

    public ViewControllerPagerAdapter getAdapter() {
        return adapter;
    }
}
