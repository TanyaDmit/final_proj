package com.company;

import work.with.database.ConnectWithDB;
import work.with.files.ReadFromFile;
import work.with.files.WriteInFile;
import work.with.info.PostalClient;
import work.with.info.PostalNotification;
import work.with.info.PostalOffices;
import work.with.info.PostalPackage;
import work.with.thread.TimerRun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        try {
            WriteInFile generalWriteInFile = new WriteInFile("log.txt");
            ReadFromFile generalReadFromFile = new ReadFromFile("test.txt", generalWriteInFile);
            new TimerRun(1, generalWriteInFile);
            new TimerRun(8, generalWriteInFile);
//            System.out.println("Task scheduled.");
            //запись данных в базу
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
            //цикл конец
            generalConnectWithDB.setDisconnect(generalWriteInFile, true);

//            //просто вычитка в самом конце
//            ConnectWithDB connectForRead = new ConnectWithDB(generalWriteInFile);
//            PostalClient.coutPostalClient(connectForRead,generalWriteInFile);
//            PostalOffices.coutPostalOffices(connectForRead,generalWriteInFile);
//            PostalPackage.coutPostalPackage(connectForRead,generalWriteInFile);
//            PostalNotification.coutPostalNotification(connectForRead,generalWriteInFile);
//            connectForRead.setDisconnect(generalWriteInFile, true);
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
