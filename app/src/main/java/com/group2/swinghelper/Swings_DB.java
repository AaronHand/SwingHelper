package com.group2.swinghelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Stefano on 7/29/15.
 */
public class Swings_DB {

    // DB constants STRINGs
    public static final String
        //DB Name
        DB_NAME = "swings_db.db",

        //columns names
        SWINGS_TABLE = "t_swings",
            SWING_ID = "_id",
            SWING_DATE = "date",
            SWING_PLAYER = "player",
            SWING_FILE_NAME = "fileName",

        // CREATE table statement, with the following relations:
        // SQLite-INTEGER = Java-long, SQLite-REAL = Java-double, SQLite-TEXT = Java-String
        CREATE_SWINGS_TABLE =
            "CREATE TABLE " + SWINGS_TABLE + " (" +
                    SWING_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SWING_DATE   + " INTEGER, " +
                    SWING_PLAYER + " TEXT, " +
                    SWING_FILE_NAME + " TEXT);",

        // DROP table statement
        DROP_SWINGS_TABLE = "DROP TABLE IF EXISTS " + SWINGS_TABLE;

    // DB constants INTs
    public static final int
        //BD version number
        DB_VERSION = 1,
        //Define the position of the columns in the table
        SWING_ID_COL = 0,
        SWING_DATE_COL = 1,
        SWING_PLAYER_COL = 2,
        SWING_FILE_NAME_COL = 3;


    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    //DB constructor
    public Swings_DB (Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }


    // DB Helper INNER CLASS will create the DB
    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            //Log.d("Tip_DB", "DBHelper called");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create t_swing
            //try {
            db.execSQL(CREATE_SWINGS_TABLE);
            //} catch (SQLException e) {
            //e.printStackTrace();
            //}

            // insert default/test rows: id, date, player, filename
            long time = System.currentTimeMillis();
            db.execSQL("INSERT INTO " + SWINGS_TABLE + " VALUES (NULL, " + time + ", 'Stefano', 'Swing1.mp4')");
            db.execSQL("INSERT INTO " + SWINGS_TABLE + " VALUES (NULL, " + time + ", 'Aaron', 'Swing_two.mp4')");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d("Tip_DB", "Upgrading DB from version " + oldVersion + " to " + newVersion);

            db.execSQL(DROP_SWINGS_TABLE);
            Log.d("Tip_DB", "Table dropped");
            onCreate(db);
        }
    }


    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() { db = dbHelper.getWritableDatabase(); }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    private void closeCursor(Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }


    private static Swing getSwingFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                //create a new Tip from the cursor
                Swing swing = new Swing(
                        cursor.getInt(SWING_ID_COL),
                        cursor.getInt(SWING_DATE_COL),
                        cursor.getString(SWING_PLAYER_COL),
                        cursor.getString(SWING_FILE_NAME_COL));
                return swing;
            }
            catch(Exception e) {
                return null;
            }
        }
    }


    // public methods
    public ArrayList<Swing> getAllSwings() {
        //String where = "1";

        this.openReadableDB();
        //Cursor cursor = db.query(TIP_TABLE, null, where, null, null, null, null);
        String[] columns = {SWING_ID, SWING_DATE, SWING_PLAYER, SWING_FILE_NAME};
        Cursor cursor = db.query(SWINGS_TABLE, columns, null, null, null, null, null);
        ArrayList<Swing> swings = new ArrayList<Swing>();
        while (cursor.moveToNext()) {
            swings.add(getSwingFromCursor(cursor));
        }

        this.closeCursor(cursor);
        this.closeDB();

        return swings;
    }

    //retrieve a Swing (a row) with a specific ID from the DB
    public Swing getLastSwing() {

        this.openReadableDB();

        String[] columns = {SWING_ID, SWING_DATE, SWING_PLAYER, SWING_FILE_NAME};
        String where = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = SWING_DATE + " desc";

        Cursor cursor = db.query(SWINGS_TABLE, columns, where, whereArgs, groupBy, having, orderBy);
        cursor.moveToFirst();
        Swing swing = getSwingFromCursor(cursor);

        this.closeCursor(cursor);
        this.closeDB();

        return swing;
    }

    //add a row of data into the TIP table
    public long insertSwing(Swing s) {
        //fetch the data from the Swing parameter and put it in a ContentValues object
        ContentValues cv = new ContentValues();
        //cv.put(SWING_ID, "null"); //
        cv.put(SWING_DATE, s.getDateMillis());
        cv.put(SWING_PLAYER, s.getPlayer());
        cv.put(SWING_FILE_NAME, s.getFileName());

        //open the DB connection
        this.openWritableDB();
        //insert the new row data into the DB (from the ContentValues object)
        long newRowID = db.insert(SWINGS_TABLE, null, cv);
        //close the DB connection
        this.closeDB();
        //return the ID of the new row in the table
        return newRowID;
    }

    //update (modify) a row in the TIP table
    public int updateSwing(Swing s) {
        //fetch the data from the Swing parameter and put it in a ContentValues object
        ContentValues cv = new ContentValues();
        //cv.put(SWING_ID, s.getId());
        cv.put(SWING_DATE, s.getDateMillis());
        cv.put(SWING_PLAYER, s.getPlayer());
        cv.put(SWING_FILE_NAME, s.getFileName());

        //select the row to update
        String where = SWING_ID + "= ?";
        String[] whereArgs = { String.valueOf(s.getId()) };

        //open the DB connection
        this.openWritableDB();
        //update the data into the DB (with the data contained in the ContentValues object)
        int rowCount = db.update(SWINGS_TABLE, cv, where, whereArgs);
        //close the DB connection
        this.closeDB();

        //return the number of the row successfully updated
        return rowCount;
    }

    //delete a row in the TIP table
    public int deleteSwing(long id) {
        //select the row to delete
        String where = SWING_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        //open the DB connection
        this.openWritableDB();
        //delete the the row
        int rowCount = db.delete(SWINGS_TABLE, where, whereArgs);
        //close the DB connection
        this.closeDB();

        //return the number of the row deleted
        return rowCount;
    }





}