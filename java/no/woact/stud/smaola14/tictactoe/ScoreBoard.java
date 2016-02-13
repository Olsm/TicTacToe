package no.woact.stud.smaola14.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {
    DBHandler dbHandler;
    ListView viewResults;
    ArrayList<Result> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        dbHandler = new DBHandler(this);
        viewResults = (ListView) findViewById(R.id.listScoreBoard);
        results = dbHandler.getResults();

        // Change the list of Result into a list of String
        ArrayList<String> listResults = new ArrayList<>();
        for (Result result : results) {
            // Add the result string to the list of results
            listResults.add(result.toString());
        }

        // Create and set the adapter for the ListView
        viewResults.setAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listResults)
        );
    }
}
