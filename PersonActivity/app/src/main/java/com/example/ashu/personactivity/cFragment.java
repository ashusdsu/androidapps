package com.example.ashu.personactivity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.example.ashu.personactivity.R.array.countrylist;


/**
 * A simple {@link Fragment} subclass.
 */
public class cFragment extends ListFragment implements AdapterView.OnItemClickListener{

//country Fragment created
    String countryName  = "";
    public cFragment() {
        // Required empty public constructor
    }

    OnCountryListener count ;

@Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //inflate the layout view of the fragment
        return inflater.inflate(R.layout.fragment_c, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getting country names from the country.xml file with the name as countrylist
        String [] countryItem = getResources().getStringArray(R.array.countrylist);
        //Log.i("country", "count");
        //Adapter for showing the string list to the countryItem
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, countryItem);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }


        //when any country in the list is clicked
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {


        countryName = parent.getItemAtPosition(position).toString();
        count.setCountry(countryName);

        Log.i("rew","position of CLick" + countryName) ;
        Log.i("rew","id of Click" + id);

    }

    public interface OnCountryListener
    {
        public void setCountry (String name);
    }

    //attaching the context with the new activity
    //fragment captures the interface implementation during its onAttach lifecycle

    @Override
    public void onAttach(Context context)
    {

        super.onAttach(context);
        Log.i("test","in on Attach");

        Activity a = (Activity) context;

        try{
            //Log.i("test", "in Exception");
            count = (OnCountryListener) a;
            //typcasting the activiy of a to OnCountry Listener
        }catch(Exception e) {}
    }
}