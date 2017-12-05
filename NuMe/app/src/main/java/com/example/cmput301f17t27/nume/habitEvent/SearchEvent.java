package com.example.cmput301f17t27.nume.habitEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * Similar to the event class except the habit that this
 * event belongs to and the username that that habit belongs
 * to is supplied. This is so that each entry in our search
 * list can be a single object
 * @author CMPUT301F17T27
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


    public SearchEvent(String userName, String habitTitle, String comment,
                       double[] location, Date dateCompleted) {
        this.userName = userName;
        this.habitTitle = habitTitle;
        this.dateCompleted = dateCompleted;
        this.location = location;
        this.comment = comment;
    }
}
