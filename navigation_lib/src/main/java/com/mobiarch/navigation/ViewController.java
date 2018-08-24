package com.mobiarch.navigation;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

/**
 * A ViewController is a much simpler and easier to reason with
 * alternative to Fragments. It has a few advantages:
 *
 * <ul>
 *     <li>The lifecycle is compatible with UIViewController of iOS. This
 *     makes porting iOS code a breeze.</li>
 *     <li>You can directly supply data to a ViewController constructor and
 *     call any of its methods from other parts of the application. This is not easy to do in
 *     Fragment where the actual Fragment instance is not readily accessible. This
 *     will speed up development.
 *     </li>
 * </ul>
 */
public class ViewController {
    private String title;
    private View view;
    private UIActivity activity;
    private int layoutResourceId;
    private Integer optionMenuResourceId;
    private UINavigationController navigationController;

    /**
     * Create a new instance of a ViewController.
     *
     * @param layoutResourceId - The layout resource ID for the controller.
     *
     */
    public ViewController(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }

    public void setView(View view) {
        this.view = view;
    }

    /**
     * Get the root view of the controller.
     *
     * @return
     */
    public View getView() {
        return view;
    }

    public UIActivity getActivity() {
        return activity;
    }

    public void setActivity(UIActivity activity) {
        this.activity = activity;
    }

    public int getLayoutResourceId() {
        return layoutResourceId;
    }

    /**
     * viewDidLoad() gets called only once per lifetime of a view controller
     * instance. This is called right after the view has been created from layout.
     * The view has not yet been added to the view hierarchy. Also the activity
     * has not been set yet. This is consistent with the behavior in iOS.
     */
    public void viewDidLoad() {

    }

    /**
     * viewWillAppear() is called just before the view is added to the hierarchy.
     * The activity for the controller has been set at this point. So you can for example
     * manipulate the action bar from here.
     *
     * This is called when the controller is first pushed to stack and when the activity comes
     * to foreground.
     */
    public void viewWillAppear() {

    }

    /**
     * viewDidAppear() is called after the view is added to the view hierarchy
     * of the activity. It should be fully visible at ths point.
     *
     * This is called when the controller is first pushed to stack and when the activity comes
     * to foreground.
     */
    public void viewDidAppear() {

    }

    /**
     * viewWillDisappear() is called just before the view of the controller
     * is removed from the view hierarchy. You have access to the activity from here.
     */
    public void viewWillDisappear() {

    }

    /**
     * viewDidDisappear() is called after the view has been removed from the view
     * hierarchy of the activity. The controller no longer has any access to the activity
     * (ie., getActivity() will return null from here.
     */
    public void viewDidDisappear() {

    }

    /**
     * Every time a controller is presented on screen this method is called
     * to set the option menu of the activity.
     *
     * @return The menu resource ID for this controller. Return null
     * if the controller has no option menu.
     */
    public Integer getOptionMenuResourceId() {
        return optionMenuResourceId;
    }

    /**
     * This sets the option menu resource ID for the controller. Next time
     * the controller is presented on screen an option menu will be created for
     * the controller.
     *
     * @param optionMenuResourceId
     */
    public void setOptionMenuResourceId(Integer optionMenuResourceId) {
        this.optionMenuResourceId = optionMenuResourceId;
    }

    /**
     * This method is called when a user selects an item from the option menu.
     *
     * @param item - The item thatis selected.
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    /**
     * This method is called for the top most controller on the stack when an activity
     * launched from this activity ends with result.
     *
     * Generally only the top most controller should launch another activity. In that
     * case the result is reported back to the same controller that launched the activity.
     * If for some reason the current top most controller did not launch the activity then it is
     * possible that the result will be reported to the wrong controller. To be safe,
     * always check the requestCode before taking any action.
     *
     * @param requestCode The code used to launch the activity.
     * @param resultCode The result code from the launched activity.
     * @param data Output data from the launched activity.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public boolean onBackPressed() {
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UINavigationController getNavigationController() {
        return navigationController;
    }

    public void setNavigationController(UINavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
