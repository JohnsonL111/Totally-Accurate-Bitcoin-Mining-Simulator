package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cmpt276.assignments.assignment3.R;
import cmpt276.assignments.assignment3.model.OptionsManager;

public class OptionsActivity extends AppCompatActivity {
    protected static final String BOARD_SIZE_X_PREFS = "BoardSizeXPrefs";
    protected static final String BOARD_SIZE_Y_PREFS = "BoardSizeYPrefs";
    protected static final String APP_PREFS = "AppPrefs";
    protected static final String NUM_MINES_PREFS = "NumberOfMinesPrefs";

    private OptionsManager options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        options = OptionsManager.getInstance();

        setNumMinesOptionsBtn();
        setBoardSizeOptionsBtn();
        setResetBtn();
    }

    // https://developer.android.com/reference/android/content/SharedPreferences.Editor#remove(java.lang.String)
    private void setResetBtn() {
        Button clear = findViewById(R.id.resetButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int noScore = getResources().getInteger(R.integer.no_best_score);
                int noGames = getResources().getInteger(R.integer.initial_total_games);
                options.setBestScore(noScore);
                options.setTotalGames(noGames);

                // remove shared preferences corresponding to GAME_DATA key
                SharedPreferences prefs = getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
            }
        });

    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, OptionsActivity.class);
    }

    @SuppressLint("SetTextI18n")
    private void setBoardSizeOptionsBtn() {
        RadioGroup boardSizeGroup = findViewById(R.id.radio_group_board_size);

        int[] boardSizeXList = getResources().getIntArray(R.array.board_list_x);
        int[] boardSizeYList = getResources().getIntArray(R.array.board_list_y);

        for(int i = 0; i < boardSizeXList.length; i++){
            final int boardDimensionX = boardSizeXList[i];
            final int boardDimensionY = boardSizeYList[i];

            RadioButton boardSizeBtn = new RadioButton(this);
            boardSizeBtn.setText(
                    boardDimensionX + getString(R.string.rows) +
                    boardDimensionY + getString(R.string.columns)
            );

            // set options to singleton
            boardSizeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    options.setBoardDimensionX(boardDimensionX);
                    options.setBoardDimensionY(boardDimensionY);

                    saveBoardDimensions(boardDimensionX, boardDimensionY);
                }
            });

            boardSizeGroup.addView(boardSizeBtn);

            if (boardDimensionX == getBoardDimensionX(this)){
                boardSizeBtn.setChecked(true);
            }
        }
    }

    private void saveBoardDimensions(int boardSizeX, int boardSizeY) {
        SharedPreferences boardSizePrefs = this.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = boardSizePrefs.edit();

        editor.putInt(BOARD_SIZE_X_PREFS, boardSizeX);
        editor.putInt(BOARD_SIZE_Y_PREFS, boardSizeY);

        editor.apply();
    }

    static public int getBoardDimensionX(Context context){
        SharedPreferences boardSizePrefs = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);

        int defaultDimX = context.getResources().getInteger(R.integer.default_dimension_x);
        return boardSizePrefs.getInt(BOARD_SIZE_X_PREFS, defaultDimX);
    }

    static public int getBoardDimensionY(Context context){
        SharedPreferences boardSizePrefs = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);

        int defaultDimY = context.getResources().getInteger(R.integer.default_dimension_y);
        return boardSizePrefs.getInt(BOARD_SIZE_Y_PREFS, defaultDimY);
    }

    @SuppressLint("SetTextI18n")
    private void setNumMinesOptionsBtn() {
        RadioGroup numMinesGroup = findViewById(R.id.radio_group_num_mines);

        int[] minesList = getResources().getIntArray(R.array.num_mines);

        // create the buttons
        for (final int numMines : minesList) {
            RadioButton numMinesBtn = new RadioButton(this);
            numMinesBtn.setText(numMines + getString(R.string.num_mines_radio_button));

            // set options to singleton
            numMinesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    options.setNumMines(numMines);
                    saveNumMines(numMines);
                }
            });

            // add the radio buttons to the group
            numMinesGroup.addView(numMinesBtn);

            // select default button
            if (numMines == getNumMines(this)) {
                numMinesBtn.setChecked(true);
            }
        }
    }

    private void saveNumMines(int numMines) {
        SharedPreferences boardSizePrefs = this.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = boardSizePrefs.edit();
        editor.putInt(NUM_MINES_PREFS, numMines);
        editor.apply();
    }

    static public int getNumMines(Context context){
        SharedPreferences boardSizePrefs = context.getSharedPreferences(APP_PREFS, MODE_PRIVATE);
        int defaultMines = context.getResources().getInteger(R.integer.default_num_mines);

        return boardSizePrefs.getInt(NUM_MINES_PREFS, defaultMines);
    }
}