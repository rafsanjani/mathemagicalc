package com.example.azizrafsanjani.numericals.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azizrafsanjani.numericals.R;

/**
 * Created by Aziz Rafsanjani on 11/2/2017.
 */

public class CustomDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_source_code, container, false);
    }

    private String message;
    public void setMessage(String msg){
        this.message = msg;
    }
}
