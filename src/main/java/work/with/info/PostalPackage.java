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
            generalConnectWithDB.setReConnect(generalWriteInFile);
        }

    }

    public static void coutPostalPackage(ConnectWithDB generalConnectWithDB, WriteInFile generalWriteInFile){
        try{
            String sql = "SELECT * FROM packages;";
            generalConnectWithDB.prst = generalConnectWithDB.conn.prepareStatement(sql);
            ResultSet resultSet = generalConnectWithDB.prst.executeQuery();
            while(resultSet.next()){
                long id_package = resultSet.getLong("id_package");
                String telephone_sender = resultSet.getNString("telephone_sender");
                long num_office_recipient = resultSet.getLong("num_office_recipient");
                String telephone = resultSet.getNString("telephone");
                String last_name = resultSet.getNString("last_name");
                String first_name = resultSet.getNString("first_name");
                String patronymic = resultSet.getNString("patronymic");
                String status = resultSet.getNString("status");
                String date_of_create = resultSet.getNString("date_of_create");//?
                String date_change_status = resultSet.getNString("date_change_status");//?

                System.out.println(id_package+" "+ telephone_sender+ " "+ num_office_recipient + " "+
                        telephone + " "+last_name+" "+first_name+" "+patronymic+" "+status+" "+
                        date_of_create+" "+date_change_status+" ");
                generalWriteInFile.writeInFile("yes");
            }
        } catch(SQLException eSQL){
            System.out.println("печаль в общем при вычитке");
            generalWriteInFile.writeInFile("game over 0.4");
        }
    }
}
