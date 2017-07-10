package com.example.ashu.hometownlocation;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MarkPostion extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    static private GoogleMap mMap;
    private GoogleMap mx;
    public static double lati;
    public static double longi;

    private ProgressBar progressBar;
    ArrayList<String> la;
    ArrayList<String> lo;
    int flag = 1;
    LatLng sLatLng = new LatLng(0,0);
    String url = "http://bismarck.sdsu.edu/hometown/users";
    //ArrayList<String> nick = findFriends.nickList;
    ArrayList<LatLng> locationList = new ArrayList<LatLng>();
    static int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_postion);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
// fetch data

        } else {
// display error
            Log.i("info", "error in connection");
        }
        Bundle bundle = getIntent().getExtras();
        lo = bundle.getStringArrayList("Longitude");
        la = bundle.getStringArrayList("Latitude");
        /*lo.addAll(getIntent().getStringArrayListExtra("Longitude"));
        la.addAll(getIntent().getStringArrayListExtra("Latitude"));
*/
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(findFriends.nickList.size());

        for(int i = 0;i<findFriends.Latitude.size();i++)
        {
            /*lo.add(findFriends.Longitude.get(i));
            la.add(findFriends.Latitude.get(i));*/
            String l = findFriends.Latitude.get(i);
            String n = findFriends.Longitude.get(i);
            la.add(l);
            lo.add(n);
            Log.i("individual latitude", l);
        }

        Log.i("Latitude", Integer.toString(findFriends.Latitude.size()));
        Log.i("Longitude", Integer.toString(findFriends.Latitude.size()));

        String s = "msg";
        SampleTask st = new SampleTask();

        st.execute(s);

        //requestUser();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

              Log.i("Async","in Background");
               /* String s = "msg";
                SampleTask st = new SampleTask();

                st.execute(s);*/

/*        CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(newLatLng, 1);

        mMap.moveCamera(newLocation);*/
        LatLng newLatLng = new LatLng(0,0);
        Log.i("length",Integer.toString(locationList.size()));

        for (int i=0;i<locationList.size();i++)
        {

            mMap.setOnMapClickListener(MarkPostion.this);
            mMap.addMarker(new MarkerOptions().position(locationList.get(i)).title(findFriends.nickList.get(i)));
        }
        CameraUpdate newLocation;
        Log.i("flag",Integer.toString(findFriends.flag));
        Log.i("locationList",Integer.toString(locationList.size()));

        if (findFriends.flag == 1) {

            newLocation = CameraUpdateFactory.newLatLngZoom(sLatLng, 6);


        }
        else
        {
            //LatLng newLatLng = new LatLng(0,0);
            Log.i("cond","zoom out");
            newLocation = CameraUpdateFactory.newLatLngZoom(newLatLng, 1);


        }
        mMap.moveCamera(newLocation);
    }

    public void onMapClick(LatLng location)
    {
        Log.i("rew", "new Location " + location.latitude + " longitude " + location.longitude );
        lati = location.latitude;
        longi = location.longitude;
        //setMarker(lat, longi);
    }

    public void setList()
    {
        finish();
    }

    public void display()
    {
        Log.i("display", "in display");
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(MarkPostion.this);
    }



    private class SampleTask extends AsyncTask<String, Integer, Void> {
        protected Void doInBackground(String... ad) {
            //code to write getPosition

            Log.i("Latitude", Integer.toString(findFriends.Latitude.size()));
            Log.i("Longitude", Integer.toString(findFriends.Latitude.size()));
            Log.i("AsyncTask",ad[0]);

            for (int i = 0; i < findFriends.nickList.size(); i++) {
                longi = Double.parseDouble(lo.get(i));
                lati = Double.parseDouble(la.get(i));
                String usercountry = findFriends.countryList.get(i);
                String userstate = findFriends.stateList.get(i);
                String usernick = findFriends.nickList.get(i);
                //Log.i("Async","in loop");
                //Log.i("nickList",Integer.toString(findFriends.nickList.size()));
                Log.i("Geocoder", usernick);
                LatLng stateLatLng;

                if (longi == 0 && lati == 0) {
                    Geocoder locator = new Geocoder(MarkPostion.this);
                    String addrs = userstate + ", " + usercountry;
                /*String addrs = "";*/
                    //string can be passed in the parameter


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
                    stateLatLng = new LatLng(lati, longi);
                    MarkPostion.this.sLatLng = stateLatLng;
                    //MarkPostion.this.flag = 1;
                    locationList.add(stateLatLng);
                } else {
                    //Log.i("Geocode", usernick);
                    stateLatLng = new LatLng(lati, longi);
                    locationList.add(stateLatLng);

                    //CameraUpdate newLocation;

                /*mMap.setOnMapClickListener(MarkPostion.this);
                mMap.addMarker(new MarkerOptions().position(stateLatLng).title(usernick));*/

                }

               /* MarkPostion.mMap.setOnMapClickListener(MarkPostion.this);
                MarkPostion.mMap.addMarker(new MarkerOptions().position(stateLatLng).title(usernick));
                Log.i("fvalue", Integer.toString(findFriends.flag));*/
                /*CameraUpdate newLocation;
                if (findFriends.flag == 0) {
                    LatLng newLatLng = new LatLng(0,0);
                    newLocation = CameraUpdateFactory.newLatLngZoom(newLatLng, 19);

                }
                else
                {
                    LatLng sLatLng = new LatLng(longi, lati);
                    newLocation = CameraUpdateFactory.newLatLngZoom(sLatLng, 6);
                }
                mMap.moveCamera(newLocation);*/


                publishProgress(i);
                //Integer.toString((int) ((i / (float) findFriends.nickList.size() ) * 100))
            }

            return (null);
        }
        protected void onPostExecute(Void unused) {
            Toast.makeText(MarkPostion.this, "Done", Toast.LENGTH_SHORT)
                    .show();
            MarkPostion.this.display();

        }

        protected void onPreExecute() {
            Toast.makeText(MarkPostion.this, "Start", Toast.LENGTH_SHORT).show();
            MarkPostion.this.display();

        }
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Toast.makeText(MarkPostion.this,"Image Number"+values[0] , Toast.LENGTH_SHORT).show();
            progressBar.setProgress(values[0]);
            /*ProgressDialog dialog = ProgressDialog.show(MarkPostion.this, "",
                    "Loading. Please wait...", true);*/
        }
    }

}
