package com.company;

import work.with.database.ConnectWithDB;
import work.with.files.ReadFromFile;
import work.with.files.WriteInFile;
import work.with.info.PostalClient;
import work.with.info.PostalOffices;
import work.with.info.PostalPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        try {
            WriteInFile generalWriteInFile = new WriteInFile("log.txt");
            ReadFromFile generalReadFromFile = new ReadFromFile("test.txt", generalWriteInFile);
            ArrayList<String> generalWorkWithString;
            Iterator<String> generalIter;
            ConnectWithDB generalConnectWithDB = new ConnectWithDB(generalWriteInFile);
            //цикл начало
            while(true){
                generalWorkWithString = readFile(generalReadFromFile);
                generalIter =  generalWorkWithString.iterator();
                if(!generalIter.hasNext()){
                    break;
                } else{
                    if(generalIter.hasNext()){
                        String tmp = generalIter.next();
                        System.out.println(tmp);
                        switch (tmp) {
                            case "REGISTRPEOPLE":
                                PostalClient postalClient = new PostalClient(generalConnectWithDB, generalWorkWithString, generalWriteInFile);
                                System.out.println("1");
                                break;
                            case "REGISTRPOSTALOFFICE":
                                PostalOffices postalOffice = new PostalOffices(generalConnectWithDB, generalWorkWithString, generalWriteInFile);
                                System.out.println("2");
                                break;
                            case "REGISTRPACKAGE":
                                PostalPackage postalPackage = new PostalPackage(generalConnectWithDB, generalWorkWithString, generalWriteInFile);
                                System.out.println("3");
                                break;
                            default:
                                System.out.println("i don`t know");
                                break;
                        }
                    } else {
                        System.out.println("game over");
                    }
                }
            }
            //do {
//                if(generalIter.hasNext()){
//                    String tmp = generalIter.next();
//                    System.out.println(tmp);
//                    switch (tmp) {
//                        case "REGISTRPEOPLE":
//                            PostalClient postalClient = new PostalClient(generalWorkWithString, generalWriteInFile);
//                            System.out.println("1");
//                            break;
//                        case "REGISTRPOSTALOFFICE":
//                            PostalOffices postalOffice = new PostalOffices(generalWorkWithString, generalWriteInFile);
//                            System.out.println("2");
//                            break;
//                        case "REGISTRPACKAGE":
//                            PostalPackage postalPackage = new PostalPackage(generalWorkWithString, generalWriteInFile);
//                            System.out.println("3");
//                            break;
//                        default:
//                            System.out.println("i don`t know");
//                            break;
//                    }
//                } else {
//                    System.out.println("game over");
//                }
//                generalWorkWithString = readFile(generalReadFromFile);
//                generalIter =  generalWorkWithString.iterator();
           // } while(generalIter.hasNext());
            //цикл конец
            generalConnectWithDB.setDisconnect(generalWriteInFile);
            generalWriteInFile.close();
        } catch (IOException e){
            System.err.print(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
        }
    }


    public static ArrayList readFile(ReadFromFile generalReadFromFile){
        return generalReadFromFile.readFromFile("word");
    }
}
