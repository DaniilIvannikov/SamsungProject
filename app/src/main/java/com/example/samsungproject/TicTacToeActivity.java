package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class TicTacToeActivity extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_tic_tac_toe);
        ticTacToeBoard = findViewById(R.id.ticTacToeBoard);
        TextView winCheck = findViewById(R.id.wincheck_display);
        ticTacToeBoard.setupgame(winCheck);
    }


    public void btnPlayAgain(View v) {
        ticTacToeBoard.PlayAgainbtn();
        ticTacToeBoard.invalidate();
    }

    public void Backbutton(View view) {
        Intent intent5 = new Intent(this, SecondActivity.class);
        startActivity(intent5);
    }
}