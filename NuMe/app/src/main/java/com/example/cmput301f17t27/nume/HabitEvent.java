package com.example.cmput301f17t27.nume;

import android.location.Location;
import android.util.Log;

import java.io.Serializable;
import java.util.Date;


public class HabitEvent implements Serializable {
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



    public HabitEvent( String comment, String image, Double Long, Double Lat) {
        this.dateCompleted = new Date();

        if ( comment.length() <= 20 ) {
            this.comment = comment;
        }

        this.image = image;
        //this.location = location;
        this.Lat =Lat;
        this.Long =Long;
    }



    public Date getDateCompleted() {
        return dateCompleted;
    }



    public String getComment() {
        return comment;
    }



    public String getImage() {
        return image;
    }



    public Double getLat() {
        return Lat;
    }
    public Double getLong() {
        return Long;
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

    public void setLat(Double lat){
        this.Lat=lat;
    }
    public void setLong(Double Long){
        this.Long=Long;
    }
    public boolean voidLocation(){
        Log.d("Location", "voidLocation: "+this.getLat());
        if( this.getLat()==null ){
            return true;
        }
        return false;
    }
    public void setNullLocation(){
        this.Long=null;
        this.Lat=null;
    }
}
