package com.dinidininta.spotifyassistant.services;

import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dinidininta.spotifyassistant.POJO.Playlists;
import com.dinidininta.spotifyassistant.VolleyCallBack;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class PlaylistService {

    private static final String ENDPOINT = "https://api.spotify.com/v1/me/playlists";
    private SharedPreferences mSharedPreferences;
    private RequestQueue mQueue;
    private Playlists playlists;

    public PlaylistService(RequestQueue queue, SharedPreferences sharedPreferences){
        mQueue = queue;
        mSharedPreferences = sharedPreferences;
    }

    public Playlists getPlaylists(){
        return playlists;
    }

    public void get(final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
            Gson gson = new Gson();
            playlists = gson.fromJson(response.toString(), Playlists.class);
            callBack.onSuccess();
        }, error -> get(() -> {

        })){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String token = mSharedPreferences.getString("token", "");
                String auth = "Bearer " + token;
                headers.put("Authorization", auth);
                return headers;
            }
        };
        mQueue.add(jsonObjectRequest);
    }
}
