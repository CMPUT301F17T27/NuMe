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
 * This an extension of the ArrayAdapter class that is
 * compatible with the custom format of the follower list
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class FollowerAdapter extends ArrayAdapter<Profile> {
    private final Context context; //Context where the adapter exists
    private final ArrayList<Profile> followerArrayList; //The actual list of followers to use


    public FollowerAdapter(Context context, ArrayList<Profile> followerArrayList){
        super(context, R.layout.follower_entry, followerArrayList);

        this.context = context;
        this.followerArrayList = followerArrayList;
    }


    /**
     * Overrides the standard getView to get our custom layout
     * for each entry in the list of followers
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
        View habitView = inflater.inflate(R.layout.follower_entry, parent, false);

        //UI definitions
        TextView userName = habitView.findViewById(R.id.username);
        TextView fullName = habitView.findViewById(R.id.fullname);

        //Fill in the UI
        userName.setText(followerArrayList.get(position).getUserName());
        fullName.setText(followerArrayList.get(position).getName());

        return habitView;
    }
}