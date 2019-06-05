package com.foreverrafs.numericals.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;

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
        //Fade enterFade = new Fade();
        //enterFade.setDuration(500);

        //next.setEnterTransition(enterFade);
        transaction.replace(containerViewId, next);
        transaction.commit();
    }

    public static void setTypeFace(View view, Context mCtx, TypeFaceName typeFaceName) {
        //cast the view to a TextView, if casting fails then we cast to an edittext and apply the necessary font
        Typeface typeface = null;

        try {
            typeface = Typeface.createFromAsset(mCtx.getAssets(), "fonts/" + typeFaceName.toString() + ".ttf");
            TextView tv = (TextView) view;
            tv.setTypeface(typeface);
        } catch (ClassCastException exception) {
            EditText editText = (EditText) view;
            editText.setTypeface(typeface);
        } catch (Exception exception) {
            android.util.Log.e(LOG_TAG, exception.getMessage());
        }
    }

    public static void setTypeFace(View view, Context mCtx, String typeFaceName) {
        //cast the view to a TextView, if casting fails then we cast to an edittext and apply the necessary font
        Typeface typeface = null;

        try {
            typeface = Typeface.createFromAsset(mCtx.getAssets(), "fonts/" + typeFaceName + ".ttf");
            TextView tv = (TextView) view;
            tv.setTypeface(typeface);
        } catch (ClassCastException exception) {
            EditText editText = (EditText) view;
            editText.setTypeface(typeface);
        } catch (Exception exception) {
            android.util.Log.e(LOG_TAG, exception.getMessage());
        }
    }

    public static void replaceFragment(Fragment next, FragmentManager fragmentManager,
                                       int containerViewId) {
        String name = fragmentManager.getClass().getSimpleName();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fade enterFade = new Fade();
        enterFade.setDuration(300);
        //Fade enterFade = new Fade();
        //enterFade.setDuration(500);

        next.setEnterTransition(enterFade);
        transaction.replace(containerViewId, next);
        transaction.commit();
    }

    public static void replaceFragment(Context mCtx, Fragment next, FragmentManager
            fragmentManager, int containerViewId) {
        if (mCtx.getClass().getSimpleName().equals("MainActivity")) {
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
        android.util.Log.e(LOG_TAG, "Only MainActivity can process fragments");
    }

    public static void loadFragment(Fragment fragment, FragmentManager fragmentManager,
                                    int containerViewId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
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

    private void animateButtonDrawable(ImageButton target, Drawable toDrawable) {
        target.setImageDrawable(toDrawable);
        final Animatable animatable = (Animatable) target.getDrawable();
        animatable.start();
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
