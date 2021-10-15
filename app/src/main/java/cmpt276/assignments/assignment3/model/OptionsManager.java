package cmpt276.assignments.assignment3.model;

public class OptionsManager {
    private int boardDimensionX;
    private int getBoardDimensionY;
    private int numMines;

    // singleton pattern
    private static OptionsManager instance;
    private OptionsManager(){}

    // get instance of singleton Options
    public static OptionsManager getInstance(){
        if(instance == null){
            instance = new OptionsManager();
        }
        return instance;
    }

    // getters
    public int getBoardDimensionX() {
        return boardDimensionX;
    }
    public int getGetBoardDimensionY() {
        return getBoardDimensionY;
    }
    public int getNumMines() {
        return numMines;
    }

    // setters
    public void setBoardDimensionX(int boardDimensionX) {
        this.boardDimensionX = boardDimensionX;
    }
    public void setBoardDimensionY(int getBoardDimensionY) {
        this.getBoardDimensionY = getBoardDimensionY;
    }
    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }
}
