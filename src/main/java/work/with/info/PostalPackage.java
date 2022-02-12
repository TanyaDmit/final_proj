package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.util.ArrayList;

public class PostalPackage {
    private int packageID;
    private int senderID;
    private int officeID;//из справочника.. наверно рандомайзер должен выбирать
    private String telephoneRecipient;
    private String recipientFirstName;
    private String recipientSecondName;
    private String recipientPatronymic;
    private String packageStatus;
    private String timeDateCreation;
    private String timeDateChange;

    public PostalPackage(ArrayList<String> dataPackage, WriteInFile generalWriteInFile){
        ConnectWithDB connectWithDB = new ConnectWithDB(dataPackage, generalWriteInFile);
    }

}
