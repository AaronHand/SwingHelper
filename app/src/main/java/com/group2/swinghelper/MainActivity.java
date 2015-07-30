package com.group2.swinghelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the DB
        Swings_DB db = new Swings_DB(this);

        ArrayList<Swing> swings = db.getAllSwings();
        for (Swing s : swings) {
            //for each row of the table log the values
            Log.d("Swings_DB", "Swing" + s.getId() + ": "  + s.getDateStringFormatted() +
                            ", " + s.getPlayer() +
                            ", " + s.getFileName()
            );

        }

        //insert Swing as new row of the table
        long time = System.currentTimeMillis();
        Swing s = new Swing(time, "Ted", "any_file.mp4");
        long newRowId = db.insertSwing(s);

        Log.d("Swings_DB", "Swing" + newRowId + " saved!: " + s.getDateStringFormatted() +
                ", " + s.getPlayer() +
                ", " + s.getFileName()
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
