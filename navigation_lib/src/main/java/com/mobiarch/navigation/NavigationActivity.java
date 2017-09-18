package com.mobiarch.navigation;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>NavigationActivity hosts a stack of ViewController objects.
 * It is analogous to UIViewController in iOS.</p>
 *
 * <p>You will need to subclass this class. From the onCreate() method
 * push the root view controller.</p>
 */
public class NavigationActivity extends AppCompatActivity {
    ViewGroup navigationContainerView;
    ArrayDeque<ArrayDeque<ViewController>> stackOfStacks = new ArrayDeque<>();
    boolean isFirstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceID());

        navigationContainerView = (ViewGroup) findViewById(R.id.navigationContainerView);

        //Create the first stack
        stackOfStacks.push(new ArrayDeque<ViewController>());

        isFirstLaunch = true;
    }

    /**
     * Subclasses can supply a custom layout resource by overriding this method.
     * The layout should either have a ViewGroup with ID navigationContainerView or
     * the subclass must set a container view by calling setNavigationContainerView.
     *
     * @return A custom layout resource for the navigation activity.
     */
    public int getLayoutResourceID() {
        return R.layout.navigation_activity;
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
            addToViewHierarchy(getTopViewController());
        }

        isFirstLaunch = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Activity has become invisible. Invoke lifecycle
        //of the top view controller.
        removeFromViewHierarchyOfContainer(getTopViewController());
    }

    /**
     * Pushes a view controller to the stack. It can be called from anywhere
     * in the lifecycle of the activity. For example, it is safe to call this
     * from the onCreate() method.
     *
     * @param viewController The view controller to be pushed to the top of the stack.
     * @param animated If animation should be used to present the view controller.
     */
    public void pushViewController(ViewController viewController, boolean animated) {
        final ViewController lastTopController = currentStack().peek();

        currentStack().push(viewController);

        addToViewHierarchy(viewController);

        if (animated) {
            viewController.getView().setTranslationX(getNavigationContainerView().getWidth());
            viewController.getView().animate().translationX(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //Remove the last top controller with lifecycle
                    removeFromViewHierarchyOfContainer(lastTopController);

                    onNavigationCompleted();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            }).start();
        } else {
            //Remove the last top controller with lifecycle
            removeFromViewHierarchyOfContainer(lastTopController);

            onNavigationCompleted();
        }
    }

    /**
     * The top most view controller is removed from the stack. This method
     * is also called automatically when user taps the system back button or
     * the back button in the actionbar.
     *
     * @param animated If animation should be used to remove the view.
     */
    public void popViewController(boolean animated) {
        final ViewController lastTopController = currentStack().pop();

        //Add the previous controller but below the current one
        addToViewHierarchy(currentStack().peek(), 0);

        if (animated) {
            lastTopController.getView().animate().translationX(getNavigationContainerView().getWidth()).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //Remove the last top controller with lifecycle
                    removeFromViewHierarchyOfContainer(lastTopController);
                    onNavigationCompleted();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        } else {
            //Remove the last top controller with lifecycle
            removeFromViewHierarchyOfContainer(lastTopController);
            onNavigationCompleted();
        }
    }

    /**
     * Get a list of all view controllers in the stack.
     *
     * @return
     */
    public List<ViewController> getViewControllers() {
        return new ArrayList<>(currentStack());
    }

    /**
     * Set the view controllers in the stack. Any existing view controllers
     * in the stack are removed.
     *
     * @param list The new list of view controllers.
     */
    public void setViewControllers(List<ViewController> list) {
        //Clear the top most one with lifecycle
        ViewController currentTop = currentStack().pop();

        removeFromViewHierarchyOfContainer(currentTop);

        currentStack().clear();

        currentStack().addAll(list);

        //Show the top most
        currentTop = currentStack().peek();

        addToViewHierarchy(currentTop);

        onNavigationCompleted();
    }

    /**
     * Removes all view controllers from the stack except for the bottom most
     * one.
     *
     * @param animated
     */
    public void popToRootViewController(boolean animated) {
        if (currentStack().size() < 2) {
            return; //Nothing to do
        }

        //Clear the top most one with lifecycle
        ViewController currentTop = currentStack().pop();

        removeFromViewHierarchyOfContainer(currentTop);

        while (currentStack().size() > 1) {
            currentStack().pop();
        }

        //Show the top most
        currentTop = currentStack().peek();

        addToViewHierarchy(currentTop);

        onNavigationCompleted();
    }

    /**
     * Opens a view controller in a popup modal. This creates a new
     * navigation stack. Calling pushViewController() after this will
     * add a view controller to this new stack.
     *
     * @param viewController The view controller to open modally.
     * @param animated
     */
    public void presentViewController(ViewController viewController, boolean animated) {
        //Remove the currently visible controller with lifecycle
        final ViewController lastTopController = getTopViewController();

        //Create a new navigation stack
        stackOfStacks.push(new ArrayDeque<ViewController>());

        currentStack().push(viewController);

        addToViewHierarchy(viewController);

        if (animated) {
            viewController.getView().setTranslationY(getNavigationContainerView().getHeight());
            viewController.getView().animate().translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    removeFromViewHierarchyOfContainer(lastTopController);
                    onNavigationCompleted();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        } else {
            removeFromViewHierarchyOfContainer(lastTopController);
            onNavigationCompleted();
        }
    }

    /**
     * Closes the top most modally open view controller. This also removes the
     * top most navigation stack.
     *
     * @param animated
     */
    public void dismissViewController(boolean animated) {
        if (stackOfStacks.size() <= 1) {
            return; //Nothing is presented
        }

        final ViewController lastTopController = currentStack().pop();

        //Get rid of the entire topmost navigation stack
        stackOfStacks.pop();

        //Show the view of the previous stack but below the current view
        addToViewHierarchy(getTopViewController(), 0);

        if (animated) {
            lastTopController.getView().animate().translationY(getNavigationContainerView().getHeight()).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //Remove the visible controller with lifecycle
                    removeFromViewHierarchyOfContainer(lastTopController);

                    onNavigationCompleted();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        } else {
            //Remove the visible controller with lifecycle
            removeFromViewHierarchyOfContainer(lastTopController);

            onNavigationCompleted();
        }
    }

    @Override
    public void onBackPressed() {
        if (currentStack().size() <= 1) {
            /*
            We have only one controller left in the stack.
            If we are presenting a modal, get rid of it and its stack.
            Otherwise close the whole activity.
             */
            if (stackOfStacks.size() > 1) {
                //We are presenting a modal
                dismissViewController(true);
            } else {
                super.onBackPressed();
            }
        } else {
            popViewController(true);
        }
    }

    protected void onNavigationCompleted() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(
                currentStack().size() > 1);
        getSupportActionBar().setHomeButtonEnabled(
                currentStack().size() > 1);

        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ViewController currentTop = currentStack().peek();

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
        ViewController currentTop = currentStack().peek();

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
        return currentStack().peek();
    }

    protected void loadViewIfNeeded(ViewController viewController) {
        if (viewController.getView() == null) {
            View v = getLayoutInflater().inflate(viewController.getLayoutResourceId(), null);

            viewController.setView(v);
            viewController.viewDidLoad();
        }
    }

    public void addToViewHierarchy(ViewController viewController) {
        addToViewHierarchy(viewController, null);
    }

    public void addToViewHierarchy(ViewController viewController, Integer index) {
        addToViewHierarchyOfContainer(getNavigationContainerView(), viewController, index);
    }

    public void addToViewHierarchyOfContainer(ViewGroup container, ViewController viewController, Integer index) {
        if (viewController != null) {
            if (viewController.getNavigationActivity() != null) {
                throw new IllegalStateException("This view controller is already presented on screen.");
            }

            loadViewIfNeeded(viewController);
            viewController.setNavigationActivity(this);
            viewController.viewWillAppear();

            //Add the view
            if (index == null) {
                container.addView(viewController.getView());
            } else {
                container.addView(viewController.getView(), index);
            }

            viewController.viewDidAppear();
        }
    }

    public void removeFromViewHierarchyOfContainer(ViewController viewController) {
        removeFromViewHierarchyOfContainer(getNavigationContainerView(), viewController);
    }

    public void removeFromViewHierarchyOfContainer(ViewGroup container, ViewController viewController) {
        if (viewController != null) {

            if (viewController.getView() == null) {
                throw new IllegalStateException("The view for this view controller was never created.");
            }

            viewController.viewWillDisappear();

            viewController.setNavigationActivity(null);
            container.removeView(viewController.getView());

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

    protected ArrayDeque<ViewController> currentStack() {
        return stackOfStacks.peek();
    }

    public ViewGroup getNavigationContainerView() {
        return navigationContainerView;
    }

    public void setNavigationContainerView(ViewGroup navigationContainerView) {
        this.navigationContainerView = navigationContainerView;
    }
}
