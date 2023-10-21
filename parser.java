/* Program:  Hack Assembler - Parser class (Nand2Tetris Assignment #6)
   Author:   David Reese
   Date:     December 7, 2022
   File:     parser.java
   Compile:  javac parser.java
   Use:      creates a parser object that can traverse through a file and return parsed instructions and 
             determine the type of intstruction
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class parser{
   public static int A_INSTRUCTION = 0;
   public static int C_INSTRUCTION = 1;
   public static int L_INSTRUCTION = 2;
   public static String currentInstr = "";
   public static Scanner input;

public parser(String fileName) throws FileNotFoundException{
   File inputFile = new File(fileName);
   input = new Scanner(inputFile);
}

public static String getCurrentInstr(){
    return currentInstr;
}

public static void advance(){
      boolean containsWS = false;     // contains whitespace
      boolean containsCM = false;     // contains comment
      do{
         containsWS = false;          // reset values
         containsCM = false;
         currentInstr = input.nextLine();
         currentInstr = currentInstr.trim();
         if(currentInstr.length()>0){
            if(currentInstr.charAt(0) == '/'){     // signifying that a comment is not after any code 
               containsCM = true;
            }
         }
         else {      // signifies the existence of whitespace
            containsWS = true;
         }
      } while((containsWS || containsCM) && hasMoreLines());
      if(currentInstr.indexOf('/') > 0){        // gets rid of the comment at the end of the line if there is one
         currentInstr = currentInstr.substring(0, currentInstr.indexOf('/'));
      }
   }
   
   public static boolean hasMoreLines(){
      return input.hasNextLine();
   }
   
   public static int instructionType(String input){
      if(input.charAt(0) == '@') {   //A instruction
         return A_INSTRUCTION;
      }
      else if(input.charAt(0) == '('){ //L instruction
         return L_INSTRUCTION;
      }
      else{
         return C_INSTRUCTION;
      }
   }
   
   public static String symbol(String input){
      input = input.trim();
      if(input.charAt(0) == '@'){
         return input.substring(1, input.length());
      }
      else{ //input.charAt(0) == '('
         return input.substring(1, input.indexOf(')'));

      }
   }

   public static String comp(String input){
      input = input.trim();
      if(input.contains(";")){
         return input.substring(input.indexOf('=')+1, input.indexOf(';'));
      }
      else{
         return input.substring(input.indexOf('=')+1);
      }
   }
   
   public static String dest(String input){
      if(input.charAt(0) == 'A'){
         if(input.substring(0,2) == "ADM" || input.substring(0,2) == "AMD") {
            return "ADM";
         }
         else if(input.charAt(1) == 'M' || input.charAt(1) == 'D'){
            return input.substring(0,2);
         }
         else{
            return "A";
         }
      }
      else if(input.charAt(0) == 'D'){
         if(input.substring(0,2) == "DAM" || input.substring(0,2) == "DMA"){
            return "ADM";
         }
         else if(input.charAt(1) == 'M'){
            return "DM";
         }
         else if(input.charAt(1) == 'A'){
            return "AD";
         }
         else if(input.charAt(1) == ';'){
            return "null";
         }
         else{
            return "D";
         }
      }
      else if(input.charAt(0) == 'M'){
         if (input.substring(0,2) == "MAD" || input.substring(0,2) == "MDA"){
            return "ADM";
         }
         else if(input.charAt(1) == 'A'){
            return "AM";
         }
         else if(input.charAt(1) == 'D'){
            return "DM";
         }
         else{
            return "M";
         }
      }
      else {
         return "null";
      }
   } 
   
   public static String jump(String input){
      if(input.contains("J")){
         return input.substring(input.indexOf('J'), input.indexOf('J')+3);
      }
      else {
         return "null";
      }
   }
}