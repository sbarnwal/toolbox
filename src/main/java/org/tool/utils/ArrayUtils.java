package org.tool.utils;

public class ArrayUtils {

    public static String convertArrayToString(Object[] array){
       StringBuilder arrayInString = new StringBuilder();
       for(Object value : array){
           arrayInString.append(value.toString()).append(",");
       }
        arrayInString.deleteCharAt(arrayInString.length()-1);
        return arrayInString.toString();
    }

    public static void main(String[] args){
        System.out.println(convertArrayToString(new String[]{"one","two","three"}));
        System.out.println((new String[]{"one","two","three"}).toString());

    }

}
