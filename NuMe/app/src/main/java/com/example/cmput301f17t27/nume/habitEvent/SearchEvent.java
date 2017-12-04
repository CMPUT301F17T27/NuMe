package com.example.cmput301f17t27.nume.habitEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * Provides a list of HabitEvents to used in the maps and list displays
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class SearchEvent implements Serializable {
    public String userName;
    public String habitTitle;
    public String comment;
    public double[] location;
    public Date dateCompleted;


    /**
     * Creates the SearchEvent
     * @param userName
     * @param habitTitle
     * @param comment
     * @param location
     * @param dateCompleted
     */
    public SearchEvent(String userName, String habitTitle, String comment,
                       double[] location, Date dateCompleted) {
        this.userName = userName;
        this.habitTitle = habitTitle;
        this.dateCompleted = dateCompleted;
        this.location = location;
        this.comment = comment;
    }
}
