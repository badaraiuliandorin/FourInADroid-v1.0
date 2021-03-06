package com.example.nuclearpowerplant.fourinadroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {
    private int[][] board;
    private GameActions gameActions;
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static boolean turn = true;//true means blue, false means red
    private static boolean isWin = false;
    private TextView currentPlayer = null;
    private LinearLayout buttonsLayout = null;
    private LinearLayout linearLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currentPlayer = (TextView)findViewById(R.id.currentPlayer);
        buttonsLayout = (LinearLayout)findViewById(R.id.gameBoardButtons);
        linearLayout = (LinearLayout)findViewById(R.id.gameBoard);
        Button resetGameButton = (Button) findViewById(R.id.reset_game_button);

        gameActions = new GameActions();
        board = new int[ROWS][COLS];

        //final LinearLayout playerLayout = (LinearLayout)findViewById(R.id.playerInfo);
        currentPlayer.setText(turn ? "Blue" : "Red");

        for(int i = 0; i < COLS; i++){
            final ImageButton imageButton = (ImageButton)buttonsLayout.getChildAt(i);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    boolean endMove = false;
                    ImageButton selectedButton = (ImageButton)v;
                    LinearLayout buttonLayout = (LinearLayout)selectedButton.getParent();
                    int column = buttonLayout.indexOfChild(selectedButton);

                    for(int i = ROWS - 1; i >= 0; i--){
                        if(!endMove){
                            if(board[i][column] == 0){
                                board[i][column] = turn ? 1 : 2;
                                LinearLayout lineAt = (LinearLayout)linearLayout.getChildAt(i + 1);
                                ImageView imageAt = (ImageView)lineAt.getChildAt(column);
                                imageAt.setImageResource(turn ? R.drawable.blue_piece_bordered : R.drawable.red_piece_bordered);

                                if(gameActions.checkWin(board, ROWS, COLS, turn, i, column)){
                                    isWin = true;
                                    for(int x = 0; x < COLS; x++){
                                        ImageButton button = (ImageButton)buttonsLayout.getChildAt(x);
                                        button.setEnabled(false);
                                        button.setImageResource(R.drawable.disabled_piece);
                                    }

                                    currentPlayer.setText(getString(R.string.winner_is) + " " + (turn ? "blue" : "red"));

                                    Toast.makeText(GameActivity.this, "Game won by " + (turn ? "blue" : "red"), Toast.LENGTH_LONG).show();
                                }

                                if(gameActions.checkIfBoardFull(board, ROWS, COLS)){
                                    isWin = true;
                                    for(int x = 0; x < COLS; x++){
                                        ImageButton button = (ImageButton)buttonsLayout.getChildAt(x);
                                        button.setEnabled(false);
                                        button.setImageResource(R.drawable.disabled_piece);
                                    }

                                    currentPlayer.setText(R.string.game_draw);

                                    Toast.makeText(GameActivity.this, "Draw", Toast.LENGTH_LONG).show();
                                }

                                endMove = !endMove;
                                turn = !turn;
                            }
                        }
                    }

                    if(!isWin){
                        for(int i = 0; i < COLS; i++){
                            if(board[0][i] == 0){
                                ImageButton button = (ImageButton)buttonLayout.getChildAt(i);
                                button.setImageResource(turn ? R.drawable.blue_piece : R.drawable.red_piece);
                            }
                        }

                        currentPlayer.setText(turn ? "Blue" : "Red");
                    }

                    if(board[0][column] != 0){
                        ImageButton disabledButton =  (ImageButton)buttonLayout.getChildAt(column);
                        disabledButton.setEnabled(false);
                        disabledButton.setImageResource(R.drawable.disabled_piece);
                    }
                }
            });
        }

        resetGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn = true;
                board = new int[ROWS][COLS];
                isWin = false;

                for(int i = 0; i < COLS; i++){
                    ImageButton button = (ImageButton)buttonsLayout.getChildAt(i);
                    button.setImageResource(R.drawable.blue_piece);
                    button.setEnabled(true);
                }

                for(int i = 0; i < ROWS; i++){
                    for(int j = 0; j < COLS; j++){
                        LinearLayout lineAt = (LinearLayout)linearLayout.getChildAt(i + 1);
                        ImageView imageAt = (ImageView)lineAt.getChildAt(j);
                        imageAt.setImageResource(R.drawable.empty_piece);
                    }
                }

                currentPlayer.setText("Blue");
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to go back to main menu?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();

    }
}
