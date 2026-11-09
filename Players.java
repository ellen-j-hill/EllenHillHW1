
public class Players {

    private String playerName;
    private int index;
    private int scoredPoints;

    public Players(String inputName, int inputTurn, int inputPoints) {
        playerName = inputName;
        index = inputTurn;
        scoredPoints = inputPoints;
    }

    public String getName() {
        return playerName;
    }

    public void addPoints(int earnedPoints) {
        scoredPoints += earnedPoints;
    }

    public int getPoints() {
        return scoredPoints;
    }

    public int getIndex() {
        return index;
    }
}
