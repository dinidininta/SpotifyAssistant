package com.dinidininta.spotifyassistant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dinidininta.spotifyassistant.POJO.Track;
import com.dinidininta.spotifyassistant.services.AssistantService;

public class AssistantActivity extends AppCompatActivity {
    private AssistantService assistantService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assistant);

        SharedPreferences mSharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        RequestQueue queue = Volley.newRequestQueue(this);
        assistantService = new AssistantService(queue, mSharedPreferences);

        TextView currentTrack = findViewById(R.id.current_track);
        Button next = findViewById(R.id.next);

        assistantService.fetchCurrentTrack(() -> {
            Track track = assistantService.getCurrentTrack();
            currentTrack.setText(track.name);
        });
        next.setOnClickListener(v -> {
            assistantService.next(() -> {
                Log.d("AssistantActivity", "Next");
            });
        });
    }
}
