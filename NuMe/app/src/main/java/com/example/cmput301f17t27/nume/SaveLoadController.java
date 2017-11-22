package com.example.cmput301f17t27.nume;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class SaveLoadController {
    //Local save/load var declarations
    private static final String PROFILEFILENAME = "profile.sav";
    private static final String EVENTSFILENAME = "events.sav";
    private static final String EVENTFILENAME = "event.sav";


    /**
     * This function loads the user's profile from a locally saved file
     * on the device.
     *
     * @param context Context from which this is called from
     * @return Profile that was loaded
     */
    public static Profile loadProfileFromFile(Context context){
        Profile profile;
        try {
            FileInputStream stream = context.openFileInput(PROFILEFILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new Gson();
            profile = gson.fromJson(in, Profile.class);
        }

        catch (FileNotFoundException e) {
            profile = null;
        }
        return profile;
    }



    /**
     * This function saves the user's profile to a locally saved file
     * on the device as well as to elastic search.
     *
     * @param context Context from which this is called from
     * @param profile Profile to save
     */
    public static void saveProfileToFile(Context context, Profile profile) {
        context.deleteFile(PROFILEFILENAME);
        context.deleteFile(EVENTSFILENAME);
        context.deleteFile(EVENTFILENAME);
        try {
            FileOutputStream stream = context.openFileOutput(PROFILEFILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            Gson gson = new Gson();
            gson.toJson(profile, writer);
            writer.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        //Save the profile to elastic search as well
        ElasticSearchController.UpdateProfileTask updateProfileTask = new ElasticSearchController.UpdateProfileTask();
        updateProfileTask.execute(profile);
    }



    /**
     * This function loads an EventCache which contains an
     * array list of events that were saved using saveEventsToFile.
     *
     * @param context Context from which this is called from
     * @return The EventCache from the file
     */
    public static EventCache loadEventsFromFile(Context context) {
        EventCache eventCache;
        try {
            FileInputStream stream = context.openFileInput(EVENTSFILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new Gson();
            eventCache = gson.fromJson(in, EventCache.class);
        }

        catch (FileNotFoundException e) {
            eventCache = null;
        }
        return eventCache;
    }



    /**
     * This function creates an EventCache object from the events and index arguments,
     * and saves it locally.
     *
     * @param context Context from which this is called from
     * @param events An array list of habit events to store
     * @param index The index of the habit that these events belong to
     */
    public static void saveEventsToFile(Context context, ArrayList<HabitEvent> events, int index) {
        EventCache eventCache = new EventCache(events, index);
        context.deleteFile(EVENTSFILENAME);
        context.deleteFile(EVENTFILENAME);

        try {
            FileOutputStream stream = context.openFileOutput(EVENTSFILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            Gson gson = new Gson();
            gson.toJson(eventCache, writer);
            writer.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



    /**
     * This function loads an EventCache which contains a single HabitEvent
     * and index for which habit event in the list of habit events it
     * represents.
     *
     * @param context Context from which this is called from
     * @return An EventCache containing a single habit event and index
     */
    public static EventCache loadEventFromFile(Context context) {
        EventCache eventCache;
        try {
            FileInputStream stream = context.openFileInput(EVENTFILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            Gson gson = new Gson();
            eventCache = gson.fromJson(in, EventCache.class);
        }

        catch (FileNotFoundException e) {
            eventCache = null;
        }
        return eventCache;
    }



    /**
     * This function creates a EventCache object from the habit event and
     * index args and saves it locally.
     *
     * @param context Context from which this is called from
     * @param event A single habit event to store
     * @param index The index of the habit event in a list of habit events
     */
    public static void saveEventToFile(Context context, HabitEvent event, int index) {
        EventCache eventCache = new EventCache(event, index);
        context.deleteFile(EVENTFILENAME);

        try {
            FileOutputStream stream = context.openFileOutput(EVENTFILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            Gson gson = new Gson();
            gson.toJson(eventCache, writer);
            writer.flush();
        }

        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
