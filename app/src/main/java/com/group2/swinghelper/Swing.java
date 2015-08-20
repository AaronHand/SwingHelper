package com.group2.swinghelper;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stefano on 7/29/15.
 */
public class Swing {

    private long id;
    private long dateMillis;
    private String player;
    private String description;
    private String fileName;

    //constructors
    public Swing() {
        setId(0);
        setDateMillis(System.currentTimeMillis());
        setPlayer("");
        setDescription("");
        setFileName("");
    }

    public Swing(String filename){
        this.setDateMillis(System.currentTimeMillis());
        this.setDescription("");
        this.setPlayer("");
        this.setFileName(filename);
    }

    public Swing(long dateMillis, String player, String description, String fileName) {
        this.setDateMillis(dateMillis);
        this.setPlayer(player);
        this.setDescription(description);
        this.setFileName(fileName);
    }

    public Swing(long id, long dateMillis, String player, String description, String fileName) {
        this.setId(id);
        this.setDateMillis(dateMillis);
        this.setPlayer(player);
        this.setDescription(description);
        this.setFileName(fileName);
    }


    //getters
    public long getId() { return id; }
    public long getDateMillis() {
        return dateMillis;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDateStringFormatted() {
        // set the date with formatting
        Date date = new Date(dateMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d yyyy HH:mm:ss");
        return sdf.format(date);
    }

    public String getPlayer() { return player; }
    public String getDescription() { return description; }
    public String getFileName() { return fileName; }


    //setters
    public void setId(long id) { this.id = id; }
    public void setDateMillis(long dateMillis) { this.dateMillis = dateMillis; }
    public void setPlayer(String player) { this.player = player; }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    //toString
    public String toString() {

        String string =
                getDateStringFormatted() + ", " +
                getPlayer() + ", " +
                getDescription() + ", " +
                getFileName();
        return string;
    }



}
