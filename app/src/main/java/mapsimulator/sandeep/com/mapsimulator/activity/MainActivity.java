package mapsimulator.sandeep.com.mapsimulator.activity;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import mapsimulator.sandeep.com.mapsimulator.R;
import mapsimulator.sandeep.com.mapsimulator.activity.MapsActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       Intent intent = new Intent(this, MapsActivity.class); //usual map

       // Intent intent = new Intent(this, StreetViewPanoramaBasicDemoActivity.class);
//        EditText editText = (EditText) findViewById(R.id.edit_message);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
