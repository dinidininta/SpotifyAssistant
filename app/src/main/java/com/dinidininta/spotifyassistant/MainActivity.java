package com.dinidininta.spotifyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView displayName = findViewById(R.id.display_name);
        TextView birthdate = findViewById(R.id.birthdate);
        TextView country = findViewById(R.id.country);
        TextView email = findViewById(R.id.email);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        displayName.setText(sharedPreferences.getString("displayName", ""));
        birthdate.setText(sharedPreferences.getString("birthdate", ""));
        country.setText(sharedPreferences.getString("country", ""));
        email.setText(sharedPreferences.getString("email", ""));
    }
}
