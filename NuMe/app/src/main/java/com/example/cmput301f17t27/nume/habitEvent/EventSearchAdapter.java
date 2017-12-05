package com.example.cmput301f17t27.nume.habitEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cmput301f17t27.nume.R;

import java.util.ArrayList;

/**
 * This an extension of the ArrayAdapter class that is
 * compatible with the custom format of the search event
 * list
 * @author CMPUT301F17T27
 * @version 1.0
 * @see SearchEvent
 * @since 1.0
 */
public class EventSearchAdapter extends ArrayAdapter<SearchEvent>{
    private final Context context; //Context where the adapter exists
    private final ArrayList<SearchEvent> eventArrayList; //Actual list of SearchEvent objects


    public EventSearchAdapter(Context context, ArrayList<SearchEvent> eventArrayList){
        super(context, R.layout.search_event_entry, eventArrayList);

        this.context = context;
        this.eventArrayList = eventArrayList;
    }



    /**
     * Overrides the standard getView to get our custom layout
     * for each entry in the list of search events
     * @param position Position of the view to get
     * @param convertView View to be converted
     * @param parent Parent of that view
     * @return The new custom view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Get the template view
        View eventView = inflater.inflate(R.layout.search_event_entry, parent, false);

        //UI definitions
        TextView userName = eventView.findViewById(R.id.username);
        TextView habitTitle = eventView.findViewById(R.id.habittitle);
        TextView comment = eventView.findViewById(R.id.comment);
        TextView date = eventView.findViewById(R.id.date);

        //Fill in the UI
        userName.setText(eventArrayList.get(position).userName);
        habitTitle.setText(eventArrayList.get(position).habitTitle);
        comment.setText(eventArrayList.get(position).comment);
        date.setText(eventArrayList.get(position).dateCompleted.toString());

        return eventView;
    }
}
