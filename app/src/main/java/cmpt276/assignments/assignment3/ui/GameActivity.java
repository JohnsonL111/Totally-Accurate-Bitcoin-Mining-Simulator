package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cmpt276.assignments.assignment3.R;
import cmpt276.assignments.assignment3.model.GameManager;
import cmpt276.assignments.assignment3.model.Block;
import cmpt276.assignments.assignment3.model.OptionsManager;


// set text to bold
// https://stackoverflow.com/questions/6200533/how-to-set-textview-textstyle-such-as-bold-italic

/**
 * Implements grid UI functionality.
 * Interacts with GameManager.java when a grid is clicked:
 * Citation: https://www.youtube.com/watch?v=4MFzuP1F-xQ (Brian Fraser's Dynamic Button + images vid).
 * Set text to bold:
 * Citation: https://stackoverflow.com/questions/6200533/how-to-set-textview-textstyle-such-as-bold-italic
 */
public class GameActivity extends AppCompatActivity {
    private OptionsManager options;
    private GameManager gameLogic;
    private Button[][] buttons; // the ui grid
    private Block[][] gameGrid; // the logic grid
    private int numMines;
    private boolean isInitiallyRevealed = true;

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

    // https://stackoverflow.com/questions/7750102/how-to-get-height-and-width-of-button
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        /*
            update initial buttons to image of un-scanned block
            after button sizes have been set by UI for viewing
            setting it earlier will cause a crash as button sizes are 0

            isInitiallyRevealed -> to prevent onWindowFocusChanged to set grid images
            to be un-scanned after the first window focus when starting GameActivity
         */
        if (hasFocus && isInitiallyRevealed) {
            for (int i = 0; i < options.getBoardDimensionX(); i++) {
                for (int j = 0; j < options.getBoardDimensionY(); j++) {
                    setButtonImage(buttons[i][j], "Un-scanned Block");
                }
            }
            isInitiallyRevealed = false;
        }
    }

    private void setGameDataText() {
        // update total games
        TextView totalGames = findViewById(R.id.total_games_played);
        totalGames.setText(getResources().getString(R.string.total_games_update)
                + " " + options.getTotalGames()
        );
        totalGames.setTypeface(null, Typeface.BOLD);
        totalGames.setTextColor(ContextCompat.getColor(this, R.color.white));

        // update best score
        TextView bestScore = findViewById(R.id.best_score);
        if (options.getBestScore() == getResources().getInteger(R.integer.no_best_score)) {
            bestScore.setText(getResources().getString(R.string.best_score_update)
                    + getString(R.string.not_applicable));
        } else {
            bestScore.setText(getResources().getString(R.string.best_score_update)
                    + " " + options.getBestScore());
        }

        bestScore.setTypeface(null, Typeface.BOLD);
        bestScore.setTextColor(ContextCompat.getColor(this, R.color.white));
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
                button.setOnClickListener(view -> gridButtonClicked(FINAL_ROW, FINAL_COL));

                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int row, int col) {
        Block clickedGrid = gameGrid[row][col];

        lockButtonSizes();
        updateGridUI(row, col, clickedGrid);
    }

    private void updateGridUI(int row, int col, Block clickedGrid) {
        // Sets variables for game button.
        Button button = buttons[row][col];

        // Initial protocol for tapping unrevealed bitcoin.
        if (clickedGrid.isBitcoin() && !clickedGrid.isMineFound()) {
            clickedGrid.setMineFound(true);
            gameLogic.IncrementNumBitcoinFound();

            // Updates the rowCol values and updates corresponding UI.
            gameLogic.updateRowColValues(row, col);
            updateRowColText(row, col);

            // Display image.
            setButtonImage(button, "Scanned Bitcoin Block");
        }
        // Initial Scan on revealed bitcoin.
        else if (!clickedGrid.isScanned() && clickedGrid.isMineFound()) {
            gameLogic.scan(row, col);
            clickedGrid.setScanned(true);
            button.setText(String.valueOf(clickedGrid.getLocalMineCounter()));
            button.setTypeface(null, Typeface.BOLD);

            // https://stackoverflow.com/questions/31842983/getresources-getcolor-is-deprecated
            button.setTextColor(ContextCompat.getColor(this, R.color.black));

            // Initial scan on block with no bitcoin
        } else if (!clickedGrid.isScanned() && !clickedGrid.isBitcoin()) {
            gameLogic.scan(row, col);
            button.setText(String.valueOf(clickedGrid.getLocalMineCounter()));
            button.setTypeface(null, Typeface.BOLD);
            button.setTextColor(ContextCompat.getColor(this, R.color.white));
            setButtonImage(button, "Scanned Empty Block");
        }

        updateScansUsedText();
        updateMinesFoundText();
        checkIfWin();
    }

    private void setButtonImage(Button button, String buttonImageType) {
        // Scale image to button on-click.
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();

        Bitmap originalBitmap = null;

        switch (buttonImageType) {
            case "Un-scanned Block":
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unscanned_block);
                break;
            case "Scanned Empty Block":
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.empty_block);
                break;
            case "Scanned Bitcoin Block":
                originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitcoin_block);
                break;
        }

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));
    }

    private void checkIfWin() {

        ImageView congratsImg = new ImageView(this);
        congratsImg.setImageResource(R.drawable.bitcoin_logo);

        if (gameLogic.getNumBitcoinFound() == numMines) {

            // update game data
            updateTotalGames();
            updateBestScore(gameLogic.getNumOfScansDone());
            setGameDataText();

            // display winning message
            AlertDialog.Builder winDialog = new AlertDialog.Builder(GameActivity.this);
            winDialog.setTitle(R.string.congratsText);
            winDialog.setMessage(R.string.winnerMsg);
            winDialog.setCancelable(false);
            winDialog.setNegativeButton(getResources().getString(R.string.dialog_ok),
                    (dialogInterface, i) -> finish()).setView(congratsImg);
            winDialog.show();
        }
    }

    private void updateTotalGames() {
        int numGames = options.getTotalGames();
        saveTotalGames(++numGames);
        options.setTotalGames(numGames);
    }

    private void updateBestScore(int currentScore) {
        int bestScore = options.getBestScore();

        if (bestScore == getResources().getInteger(R.integer.no_best_score) || bestScore > currentScore) {
            saveGameConfigScore(currentScore, options.getTempKey());
            options.setBestScore(currentScore);
        }
    }

    private void updateMinesFoundText() {
        TextView minesFound = findViewById(R.id.minesFound);
        minesFound.setText(getString(R.string.num_bitcoin_display_text_1) + gameLogic.getNumBitcoinFound()
                + getString(R.string.num_bitcoin_display_text_2) + numMines + getString(R.string.num_bitcoin_display_text_3));
        minesFound.setTypeface(null, Typeface.BOLD);
        minesFound.setTextColor(ContextCompat.getColor(this, R.color.white));

    }

    private void updateScansUsedText() {
        TextView scansUsed = findViewById(R.id.scansUsed);
        scansUsed.setText(getString(R.string.num_attempted_scans) + gameLogic.getNumOfScansDone());
        scansUsed.setTypeface(null, Typeface.BOLD);
        scansUsed.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    private void updateRowColText(int row, int col) {
        // Updates text for grids in the same row as the clicked grid.
        for (int gridInCol = 0; gridInCol < options.getBoardDimensionY(); ++gridInCol) {
            Button button = buttons[row][gridInCol];
            Block gridToUpdate = gameGrid[row][gridInCol];

            if (!gridToUpdate.isScanned()) {
                continue;
            }
            button.setText(String.valueOf(gridToUpdate.getLocalMineCounter()));
        }

        // Updates text for grids in the same col as the clicked grid.
        for (int gridInRow = 0; gridInRow < options.getBoardDimensionX(); ++gridInRow) {
            Button button = buttons[gridInRow][col];
            Block gridToUpdate = gameGrid[gridInRow][col];

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

    private void saveTotalGames(int numGames) {
        SharedPreferences prefs = this.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(OptionsManager.getTotalGamesKey(), numGames);
        editor.apply();

        System.out.println(getTotalGames(this));
    }

    static public int getTotalGames(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        int noGames = context.getResources().getInteger(R.integer.initial_total_games);

        return prefs.getInt(OptionsManager.getTotalGamesKey(), noGames);
    }

    private void saveGameConfigScore(int bestScore, final String key) {
        SharedPreferences prefs = this.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, bestScore);
        editor.apply();
    }

    static public int getGameConfigScore(Context context, final String key) {
        SharedPreferences prefs = context.getSharedPreferences(OptionsManager.getGameData(), MODE_PRIVATE);
        int noScore = context.getResources().getInteger(R.integer.no_best_score);
        return prefs.getInt(key, noScore);
    }

}