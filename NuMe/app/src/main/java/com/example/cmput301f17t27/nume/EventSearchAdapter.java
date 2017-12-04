package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xuan on 2017-11-26.
 */

public class EventSearchAdapter extends ArrayAdapter<SearchEvent>{
    /*
        This an extension of the ArrayAdapter class that is
        compatible with the custom format of the searchEvent
        list.
    */

    private final Context context;
    private final ArrayList<SearchEvent> eventArrayList;

    public EventSearchAdapter(Context context, ArrayList<SearchEvent> eventArrayList){
        super(context, R.layout.search_event_entry, eventArrayList);

        this.context = context;
        this.eventArrayList = eventArrayList;
    }



    //Override the getView for the new list format
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
