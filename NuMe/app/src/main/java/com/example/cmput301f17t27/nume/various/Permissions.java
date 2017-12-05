package com.example.cmput301f17t27.nume.various;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;

/**
 * This class has all static methods and is meant to
 * act as a toolbox for requesting permissions from
 * Android.
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class Permissions {

    /**
     * Requests permission to read from the device's disk
     * @param con Context where the permission was requested
     */
    public static void requestReadPermission(Context con) {
        final Activity context = (Activity) con;
        String perm = Manifest.permission.READ_EXTERNAL_STORAGE;
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, perm)) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(context, perms, 1);
                }
            };

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("NuMe would like to access your images.");
            dialog.setPositiveButton("Allow", listener);
            dialog.show();
        }

        else {
            String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(context, perms, 1);
        }
    }



    /**
     * Requests permission to get the device's location.
     * @param con Context where the permission was requested
     */
    public static void requestLocationPermission(Context con) {
        final Activity context = (Activity) con;
        String perm = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.shouldShowRequestPermissionRationale(context, perm)) {
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                    ActivityCompat.requestPermissions(context, perms, 1);
                }
            };

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("NuMe would like to access your location.");
            dialog.setPositiveButton("Allow", listener);
            dialog.show();
        }

        else {
            String[] perms = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(context, perms, 1);
        }
    }
}
