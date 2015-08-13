package com.group2.swinghelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 *
 * TODO:
 *  -- define the parameters for the sentEmail() method
 *
 */




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLaunch;
    private Button DarkSide;
    private Button btnList;
    private Button btnSend;
    private RelativeLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        setContentView(R.layout.activity_main);
        btnLaunch = (Button) findViewById(R.id.btnLaunch);
        btnLaunch.setOnClickListener(this);
*/
        setContentView(R.layout.activity_main);
        mainLayout=(RelativeLayout)findViewById(R.id.myLayout);
        btnLaunch = (Button) findViewById(R.id.btnLaunch);
        btnLaunch.setOnClickListener(this);
        DarkSide = (Button) findViewById(R.id.DarkSide);
        DarkSide.setOnClickListener(this);
        btnList = (Button) findViewById(R.id.btnList);
        btnList.setOnClickListener(this);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        mainLayout.setBackgroundResource(R.drawable.golfing);
    }//end onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }//end onCreateOptionsMenu()

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.menu_about:
                // Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }//end onOptionsItemSelected()

    @Override
    public void onClick(View v) {
/*
        Intent intent = new Intent(this,CameraActivity.class);
        startActivity(intent);
*/
        switch (v.getId()) {
            case R.id.btnLaunch:
                Intent intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.DarkSide:
                mainLayout.setBackgroundResource(R.drawable.vader);
                break;
            case R.id.btnList:
                //ToDo List code goes here
                break;
            case R.id.btnSend:
                sendEmail("driveLink", "playerName");
                break;

        }
    }//end onClick()



    //starts the client to send a message and incorporates the link to the video, uploaded on
    // Google Drive, into the text of the message
    protected void sendEmail(String driveLink, String playerName) {
        Log.i("Email", "Send email");
        String[] TO = {"To: "};
        String[] CC = {"Cc: "};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Evaluation request from" + playerName);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "" +
                "" +
                "\n" +
                driveLink);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Email", "Finished sending email...");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }//end sendEmail()




}
