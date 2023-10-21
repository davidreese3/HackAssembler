/* Program:  Hack Assembler
   Author:   David Reese
   Date:     December 7, 2022
   File:     HackAssembler.java
   Compile:  javac HackAssembler.java
   Run:      java HackAssembler FILE_NAME
   Use:      assembler that translates .asm files in to .hack files
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class HackAssembler{
   public static int A_INSTRUCTION = 0;
   public static int C_INSTRUCTION = 1;
   public static int L_INSTRUCTION = 2;
   public static symbolTable symbTab;  

   public static void main(String[] args) throws FileNotFoundException, IOException{      
      String inputFile = args[0];
      symbTab = new symbolTable(countForSymbTable(inputFile));
      pass1(inputFile, symbTab);
      pass2(inputFile, symbTab);
   } 
   
   public static void pass1(String fileName, symbolTable symbTab) throws FileNotFoundException, IOException{
      parser pars = new parser(fileName); 
      String currentInstr;
      int count = 0;
      while(pars.hasMoreLines()){
         pars.advance();
         currentInstr = parser.getCurrentInstr();
         if(currentInstr.charAt(0)=='(' && !(Character.isDigit(currentInstr.charAt(1)))){
            symbTab.addEntry(pars.symbol(currentInstr),count);
         }
         else{
            count++;
         }
      }
   }

   // will count up all the symbols (not including predefined symbols) to have a rough length for the symbol table initialization
   public static int countForSymbTable(String fileName) throws FileNotFoundException{
      parser pars = new parser(fileName); 
      String currentInstr; 
      int count = 0;
      while(pars.hasMoreLines()){
         pars.advance();
         currentInstr = parser.getCurrentInstr();
         if((currentInstr.charAt(0) == '@' && !(Character.isDigit(currentInstr.charAt(1)))) || currentInstr.charAt(0) == '('){
            if(!(symbTab.isPredefined(pars.symbol(currentInstr)))){   // this could cause some issues later
               count++;
            } 
         }
      }
      return count;
   }
   
   public static void pass2(String fileName, symbolTable symbTab) throws FileNotFoundException, IOException{
      parser pars = new parser(fileName);
      PrintWriter writer = new PrintWriter(fileName.substring(0,fileName.indexOf('.')) + ".hack"); 
      int instrType;
      String currentInstr;
      while(pars.hasMoreLines()){
         pars.advance();
         currentInstr = pars.getCurrentInstr();
         instrType = pars.instructionType(currentInstr);
         if (instrType == A_INSTRUCTION){
            writer.println(AInstr(pars.symbol(currentInstr), pars));
         } 
         else if(instrType == C_INSTRUCTION){
            writer.println(CInstr(currentInstr, pars));
         }
      }
      writer.close();
   }

   public static String AInstr(String currentSymb, parser pars){
      String binValue;
      if(Character.isDigit(currentSymb.charAt(0))){
         int intValue = Integer.parseInt(currentSymb);
         binValue = Integer.toBinaryString(intValue);
      }    
      else {
         if(!(symbTab.isPredefined(currentSymb)) && (!(symbTab.contains(currentSymb)))){
            symbTab.addEntry(currentSymb, -1);
         }
         binValue = symbTab.getAddress(currentSymb);
      }
      int length = binValue.length();
      return padZeros(length,binValue);
   }

   public static String CInstr(String currentInstr, parser pars){
      String compBinValue = code.comp(pars.comp(currentInstr));
      String destBinValue = code.dest(pars.dest(currentInstr));
      String jumpBinValue = code.jump(pars.jump(currentInstr));
      return "111" + compBinValue + destBinValue + jumpBinValue;
   }
   
   public static String padZeros(int length, String input){
      for(int i = length; i < 16; i++){
         input = "0" + input;
      }
      return input;
   }
}