package com.example.cmput301f17t27.nume.habit;

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
 * compatible with the custom format of the habit list
 * @author CMPUT301F17T27
 * @version 1.0
 * @see Habit
 * @since 1.0
 */
public class HabitAdapter extends ArrayAdapter<Habit> {
    private final Context context; //Context where the adapter exists
    private final ArrayList<Habit> habitArrayList; //The actual list of habits to use


    public HabitAdapter(Context context, ArrayList<Habit> habitArrayList){
        super(context, R.layout.habit_entry, habitArrayList);

        this.context = context;
        this.habitArrayList = habitArrayList;
    }



    /**
     * Overrides the standard getView to get our custom layout
     * for each entry in the list of habits
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
        View habitView = inflater.inflate(R.layout.habit_entry, parent, false);

        //UI definitions
        TextView titleView = habitView.findViewById(R.id.title);
        TextView dateView = habitView.findViewById(R.id.date);
        TextView reasonView = habitView.findViewById(R.id.reason);

        //Fill in the UI
        titleView.setText(habitArrayList.get(position).getTitle());
        dateView.setText(habitArrayList.get(position).getDateToStart().toString());
        reasonView.setText(habitArrayList.get(position).getReason());

        return habitView;
    }
}
