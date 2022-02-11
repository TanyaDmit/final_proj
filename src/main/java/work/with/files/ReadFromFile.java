package work.with.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFile {
    private BufferedReader fileReader;

    public ReadFromFile(String fileName) throws IOException {
        fileReader = new BufferedReader(new FileReader(fileName));
    }

    public void readFromFile(){
        try{
            String line;
            while((line = fileReader.readLine()) != null) {
                System.out.println(line);
            }
        }catch(IOException e){
            System.err.print(e);
        }
    }

    public String readFromFile(int i){
        String line = null;
        try{
            //String line;
            if(fileReader.ready()){
                line = fileReader.readLine();
                System.out.println(line);
            } else {
                line = null;
            }
        }catch(IOException e){
            System.err.print(e);
        }
        return line;
    }

    public void close() throws Exception{
        if(fileReader != null){
            fileReader.close();
        }
    }
}
