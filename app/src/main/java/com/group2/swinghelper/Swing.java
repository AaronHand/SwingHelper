package com.group2.swinghelper;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO: add comment attribute to the Swing object and in the DB
 */



/**
 * Created by Stefano on 7/29/15.
 */
public class Swing {

    private long id;
    private long dateMillis;
    private String player;
    private String fileName;


    //constructors
    public Swing() {
        setId(0);
        setDateMillis(System.currentTimeMillis());
        setPlayer("");
        setFileName("");
    }


    public Swing(long dateMillis, String player, String fileName) {
        //this.setId(id);
        this.setDateMillis(dateMillis);
        this.setPlayer(player);
        this.setFileName(fileName);
    }

    public Swing(long id, long dateMillis, String player, String fileName) {
        this.setId(id);
        this.setDateMillis(dateMillis);
        this.setPlayer(player);
        this.setFileName(fileName);
    }


    //getters
    public long getId() {
        return id;
    }
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
    public String getFileName() { return fileName; }


    //setters
    public void setId(long id) {
        this.id = id;
    }
    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }
    public void setPlayer(String player) {
        this.player = player;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    //toString
    public String toString() { return getDateStringFormatted(); }


}
