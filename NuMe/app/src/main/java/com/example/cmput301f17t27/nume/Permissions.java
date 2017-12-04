package com.example.cmput301f17t27.nume;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Colman on 13/11/2017.
 */

public class Permissions {

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
