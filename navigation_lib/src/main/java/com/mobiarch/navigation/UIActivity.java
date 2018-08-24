package com.mobiarch.navigation;

import android.animation.Animator;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayDeque;

/**
 * <p>UIActivity hosts a stack of UIViewController objects.
 * It is analogous to UIViewController in iOS.</p>
 *
 * <p>You will need to subclass this class. From the onCreate() method
 * push the root view controller.</p>
 */
public class UIActivity extends AppCompatActivity {
    ViewGroup containerView;
    ArrayDeque<UIViewController> stack = new ArrayDeque<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceID());

        containerView = (ViewGroup) findViewById(R.id.navigationContainerView);
    }

    public void setRootViewController(UIViewController c) {

        while (stack.size() > 0) {
            UIViewController topController = stack.pop();

            if (getLifecycle().getCurrentState() == Lifecycle.State.STARTED || getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
                removeFromViewHierarchy(topController);
            }
        }

        stack.push(c);

        if (getLifecycle().getCurrentState() == Lifecycle.State.STARTED || getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {
            addToViewHierarchy(c);
        }
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

        if (stack.size() > 0) {
            addToViewHierarchy(getTopViewController());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        //Activity has become invisible. Invoke lifecycle
        //of the top view controller.
        removeFromViewHierarchy(getTopViewController());
    }


    /**
     * Opens a view controller in a popup modal. This creates a new
     * navigation stack. Calling pushViewController() after this will
     * add a view controller to this new stack.
     *
     * @param viewController The view controller to open modally.
     * @param animated
     */
    public void presentViewController(UIViewController viewController, boolean animated) {
        //Remove the currently visible controller with lifecycle
        final UIViewController lastTopController = getTopViewController();

        stack.push(viewController);

        addToViewHierarchy(viewController);

        if (animated) {
            viewController.getView().setTranslationY(getContainerView().getHeight());
            viewController.getView().animate().translationY(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    removeFromViewHierarchy(lastTopController);
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
            removeFromViewHierarchy(lastTopController);
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
        if (stack.size() <= 1) {
            return; //Nothing is presented
        }

        final UIViewController lastTopController = stack.pop();

        //Show the view of the previous stack but below the current view
        addToViewHierarchy(getTopViewController(), 0);

        if (animated) {
            lastTopController.getView().animate().translationY(getContainerView().getHeight()).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //Remove the visible controller with lifecycle
                    removeFromViewHierarchy(lastTopController);

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
            removeFromViewHierarchy(lastTopController);

            onNavigationCompleted();
        }
    }

    @Override
    public void onBackPressed() {
        if (stack.size() > 1) {
            //We are presenting a modal
            if (!getTopViewController().onBackPressed()) {
                dismissViewController(true);
            }
        } else {
            if (!getTopViewController().onBackPressed()) {
                super.onBackPressed();
            }
        }
    }

    protected void onNavigationCompleted() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(
                stack.size() > 1);
        getSupportActionBar().setHomeButtonEnabled(
                stack.size() > 1);

        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        UIViewController currentTop = stack.peek();

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
        UIViewController currentTop = stack.peek();

        if (currentTop != null) {
            if (currentTop.getOptionMenuResourceId() != null) {
                MenuInflater inflater = getMenuInflater();

                inflater.inflate(currentTop.getOptionMenuResourceId(), menu);

                return true;
            }
        }

        return super.onCreateOptionsMenu(menu);
    }

    public UIViewController getTopViewController() {
        return stack.peek();
    }

    protected void loadViewIfNeeded(UIViewController viewController) {
        if (viewController.getView() == null) {
            View v = getLayoutInflater().inflate(viewController.getLayoutResourceId(), null);

            viewController.setView(v);
            viewController.viewDidLoad();
        }
    }

    private void addToViewHierarchy(UIViewController viewController) {
        addToViewHierarchy(viewController, null);
    }

    private void addToViewHierarchy(UIViewController viewController, Integer index) {
        addToViewHierarchyOfContainer(getContainerView(), viewController, index);
    }

    public void addToViewHierarchyOfContainer(ViewGroup container, UIViewController viewController, Integer index) {
        if (viewController != null) {
            if (viewController.getActivity() != null) {
                throw new IllegalStateException("This view controller is already presented on screen.");
            }

            loadViewIfNeeded(viewController);
            viewController.setActivity(this);
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

    private void removeFromViewHierarchy(UIViewController viewController) {
        removeFromViewHierarchyOfContainer(getContainerView(), viewController);
    }

    public void removeFromViewHierarchyOfContainer(ViewGroup container, UIViewController viewController) {
        if (viewController != null) {

            if (viewController.getView() == null) {
                throw new IllegalStateException("The view for this view controller was never created.");
            }

            viewController.viewWillDisappear();

            viewController.setActivity(null);
            container.removeView(viewController.getView());

            viewController.viewDidDisappear();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        UIViewController controller = getTopViewController();

        if (controller != null) {
            controller.onActivityResult(requestCode, resultCode, data);
        }
    }

    public ViewGroup getContainerView() {
        return containerView;
    }

    public void setNavigationContainerView(ViewGroup v) {
        this.containerView = v;
    }

    protected ArrayDeque<UIViewController> getStack() {
        return stack;
    }
}
