package work.with.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFromFile {
    private BufferedReader fileReader;

    public ReadFromFile(String fileName,WriteInFile generalWriteInFile) throws IOException {
        fileReader = new BufferedReader(new FileReader(fileName));
        generalWriteInFile.writeInFile("file '" + fileName + "' is opened");
    }

    public void readFromFile(WriteInFile generalWriteInFile){
        try{
            String line;
            while((line = fileReader.readLine()) != null) {
                System.out.println(line);
            }
        }catch(IOException e){
            System.err.print(e);
        }
        generalWriteInFile.writeInFile("all file was read");
    }

    public ArrayList readFromFile(String str){
        String line = null;
        ArrayList<String> workWithString = new ArrayList<>();
        try{
            if(fileReader.ready()){
                //получаем новые слова
                line = fileReader.readLine();
                String[] newWords = line.split(";");
                for(int j = 0; j < newWords.length; j++){
                    workWithString.add(newWords[j]);
                }
            }
        }catch(NullPointerException | IOException e){
            System.err.print(e);
        }
        return workWithString;
    }

    public void close() throws Exception{
        if(fileReader != null){
            fileReader.close();
        }
    }
}
