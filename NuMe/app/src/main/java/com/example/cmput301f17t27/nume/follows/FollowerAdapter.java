package com.example.cmput301f17t27.nume.follows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.R;

import java.util.ArrayList;

/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
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


    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
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