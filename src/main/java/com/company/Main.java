package com.company;

import work.with.files.ReadFromFile;
import work.with.files.WriteInFile;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try{
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
        System.out.println("that`s ok");
        try {
            ReadFromFile file1 = new ReadFromFile("test.txt");
            file1.readFromFile();
            file1.close();
            ReadFromFile file2 = new ReadFromFile("test.txt");
            file2.readFromFile(5);
            file2.readFromFile(5);

            file2.close();
        } catch (IOException e){
            System.err.print(e);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.print(e);
        }
    }
}
