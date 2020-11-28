package com.dinidininta.spotifyassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dinidininta.spotifyassistant.POJO.Playlist;
import com.dinidininta.spotifyassistant.POJO.Playlists;
import com.dinidininta.spotifyassistant.adapters.PlaylistsAdapter;
import com.dinidininta.spotifyassistant.services.PlaylistService;

import java.util.ArrayList;

public class PlaylistsActivity extends AppCompatActivity {
    private RecyclerView rvPlaylists;
    private ArrayList<Playlist> playlists = new ArrayList<>();
    private SharedPreferences mSharedPreferences;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        mSharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(this);
        rvPlaylists = findViewById(R.id.rv_playlists);
        rvPlaylists.setHasFixedSize(true);

        fetchUserPlaylists();
    }

    private void showRecyclerList(){
        rvPlaylists.setLayoutManager(new LinearLayoutManager(this));
        PlaylistsAdapter playlistsAdapter = new PlaylistsAdapter(playlists);
        rvPlaylists.setAdapter(playlistsAdapter);
    }

    private void fetchUserPlaylists(){
        PlaylistService playlistService = new PlaylistService(queue, mSharedPreferences);
        playlistService.get(() -> {
            Playlists fetchedPlaylists = playlistService.getPlaylists();
            for(int i = 0; i < fetchedPlaylists.items.length; i++){
                Playlist item = fetchedPlaylists.items[i];
                Playlist fetchedPlaylist = new Playlist();
                fetchedPlaylist.id = item.id;
                fetchedPlaylist.name = item.name;
                fetchedPlaylist.uri = item.uri;
                fetchedPlaylist.isPublic = item.isPublic;
                playlists.add(fetchedPlaylist);
            }
            showRecyclerList();
        });
    }
}
