package com.example.cmput301f17t27.nume.follows;

import com.example.cmput301f17t27.nume.habitEvent.HabitEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class FollowingHabit implements Serializable {
    public String userName;
    public String title;
    public String reason;
    public Date dateToStart;
    public ArrayList<String> frequency;
    public HabitEvent habitEvent;

    /**
     *
     * @param userName
     * @param title
     * @param reason
     * @param dateToStart
     * @param frequency
     * @param habitEvent
     */
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
