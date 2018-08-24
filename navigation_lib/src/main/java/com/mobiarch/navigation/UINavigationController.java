package com.mobiarch.navigation;

import android.animation.Animator;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class UINavigationController extends UIViewController {
    ArrayDeque<UIViewController> stack = new ArrayDeque<>();

    public UINavigationController(UIViewController rootViewController) {
        super(R.layout.navigation_controller);

        stack.push(rootViewController);
    }

    /**
     * Get a list of all view controllers in the stack.
     *
     * @return
     */
    public List<UIViewController> getViewControllers() {
        return new ArrayList<>(stack);
    }

    /**
     * Set the view controllers in the stack. Any existing view controllers
     * in the stack are removed.
     *
     * @param list The new list of view controllers.
     */
    public void setViewControllers(List<UIViewController> list) {
        //Clear the top most one with lifecycle
        getTopViewController().removeFromParent(getContainer());

        stack.clear();

        stack.addAll(list);

        //Show the top most
        addChild(getTopViewController(), getContainer(), null);
    }

    /**
     * Pushes a view controller to the stack. It can be called from anywhere
     * in the lifecycle of the activity. For example, it is safe to call this
     * from the onCreate() method.
     *
     * @param viewController The view controller to be pushed to the top of the stack.
     * @param animated If animation should be used to present the view controller.
     */
    public void pushViewController(UIViewController viewController, boolean animated) {
        final UIViewController lastTopController = stack.peek();

        stack.push(viewController);

        addChild(viewController, getContainer(), null);

        if (animated) {
            viewController.getView().setTranslationX(getContainer().getWidth());
            viewController.getView().animate().translationX(0).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //Remove the last top controller with lifecycle
                    lastTopController.removeFromParent(getContainer());
//                    onNavigationCompleted();
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
            lastTopController.removeFromParent(getContainer());
//            onNavigationCompleted();
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
        final UIViewController lastTopController = stack.pop();

        //Add the previous controller but below the current one
        addChild(stack.peek(), getContainer(), 0);

        if (animated) {
            lastTopController.getView().animate().translationX(getContainer().getWidth()).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //Remove the last top controller with lifecycle
                    lastTopController.removeFromParent(getContainer());

//                    onNavigationCompleted();
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
            lastTopController.removeFromParent(getContainer());
//            onNavigationCompleted();
        }
    }
    /**
     * Removes all view controllers from the stack except for the bottom most
     * one.
     *
     * @param animated
     */
    public void popToRootViewController(boolean animated) {
        if (stack.size() < 2) {
            return; //Nothing to do
        }

        //Clear the top most one with lifecycle
        getTopViewController().removeFromParent(getContainer());

        while (stack.size() > 1) {
            stack.pop();
        }

        //Show the top most
        addChild(getTopViewController(), getContainer(), null);

        //onNavigationCompleted();
    }


    public UIViewController getTopViewController() {
        return stack.peek();
    }

    public ViewGroup getContainer() {
        return (ViewGroup) getView();
    }

    @Override
    public void viewWillAppear() {
        super.viewWillAppear();

        addChild(getTopViewController(), getContainer(), null);
    }

    @Override
    public void viewWillDisappear() {
        super.viewWillDisappear();

        getTopViewController().removeFromParent(getContainer());
    }

    @Override
    public boolean onBackPressed() {
        if (stack.size() > 1) {
            popViewController(true);

            return true;
        }

        return super.onBackPressed();
    }
}
