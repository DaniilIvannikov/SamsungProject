package com.example.samsungproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class FastClickingActivity extends AppCompatActivity {

    ImageView red_button;
    TextView textView_info, textView_result, textViewNumbers;
    int currentTaps = 0;
    boolean Startgame = false;
    CountDownTimer timer;
    int resultbest = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        ((Window) w).getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_fast_clicking);
        red_button = findViewById(R.id.red_button);
        textView_info = findViewById(R.id.textView_info);
        textView_result = findViewById(R.id.textView_result);
        textViewNumbers = findViewById(R.id.textViewNumbers);
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        resultbest = preferences.getInt("highScore", 0);
        textView_result.setText("Best Result: " + resultbest);
        red_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Startgame) {
                    currentTaps++;
                    textViewNumbers.setText("" + currentTaps);
                } else {
                    textView_info.setText("Click, Click...");
                    Startgame = true;
                    timer.start();
                }
            }
        });

        timer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                long TimeTillEnd = (l / 1000) + 1;
                textView_result.setText("Time Remaining: " + TimeTillEnd);
            }

            @Override
            public void onFinish() {
                red_button.setEnabled(false);
                Startgame = false;
                textView_info.setText("Game Over!");
                textViewNumbers.setText("");
                if (currentTaps > resultbest) {
                    resultbest = currentTaps;

                    SharedPreferences preferences1 = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putInt("highScore", resultbest);
                    editor.apply();
                }

                textView_result.setText("Best Result: " + resultbest + "\nCurrent Taps: " + currentTaps);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        red_button.setEnabled(true);
                        textView_info.setText("Start Clicking");
                        currentTaps = 0;
                    }
                }, 2000);
            }
        };
    }

    public void Bkbutton(View view) {
        Intent intent6 = new Intent(this, SecondActivity.class);
        startActivity(intent6);
    }

}