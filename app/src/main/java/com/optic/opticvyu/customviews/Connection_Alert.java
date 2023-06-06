package com.optic.opticvyu.customviews;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.optic.interior.R;

/**
 * Created by Adarsh on 19-03-2018.
 */

public class Connection_Alert {


    public  void  NetConnection(Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.connectivity_error_message));

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

    public  void  NetLocal(Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.local_error_message));

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

    public  void  interiorAccess(Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.monitor_access));

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


    public  void  ActiveNetWork(Context context)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Alert");

        // Setting Dialog Message
        alertDialog.setMessage(context.getString(R.string.ActiveInternet));

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
