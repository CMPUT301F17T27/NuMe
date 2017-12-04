package com.example.cmput301f17t27.nume;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Colman on 30/11/2017.
 */

public class FollowerAdapter extends ArrayAdapter<Profile> {
    /*
        This an extension of the ArrayAdapter class that is
        compatible with the custom format of the follower list.
    */

    private final Context context;
    private final ArrayList<Profile> followerArrayList;

    public FollowerAdapter(Context context, ArrayList<Profile> followerArrayList){
        super(context, R.layout.follower_entry, followerArrayList);

        this.context = context;
        this.followerArrayList = followerArrayList;
    }



    //Override the getView for the new list format
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View habitView = inflater.inflate(R.layout.follower_entry, parent, false);

        TextView userName = habitView.findViewById(R.id.username);
        userName.setText(followerArrayList.get(position).getUserName());

        TextView fullName = habitView.findViewById(R.id.fullname);
        fullName.setText(followerArrayList.get(position).getName());

        return habitView;
    }
}