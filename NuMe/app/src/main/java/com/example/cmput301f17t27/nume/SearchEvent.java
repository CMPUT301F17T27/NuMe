package com.example.cmput301f17t27.nume;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Colman on 03/12/2017.
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
