package com.example.ashu.hometownlocation;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> countries = new ArrayList<String>();
    ArrayList<String> states = new ArrayList<String>();
    ArrayList<String> nickList = new ArrayList<String>();
    public static String userCountry;
    String password;
    String userNickName;
    String userCity;
    double longitude = 0;
    double latitude = 0;
    int year = 0;
    private static final int x = 23;


    public static String userState;

    EditText nickEdit, yearEdit, LongLatEdit, passwordEdit, cityEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
// fetch data

        } else {
// display error
            Log.i("info", "error in connection");
        }
        Log.i("on Create", "hiding");

        nickEdit = (EditText) this.findViewById(R.id.nickname);

        yearEdit = (EditText) this.findViewById(R.id.year);
        LongLatEdit = (EditText) this.findViewById(R.id.LongLat);
        passwordEdit = (EditText) this.findViewById(R.id.password);
        cityEdit = (EditText) this.findViewById(R.id.city);
        String LongLat = Double.toString(longitude) + ", " + Double.toString(latitude);
        LongLatEdit.setText(LongLat);

        getRequest();
        isUnique();

    }

    public void addItemOnCountrySpinner() {
        Spinner country = (Spinner) findViewById(R.id.country);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapter.add("Select Country");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setOnItemSelectedListener(this);
        country.setAdapter(adapter);
        int sp = adapter.getPosition("Select Country");
        country.setSelection(sp);

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

        //selection=(TextView)findViewById(R.id.selection);


        String url = "http://bismarck.sdsu.edu/hometown/countries";
        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);

        //Log.i("First Country", countries.get(1));

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

                        //Log.i("rew",states.get(i) );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                addItemOnStateSpinner();
            }

        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };

        //selection=(TextView)findViewById(R.id.selection);


        String url = "http://bismarck.sdsu.edu/hometown/states?country=" + userCountry;
        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);

        //Log.i("First Country", countries.get(1));

    }

    public void addItemOnStateSpinner() {
        Spinner state = (Spinner) findViewById(R.id.state);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);
        adapter.add("Select States");
        int sp = adapter.getPosition("Select State");
        state.setSelection(sp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setOnItemSelectedListener(new stateItemSelectedListener());
        state.setAdapter(null);
        state.setAdapter(adapter);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("nothing selected", "");
    }

    public void onSubmit(View view) {
        boolean f;
        userNickName = nickEdit.getText().toString();

        String value = yearEdit.getText().toString();
        if (value.equals("")){
            Toast.makeText(this, "Please Fill the year", Toast.LENGTH_LONG).show();
            return;
        }
        year = Integer.parseInt(value);
        userCity = cityEdit.getText().toString();

        password = passwordEdit.getText().toString();

        Log.i("onSubmit NickName", userNickName);
        Log.i("onSubmit year", Integer.toString(year));
        Log.i("onSubmit password", password);
        Log.i("onSubmit country", userCountry);
        Log.i("onSubmit state", userState);
        //Log.i("onSubmit city", userCity);
        Log.i("onSubmit Latitude", Double.toString(latitude));
        Log.i("onSubmit Longitude", Double.toString(longitude));

        if ((year>2017) || (year<1970) )
        {
            Toast.makeText(this, "Please enter the date in range", Toast.LENGTH_LONG).show();
            return ;
        }

        if (password.equals("") || userNickName.equals("") || userCity.equals("") || (password.length() < 3)) {
            Toast.makeText(this, "Please Fill all the details described above or increase password length", Toast.LENGTH_LONG).show();

            return;
        }


        f = eqi();
        if (f)
        {
            Toast.makeText(this, "Please add the unique nickname", Toast.LENGTH_LONG).show();
            Log.i("not unique", Boolean.toString(f));

            return ;
        }
        JSONObject data = new JSONObject();
        try {
            data.put("nickname", userNickName);
            data.put("password", password);
            data.put("country", userCountry);
            data.put("state", userState);
            data.put("city", userCity);
            data.put("year", year);
            data.put("latitude", latitude);
            data.put("longitude", longitude);

            Log.i("Json Submission", "Done No Exception");
            //toast about the submission

        } catch (JSONException error) {
            Log.e("rew", "Json error", error);
            return;
        }
        Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//Process response here
                Toast.makeText(MainActivity.this, "Submission Complete", Toast.LENGTH_LONG).show();
                Log.i("rew", response.toString());
            }
        };

        Response.ErrorListener failure = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("rew", "post fail " + new String(error.networkResponse.data));
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        };
        String url = "http://bismarck.sdsu.edu/hometown/adduser";
        JsonObjectRequest postRequest = new JsonObjectRequest(url, data, success, failure);
        VolleyQueue.instance(this).add(postRequest);


    }

    public void friendFinder(View view) {
        Intent dial = new Intent(this, findFriends.class);
        startActivity(dial);
    }

    public void setLocation(View view) {
        Intent location = new Intent(this, maps_loc.class);
        startActivityForResult(location, x);

    }

    public void onActivityResult(int req, int resCode, Intent data) {
        if (resCode == RESULT_OK) {
            switch (req) {
                case x:
                    longitude = data.getDoubleExtra("Longitude", 0);
                    latitude = data.getDoubleExtra("Latitude", 0);

            }
        }

        String LongLat = Double.toString(longitude) + ", " + Double.toString(latitude);
        LongLatEdit.setText(LongLat);

    }

    public void isUnique() {

        nickList.clear();

        Log.i("users", "start");
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("users", "response available");
                try {
                    for (int i = 0; i < response.length(); i++)

                    {
                        JSONObject data = response.getJSONObject(i);
                        nickList.add(data.getString("nickname"));

                    }


                    Log.i("nickList", Integer.toString(nickList.size()));

                } catch (JSONException e) {
                    Log.i("JsonArray", "Problem here");

                    e.printStackTrace();
                }

            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };
        JSONObject data = new JSONObject();


       /* for (int i=0;i<nickList.size();i++)
        {
            //nick.toLowerCase().equals(nickList.get(i).toLowerCase());

            if (nick.toLowerCase().equals(nickList.get(i).toLowerCase()));
            {
                f = false;
            }
        }
*/

        String url = "http://bismarck.sdsu.edu/hometown/users";

        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);




    }

    public boolean eqi()
    {
        boolean f = true;
        Log.i("nickinuni", userNickName);
        for (int i=0;i<nickList.size();i++)
        {
            //nick.toLowerCase().equals(nickList.get(i).toLowerCase());
            f = userNickName.toLowerCase().equals(nickList.get(i).toLowerCase());
            /*if (userNickName.toLowerCase().equals(nickList.get(i).toLowerCase()));
            {
                //Log.i("nickList",nickList.get(i).toLowerCase());
                f = false;
            }*/
        }
        Log.i("not unique", Boolean.toString(f));
        return f;
    }


}
