package work.with.files;

import java.io.FileWriter;
import java.io.IOException;

public class WriteInFile {
    private FileWriter fileWriter;

    public WriteInFile(String fileName) throws IOException{
        fileWriter = new FileWriter(fileName, true);
    }

    public void writeInFile(String text){
        try{
            fileWriter.write(text);
        }catch(IOException e){
            System.err.print(e);
        }
    }

    public void close() throws Exception{
        if(fileWriter != null){
            fileWriter.close();
        }
    }
}
