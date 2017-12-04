package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class FollowingAdapter extends ArrayAdapter<FollowingHabit> {
    /*
        This an extension of the ArrayAdapter class that is
        compatible with the custom format of the habit list.
    */

    private final Context context;
    private final ArrayList<FollowingHabit> followingArrayList;

    public FollowingAdapter(Context context, ArrayList<FollowingHabit> followingArrayList){
        super(context, R.layout.following_entry, followingArrayList);

        this.context = context;
        this.followingArrayList = followingArrayList;
    }



    //Override the getView for the new list format
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View habitView = inflater.inflate(R.layout.following_entry, parent, false);

        TextView userName = habitView.findViewById(R.id.username);
        TextView title = habitView.findViewById(R.id.title);
        TextView reason = habitView.findViewById(R.id.reason);

        userName.setText(followingArrayList.get(position).userName);
        title.setText(followingArrayList.get(position).title);
        reason.setText(followingArrayList.get(position).reason);

        return habitView;
    }
}