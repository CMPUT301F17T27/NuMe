package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Colman on 12/11/2017.
 */

public class MapController {

    public static Location getLocation(Context context) {
        /*
            This function gets the last known location of the device.

            Args - None
            Returns - A Location object (Null if location services are
                not enabled)
        */

        try {
            Log.d("Location", "If granted!");
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean GPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!networkEnabled && !GPSEnabled) {
                // cannot get location
            }

            else {
                Log.d("Location", "If granted~");

                if (GPSEnabled && lm != null)  {
                    //LocationListener locationListener = new MyLocationListener();
                    //lm.requestLocationUpdates( LocationManager.GPS_PROVIDER,50,10, locationListener);
                    List<String> providers = lm.getProviders(true);
                    Location bestLocation = null;
                    for (String provider : providers) {
                        Location l = lm.getLastKnownLocation(provider);
                        if (l == null) {
                            continue;
                        }
                        if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                            // Found best last known location: %s", l);
                            bestLocation = l;
                        }
                    }
                    Log.d("Location", "If granted gps!: "+bestLocation);
                    return bestLocation;
                    //return lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                if (networkEnabled && lm != null) {
                    Log.d("Location", "If granted network");
                    return lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }

        catch (SecurityException ex)  {
            //Security exception
        }

        return null;
    }

    /*
    private class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location loc) {
            Log.i("Location", "onLocationChanged: "+loc.getLatitude());

        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

    }*/

}
