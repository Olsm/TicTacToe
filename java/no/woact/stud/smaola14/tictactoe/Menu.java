package no.woact.stud.smaola14.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Menu extends AppCompatActivity {

    Context context;
    Button btnStart;
    EditText txtPlayerOne;
    EditText txtPlayerTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        context = this;
        btnStart = (Button) findViewById(R.id.btnStart);
        txtPlayerOne = (EditText) findViewById(R.id.txtPlayerOne);
        txtPlayerTwo = (EditText) findViewById(R.id.txtPlayerTwo);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerOne = txtPlayerOne.getText().toString();
                String playerTwo = txtPlayerTwo.getText().toString();

                // Make sure names cannot be empty
                if (playerOne.isEmpty() || playerTwo.isEmpty()) {
                    if (playerOne.isEmpty())
                        txtPlayerOne.setError(getString(R.string.empty_field_error));
                    if (playerTwo.isEmpty())
                        txtPlayerTwo.setError(getString(R.string.empty_field_error));
                    return;
                }

                Intent intent = new Intent(context, Game.class);
                intent.putExtra("PlayerOne", playerOne);
                intent.putExtra("PlayerTwo", playerTwo);
                startActivity(intent);
            }
        });
    }
}
