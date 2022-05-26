package com.example.samsungproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TicTacToeBoard extends View {

    private final int boardColor;
    private final int XColor;
    private final int OColor;
    private final int winningLineColor;
    private boolean winningLine = false;
    private int cSize = getWidth() / 3;

    private final Paint paint = new Paint();
    private final TicTacToeLogic game;

    public TicTacToeBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        game = new TicTacToeLogic();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.TicTacToeBoard, 0, 0);
        try {
            boardColor = a.getInteger(R.styleable.TicTacToeBoard_boardColor, 0);
            XColor = a.getInteger(R.styleable.TicTacToeBoard_XColor, 0);
            OColor = a.getInteger(R.styleable.TicTacToeBoard_OColor, 0);
            winningLineColor = a.getInteger(R.styleable.TicTacToeBoard_winningLineColor, 0);
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);
        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cSize = dimension / 3;
        setMeasuredDimension(dimension, dimension);
    }

    @Override
    protected void onDraw(Canvas canvas){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        drawGameBoard(canvas);
        drawMarkers(canvas);
        if (winningLine){
            paint.setColor(winningLineColor);
            DrawWinLine(canvas);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            int row = (int) Math.ceil(y / cSize);
            int col = (int) Math.ceil(x / cSize);

            if (!winningLine){
                if (game.updateTicTacToeBoard(row, col)){
                    invalidate();

                    if (game.CheckWinner()){
                        winningLine = true;
                        invalidate();
                    }


                    if (game.getPlayer() % 2 == 0){
                        game.setPlayer(game.getPlayer() - 1);
                    }
                    else {
                        game.setPlayer(game.getPlayer() + 1);
                    }
                }
            }
            invalidate();
            return true;
        }
        return false;
    }

    private void drawGameBoard(Canvas canvas) {
        paint.setColor(boardColor);
        paint.setStrokeWidth(16);
        for (int c=1; c<3; c++){
            canvas.drawLine(cSize * c, 0, cSize * c, canvas.getWidth(), paint);
        }
        for (int r=1; r<3; r++){
            canvas.drawLine(0, cSize * r, canvas.getWidth(), cSize * r, paint);
        }
    }

    private void drawMarkers(Canvas canvas) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (game.getTicTacToeBoard()[r][c] != 0) {
                    if (game.getTicTacToeBoard()[r][c] == 1) {
                        Xdraw(canvas, r, c);
                    } else {
                        Odraw(canvas, r, c);
                    }
                }
            }
        }
    }

    private void Xdraw(Canvas canvas, int row, int col){
        paint.setColor(XColor);
        canvas.drawLine((float)((col+1)*cSize - cSize * 0.1), (float)(row * cSize + cSize * 0.1), (float)(col * cSize + cSize * 0.1), (float)((row + 1) * cSize - cSize * 0.1), paint);
        canvas.drawLine((float)(col*cSize + cSize * 0.1), (float)(row * cSize + cSize * 0.1), (float)((col+1) * cSize - cSize * 0.1 ), (float)((row + 1) * cSize - cSize * 0.1), paint);
    }

    private void Odraw(Canvas canvas, int row, int col){
        paint.setColor(OColor);
        canvas.drawOval((float)(col*cSize + cSize * 0.1), (float)(row * cSize + cSize * 0.1), (float)((col*cSize + cSize) - cSize * 0.1), (float)((row *cSize + cSize) - cSize * 0.1), paint);

    }

    private void DrawLineHor(Canvas canvas, int row, int col){
        canvas.drawLine(col, row * cSize + (float)cSize / 2, cSize * 3, row * cSize + (float)cSize / 2, paint);
    }

    private void DrawLineVert(Canvas canvas, int row, int col){
        canvas.drawLine(col * cSize + (float)cSize / 2, row, col * cSize + (float)cSize / 2, cSize * 3, paint);
    }

    private void DrawLineDiag1(Canvas canvas){
        canvas.drawLine(0, cSize * 3, cSize * 3, 0, paint);
    }

    private void DrawLineDiag2(Canvas canvas){
        canvas.drawLine(0, 0, cSize * 3, cSize * 3, paint);
    }

    private void DrawWinLine(Canvas canvas){
        int row = game.getWinType()[0];
        int col = game.getWinType()[1];
        switch (game.getWinType()[2]){
            case 1:
                DrawLineHor(canvas, row, col);
                break;
            case 2:
                DrawLineVert(canvas, row, col);
                break;
            case 3:
                DrawLineDiag2(canvas);
                break;
            case 4:
                DrawLineDiag1(canvas);
                break;
        }
    }

    public void setupgame(TextView wincheck_display){
        game.WinCheck(wincheck_display);
    }

    public void PlayAgainbtn(){
        game.PlayAgainbtn();
        winningLine = false;
    }
}
