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
 compatible with the custom format of the searchEvent
 list.
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class EventSearchAdapter extends ArrayAdapter<SearchEvent>{
    private final Context context;
    private final ArrayList<SearchEvent> eventArrayList;

    /**
     *
     * @param context
     * @param eventArrayList
     */
    public EventSearchAdapter(Context context, ArrayList<SearchEvent> eventArrayList){
        super(context, R.layout.search_event_entry, eventArrayList);

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

        View eventView = inflater.inflate(R.layout.search_event_entry, parent, false);

        TextView userName = eventView.findViewById(R.id.username);
        userName.setText(eventArrayList.get(position).userName);

        TextView habitTitle = eventView.findViewById(R.id.habittitle);
        habitTitle.setText(eventArrayList.get(position).habitTitle);

        TextView comment = eventView.findViewById(R.id.comment);
        comment.setText(eventArrayList.get(position).comment);

        TextView date = eventView.findViewById(R.id.date);
        date.setText(eventArrayList.get(position).dateCompleted.toString());

        return eventView;
    }
}
