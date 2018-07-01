package com.abrarkotwal.moviezone.SessionManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class AlertDialogManager {

    public static void internetConnectionErrorAlert(final Context context, DialogInterface.OnClickListener onClickTryAgainButton) {
        String message = "Please Turn On Your Internet? Or Try Again!!!";
        new AlertDialog.Builder(context)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("Network Error")
                .setMessage(message)
                .setPositiveButton("Try Again", onClickTryAgainButton)
                .setNegativeButton("Turn Internet On", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Settings.ACTION_SETTINGS);
                        ((Activity) context).startActivityForResult(i, 0);
                    }
                })
                .show();
    }

    public static void networkError(final Context context) {
        String message = "Please Turn On Your Internet? Or Try Again!!!";
        new AlertDialog.Builder(context)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("Network Error")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)context).finish();
                    }
                })
                .setNegativeButton("Turn Internet On", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Settings.ACTION_SETTINGS);
                        ((Activity) context).startActivityForResult(i, 0);
                    }
                })
                .show();
    }
}