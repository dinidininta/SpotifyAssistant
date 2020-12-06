package com.dinidininta.spotifyassistant.services;

import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dinidininta.spotifyassistant.POJO.Playlist;
import com.dinidininta.spotifyassistant.VolleyCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaylistService {

    private static final String ENDPOINT = "https://api.spotify.com/v1/me/playlists";
    private SharedPreferences mSharedPreferences;
    private RequestQueue mQueue;
    private ArrayList<Playlist> playlists = new ArrayList<>();

    public PlaylistService(RequestQueue queue, SharedPreferences sharedPreferences){
        mQueue = queue;
        mSharedPreferences = sharedPreferences;
    }

    public ArrayList<Playlist> getPlaylists(){
        return playlists;
    }

    public void get(final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ENDPOINT, null, response -> {
            Gson gson = new Gson();
            JSONArray jsonArray = response.optJSONArray("items");
            for(int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject object = jsonArray.getJSONObject(i);
                    Playlist playlist = gson.fromJson(object.toString(), Playlist.class);
                    playlists.add(playlist);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
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
