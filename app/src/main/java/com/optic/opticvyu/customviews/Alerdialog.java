package com.optic.opticvyu.customviews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;



import com.optic.interior.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by optimum2 on 5/12/17.
 */

public class Alerdialog {


    public void netAlert(Context context, String alert, String message) {


        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(alert);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_launcher);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        // Showing Alert Message
        alertDialog.show();


    }


}
