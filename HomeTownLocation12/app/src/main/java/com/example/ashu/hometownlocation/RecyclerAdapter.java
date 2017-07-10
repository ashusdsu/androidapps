package com.example.ashu.hometownlocation;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

    //things for the
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;



    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;


    public RecyclerAdapter(ArrayList<String> nick, ArrayList<String> country, ArrayList<String> state, ArrayList<String> city, RecyclerView recycle) {
        this.nicknames = nick;
        this.country = country;
        this.state = state;
        this.city = city;

        if (recycle.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recycle
                    .getLayoutManager();


            recycle
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            Log.i("onScrolled","true");
                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            Log.i("Lastitem", Integer.toString(lastVisibleItem));
                            Log.i("totalitemCount", Integer.toString(totalItemCount));
                            Log.i("loading",Boolean.toString(loading));
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                //findFriends.getUsers(findFriends.page);
                                Log.i("Adapter", "Last Item Seen");
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }

    }



    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycle_layout, parent, false);
        return new ListHolder(view);
    }



    public void onBindViewHolder(ListHolder holder, int position) {

        if (holder instanceof ListHolder)
        {
            String nameHolder = nicknames.get(position);
            String countryHolder = country.get(position);
            String stateHolder = state.get(position);
            String cityHolder = city.get(position);
            holder.bindName(nameHolder, countryHolder, stateHolder, cityHolder);
        }
        else
        {
            //((ProgressViewHolder) holder).progressBar.setIndeterminate(true);

        }

    }
    public int getItemCount() { return nicknames.size(); }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            //progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

}


