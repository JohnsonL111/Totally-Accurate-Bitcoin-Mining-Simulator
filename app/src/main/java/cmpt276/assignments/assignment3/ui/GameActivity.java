package cmpt276.assignments.assignment3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import android.widget.Toast;

import cmpt276.assignments.assignment3.R;

// Implements grid UI functionality.
// Interacts with GameManager.java when a grid is clicked.
// Citation: https://www.youtube.com/watch?v=4MFzuP1F-xQ (Brian Frasers Dynamic Button + images vid).
public class GameActivity extends AppCompatActivity {

    // TODO: Make it so this is not hard-coded.
    private static final int NUM_ROWS = 7;
    private static final int NUM_COLS = 10;

    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        populateButtons();
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, GameActivity.class);
    }

    private void populateButtons() {
        TableLayout table = (TableLayout)findViewById(R.id.tableForButtons);
        for (int row = 0; row < NUM_ROWS; ++row) {
            TableRow tableRow = new TableRow(this);

            // Set scaling layout for tableRow view.
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));

            table.addView(tableRow);
            for (int col = 0; col < NUM_COLS; ++col) {
                final int FINAL_COL = col;
                final int FINAL_ROW = row;

                Button button = new Button(this);

                // Set scaling layout for button view.
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setText("(" + row + "," + col + ")");

                // Makes text not clip on smaller buttons
                button.setPadding(0,0,0,0);

                // Implements On-Click functionality for grids.
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gridButtonClicked(FINAL_COL, FINAL_ROW);
                    }
                });
                tableRow.addView(button);
                buttons[row][col] = button;
            }
        }
    }

    private void gridButtonClicked(int col, int row) {
        Toast.makeText(this, "Button clicked at (" + col + "," + row + ")",
                Toast.LENGTH_SHORT).show();

        Button button = buttons[row][col];

        // Lock button sizes.
        lockButtonSizes();

        // Scale image to button on-click.
        int newWidth = button.getWidth();
        int newHeight = button.getHeight();
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.action_lock_silver);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
        Resources resource = getResources();
        button.setBackground(new BitmapDrawable(resource, scaledBitmap));

        // Change text on button click.
        button.setText("" + col);

    }

    private void lockButtonSizes() {
        for (int row = 0; row < NUM_ROWS; ++row) {
            for (int col = 0; col < NUM_COLS; ++col) {
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