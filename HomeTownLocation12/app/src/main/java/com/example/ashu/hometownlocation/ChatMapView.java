package com.example.ashu.hometownlocation;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ChatMapView extends FragmentActivity implements GoogleMap.OnMapClickListener, OnMapReadyCallback{

    static private GoogleMap mMap;
    private GoogleMap mx;
    public static double lati;
    public static double longi;
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    int flag = 1;
    LatLng sLatLng = new LatLng(0,0);
    String selectedUser;
    String selectedUserEmail;
    String url = "http://bismarck.sdsu.edu/hometown/users";
    //ArrayList<String> nick = findFriends.nickList;
    ArrayList<LatLng> locationList = new ArrayList<LatLng>();
    static int i = 0;

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
                Intent n = new Intent(ChatMapView.this,MainActivity.class);
                startActivity(n);
                ///item.setChecked(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_map_view);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser u = firebaseAuth.getCurrentUser();

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
// fetch data

        } else {
// display error
            Log.i("info", "error in connection");
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(ChatList.selectedUser.size());

        String s = "msg";
        ChatMapView.SampleTask st = new ChatMapView.SampleTask();

        st.execute(s);
    }

    @Override
    public void onMapClick(LatLng location) {
        Log.i("rew", "new Location " + location.latitude + " longitude " + location.longitude );
        lati = location.latitude;
        longi = location.longitude;
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

            mMap.setOnMapClickListener(ChatMapView.this);
            mMap.addMarker(new MarkerOptions().position(locationList.get(i)).title(Integer.toString(i)+"/"+ChatList.selectedUser.get(i)));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    String[] t = marker.getTitle().split("/");
                    Log.i("loclist",t[0]);
                    int num = Integer.parseInt(t[0]);


                    Toast.makeText(ChatMapView.this, "Marker clicked" + t[0], Toast.LENGTH_LONG).show();
                    selectedUser = ChatList.selectedUser.get(num);

                    selectedUserEmail = ChatList.selectedUserEmail.get(num);


                    ChatMapView.this.callChatRoom();

                    return false;
                }
            });

        }
        CameraUpdate newLocation;
        Log.i("flag",Integer.toString(findFriends.flag));
        Log.i("locationList",Integer.toString(locationList.size()));

       /* if (findFriends.flag == 1) {

            newLocation = CameraUpdateFactory.newLatLngZoom(sLatLng, 6);


        }
        else
        {
            //LatLng newLatLng = new LatLng(0,0);
            Log.i("cond","zoom out");
            newLocation = CameraUpdateFactory.newLatLngZoom(newLatLng, 1);


        }*/
        newLocation = CameraUpdateFactory.newLatLngZoom(newLatLng, 1);
        mMap.moveCamera(newLocation);
    }

    public void callChatRoom()
    {
        Log.i("mapview selectedUser", selectedUser);
        Log.i("mv selectedUseremail", selectedUserEmail);
        Intent n = new Intent(ChatMapView.this, ChatRoom.class);
        n.putExtra("current user", ChatList.currentUser);
        n.putExtra("selected user", selectedUser);
        n.putExtra("current user email", ChatList.currentUserEmail);
        n.putExtra("selected user email", selectedUserEmail);
        startActivity(n);


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

        mapFragment.getMapAsync(ChatMapView.this);
    }


    private class SampleTask extends AsyncTask<String, Integer, Void> {
        protected Void doInBackground(String... ad) {
            //code to write getPosition

            Log.i("AsyncTask",ad[0]);
            for (int i = 0; i < ChatList.selectedUser.size(); i++) {
                //longi = Double.parseDouble(findFriends.Longitude.get(i));
                //lati = Double.parseDouble(findFriends.Latitude.get(i));
                String usercountry = ChatList.chatCountry.get(i);
                String userstate = ChatList.chatState.get(i);
                String usernick = ChatList.selectedUser.get(i);
                //Log.i("Async","in loop");
                //Log.i("nickList",Integer.toString(findFriends.nickList.size()));
                Log.i("Geocoder", usernick);
                LatLng stateLatLng;


                Geocoder locator = new Geocoder(ChatMapView.this);
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
                    ChatMapView.this.sLatLng = stateLatLng;
                    //MarkPostion.this.flag = 1;
                    locationList.add(stateLatLng);

                publishProgress(i);
                //Integer.toString((int) ((i / (float) findFriends.nickList.size() ) * 100))
            }

            return (null);
        }
        protected void onPostExecute(Void unused) {
            Toast.makeText(ChatMapView.this, "Done", Toast.LENGTH_SHORT)
                    .show();
            ChatMapView.this.display();

        }

        protected void onPreExecute() {
            Toast.makeText(ChatMapView.this, "Start", Toast.LENGTH_SHORT).show();
            ChatMapView.this.display();

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
