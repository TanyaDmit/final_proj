package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.*;
import java.time.LocalDateTime;
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

    public PostalPackage(WriteInFile generalWriteInFile){
        ConnectWithDB connectWithDB = new ConnectWithDB(generalWriteInFile);
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
        }catch(SQLTransientException eTSQL){
            System.out.println("печаль с данными");
            generalWriteInFile.writeInFile("game over 0.1");
        }catch(SQLRecoverableException eTSQL){
            System.out.println("печаль с данными");
            generalWriteInFile.writeInFile("game over 0.2");
        }catch(SQLException eSQL) {
            System.out.println("печаль в общем");
            generalWriteInFile.writeInFile("game over 0.3");
            generalConnectWithDB.setReConnect(generalWriteInFile, true);
        }

    }

    public static void coutPostalPackage(ConnectWithDB connectForRead, WriteInFile generalWriteInFile){
        try{
            connectForRead.stmt = connectForRead.conn.createStatement();
            String sql = "SELECT * FROM packages;";
            String tmpLink = null;
            ResultSet resultSet = connectForRead.stmt.executeQuery(sql);
            while(resultSet.next()){
                long id_package = resultSet.getLong("id_package");
                String telephone_sender = resultSet.getString("telephone_sender");
                long num_office_recipient = resultSet.getLong("num_office_recipient");
                String telephone = resultSet.getString("telephone");
                String last_name = resultSet.getString("last_name");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String status = resultSet.getString("status");
                String date_of_create = resultSet.getString("date_of_create");//?
                String date_change_status = resultSet.getString("date_change_status");//?

                tmpLink = (id_package+" "+ telephone_sender+ " "+ num_office_recipient + " "+
                        telephone + " "+last_name+" "+first_name+" "+patronymic+" "+status+" "+
                        date_of_create+" "+date_change_status+" ");
                System.out.println(tmpLink);
                generalWriteInFile.writeInFile("output packages on screen:" + tmpLink);
            }
        } catch(SQLException eSQL){
            System.out.println("печаль в общем при вычитке");
            generalWriteInFile.writeInFile("game over 0.4");
        }
    }
}
