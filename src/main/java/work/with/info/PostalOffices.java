package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.ArrayList;
import java.util.Iterator;

public class PostalOffices {
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
        } catch (SQLException eSQL) {
            System.out.println("can`t add data in table postal_offices");
            generalWriteInFile.writeInFile("can`t add data in table postal_offices");
        }
    }

    public static void coutPostalOffices(ConnectWithDB connectForRead, WriteInFile generalWriteInFile){
        String sql = "SELECT * FROM postal_offices;";
        try{
            String tmpLink = null;
            connectForRead.prst = connectForRead.conn.prepareStatement(sql);
            ResultSet resultSet = connectForRead.prst.executeQuery();
            while(resultSet.next()){
                long id_office = resultSet.getLong("id_office");
                long num_office = resultSet.getLong("num_office");
                String description_office = resultSet.getString("description_office");

                tmpLink = (id_office+" "+ num_office+ " "+ description_office);
                System.out.println(tmpLink);
                generalWriteInFile.writeInFile("output postal_offices on screen:" + tmpLink);
            }
        } catch(SQLException eSQL){
            System.out.println("error when we read from postal_offices all data");
            generalWriteInFile.writeInFile("error when we read from postal_offices all data");
        }
    }
}