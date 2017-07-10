package com.example.ashu.hometownlocation;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ashu on 19/3/17.
 */
public class ListHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener {
    private TextView nickView;
    private TextView countryView;
    private TextView stateView;
    private TextView cityView;
    private String name;
    public ListHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        nickView = (TextView) itemView.findViewById(R.id.nickname);
        countryView = (TextView) itemView.findViewById(R.id.country);
        stateView = (TextView) itemView.findViewById(R.id.state);
        cityView = (TextView) itemView.findViewById(R.id.city);
        Log.i("ListHolder", "Constructor");
    }
    public void bindName(String nick, String country, String state, String city) {
        this.name = nick;
        //Log.i("ListHolder", "bindname");
        nickView.setText(nick);
        countryView.setText(country);
        stateView.setText(state);
        cityView.setText(city);

    }

    public void onClick(View source) {
        Log.i("onClick", "on onclick");
        /*MainActivity.this.selection.setText(this.name);*/

    }
}