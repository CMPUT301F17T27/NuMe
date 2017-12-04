package com.example.cmput301f17t27.nume;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationClient;
    //User Info
    private Profile profile;
    private ArrayList<SearchEvent> filteredEventList;
    private ArrayList<Habit> habitArrayList = new ArrayList<>();
    private ArrayList<Marker> CurrentEventMarkers = new ArrayList<>();
    private ArrayList<HabitEvent> EventLatLong = new ArrayList<>();
    //
    private GoogleMap mMap;
    private Switch toggle;
    private Circle circle;
    private double Long;
    private double Lat;
    private boolean sEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        toggle = (Switch) findViewById(R.id.radToggle);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");
        filteredEventList = (ArrayList<SearchEvent>) bundle.getSerializable("SEARCHEVENTS");
        if(profile==null){
            sEvent=true;
            //habitArrayList = filteredEventList;
        }else {
            //extract all habitEvent
            sEvent=false;
            habitArrayList = profile.getHabitList();
            ArrayList<Profile> followingProfiles = profile.getFollowingProfiles();
            for(Profile fProfile : followingProfiles) {
                for (Habit habit : fProfile.getHabitList()) {
                    habitArrayList.add(habit);
                }
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    LatLng CurrentLocation = new LatLng(Lat, Long);
                    Location CurrentLoc = new Location("");
                    CurrentLoc.setLatitude(Lat);
                    CurrentLoc.setLongitude(Long);

                    circle =  mMap.addCircle(new CircleOptions().center(CurrentLocation).radius(5000).strokeColor(Color.GRAY));

                    for(Marker marker : CurrentEventMarkers){
                        Location EventLoc = new Location("");
                        EventLoc.setLatitude(marker.getPosition().latitude);
                        EventLoc.setLongitude(marker.getPosition().longitude);
                        if(CurrentLoc.distanceTo(EventLoc)>5000){
                            marker.remove();
                        }

                    }
                }else{
                    circle.remove();
                    for(Marker marker : CurrentEventMarkers) {
                        marker.remove();
                    }
                    if(sEvent){
                        for (SearchEvent searchEvent : filteredEventList){
                            double[] EventLatLong = searchEvent.location;
                            if(EventLatLong != null) {
                                LatLng eventLocation = new LatLng(EventLatLong[0], EventLatLong[1]);
                                Marker marker = mMap.addMarker(new MarkerOptions().position(eventLocation)
                                        .title(searchEvent.comment));
                                CurrentEventMarkers.add(marker);
                            }
                        }
                    }else {
                        for (HabitEvent event : EventLatLong) {
                            double[] EventLatLong = event.getLocation();
                            LatLng eventLocation = new LatLng(EventLatLong[0], EventLatLong[1]);
                            Marker marker = mMap.addMarker(new MarkerOptions().position(eventLocation)
                                    .title(event.getComment()));
                            CurrentEventMarkers.add(marker);

                        }
                    }
                }
            }
        });


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Edmonton, Alberta.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
        if(sEvent){
            for (SearchEvent searchEvent : filteredEventList){
                double[] EventLatLong = searchEvent.location;
                if(EventLatLong != null) {
                    LatLng eventLocation = new LatLng(EventLatLong[0], EventLatLong[1]);
                    Marker marker = mMap.addMarker(new MarkerOptions().position(eventLocation)
                            .title(searchEvent.comment));
                    CurrentEventMarkers.add(marker);
                }
            }
        }else {
            for (Habit habits : habitArrayList) {
                ArrayList<HabitEvent> habitEvents = habits.getEvents();
                for (HabitEvent event : habitEvents) {
                    if (event.getLocation() != null) {
                        double[] LatLong = event.getLocation();
                        LatLng eventLocation = new LatLng(LatLong[0], LatLong[1]);
                        Marker marker = mMap.addMarker(new MarkerOptions().position(eventLocation)
                                .title(event.getComment()));
                        CurrentEventMarkers.add(marker);
                        EventLatLong.add(event);
                    }
                }
            }

        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object
                    Long = location.getLongitude();
                    Lat = location.getLatitude();
                    LatLng CurrentLocation = new LatLng(Lat, Long);
                    //Log.d("...", "onSuccess: "+CurrentLocation);
                    mMap.addMarker(new MarkerOptions().position(CurrentLocation)
                            .title("Current Location"));
                    //Marker marker = mMap.addMarker(new MarkerOptions().position(edmonton).title("Current Location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(CurrentLocation,12));
                }
            }
        });


        //return;
    }
}