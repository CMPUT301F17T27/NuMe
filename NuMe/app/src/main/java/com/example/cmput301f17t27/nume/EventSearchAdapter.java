package com.example.cmput301f17t27.nume;

import android.app.Activity;
import android.content.Intent;
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

public class EventSearchAdapter extends ArrayAdapter<HabitEvent>{
    private final Activity context;
    private final ArrayList<HabitEvent> HabitEventList;
    private final ArrayList<String> HabitEventNameArray;
    //private final String[] HabitEventCommentArray;
    //private final String[] DateArray;

    public EventSearchAdapter(Activity context, ArrayList<HabitEvent> HabitEventList,
                              ArrayList<String> HabitEventNameArray ){
        super(context,R.layout.search_event_content, HabitEventList );

        this.context = context;
        this.HabitEventNameArray = HabitEventNameArray;
        this.HabitEventList = HabitEventList;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.search_event_content, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView HabitEventName = (TextView) rowView.findViewById(R.id.searchEventNameText);
        TextView HabitEventComments = (TextView) rowView.findViewById(R.id.searchEventCommentsText);
        TextView HabitEventDates = (TextView) rowView.findViewById(R.id.searchEventDateText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.SearchEventImage);

        //this code sets the values of the objects to values from the arrays
        HabitEventName.setText(HabitEventNameArray.get(position));
        HabitEventComments.setText(HabitEventList.get(position).getComment());
        HabitEventDates.setText(HabitEventList.get(position).getDateCompleted().toString());
        //imageView.setImageResource(Integer.valueOf(HabitEventList.get(position).getImage()));


        return rowView;

    };


}
