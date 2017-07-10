package com.example.ashu.hometownlocation;

/**
 * Created by ashu on 18/3/17.
 */
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;



public class stateitemff implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        String s = parent.getItemAtPosition(pos).toString();
        s = s.replaceAll("\\s","%20");
        findFriends.userState = s;
        //findFriends.userState = parent.getItemAtPosition(pos).toString();
        Log.i("stateItem", findFriends.userState);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        Log.i("Error", "Nothing Selected");
    }

}
