package com.example.ashu.clickcount;

import android.content.Context;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private int cCount = 0;
    private int bCount = 0;
    TextView tvClick, tvBack;

    public static final String MyPREFERENCES = "MyPrefs";

    SharedPreferences sharedpreferences;

    //onCreate start of the lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        if (savedInstanceState != null)
        {
            cCount = sharedpreferences.getInt("clickCount", 0);
            bCount = sharedpreferences.getInt("backCount", 0);

        }

        Log.i("test", "Instance Created "+String.valueOf(bCount) + " " + String.valueOf(cCount));
        setContentView(R.layout.activity_main);
        tvClick = (TextView) this.findViewById(R.id.editTextCount);
        tvBack = (TextView) this.findViewById(R.id.editBackCount);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          cCount++;
                                          tvClick.setText(String.valueOf(cCount));

                                      }
                                  }
        );
    }

    @Override
    //Setting the textView of click and backcount in resume
    protected void onResume() {
        super.onResume();
        tvClick.setText(String.valueOf(cCount));
        tvBack.setText(String.valueOf(bCount));
        Log.i("test", "onResume "+String.valueOf(bCount) + " " + String.valueOf(cCount));
    }

    @Override
    //Restoring the value of the new thing
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cCount = sharedpreferences.getInt("clickCount", 0);
        bCount = sharedpreferences.getInt("backCount", 0);

        Log.i("test", "Instance Created "+String.valueOf(bCount) + " " + String.valueOf(cCount));
    }

    @Override
    protected void onStop() {
        super.onStop();
        //bCount += 1;

        if (!isChangingConfigurations())
        {
            bCount++;
        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //Using sharedPreferences for the usage of new things and making of the new

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("clickCount", cCount);
        editor.putInt("backCount", bCount);

        editor.commit();

        Log.i("test", "Onstop "+String.valueOf(bCount) + " " + String.valueOf(cCount));

    }

    @Override
        //Saving instance bundle for future use
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putInt("clickCount", cCount);
        outState.putInt("backCount", bCount);

        Log.i("test", "On SavedInstance "+String.valueOf(bCount) + " " + String.valueOf(cCount));
    }

}
