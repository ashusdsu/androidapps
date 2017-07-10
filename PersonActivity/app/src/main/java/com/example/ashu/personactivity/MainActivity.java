package com.example.ashu.personactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.String;

public class MainActivity extends AppCompatActivity {

    private String fname;
    private String lname;
    private String age;
    private String email;
    private String phone;
    private String birthdate;
    private String country;

    public static final String MyPREFERENCES = "MyPrefs";
    private static final int x = 23;
    private static final int y = 26;
    EditText tvfname, tvlname, tvage, tvemail, tvphone;
            TextView tvbirthdate, tvcountry;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES,0);



        //gettng data from shared preferences
        fname = sharedPreferences.getString("FirstName",null);
        lname = sharedPreferences.getString("LastName",null);
        age = sharedPreferences.getString("Age",null);
        email = sharedPreferences.getString( "Email",null);
        phone = sharedPreferences.getString("Phone",null);
        birthdate = sharedPreferences.getString("DOB",null);
        country = sharedPreferences.getString("CountryState",null);

        //initializing the editText of the
        tvfname = (EditText) this.findViewById(R.id.fname);
        tvlname = (EditText) this.findViewById(R.id.lname);
        tvage = (EditText) this.findViewById(R.id.age);
        tvemail = (EditText) this.findViewById(R.id.email);
        tvphone = (EditText) this.findViewById(R.id.phone);
        tvbirthdate = (TextView) this.findViewById(R.id.dob);
        tvcountry = (TextView) this.findViewById(R.id.country);

        //
        tvfname.setText(fname);
        tvlname.setText(lname);
        tvage.setText(age);
        tvemail.setText(email);
        tvphone.setText(phone);
        tvbirthdate.setText(birthdate);
        tvcountry.setText(country);



        final Button done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sharedPreferences

        fname = tvfname.getText().toString();
        lname = tvlname.getText().toString();
        age = tvage.getText().toString();
        email = tvemail.getText().toString();
        phone = tvphone.getText().toString();
                birthdate = tvbirthdate.getText().toString();
                country = tvcountry.getText().toString();


        //Using sharedPreferences for the usage of new things and making of the new file and storing it in the background

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FirstName", fname );
        editor.putString("LastName", lname);
        editor.putString("Age", age);
        editor.putString("Email", email);
        editor.putString("Phone", phone);
        editor.putString("DOB", birthdate);
        editor.putString("CountryState", country);

        editor.commit();
            }
        });

        // getting the cursor at the end of the text in the editText
        tvfname.setSelection(tvfname.getText().length());
        tvlname.setSelection(tvlname.getText().length());
        tvage.setSelection(tvage.getText().length());
        tvemail.setSelection(tvemail.getText().length());
        tvphone.setSelection(tvphone.getText().length());
        //tvbirthdate.setText(getIntent().getExtras().getString("DATE"));


    }
    //onclick function for the setting date
    public void setd(View v)
    {
        Intent intent = new Intent(this, date_activity.class);

        startActivityForResult(intent, x);

    }

    //setting the result and making of the
    public void onActivityResult(int req, int resCode, Intent data)
    {
        if (resCode == RESULT_OK)
        {
        switch (req) {
            case x:
                birthdate = data.getStringExtra("DATE");
                Log.i("testr", birthdate);
                tvbirthdate = (TextView) this.findViewById(R.id.dob);
                tvbirthdate.setText(birthdate);
                break;

            case y:
                country = data.getStringExtra("Country");
                //concatenating country and state
                country += ", " + data.getStringExtra("State");

                tvcountry.setText(country);
                break;

        }
        }

    }

    //onClick functionn for the setting country and state

    public void setc(View v)
    {
        Log.i("test","countrybtn");
        Intent intent = new Intent(this, countrys.class);
        startActivityForResult(intent, y);

    }


}
