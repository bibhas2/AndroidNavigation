package com.example.bibhas.navigation;

import android.view.View;

public class ViewController {
    private View view;
    private NavigationActivity activity;
    private int layoutResourceId;

    public ViewController(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public NavigationActivity getNavigationActivity() {
        return activity;
    }

    public void setNavigationActivity(NavigationActivity activity) {
        this.activity = activity;
    }

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    public void viewDidLoad() {

    }

    public void viewWillAppear() {

    }

    public void viewDidAppear() {

    }

    public void viewWillDisappear() {

    }

    public void viewDidDisappear() {

    }
}
