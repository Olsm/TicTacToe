package no.woact.stud.smaola14.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;

/**
 * TicTacToe Game
 */
public class Game extends AppCompatActivity {
    Context context;
    DBHandler dbHandler;

    GridLayout gameBoard;
    TextView txtPlayers;
    Button btnNewGame;
    Button btnResults;

    ArrayList<TextView> boardElementViews;
    String[] boardElements;
    String playerOne;
    String playerTwo;
    int elementsOnBoard = 0;
    int player = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context = this;
        dbHandler = new DBHandler(this);

        // Get views and widgets
        gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnResults = (Button) findViewById(R.id.btnResults);
        txtPlayers = (TextView) findViewById(R.id.txtStatus);

        // Add players to the title: player1 VS player2
        Intent intent = getIntent();
        playerOne = intent.getStringExtra("PlayerOne");
        playerTwo = intent.getStringExtra("PlayerTwo");
        txtPlayers.setText(playerOne + " VS " + playerTwo);

        // Create and set OnClickListeners
        setButtonOnClickListeners();
        final View.OnClickListener boardElementListener = new View.OnClickListener() {
            @Override
            public void onClick(View boardElement) {
                addElementToBoard((TextView) boardElement);
                boardElement.setClickable(false);
            }
        };

        // Get the gameBoard and all its elements
        boardElements = new String[9];
        boardElementViews = new ArrayList<>(9);
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElementViews.add(i, boardElement);
            boardElements[i] = "";
            boardElement.setOnClickListener(boardElementListener);
        }
    }

    // Create and set OnClickListeners for buttons
    private void setButtonOnClickListeners() {
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Scoreboard.class);
                startActivity(intent);
            }
        });
    }

    // Reset the game when starting a new game
    private void resetGame() {
        txtPlayers.setText(playerOne + " VS " + playerTwo);
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElement.setText("");
            boardElement.setEnabled(true);
            boardElement.setClickable(true);
            Arrays.fill(boardElements, "");
            elementsOnBoard = 0;
            player = 1;
        }
    }

    // Add element to board, player 1 = X, player 2 = O
    private void addElementToBoard(TextView boardElement) {
        String element;
        if (player == 1)
            element = "X";
        else
            element = "O";

        boardElement.setText(element);
        int elementViewIndex = boardElementViews.indexOf(boardElement);
        boardElements[elementViewIndex] = element;

        elementsOnBoard++;
        player *= -1;
        checkGameState();
    }

    // End the game and show results
    private void endGame(String winnerElement) {
        String winner = "";

        // Find whoe was the winner by element
        if (winnerElement.equals("X"))
            winner = playerOne;
        else if (winnerElement.equals("O"))
            winner = playerTwo;

        String winnerText;
        if (!winner.isEmpty())
            winnerText = winner + " won!";
        else
            winnerText = "TIE";

        // Display result and save result to DB
        txtPlayers.setText(winnerText);
        GregorianCalendar gc = new GregorianCalendar();
        String dateTime = String.valueOf(gc.get(GregorianCalendar.YEAR))
                + "-" + (gc.get(GregorianCalendar.MONTH) + 1)
                + "-" + gc.get(GregorianCalendar.DAY_OF_MONTH)
                + " " + gc.get(GregorianCalendar.HOUR_OF_DAY)
                + ":" + gc.get(GregorianCalendar.MINUTE)
                + ":" + gc.get(GregorianCalendar.SECOND);
        Result result = new Result(playerOne, playerTwo, winner, dateTime);
        dbHandler.addResult(result);

        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElement.setEnabled(false);
        }
    }

    // Check if there is a winner or if game is over
    private void checkGameState() {
        String winner = "";

        if (elementsOnBoard == 9)
            endGame("");

        // Check if there is a winner from the middle position (4)
        if (checkForWinnerDiagonal())
            endGame(boardElements[4]);

        // Or if there a winner in rows
        for (int i = 0; i < 7; i+= 3) {
            if (checkForWinnerRow(i)) {
                endGame(boardElements[i]);
            }
        }

        // Check if there is a winner in columns
        for (int i = 0; i < 3; i++) {
            if (checkForWinnerColumn(i)) {
                endGame(boardElements[i]);
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
        String elementOne = boardElements[cellOne];
        String elementTwo = boardElements[cellTwo];
        String elementThree = boardElements[cellThree];

        return !elementOne.isEmpty()
                && elementOne.equals(elementTwo)
                && elementOne.equals(elementThree);
    }
}
