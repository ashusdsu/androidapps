package com.example.ashu.hometownlocation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class findFriends extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> countries = new ArrayList<String>();
    ArrayList<String> states = new ArrayList<String>();
    ArrayList<String> year = new ArrayList<String>();

    String url = "http://bismarck.sdsu.edu/hometown/users";

    public static ArrayList<String> countryList = new ArrayList<String>();
    public static ArrayList<String> stateList = new ArrayList<String>();
    public static ArrayList<String> nickList = new ArrayList<String>();
    public static ArrayList<String> cityList = new ArrayList<String>();
    public static ArrayList<String> Latitude = new ArrayList<String>();
    public static ArrayList<String> Longitude = new ArrayList<String>();

    String userCountry = "India";
    static int flag = 0;
    public static String userState;
    String userCity;
    EditText yearEdit;
    String ye = "z";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friends);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
// fetch data

        } else {
// display error
            Log.i("info", "error in connection");
        }

        //states.add("Select State");
        yearEdit = (EditText) this.findViewById(R.id.yearEdit);
        //yearEdit.setText("XXXX");

        //Log.i("year",ye);
        getRequest();
        getUsers();
        //addItemOnYearSpinner();
        /*SampleTask x = new SampleTask();
        x.execute();*/
    }



    public void getUsers() {
        //start
        Log.i("users", "start");
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("users", "response available");
                //code to write which adapter to call
                nickList.clear();
                countryList.clear();
                stateList.clear();
                cityList.clear();
                Latitude.clear();
                Longitude.clear();


                try {
                    for (int i = 0; i < response.length(); i++)

                    {
                        JSONObject data = response.getJSONObject(i);
                        nickList.add(data.getString("nickname"));
                        countryList.add(data.getString("country"));
                        stateList.add(data.getString("state"));
                        cityList.add(data.getString("year"));

                        Latitude.add(data.getString("latitude"));
                        Longitude.add(data.getString("longitude"));
                    }


                    Log.i("nickList", Integer.toString(nickList.size()));
                    Log.i("countryList", Integer.toString(countryList.size()));
                    Log.i("cityList", Integer.toString(cityList.size()));
                    Log.i("LatitudeList", Integer.toString(Latitude.size()));
                    Log.i("LongitudeList", Integer.toString(Longitude.size()));



                    /*JSONArray data1 = response.getJSONArray("nickname");
                    if (data1 != null) {
                        for (int i = 0; i < data1.length(); i++) {
                            nickList.add(data1.getString(i));
                        }
                        Log.i("nickList",Integer.toString(nickList.size()));

                    }
                    JSONArray data2 = response.getJSONArray("country");
                    if (data2 != null) {
                        for (int i = 0; i < data2.length(); i++) {
                            countryList.add(data2.getString(i));

                        }
                        Log.i("countryList",Integer.toString(countryList.size()));
                    }
                    JSONArray data3 = response.getJSONArray("state");
                    if (data3 != null) {
                        for (int i = 0; i < data3.length(); i++) {
                            stateList.add(data3.getString(i));

                        }
                        Log.i("stateList",Integer.toString(stateList.size()));
                    }
                    JSONArray data4 = response.getJSONArray("city");
                    if (data4 != null) {
                        for (int i = 0; i < data4.length(); i++) {
                            cityList.add(data4.getString(i));
                        }
                        Log.i("cityList",Integer.toString(cityList.size()));
                    }*/

                } catch (JSONException e) {
                    Log.i("JsonArray", "Problem here");

                    e.printStackTrace();
                }
                //recyclerview adapter must be called
                recyclerVA();
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };
        JSONObject data = new JSONObject();


        //String url = "http://bismarck.sdsu.edu/hometown/users?country="+userCountry+"&state="+userState;
        /*if (flag == 2)
        {
            //all users from the server

            String y = Integer.toString(year);
            Log.i("flag 2", y);
            url = "http://bismarck.sdsu.edu/hometown/users?country="+userCountry+"&state="+userState+"&year="+y;

        }
        else if (flag == 1)
        {
            url = "http://bismarck.sdsu.edu/hometown/users?country="+userCountry+"&state="+userState;
        }*/


        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);

    }

    public void recyclerVA() {
        //code for the main recycle view in the making
        RecyclerView recycle = (RecyclerView) findViewById(R.id.personrow);
        recycle.setLayoutManager(new LinearLayoutManager(this));


        RecyclerAdapter nameAdapter = new RecyclerAdapter(nickList, countryList, stateList, cityList);
        recycle.setAdapter(nameAdapter);
    }

    public void getRequest() {

        Log.i("rew", "Start");


        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("rew", response.toString());
                for (int i = 0; i < response.length(); i++) {
                    //JSONObject actor = response.getJSONObject(i);
                    try {
                        countries.add(response.getString(i));

                        Log.i("rew", countries.get(i));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                addItemOnCountrySpinner();
            }

        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };
        String url = "http://bismarck.sdsu.edu/hometown/countries";
        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);
    }

    public void addItemOnCountrySpinner() {
        Spinner country = (Spinner) findViewById(R.id.countryff);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        //adapter.add("Select Country");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setOnItemSelectedListener(this);
        country.setAdapter(adapter);
        //int sp = adapter.getPosition("Select Country");
        //Log.i("position",Integer.toString(sp));
        //country.setSelection(sp);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        userCountry = parent.getItemAtPosition(position).toString();
        getStateRequest();
        Log.i("item selected number", userCountry);
    }

    public void getStateRequest() {

        Log.i("rew", "Start");


        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("rew", response.toString());
                states.clear();
                for (int i = 0; i < response.length(); i++) {
                    //JSONObject actor = response.getJSONObject(i);
                    try {

                        states.add(response.getString(i));
                        addItemOnStateSpinner();
                        //Log.i("rew",states.get(i) );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };

        //selection=(TextView)findViewById(R.id.selection);
        flag = 1;

        String url = "http://bismarck.sdsu.edu/hometown/states?country=" + userCountry;
        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);

        //Log.i("First Country", countries.get(1));

    }

    public void addItemOnStateSpinner() {
        Spinner state = (Spinner) findViewById(R.id.stateff);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);
        //adapter.add("Select State");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setOnItemSelectedListener(new stateitemff());
        //state.setAdapter(null);
        state.setAdapter(adapter);

        //int sp = adapter.getPosition("Select State");
        //state.setSelection(sp);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("nothing selected", "");
    }

    public void updateList(View view) {
        //for the update List

        //String ye = "";
        flag = 1;
        ye = yearEdit.getText().toString();
        Log.i("inside update ", "updating list");


        if (ye.equals("")) {
            Log.d("update", ye);
            url = "http://bismarck.sdsu.edu/hometown/users?country=" + userCountry + "&state=" + userState;

            //flag = 1;

        } else {
            int x = Integer.parseInt(ye);
            Log.i("Yearentered", "Check");
            if (x>=1970 && x<=2017)
            {
                Log.d("flag2", ye);
                url = "http://bismarck.sdsu.edu/hometown/users?country=" + userCountry + "&state=" + userState + "&year=" + ye;
            }
            else
            {
                Toast.makeText(this, "Enter correct date between 1970 to 2017", Toast.LENGTH_LONG).show();
            }



        }
        getUsers();

        //getUpdatedUsers();

    }

    public void mapView(View view) {
        //for the mapView
        Intent mark = new Intent(this, MarkPostion.class);
        startActivity(mark);
    }

    public void listView(View view) {
        //for the ListView

    }

}

