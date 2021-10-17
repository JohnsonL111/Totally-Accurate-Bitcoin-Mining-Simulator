package cmpt276.assignments.assignment3.model;

import java.util.Random;

// Comments for Guidance:
// Singleton class with functionality:
// (1) Handles which cells are bombs (sets the bomb grids?)
// (2) Getting number of bombs vertically/horizontally (scan)
public class GameManager {
    // TODO: These will be passed via options in final version
    private static final int NUM_MINES = 6;
    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 3;
    private static GridCell[][] gridCells = new GridCell[NUM_ROWS][NUM_COLS]; // 2D array of grids (hard-coded for now)
    private int numOfScansDone = 0;
    private int numOfMinesFound = 0;

    public void IncrementNumOfScansDone() {
        numOfScansDone++;
    }

    public void IncrementNumOfMinesFound() {
        numOfMinesFound++;
    }

    public int getNumOfScansDone() {
        return numOfScansDone;
    }

    public int getNumOfMinesFound() {
        return numOfMinesFound;
    }

    public GridCell[][] getGridCells() {
        return gridCells;
    }

    // TODO: The constructor should also set the rows and cols for GridCell.
    public GameManager() {

        // Instantiate grid cell objects.
        for (int row = 0; row < NUM_ROWS; ++row) {
            for (int col = 0; col < NUM_COLS; ++col) {
                gridCells[row][col] = new GridCell(false, false, false);
            }
        }

        setMineCords();
    }

    public void setMineCords() {
        int i = 0;

        while (i < NUM_MINES) {
            int row = genRandomVal(NUM_ROWS);
            int col = genRandomVal(NUM_COLS);

            GridCell grid = gridCells[row][col];

            // Only set the mine if it is not already a mine.
            if (!grid.isMine()) {
                grid.setMine(true);
                ++i;
            }
        }
    }

    // For generating mine locations.
    public int genRandomVal(int upperBound) {
        Random rand = new Random();
        return rand.nextInt(upperBound);
    }

    // Scans for bombs in a specific grid entry.
    public void scan(int row, int col) {
        GridCell gridToScan = gridCells[row][col];

        // Guard statement if scanned already.
        if (gridToScan.isScanned()) {
            return;
        }

        int numOfMine = 0;
        IncrementNumOfScansDone();

        // Scans all grids in the row for num of bombs.
        for (int gridInCol = 0; gridInCol < NUM_COLS; ++gridInCol) {
            GridCell scannedGrid = gridCells[row][gridInCol];

            // Guard to avoid counting the clicked entry and already found mines.
            if (gridInCol == col) {
                continue;
            }

            // Update number of mines found.
            if (scannedGrid.isMine() && !scannedGrid.isMineFound()) {
                numOfMine++;
            }

            // Update mineCounter in its row.
            if (gridToScan.isMine()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

        // Scans all grids in the col for num of bombs.
        for (int gridInRow = 0; gridInRow < NUM_ROWS; ++gridInRow) {
            GridCell scannedGrid = gridCells[gridInRow][col];

            // Guard to avoid counting the clicked entry and already found mines.
            if (gridInRow == row) {
                continue;
            }

            // Update number of mines found.
            if (scannedGrid.isMine() && !scannedGrid.isMineFound()) {
                numOfMine++;
            }

            // Update mineCounter in its row.
            if (gridToScan.isMine()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

        if (!gridToScan.isMine()) {
            gridToScan.setScanned(true);
        }

        gridToScan.setLocalMineCounter(numOfMine);
        return;
    }

    public void updateRowColValues(int row, int col) {
        GridCell gridToScan = gridCells[row][col];

        // Scans all grids in the row for num of bombs.
        for (int gridInCol = 0; gridInCol < NUM_COLS; ++gridInCol) {
            GridCell scannedGrid = gridCells[row][gridInCol];

            // Guard to avoid counting the clicked entry and already found mines.
            if (gridInCol == col) {
                continue;
            }

            // Update mineCounter in its row.
            if (gridToScan.isMine()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

        // Scans all grids in the col for num of bombs.
        for (int gridInRow = 0; gridInRow < NUM_ROWS; ++gridInRow) {
            GridCell scannedGrid = gridCells[gridInRow][col];

            // Guard to avoid counting the clicked entry and already found mines.
            if (gridInRow == row) {
                continue;
            }

            // Update mineCounter in its row.
            if (gridToScan.isMine()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

    }

}