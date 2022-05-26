package com.example.samsungproject;

import android.widget.TextView;

public class TicTacToeLogic {
    private int[][] TicTacToeBoard;
    private int player = 1;
    private int[] WinType = {-1, -1, -1};
    private TextView winCheck;

    TicTacToeLogic(){
        TicTacToeBoard = new int[3][3];
        for (int r=0; r<3; r++){
            for (int c=0; c < 3; c++){
                TicTacToeBoard[r][c] = 0;
            }
        }
    }

    public boolean updateTicTacToeBoard(int row, int col){
        if (TicTacToeBoard[row-1][col-1] == 0){
            TicTacToeBoard[row-1][col-1] = player;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean CheckWinner(){
        boolean winner = false;
        for (int r=0; r<3; r++){
            if (TicTacToeBoard[r][0] == TicTacToeBoard[r][1] && TicTacToeBoard[r][0] == TicTacToeBoard[r][2] && TicTacToeBoard[r][0] != 0){
                WinType = new int[] {r, 0, 1};
                winner = true;
            }
        }

        for (int c=0; c<3; c++){
            if (TicTacToeBoard[0][c] == TicTacToeBoard[1][c] && TicTacToeBoard[2][c] == TicTacToeBoard[0][c] && TicTacToeBoard[0][c] != 0){
                WinType = new int[] {0, c, 2};
                winner = true;
            }
        }

        if (TicTacToeBoard[0][0] == TicTacToeBoard[1][1] && TicTacToeBoard[0][0] == TicTacToeBoard[2][2] && TicTacToeBoard[0][0] != 0){
            WinType = new int[] {0, 2, 3};
            winner = true;
        }

        if (TicTacToeBoard[2][0] == TicTacToeBoard[1][1] && TicTacToeBoard[2][0] == TicTacToeBoard[0][2] && TicTacToeBoard[2][0] != 0){
            WinType = new int[] {2, 2, 4};
            winner = true;
        }

        int b = 0;

        for (int r=0; r<3; r++) {
            for (int c = 0; c < 3; c++) {
                if (TicTacToeBoard[r][c] != 0) {
                    b += 1;
                }
            }
        }

        if (winner){
            winCheck.setText("WIN");
            return true;
        }
        else if (b == 9){
            winCheck.setText("DRAW");
            return true;

        }
        else{
            return false;
        }
    }

    public void PlayAgainbtn(){
        for (int r=0; r<3; r++){
            for (int c=0; c < 3; c++){
                TicTacToeBoard[r][c] = 0;
            }
        }
        player = 1;
        winCheck.setText("");
        WinType = new int[] {-1, -1, -1};
    }

   public void WinCheck(TextView winCheck){
        this.winCheck = winCheck;
    }

    public int[][] getTicTacToeBoard() {
        return TicTacToeBoard;
    }

    public void setPlayer(int player){
        this.player = player;
    }

    public int getPlayer(){
        return player;
    }

    public int[] getWinType() {
        return WinType;
    }
}
