package no.woact.stud.smaola14.tictactoe;

/**
 * Result stores data about a match:
 * Who was the players and who who won
 * The date and time it took place
 */
public class Result {
    private String playerOne;
    private String playerTwo;
    private String winner;
    private String dateTime;

    // A result cannot be changed so there is only one constructor and no setters
    public Result(String playerOne, String playerTwo, String winner, String dateTime) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.winner = winner;
        this.dateTime = dateTime;
    }

    // Convert the Result into a string
    public String toString() {
        String dateTime = getDateTime();
        String playerOne = getPlayerOne();
        String playerTwo = getPlayerTwo();
        String winner = getWinner();

        // Set default string "p1 vs p2: winner" (for tie winner is "tie")
        String result = dateTime + " | ";

        // If there was a winner, add who is winner and loser
        if (!winner.equals("tie")) {
            String loser;
            if (playerOne.equals(winner))
                loser = playerTwo;
            else
                loser = playerOne;
            result += winner + " won against " + loser;
        }
        else
            result += playerOne + " vs " + playerTwo + ": tie!";

        return result;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getWinner() {
        return winner;
    }

    public String getDateTime() {
        return dateTime;
    }
}
