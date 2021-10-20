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
    private static final String CONFIG_1 = "4x6-6";
    private static final String CONFIG_2 = "4x6-10";
    private static final String CONFIG_3 = "4x6-15";
    private static final String CONFIG_4 = "4x6-20";
    private static final String CONFIG_5 = "5x10-6";
    private static final String CONFIG_6 = "5x10-10";
    private static final String CONFIG_7 = "5x10-15";
    private static final String CONFIG_8 = "5x10-20";
    private static final String CONFIG_9 = "6x15-6";
    private static final String CONFIG_10 = "6x15-10";
    private static final String CONFIG_11 = "6x15-15";
    private static final String CONFIG_12 = "6x15-20";
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

    public static String getConfig1() {
        return CONFIG_1;
    }

    public static String getConfig2() {
        return CONFIG_2;
    }

    public static String getConfig3() {
        return CONFIG_3;
    }

    public static String getConfig4() {
        return CONFIG_4;
    }

    public static String getConfig5() {
        return CONFIG_5;
    }

    public static String getConfig6() {
        return CONFIG_6;
    }

    public static String getConfig7() {
        return CONFIG_7;
    }

    public static String getConfig8() {
        return CONFIG_8;
    }

    public static String getConfig9() {
        return CONFIG_9;
    }

    public static String getConfig10() {
        return CONFIG_10;
    }

    public static String getConfig11() {
        return CONFIG_11;
    }

    public static String getConfig12() {
        return CONFIG_12;
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
