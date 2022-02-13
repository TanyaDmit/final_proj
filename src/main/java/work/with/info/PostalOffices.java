package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.ArrayList;
import java.util.Iterator;

public class PostalOffices {
    private int officeID;//?
    private String officeDescription;

    public PostalOffices(String officeDescription) {
        this.officeDescription = officeDescription;
    }

    public PostalOffices(ArrayList<String> dataOffice, WriteInFile generalWriteInFile) {
        ConnectWithDB connectWithDB = new ConnectWithDB(dataOffice, generalWriteInFile);
    }

    public PostalOffices(ConnectWithDB generalConnectWithDB, ArrayList<String> dataOffice,
                         WriteInFile generalWriteInFile) {
        String sql = "INSERT INTO postal_offices (num_office, description_office) "
                + "VALUES (?::int, ?);";
        Iterator<String> iter = dataOffice.iterator();
        iter.next();
        int len = 2;
        String line = " ";
        String tmp = null;
        try {
            generalConnectWithDB.prst = generalConnectWithDB.conn.prepareStatement(sql);//создание connect
            for (int counter = 1; counter <= len; counter++) {
                tmp = iter.next();
                generalConnectWithDB.prst.setString(counter, tmp);
                line = line + tmp + " ";
            }
            generalConnectWithDB.prst.executeUpdate();
            generalWriteInFile.writeInFile("add the record in table postal_offices: " + line);
        } catch (SQLNonTransientException eNTSQL) {
            System.out.println("печаль с данными");
        } catch (SQLException eSQL) {
            System.out.println("печаль в общем");
        }
    }
}