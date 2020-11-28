package com.dinidininta.spotifyassistant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dinidininta.spotifyassistant.POJO.User;
import com.dinidininta.spotifyassistant.services.UserService;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences mSharedPreferences;

    private RequestQueue queue;

    private static final String CLIENT_ID = "83f153aac4c34b1290eb45d77a2521f5";
    private static final String REDIRECT_URI = "com.dinidininta.spotifyassistant://callback";
    private static final String SCOPES = "user-read-email,user-read-private,playlist-read-private,user-library-read";
    private static final int REQUEST_CODE = 412;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authenticateSpotify();

        mSharedPreferences = this.getSharedPreferences("SPOTIFY", 0);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE){
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()){
                case TOKEN:
                    editor = getSharedPreferences("SPOTIFY", 0).edit();
                    editor.putString("token", response.getAccessToken());
                    Log.d("LOGGING IN", "AUTHENTICATION TOKEN ACQUIRED");
                    editor.apply();
                    waitForUserInfo();
                    break;

                case ERROR:
                    break;

                    default:
            }
        }
    }

    // Single sign-on
    private void authenticateSpotify(){
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    private void waitForUserInfo(){
        UserService userService = new UserService(queue, mSharedPreferences);
        userService.get(() -> {
            User user = userService.getUser();
            editor = getSharedPreferences("SPOTIFY", 0).edit();
            editor.putString("userId", user.id);
            editor.putString("birthdate", user.birthdate);
            editor.putString("displayName", user.displayName);
            editor.putString("email", user.email);
            editor.putString("country", user.country);
            Log.d("STARTING", "USER INFORMATION ACQUIRED");
            editor.commit();
            startMainActivity();
        });
    }

    private void startMainActivity(){
        Intent newIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(newIntent);
    }
}
