package com.example.ashu.personactivity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class countrys extends AppCompatActivity implements cFragment.OnCountryListener, sFragment.OnStateListener{

    String country_name;
    String state_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrys);

        Log.i("rew","inyuhj");
        cFragment fragment = new cFragment();
        //sFragment sfrag = new sFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity_countrys, fragment);

        /*if (savedInstanceState.getarg    getArguments() != null) {
            userName = getArguments().getString(“name”);
            userAge = getArguments().getString(“age”);*/

        //fragmentTransaction.replace(R.id.activity_countrys, sfrag);
        fragmentTransaction.commit();
        //getSupportFragmentManager().beginTransaction().replace(R.id.activity_countrys, sfrag).commit();
        //fragmentTransaction.add(R.id.activity_countrys,fragment); // add(R.id.activity_countrys, fragment);
        //fragmentTransaction.commit();
    }
        @Override
        public void setCountry (String name)
        {
            Log.i("test", "setcountry running");
            country_name = name;

            //sFragment state = (sFragment) getSupportFragmentManager().findFragmentById(R.id.sfragment);
            sFragment sfrag = new sFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_countrys, sfrag).commit();


           /* if (state != null) {
                state.updateInfo(name);
            }*/

        }

    @Override
    public void  getState(String name){
        Log.i("test","getstate running");
        state_name = name;
        Log.i("in getState",state_name);
        Log.i("in getState",country_name);


    }

    public void returnCountryState(View v)
    {
        if(country_name!= null)
        {
            if (state_name !=null)
            {
                Intent retCS = getIntent();//new Intent(this, MainActivity.class);
                retCS.putExtra("Country", country_name);
                retCS.putExtra("State", state_name);
                setResult(RESULT_OK,retCS);
                finish();
            }
            else{
                Toast.makeText(this,"Please select state",Toast.LENGTH_LONG ).show();
            }
        }
        else {
            Toast.makeText(this,"Please Select country and state",Toast.LENGTH_LONG ).show();

        }
        /*Intent retCS = getIntent();//new Intent(this, MainActivity.class);
        retCS.putExtra("Country", country_name);
        retCS.putExtra("State", state_name);
        setResult(RESULT_OK,retCS);
        finish();*/
    }

    public String getCountry(){
        return country_name;
    }

    public void cancelCountryState(View v)
    {
        finish();
    }

}
