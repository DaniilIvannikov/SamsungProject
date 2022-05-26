package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);
    }

    public void Music(){
        MediaPlayer mPlayer = MediaPlayer.create(this, R.raw.musicforgame);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    public void btnClick(View v) {
        Music();
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}