package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

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
    //?private int clientID;

    //пока только идеальный вариант, без валидации
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
        } catch(SQLNonTransientException eNTSQL){
            System.out.println("печаль с данными");
        }catch(SQLException eSQL) {
            System.out.println("печаль в общем");
        }

    }

    public PostalClient transformation(String str){
        System.out.println(str);
        return null;
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
