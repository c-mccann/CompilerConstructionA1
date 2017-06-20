/**
 * Created by carlmccann2 on 17/02/15.
 * 12508463
 * Compiler Construction Assignment 1
 */

import java.util.Arrays;

public class SymbolTable {

    private static int[] intArray = new int[512];
    private static char[] charArray = new char[4096];

    private static int[] primes = new int[]   {13001, 13003, 13007, 13009, 13033,
            13037, 13043, 13049, 13063, 13093,
            13099, 13103, 13109, 13121, 13127, 13147, 13151, 13159, 13163, 13171,
            13177, 13183, 13187, 13217, 13219};
    private static int counter1 = -1;
    private static int counter2 = 0;
    private static int total = 0;


    public static void symbolTable(){
        Arrays.fill(charArray,'~');
        Arrays.fill(intArray,-1);
    }

    public static void firstMethod (char next){         // stores next char into array and sums total

        charArray[counter2] = next;
        counter2++;
        total += (int) next * primes[(counter2 - counter1 - 2) % primes.length];
        //c2-c1-2 gives sequence of 0,1,2,3...
    }

    public static int secondMethod () {
        int hash = total % 512;
        if (intArray[hash] == -1 ){                        // empty
            intArray[hash] = counter1 + 1;
            counter1 = counter2;
            counter2++;
        }
        else{
            if( thirdMethod(counter1 + 1, intArray[hash])){                                         // same symbol
                for (int i = counter1; i < counter2 ; i++) {
                    Arrays.fill(charArray, counter1, counter2, '~');
                }
                counter2 = counter1 + 1;
            }
            else {                                                                                 // different symbol
                total++;
                hash = secondMethod();
            }
        }
        //System.out.println("hash = " + hash);                 // to check if bogota hash was ++
        total = 0;
        return hash;

    }

    public static boolean thirdMethod (int first, int second) {                      // return boolean if same symbol
        boolean match = false;
        while( charArray[first] != '~' ){

            if( charArray[first] == charArray[second]){
                match = true;
            }
            else{
                match = false;
            }
            first++;
            second++;
            if ( !match ) {
                break;
            }
        }
        if (charArray[first] == '~' && charArray[first] != charArray[second]){
            match = false;
        }
        return match;
    }

    public static void main(String[] args) {                        // TESTS

        System.out.println("Hash Table Program");

        String myString = "tom dick harry dick tom ELIPORTER bogota bog";
        symbolTable();
        for(int i = 0; i < myString.length();i++)
        {
            if(myString.charAt(i) == ' '){

                System.out.println("");
                System.out.println("Counter 1: " + counter1);           //tests
                System.out.println("Counter 2: " + counter2);
                System.out.println("Total: " + total);
                System.out.println("Total modulo 512: " + total%512);
                System.out.print("Char Array: ");
                System.out.println(Arrays.toString(charArray));
                System.out.print("Int Array: ");
                System.out.println(Arrays.toString(intArray));

                secondMethod();
            }
            else{
                firstMethod(myString.charAt(i));
            }
        }

        System.out.println();
        System.out.println("intArray[316] = " + intArray[316]);
        System.out.println("intArray[303] = " + intArray[303]);
        System.out.println("intArray[500] = " + intArray[500]);
        System.out.println("intArray[432] = " + intArray[432]);
        System.out.println("intArray[64] = " + intArray[64] + " should be 25");
        System.out.println("intArray[65] = " + intArray[65] + " should be 29");
        System.out.println();

        System.out.println("");
        System.out.println("Counter 1: " + counter1);           //tests
        System.out.println("Counter 2: " + counter2);
        System.out.println("Total: " + total);
        System.out.println("Total modulo 512: " + total%512);
        System.out.print("Char Array: ");
        System.out.println(Arrays.toString(charArray));
        System.out.print("Int Array: ");
        System.out.println(Arrays.toString(intArray));
    }
}