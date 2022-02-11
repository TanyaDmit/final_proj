package work.with.info;

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

    public PostalClient(ArrayList<String> dataClient){
        Iterator<String> iter =  dataClient.iterator();
        iter.next();
        this.clientFirstName = iter.next();
        this.clientSecondName = iter.next();
        this.clientPatronymic = iter.next();
        this.clientEmail = iter.next();
        this.clientTelephone = iter.next();
    }

    public PostalClient transformation(String str){
        System.out.println(str);
        return null;
    }

    public void printClient(){
        System.out.println(clientFirstName+" "+clientSecondName+" "+clientPatronymic+" "+clientEmail+" "+clientTelephone+"\n");
    }

    public PostalClient(){
        this.clientFirstName = null;
        this.clientSecondName = null;
        this.clientPatronymic = null;
        this.clientEmail = null;
        this.clientTelephone = null;
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
