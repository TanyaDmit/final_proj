package work.with.files;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class WriteInFile {
    private PrintWriter printWriter;

    public WriteInFile(String fileName) throws IOException{
        printWriter = new PrintWriter(new FileWriter(fileName, true));
    }

    public void writeInFile(String text){
//      System.out.println("in writeinfile");
        LocalDateTime localDateTime = LocalDateTime.now();
//      System.out.println("in writeinfile");
        printWriter.print(localDateTime + " " + text + "\n");

    }

    public void close() throws Exception{
        if(printWriter != null){
            printWriter.close();
        }
    }
}
