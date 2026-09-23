import java.util.Random;

public class Main{
public static void main (String[] args){
    System. out.println("Farkle Rolling & Scoring in Java!");
    Random rand = new Random();

    int[] dice = {0,0,0,0,0,0};
        for (int i = 0; i<6; i++){       
            dice[i] = (rand.nextInt(5)+1);
            System. out.println("Placed " + dice[i] + " at " + i);
        }
    //Testing Hand with known values
    /*    int[] testhand = {1, 5, 5, 3, 4, 5};
        for (int i = 0; i < 6; i++){
                dice[i] = testhand[i];
        }
    */

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

    //Reviewing Test /*
     for (int i = 0; i<6; i++){       
        System. out.println(dice[i] + " is at position " + i);
        }
    }
}
    //*/
/*
    int age = 30;
    float price = 10.99F; //Suffix is "F" to ensure that it is a float
    char letter = 'a'; // Suffix is to use single quotes, and double quotes are for strings
    System. out.println(age);
    System. out.println(price);
    System. out.println(letter);

// Escape Sequences
System.out.println("\tIt's a \"beautiful\" day");

// Arrays
int[] myNum = {10,20,30,40};
// Array Indexes start at 0, and 1 is the second element
System.out.println(myNum[0]);

//Multidimensional arrays!
int[] [] myNums = {{1,2},{3,4,5},{6,7,8,9}};
System.out.println(myNums[0][0]);
System.out.println(myNums[1][2]);
}*/