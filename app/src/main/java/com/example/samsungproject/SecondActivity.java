package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_second);
    }

    public void GoBack(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnSnake(View v) {
        Intent intent1 = new Intent(this, SnakeActivity.class);
        startActivity(intent1);
    }

    public void btnTicTacToe(View v) {
        Intent intent2 = new Intent(this, TicTacToeActivity.class);
        startActivity(intent2);
    }

    public void btnFastTapping(View v) {
        Intent intent3 = new Intent(this, FastClickingActivity.class);
        startActivity(intent3);
    }

}