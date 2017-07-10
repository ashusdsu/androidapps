package com.example.ashu.hometownlocation;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class findFriends extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> countries = new ArrayList<String>();
    ArrayList<String> states = new ArrayList<String>();
    ArrayList<String> year = new ArrayList<String>();
    ArrayList<String> idList = new ArrayList<String>();

    private FirebaseAuth firebaseAuth;
    SQLiteDatabase db;


    String url = "http://bismarck.sdsu.edu/hometown/users?reverse=true&page=0";

    //container  for the map view and sometimes with the
    public static ArrayList<String> countryList = new ArrayList<String>();
    public static ArrayList<String> stateList = new ArrayList<String>();
    public static ArrayList<String> nickList = new ArrayList<String>();
    public static ArrayList<String> cityList = new ArrayList<String>();
    public static ArrayList<String> Latitude = new ArrayList<String>();
    public static ArrayList<String> Longitude = new ArrayList<String>();
    public static ArrayList<User> UserList = new ArrayList<User>();
    public static ArrayList<User> UserDB = new ArrayList<User>();
    public static ArrayList<User> UserRecycle = new ArrayList<User>();

    //Making the container for the recylcer view
    public static ArrayList<String> recycleCountry = new ArrayList<String>();
    public static ArrayList<String> recycleState = new ArrayList<String>();
    public static ArrayList<String> recycleNick = new ArrayList<String>();
    public static ArrayList<String> recycleCity = new ArrayList<String>();
    //protected Handler handler;

    String userCountry = "India";
    static int flag = 0;
    public static String userState;
    String userCity;
    EditText yearEdit;
    String ye = "z";
    UserLiteDB namesHelper;
    public int z = 0;
    public int page = 0;
    public String uid = "";
    RecyclerAdapter nameAdapter;
    String link;

    protected Handler handler;

   /* boolean isLoading;
    int visibleThreshold = 5;
    int lastVisibleItem, totalItemCount;*/



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
        namesHelper = new UserLiteDB(this);
        //yearEdit.setText("XXXX");
        //db = namesHelper.getWritableDatabase();

        //Log.i("year",ye);
        getRequest();
        getUsers(page);
        //addItemOnYearSpinner();
        /*SampleTask x = new SampleTask();
        x.execute();*/
    }



    public void getUsers(int p) {
        //start
        Log.i("users", "start");
        Log.i("page number", Integer.toString(p));
        Log.i("getUsers_uid",uid);
        UserList.clear();
        Response.Listener<JSONArray> success = new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                Log.d("users", "response available");
                //code to write which adapter to call


                /*if (z == 1)
                {
                    idList.clear();
                    nickList.clear();
                    countryList.clear();
                    stateList.clear();
                    cityList.clear();
                    Latitude.clear();
                    Longitude.clear();
                    UserList.clear();
                    //z=0;
                }*/


                Log.i("get user", String.valueOf(UserList.size()));
                //coding part for firebase Auth

                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser u = firebaseAuth.getCurrentUser();

                if (u != null) {
                    Log.i("current user", firebaseAuth.getCurrentUser().getEmail());
                }
                else
                {
                    Log.i("current user", "null");
                }


                try {
                    for (int i = 0; i < response.length(); i++)

                    {
                        JSONObject data = response.getJSONObject(i);
                       /* idList.add(data.getString("id"));
                        nickList.add(data.getString("nickname"));
                        countryList.add(data.getString("country"));
                        stateList.add(data.getString("state"));
                        cityList.add(data.getString("year"));
                        */
                        Log.i("Adding user", data.getString("id"));
                        User user = new User();
                        user.user_id = data.getString("id");
                        user.countries = data.getString("country");
                        user.state = data.getString("state");
                        user.nick = data.getString("nickname");
                        user.city = data.getString("city");
                        user.year = data.getString("year");
                        user.Latitude = data.getString("latitude");
                        user.Longitude = data.getString("longitude");
                        Latitude.add(data.getString("latitude"));
                        Longitude.add(data.getString("longitude"));

                        //Adding it to the list

                        UserList.add(user);
                        //Getting the user list from the server
                    }

                    Log.i("UserList Size", Integer.toString(UserList.size()));
                    Log.i("nickList", Integer.toString(nickList.size()));
                    Log.i("countryList", Integer.toString(countryList.size()));
                    Log.i("cityList", Integer.toString(cityList.size()));
                    Log.i("LatitudeList", Integer.toString(Latitude.size()));
                    Log.i("LongitudeList", Integer.toString(Longitude.size()));



                } catch (JSONException e) {
                    Log.i("JsonArray", "Problem here");

                    e.printStackTrace();
                }
                //recyclerview adapter must be called
                //recyclerVA();
                if (z==1)
                {
                    Log.i("Filter", "getTestFilter");
                    getfilterDBUsers(uid);
                }else {

                    getunfilterDBusers(uid);
                }
            }
        };
        Response.ErrorListener failure = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("rew", error.toString());
            }
        };
        JSONObject data = new JSONObject();

        if (z == 1)
        {

            url = link+Integer.toString(p);
            Log.i("URL", url);
        }
        else
        {
             url = "http://bismarck.sdsu.edu/hometown/users?reverse=true&page="+Integer.toString(p);

        }



        JsonArrayRequest getRequest = new JsonArrayRequest(url, success, failure);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);

    }



    public void recyclerVA() {

        //code for the main recycle view in the making

        //entering the data to the list

        Log.i("inrecycle Lat", Integer.toString(Latitude.size()));
        Log.i("inrecycle Long", Integer.toString(Longitude.size()));
        final RecyclerView recycle = (RecyclerView) findViewById(R.id.personrow);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycle.setLayoutManager(linearLayoutManager);


        nameAdapter = new RecyclerAdapter(nickList, countryList, stateList, cityList, recycle);
        recycle.setAdapter(nameAdapter);


        nameAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.i("onLoadMore","true");

                findFriends.this.getUsers(findFriends.this.page);
                Log.i("UserRecycleSize", Integer.toString(UserRecycle.size()));
                /*for (int i=0;i<UserRecycle.size(); i++)
                {
                    //idList.add(UserRecycle.get(i).user_id);
                    nickList.add(UserRecycle.get(i).nick);
                    countryList.add(UserRecycle.get(i).countries);
                    // stateList.add(UserList.get(i).user_id);
                    stateList.add(UserRecycle.get(i).state);
                    cityList.add(UserRecycle.get(i).city);
                    //Latitude.add(UserRecycle.get(i).Latitude);
                    //Longitude.add(UserRecycle.get(i).Longitude);

                    Log.i("RecyclerAdapter",UserRecycle.get(i).nick);
                }*/
                /*nameAdapter.notifyDataSetChanged();

                nameAdapter.setLoaded();*/

                //recycle.setAdapter(nameAdapter);


                Toast.makeText(findFriends.this, "Loading please wait",Toast.LENGTH_LONG ).show();
                //handler.;
            }
        });


    }



    public void addtoRecycle(ArrayList <User> recycleList)
    {
        //entering data to recycle view
        Log.i("addtoRecycle","Adding");
        UserRecycle.clear();
        uid = recycleList.get(24).user_id;
        //doAsyncTask();
        for (int i =0;i<recycleList.size();i++)
        {
            UserRecycle.add(recycleList.get(i));
            /*Longitude.add(recycleList.get(i).Longitude);
            Latitude.add(recycleList.get(i).Latitude);*/
            Log.i("long", Longitude.get(i));
            Log.i("lat", Latitude.get(i));
            nickList.add(recycleList.get(i).nick);
            countryList.add(recycleList.get(i).countries);
            stateList.add(recycleList.get(i).state);
            cityList.add(recycleList.get(i).city);
        }



        nameAdapter.notifyDataSetChanged();
        nameAdapter.setLoaded();


        Log.i("uid",uid);
        //recyclerVA();

    }

    public void addtoFirstTime(ArrayList <User> recycleList)
    {
        for (int i=0;i<recycleList.size(); i++)
        {
            idList.add(recycleList.get(i).user_id);
            nickList.add(recycleList.get(i).nick);
            countryList.add(recycleList.get(i).countries);
            // stateList.add(UserList.get(i).user_id);
            stateList.add(recycleList.get(i).state);
            cityList.add(recycleList.get(i).city);
           /* Latitude.add(recycleList.get(i).Latitude);
            Longitude.add(recycleList.get(i).Longitude);*/

        }
        uid = UserList.get(24).user_id;
        Log.i("uid",uid);
        recyclerVA();
    }

    public void getunfilterDBusers(String uid){
            //getUsers(page);
            Log.i("Unfilter", uid);
            Log.i("Unfilter",Integer.toString(UserList.size()));
            page++;
            if (TextUtils.equals(uid,""))
            {
                db = namesHelper.getWritableDatabase();
                //changing the sql line

                String query = "Select * from NAMES  order by ID DESC limit 25;";
                Cursor result = db.rawQuery(query, null);
                int rowCount = result.getCount();
                //starting of the application

                if (rowCount == 0)
                {

                    //getUsers(page);
                    //page++;
                    //push data into the database

                    Log.i("Unfilter", "No Records found in db");
                    addtoFirstTime(UserList);
                    Log.i("Unfilter","Recycle VIew");
                    //select();
                    doAsyncTask();
                    databaseInsertion(UserList);

                    select();


                    //get the uid of the 25th element of the list
                    //
                }
                else {
                    //getting the 25th record uid and comparing it
                    //getting records from the table
                    UserDB.clear();
                    Log.i("unfilter", "records found in db");

                    if(result.moveToFirst()){
                        do{

                            //Log.e("TAG", result1.getString(0));
                            User user = new User();
                            user.user_id = result.getString(0);
                                    user.nick = result.getString(1);
                                    user.countries = result.getString(2);
                                    user.state = result.getString(3);
                                    user.city = result.getString(4);
                                    user.year = result.getString(5);
                                    user.Longitude = result.getString(6);
                                user.Longitude = result.getString(7);

                            UserDB.add(user);
                        }
                        while (result.moveToNext());
                    }

                    Log.i("Unfilter", Integer.toString(UserDB.size()));
                    //compare the ids of the 25th of both the list
                    Log.i("Unfilter server uid", UserList.get(24).user_id);
                    Log.i("Unfilter db uid", UserDB.get(24).user_id);
                    select();
                    if (TextUtils.equals(UserList.get(24).user_id, UserDB.get(24).user_id))
                    {
                        Log.i("Unfilter", "No new records found");
                        addtoFirstTime(UserDB);
                        //databaseInsertion(UserDB);
                    }
                    else
                    {
                        Log.i("Unfilter", "New Records found");
                        doAsyncTask();
                        databaseInsertion(UserList);
                        addtoFirstTime(UserList);

                    }


                }



                //putting the last of server in uid
                //uid = UserList.get(24).user_id;

                //compare the end of server list and dblist

            }
            else{
                Log.i("Unfilter2","Uid found however");
                db = namesHelper.getWritableDatabase();
                databaseSelection(uid);
                Log.i("user database", String.valueOf(UserDB.size()));
                if (UserDB.size() < 25)
                {
                    Log.i("Unfilter", "Less Amount is found");
                    doAsyncTask();
                    databaseInsertion(UserList);
                    addtoRecycle(UserList);
                }
                else
                {
                    Log.i("Unfilter","Records found");
                    if (TextUtils.equals(UserList.get(24).user_id, UserDB.get(24).user_id))
                    {
                        Log.i("Unfilter2", "Records from DB");
                        addtoRecycle(UserDB);
                        //databaseInsertion(UserDB);
                    }
                    else
                    {
                        Log.i("Unfilter2","Adding records to db");
                        doAsyncTask();
                        databaseInsertion(UserList);
                        addtoRecycle(UserList);
                    }
                }
            }

        //recyclerVA();
    }

    //Under Construction

    public void gettestfilter(String uid)
    {
        Log.i("filter uid", uid );
        page++;
        Log.i("testfilter page", Integer.toString(page));
        addtoRecycle(UserList);

    }
    public void getfilterDBUsers(String uid)
    {
        //getUsers(page);
        Log.i("filter uid", uid);
        Log.i("filter ul size", Integer.toString(UserList.size()));
        page++;
        if (TextUtils.equals(uid,""))
        {
            db = namesHelper.getWritableDatabase();
            String query = "Select * from NAMES where COUNTRY = \""+ userCountry+"\" AND STATE = \""+ userState +"\" AND YEAR = \""+ ye + "\" ORDER BY ID DESC LIMIT 25";
            Cursor result = db.rawQuery(query, null);
            int rowCount = result.getCount();
            Log.i("filter", Integer.toString(rowCount));
            //starting of the application

            if (rowCount == 0)
            {
                //getUsers(page);
                //page++;
                //push data into the database

                Log.i("filter", "No Records found in db");
                addtoFirstTime(UserList);
                if(UserList.size() == 0)
                {
                    Toast.makeText(this, "No data Found", Toast.LENGTH_LONG).show();
                }
                Log.i("filter","Recycle View");
                //select();
                doAsyncTask();
                databaseInsertion(UserList);

                //select();
                //get the uid of the 25th element of the list
                //
            }
            else {
                //getting the 25th record uid and comparing it
                //getting records from the table
                UserDB.clear();
                Log.i("filter", "records found in db");

                if(result.moveToFirst()){
                    do{

                        //Log.e("TAG", result1.getString(0));
                        User user = new User();
                        user.user_id = result.getString(0);
                        user.nick = result.getString(1);
                        user.countries = result.getString(2);
                        user.state = result.getString(3);
                        user.city = result.getString(4);
                        user.year = result.getString(5);
                        user.Longitude = result.getString(6);
                        user.Longitude = result.getString(7);

                        UserDB.add(user);
                    }
                    while (result.moveToNext());
                }

                Log.i("filter", Integer.toString(UserDB.size()));
                //compare the ids of the 25th of both the list
                Log.i("filter server uid", UserList.get(24).user_id);
                Log.i("filter db uid", UserDB.get(24).user_id);
                select();
                if (TextUtils.equals(UserList.get(24).user_id, UserDB.get(24).user_id))
                {
                    Log.i("filter", "No new records found");
                    addtoRecycle(UserDB);
                    //databaseInsertion(UserDB);
                }
                else
                {
                    Log.i("filter", "New Records found");
                    doAsyncTask();
                    databaseInsertion(UserList);
                    addtoRecycle(UserList);

                }
            }
        }
        else{
            Log.i("filter2","Uid found however");
            db = namesHelper.getWritableDatabase();
            databaseSelection(uid);
            Log.i("filter 2 userdb", String.valueOf(UserDB.size()));
            if (UserDB.size() < 25)
            {
                Log.i("filter2", "Less Amount is found");
                doAsyncTask();
                databaseInsertion(UserList);
                addtoRecycle(UserList);
            }
            else
            {
                Log.i("filter2","Records found");
                Log.i("filter2 UserLid",UserList.get(UserList.size()-1).user_id);
                Log.i("filter2 UserDBid",UserDB.get(UserList.size()-1).user_id);
                if (TextUtils.equals(UserList.get(UserList.size()-1).user_id, UserDB.get(UserDB.size()-1).user_id))
                {
                    Log.i("filter2", "Records from DB");
                    addtoRecycle(UserDB);
                    //databaseInsertion(UserDB);
                }
                else
                {
                    Log.i("filter2","Adding records to db");
                    doAsyncTask();
                    databaseInsertion(UserList);
                    addtoRecycle(UserList);
                }
            }
        }
        //recyclerVA();
    }

    //Not Required as we are gonna call it from the DB only

    public boolean isDBEmpty(SQLiteDatabase db) {
        db = namesHelper.getWritableDatabase();
        String query = "Select count(*) from USERNAMES;";
        Cursor result = db.rawQuery(query, null);
        int rowCount = result.getCount();
        if (rowCount == 0)
        {
            return true;
        }
        return false;
    }

    //Adding country on the spinner

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

    public void databaseInsertion(ArrayList<User> list)
    {
        Log.i("Insertion","Yes");
        for (int i = 0;i<list.size();i++)
        {
            insert(list.get(i).user_id, list.get(i).nick, list.get(i).countries, list.get(i).state, list.get(i).city,list.get(i).year, list.get(i).Longitude, list.get(i).Latitude);
        }
    }

    public void insert(String uid, String nname, String country, String state, String city, String year, String longitude, String latitude)
    {
        SQLiteDatabase db = namesHelper.getWritableDatabase();
        ContentValues newName = new ContentValues();
        int i = Integer.parseInt(uid);
        newName.put("ID", i );
        newName.put("NICKNAME", nname);
        newName.put("COUNTRY", country);
        newName.put("STATE", state);
        newName.put("CITY", city);
        newName.put("YEAR", year);
        newName.put("LONGITUDE", longitude);
        newName.put("LATITUDE", latitude);
        db.insertWithOnConflict("NAMES", null, newName, db.CONFLICT_IGNORE);

        return;
    }

    public void select()
    {
        db = namesHelper.getWritableDatabase();
        //String query = "DROP TABLE NAMES";
        String query1 = "select * from NAMES order by ID desc limit 25";

        Cursor result1 = db.rawQuery(query1,null);

        int total = result1.getColumnCount();
        Log.i("TAG","Total: "+total);

       if(result1.moveToFirst()){
            do{
                //Log.e("TAG", result1.getString(0));
                Log.e("TAG","#0: "+result1.getInt(0)
                        +"#1: "+result1.getString(1)
                        +"#2: "+result1.getString(2)
                        +"#3: "+result1.getString(3)
                        +"#4: "+result1.getString(4)
                        +"#5: "+result1.getString(5)
                        +"#6: "+result1.getString(6)
                        +"#7: "+result1.getString(7)
                );
            }
            while (result1.moveToNext());
        }

        Log.i("TAG","Everything Displayed");
    }
    public void databaseSelection(String userID)
    {
        db = namesHelper.getWritableDatabase();
         String query;

        if (z == 1)
        {
            query = "Select * from NAMES where ID <"+userID+" AND Country = \""+ userCountry+"\" AND STATE = \""+ userState +"\" AND YEAR = \""+ ye + "\" ORDER BY ID DESC LIMIT 25";
        }
        else
        {
            query = "Select * from NAMES WHERE ID <"+userID+" ORDER BY ID DESC LIMIT 25";
        }
        UserDB.clear();

        Cursor result = db.rawQuery(query, null);
        int rowCount = result.getCount();
        Log.i("dbSelection", String.valueOf(rowCount));
        if (rowCount>0)
        {
            if (result.moveToFirst())
            {
                do
                {
                    User user = new User();

                    user.user_id = result.getString(0);
                    //Log.i("user_id",result.getString(0));
                    user.nick = result.getString(1);
                    user.countries = result.getString(2);
                    user.state = result.getString(3);
                    user.city = result.getString(4);
                    user.Longitude = result.getString(5);
                    user.Latitude = result.getString(6);
                    UserDB.add(user);
                    //Log.i("Data Entry Number",result.getString(0));
                }while (result.moveToNext());
            }
            Log.i("First Entry",UserDB.get(0).nick);
            //doubt whether the UserDB start from the 0 or 1
            //result.moveToFirst();
        }

    }

    //menu item selected for logout
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menulist, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.Logout:
                firebaseAuth.signOut();
                ///item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        z= 1;
        ye = yearEdit.getText().toString();
        Log.i("inside update ", "updating list");


        if (ye.equals("")) {
            Log.d("update", ye);
            //url = "http://bismarck.sdsu.edu/hometown/users?country=" + userCountry + "&state=" + userState;
            Toast.makeText(this, "Please Enter the date", Toast.LENGTH_LONG).show();
            //flag = 1;

        } else {
            int x = Integer.parseInt(ye);
            Log.i("Yearentered", "Check");
            if (x>=1970 && x<=2017)
            {
                Log.d("flag2", ye);
                link = "http://bismarck.sdsu.edu/hometown/users?country=" + userCountry + "&state=" + userState + "&year=" + ye +"&reverse=true&page=";
                uid = "";
                idList.clear();
                nickList.clear();
                countryList.clear();
                stateList.clear();
                cityList.clear();
                Latitude.clear();
                Longitude.clear();
                UserList.clear();
                page = 0;
                getUsers(page);
            }
            else
            {
                Toast.makeText(this, "Enter correct date between 1970 to 2017", Toast.LENGTH_LONG).show();
            }

        }


        //getUpdatedUsers();

    }

    public void mapView(View view) {
        //for the mapView
        Intent mark = new Intent(this, MarkPostion.class);
        mark.putStringArrayListExtra("Latitude", Latitude);
        mark.putStringArrayListExtra("Longitude", Longitude);
        startActivity(mark);
    }

    public void listView(View view) {
        //for the ListView

    }

    public void chatting(View view)
    {
        Intent chatList = new Intent (this, ChatList.class);
        startActivity(chatList);
    }

    //####################################################################################################

    //AsyncTask

    public void doAsyncTask()
    {
        SampleTask st = new SampleTask();
        String s = "msg";
        //st.execute(s);
    }

    private class SampleTask extends AsyncTask<String, Integer, Void>{
       protected Void doInBackground(String...ad) {
           Log.i("AsyncTask", ad[0]);
           for (int i = 0; i < UserList.size(); i++) {
               Log.i("AsyncTask","Ltlng");
               double longi = Double.parseDouble(UserList.get(i).Longitude);
               double lati = Double.parseDouble(UserList.get(i).Latitude);
               String usercountry = UserList.get(i).countries;
               String userstate = UserList.get(i).state;
               LatLng stateLatLng;

               if (longi == 0 && lati == 0) {
                   Geocoder locator = new Geocoder(findFriends.this);
                   String addrs = userstate + ", " + usercountry;

                   try {

                       List<Address> state = locator.getFromLocationName(addrs, 1);
                       for (Address stateLocation : state) {
                           if (stateLocation.hasLatitude())
                               lati = stateLocation.getLatitude();
                           if (stateLocation.hasLongitude())
                               longi = stateLocation.getLongitude();
                       }
                   } catch (Exception error) {
                       Log.e("rew", "Address lookup Error", error);
                   }

                   UserList.get(i).Longitude = String.valueOf(longi);
                   UserList.get(i).Latitude = String.valueOf(lati);
               }
           }
           return (null);
       }
    }
}

