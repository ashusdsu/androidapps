package com.example.ashu.hometownlocation;

/**
 * Created by ashu on 18/3/17.
 */

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class    stateItemSelectedListener implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
       MainActivity.userState = parent.getItemAtPosition(pos).toString();
        Log.i("stateItem", MainActivity.userState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
