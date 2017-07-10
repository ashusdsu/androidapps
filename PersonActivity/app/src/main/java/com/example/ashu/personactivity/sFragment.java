package com.example.ashu.personactivity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.ashu.personactivity.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.lang.String;

/**
 * A simple {@link Fragment} subclass.
 */
public class sFragment extends ListFragment implements AdapterView.OnItemClickListener{

    String country_name;

    public sFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_s, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState)  {
        super.onActivityCreated(savedInstanceState);
        //getting the country name and from the country fragment
        country_name = ((countrys)getActivity()).getCountry();
        if (country_name != null) {
            Log.i("test_state", country_name);
            //array list for all the state items from the file
            ArrayList<String> stateItem = new ArrayList<String>();

            //using assets in the state file activity
            try {
                //reading the states name from the assets with the file name as country_name
                InputStream statesFile = getActivity().getAssets().open(country_name);
                BufferedReader in = new BufferedReader(new InputStreamReader(statesFile));
                String s;
                int i = 0;
                //adding data into arraylist one by one
                while ((s = in.readLine()) != null) {
                    stateItem.add(s);
                    i++;
                }
                //arrayadapter for managing the list of the new
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, stateItem);
                setListAdapter(adapter);

            } catch (IOException e) {
                //catching the IOException
                Log.e("rew", "read Error", e);
            }

        }
        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        //Log.i("rew","position of CLick" + position) ;
        //Log.i("rew","id of Click" + id);
        //Selecting the state name which got clicked and then storing it stateSelect
        String stateSelect = parent.getItemAtPosition(position).toString();
        //calling the getState function present in the countrys activity
        ((countrys)getActivity()).getState(stateSelect);
    }

    //makking interface for transferring the state name to the main activity
    public interface OnStateListener{
        public void getState(String name);
    }

}
