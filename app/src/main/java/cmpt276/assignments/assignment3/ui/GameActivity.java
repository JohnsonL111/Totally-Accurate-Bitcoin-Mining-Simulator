package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import cmpt276.assignments.assignment3.R;
import cmpt276.assignments.assignment3.model.GameManager;
import cmpt276.assignments.assignment3.model.GridCell;
import cmpt276.assignments.assignment3.model.OptionsManager;

// Implements grid UI functionality.
// Interacts with GameManager.java when a grid is clicked.
// Citation: https://www.youtube.com/watch?v=4MFzuP1F-xQ (Brian Frasers Dynamic Button + images vid).
public class GameActivity extends AppCompatActivity {

    private OptionsManager options;
    private GameManager gameLogic;
    private Button[][] buttons;
    private GridCell[][] gameGrid;
    private static int numMines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        options = OptionsManager.getInstance();

        setUpGrid(options.getBoardDimensionX(), options.getGetBoardDimensionY(), options.getNumMines());
        populateButtons();
        updateScansUsedText();
        updateMinesFoundText();
    }

    private void setUpGrid(final int dimX, final int dimY, int numMines) {
        this.numMines = options.getNumMines();
        buttons = new Button[dimX][dimY];
        gameLogic = new GameManager();
        gameGrid = gameLogic.getGridCells();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void populateButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for (int row = 0; row < options.getBoardDimensionX(); ++row) {
            TableRow tableRow = new TableRow(this);

            // Set scaling layout for tableRow view.
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));

            table.addView(tableRow);
            for (int col = 0; col < options.getGetBoardDimensionY(); ++col) {
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
            button.setText("" + clickedGrid.getLocalMineCounter());
            // Basic case for non-mine grid.
        } else {
            gameLogic.scan(row, col);
            button.setText("" + clickedGrid.getLocalMineCounter());
        }

        updateScansUsedText();
        updateMinesFoundText();
        checkIfWin();
    }

    private void checkIfWin() {
        // Displays winning message.
        if (gameLogic.getNumOfMinesFound() == numMines) {
            AlertDialog.Builder winDialog = new AlertDialog.Builder(GameActivity.this);
            winDialog.setTitle("Congratulations!");
            winDialog.setMessage("Good work on finding those bit coins :)");
            winDialog.setCancelable(false);
            winDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Switch back to main menu when you win.
                    Intent mainMenuIntent = MainMenuActivity.makeIntent(GameActivity.this);
                    startActivity(mainMenuIntent);
                }
            });
            winDialog.show();

        }
    }

    private void updateMinesFoundText() {
        TextView minesFound = (TextView)findViewById(R.id.minesFound);
        minesFound.setText("Found " +  gameLogic.getNumOfMinesFound()
                            + " of " + numMines + " bitcoins");
    }

    private void updateScansUsedText() {
        TextView scansUsed = (TextView)findViewById(R.id.scansUsed);
        scansUsed.setText("Hashes (scans) Used: " +  gameLogic.getNumOfScansDone());
    }

    private void updateRowColText(int row, int col) {
        // Updates text for grids in the same row as the clicked grid.
        for (int gridInCol = 0; gridInCol < options.getGetBoardDimensionY(); ++gridInCol) {
            Button button = buttons[row][gridInCol];
            GridCell gridToUpdate = gameGrid[row][gridInCol];

            if (!gridToUpdate.isScanned()) {
                continue;
            }
            button.setText("" + gridToUpdate.getLocalMineCounter());
        }

        // Updates text for grids in the same col as the clicked grid.
        for (int gridInRow = 0; gridInRow < options.getBoardDimensionX(); ++gridInRow) {
            Button button = buttons[gridInRow][col];
            GridCell gridToUpdate = gameGrid[gridInRow][col];

            if (!gridToUpdate.isScanned()) {
                continue;
            }
            button.setText("" + gridToUpdate.getLocalMineCounter());
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < options.getBoardDimensionX(); ++row) {
            for (int col = 0; col < options.getGetBoardDimensionY(); ++col) {
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

}