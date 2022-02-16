package com.company;

import work.with.database.ConnectWithDB;
import work.with.files.ReadFromFile;
import work.with.files.WriteInFile;
import work.with.info.PostalClient;
import work.with.info.PostalOffices;
import work.with.info.PostalPackage;
import work.with.thread.TimerRun;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerRun(), 100,3000);
        try {
            WriteInFile generalWriteInFile = new WriteInFile("log.txt");
            ReadFromFile generalReadFromFile = new ReadFromFile("test.txt", generalWriteInFile);
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

            //отправки посылок

            //просто вычитка в самом конце
            generalConnectWithDB.setDisconnect(generalWriteInFile, true);
            ConnectWithDB connectForRead = new ConnectWithDB(generalWriteInFile);
            PostalPackage.coutPostalPackage(connectForRead,generalWriteInFile);
            PostalClient.coutPostalClient(connectForRead,generalWriteInFile);
            PostalOffices.coutPostalOffices(connectForRead,generalWriteInFile);
            connectForRead.setDisconnect(generalWriteInFile, false);
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
