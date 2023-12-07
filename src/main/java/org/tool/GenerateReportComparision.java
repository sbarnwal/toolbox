package org.tool;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class GenerateReportComparision {

    public static void generateReportComparision(LinkedList<String> dirList, LinkedList<String> reportName) throws IOException {

//Generate report comparision table in LinkedListHashMap with key as the test case name in string and value as status of test case from reports in string array
        LinkedHashMap<String, String[]> reportComparisionTable = generateReportComparisionTable(dirList,reportName);
//        //write to csv file
        System.out.println("is write successful ? "+writeToCSVFile(reportComparisionTable));


    }

    public static LinkedHashMap<String, String[]> generateReportComparisionTable(LinkedList<String> dirList, LinkedList<String> reportName){
        LinkedHashMap<String, String[]> reportComparisionTable = new LinkedHashMap<String,String[]>();
        String[] header = new String[dirList.size()];
        for(String dir_temp : dirList){
            header[dirList.indexOf(dir_temp)] = reportName.get(dirList.indexOf(dir_temp));
        }
        reportComparisionTable.put("Test Case Name",header); // add the header column
        int reportIndex=0;
        dirList.remove(null);
        for(String dirpath : dirList){ // traverse through the report directories
            LinkedList<String[]> parsedReport = parseTestReportTable(dirpath);

            for(String[] row : parsedReport){ // traverse through each entry in the parsed List

                if(row.length==5){
                    String testName = row[0]+"."+row[1];
                    String testStatus = row[4];
                    String[] testStatusRow = new String[dirList.size()];
                    if(reportComparisionTable.containsKey(testName)){ // Update the key value if it is already present
                        if(reportComparisionTable.get(testName)[reportIndex]==null || !reportComparisionTable.get(testName)[reportIndex].equalsIgnoreCase("FAILED")){// Each entry is a test step. So Only update the status if it is not failed since.
                            reportComparisionTable.get(testName)[reportIndex]=testStatus;
                        }
                    }else { // Add key value if it is not present

                        testStatusRow[reportIndex]=testStatus;
                        reportComparisionTable.put(testName,testStatusRow);
                    }
                }else {
                    System.out.println("Row "+row+" is being ignored since its size is less than 5");
                }

            }
            reportIndex++;

        }
        return reportComparisionTable;
    }

    public static boolean writeToCSVFile(LinkedHashMap<String,String[]> reportComparisionTable) throws IOException {
        boolean isSuccess = true;
        String csvFilePath = "./reportComparisionCsv.csv";
        File reportComparisionCsv = new File(csvFilePath);
        BufferedWriter csvFileWriter = new BufferedWriter(new FileWriter(reportComparisionCsv));
        for(String key : reportComparisionTable.keySet()){

            String[] row = reportComparisionTable.get(key);
            StringBuilder executionStatus = new StringBuilder();
            for(String value : row){
                executionStatus=executionStatus.append(",").append(value);
            }
            csvFileWriter.write(key + executionStatus +"\n");
            System.out.println(key+executionStatus);
        }
        csvFileWriter.close();
        return isSuccess;
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
