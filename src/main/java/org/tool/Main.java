package org.tool;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args){

        if(args[0].equalsIgnoreCase(Constants.GENERATEHMAC)){
            String messageDigest = null;
            try {
                messageDigest = new HmacUtils().generateHmac256(args[1], Constants.KEY);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            System.out.println("HMAC encrypted value is :"+messageDigest);
        }else if (args[0].equalsIgnoreCase(Constants.GENERATERC)){
            try {
                LinkedList<String> dirList = new LinkedList<String>();
                LinkedList<String> reportName = new LinkedList<>();

                for(int argIndex = 1; argIndex<args.length;argIndex++){
                    String arg = args[argIndex];
                    dirList.add(arg.split("=")[1].trim()); //store directory path from argument list
                    reportName.add(arg.split("=")[0].trim()); //store report name from argument list
                }
                new GenerateReportComparision().generateReportComparision(dirList,reportName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("ERROR : Invalid arguments");
        }

    }

}
