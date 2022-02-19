package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.ArrayList;
import java.util.Iterator;

public class PostalClient {
    private String clientFirstName;
    private String clientSecondName;
    private String clientPatronymic;
    private String clientEmail;//валидацию на @
    private String clientTelephone;//валидацию на + и 38

    public PostalClient(String clientFirstName, String clientSecondName, String clientPatronymic,
                        String clientEmail, String clientTelephone/*, String clientPassword*/){
        this.clientFirstName = clientFirstName;
        this.clientSecondName = clientSecondName;
        this.clientPatronymic = clientPatronymic;
        this.clientEmail = clientEmail;
        this.clientTelephone = clientTelephone;
        //this.clientPassword = clientPassword;
    }

    public PostalClient(ArrayList<String> dataClient, WriteInFile generalWriteInFile){
        ConnectWithDB connectWithDB = new ConnectWithDB(dataClient, generalWriteInFile);
    }

    public PostalClient(ConnectWithDB generalConnectWithDB, ArrayList<String> dataClient, WriteInFile generalWriteInFile){
        String sql = "INSERT INTO clients (last_name, first_name, patronymic, email, telephone) "
                + "VALUES (?, ?, ?, ?, ?);";
        Iterator<String> iter =  dataClient.iterator();
        iter.next();
        int len = 5;
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
            generalWriteInFile.writeInFile("add the record in table clients: " + line);
        } catch(SQLException eSQL) {
            System.out.println("can`t add data in table clients");
            generalWriteInFile.writeInFile("can`t add data in table clients");
        }

    }

    public static void coutPostalClient(ConnectWithDB connectForRead, WriteInFile generalWriteInFile){
        String sql = "SELECT * FROM clients;";
        try{
            String tmpLink = null;
            connectForRead.prst = connectForRead.conn.prepareStatement(sql);
            ResultSet resultSet = connectForRead.prst.executeQuery();
            while(resultSet.next()){
                long id_client = resultSet.getLong("id_client");
                String telephone = resultSet.getString("telephone");
                String last_name = resultSet.getString("last_name");
                String first_name = resultSet.getString("first_name");
                String patronymic = resultSet.getString("patronymic");
                String email = resultSet.getString("email");

                tmpLink = (id_client+" "+ telephone + " "+last_name+" "+first_name+" "+patronymic+" "+email+" ");
                System.out.println(tmpLink);
                generalWriteInFile.writeInFile("output clients on screen:" + tmpLink);
            }
        } catch(SQLException eSQL){
            System.out.println("error when we read from clients all data");
            generalWriteInFile.writeInFile("error when we read from clients all data");
        }
    }

    public void printClient(){
        System.out.println(clientFirstName+" "+clientSecondName+" "+clientPatronymic+" "+clientEmail+" "+clientTelephone+"\n");
    }

    private boolean EmailValidation(String clientEmail){
        //валидация
        return true;
    }

    private boolean TelephoneValidation(String clientTelephone){
        //посчитать длину, перевести в массив, проверить на + и 38
        //валидация
        return true;
    }

    private boolean PassvordValidation(String clientPassword){
        //проверка количества и чтоб там были большие буквы и цифры
        return true;
    }

    private boolean PasswordUniqueness(String clientPassword){
        //идти на поклон к базе и спрашивать есть ли такой пароль
        //наверно надо еще над валидацией логина подумать
        return true;
    }
}
