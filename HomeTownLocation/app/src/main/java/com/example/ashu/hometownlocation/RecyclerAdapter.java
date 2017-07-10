package com.example.ashu.hometownlocation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashu on 19/3/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ListHolder> {

    private ArrayList<String> nicknames;
    private ArrayList<String> country;
    private ArrayList<String> state;
    private ArrayList<String> city;
    public RecyclerAdapter(ArrayList<String> nick, ArrayList<String> country, ArrayList<String> state, ArrayList<String> city) {
        this.nicknames = nick;
        this.country = country;
        this.state = state;
        this.city = city;

    }
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_layout, parent, false);
        return new ListHolder(view);
    }
    public void onBindViewHolder(ListHolder holder, int position) {
        String nameHolder = nicknames.get(position);
        String countryHolder = country.get(position);
        String stateHolder = state.get(position);
        String cityHolder = city.get(position);
        holder.bindName(nameHolder, countryHolder, stateHolder, cityHolder);
    }
    public int getItemCount() { return nicknames.size(); }
}
