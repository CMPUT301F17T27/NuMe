package com.example.cmput301f17t27.nume.habitEvent;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.cmput301f17t27.nume.habit.Habit;
import com.example.cmput301f17t27.nume.various.MapsActivity;
import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class EventSearchActivity extends AppCompatActivity {
    //Request codes for startingActivityForResult
    protected static final int VIEW_MAP_REQUEST_CODE = 108;
    //Return codes for startingActivityForResult
    protected static final int NO_ACTION_RESULT_CODE = 0;

    //Profile declaration
    private Profile profile;

    //Event list declarations
    private ArrayList<SearchEvent> fullEventList;
    private ArrayList<SearchEvent> filteredEventList;

    //UI declarations
    private SearchView search;
    private ListView searchEvents;
    private EventSearchAdapter adapter;
    private Button mapButton;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        //Profile definition
        Bundle bundle = getIntent().getExtras();
        profile = (Profile) bundle.getSerializable("PROFILE");

        //Get the events from the profile member
        fullEventList = new ArrayList<>();
        ArrayList<Habit> habitList = profile.getHabitList();
        for (Habit habit : habitList) {
            ArrayList<HabitEvent> habitEvents = habit.getEvents();
            for (HabitEvent event : habitEvents) {
                SearchEvent sEvent = new SearchEvent(profile.getUserName(), habit.getTitle(),
                        event.getComment(), event.getLocation(), event.getDateCompleted());
                fullEventList.add(sEvent);
            }
        }
        //Add the first event from all the habits that the profile member is following
        ArrayList<Profile> fProfiles = profile.getFollowingProfiles();
        for (Profile fProfile : fProfiles) {
            ArrayList<Habit> fHabits = fProfile.getHabitList();
            for (Habit fHabit : fHabits) {
                ArrayList<HabitEvent> fEvents = fHabit.getEvents();
                if (fEvents.size() > 0) {
                    SearchEvent sEvent = new SearchEvent(fProfile.getUserName(), fHabit.getTitle(),
                            fEvents.get(0).getComment(), fEvents.get(0).getLocation(),
                            fEvents.get(0).getDateCompleted());
                    fullEventList.add(sEvent);
                }
            }
        }
        //Sort the the events in reverse chronological order
        Collections.sort(fullEventList, new Comparator<SearchEvent>() {
            @Override
            public int compare(SearchEvent event1, SearchEvent event2) {
                return event2.dateCompleted.compareTo(event1.dateCompleted);
            }
        });
        filteredEventList = fullEventList;

        //UI definitions
        searchEvents = (ListView) findViewById(R.id.searchevents);
        adapter = new EventSearchAdapter(this, filteredEventList);
        mapButton = (Button) findViewById(R.id.mapbutton);

        //Set the adapter
        searchEvents.setAdapter(adapter);


        //Setup the listener for the add button
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventSearchActivity.this, MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SEARCHEVENTS", filteredEventList);
                intent.putExtras(bundle);
                startActivityForResult(intent, VIEW_MAP_REQUEST_CODE);
            }
        });
    }


    /**
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_event_search, menu);

        //Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                //Clear the filtered list
                filteredEventList = new ArrayList<>();

                //Fill up the filtered list with the right events
                for(SearchEvent sEvent: fullEventList){
                    if(sEvent.comment.contains(newText) || sEvent.habitTitle.contains(newText)){
                        filteredEventList.add(sEvent);
                    }
                }

                //Update the adapter
                adapter = new EventSearchAdapter(EventSearchActivity.this, filteredEventList);
                searchEvents.setAdapter(adapter);

                return true;
            }
        });

        return true;
    }


    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If you're returning from the MapsActivity
        if(requestCode == VIEW_MAP_REQUEST_CODE) {
            //If the user clicked the back button
            if(resultCode == NO_ACTION_RESULT_CODE) {
                //Do nothing
            }
        }
    }
}
