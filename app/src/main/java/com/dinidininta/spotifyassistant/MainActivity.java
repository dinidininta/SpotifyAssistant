package com.dinidininta.spotifyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
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
        Button openPlaylist = findViewById(R.id.btn_open_playlist);
        Button openAssistant = findViewById(R.id.btn_open_assistant);

        SharedPreferences sharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        displayName.setText(sharedPreferences.getString("displayName", ""));
        birthdate.setText(sharedPreferences.getString("birthdate", ""));
        country.setText(sharedPreferences.getString("country", ""));
        email.setText(sharedPreferences.getString("email", ""));

        openPlaylist.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PlaylistsActivity.class);
            startActivity(intent);
        });

        openAssistant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AssistantActivity.class);
            startActivity(intent);
        });
    }
}
