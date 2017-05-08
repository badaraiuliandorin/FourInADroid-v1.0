package com.example.nuclearpowerplant.fourinadroid;

/**
 * Created by NUCLEARPOWERPLANT on 4/23/2017.
 */

public class GameActions {


    public static int checkBigDiagonal(int[][]board, int rows, int cols, int player, int cellRow, int cellCol){
        boolean okTopLeft = true;
        boolean okBottomRight = true;
        int pieceCountTopLeft = 0;
        int pieceCountBottomRight = 0;
        int colToRun = cellCol;

        if((cellRow != 0) && (cellCol != 0)){
            for(int i = cellRow; i >= 0 && okTopLeft && colToRun >= 0; i--){
                if(board[i][colToRun] == player){
                    pieceCountTopLeft++;
                }
                else{
                    okTopLeft = false;
                }
                colToRun--;
            }
        }
        else
        {
            pieceCountTopLeft = 1;
        }
        colToRun = cellCol;

        if((cellRow != (rows - 1)) && (cellCol != (cols - 1)))
        {
            for(int i = cellRow; i < rows && okBottomRight && colToRun < cols; i++){
                if(board[i][colToRun] == player){
                    pieceCountBottomRight++;
                }
                else{
                    okBottomRight = false;
                }
                colToRun++;
            }
        }
        else
        {
            pieceCountBottomRight = 1;
        }

        return pieceCountTopLeft + pieceCountBottomRight - 1;
    }

    public static int checkSmallDiagonal(int[][]board, int rows, int cols, int player, int cellRow, int cellCol){
        boolean okTopRight = true;
        boolean okBottomLeft = true;
        int pieceCountTopRight = 0;
        int pieceCountBottomLeft = 0;
        int colToRun = cellCol;

        if((cellRow != (rows - 1)) && (cellCol != 0))
        {
            for(int i = cellRow; i < rows && okBottomLeft && colToRun >= 0; i++){
                if(board[i][colToRun] == player){
                    pieceCountBottomLeft++;
                    colToRun--;
                }
                else{
                    okBottomLeft = false;
                }
            }
        }
        else
        {
            pieceCountBottomLeft = 1;
        }

        colToRun = cellCol;

        if((cellRow != 0) && (cellCol != (cols - 1)))
        {
            for(int i = cellRow; i >= 0 && okTopRight && colToRun < cols; i--){
                if(board[i][colToRun] == player){
                    pieceCountTopRight++;
                    colToRun++;
                }
                else{
                    okTopRight = false;
                }
            }
        }
        else
        {
            pieceCountTopRight = 1;
        }

        return pieceCountTopRight + pieceCountBottomLeft - 1;
    }

    public static int checkColumn(int[][]board, int rows, int player, int cellRow, int cellCol){
        int pieceCount = 0;

        for(int i = cellRow; i < rows; i++){
            if(board[i][cellCol] == player){
                pieceCount++;
            }
            else{
                return pieceCount;
            }
        }
        return pieceCount;
    }

    public static int checkRow(int[][]board, int cols, int player, int cellRow, int cellCol){
        int pieceCountLeft = 0;
        boolean okLeft = true;
        boolean okRight = true;
        int pieceCountRight = 0;
        for(int i = cellCol; i >= 0 && okLeft; i--){
            if(board[cellRow][i] == player){
                pieceCountLeft++;
            }
            else{
                okLeft = false;
            }
        }

        for(int i = cellCol; i < cols && okRight; i++){
            if(board[cellRow][i] == player){
                pieceCountRight++;
            }
            else{
                okRight = false;
            }
        }
        return pieceCountLeft + pieceCountRight - 1;
    }

    public boolean checkWin(int[][]board, int rows, int cols, boolean turn, int cellRow, int cellCol){
        int player = turn ? 1 : 2;

        if(checkBigDiagonal(board, rows, cols, player, cellRow, cellCol) >= 4){
            return true;
        }

        if(checkSmallDiagonal(board, rows, cols, player, cellRow, cellCol) >= 4){
            return true;
        }

        if(checkColumn(board, rows, player, cellRow, cellCol) >= 4){
            return true;
        }

        if(checkRow(board, cols, player, cellRow, cellCol) >= 4){
            return true;
        }

        return false;
    }

    public boolean checkIfBoardFull(int[][]board, int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String boardToString(int[][] board, int rows, int cols) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0 ;i< rows; i ++){
            for(int j = 0; j < cols; j++){
                stringBuilder.append(board[i][j] + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}
