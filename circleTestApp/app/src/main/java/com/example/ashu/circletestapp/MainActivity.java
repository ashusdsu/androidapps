package com.example.ashu.circletestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity  {

    public static int modeSelect = 1;
    public static String colorValue = "Black";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId())
    {
        case R.id.Draw:
            modeSelect = 1;
            item.setChecked(true);
            return true;
        case R.id.Delete:
            modeSelect = 2;
            item.setChecked(true);
            return true;
        case R.id.Move:
                modeSelect = 3;
            item.setChecked(true);
            return true;
        case R.id.Green:
                colorValue = "GREEN";
            item.setChecked(true);
            return true;
        case R.id.Black:
            colorValue = "BLACK";
            item.setChecked(true);
            return true;
        case R.id.Red:
            colorValue = "RED";
            item.setChecked(true);
            return true;
        case R.id.Blue:
            colorValue = "BLUE";
            item.setChecked(true);
            return true;

    }

        return super.onOptionsItemSelected(item);

    }

    public String getcolorValue()
    {
        return colorValue;
    }

    public int getmode()
    {
        return modeSelect;
    }

}



