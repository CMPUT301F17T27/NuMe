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
 *  This an extension of the ArrayAdapter class that is
 compatible with the custom format of the habit list.
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class HabitAdapter extends ArrayAdapter<Habit> {
    private final Context context;
    private final ArrayList<Habit> habitArrayList;

    /**
     *
     * @param context
     * @param habitArrayList
     */
    public HabitAdapter(Context context, ArrayList<Habit> habitArrayList){
        super(context, R.layout.habit_entry, habitArrayList);

        this.context = context;
        this.habitArrayList = habitArrayList;
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

        View habitView = inflater.inflate(R.layout.habit_entry, parent, false);

        TextView titleView = habitView.findViewById(R.id.title);
        titleView.setText(habitArrayList.get(position).getTitle());

        TextView dateView = habitView.findViewById(R.id.date);
        dateView.setText(habitArrayList.get(position).getDateToStart().toString());

        TextView reasonView = habitView.findViewById(R.id.reason);
        reasonView.setText(habitArrayList.get(position).getReason());

        return habitView;
    }
}
