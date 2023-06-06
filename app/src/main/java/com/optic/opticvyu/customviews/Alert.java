package com.optic.opticvyu.customviews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;


import com.optic.interior.R;
import com.optic.opticvyu.Constants;
import com.optic.opticvyu.LoginScreenActivity;

/**
 * Created by optimum2 on 5/12/17.
 */

public class Alert {
    Dialog dialog;

    @SuppressLint("NotConstructor")
    public void Alert(Context context, String alert, String message) {


        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);
        // Setting Dialog Title
        alertDialog.setTitle(alert);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                context.startActivity(new Intent(context, LoginScreenActivity.class));
                final SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
                sharedPreferences.edit().putBoolean(Constants.LOGIN_PREFERENCE, false).apply();
                context.startActivity(new Intent(context, LoginScreenActivity.class));
                ((Activity) context).finish();

            }
        });
        // Showing Alert Message
        alertDialog.show();


    }


}
