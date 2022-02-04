package work.with.info;

public class PostalClient {
    private String clientFirstName;
    private String clientSecondName;
    private String clientPatronymic;
    private String clientEmail;//валидацию на @
    private String clientTelephone;//валидацию на + и 38
    private String clientPassword;
    private int clientID;//?

    //пока только идеальный вариант, без валидации
    public PostalClient(String clientFirstName, String clientSecondName, String clientPatronymic,
                        String clientEmail, String clientTelephone, String clientPassword){
        this.clientFirstName = clientFirstName;
        this.clientSecondName = clientSecondName;
        this.clientPatronymic = clientPatronymic;
        this.clientEmail = clientEmail;
        this.clientTelephone = clientTelephone;
        this.clientPassword = clientPassword;
    }

    public PostalClient(){
        System.out.println("Введите данные!");
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
