package com.example.alexkeith.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView clueTextview;
    private Button guessButton;
    private EditText guess;
    private int generatedNumber;
    private int numberOfGuesses = 0;
    private final int MAX_GUESS_COUNT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        clueTextview = findViewById(R.id.clue_textview);
        guessButton = findViewById(R.id.submit_guess_button);
        guess = findViewById(R.id.guess_edittext);

        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        generatedNumber = (int) Math.ceil(Math.random() * 100 );
        numberOfGuesses = 0;
        clueTextview.setVisibility(View.INVISIBLE);
        guess.setText("");
    }

    private void setListener() {
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userGuess = Integer.parseInt(guess.getText().toString());
                if (userGuess > 100) {
                    clueTextview.setText(R.string.invalid_number_message);
                    clueTextview.setVisibility(View.VISIBLE);
                    guess.setText("");
                } else {
                    checkGuess(userGuess);
                }
            }
        });
    }


    private void checkGuess(int userGuess) {
        if (userGuess == generatedNumber) {
            Intent winner = new Intent(this, ResultsActivity.class);
            startActivity(winner);
        } else if (numberOfGuesses == MAX_GUESS_COUNT) {
            //TODO - Create intent to go to winning activity - handle out of chances
            Intent loser = new Intent(this, ResultsActivity.class);
            loser.putExtra("WINNING_NUMBER", generatedNumber);
            startActivity(loser);
        } else if (userGuess < generatedNumber) {
            clueTextview.setText(R.string.higher);
            clueTextview.setVisibility(View.VISIBLE);
            guess.setText("");
            numberOfGuesses++;
        } else if (userGuess > generatedNumber) {
            clueTextview.setText(R.string.lower);
            clueTextview.setVisibility(View.VISIBLE);
            guess.setText("");
            numberOfGuesses++;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
