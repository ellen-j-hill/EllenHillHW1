import java.util.Random;

public class Main{
public static void main (String[] args){
    System. out.println("Farkle Rolling & Scoring in Java!");
    Random rand = new Random();

    int[] dice = {0,0,0,0,0,0};
        for (int i = 0; i<6; i++){       
            dice[i] = (rand.nextInt(6)+1);
        //    System. out.println("Placed " + dice[i] + " at " + i);
        }
    //Testing Hand with known values
    
        int[] testhand = {1, 5, 5, 3, 4, 5};
        for (int i = 0; i < 6; i++){
                dice[i] = testhand[i];
        }
    //*/

    //Sort the Hand
    for (int i = 0; i<6; i++){  
        for (int j = 0; j < 6- i - 1; j++) {
            if (dice[j] > dice[j+1]){
                int temp = dice[j];
                dice[j] = dice[j+1];
                dice[j+1] = temp; 
                }
            }
        }

    // Print the Hand
    System. out.print("Hand: ");
        for (int i = 0; i<6; i++){       
        System. out.print(dice[i] + " ");
        }
    System. out.println(" ");


    // Count Number Values
        int[] diceNumberCount = {0,0,0,0,0,0,0};
        for (int i = 0; i < 6; i++){
                diceNumberCount[dice[i]]++; 
        }
    
        System. out.println("Quantity of each die value: ");
        for( int i = 1; i < 7; i++){
            System. out.println(i + ": "+ diceNumberCount[i] + " ");
        }
        System. out.println(" ");

    //Check for Farkle!
        boolean isFarkle = true;
        if (diceNumberCount[1] !=0 || diceNumberCount[5] !=0){
            isFarkle = false;
        }

        for (int i = 2; i < 7; i++){
            if(diceNumberCount[i] >= 3){
                isFarkle = false;
            }
        }

    //Checking for Pairs!
        int pairCount = 0;
        for ( int i = 1; i < 7; i++){
            if (diceNumberCount[i] == 2){
                pairCount++;
            }
        }
        if(pairCount==3){
            isFarkle = false;
        }

        int totalScore = 0;
        if (isFarkle){
            System.out.println("Farkle! Points: 0");
        }else{
            String userInput = "";
            int meldScore = 0;
            int[] meld = {0,0,0,0,0,0};
            boolean done = false;

            //while (!done){
            for (int i = 1; i <2; i++){
                //Print Status
                System.out.println("");
                System.out.println("*************************** Current hand and meld *******************");
                System.out.println(" Die   Hand |   Meld");
                System.out.println("------------+---------------");

                for(int i =0; i<6; i++){
                    char blank = 'A'; 
                    System.out.println(" (" + option + " " + i +") ");
                    if (dice[i] !=0){
                        System.out.print("dice[i]");
                    }else{System.out.print(" ");}
                    
                    System.out.print("   |     ");
                    if(meld[i] != 0){
                        System.out.print("meld[i]");
                    }else{System.out.print(" ");}
                    System.out.print("");
                }
                System.out.println("");
            }
            System.out.println("------------+---------------");
            boolean isValidMeld = false; 
            //calculate the Meld
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

                //Checking for Straight!

                boolean isStraight = true;
                for( int i = 1; i <7; i++){
                    if(meldDiceSizesCount[i] !=1){
                        isStraight=false; 
                    }
                }
                if(isStraight){
                    meldScore+=1000;
                }else{
                    int pairsCounter = 0;
                    for(int i = 1; i <7; i++){
                        if(meldDiceSizesCount[i] ==2){
                            pairsCounter++;
                        }
                    }
                if(pairsCounter==3){
                    //Check 3 Pairs
                        meldScore += 750; 
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
                            tripleSetPoints = i * 100;
                        }
                        if (meldDiceSizesCount[i]>3){
                            tripleSetPoints += (meldDiceSizesCount[i]-3) *100 * 1;
                        }
                        meldScore+= tripleSetPoints;
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
            }      
        }
    }
