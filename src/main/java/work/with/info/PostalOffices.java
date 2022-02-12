package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.util.ArrayList;

public class PostalOffices {
    private int officeID;//?
    private String officeDescription;

    public PostalOffices(String officeDescription){
        this.officeDescription = officeDescription;
    }

    public PostalOffices(ArrayList<String> dataOffice, WriteInFile generalWriteInFile){
        ConnectWithDB connectWithDB = new ConnectWithDB(dataOffice, generalWriteInFile);
    }
}
