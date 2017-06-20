import java.io.FileReader;
import java.io.IOException;

/**
 * Created by carlmccann2 on 18/02/15.
 * 12508463
 * Compiler Construction Assignment 1
 */


public class Lexer {
    public static int currHash = 0;
    public static int State = 0;
    public static final char[] Delims = {'(',')',';'};
    public static SymbolTable mySymbols = new SymbolTable();
    public static String tempString = "";
    public static String intString = "";


    public  static void driver() throws java.io.IOException {
        FileReader input= null;

        mySymbols.symbolTable();
        mySymbols.firstMethod('s');
        mySymbols.firstMethod('t');
        mySymbols.firstMethod('o');
        mySymbols.firstMethod('p');
        mySymbols.firstMethod('l');
        mySymbols.firstMethod('e');
        mySymbols.firstMethod('x');
        mySymbols.firstMethod('e');
        mySymbols.firstMethod('r');
        int finish = mySymbols.secondMethod();

        try {
            input = new FileReader("src/tobelexed");
            int c;
            c = input.read();
            boolean temp = true;

            while ( finish != currHash ) {
                if ( c != -1  && temp){
                    temp = Automaton((char)c);
                    if ( temp != true ){
                        temp = Automaton((char)c);
                    }
                    c=input.read();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (input != null) {
                input.close();
            }
        }
    }

    public static boolean Automaton(char currChar){
        boolean moveOn = false;
        switch (State){

            case 0:                                               // delims + whitespace
                if ( Character.isWhitespace(currChar) || new String(Delims).indexOf(currChar) != -1){
                    State = 0;
                    if(Character.isWhitespace(currChar)){
                        moveOn= true;
                    }
                    if (currChar == '('){
                        System.out.println("<lpar,0>");
                        moveOn = true;
                    }
                    else if (currChar == ')'){
                        System.out.println("<rpar,0>");
                        moveOn = true;
                    }

                    else if (currChar == ';'){
                        System.out.println("<scol,0>");
                        moveOn = true;
                    }

                } else if (Character.isDigit(currChar)){
                    State = 1;
                    moveOn = true;
                    intString += currChar;
                } else if (Character.isUpperCase(currChar)){
                    State = 2;
                    mySymbols.firstMethod(currChar);
                    moveOn = true;
                }
                else if (Character.isLowerCase(currChar)){
                    State = 3;
                    mySymbols.firstMethod(currChar);
                    moveOn = true;
                }
                else if (currChar == '\"'){
                    State = 6;
                    moveOn = true;
                }
                else{
                    System.out.println("<error,0>");
                    moveOn = true;
                }
                break;

            case 1:                                                         // digits
                if (Character.isDigit(currChar)){
                    State = 1;
                    moveOn = true;
                    intString += currChar;
                }
                else if( false ) {                                          // MAX INT 12345678
                    State = 4;
                }
                else if ( !Character.isDigit(currChar) ){
                    State = 0;
                    moveOn = true;                                         // currChar is new token
                    System.out.println("<int,"+intString+">");
                    intString = "";
                }
                break;

            case 2:                                                         // Upper Case
                if (Character.isUpperCase(currChar)){
                    State = 2;
                    moveOn = true;
                    mySymbols.firstMethod(currChar);

                }
                else if ( Character.isDigit(currChar)){
                    State = 5;
                    moveOn = true;
                }
                else{
                    State = 0;
                    System.out.println("<error,0>");
                    moveOn = false;
                }
                break;

            case 3:                                                         // Lower Case
                if (Character.isLowerCase(currChar)){
                    mySymbols.firstMethod(currChar);
                    State = 3;
                    moveOn = true;
                }
                else{
                    currHash = mySymbols.secondMethod();                    // seperate token
                    System.out.println("<id," +currHash+">");           // mySymbols.secondMethod()
                    State = 0;
                    moveOn = false;
                }
                break;

            case 4:                                                         // Max INT
                moveOn = true;
                break;

            case 5:                                                         // EXAMPLE4
                if ( Character.isDigit(currChar)){
                    State = 5;
                    moveOn = true;
                }
                else{
                    State = 0;
                    currHash = mySymbols.secondMethod();
                    moveOn = true;
                    System.out.println("<id,"+ currHash+">");
                }
                break;

            case 6:                                                         // Start of String "
                if(currChar == '|'){
                    State = 7;
                    moveOn = true;
                }
                else if( currChar == '\"'){
                    State = 8;
                    moveOn=true;
                }
                else{
                    tempString += currChar;
                    moveOn = true;
                }
                break;

            case 7:                                                         // ANY CHAR ||||||
                tempString += currChar;
                State = 6;
                moveOn = true;
                break;

            case 8:                                                         // End of String "
                System.out.println("<string,"+tempString+">");
                tempString = "";
                moveOn = true;
                State = 0;
                Automaton(currChar);
                break;
        }
        return moveOn;
    }

    public static void main(String[] args) throws IOException {
        driver();
    }
}