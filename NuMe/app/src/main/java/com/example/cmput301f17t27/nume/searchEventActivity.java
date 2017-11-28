package com.example.cmput301f17t27.nume;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class searchEventActivity extends AppCompatActivity {

    protected static final int EDIT_EVENT_REQUEST_CODE = 103;

    protected static final int NO_ACTION_RESULT_CODE = 0;
    protected static final int EVENT_CHANGED_RESULT_CODE = 5;

    private ArrayList<Habit> habitArrayList = new ArrayList<>();
    private ArrayList<HabitEvent> habitEventArrayList = new ArrayList<>();
    private EventSearchAdapter eventSearchAdapter;
    private Profile profile;
    private ArrayList<String> HabitName = new ArrayList<String>();
    ListView listView;
    SearchView searchView;

    private int EventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_event);

        //get profile object from HabitListActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        profile = (Profile) bundle.getSerializable("Profile");

        //extract all habitEvent
        habitArrayList = profile.getHabitList();

        for (Habit habits : habitArrayList) {
            ArrayList<HabitEvent> habitEvents = habits.getEvents();
            for (HabitEvent event : habitEvents) {
                HabitName.add(habits.getTitle());
                habitEventArrayList.add(event);
            }

        }

        //Log.i("habitEventList",String.valueOf(habitEventArrayList.size()));
        listView = (ListView) findViewById(R.id.HabitEventList);
        eventSearchAdapter = new EventSearchAdapter(this, habitEventArrayList, HabitName);
        listView.setAdapter(eventSearchAdapter);





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                Intent intent = new Intent(searchEventActivity.this, ViewEventActivity.class);
                Bundle bundle = new Bundle();
                HabitEvent habitEvent = habitEventArrayList.get(position);
                bundle.putSerializable("EVENT",habitEvent);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_EVENT_REQUEST_CODE);
            }
        });
    }




        //search
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the options menu from XML
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.options_menu_event_search, menu);

            // Get the SearchView and set the searchable configuration
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default



            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){


                @Override
                public boolean onQueryTextSubmit(String query){

                    return false;
                }




                @Override
                public boolean onQueryTextChange(String newText){


                    final ArrayList<HabitEvent> habitEvents = new ArrayList<>();
                    for(HabitEvent habitEvent: habitEventArrayList){
                        if(habitEvent.getComment().contains(newText)){
                            habitEvents.add(habitEvent);
                        }
                    }
                    //Log.i("habitEventNumber",String.valueOf(habitEvents.size()));
                    //habitEvents contains the right habitEvent; just need to display them

                    eventSearchAdapter = new EventSearchAdapter(searchEventActivity.this, habitEvents, HabitName);
                    //eventSearchAdapter.getFilter().filter(newText);
                    listView.setAdapter(eventSearchAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                            Intent intent = new Intent(searchEventActivity.this, ViewEventActivity.class);
                            Bundle bundle = new Bundle();

                            HabitEvent habitEvent = habitEvents.get(position);
                            bundle.putSerializable("EVENT",habitEvent);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, EDIT_EVENT_REQUEST_CODE);
                        }
                    });



                    return false;

                }





            });

            return true;
        }









}
