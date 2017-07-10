package com.example.ashu.hometownlocation;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class maps_loc extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    public static double lat;
    public static double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_loc);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
// Add a marker in Sydney and move the camera
        Geocoder locator = new Geocoder(this);
        String addrs = MainActivity.userState+ ", "+MainActivity.userCountry;
        try {
            List<Address> state = locator.getFromLocationName(addrs,1);
            for (Address stateLocation: state) {
                if (stateLocation.hasLatitude())
                    lat = stateLocation.getLatitude();
                if (stateLocation.hasLongitude())
                    longi = stateLocation.getLongitude();
            }
        } catch (Exception error) {
            Log.e("rew", "Address lookup Error", error);
        }
        LatLng stateLatLng = new LatLng(lat, longi);
        CameraUpdate newLocation = CameraUpdateFactory.newLatLngZoom(stateLatLng, 6);
        mMap.moveCamera(newLocation);
        //LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(stateLatLng).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(this);
    }

    public void onMapClick(LatLng location)
    {
        Log.i("rew", "new Location " + location.latitude + " longitude " + location.longitude );
        lat = location.latitude;
        longi = location.longitude;
        setMarker(lat, longi);
    }
     public void setMarker(double la, double lo)
     {
         LatLng Loc = new LatLng(la,lo);
         mMap.clear();
         mMap.addMarker(new MarkerOptions().position(Loc).title("Your Location"));
     }

    public void set(View view)
    {
        if (lat == 0 && longi == 0)
        {
            Toast.makeText(this,"Please enter location",Toast.LENGTH_LONG ).show();
        }
        else {
            Log.i("set", "sending intent");
            Intent locIntent = getIntent();
            locIntent.putExtra("Longitude", longi);
            locIntent.putExtra("Latitude", lat);
            setResult(RESULT_OK, locIntent);
            finish();

        }
    }
}
