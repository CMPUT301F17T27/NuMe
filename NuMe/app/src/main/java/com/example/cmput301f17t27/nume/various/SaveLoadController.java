package com.example.cmput301f17t27.nume.various;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cmput301f17t27.nume.account.Profile;
import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * This is a static class that provides a toolbox for saving
 * locally and to ElasticSearch. (This holds all the
 * ElasticSearch code)
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class SaveLoadController {
    //Local save/load var declaration
    private static final String PROFILEFILENAME = "profile.sav";

    //JestClient for ElasticSearch
    private static JestClient client;


    /**
     * This function loads a user's profile. First it tries
     * locally and if that doesn't work, it tries from
     * ElasticSearch.
     *
     * @param context Context from which this is called from
     * @param profile The profile to add
     * @return boolean False if the profile wasn't added, true if it was
     */
    public static boolean addProfile(Context context, Profile profile) {
        //Start the getProfile AsyncTask
        GetProfileTask getProfileTask = new GetProfileTask();
        getProfileTask.execute(profile.getUserName());

        try {
            //Get the getProfile from the task
            Profile profile2 = getProfileTask.get();

            //If the profile exists
            if(profile2 != null){
                return false;
            }

            //If the profile doesn't exist
            else {
                //Start the addProfile AsyncTask
                AddProfileTask addProfileTask = new AddProfileTask();
                addProfileTask.execute(profile);

                //Get the ID of the added profile
                String id = addProfileTask.get();

                //Set the id of the profile object to the one that was just found
                profile.setId(id);

                //Update the profile on ElasticSearch so it has the id member
                UpdateProfileTask updateProfileTask = new UpdateProfileTask();
                updateProfileTask.execute(profile);
            }
            return true;
        }

        catch (Exception e){
            return false;
        }
    }



    /**
     * This function loads a user's profile. First it tries
     * locally and if that doesn't work, it tries from
     * ElasticSearch.
     *
     * @param context Context from which this is called from
     * @param userName The username of the profile to load
     * @return Profile that was loaded
     */
    public static Profile loadProfile(Context context, String userName){
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

        if (profile == null) {
            //Start the getProfile AsyncTask
            GetProfileTask getProfileTask = new GetProfileTask();
            getProfileTask.execute(userName);

            try {
                //Get the profile from the task
                profile = getProfileTask.get();
            }

            catch (Exception e) {
                profile = null;
            }
        }

        return profile;
    }



    /**
     * This function saves the user's profile locally and
     * to ElasticSearch. (If available)
     *
     * @param context Context from which this is called from
     * @param profile Profile to save
     */
    public static void saveProfile(Context context, Profile profile) {
        context.deleteFile(PROFILEFILENAME);
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
        UpdateProfileTask updateProfileTask = new UpdateProfileTask();
        updateProfileTask.execute(profile);
    }



    /**
     * Deletes the profile saved locally.
     * @param context Context where the deletion is requested
     */
    public static void deleteLocalProfile(Context context) {
        context.deleteFile(PROFILEFILENAME);
    }



    /**
     * Get a profile from ElasticSearch only. Doesn't check the local
     * cache
     * @param uName Username of the profile to get
     * @return The profile object that has that username. Null if no
     * profile was found
     */
    public static Profile getProfile(String uName) {
        GetProfileTask getProfileTask = new GetProfileTask();
        getProfileTask.execute(uName);

        Profile profile;
        try {
            //Get the profile from the task
            profile = getProfileTask.get();
        }

        catch (Exception e) {
            profile = null;
        }

        return profile;
    }



    /**
     * Updates a profile on ElasticSearch only. Doesn't update
     * the local one
     * @param profile Profile to replace the old one
     */
    public static void updateProfile(Profile profile) {
        //Save the profile to elastic search as well
        UpdateProfileTask updateProfileTask = new UpdateProfileTask();
        updateProfileTask.execute(profile);
    }



    /**
     * A class for starting a second thread to add a profile to ElasticSearch
     */
    private static class AddProfileTask extends AsyncTask<Profile, Void, String> {
        /**
         * This Async task adds a profile to ElasticSearch.
         * @param profiles The one profile you are adding (Only supports one argument)
         * @return The ID where the added profile is stored on ElasticSearch
         */
        @Override
        protected String doInBackground(Profile... profiles) {
            verifySettings();

            //Make the index
            Index index = new Index.Builder(profiles[0]).index("cmput301f17t27_nume").type("profile").build();

            try {
                //Execute the index
                DocumentResult result = client.execute(index);

                //Return the id
                return result.getId();
            }

            catch (Exception e) {
                Log.i("Error", "Failed to add profile to ElasticSearch");
            }

            return null;
        }
    }



    /**
     * A class for starting a second thread to update a profile on ElasticSearch
     */
    private static class UpdateProfileTask extends AsyncTask<Profile, Void, Void> {
        /**
         * This Async task updates a profile on ElasticSearch.
         * @param profiles The one profile you are updating (Only supports one argument)
         */
        @Override
        protected Void doInBackground(Profile... profiles) {
            verifySettings();

            //Get the profile argument
            Profile profile = profiles[0];

            //Build the index
            Index index = new Index.Builder(profile).index("cmput301f17t27_nume").type("profile").id(profile.getId()).build();

            try {
                //Execute the index
                DocumentResult result = client.execute(index);

                if (!result.isSucceeded()) {
                    Log.i("Error", "Failed to update the profile on ElasticSearch");
                }
            }

            catch (Exception e) {
                Log.i("Error", "Failed to update the profile on ElasticSearch");
            }

            return null;
        }
    }



    /**
     * A class for starting a second thread to get a profile from ElasticSearch
     */
    private static class GetProfileTask extends AsyncTask<String, Void, Profile> {
        /**
         * This Async task gets a profile from ElasticSearch.
         * @param usernames The username of the profile to get (Only supports one argument)
         * @return The profile object associated with that username
         */
        @Override
        protected Profile doInBackground(String... usernames) { //String defines the param type; ...means multiple params
            verifySettings();

            //Get the username argument
            String username = usernames[0];

            //Formulate the search query
            String query = "{\"query\" : {\"match\" : { \"username\" : \"" + username + "\" }}}";

            //Build the search object
            Search search = new Search.Builder(query).addIndex("cmput301f17t27_nume").addType("profile").build();

            try {
                //Execute the search
                SearchResult result = client.execute(search);

                if (result.isSucceeded()) {
                    //Get and return the profile
                    SearchResult.Hit<Profile, Void> hit = result.getFirstHit(Profile.class);
                    return hit.source;
                }

                else {
                    Log.i("Error", "Failed to get the profile from ElasticSearch");
                }
            }

            catch (Exception e) {
                Log.i("Error", "Failed to get the profile from ElasticSearch");
            }

            return null;
        }
    }



    /**
     * This function initializes the JestDroidClient and sets up ElasticSearch.
     */
    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
