package com.dinidininta.spotifyassistant.services;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dinidininta.spotifyassistant.POJO.Track;
import com.dinidininta.spotifyassistant.VolleyCallBack;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TracklistService {

    private SharedPreferences mSharedPreferences;
    private RequestQueue mQueue;
    private ArrayList<Track> tracklist = new ArrayList<>();
    private String endpoint = "";

    public TracklistService(RequestQueue queue, SharedPreferences sharedPreferences){
        mQueue = queue;
        mSharedPreferences = sharedPreferences;
        endpoint = mSharedPreferences.getString("playlistHref", "");
    }

    public ArrayList<Track> getTracklist() {
        return tracklist;
    }

    public void get(final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, endpoint, null, response -> {
            try {
                JSONObject tracksObject = response.optJSONObject("tracks");
                Gson gson = new Gson();
                JSONArray jsonArray = tracksObject.optJSONArray("items");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i).optJSONObject("track");
                    Log.d("TracklistService", object.toString());
                    Track track = gson.fromJson(object.toString(), Track.class);
                    tracklist.add(track);
                }
            } catch (Exception e){
                e.printStackTrace();
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
