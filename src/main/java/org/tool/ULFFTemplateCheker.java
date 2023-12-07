package org.tool;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import org.json.JSONObject;
import org.tool.utils.ArrayUtils;

public class ULFFTemplateCheker {

    private String[] template;
    LinkedList<JSONObject> ULFFLogLines;
    LinkedHashMap<String, String[]> ULFFTemplateomparisionTable;
    private String ULFFFilePath;

    //initiallize ULFFTemplateCheker with ulff log file path and template(string with comma seperated values)
    public ULFFTemplateCheker(String filePath, String template){
        setTemplate(template);
        setULFFFilePath(filePath);
    }

    //Set template of the ULFF logs which will be used for comparision
    private void setTemplate(String template){
        this.template = template.split("\\,");
    }
    private void setULFFFilePath(String filePath){
        this.ULFFFilePath = filePath;
    }

    //Fetch the log lines in ULFF logs and store them in linkedlist
    private LinkedList<JSONObject> getLinesFromULFFLog() throws IOException {

        LinkedList<JSONObject> ulffLogLinesInJSON = new LinkedList<>();

        BufferedReader ulffLogFileReader = new BufferedReader(new FileReader(new File(ULFFFilePath)));

        String ulffLogLine;
        //Fetch Line from ulff log file run for each line
        while((ulffLogLine = ulffLogFileReader.readLine())!=null){

            //convert the lines into json object
            JSONObject ulffJOSNObject = new JSONObject(ulffLogLine);

            //Store the json object into linkedlist
            ulffLogLinesInJSON.add(ulffJOSNObject);

        }
        return ulffLogLinesInJSON;
    }




    //Get the final result after comparision of each log line with the template in table
    public LinkedHashMap<String, String[]> compareULFFLogWithTemplate() throws IOException {

        LinkedHashMap<String, String[]> results = new LinkedHashMap<>();

        this.ULFFLogLines = getLinesFromULFFLog();

        //traverse through json object list
        Iterator<JSONObject> ULFFLogLinesIterator = ULFFLogLines.iterator();
        while(ULFFLogLinesIterator.hasNext()){
            JSONObject tempObject = ULFFLogLinesIterator.next();
            String[] fieldPresentStatus = new String[template.length];
            //compare each json object with template for fields present
            for(int fieldIndex =0; fieldIndex<template.length;fieldIndex++){
                if(tempObject.opt(template[fieldIndex])!=null){
                    fieldPresentStatus[fieldIndex]="Preset";
                }else {
                    fieldPresentStatus[fieldIndex]="Not Present";
                }
            }
            //store the result in the HashMap
            System.out.println(tempObject.toString() + ArrayUtils.convertArrayToString(fieldPresentStatus));
            results.put(tempObject.toString(),fieldPresentStatus);

        }

        this.ULFFTemplateomparisionTable=results;
        return results;
    }

    public boolean writeToCsv(String path) throws IOException {

        File resultsCSVFile = new File(path);
        BufferedWriter csvFileWritter = new BufferedWriter(new FileWriter(resultsCSVFile));
        Iterator<String> ULFFTemplateomparisionTableKeySetIterator = ULFFTemplateomparisionTable.keySet().iterator();
        while (ULFFTemplateomparisionTableKeySetIterator.hasNext()){
            String key = ULFFTemplateomparisionTableKeySetIterator.next();
            csvFileWritter.write( key +","+ ULFFTemplateomparisionTable.get(key)+"\n" );
        }
        csvFileWritter.close();
        return resultsCSVFile.exists();
    }

    public static void main(String[] args) throws IOException {

        String template = "caller-id,charging_id,component,country-code,logpoint,service,service,service-type";
        String ulffPath = "/home/cloud-ops/Desktop/sampleulff.log";
        String outputPath = "/home/cloud-ops/Desktop/sampleulffComparision.csv";

        ULFFTemplateCheker templateCheker = new ULFFTemplateCheker(ulffPath,template);
        templateCheker.compareULFFLogWithTemplate();
        //System.out.println(templateCheker.writeToCsv(outputPath));

    }








}
