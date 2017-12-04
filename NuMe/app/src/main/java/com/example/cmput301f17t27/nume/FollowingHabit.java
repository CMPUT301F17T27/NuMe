package com.example.cmput301f17t27.nume;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by koivisto on 12/1/17.
 */

public class FollowingHabit implements Serializable {
    public String userName;
    public String title;
    public String reason;
    public Date dateToStart;
    public ArrayList<String> frequency;
    public HabitEvent habitEvent;


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
