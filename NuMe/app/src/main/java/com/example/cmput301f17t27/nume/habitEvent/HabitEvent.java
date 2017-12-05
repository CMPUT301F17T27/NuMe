package com.example.cmput301f17t27.nume.habitEvent;

import java.io.Serializable;
import java.util.Date;

/**
 * An instantiation of this class represents a single habit
 * event for a habit.
 * @author CMPUT301F17T27
 * @version 1.0
 * @see
 * @since 1.0
 */
public class HabitEvent implements Serializable {
    private Date dateCompleted; //Date when the event was finished (When it was instantiated)
    private double[] location; //Location of where the event was created (null if not given)
    private byte[] image; //Byte data of the optional image supplied by the user
    private String comment; //Comment added to the event


    public HabitEvent(byte[] image, String comment, double[] location) {
        this.dateCompleted = new Date();
        this.image = image;
        this.comment = comment;
        this.location = location;
    }



    /**
     * Gets the date that the habit was created
     * @return The date that the habit was created
     */
    public Date getDateCompleted() {
        return dateCompleted;
    }



    /**
     * Get the image that the user attached to the event.
     * @return The byte data of the image. Null if not supplied
     */
    public byte[] getImage() {
        return image;
    }



    /**
     * Set the byte data of the image.
     * @param image The byte data of the image
     */
    public void setImage(byte[] image) {
        this.image = image;
    }



    /**
     * Gets the comment attached to the event
     * @return The comment
     */
    public String getComment() {
        return comment;
    }



    /**
     * Sets the comment for the habit event
     * @param comment Comment to set to
     */
    public void setComment(String comment) {
        this.comment = comment;
    }



    /**
     * Gets the location attached to the habit event
     * @return The lat, long values of the location. Null if not supplied
     */
    public double[] getLocation() {
        return location;
    }



    /**
     * Sets the location of the habit event
     * @param location The lat, long values to use. Null to mark as not supplied
     */
    public void setLocation(double[] location) {
        this.location = location;
    }
}
