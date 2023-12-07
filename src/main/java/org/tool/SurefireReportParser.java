package org.tool;

import java.io.File;
import java.util.LinkedList;

public class SurefireReportParser {


    public static void main(String[] args){

        LinkedList<String[]> testReportTable = parseTestReportTable("/home/cloud-ops/IdeaProjects/Test/demo_testfiles");

        for(String[] row : testReportTable){
            System.out.println(row[0]+" , "+row[1]+" , "+row[2]+" , "+row[3]+" , "+row[4]);
        }

    }

    public static LinkedList<String[]> parseTestReportTable(String dirPath){

        LinkedList<String[]> testReportTable = new LinkedList<String[]>();

        //fetch the list of files name in the directory and store it in array
        File[] fileList = new File(dirPath).listFiles();

        //read each entry in the list
        for (File file : fileList){

            //split the entry/filename using "-" and store in the row array
            String[] rowValues = file.getName().substring(0,file.getName().length()-4).split("\\-");

            //Store the rows in the table linked list
            testReportTable.add(rowValues);

        }

        return testReportTable;

    }

}
