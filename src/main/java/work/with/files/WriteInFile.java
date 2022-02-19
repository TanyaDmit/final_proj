package work.with.files;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class WriteInFile {
    private PrintWriter printWriter;

    public WriteInFile(String fileName){
        try{
            printWriter = new PrintWriter(new FileWriter(fileName, true));
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("try to open the file for write");
        }
    }

    public void writeInFile(String text){
        LocalDateTime localDateTime = LocalDateTime.now();
        printWriter.print(localDateTime + " " + text + "\n");

    }

    public void close() throws Exception{
        if(printWriter != null){
            printWriter.close();
        }
    }
}
