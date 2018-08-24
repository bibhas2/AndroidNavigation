package com.mobiarch.navigation.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.mobiarch.navigation.UIActivity;
import com.mobiarch.navigation.ViewController;

/**
 * <p>An adapter for the ViewPager widget that can show ViewController.
 * You need to subclass this class and implement the abstract methods.</p>
 *
 * <p>
 * A special note about the lifecycle of the view controllers shown in a ViewPager:
 * Due to the way ViewPager works, it may proactively go ahead and add the view of a
 * ViewController to the hierarchy even before the view is actually scrolled into view.
 * As a result, viewWillAppear and viewDidAppear may get called even when the ViewController
 * is not actually visible (but is added to the view hierarchy). As a result you should refrain
 * from setting the actionbar title and option menu from these view controllers. If you must set
 * the title and option menu, install a ViewPager.OnPageChangeListener for the ViewPager and
 * do so from there.
 * </p>
 */
public abstract class ViewControllerPagerAdapter extends PagerAdapter {
    UIActivity activity;

    public ViewControllerPagerAdapter(UIActivity activity) {
        this.activity = activity;
    }

    /**
     * Returns the ViewController instance at a given position.
     * A subclass needs to create and return this instance. But
     * the subclass does not have to do anything to add the controller's view
     * to the hierarchy or manage the controller's lifecycle.
     *
     * @param position The index for which a ViewController instance needs to be returned. This
     *                 method may get called repeatedly as the user scrolls through the ViewPager.
     *                 A subclass of this class should, in most cases, attempt to return the
     *                 same instance for a given position. That is, instances can be safely reused.
     * @return A ViewController instance.
     */
    abstract public ViewController getItem(int position);

    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewController controller = (ViewController) object;

        return controller.getView() == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewController controller = getItem(position);

        getUIActivity().addToViewHierarchyOfContainer(container, controller, null);

        return controller;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewController controller = (ViewController) object;

        getUIActivity().removeFromViewHierarchyOfContainer(container, controller);
    }

    public UIActivity getUIActivity() {
        return activity;
    }
}
