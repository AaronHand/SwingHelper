package com.group2.swinghelper;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class GalleryActivity extends AppCompatActivity {

    private ListView lv;
    private ImageView thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        lv = (ListView) findViewById(R.id.swingList);
        thumb = (ImageView) findViewById(R.id.thumbnail);
        listPop();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void listPop(){
        final Swings_DB db = new Swings_DB(this);
        String[] from = {db.SWING_FILE_NAME,db.SWING_DATE };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                db.getAllSwings(),
                from,
                new int[] { android.R.id.text1, android.R.id.text2 });
        lv.setAdapter(adapter);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            Cursor c = db.getAllSwings();
            c.moveToPosition(position);
            Bitmap bitmap =ThumbnailUtils.createVideoThumbnail(Environment.getExternalStorageDirectory()+ "/Android/data/com.group2.swinghelper/files"+c.getString(Swings_DB.SWING_FILE_NAME_COL),MediaStore.Video.Thumbnails.MINI_KIND);
            thumb.setImageBitmap(bitmap);
            Log.d("--",Environment.getExternalStorageDirectory()+ "/Android/data/com.group2.swinghelper/files"+c.getString(Swings_DB.SWING_FILE_NAME_COL));
        });

    }

}
