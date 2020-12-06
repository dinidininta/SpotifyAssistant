package com.dinidininta.spotifyassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dinidininta.spotifyassistant.POJO.Track;
import com.dinidininta.spotifyassistant.adapters.TracklistAdapter;
import com.dinidininta.spotifyassistant.services.TracklistService;

import java.util.ArrayList;

public class TracklistActivity extends AppCompatActivity {
    private RecyclerView rvTracklist;
    private ArrayList<Track> tracklist = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracklist);

        mSharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(this);
        rvTracklist = findViewById(R.id.rv_tracklist);
        rvTracklist.setHasFixedSize(true);

        fetchTracklist();
    }

    private void showRecyclerList(){
        rvTracklist.setLayoutManager(new LinearLayoutManager(this));
        TracklistAdapter tracklistAdapter =  new TracklistAdapter(tracklist);
        rvTracklist.setAdapter(tracklistAdapter);
    }

    private void fetchTracklist(){
        TracklistService tracklistService = new TracklistService(queue, mSharedPreferences);
        tracklistService.get(() -> {
            tracklist = tracklistService.getTracklist();
            for(Track track : tracklist){
                Log.d("TracklistActivity", track.name);
            }
            showRecyclerList();
        });
    }
}
