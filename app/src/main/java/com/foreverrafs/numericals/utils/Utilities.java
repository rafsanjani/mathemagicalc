package com.foreverrafs.numericals.utils;


import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.activities.ShowAlgorithm;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public final class Utilities {

    public static final String LOG_TAG = "TAG";

    public static void replaceFragment(Fragment next, FragmentManager fragmentManager, int containerViewId, boolean isGoingBack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!isGoingBack)
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        else
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        //next.setEnterTransition(enterFade);
        transaction.replace(containerViewId, next);
        transaction.commit();
    }

//    public static void setTypeFace(View view, Context mCtx, TypeFaceName typeFaceName) {
//        //cast the view to a TextView, if casting fails then we cast to an edittext and apply the necessary font
//        Typeface typeface = null;
//
//        try {
//            typeface = Typeface.createFromAsset(mCtx.getAssets(), "fonts/" + typeFaceName.toString() + ".ttf");
//            TextView tv = (TextView) view;
//            tv.setTypeface(typeface);
//        } catch (ClassCastException exception) {
//            EditText editText = (EditText) view;
//            editText.setTypeface(typeface);
//        } catch (Exception exception) {
//            android.util.Log.e(LOG_TAG, exception.getMessage());
//        }
//    }

//    public static void setTypeFace(View view, Context mCtx, String typeFaceName) {
//        //cast the view to a TextView, if casting fails then we cast to an edittext and apply the necessary font
//        Typeface typeface = null;
//
//        try {
//            typeface = Typeface.createFromAsset(mCtx.getAssets(), "fonts/" + typeFaceName + ".ttf");
//            TextView tv = (TextView) view;
//            tv.setTypeface(typeface);
//        } catch (ClassCastException exception) {
//            EditText editText = (EditText) view;
//            editText.setTypeface(typeface);
//        } catch (Exception exception) {
//            android.util.Log.e(LOG_TAG, exception.getMessage());
//        }
//    }

    public static void replaceFragment(Fragment next, FragmentManager fragmentManager,
                                       int container) {
        String name = fragmentManager.getClass().getSimpleName();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fade enterFade = new Fade();
        enterFade.setDuration(300);

        next.setEnterTransition(enterFade);
        transaction.replace(container, next);
        transaction.commit();
    }

    public static void replaceFragment(Context mCtx, Fragment next, FragmentManager
            fragmentManager, int containerViewId) {
        if (mCtx.getClass().getSimpleName().equals("MainMenuActivity")) {
            String name = fragmentManager.getClass().getSimpleName();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fade enterFade = new Fade();
            enterFade.setDuration(300);
            //Fade enterFade = new Fade();
            //enterFade.setDuration(500);

            next.setEnterTransition(enterFade);
            transaction.replace(containerViewId, next);
            transaction.commit();
            return;
        }
        android.util.Log.e(LOG_TAG, "Only MainMenuActivity can process fragments");
    }

    public static void loadFragment(Fragment fragment, FragmentManager fragmentManager,
                                    int containerViewId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    public static void animateTextViewColorAndAlpha(Context mCtx, TextView textView) {
        AnimatorSet animatorSet = new AnimatorSet();

        Integer colorFrom = textView.getCurrentTextColor();
        Integer colorTo = mCtx.getResources().getColor(R.color.black);

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        ValueAnimator alphaAnimation = ValueAnimator.ofFloat(textView.getAlpha(), 0.2f);

        colorAnimation.addUpdateListener(animator -> textView.setTextColor((Integer) animator.getAnimatedValue()));
        alphaAnimation.addUpdateListener(animator -> textView.setAlpha((Float) animator.getAnimatedValue()));

        animatorSet.playTogether(alphaAnimation, colorAnimation);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }


    public static void animateAnswer(View answerView, ViewGroup viewGroup, DisplayMode
            displayMode) {

        switch (displayMode) {
            case SHOW:
                TransitionSet set = new TransitionSet()
                        .addTransition(new Fade())
                        .setInterpolator(new LinearOutSlowInInterpolator());

                TransitionManager.beginDelayedTransition(viewGroup);
                answerView.setVisibility(View.VISIBLE);
                //  TransitionManager.beginDelayedTransition(parentContainer, set);

                break;

            case HIDE:
                TransitionManager.beginDelayedTransition(viewGroup);
                answerView.setVisibility(View.GONE);
                break;
        }
    }

    public static void showAlgorithmScreen(Context c, String algoName) {
        if (algoName.isEmpty())
            algoName = "index";
        Bundle bundle = new Bundle();
        bundle.putString("algorithm_name", algoName);

        c.startActivity(new Intent(c, ShowAlgorithm.class).putExtras(bundle));
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public enum DisplayMode {
        SHOW,
        HIDE
    }

    public enum TypeFaceName {
        //TypeFace should be the exact name of file stored in assets/fonts without the extension
        raleway_bold,
        falling_sky,
        bitter_italic,
        philosopher_bold,
    }
}

/*
    LATEX TEXTS
    JACOBI'S METHOD
    $$x^{(k)}_{1} = \frac{1}{a_{11}}[b_{1} - (a_{12}x^{(k-1)}_{2})+a_{13}x^{(k-1)}_{3}] \newline\newline

    x^{(k)}_{2} = \frac{1}{a_{22}}[b_{2} - (a_{21}x^{(k-1)}_{1})+a_{23}x^{(k-1)}_{3}] \newline\newline

    x^{(k)}_{3} = \frac{1}{a_{33}}[b_{3} - (a_{31}x^{(k-1)}_{1})+a_{32}x^{(k-1)}_{2}]

    GAUSS SEIDEL'S METHOD
 */
