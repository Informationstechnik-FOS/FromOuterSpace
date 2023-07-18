package Code;

public class ScoreData {
    private String playerName1;
    private String playerName2;
    private int score;

    public ScoreData(int score, String playerName1, String playerName2) {
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.score = score;
    }

    public String getPlayerName1() {
        return playerName1;
    }

    public String getPlayerName2() {
        return playerName2;
    }

    public int getScore() {
        return score;
    }
}
