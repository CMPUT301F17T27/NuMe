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
 compatible with the custom format of the habitEvent list.
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class EventAdapter extends ArrayAdapter<HabitEvent> {
    private final Context context;
    private final ArrayList<HabitEvent> eventArrayList;

    public EventAdapter(Context context, ArrayList<HabitEvent> eventArrayList){
        super(context, R.layout.event_entry, eventArrayList);

        this.context = context;
        this.eventArrayList = eventArrayList;
    }


    /**
     * Override the getView for the new list format
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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
