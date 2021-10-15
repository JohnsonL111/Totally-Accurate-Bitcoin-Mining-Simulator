package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cmpt276.assignments.assignment3.R;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        setBoardSizeOptions();
        setNumMinesOptions();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    @SuppressLint("SetTextI18n")
    private void setBoardSizeOptions() {
        RadioGroup boardSizeGroup = findViewById(R.id.radio_group_board_size);

        int[] boardSizeX = getResources().getIntArray(R.array.board_size_x);
        int[] boardSizeY = getResources().getIntArray(R.array.board_size_y);

        for(int i = 0; i < boardSizeX.length; i++){
            RadioButton boardSizeBtn = new RadioButton(this);
            boardSizeBtn.setText(
                    boardSizeX[i] + getString(R.string.rows) +
                    boardSizeY[i] + getString(R.string.columns)
            );

            // set options to singleton
            boardSizeGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            boardSizeGroup.addView(boardSizeBtn);
        }


    }

    @SuppressLint("SetTextI18n")
    private void setNumMinesOptions() {
        RadioGroup numMinesGroup = findViewById(R.id.radio_group_num_mines);

        int[] minesList = getResources().getIntArray(R.array.num_mines);

        // create the buttons
        for (final int numMines : minesList) {
            RadioButton radioBtn = new RadioButton(this);
            radioBtn.setText(numMines + getString(R.string.num_mines_radio_button));

            // set options to singleton
            radioBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            // add the radio buttons to the group
            numMinesGroup.addView(radioBtn);
        }
    }
}