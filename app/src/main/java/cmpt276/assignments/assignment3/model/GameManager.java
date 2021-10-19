package cmpt276.assignments.assignment3.model;

import java.util.Random;

// Handles Game Functionality like:
// (1) Handles which blocks contain bitcoins (sets the bitcoin locations).
// (2) Getting number of bitcoin vertically/horizontally (scan).
// (3) Dynamically updating bitcoin counts in row/col when a new bitcoin is found.
public class GameManager {
    private final int NUM_MINES;
    private final int NUM_ROWS;
    private final int NUM_COLS;
    private static Block[][] blockChain;
    private int numOfScansDone = 0;
    private int numBitcoinFound = 0;

    public void IncrementNumOfScansDone() {
        numOfScansDone++;
    }

    public void IncrementNumBitcoinFound() {
        numBitcoinFound++;
    }

    public int getNumOfScansDone() {
        return numOfScansDone;
    }

    public int getNumBitcoinFound() {
        return numBitcoinFound;
    }

    public Block[][] getGridCells() {
        return blockChain;
    }

    public GameManager(int dimX, int dimY, int numBitcoin) {
        NUM_MINES = numBitcoin;
        NUM_ROWS = dimX;
        NUM_COLS = dimY;
        blockChain = new Block[NUM_ROWS][NUM_COLS];

        fillBlockChain();
        setBitCoinCords();
    }

    // Instantiate Block objects.
    private void fillBlockChain(){
        for (int row = 0; row < NUM_ROWS; ++row) {
            for (int col = 0; col < NUM_COLS; ++col) {
                blockChain[row][col] = new Block(false, false, false);
            }
        }
    }

    public void setBitCoinCords() {
        int numGeneratedBitcoins = 0;

        while (numGeneratedBitcoins < NUM_MINES) {
            int row = getRandomValue(NUM_ROWS);
            int col = getRandomValue(NUM_COLS);

            Block grid = blockChain[row][col];

            // Only set the bitcoin if it is not already a bitcoin.
            if (!grid.isBitcoin()) {
                grid.setBitcoin(true);
                numGeneratedBitcoins++;
            }
        }
    }

    // For generating mine locations.
    public int getRandomValue(int upperBound) {
        Random rand = new Random();
        return rand.nextInt(upperBound);
    }

    // Scans for bitcoin in a specific grid entry.
    public void scan(int row, int col) {
        Block gridToScan = blockChain[row][col];

        // Guard statement if scanned already.
        if (gridToScan.isScanned()) {
            return;
        }

        int numBitcoin = 0;
        IncrementNumOfScansDone();

        // Scans all grids in the row for num of bitcoins.
        for (int gridInCol = 0; gridInCol < NUM_COLS; ++gridInCol) {
            Block scannedGrid = blockChain[row][gridInCol];

            // Guard to avoid counting the clicked entry
            if (gridInCol == col) {
                continue;
            }

            // Update number of bitcoin found.
            if (scannedGrid.isBitcoin() && !scannedGrid.isMineFound()) {
                numBitcoin++;
            }

            // Update bitCoinCounter in its row.
            if (gridToScan.isBitcoin()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

        // Scans all grids in the col for num of bitcoin
        for (int gridInRow = 0; gridInRow < NUM_ROWS; ++gridInRow) {
            Block scannedGrid = blockChain[gridInRow][col];

            // Guard to avoid counting the clicked entry
            if (gridInRow == row) {
                continue;
            }

            // Update number of bitcoins found.
            if (scannedGrid.isBitcoin() && !scannedGrid.isMineFound()) {
                numBitcoin++;
            }

            // Update bitCoinCounter in its row.
            if (gridToScan.isBitcoin()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

        if (!gridToScan.isBitcoin()) {
            gridToScan.setScanned(true);
        }

        gridToScan.setLocalBitCoinCounter(numBitcoin);
    }

    public void updateRowColValues(int row, int col) {
        Block gridToScan = blockChain[row][col];

        // Scans all grids in the row for num of bitcoin
        for (int gridInCol = 0; gridInCol < NUM_COLS; ++gridInCol) {
            Block scannedGrid = blockChain[row][gridInCol];

            // Guard to avoid counting the clicked entry
            if (gridInCol == col) {
                continue;
            }

            // Update mineCounter in its row.
            if (gridToScan.isBitcoin()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

        // Scans all grids in the col for num of bitcoin
        for (int gridInRow = 0; gridInRow < NUM_ROWS; ++gridInRow) {
            Block scannedGrid = blockChain[gridInRow][col];

            // Guard to avoid counting the clicked entry
            if (gridInRow == row) {
                continue;
            }

            // Update mineCounter in its row.
            if (gridToScan.isBitcoin()) {
                scannedGrid.decrementLocalMineCounter();
            }
        }

    }
}