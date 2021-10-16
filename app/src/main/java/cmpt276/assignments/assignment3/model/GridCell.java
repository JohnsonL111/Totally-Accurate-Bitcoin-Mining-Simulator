package cmpt276.assignments.assignment3.model;

public class GridCell {
    private boolean isScanned;
    private boolean isMine;
    private boolean isMineFound;
    private int localMineCounter;

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
}
