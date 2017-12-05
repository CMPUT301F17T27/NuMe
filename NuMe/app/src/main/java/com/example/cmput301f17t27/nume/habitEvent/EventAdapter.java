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
 * compatible with the custom format of the event list
 * @author CMPUT301F17T27
 * @version 1.0
 * @see HabitEvent
 * @since 1.0
 */
public class EventAdapter extends ArrayAdapter<HabitEvent> {
    private final Context context; //Context where the adapter exists
    private final ArrayList<HabitEvent> eventArrayList; //The actual list of events to display

    public EventAdapter(Context context, ArrayList<HabitEvent> eventArrayList){
        super(context, R.layout.event_entry, eventArrayList);

        this.context = context;
        this.eventArrayList = eventArrayList;
    }



    /**
     * Overrides the standard getView to get our custom layout
     * for each entry in the list of events
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
        View eventView = inflater.inflate(R.layout.event_entry, parent, false);

        //UI definitions
        TextView commentView = eventView.findViewById(R.id.comment);
        TextView dateView = eventView.findViewById(R.id.date);

        //Fill in the UI
        commentView.setText(eventArrayList.get(position).getComment());
        dateView.setText(eventArrayList.get(position).getDateCompleted().toString());

        return eventView;
    }
}
