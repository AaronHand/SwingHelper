package com.group2.swinghelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * TODO:
 */


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
            SWING_DESCRIPTION = "description",
            SWING_FILE_NAME = "fileName",

        // CREATE table statement, with the following relations:
        // SQLite-INTEGER = Java-long, SQLite-REAL = Java-double, SQLite-TEXT = Java-String
        CREATE_SWINGS_TABLE =
            "CREATE TABLE " + SWINGS_TABLE + " (" +
                    SWING_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SWING_DATE   + " INTEGER, " +
                    SWING_PLAYER + " TEXT, " +
                    SWING_DESCRIPTION + " TEXT, " +
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
        SWING_DESCRIPTION_COL = 3,
        SWING_FILE_NAME_COL = 4;


    // database and database helper objects
    private static SQLiteDatabase db;
    private DBHelper dbHelper;
    private static int lastID = 0;

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
            db.execSQL("INSERT INTO " + SWINGS_TABLE + " VALUES (NULL, " + time + ", 'Stefano', 'Description1', 'Swing1.mp4')");
            lastID++;
            db.execSQL("INSERT INTO " + SWINGS_TABLE + " VALUES (NULL, " + time + ", 'Aaron', 'Description2', 'Swing_two.mp4')");
            lastID++;

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
                        cursor.getString(SWING_DESCRIPTION_COL),
                        cursor.getString(SWING_FILE_NAME_COL));
                return swing;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    //return the maximum ID currently present in the DB
    public static int getMaxID() {
        return lastID;
    }


    // public methods
    public ArrayList<Swing> getAllSwings() {

        this.openReadableDB();
        String[] columns = {SWING_ID, SWING_DATE, SWING_PLAYER, SWING_DESCRIPTION, SWING_FILE_NAME};
        Cursor cursor = db.query(SWINGS_TABLE, columns, null, null, null, null, null);
        ArrayList<Swing> swings = new ArrayList<>();
        while (cursor.moveToNext()) {
            swings.add(getSwingFromCursor(cursor));
        }

        this.closeCursor(cursor);
        this.closeDB();

        return swings;
    }

    //add a row of data into the TIP table
    public long insertSwing(Swing s) {
        //fetch the data from the Swing parameter and put it in a ContentValues object
        ContentValues cv = new ContentValues();
        //cv.put(SWING_ID, "null"); //
        cv.put(SWING_DATE, s.getDateMillis());
        cv.put(SWING_PLAYER, s.getPlayer());
        cv.put(SWING_DESCRIPTION, s.getDescription());
        cv.put(SWING_FILE_NAME, s.getFileName());

        //open the DB connection
        this.openWritableDB();
        //insert the new row data into the DB (from the ContentValues object)
        long newRowID = db.insert(SWINGS_TABLE, null, cv);
        //close the DB connection
        this.closeDB();
        //increment lastID
        lastID++;
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
        cv.put(SWING_DESCRIPTION, s.getDescription());
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
