package com.example.azizrafsanjani.numericals.utils;


import android.support.transition.Fade;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;


/**
 * Created by Aziz Rafsanjani on 11/4/2017.
 */

public final class Utilities {

    public static final String Log = "Log";

   public static void replaceFragment(Fragment current, Fragment next, FragmentManager fragmentManager, int containerViewId){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fade enterFade = new Fade();
        enterFade.setDuration(500);

        next.setEnterTransition(enterFade);
        transaction.replace(containerViewId, next);
        transaction.commit();
    }

    public static void loadFragment(Fragment fragment, FragmentManager fragmentManager, int containerViewId){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId,  fragment);
        fragmentTransaction.commit();
    }

    public static void animateAnswer(TextView answerView, ViewGroup viewGroup, DisplayMode displayMode) {

        switch (displayMode){
            case SHOW:
                TransitionSet set = new TransitionSet()
                        .addTransition(new Scale(0.3f))
                        .addTransition(new com.transitionseverywhere.Fade())
                        .setInterpolator(new LinearOutSlowInInterpolator());

                TransitionManager.beginDelayedTransition(viewGroup, set);
                answerView.setVisibility(View.VISIBLE);
                break;

            case HIDE:
                TransitionManager.beginDelayedTransition(viewGroup);
                answerView.setVisibility(View.GONE);
                break;
        }
    }

    public enum DisplayMode{
        SHOW,
        HIDE
    }
}
