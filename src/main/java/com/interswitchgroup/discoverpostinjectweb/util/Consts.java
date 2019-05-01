package com.interswitchgroup.discoverpostinjectweb.util;

import java.util.HashMap;

public class Consts {

    private static final HashMap<Character,Character> amountLastDigit = new HashMap<>();

    static {

        //for +ve numbers
        amountLastDigit.put('{','0');
        amountLastDigit.put('A','1');
        amountLastDigit.put('B','2');
        amountLastDigit.put('C','3');
        amountLastDigit.put('D','4');
        amountLastDigit.put('E','5');
        amountLastDigit.put('F','6');
        amountLastDigit.put('G','7');
        amountLastDigit.put('H','8');
        amountLastDigit.put('I','9');

        //for -ve numbers
        amountLastDigit.put('}','0');
        amountLastDigit.put('J','1');
        amountLastDigit.put('K','2');
        amountLastDigit.put('L','3');
        amountLastDigit.put('M','4');
        amountLastDigit.put('N','5');
        amountLastDigit.put('O','6');
        amountLastDigit.put('P','7');
        amountLastDigit.put('Q','8');
        amountLastDigit.put('R','9');
    }

    public static char getDigit(char symbol){
        try{
            return amountLastDigit.get(symbol);
        }
        catch(Exception ex){
            return symbol;
        }
    }
}
