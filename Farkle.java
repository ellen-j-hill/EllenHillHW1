/*
* This program allows the user to play a game of farkle!
* CPSC 224-01, Fall 2026
* Homework #2
* No Sources to Cite 
* 
* @author Ellen Hill
* @version v1.2 11/8/2026
 */

import java.util.*;

public class Farkle {

    public static void main(String[] args) {
        printGameStart();
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        int maxPoints = printQueryPoints();

        int numPlayers = printQueryPlayers();
        List<Players> currentPlayers = new ArrayList<>();

        for (int i = 1; i <= numPlayers; i++) {
            String userName = getUserName(i);

            Players newPlayer = new Players(userName, i, 0);
            currentPlayers.add(newPlayer);
        }

        // Declare variables that are modified round to round
        int numRounds = 0;
        Boolean playingGame = true;

        while (playingGame) {
            int roundScore = 0;
            int diceToRoll = 6;
            int tempScore = 0;
            Boolean turnOver = false;
            numRounds++;

            for (Players p : currentPlayers) {
                roundScore = 0;
                diceToRoll = 6;
                tempScore = 0;
                if (!playingGame) {
                    break;
                }
                turnOver = false;

                while (!turnOver) {
                    // Declare Arrays for dice values
                    int[] dice = {0, 0, 0, 0, 0, 0};
                    int[] diceNumberCount = {0, 0, 0, 0, 0, 0, 0};

                    // Rolling Dice
                    for (int i = 0; i < diceToRoll; i++) {
                        dice[i] = (rand.nextInt(6) + 1);
                    }

                    // Testing round hand with known values
                    int[] testHand = {5, 1, 1, 1, 1, 1};
                    for (int i = 0; i < diceToRoll; i++) {
                        dice[i] = testHand[i];
                    }

                    // Sort the round's hand
                    for (int i = 0; i < diceToRoll; i++) {
                        for (int j = 0; j < diceToRoll - i - 1; j++) {
                            if (dice[j] > dice[j + 1]) {
                                int temp = dice[j];
                                dice[j] = dice[j + 1];
                                dice[j + 1] = temp;
                            }
                        }
                    }

                    // Count Number Values
                    for (int i = 0; i < diceToRoll; i++) {
                        diceNumberCount[dice[i]]++;
                    }

                    //Check for Farkle!
                    boolean isFarkle = checkIfFarkle(diceNumberCount, diceToRoll);
                    if (isFarkle) {
                        System.out.println("Farkle! You rolled no playable dice! Points earned: 0");
                        printRoundScoreBoard(currentPlayers);
                        turnOver = true;
                    } else {
                        String input;
                        int[] meld = {0, 0, 0, 0, 0, 0};
                        boolean doneSelecting = false;
                        boolean invalidMoveTrigger = false;
                        int meldScore = 0;
                        while (doneSelecting == false) {
                            //Print Status
                            printActiveDice(numRounds, p, dice, meld);

                            meldScore = 0; //Reset each time user changes meld
                            int meldDiceCount = 0;
                            int[] meldDice = {0, 0, 0, 0, 0, 0};
                            for (int i = 0; i < 6; i++) {
                                if (meld[i] != 0) {
                                    meldDice[meldDiceCount] = meld[i];
                                    meldDiceCount++;
                                }
                            }

                            int[] meldDiceSizesCount = {0, 0, 0, 0, 0, 0, 0};
                            for (int i = 0; i < 6; i++) {
                                meldDiceSizesCount[meldDice[i]]++;
                            }

                            meldScore = calculateMeldScore(meldDiceSizesCount);
                            printOptions(meldScore);

                            input = scanner.nextLine();
                            for (int i = 0; i < input.length() && !doneSelecting; i++) {
                                char letter = Character.toUpperCase(input.charAt(i));

                                if (letter >= 'A' && letter <= 'F') {
                                    int index = letter - 'A';
                                    if (dice[index] != 0) {
                                        meld[index] = dice[index];
                                        dice[index] = 0;
                                    } else {
                                        dice[index] = meld[index];
                                        meld[index] = 0;
                                    }
                                    for (int j = 0; j < 7; j++) {
                                        meldDiceSizesCount[j] = 0;
                                    }
                                    meldDiceCount = 0;
                                    for (int j = 0; j < 6; j++) {
                                        if (meld[j] != 0) {
                                            meldDiceSizesCount[meld[j]]++;
                                            meldDiceCount++;
                                        }
                                    }
                                    meldScore = calculateMeldScore(meldDiceSizesCount);

                                } else if (letter == 'Q') {
                                    System.out.println();
                                    System.out.println("Thank you for playing for " + numRounds + " rounds");
                                    printEndGame(currentPlayers);
                                    printRoundScoreBoard(currentPlayers);
                                    doneSelecting = true;
                                    turnOver = true;
                                    playingGame = false;
                                } else if (letter == 'K') {
                                    tempScore += meldScore;
                                    diceToRoll -= meldDiceCount;
                                    if (diceToRoll == 0) {
                                        printHotHands();
                                        String hotHandChoice = scanner.nextLine().trim().toUpperCase();
                                        if (hotHandChoice.equals("Y")) {
                                            System.out.println("Preparing to roll 6 more dice...");
                                            diceToRoll = 6;
                                            doneSelecting = true;
                                        } else {
                                            System.out.println("Playing it safe! Banking your current points...");
                                            p.addPoints(tempScore);
                                            doneSelecting = true;
                                            turnOver = true;
                                            printRoundScoreBoard(currentPlayers);
                                        }
                                    } else {
                                        p.addPoints(tempScore);
                                        doneSelecting = true;
                                        turnOver = true;
                                        System.out.println();
                                        System.out.println("Round over. Your score is currently: " + p.getPoints());
                                        System.out.println();
                                        printRoundScoreBoard(currentPlayers);
                                        if (checkBeyondPointCap(maxPoints, p) && numPlayers == 1) {
                                            System.out.println("You scored above the point cap! That's Game!");
                                            System.out.println();
                                            printEndGame(currentPlayers);
                                            printRoundScoreBoard(currentPlayers);
                                            playingGame = false;
                                        } else if (checkBeyondPointCap(maxPoints, p)) {
                                            System.out.println("You scored above the point cap! We are entering the FINAL ROUND!");

                                        }
                                    }
                                } else if (letter == 'R') {
                                    if (meldScore == 0) {
                                        System.out.println("Uh oh! You need at least one scoring dice to reroll! Try adding a scoring die to your meld");
                                        doneSelecting = true;
                                        invalidMoveTrigger = true;
                                    } else {
                                        tempScore += meldScore;
                                        diceToRoll -= meldDiceCount;
                                        if (diceToRoll == 0) {
                                            printHotHands();
                                            String hotHandChoice = scanner.nextLine().trim().toUpperCase();
                                            if (hotHandChoice.equals("Y")) {
                                                System.out.println("Preparing to roll 6 more dice...");
                                                diceToRoll = 6;
                                                doneSelecting = true;
                                            } else {
                                                System.out.println("Playing it safe! Banking your current points...");
                                                p.addPoints(tempScore);
                                                doneSelecting = true;
                                                turnOver = true;
                                            }
                                        } else {
                                            System.out.println();
                                            System.out.println("You have " + diceToRoll + " dice remaining to roll. Your previously scored dice (" + tempScore + ") will not count if you fail to score these dice");
                                            System.out.println();
                                            doneSelecting = true;
                                        }
                                    }
                                    if (invalidMoveTrigger) {
                                        doneSelecting = false;
                                        invalidMoveTrigger = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static String getUserName(int indicy) {
        Scanner nameScanner = new Scanner(System.in);
        System.out.print("What is player #" + indicy + "'s name? ");
        String name = "Unknown Player";
        List<String> defaultNames = new ArrayList<>();
        defaultNames.add("The Very Best Player");
        defaultNames.add("Destined to Win Player");
        defaultNames.add("The Fated Champion");
        defaultNames.add("The Farkler");

        String givenName = nameScanner.nextLine();
        if (givenName.isEmpty()) {
            name = defaultNames.get(indicy - 1);
            System.out.println("You did not enter a name! You are " + name + "!");
        } else {
            name = givenName;
            System.out.println("A new farkle champion has entered! Welcome " + name + "!");
        }
        System.out.println();
        return name;
    }

    public static int printQueryPoints() {
        Scanner pointScanner = new Scanner(System.in);
        System.out.print("How many points would you like to play to?");
        String maxPointsString = pointScanner.nextLine();
        int maxPoints = 10000;

        if (maxPointsString.isEmpty()) {
            System.out.println("You did not enter a point max! Using the default of 10,000...");
            return maxPoints;
        } else if (Integer.parseInt(maxPointsString) < 1) {
            System.out.println("You did not enter a valid point max! Using the default of 10,000...");
            return maxPoints;
        } else {
            maxPoints = Integer.parseInt(maxPointsString);
            System.out.println("You have selected a point max of " + maxPoints + " points...");
            return maxPoints;
        }
    }

    public static void printGameStart() {
        System.out.println("************************************************************************");
        System.out.println("*                     Zag Farkle by Ellen Hill!                        *");
        System.out.println("*                         Copyright: 2026                              *");
        System.out.println("************************************************************************");
        System.out.println();
    }

    public static int printQueryPlayers() {
        Scanner playerScanner = new Scanner(System.in);
        System.out.print("How many players are playing today? (Select 1-4)");
        String numberOfPlayersString = playerScanner.nextLine();
        int numberOfPlayers = 1;

        if (numberOfPlayersString.isEmpty()) {
            System.out.println("Defaulting to 1 Player...");
            return numberOfPlayers;
        } else if (numberOfPlayers > 4) {
            System.out.println("Defaulting to 1 Player...");
            return numberOfPlayers;
        } else {
            numberOfPlayers = Integer.parseInt(numberOfPlayersString);
            return numberOfPlayers;
        }
    }

    public static void printActiveDice(int numRounds, Players player, int[] dice, int[] meld) {
        System.out.println("");
        System.out.println("******************** Round " + numRounds + " : " + player.getName() + "'s Turn! ********************");
        System.out.println("******************** Current Hand and Meld ********************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("---------------+---------------");

        for (int i = 0; i < 6; i++) {
            char option = (char) ('A' + i);
            System.out.print(" (" + option + ") ");
            if (dice[i] != 0) {
                System.out.print(dice[i]);
            } else {
                System.out.print(" ");
            }

            System.out.print("   |     ");
            if (meld[i] != 0) {
                System.out.print(meld[i]);
            } else {
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("---------------+---------------");
    }

    public static void printOptions(int meldScore) {
        System.out.println("         Meld Score: " + meldScore);
        System.out.println("");
        System.out.println("(K) Bank Meld & End Round");
        System.out.println("(R) Bank Meld & Roll Remaining Dice");
        System.out.println("(Q) Quit Game");
        System.out.println("");
        System.out.println("Enter letters for your choice(s)");
    }

    public static void printHotHands() {
        System.out.println("*********************** HOT HAND ********************");
        System.out.println("        You get to roll again with all 6 dice!       ");
        System.out.println("           Do you want to roll again? (Y/N)          ");
        System.out.println("*****************************************************");
    }

    public static boolean checkIfFarkle(int[] diceNumberCount, int diceToRoll) {
        //Check for 1s and 5s
        if (diceNumberCount[1] > 0 || diceNumberCount[5] != 0) {
            return false;
        }
        //Check for Triples
        for (int i = 2; i < 7; i++) {
            if (diceNumberCount[i] >= 3) {
                return false;
            }
        }
        //Checking for Pairs!
        int pairCount = 0;
        for (int i = 1; i < 7; i++) {
            if (diceNumberCount[i] == 2) {
                pairCount++;
            }
        }

        if (pairCount == 3 && diceToRoll == 6) {
            return false;
        }
        return true;
    }

    public static int calculateMeldScore(int[] meldDiceSizesCount) {
        int meldScore = 0;
        Boolean isFullHouse = false;
        //Checking for Straight!
        boolean isStraight = true;
        for (int i = 1; i < 7; i++) {
            if (meldDiceSizesCount[i] != 1) {
                isStraight = false;
            }
        }
        if (isStraight == true) {
            meldScore += 1000;
            return meldScore; //No available dice after these hands
        } else {
            int pairsCounter = 0;
            for (int i = 1; i < 7; i++) {
                if (meldDiceSizesCount[i] == 2) {
                    pairsCounter++;
                }
            }

            int triplesCounter = 0;
            for (int i = 1; i < 7; i++) {
                if (meldDiceSizesCount[i] == 3) {
                    triplesCounter++;
                }
            }
            //Checking for 3 Pairs
            if (pairsCounter == 3) {
                meldScore += 750;
                return meldScore; //Because there are no available dice after these hands
            } else {
                //Check for Triples & Full House
                for (int i = 1; i < 7; i++) {
                    if (meldDiceSizesCount[i] >= 3) {
                        if (pairsCounter == 1 || triplesCounter == 2) {
                            if (isFullHouse == false) {
                                isFullHouse = true;
                                meldScore += 1500;
                            }
                        } else {
                            int tripleSetPoints = 0;
                            if (i == 1) {
                                tripleSetPoints = 1000;
                            } else {
                                tripleSetPoints = (i * 100);
                            }
                            if (meldDiceSizesCount[i] > 3) {
                                tripleSetPoints += (meldDiceSizesCount[i] - 3) * 100 * 1;
                            }
                            meldScore += tripleSetPoints;
                        }
                    }
                }
            }
            if (isFullHouse == true && meldDiceSizesCount[1] + meldDiceSizesCount[5] == 6) {
                if (meldDiceSizesCount[1] >= meldDiceSizesCount[5]) {
                    meldScore += 100;
                } else {
                    meldScore += 50;
                }
                return meldScore;
            } else if (isFullHouse == true && meldDiceSizesCount[1] + meldDiceSizesCount[5] == 5) {
                return meldScore;
            } else if (isFullHouse == true && meldDiceSizesCount[1] == 1 || isFullHouse == true && meldDiceSizesCount[1] == 4) {
                meldScore += 100;
                return meldScore;
            } else if (isFullHouse == true && meldDiceSizesCount[5] == 1 || isFullHouse == true && meldDiceSizesCount[5] == 4) {
                meldScore += 50;
                return meldScore;
            } else {// Add 1s and 5s if unused
                if (meldDiceSizesCount[1] < 3) {
                    meldScore += meldDiceSizesCount[1] * 100;
                }
                if (meldDiceSizesCount[5] < 3) {
                    meldScore += meldDiceSizesCount[5] * 50;
                }
                return meldScore;
            }
        }
    }

    public static void printRoundScoreBoard(List<Players> currentPlayers) {
        System.out.println("*********************** SCORE BOARD ***********************");
        for (Players p : currentPlayers) {
            System.out.println("     " + p.getName() + ": " + p.getPoints());
        }
        System.out.println("***********************************************************");
    }

    public static void printEndGame(List<Players> currentPlayers) {
        String winnerName = "No One";
        int winningPoints = 0;
        for (Players p : currentPlayers) {
            if (p.getPoints() > winningPoints) {
                winningPoints = p.getPoints();
                winnerName = p.getName();
            }
        }
        System.out.println("* Congratulations " + winnerName + " won with " + winningPoints + "! *");
    }

    public static boolean checkBeyondPointCap(int pointCap, Players p) {
        if (pointCap > p.getPoints()) {
            return false;
        }
        return true;
    }
}
