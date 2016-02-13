package no.woact.stud.smaola14.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Create, add data and get data from database
 */
public class DBHandler extends SQLiteOpenHelper {
    private static final String DATABASE = "tictactoe";
    private static final String TABLE = "results";
    private static final String PLAYER_ONE = "playerone";
    private static final String PLAYER_TWO = "playertwo";
    private static final String WINNER = "winner";
    private static final String DATETIME = "datetime";
    private static final int DATABASE_VERSION = 1;

    public DBHandler(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }

    // Create the table if it doesnt exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PLAYER_ONE + " TEXT, "
                        + PLAYER_TWO + " TEXT, "
                        + WINNER + " TEXT, "
                        + DATETIME + " TEXT)"
        );
    }

    // Drop and recreate table if db has been updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE);
        onCreate(db);
    }

    // Get results from db and return as ArrayList of Result objects
    public ArrayList<Result> getResults () {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Result> results = new ArrayList<>();

        // Run a query to get all rows of results table
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " ORDER BY " + DATETIME + " DESC", null);

        // Get each individual cell from each row and add to Result objects
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String playerOne = cursor.getString(cursor.getColumnIndex(PLAYER_ONE));
            String playerTwo = cursor.getString(cursor.getColumnIndex(PLAYER_TWO));
            String winner = cursor.getString(cursor.getColumnIndex(WINNER));
            String dateTime = cursor.getString(cursor.getColumnIndex(DATETIME));

            Result result = new Result(playerOne, playerTwo, winner, dateTime);
            results.add(result);
            cursor.moveToNext();
        }

        db.close();
        return results;
    }

    // Add a result to db
    public void addResult (Result result) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Use ContentValues to easily add results to db
        contentValues.put(PLAYER_ONE, result.getPlayerOne());
        contentValues.put(PLAYER_TWO, result.getPlayerTwo());
        contentValues.put(WINNER, result.getWinner());
        contentValues.put(DATETIME, result.getDateTime());

        db.insert(TABLE, null, contentValues);
        db.close();
    }

    // Delete all results from the db
    public void deleteResults () {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE * FROM " + TABLE);
    }
}
