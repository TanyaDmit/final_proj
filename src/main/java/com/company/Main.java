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
        try {
            WriteInFile generalWriteInFile = new WriteInFile("log.txt");
            ReadFromFile generalReadFromFile = new ReadFromFile("test.txt", generalWriteInFile);
            new TimerRun(1, generalWriteInFile);
            System.out.println("Task scheduled.");
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

            //отправки посылок
//            ConnectWithDB connectForSend = new ConnectWithDB(generalWriteInFile);
//            ArrayList<PostalPackage> stPackage;
//            boolean acceptFlag;
//            while(true){
//                stPackage = PostalPackage.getSendPackage(connectForSend,generalWriteInFile);
//                acceptFlag = PostalPackage.changeStatus(connectForSend, stPackage, generalWriteInFile);
//                if(!acceptFlag){
//                    break;
//                }
//            }
//            connectForSend.setDisconnect(generalWriteInFile, true);
//            Timer timer = new Timer();
//            timer.schedule(new TimerRun(connectForSend, stPackage, generalWriteInFile), 0,1000);
//            boolean acceptFlag;
//            while(true){
//                stPackage = PostalPackage.getSendPackage(connectForSend,generalWriteInFile);
//                acceptFlag = PostalPackage.changeStatus(connectForSend, stPackage, generalWriteInFile);
//                if(!acceptFlag){
//                    break;
//                }
//            }

//            Iterator<PostalPackage> statusIter = stPackage.iterator();
//            while(statusIter.hasNext()){
//                PostalPackage.coutPostalPackage(statusIter.next());
//            }
//            connectForSend.setDisconnect(generalWriteInFile, false);

//            //просто вычитка в самом конце
//            ConnectWithDB connectForRead = new ConnectWithDB(generalWriteInFile);
//            PostalClient.coutPostalClient(connectForRead,generalWriteInFile);
//            PostalOffices.coutPostalOffices(connectForRead,generalWriteInFile);
//            PostalPackage.coutPostalPackage(connectForRead,generalWriteInFile);
//            connectForRead.setDisconnect(generalWriteInFile, false);
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
