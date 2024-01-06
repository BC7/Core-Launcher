package com.sevenbitstudios.corelauncher;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    ImageView cHomeBackground;
    String PREFS_NAME = "CoreLaunchPrefs";
    final int REQUEST_CODE_IMAGE = 1;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    cHomeBackground.setImageURI(uri);
                    savePreferences(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button homeScreenBtn = findViewById(R.id.homeScreenBtn);
        cHomeBackground = findViewById(R.id.homeScreenImage);

        homeScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("images/*");
            }
        });

        getPreferences();
    }

    private void getPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String homeImageUri = preferences.getString("homeImageUri", null);

        if(homeImageUri != null) {
            cHomeBackground.setImageURI(Uri.parse(homeImageUri));
        }
    }
    private void savePreferences(Uri uri) {
        SharedPreferences.Editor preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        preferences.putString("homeImageUrI", uri.toString());

        preferences.apply();
    }
}