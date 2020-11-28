package com.dinidininta.spotifyassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dinidininta.spotifyassistant.POJO.Playlist;
import com.dinidininta.spotifyassistant.adapters.PlaylistsAdapter;

import java.util.ArrayList;

public class PlaylistsActivity extends AppCompatActivity {
    private RecyclerView rvPlaylists;
    private ArrayList<Playlist> playlists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        rvPlaylists = findViewById(R.id.rv_playlists);
        rvPlaylists.setHasFixedSize(false);

        // populate playlists here?
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvPlaylists.setLayoutManager(new LinearLayoutManager(this));
        PlaylistsAdapter playlistsAdapter = new PlaylistsAdapter(playlists);
        rvPlaylists.setAdapter(playlistsAdapter);
    }
}
