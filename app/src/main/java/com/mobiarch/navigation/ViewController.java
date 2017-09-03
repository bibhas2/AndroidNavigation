package com.mobiarch.navigation;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

public class ViewController {
    private View view;
    private NavigationActivity activity;
    private int layoutResourceId;
    private Integer optionMenuResourceId;

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

    public Integer getOptionMenuResourceId() {
        return optionMenuResourceId;
    }

    public void setOptionMenuResourceId(Integer optionMenuResourceId) {
        this.optionMenuResourceId = optionMenuResourceId;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
}
