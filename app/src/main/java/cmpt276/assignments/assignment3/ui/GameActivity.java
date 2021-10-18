package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cmpt276.assignments.assignment3.R;
import cmpt276.assignments.assignment3.model.GameManager;
import cmpt276.assignments.assignment3.model.GridCell;
import cmpt276.assignments.assignment3.model.OptionsManager;

// Implements grid UI functionality.
// Interacts with GameManager.java when a grid is clicked.
// Citation: https://www.youtube.com/watch?v=4MFzuP1F-xQ (Brian Fraser's Dynamic Button + images vid).
// set text to bold
// https://stackoverflow.com/questions/6200533/how-to-set-textview-textstyle-such-as-bold-italic
public class GameActivity extends AppCompatActivity {
    private OptionsManager options;
    private GameManager gameLogic;
    private Button[][] buttons;
    private GridCell[][] gameGrid;
    private int numMines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        options = OptionsManager.getInstance();

        setUpGrid(options.getBoardDimensionX(), options.getBoardDimensionY(), options.getNumMines());
        populateButtons();
        updateScansUsedText();
        updateMinesFoundText();
        setGameDataText();
    }

    private void setGameDataText(){
        TextView totalGames = findViewById(R.id.total_games_played);
        totalGames.setText(getResources().getString(R.string.total_games_update)
                        + " " + options.getTotalGames()
                );
        totalGames.setTypeface(null, Typeface.BOLD);

        TextView bestScore = findViewById(R.id.best_score);
        if(options.getBestScore() == getResources().getInteger(R.integer.no_best_score)){
            bestScore.setText(getResources().getString(R.string.best_score_update)
                    + getString(R.string.not_applicable));
        }else{
            bestScore.setText(getResources().getString(R.string.best_score_update)
                    + " " + options.getBestScore());
        }

        bestScore.setTypeface(null, Typeface.BOLD);
    }

    private void setUpGrid(final int dimX, final int dimY, int numMines) {
        this.numMines = options.getNumMines();
        buttons = new Button[dimX][dimY];
        gameLogic = new GameManager(dimX, dimY, numMines);
        gameGrid = gameLogic.getGridCells();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForButtons);
        for (int row = 0; row < options.getBoardDimensionX(); ++row) {
            TableRow tableRow = new TableRow(this);

            // Set scaling layout for tableRow view.
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));

            table.addView(tableRow);
            for (int col = 0; col < options.getBoardDimensionY(); ++col) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);

                // Set scaling layout for button view.
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                // Makes text not clip on smaller buttons
                button.setPadding(0, 0, 0, 0);

                // Implements On-Click functionality for grids.
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonClicked(FINAL_ROW, FINAL_COL);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int row, int col) {
        GridCell clickedGrid = gameGrid[row][col];

        lockButtonSizes();
        updateGridUI(row, col, clickedGrid);
    }

    private void updateGridUI(int row, int col, GridCell clickedGrid) {
        // Sets variables for game button.
        Button button = buttons[row][col];

        // Scale image to button on-click.
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();

        // Initial protocol for tapping unrevealed mine.
        if (clickedGrid.isMine() && !clickedGrid.isMineFound()) {
            clickedGrid.setMineFound(true);
            gameLogic.IncrementNumOfMinesFound();

            // Updates the rowCol values and updates corresponding UI.
            gameLogic.updateRowColValues(row, col);
            updateRowColText(row, col);

            // Display image.
            Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitcoin_logo);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));
        }
        // Initial Scan on revealed mine.
        else if (!clickedGrid.isScanned() && clickedGrid.isMineFound()) {
            gameLogic.scan(row, col);
            clickedGrid.setScanned(true);
            button.setText(String.valueOf(clickedGrid.getLocalMineCounter()));
            // Basic case for non-mine grid.
        } else {
            gameLogic.scan(row, col);
            button.setText(String.valueOf(clickedGrid.getLocalMineCounter()));
        }

        updateScansUsedText();
        updateMinesFoundText();
        checkIfWin();
    }

    private void checkIfWin() {

        ImageView congratsImg = new ImageView(this);
        congratsImg.setImageResource(R.drawable.bitcoin_logo);

        // Displays winning message.
        if (gameLogic.getNumOfMinesFound() == numMines) {
            AlertDialog.Builder winDialog = new AlertDialog.Builder(GameActivity.this);
            winDialog.setTitle(R.string.congratsText);
            winDialog.setMessage(R.string.winnerMsg);
            winDialog.setCancelable(false);
            winDialog.setNegativeButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick (DialogInterface dialogInterface, int i){
                    int numGames = options.getTotalGames();
                    saveTotalGames(++numGames);
                    determineBestScore(gameLogic.getNumOfScansDone());
                    finish();
                }
            }).setView(congratsImg);
            winDialog.show();
        }
    }

    private void determineBestScore(int currentScore){
        int bestScore = options.getBestScore();

        if (bestScore == getResources().getInteger(R.integer.no_best_score)) {
            saveGameConfigScore(currentScore, options.getTempKey());
        } else if (bestScore > currentScore) {
            saveGameConfigScore(currentScore, options.getTempKey());
        }
    }

    private void updateMinesFoundText() {
        TextView minesFound = findViewById(R.id.minesFound);
        minesFound.setText(getString(R.string.num_bitcoin_display_text_1) +  gameLogic.getNumOfMinesFound()
                            + getString(R.string.num_bitcoin_display_text_2) + numMines + getString(R.string.num_bitcoin_display_text_3));
        minesFound.setTypeface(null, Typeface.BOLD);
    }

    private void updateScansUsedText() {
        TextView scansUsed = findViewById(R.id.scansUsed);
        scansUsed.setText(getString(R.string.num_attempted_scans) + gameLogic.getNumOfScansDone());
        scansUsed.setTypeface(null, Typeface.BOLD);
    }

    private void updateRowColText(int row, int col) {
        // Updates text for grids in the same row as the clicked grid.
        for (int gridInCol = 0; gridInCol < options.getBoardDimensionY(); ++gridInCol) {
            Button button = buttons[row][gridInCol];
            GridCell gridToUpdate = gameGrid[row][gridInCol];

            if (!gridToUpdate.isScanned()) {
                continue;
            }
            button.setText(String.valueOf(gridToUpdate.getLocalMineCounter()));
        }

        // Updates text for grids in the same col as the clicked grid.
        for (int gridInRow = 0; gridInRow < options.getBoardDimensionX(); ++gridInRow) {
            Button button = buttons[gridInRow][col];
            GridCell gridToUpdate = gameGrid[gridInRow][col];

            if (!gridToUpdate.isScanned()) {
                continue;
            }
            button.setText(String.valueOf(gridToUpdate.getLocalMineCounter()));
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < options.getBoardDimensionX(); ++row) {
            for (int col = 0; col < options.getBoardDimensionY(); ++col) {
                Button button = buttons[row][col];

                // Prevents the image width from re-scaling.
                int width = button.getWidth();

                button.setMinWidth(width);
                button.setMaxWidth(width);

                // Prevents the image height from re-scaling.
                int height = button.getHeight();

                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    private void saveTotalGames(int numGames){
        SharedPreferences prefs = this.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(OptionsManager.getTotalGamesKey(), numGames);
        editor.apply();

        System.out.println(getTotalGames(this));
    }

    static public int getTotalGames(Context context){
        SharedPreferences prefs = context.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        int noGames = context.getResources().getInteger(R.integer.initial_total_games);

        return prefs.getInt(OptionsManager.getTotalGamesKey(),noGames);
    }

    private void saveGameConfigScore(int bestScore, final String key){
        SharedPreferences prefs = this.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, bestScore);
        editor.apply();
    }

    static public int getGameConfigScore(Context context, final String key){
        SharedPreferences prefs = context.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        int noScore = context.getResources().getInteger(R.integer.no_best_score);
        return prefs.getInt(key,noScore);
    }

}