package com.company;

import work.with.files.ReadFromFile;
import work.with.files.WriteInFile;
import work.with.info.PostalClient;
import work.with.info.PostalOffices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        /*try{
            WriteInFile file1 = new WriteInFile("test.txt");
            file1.writeInFile("\n hello");
            System.out.println("hello");
            file1.close();
        } catch (IOException e){
            System.err.print(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
        }
        System.out.println("that`s ok");*/
        readFile();
    }

    public static void readFile(){
        try {
            ReadFromFile generalReadFromFile = new ReadFromFile("test.txt");
            ArrayList<String> generalWorkWithString = generalReadFromFile.readFromFile("word");;
            Iterator<String> generalIter = generalWorkWithString.iterator();
            //цикл начало
            do {
                if(generalIter.hasNext()){
                    String tmp = generalIter.next();
                    System.out.println(tmp);
                    switch (tmp) {
                        case "REGISTRPEOPLE":
                            PostalClient postalClient = new PostalClient(generalWorkWithString);
                            System.out.println("1");
                            break;
                        case "REGISTRPOSTALOFFICE":
                            PostalOffices postalOffice = new PostalOffices(generalWorkWithString);
                            System.out.println("2");
                            break;
                        case "REGISTRPACKAGE":
                            System.out.println("3");
                            break;
                        default:
                            System.out.println("i don`t know");
                            break;
                    }
                } else {
                    System.out.println("game over");
                }
                generalWorkWithString = generalReadFromFile.readFromFile("word");
                generalIter =  generalWorkWithString.iterator();
            } while(generalIter.hasNext());
//            ArrayList<String> workWithString1 = file1.readFromFile("word");
//            Iterator<String> iterator1 =  workWithString1.iterator();
//            if(iterator1.hasNext()){
//                String tmp = iterator1.next();
//                System.out.println(tmp);
//                switch (tmp) {
//                    case "REGISTRPEOPLE":
//                        PostalClient postalClient = new PostalClient(workWithString1);
//                        //PostalClient postalClient1 = new PostalClient();
//                        //postalClient.printClient();
//                        System.out.println("1");
//                        break;
//                    case "REGISTRPOSTALOFFICE":
//                        System.out.println("2");
//                        break;
//                    case "REGISTRPACKAGE":
//                        System.out.println("3");
//                        break;
//                    default:
//                        System.out.println("i don`t know");
//                        break;
//                }
//            } else {
//                System.out.println("game over");
//            }
//
            //цикл конец

            /*while(iterator1.hasNext()){
                System.out.println(iterator1.next());
            }*/
        } catch (IOException e){
            System.err.print(e);
        }
    }
}
