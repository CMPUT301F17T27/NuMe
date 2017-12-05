package com.example.cmput301f17t27.nume.follows;

import com.example.cmput301f17t27.nume.habitEvent.HabitEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * A special class that is very similar to habit but holds
 * the username that the habit belongs to as well as its most
 * recent event. This is so that we have a single object to
 * put for each entry in the following list
 * @author CMPUT301F17T27
 * @version 1.0
 * @see FollowingHabit
 */
public class FollowingHabit implements Serializable {
    public String userName; //The username of the user who this belongs to
    public String title; //The title of the habit
    public String reason; //Reason for the habit
    public Date dateToStart; //Date to start the habit
    public ArrayList<String> frequency; //Days of the week the habit should occur
    public HabitEvent habitEvent; //The most recent habit event (if any) of the habit


    public FollowingHabit(String userName, String title, String reason, Date dateToStart,
                          ArrayList<String> frequency, HabitEvent habitEvent) {
        this.userName = userName;
        this.title = title;
        this.reason = reason;
        this.dateToStart = dateToStart;
        this.frequency = frequency;
        this.habitEvent = habitEvent;
    }
}
