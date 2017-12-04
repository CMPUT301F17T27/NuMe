package com.example.cmput301f17t27.nume.various;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cmput301f17t27.nume.R;
import com.example.cmput301f17t27.nume.account.Profile;
import com.example.cmput301f17t27.nume.habit.HabitListActivity;

import java.util.ArrayList;

/**
 * This an extension of the ArrayAdapter class that is
 compatible with the custom format of the notification
 list.
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class NotificationAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> notificationArrayList;

    /**
     *
     * @param context
     * @param notificationArrayList
     */
    public NotificationAdapter(Context context, ArrayList<String> notificationArrayList){
        super(context, R.layout.habit_entry, notificationArrayList);

        this.context = context;
        this.notificationArrayList = notificationArrayList;
    }


    /**
     * Override the getView for the new list format
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View notificationView = inflater.inflate(R.layout.notification_entry, parent, false);

        //Get the username string for the entry in the list
        final String userNameStr = notificationArrayList.get(position);

        //Set the username
        TextView userName = notificationView.findViewById(R.id.username);
        userName.setText(userNameStr);

        Button acceptButton = (Button) notificationView.findViewById(R.id.acceptbutton);
        Button declineButton = (Button) notificationView.findViewById(R.id.declinebutton);

        //Setup the listener for the accept button
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add the user to the other user's following list
                Profile profile2 = SaveLoadController.getProfile(userNameStr);
                profile2.addFollowing(NotificationsActivity.profile.getUserName());
                SaveLoadController.updateProfile(profile2);

                //Add the other user to this user's followers list
                NotificationsActivity.profile.addFollower(userNameStr);
                NotificationsActivity.profile.deleteRequest(position);
                SaveLoadController.saveProfile(context, NotificationsActivity.profile);
                NotificationsActivity.adapter.notifyDataSetChanged();
            }
        });


        //Setup the listener for the decline button
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Remove the request from this user's profile
                NotificationsActivity.profile.deleteRequest(position);
                SaveLoadController.saveProfile(context, NotificationsActivity.profile);
                NotificationsActivity.adapter.notifyDataSetChanged();
            }
        });

        return notificationView;
    }
}
