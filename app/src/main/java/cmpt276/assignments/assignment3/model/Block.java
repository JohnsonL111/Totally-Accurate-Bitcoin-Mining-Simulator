package cmpt276.assignments.assignment3.model;

/**
 * Underlying class for each block the blockchain
 */
public class Block {
    private boolean isScanned; // so can only scan once (unless uncovered mine)
    private boolean isBitcoin;
    private boolean isBitcoinFound;
    private int localBitcoinCounter = 0; // the number after scanning the row/col

    public Block(boolean isScanned, boolean isBitcoin, boolean isBitcoinFound) {
        this.isScanned = isScanned;
        this.isBitcoin = isBitcoin;
        this.isBitcoinFound = isBitcoinFound;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public int getLocalBitcoinCounter() {
        return localBitcoinCounter;
    }

    public boolean isBitcoin() {
        return isBitcoin;
    }

    public boolean isBitcoinFound() {
        return isBitcoinFound;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public void setBitcoin(boolean bitCoin) {
        isBitcoin = bitCoin;
    }

    public void setBitcoinFound(boolean bitcoinFound) {
        isBitcoinFound = bitcoinFound;
    }

    public void setLocalBitCoinCounter(int localBitCoinCounter) {
        this.localBitcoinCounter = localBitCoinCounter;
    }

    public void decrementLocalMineCounter() {
        if (localBitcoinCounter != 0) {
            localBitcoinCounter--;
        }
    }
}
