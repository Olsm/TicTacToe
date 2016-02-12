package no.woact.stud.smaola14.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * TicTacToe Game
 */
public class Game extends AppCompatActivity {
    Context context;

    GridLayout gameBoard;
    TextView txtPlayers;
    Button btnNewGame;
    Button btnMenu;
    Button btnResults;

    TextView[] boardElements;
    String playerOne;
    String playerTwo;
    int elementsOnBoard = 0;
    int player = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        context = this;

        // Get views and widgets
        gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        btnMenu = (Button) findViewById(R.id.btnMenu);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnResults = (Button) findViewById(R.id.btnResults);
        txtPlayers = (TextView) findViewById(R.id.txtPlayers);

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
        boardElements = new TextView[9];
        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElements[i] = boardElement;
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

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Menu.class);
                startActivity(intent);
            }
        });

        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Results.class);
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
            elementsOnBoard = 0;
        }
    }

    // Add element to board, player 1 = X, player 2 = O
    private void addElementToBoard(TextView boardElement) {
        if (player == 1)
            boardElement.setText("X");
        else
            boardElement.setText("O");
        player *= -1;

        elementsOnBoard++;
        checkGameState();
    }

    // End the game and show results
    // TODO: 11.02.2016 Check if a player has won or game is over
    private void endGame(String winner) {
        if (!winner.isEmpty())
            txtPlayers.setText(winner + " won!");
        else
            txtPlayers.setText("TIE!");

        for (int i = 0; i < gameBoard.getChildCount(); i++) {
            TextView boardElement = (TextView) gameBoard.getChildAt(i);
            boardElement.setEnabled(false);
        }
    }

    // Check if there is a winner or if game is over
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
        if (winner.equals("X") || winner.equals("O") || elementsOnBoard == 9) {
            if (winner.equals("X"))
                winner = playerOne;
            else if (winner.equals("O"))
                winner = playerTwo;

            endGame(winner);
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
