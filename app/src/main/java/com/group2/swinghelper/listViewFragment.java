package com.group2.swinghelper;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tedst on 8/8/2015.
 */
public class listViewFragment extends Fragment {

    private ListView lv;
    Swings_DB db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        lv = (ListView) view.findViewById(R.id.dbListView);
        db = new Swings_DB(getActivity().getApplicationContext());

        String group;

        //ArrayList<Swing> swings = db.getAllSwings();
        ArrayList<String> swingscat = new ArrayList<>();

//concatenating every four items of the db to form the group of string that will fill each row in the listview
        for (int i=0;swings != null && i < swings.size();i+=4) {
            group = "";
            if (i<swings.size()) {
                for (int i2 = 0; i2 < 3; i2++) {
                    if (group == "") {
                        group = swings.get(i).toString();
                    } else {
                        group = group + " " + swings.get(i).toString();
                    }
                }
                swingscat.add(group);
            }
        }

        Swing[] arr = new Swing[3];
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);

        ListAdapter la = new ArrayAdapter<Swing>(this,android.R.layout.simple_list_item_1,arr);


        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, swingscat);
        lv.setAdapter(itemsAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
