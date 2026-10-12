/*
* This program allows the user to play a game of farkle!
* CPSC 224-01, Fall 2026
* Homework #2
* No Sources to Cite 
* 
* @author Ellen Hill
* @version v1.2 10/11/2026
*/

import java.util.Random;
import java.util.Scanner;

public class Main{
    public static void main (String[] args){
        printGameStart();
        String userName = getUserName();
        Random rand = new Random();
        Scanner scanner = new Scanner(System.in);

        // Declare variables that are modified round to round
        int numRounds = 1;
        int totalScore = 0;
        Boolean playingGame = true;

        while (playingGame){
            // Declare variables that reset at the round
            int diceToRoll = 6;
            int tempScore = 0;
            Boolean turnOver = false; 
                
            while (!turnOver){
                // Declare Arrays for dice values
                    int[] dice = {0,0,0,0,0,0};
                    int[] diceNumberCount = {0,0,0,0,0,0,0};

                // Rolling Dice
                    for (int i = 0; i<diceToRoll; i++){       
                        dice[i] = (rand.nextInt(6)+1);
                    }

                // Testing round hand with known values
                    // int[] testHand = { 2, 2, 2, 5, 1, 5};
                    // for( int i = 0; i < diceToRoll; i++ ) {
                    // dice[i] = testHand[i];
                    // }
                    
                // Sort the round's hand
                    for (int i = 0; i<diceToRoll; i++){  
                        for (int j = 0; j < diceToRoll - i - 1; j++) {
                            if (dice[j] > dice[j+1]){
                                int temp = dice[j];
                                dice[j] = dice[j+1];
                                dice[j+1] = temp; 
                                }
                            }
                        }
                // Print the Hand
                /*
                System. out.print("Hand: ");
                    for (int i = 0; i<6; i++){       
                    System.out.print(dice[i] + " ");
                    }
                System. out.println(" ");
                */
                // Count Number Values
                    for (int i = 0; i < diceToRoll; i++){
                            diceNumberCount[dice[i]]++; 
                    }
                /*
                    System. out.println("Quantity of each die value: ");
                    for( int i = 1; i < 7; i++){
                        System.out.println(i + ": "+ diceNumberCount[i] + " ");
                    }
                */
               //Check for Farkle!
                boolean isFarkle = checkIfFarkle(diceNumberCount, diceToRoll);
                    if (isFarkle){
                        System.out.println("Farkle! You rolled no playable dice! Points earned: 0");
                        turnOver = true;
                    }else{
                        String input;
                        int[] meld = {0,0,0,0,0,0};
                        boolean doneSelecting = false;
                        boolean invalidMoveTrigger = false;
                        int meldScore = 0;
                        while (doneSelecting==false){
                            //Print Status
                            printActiveDice(numRounds, userName, dice, meld);

                            meldScore = 0; //Reset each time user changes meld
                            int meldDiceCount = 0;
                            int[] meldDice = {0,0,0,0,0,0};
                            for(int i=0; i < 6; i++){
                                if(meld[i] != 0){
                                    meldDice[meldDiceCount] = meld[i];
                                    meldDiceCount++;
                                }
                            }
                            
                            int[] meldDiceSizesCount = {0,0,0,0,0,0,0};
                            for(int i = 0; i < 6; i++) {
                                meldDiceSizesCount[meldDice[i]]++;
                            }

                            meldScore = calculateMeldScore(meldDiceSizesCount);            
                            printOptions(meldScore);

                            input = scanner.nextLine();
                            for (int i = 0; i < input.length() && !doneSelecting; i++){
                                char letter = Character.toUpperCase(input.charAt(i));
                                
                                if( letter >= 'A' && letter <='F'){
                                    int index = letter - 'A';
                                    if(dice[index] != 0){
                                        meld[index] = dice[index];
                                        dice[index] = 0;
                                    } else {
                                        dice[index] = meld[index];
                                        meld[index] = 0; 
                                    }
                                    for (int j = 0; j < 7; j++){
                                        meldDiceSizesCount[j] = 0;
                                    }
                                    meldDiceCount = 0;
                                    for (int j = 0; j < 6; j++){
                                        if (meld[j] != 0) {
                                            meldDiceSizesCount[meld[j]]++;
                                            meldDiceCount++;
                                        }
                                    }
                                    meldScore = calculateMeldScore(meldDiceSizesCount);

                                } else if (letter == 'Q'){
                                    System.out.println();
                                    System.out.println("Thank you for playing for " + numRounds + " rounds, " + userName + "!");
                                    System.out.println("Game over. Your final score is: " + totalScore);
                                    System.out.println();
                                    doneSelecting = true;
                                    turnOver = true; 
                                    playingGame = false;
                                } else if (letter == 'K'){
                                    tempScore += meldScore;
                                    diceToRoll -= meldDiceCount;
                                    if (diceToRoll == 0){
                                            printHotHands();
                                            String hotHandChoice = scanner.nextLine().trim().toUpperCase();
                                            if (hotHandChoice.equals("Y")){
                                                System.out.println("Preparing to roll 6 more dice...");
                                                diceToRoll=6;
                                                doneSelecting=true;
                                            }else{
                                                System.out.println("Playing it safe! Banking your current points...");
                                                totalScore += tempScore;
                                                numRounds++;
                                                doneSelecting = true;
                                                turnOver=true; 
                                            }
                                    }else{
                                        totalScore += tempScore;
                                        numRounds++;
                                        doneSelecting = true;
                                        turnOver=true; 
                                        System.out.println();
                                        System.out.println("Round over. Your score is currently: " + totalScore);
                                        System.out.println();
                                    }
                                } else if (letter == 'R'){
                                    if(meldScore == 0 ){
                                        System.out.println("Uh oh! You need at least one scoring dice to reroll! Try adding a scoring die to your meld");
                                        doneSelecting = true; 
                                        invalidMoveTrigger = true;
                                    }else{

                                        tempScore += meldScore;
                                        diceToRoll -= meldDiceCount; 
                                        if (diceToRoll == 0){
                                                printHotHands();
                                                String hotHandChoice = scanner.nextLine().trim().toUpperCase();
                                                if (hotHandChoice.equals("Y")){
                                                    System.out.println("Preparing to roll 6 more dice...");
                                                    diceToRoll=6;
                                                    doneSelecting=true;
                                                }else{
                                                    System.out.println("Playing it safe! Banking your current points...");
                                                    totalScore += tempScore;
                                                    numRounds++;
                                                    doneSelecting = true;
                                                    turnOver=true; 
                                                }
                                        }else{
                                        System.out.println();
                                        System.out.println("You have " + diceToRoll + " dice remaining to roll. Your previously scored dice (" + tempScore + ") will not count if you fail to score these dice");
                                        System.out.println();
                                        doneSelecting = true;
                                        }
                                }
                                if (invalidMoveTrigger){
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
   
    public static String getUserName(){
        Scanner nameScanner = new Scanner(System.in);
        System.out.print("Who is playing today?");
        String name = "Unknown Player";
        String givenName = nameScanner.nextLine(); 
            if (givenName.isEmpty()){
                System.out.println("You did not enter a name! You are an "+ name +"!");
            } else {
                name = givenName; 
                System.out.println("A new farkle champion has entered! Welcome " + name + "!");
            }
        System.out.println();
        return name; 
    }

    public static void printGameStart(){
        System.out.println("************************************************************************");
        System.out.println("*                     Zag Farkle by Ellen Hill!                        *");
        System.out.println("*                         Copyright: 2026                              *");
        System.out.println("************************************************************************");
        System.out.println();
        }

    public static void printActiveDice(int numRounds, String userName, int[] dice, int[] meld) {
        System.out.println("");
        System.out.println("******************** Round " + numRounds + " : " + userName + "'s Turn! ********************");
        System.out.println("******************** Current Hand and Meld ********************");
        System.out.println(" Die   Hand |   Meld");
        System.out.println("---------------+---------------");

        for(int i =0; i<6; i++){
            char option = (char) ('A' + i); 
            System.out.print(" (" + option + ") ");
            if (dice[i] !=0){
                System.out.print(dice[i]);
            }else{System.out.print(" ");}
            
            System.out.print("   |     ");
            if(meld[i] != 0){
                System.out.print(meld[i]);
            }else{System.out.print(" ");}
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

    public static void printHotHands(){
        System.out.println("*********************** HOT HAND ********************");
        System.out.println("        You get to roll again with all 6 dice!       ");
        System.out.println("           Do you want to roll again? (Y/N)          ");
        System.out.println("*****************************************************");
    }

    public static boolean checkIfFarkle(int[] diceNumberCount, int diceToRoll){
        //Check for 1s and 5s
        if (diceNumberCount[1] > 0 || diceNumberCount[5] !=0){
            return false;
        }
        //Check for Triples
        for (int i = 2; i < 7; i++){
            if(diceNumberCount[i] >= 3){
                return false;
            }
        }
        //Checking for Pairs!
        int pairCount = 0;
        for ( int i = 1; i < 7; i++){
            if (diceNumberCount[i] == 2){
                pairCount++;
            }
        }

        if(pairCount==3 && diceToRoll == 6){
            return false;
        }
        return true;
    }

    public static int calculateMeldScore(int[] meldDiceSizesCount){
        int meldScore = 0;
        //Checking for Straight!
        boolean isStraight = true;
            for( int i = 1; i <7; i++){
                if(meldDiceSizesCount[i] !=1){
                    isStraight=false; 
                }
            }
            if(isStraight == true){
                meldScore+=1000;
                return meldScore;
            }else{
                int pairsCounter = 0;
                for(int i = 1; i <7; i++){
                    if(meldDiceSizesCount[i] ==2){
                        pairsCounter++;
                    }
                }

            //Checking for 3 Pairs
            if(pairsCounter==3){
                    meldScore += 750; 
                    return meldScore;
            }else{
                //Check for Triples!
                Boolean isTriple = false;
                for(int i = 1; i < 7; i++){
                    if(meldDiceSizesCount[i] >=3){
                        isTriple = true;
                        int tripleSetPoints=0;
                        if(i ==1) {
                            tripleSetPoints=1000;
                        }else{
                            tripleSetPoints = (i * 100);
                        }
                        if (meldDiceSizesCount[i]>3){
                            tripleSetPoints += (meldDiceSizesCount[i]-3) *100 * 1;
                        }
                    meldScore += tripleSetPoints;
                    }
                }
            }
        
            // Add 1s and 5s if unused
            if (meldDiceSizesCount[1] < 3){
                meldScore += meldDiceSizesCount[1] * 100;
            }
            if (meldDiceSizesCount[5]<3){
                meldScore += meldDiceSizesCount[5] * 50;
            }
        }      
        return meldScore;
    }

}