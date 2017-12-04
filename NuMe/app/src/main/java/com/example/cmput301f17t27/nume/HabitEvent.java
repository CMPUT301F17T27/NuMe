package com.example.cmput301f17t27.nume;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;


public class HabitEvent implements Serializable {
    private Date dateCompleted;
    private double[] location;
    private byte[] image;
    private String comment;



    public HabitEvent(byte[] image, String comment, double[] location) {
        this.dateCompleted = new Date();
        this.image = image;
        this.comment = comment;
        this.location = location;
    }



    public Date getDateCompleted() {
        return dateCompleted;
    }



    public byte[] getImage() {
        return image;
    }


/*
    public Bitmap getImageBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
*/

/*
    public void setImage(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.image = stream.toByteArray();
    }
*/


    public void setImage(byte[] image) {
        this.image = image;
    }



    public String getComment() {
        return comment;
    }



    public void setComment(String comment) {
        this.comment = comment;
    }



    public double[] getLocation() {
        return location;
    }



    public void setLocation(double[] location) {
        this.location = location;
    }
}
