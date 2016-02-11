package no.woact.stud.smaola14.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * TicTacToe Game
 */
public class Game extends AppCompatActivity {

    TextView txtPlayers;
    GridLayout gameBoard;
    ArrayList <TextView> boardElements;
    int player = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Add players to the title: player1 VS player2
        txtPlayers = (TextView) findViewById(R.id.txtPlayers);
        Intent intent = getIntent();
        String playerOne = intent.getStringExtra("PlayerOne");
        String playerTwo = intent.getStringExtra("PlayerTwo");
        txtPlayers.setText(playerOne + " VS " + playerTwo);

        final View.OnClickListener boardElementListener = new View.OnClickListener() {
            @Override
            public void onClick(View boardElement) {
                addElementToBoard((TextView) boardElement);
            }
        };

        // Get the gameBoard and all its elements
        gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        boardElements = new ArrayList<>();
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElements.add(boardElement);
            boardElement.setOnClickListener(boardElementListener);
        }



    }

    // Add element to board, player 1 = X, player 2 = O
    private void addElementToBoard(TextView boardElement) {
        if (player == 1)
            boardElement.setText("X");
        else
            boardElement.setText("O");
        player *= -1;

        checkGameState();
    }

    // TODO: 11.02.2016 Check if a player has won or game is over
    private void checkGameState() {
    }
}
