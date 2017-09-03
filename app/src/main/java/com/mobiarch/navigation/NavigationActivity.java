package com.mobiarch.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends Activity {
    LinearLayout rootView;
    ArrayDeque<ViewController> viewControllers = new ArrayDeque<>();
    boolean isFirstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        rootView = (LinearLayout) findViewById(R.id.rootView);

        isFirstLaunch = true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isFirstLaunch == false) {
            /*
            Activity is coming to foreground but not
            from the initial launch. Which means this block
            will match a corresponding onStop call made earlier.

            Show the top most view controller with lifecycle.
            */
            presentOnScreen(getTopViewController());
        }

        isFirstLaunch = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Activity has become invisible. Invoke lifecycle
        //of the top view controller.
        removeFromScreen(getTopViewController());
    }

    public void pushViewController(ViewController viewController, boolean animated) {
        //Call viewWillDisappear for the currently visible view controller.
        ViewController currentTop = viewControllers.peek();

        removeFromScreen(currentTop);

        viewControllers.push(viewController);

        presentOnScreen(viewController);

        onNavigationCompleted();
    }

    public void popViewController(boolean animated) {
        ViewController currentTop = viewControllers.pop();

        removeFromScreen(currentTop);

        currentTop = viewControllers.peek();

        presentOnScreen(currentTop);

        onNavigationCompleted();
    }

    public List<ViewController> getViewControllers() {
        return new ArrayList<>(viewControllers);
    }

    public void setViewControllers(List<ViewController> list) {
        //Clear the top most one with lifecycle
        ViewController currentTop = viewControllers.pop();

        removeFromScreen(currentTop);

        viewControllers.clear();

        viewControllers.addAll(list);

        //Show the top most
        currentTop = viewControllers.peek();

        presentOnScreen(currentTop);

        onNavigationCompleted();
    }

    public void popToRootViewController(boolean animated) {
        if (viewControllers.size() < 2) {
            return; //Nothing to do
        }

        //Clear the top most one with lifecycle
        ViewController currentTop = viewControllers.pop();

        removeFromScreen(currentTop);

        while (viewControllers.size() > 1) {
            viewControllers.pop();
        }

        //Show the top most
        currentTop = viewControllers.peek();

        presentOnScreen(currentTop);

        onNavigationCompleted();
    }

    @Override
    public void onBackPressed() {
        if (viewControllers.size() <= 1) {
            super.onBackPressed();
        } else {
            popViewController(true);
        }
    }

    protected void onNavigationCompleted() {
        getActionBar().setDisplayHomeAsUpEnabled(
                viewControllers.size() > 1);
        getActionBar().setHomeButtonEnabled(
                viewControllers.size() > 1);

        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ViewController currentTop = viewControllers.peek();

        if (currentTop != null) {
            if (currentTop.onOptionsItemSelected(item)) {
                return true;
            }
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ViewController currentTop = viewControllers.peek();

        if (currentTop != null) {
            if (currentTop.getOptionMenuResourceId() != null) {
                MenuInflater inflater = getMenuInflater();

                inflater.inflate(currentTop.getOptionMenuResourceId(), menu);

                return true;
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    public ViewController getTopViewController() {
        return viewControllers.peek();
    }

    protected void loadViewIfNeeded(ViewController viewController) {
        if (viewController.getView() == null) {
            View v = getLayoutInflater().inflate(viewController.getLayoutResourceId(), null);

            viewController.setView(v);
            viewController.viewDidLoad();
        }
    }

    protected void presentOnScreen(ViewController viewController) {
        if (viewController != null) {
            loadViewIfNeeded(viewController);
            viewController.setNavigationActivity(this);
            viewController.viewWillAppear();

            //Add the view
            rootView.addView(viewController.getView());

            viewController.viewDidAppear();
        }
    }

    protected void removeFromScreen(ViewController viewController) {
        if (viewController != null) {

            if (viewController.getView() == null) {
                throw new IllegalStateException("The view for this view controller was never created.");
            }

            viewController.viewWillDisappear();

            viewController.setNavigationActivity(null);
            rootView.removeView(viewController.getView());

            viewController.viewDidDisappear();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ViewController controller = getTopViewController();

        if (controller != null) {
            controller.onActivityResult(requestCode, resultCode, data);
        }
    }
}
