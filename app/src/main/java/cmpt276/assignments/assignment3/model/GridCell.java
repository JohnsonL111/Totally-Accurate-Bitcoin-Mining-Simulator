package cmpt276.assignments.assignment3.model;

public class GridCell {
    private boolean isScanned; // so can only scan once (unless uncovered mine)
    private boolean isMine;
    private boolean isMineFound;
    private int localMineCounter = 0; // the number after scanning the row/col

    public GridCell(boolean isScanned, boolean isMine, boolean isMineFound) {
        this.isScanned = isScanned;
        this.isMine = isMine;
        this.isMineFound = isMineFound;
    }

    // getter
    public boolean isScanned() {
        return isScanned;
    }
    public int getLocalMineCounter() {
        return localMineCounter;
    }
    public boolean isMine() {
        return isMine;
    }
    public boolean isMineFound() {
        return isMineFound;
    }

    // setter
    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }
    public void setMine(boolean mine) {
        isMine = mine;
    }
    public void setMineFound(boolean mineFound) {
        isMineFound = mineFound;
    }
    public void setLocalMineCounter(int localMineCounter) {
        this.localMineCounter = localMineCounter;
    }

    public void decrementLocalMineCounter () {
        if (localMineCounter != 0) {
            localMineCounter--;
        }
    }
}
