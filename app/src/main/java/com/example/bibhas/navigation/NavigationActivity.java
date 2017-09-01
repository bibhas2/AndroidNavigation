package com.example.bibhas.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayDeque;
import java.util.List;

public class NavigationActivity extends Activity {
    LinearLayout rootView;
    ArrayDeque<ViewController> viewControllers = new ArrayDeque<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        rootView = (LinearLayout) findViewById(R.id.rootView);
    }

    public void pushViewController(ViewController viewController, boolean animated) {
        if (viewController.getView() == null) {
            View v = getLayoutInflater().inflate(viewController.getLayoutResourceId(), null);

            viewController.setView(v);
            viewController.viewDidLoad();
        }

        //Call viewWillDisappear for the currently visible view controller.
        ViewController currentTop = viewControllers.peek();

        if (currentTop != null) {
            currentTop.viewWillDisappear();

            currentTop.setNavigationActivity(null);
            rootView.removeView(currentTop.getView());

            currentTop.viewDidDisappear();
        }

        viewController.setNavigationActivity(this);
        viewController.viewWillAppear();

        //Add the view
        viewControllers.push(viewController);
        rootView.addView(viewController.getView());

        viewController.viewDidAppear();

        manageActionbarBackButton();
    }

    public void popViewController(boolean animated) {
        ViewController currentTop = viewControllers.peek();

        if (currentTop != null) {
            currentTop.viewWillDisappear();

            currentTop.setNavigationActivity(null);
            viewControllers.pop();
            rootView.removeView(currentTop.getView());

            currentTop.viewDidDisappear();
        }

        currentTop = viewControllers.peek();

        if (currentTop != null) {
            currentTop.setNavigationActivity(this);
            currentTop.viewWillAppear();

            //Add the view
            rootView.addView(currentTop.getView());

            currentTop.viewDidAppear();
        }

        manageActionbarBackButton();
    }

    public void setViewControllers(List<ViewController> list) {
        //Clear the top most one with lifecycle
        ViewController currentTop = viewControllers.peek();

        if (currentTop != null) {
            currentTop.viewWillDisappear();

            currentTop.setNavigationActivity(null);
            viewControllers.pop();
            rootView.removeView(currentTop.getView());

            currentTop.viewDidDisappear();
        }

        viewControllers.clear();

        viewControllers.addAll(list);

        //Show the top most
        currentTop = viewControllers.peek();

        if (currentTop != null) {
            currentTop.setNavigationActivity(this);
            currentTop.viewWillAppear();

            //Add the view
            rootView.addView(currentTop.getView());

            currentTop.viewDidAppear();
        }

        manageActionbarBackButton();
    }

    @Override
    public void onBackPressed() {
        if (viewControllers.size() <= 1) {
            super.onBackPressed();
        } else {
            popViewController(true);
        }
    }

    public ArrayDeque<ViewController> getViewControllers() {
        return viewControllers;
    }

    private void manageActionbarBackButton() {
        getActionBar().setDisplayHomeAsUpEnabled(
                viewControllers.size() > 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
