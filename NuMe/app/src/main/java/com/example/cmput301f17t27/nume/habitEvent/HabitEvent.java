package com.example.cmput301f17t27.nume.habitEvent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Template for javaDoc
 * @author
 * @version 1.0
 * @see
 * @since 1.0
 */
public class HabitEvent implements Serializable {
    private Date dateCompleted;
    private double[] location;
    private byte[] image;
    private String comment;

    /**
     *
     * @param image
     * @param comment
     * @param location
     */
    public HabitEvent(byte[] image, String comment, double[] location) {
        this.dateCompleted = new Date();
        this.image = image;
        this.comment = comment;
        this.location = location;
    }


    /**
     *
     * @return
     */
    public Date getDateCompleted() {
        return dateCompleted;
    }


    /**
     *
     * @return
     */
    public byte[] getImage() {
        return image;
    }

    /**
     *
     * @param image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     *
     * @return
     */
    public String getComment() {
        return comment;
    }


    /**
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     *
     * @return
     */
    public double[] getLocation() {
        return location;
    }


    /**
     *
     * @param location
     */
    public void setLocation(double[] location) {
        this.location = location;
    }
}
