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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AssistantService {
    private static final String NEXT_ENDPOINT = "https://api.spotify.com/v1/me/player/next";
    private static final String CURRENT_TRACK_ENDPOINT = "https://api.spotify.com/v1/me/player/currently-playing";
    private SharedPreferences mSharedPreferences;
    private RequestQueue mQueue;
    private Track currentTrack;

    public AssistantService(RequestQueue queue, SharedPreferences sharedPreferences){
        mQueue = queue;
        mSharedPreferences = sharedPreferences;
    }

    public Track getCurrentTrack(){
        return currentTrack;
    }

    public void fetchCurrentTrack(final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CURRENT_TRACK_ENDPOINT, null, response -> {
            try {
                JSONObject trackObject = response.optJSONObject("item");
                Gson gson = new Gson();
                currentTrack = gson.fromJson(trackObject.toString(), Track.class);
            } catch (Exception e){
                e.printStackTrace();
            }
            callBack.onSuccess();
        }, error -> {
            fetchCurrentTrack(error::printStackTrace);
        }){
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

    public void next(final VolleyCallBack callBack){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NEXT_ENDPOINT, null, response -> {
            Log.d("Clicking next", response.toString());
            callBack.onSuccess();
        }, error -> {
            next(error::printStackTrace);
        }){
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
        mQueue.stop();
    }
}
