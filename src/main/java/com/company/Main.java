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
            new TimerRun(1, generalWriteInFile);
            new TimerRun(8, generalWriteInFile);
            ReadFromFile generalReadFromFile = new ReadFromFile("input_data.txt", generalWriteInFile);

            ArrayList<String> generalWorkWithString;
            Iterator<String> generalIter;
            ConnectWithDB generalConnectWithDB = new ConnectWithDB(generalWriteInFile);
            int counterForPackage = 0;
            ArrayList<PostalOffices> postalOfficesArrayList = new ArrayList<>();
            boolean postalOfficesPull = true;
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
                                break;
                            case "REGISTRPOSTALOFFICE":
                                postalOfficesArrayList.add(new PostalOffices(generalIter.next(), generalIter.next()));
//                                PostalOffices postalOffice = new PostalOffices(generalConnectWithDB, generalWorkWithString, generalWriteInFile, "str");
                                break;
                            case "REGISTRPACKAGE":
                                if(postalOfficesPull) {
                                    PostalOffices postalOffice = new PostalOffices(generalConnectWithDB, postalOfficesArrayList, generalWriteInFile);
                                    postalOfficesPull = false;
                                }
                                PostalPackage postalPackage = new PostalPackage(generalConnectWithDB, generalWorkWithString, generalWriteInFile);
                                counterForPackage++;
                                if(counterForPackage % 7 == 0) {
                                    generalConnectWithDB.setDisconnect(generalWriteInFile, true);
                                    generalWriteInFile.close();
                                    do {
                                        Thread.sleep(1000);
                                    } while (TimerRun.activeWorkFlag);
                                    generalWriteInFile = new WriteInFile("log.txt");
                                    generalConnectWithDB = new ConnectWithDB(generalWriteInFile);
                                }
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

            while(true) {
                Thread.sleep(1000);
                if(TimerRun.activeWorkFlag){
                    break;
                }
            }

            //просто вычитка в самом конце
            ConnectWithDB connectForRead = new ConnectWithDB(generalWriteInFile);
            System.out.println("Клиенты: ");
            PostalClient.coutPostalClient(connectForRead,generalWriteInFile);
            System.out.println("Офисы: ");
            PostalOffices.coutPostalOffices(connectForRead,generalWriteInFile);
            System.out.println("Посылки: ");
            PostalPackage.coutPostalPackage(connectForRead,generalWriteInFile);
            System.out.println("Сообщения: ");
            PostalNotification.coutPostalNotification(connectForRead,generalWriteInFile);
            connectForRead.setDisconnect(generalWriteInFile, true);
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
