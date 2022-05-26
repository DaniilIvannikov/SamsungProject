package com.example.samsungproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private final List<SnakePoints> snakePointsList = new ArrayList<>();
    private SurfaceView surfaceView;
    private TextView showscore;
    private SurfaceHolder surfaceHolder;
    private int score = 0;
    private Paint point = null;
    private Paint pe = null;
    private Paint pe1 = null;
    private int positionX;
    private int positionY;
    private Timer timer;
    private Canvas canvas = null;
    private String movingPosition = "right";
    private static final int pointSize = 30;
    private static final int DefaultTalePoints = 3;
    private static final int snakeMovingSpeed = 800;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_snake);
        surfaceView = findViewById(R.id.surfaceView);
        showscore = findViewById(R.id.showscore);
        surfaceView.getHolder().addCallback(this);
        final AppCompatImageButton topbtn = findViewById(R.id.topbtn);
        final AppCompatImageButton leftbtn = findViewById(R.id.leftbtn);
        final AppCompatImageButton rightbtn = findViewById(R.id.rightbtn);
        final AppCompatImageButton bottombtn = findViewById(R.id.bottombtn);


        topbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingPosition.equals("bottom")){
                    movingPosition = "top";
                }
            }
        });

        leftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingPosition.equals("right")){
                    movingPosition = "left";
                }
            }
        });

        rightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingPosition.equals("left")){
                    movingPosition = "right";
                }
            }
        });

        bottombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!movingPosition.equals("top")){
                    movingPosition = "bottom";
                }
            }
        });
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) { }

    private void init(){
        snakePointsList.clear();
        showscore.setText("0");
        score = 0;
        movingPosition = "right";
        int startPositionX = (pointSize) * DefaultTalePoints;
        for (int i = 0; i < DefaultTalePoints; i++){
            SnakePoints snakePoints = new SnakePoints(startPositionX, pointSize);
            snakePointsList.add(snakePoints);
            startPositionX = startPositionX - (pointSize * 2);
        }
        addPoints();
        moveSnake();
    }

    private void addPoints(){
        int surfaceWidth = surfaceView.getWidth() - (pointSize * 2);
        int surfaceHeight = surfaceView.getHeight() - (pointSize * 2);
        int RandomPositionX = new Random().nextInt(surfaceWidth / pointSize);
        int RandomPositionY = new Random().nextInt(surfaceHeight / pointSize);
        if ((RandomPositionX % 2) != 0){
            RandomPositionX = RandomPositionX + 1;
        }
        if((RandomPositionY % 2) != 0){
            RandomPositionY = RandomPositionY + 1;
        }
        positionX = (pointSize * RandomPositionX) + pointSize;
        positionY = (pointSize * RandomPositionY) + pointSize;
    }

    private void moveSnake(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int TopPositionX = snakePointsList.get(0).getPositionX();
                int TopPositionY = snakePointsList.get(0).getPositionY();
                if(TopPositionX == positionX && TopPositionY == positionY){
                    SnakeIncrease();
                    addPoints();
                }
                switch (movingPosition){
                    case "right":
                        snakePointsList.get(0).setPositionX(TopPositionX + (pointSize * 2));
                        snakePointsList.get(0).setPositionY(TopPositionY);
                        break;
                    case "left":
                        snakePointsList.get(0).setPositionX(TopPositionX - (pointSize * 2));
                        snakePointsList.get(0).setPositionY(TopPositionY);
                        break;
                    case "top":
                        snakePointsList.get(0).setPositionX(TopPositionX);
                        snakePointsList.get(0).setPositionY(TopPositionY - (pointSize * 2));
                        break;
                    case "bottom":
                        snakePointsList.get(0).setPositionX(TopPositionX);
                        snakePointsList.get(0).setPositionY(TopPositionY + (pointSize * 2));
                        break;
                }
                if(CheckGameOver(TopPositionX, TopPositionY)){
                    timer.purge();
                    timer.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SnakeActivity.this);
                    builder.setMessage("Your Result: " + score);
                    builder.setTitle("GAME OVER");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Start Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            init();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }
                    });
                }
                else {
                    canvas = surfaceHolder.lockCanvas();
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                    canvas.drawCircle(snakePointsList.get(0).getPositionX(), snakePointsList.get(0).getPositionY(), pointSize, nt());
                    canvas.drawCircle(positionX, positionY, pointSize, paint());
                    for (int i = 1; i < snakePointsList.size(); i++){
                        int TempPositionX = snakePointsList.get(i).getPositionX();
                        int TempPositionY = snakePointsList.get(i).getPositionY();
                        snakePointsList.get(i).setPositionX(TopPositionX);
                        snakePointsList.get(i).setPositionY(TopPositionY);
                        canvas.drawCircle(snakePointsList.get(i).getPositionX(), snakePointsList.get(i).getPositionY(), pointSize, nt1());
                        TopPositionX = TempPositionX;
                        TopPositionY = TempPositionY;
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }, 1000 - snakeMovingSpeed, 1000 - snakeMovingSpeed);
    }

    private void SnakeIncrease(){
        SnakePoints snakePoints = new SnakePoints(0, 0);
        snakePointsList.add(snakePoints);
        score++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showscore.setText(String.valueOf(score));
            }
        });
    }

    private boolean CheckGameOver(int TopPositionX, int TopPositionY){
        boolean GameOver = false;
        if (snakePointsList.get(0).getPositionX() < 0 || snakePointsList.get(0).getPositionY() < 0 || snakePointsList.get(0).getPositionX() >= surfaceView.getWidth() || snakePointsList.get(0).getPositionY() >= surfaceView.getHeight()){
            GameOver = true;
        }
        else {
            for (int i = 1; i < snakePointsList.size(); i++){
                if (TopPositionX == snakePointsList.get(i).getPositionX() && TopPositionY == snakePointsList.get(i).getPositionY()){
                    GameOver = true;
                    break;
                }
            }
        }
        return GameOver;


    }

    private Paint paint(){
        if(point == null){
            point = new Paint();
            point.setColor(Color.MAGENTA);
            point.setStyle(Paint.Style.FILL);
            point.setAntiAlias(true);
        }
        return point;
    }

    private Paint nt(){
        if(pe == null){
            pe = new Paint();
            pe.setColor(Color.GREEN);
            pe.setStyle(Paint.Style.FILL);
            pe.setAntiAlias(true);
        }
        return pe;
    }

    private Paint nt1(){
        if(pe1 == null){
            pe1 = new Paint();
            pe1.setColor(Color.rgb(150, 250, 0));
            pe1.setStyle(Paint.Style.FILL);
            pe1.setAntiAlias(true);
        }
        return pe1;
    }


    public void BACKbtn1(View v) {
        timer.purge();
        timer.cancel();
        Intent intent10 = new Intent(this, SecondActivity.class);
        startActivity(intent10);
    }
}