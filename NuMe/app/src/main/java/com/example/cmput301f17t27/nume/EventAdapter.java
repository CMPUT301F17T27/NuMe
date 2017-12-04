package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Colman on 30/10/2017.
 */

public class EventAdapter extends ArrayAdapter<HabitEvent> {
    /*
        This an extension of the ArrayAdapter class that is
        compatible with the custom format of the habitEvent list.
    */

    private final Context context;
    private final ArrayList<HabitEvent> eventArrayList;

    public EventAdapter(Context context, ArrayList<HabitEvent> eventArrayList){
        super(context, R.layout.event_entry, eventArrayList);

        this.context = context;
        this.eventArrayList = eventArrayList;
    }



    //Override the getView for the new list format
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View eventView = inflater.inflate(R.layout.event_entry, parent, false);

        TextView commentView = eventView.findViewById(R.id.comment);
        commentView.setText(eventArrayList.get(position).getComment());

        TextView dateView = eventView.findViewById(R.id.date);
        dateView.setText(eventArrayList.get(position).getDateCompleted().toString());

        return eventView;
    }
}
