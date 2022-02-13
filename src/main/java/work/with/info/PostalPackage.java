package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.ArrayList;
import java.util.Iterator;

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

    public PostalPackage(ConnectWithDB generalConnectWithDB, ArrayList<String> dataPackage, WriteInFile generalWriteInFile){
        String sql = "INSERT INTO packages (telephone_sender, num_office_recipient, telephone, last_name, first_name, " +
                "patronymic, date_of_create, status)"
                + "VALUES (?, ?::int, ?, ?, ?, ?, ?::timestamp,'new_package');";
        Iterator<String> iter =  dataPackage.iterator();
        iter.next();
        int len = 7;
        String line = " ";
        String tmp = null;
        try{
            generalConnectWithDB.prst = generalConnectWithDB.conn.prepareStatement(sql);//создание connect
            for(int counter = 1; counter <= len; counter++){
                tmp = iter.next();
                generalConnectWithDB.prst.setString(counter, tmp);
                line = line + tmp + " ";
            }
            generalConnectWithDB.prst.executeUpdate();
            generalWriteInFile.writeInFile("add the record in table packages: " + line);
        }catch(SQLNonTransientException eNTSQL){
            System.out.println("печаль с данными");
            generalWriteInFile.writeInFile("game over");
        }catch(SQLException eSQL) {
            System.out.println("печаль в общем");
            generalWriteInFile.writeInFile("game over 0.2");
        }

    }
}
