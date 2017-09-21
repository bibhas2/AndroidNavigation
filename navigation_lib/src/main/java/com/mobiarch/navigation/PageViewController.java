package com.mobiarch.navigation;

import android.support.v4.view.ViewPager;
import com.mobiarch.navigation.adapter.ViewControllerPagerAdapter;

public class PageViewController extends ViewController {
    ViewPager viewPager;
    ViewController viewControllers[];
    ViewControllerPagerAdapter adapter;

    public PageViewController() {
        super(R.layout.page_view_controller);

        adapter = new ViewControllerPagerAdapter() {
            @Override
            public NavigationActivity getActivity() {
                return getNavigationActivity();
            }

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
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        setViewPager((ViewPager) getView());
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;

        viewPager.setAdapter(getAdapter());
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
