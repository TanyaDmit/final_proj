package work.with.info;

import work.with.database.ConnectWithDB;

import java.util.ArrayList;

public class PostalOffices {
    private int officeID;//?
    private String officeDescription;

    public PostalOffices(String officeDescription){
        this.officeDescription = officeDescription;
    }

    public PostalOffices(ArrayList<String> dataOffice){
        ConnectWithDB connectWithDB = new ConnectWithDB(dataOffice);
    }
}
