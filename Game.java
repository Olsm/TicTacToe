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
    String playerOne;
    String playerTwo;
    GridLayout gameBoard;
    TextView[] boardElements;
    int player = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Add players to the title: player1 VS player2
        txtPlayers = (TextView) findViewById(R.id.txtPlayers);
        Intent intent = getIntent();
        playerOne = intent.getStringExtra("PlayerOne");
        playerTwo = intent.getStringExtra("PlayerTwo");
        txtPlayers.setText(playerOne + " VS " + playerTwo);

        final View.OnClickListener boardElementListener = new View.OnClickListener() {
            @Override
            public void onClick(View boardElement) {
                addElementToBoard((TextView) boardElement);
                boardElement.setClickable(false);
            }
        };

        // Get the gameBoard and all its elements
        gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        boardElements = new TextView[9];
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElements[i] = boardElement;
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
        String winner = "";

        // Check if there is a winner from the middle position (4)
        if (checkForWinnerDiagonal())
            winner = boardElements[4].getText().toString();

        // Or if there a winner in rows
        for (int i = 0; i < 7; i+= 3) {
            if (checkForWinnerRow(i)) {
                winner = boardElements[i].getText().toString();
                break;
            }
        }

        // Check if there is a winner in columns
        for (int i = 0; i < 3; i++) {
            if (checkForWinnerColumn(i)) {
                winner = boardElements[i].getText().toString();
                break;
            }
        }

        // And check if there is a winner diagonally
        if (checkForWinnerDiagonal())
            winner = boardElements[4].getText().toString();

        // todo: save results
        if (winner.equals("X") || winner.equals("O")) {
            if (winner.equals("X"))
                winner = playerOne;
            else
                winner = playerTwo;

            txtPlayers.setText(winner + " has won!");

            for (int i = 0; i < gameBoard.getChildCount(); i++) {
                TextView boardElement = (TextView) gameBoard.getChildAt(i);
                boardElement.setEnabled(false);
            }
        }
    }

    // Look for a winner in a row of cells
    private boolean checkForWinnerRow (int firstCell) {
        return checkForWinnerCells(firstCell, firstCell + 1, firstCell + 2);
    }

    // Look for a winner in a column of cells
    private boolean checkForWinnerColumn (int firstCell) {
        return checkForWinnerCells(firstCell, firstCell + 3, firstCell + 6);
    }

    // Look for a winner diagonally of cells
    private boolean checkForWinnerDiagonal () {
        return  checkForWinnerCells(0, 4, 8)
                || checkForWinnerCells(2, 4, 6);
    }

    // Compare 3 cells to see if there is a winner
    private boolean checkForWinnerCells (int cellOne, int cellTwo, int cellThree) {
        CharSequence elementOne = boardElements[cellOne].getText();
        CharSequence elementTwo = boardElements[cellTwo].getText();
        CharSequence elementThree = boardElements[cellThree].getText();

        return !elementOne.toString().isEmpty()
                && elementOne.equals(elementTwo)
                && elementOne.equals(elementThree);
    }
}
