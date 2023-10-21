/* Program:  Hack Assembler - Symbol Table Class (Nand2Tetris Assignment #6)
   Author:   David Reese
   Date:     December 7, 2022
   File:     symbolTable.java
   Compile:  javac symbolTable.java
   Use:      creates a table that contains symbolic refrences and their binary equivalent
*/

public class symbolTable{
    public static String[] symbTable;
    public static String[] indexTable;
    public static int index = 0;
    public static int varIndex = 16;

    public symbolTable(int length){
        symbTable = new String[length + 23];    // 23 is to account for hardcoded values 
        indexTable = new String[length + 23];   // 16 for reg. 7 for SP, LCL etc.
        for(int i = 0; i < 16; i++){
            symbTable[i] = "R" + i;
            indexTable[i] = Integer.toBinaryString(i); 
            index++;
        }
        setUpPredefined();
    }

    public static void setUpPredefined(){
        symbTable[index] = "SP";
        indexTable[index] = Integer.toBinaryString(0);
        index++;
        symbTable[index] = "LCL";
        indexTable[index] = Integer.toBinaryString(1);
        index++;
        symbTable[index] = "ARG";
        indexTable[index] = Integer.toBinaryString(2);
        index++;
        symbTable[index] = "THIS";
        indexTable[index] = Integer.toBinaryString(3);
        index++;
        symbTable[index] = "THAT";
        indexTable[index] = Integer.toBinaryString(4);
        index++;
        symbTable[index] = "SCREEN";
        indexTable[index] = Integer.toBinaryString(16384);
        index++;
        symbTable[index] = "KBD";
        indexTable[index] = Integer.toBinaryString(24576);
        index++;
    }

    // will add any entry into the tables. To add a variable, specify address as -1, signifying no address
    public static void addEntry(String symbol, int address){
        symbTable[index] = symbol;
        if(address == -1){    // add variable
            indexTable[index] = Integer.toBinaryString(varIndex);
            varIndex++;
        }
        else {      // adding a label
            indexTable[index] = Integer.toBinaryString(address);
        }
        index++; 
    }

    // return location in the symbol array where the inputed symbol exists, if it does not exist returns -1
    public static int returnIndexOfArray(String symbol){
        int index = -1;
        for(int i = 0; i < symbTable.length; i++){
            if(symbol.equals(symbTable[i])){
                index = i;
            }
        }
        return index;
    }

    public static boolean isRegister(String symbol){
        boolean result = false;
        char spot0 = symbol.charAt(0);
        if(spot0 == 'R' && (symbol.length() == 2 || symbol.length() == 3)){
            char spot1 = symbol.charAt(1);
            if(spot1 == '1' && symbol.length() == 3){
                char spot2 = symbol.charAt(2);
                if((Character.isDigit(spot2)) && (Integer.parseInt(String.valueOf(spot2)) <= 5)){
                    result = true;
                }
            }
            if(Character.isDigit(spot1) && symbol.length() == 2){
                result = true;
            }
        }
        return result;
    }

    // returns the binary location of the symbol
    public static String getAddress(String symbol){
        return indexTable[returnIndexOfArray(symbol)];
    }

    // returns a boolean value if the inputed symbol is hardcoded or not
    public static boolean isPredefined(String symbol){
        boolean result = false;
        if(isRegister(symbol)){
            result = true;
        }
        else if(symbol.equals("SP")){
            result = true;
        }
        else if(symbol.equals("LCL")){
            result = true;
        }
        else if(symbol.equals("ARG")){
            result = true;
        }
        else if(symbol.equals("THIS")){
            result = true;
        }
        else if(symbol.equals("THAT")){
            result = true;
        }
        else if(symbol.equals("SCREEN")){
            result = true;
        }
        else if(symbol.equals("KBD")){
            result = true;
        }
        return result;
    }

    // returns if a symbol exists in the table or not
    public static boolean contains(String symbol){
        return returnIndexOfArray(symbol) != -1;
    }
}