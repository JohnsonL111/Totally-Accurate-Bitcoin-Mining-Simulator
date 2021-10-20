package cmpt276.assignments.assignment3.model;

/**
 * Class for managing the logistics of the game.
 */
public class OptionsManager {
    private int boardDimensionX;
    private int getBoardDimensionY;
    private int numMines;
    private int bestScore;
    private int totalGames;
    private String tempKey;

    // for getting best scores of specific configurations
    private static final String GAME_DATA = "GameData.OptionsManager";
    private static final String TOTAL_GAMES = "TotalGamesPrefs";

    // Options follows a singleton pattern for interactions with shared preferences.
    private static OptionsManager instance;

    private OptionsManager() {
    }

    public static OptionsManager getInstance() {
        if (instance == null) {
            instance = new OptionsManager();
        }
        return instance;
    }

    public int getBoardDimensionX() {
        return boardDimensionX;
    }

    public int getBoardDimensionY() {
        return getBoardDimensionY;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public String getTempKey() {
        return tempKey;
    }

    public static String getTotalGamesKey() {
        return TOTAL_GAMES;
    }

    public static String getGameData() {
        return GAME_DATA;
    }

    public void setBoardDimensionX(int boardDimensionX) {
        this.boardDimensionX = boardDimensionX;
    }

    public void setBoardDimensionY(int getBoardDimensionY) {
        this.getBoardDimensionY = getBoardDimensionY;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }
}
