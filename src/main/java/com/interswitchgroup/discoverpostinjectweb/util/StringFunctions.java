package com.interswitchgroup.discoverpostinjectweb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class StringFunctions {

    private static Logger logger = LoggerFactory.getLogger(StringFunctions.class);

    public StringFunctions() {}

    static SecureRandom number = null;
    static {
        try {
            number = SecureRandom.getInstance("SHA1PRNG");
        }catch(NoSuchAlgorithmException e) {
            logger.info("generateRandom Exception: " + e.getMessage());
        }
    }

    public static Integer generateRandom(int length) throws Exception {
        if(number != null) {
          return number.nextInt(length);
        }
        throw new Exception("Number generator is null or uninitialized");
    }

    public String removeCharAt(String stringValue, int i) {
        StringBuffer buf = new StringBuffer(stringValue.length() -1);
        buf.append(stringValue.substring(0,i)).append(stringValue.substring(i + 1));
        return buf.toString();
    }

    public  String removeChar( String stringValue,char c) {
        StringBuffer buf = new StringBuffer(stringValue.length());
        buf.setLength(stringValue.length());
        int current = 0;
        for (int i = 0; i < stringValue.length(); i++) {
            char cur = stringValue.charAt(i);
            if (cur != c)
                buf.setCharAt(current++, cur);
        }
        return buf.toString();
    }

    public  String replaceCharAt( String stringValue,int i, char c) {
        StringBuffer buf = new StringBuffer(stringValue);
        buf.setCharAt(i, c);
        return buf.toString();
    }

    public  String deleteAllNonDigit(String stringValue) {
        String temp = stringValue.replaceAll("\\D", "");
        return temp;
    }

    public  String repalceAllChar(String stringValue,String f, String r) {
        String temp = stringValue.replace(f, r);
        return temp;
    }
}
