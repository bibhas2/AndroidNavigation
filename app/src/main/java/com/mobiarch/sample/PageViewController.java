package com.mobiarch.sample;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import com.mobiarch.navigation.NavigationActivity;
import com.mobiarch.navigation.ViewController;
import com.mobiarch.navigation.adapter.ViewControllerPagerAdapter;

/**
 * A controller that shows how to use the ViewControllerPagerAdapter.
 * This is similar to the UIPageViewController class in iOS.
 */
public class PageViewController extends ViewController {
    ViewPager viewPager;
    ViewController[] controllerList = {
            new ViewControllerA(),
            new ViewControllerB(),
            new ViewControllerC()
    };

    public PageViewController() {
        super(R.layout.view_pager_controller);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();

        viewPager = (ViewPager) getView().findViewById(R.id.viewPager);

        viewPager.setAdapter(new ViewControllerPagerAdapter() {
            @Override
            public NavigationActivity getActivity() {
                return getNavigationActivity();
            }

            @Override
            public ViewController getItem(int position) {
                return controllerList[position];
            }

            @Override
            public int getCount() {
                return controllerList.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "TAB " + position;
            }
        });

        TabLayout tabs = (TabLayout) getView().findViewById(R.id.tabs);

        tabs.setupWithViewPager(viewPager);
    }
}
