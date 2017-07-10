package com.example.ashu.personactivity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;

public class date_activity extends AppCompatActivity {

    private int dd ;
        private int mm ;
        private int yyyy ;

        private String fdate;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_date_activity);
            //using datepicker for taking date from the user
            final DatePicker dp = (DatePicker) findViewById(R.id.dp);

            final Button setBtn = (Button) findViewById(R.id.btn);

            //onClicklistener for putting data in getIntent putting data in the i intent and setting result as resultOK
            //the reason behind setting the result is to signify that the data is good to read

            setBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dd = dp.getDayOfMonth();
                    mm = dp.getMonth() + 1;
                    yyyy = dp.getYear();
                    fdate = String.valueOf(dd) + "-" + String.valueOf(mm) + "-" + String.valueOf(yyyy);

                    Intent i = getIntent();

                    i.putExtra("DATE", fdate);

                    setResult(RESULT_OK, i);

                    finish();
                }
            });

        }
        //onCLick function for the cancel button
    public void cancelDate()
    {
        finish();
    }

}
