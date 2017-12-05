package com.example.cmput301f17t27.nume.follows;

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
 * compatible with the custom format of the habit list.
 * @author CMPUT301F17T27
 * @version 1.0
 */
public class FollowingAdapter extends ArrayAdapter<FollowingHabit> {
    private final Context context; //Context where this adapter exists
    private final ArrayList<FollowingHabit> followingArrayList; //The actual list of FollowingHabits


    public FollowingAdapter(Context context, ArrayList<FollowingHabit> followingArrayList){
        super(context, R.layout.following_entry, followingArrayList);

        this.context = context;
        this.followingArrayList = followingArrayList;
    }



    /**
     * Overrides the standard getView to get our custom layout
     * for each entry in the list of following habits
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
        View habitView = inflater.inflate(R.layout.following_entry, parent, false);

        //UI definitions
        TextView userName = habitView.findViewById(R.id.username);
        TextView title = habitView.findViewById(R.id.title);
        TextView reason = habitView.findViewById(R.id.reason);

        //Set the UI values
        userName.setText(followingArrayList.get(position).userName);
        title.setText(followingArrayList.get(position).title);
        reason.setText(followingArrayList.get(position).reason);

        return habitView;
    }
}