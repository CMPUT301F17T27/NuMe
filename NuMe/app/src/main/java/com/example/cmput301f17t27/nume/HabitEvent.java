package com.example.cmput301f17t27.nume;

import android.location.Location;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HabitEvent implements Serializable{
    private String HabitName;
    private Date dateCompleted;
    private String comment;
    private String image;
    private Location location;
    private Double Lat;
    private Double Long;



    public HabitEvent() {
        this.dateCompleted = new Date();
    }



    public HabitEvent( String commentOrImage, int index ) {
        this.dateCompleted = new Date();

        if ( index == 0 ) {
            if ( commentOrImage.length() <= 20 ) {
                this.comment = commentOrImage;
            }
        }

        else if ( index == 1 ) {
            this.image = commentOrImage;
        }
    }



    public HabitEvent( String comment, String image ) {

        this.dateCompleted = new Date();

        if ( comment.length() <= 20 ) {
            this.comment = comment;
        }

        this.image = image;
    }



    public HabitEvent( String HabitName, String comment, String image, Double Long, Double Lat) {
        this.dateCompleted = new Date();

        if ( comment.length() <= 20 ) {
            this.comment = comment;
        }

        this.image = image;
        //this.location = location;
        this.Lat =Long;
        this.Long =Long;
        this.HabitName = HabitName;
    }




    public String getHabitName(){return HabitName;}

    public void setHabitName(String HabitName){this.HabitName = HabitName;}


    public Date getDateCompleted() {
        return dateCompleted;
    }



    public String getComment() {
        return comment;
    }



    public String getImage() {
        return image;
    }



    public Location getLocation() {
        return location;
    }



    public void setDateCompleted() {
        this.dateCompleted = new Date();
    }



    public void setComment(String comment) {
        if (comment.length() <= 20) {
            this.comment = comment;
        }
    }



    public void setImage(String image) {
        this.image = image;
    }



    public void setLocation(Location location) {
        this.location = location;
    }

//    @Override
//    public int compareTo(HabitEvent habitEvent) {
//        Date habitEventCompletedDate = habitEvent.getDateCompleted();
//        long numericalDateRepresentation = habitEventCompletedDate.getTime();
//        int newInt = (int) numericalDateRepresentation;
//
//        return this.getDateCompleted().getTime() - newInt;
//
//        /* For Descending order do like this */
//        //return compareage-this.studentage;
//    }



}
