/* Program:  Hack Assembler - Code Class (Nand2Tetris Assignment #6)
   Author:   David Reese
   Date:     December 7, 2022
   File:     code.java
   Compile:  javac code.java
   Use:      provides services to translate symbolic Hack mnemocis into binary
*/

public class code{
   public static String[] destCodes = {"null", "M", "D", "DM", "A", "AM", "AD", "ADM"};
   public static String[] jumpCodes = {"null", "JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"};
   public static String[] destjumpValues = {"000", "001", "010", "011", "100", "101", "110", "111"};
   public static String[] comp0Codes = {"0", "1", "-1", "D", "A", "!D", "!A", "-D", "-A", "D+1", "A+1", "D-1", "A-1", "D+A", "D-A", "A-D", "D&A", "D|A"};
   public static String[] comp0Values = {"101010", "111111", "111010", "001100", "110000", "001101", "110001", "001111", "110011", "011111", "110111", "001110", "110010", "000010", "010011", "000111", "000000", "010101"};
   public static String[] comp1Codes = {"M", "!M", "-M", "M+1", "M-1", "D+M", "D-M", "M-D", "D&M", "D|M"};
   public static String[] comp1Values = {"110000", "110001", "110011", "110111", "110010", "000010", "010011", "000111", "000000", "010101"};
   
   public static String dest(String input){
      int i=0;
      while(!(input.equals(destCodes[i])) && i < destCodes.length){
         i++;
      }
      return destjumpValues[i];
   }
   
   public static String jump(String input){
      int i=0;
      while(!(input.equals(jumpCodes[i])) && i < jumpCodes.length){
         i++;
      }
      return destjumpValues[i];
   }
   
   public static String comp(String input){
      int i=0;
      if(!(input.contains("M") || input.equals("M"))){
         while(!(input.equals(comp0Codes[i])) && i < comp0Codes.length){
            i++;
         }
         return "0" + comp0Values[i];
      }
      else{
         while(!(input.equals(comp1Codes[i])) && i < comp1Codes.length){
            i++;
         }
         return "1" + comp1Values[i];
      }
   }
}