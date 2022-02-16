package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class PostalNotification {
    private int notificationID;
    private String notificationText;
    private String notificationStatus;

    public PostalNotification(ConnectWithDB generalConnectWithDB, int num_pac, WriteInFile generalWriteInFile){
        String sql = "INSERT INTO messages (num_package, text_message, status) "
                + "VALUES (?, ?, ?);";
        String line = " new message: created a package";
        String tmp = null;
        try{
            generalConnectWithDB.prst = generalConnectWithDB.conn.prepareStatement(sql);//создание connect
            generalConnectWithDB.prst.setInt(1, num_pac);
            generalConnectWithDB.prst.setString(2, "created a package");
            generalConnectWithDB.prst.setString(3, "new_message");
            generalConnectWithDB.prst.executeUpdate();
            generalWriteInFile.writeInFile("add the record in table clients: " + line);
        } catch(SQLNonTransientException eNTSQL){
            System.out.println("печаль с данными");
        }catch(SQLException eSQL) {
            System.out.println("печаль в общем");
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalNotification that = (PostalNotification) o;
        return notificationID == that.notificationID && Objects.equals(notificationText, that.notificationText) && Objects.equals(notificationStatus, that.notificationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationID, notificationText, notificationStatus);
    }

    //    public static void coutPostalNotification(ConnectWithDB connectForRead, WriteInFile generalWriteInFile){
//        try{
//            connectForRead.stmt = connectForRead.conn.createStatement();
//            String sql = "SELECT * FROM messages;";
//            String tmpLink = null;
//            ResultSet resultSet = connectForRead.stmt.executeQuery(sql);
//            while(resultSet.next()){
//                long id_client = resultSet.getLong("id_client");
//                String telephone = resultSet.getString("telephone");
//                String last_name = resultSet.getString("last_name");
//                String first_name = resultSet.getString("first_name");
//                String patronymic = resultSet.getString("patronymic");
//                String email = resultSet.getString("email");
//
//                tmpLink = (id_client+" "+ telephone + " "+last_name+" "+first_name+" "+patronymic+" "+email+" ");
//                System.out.println(tmpLink);
//                generalWriteInFile.writeInFile("output clients on screen:" + tmpLink);
//            }
//        } catch(SQLException eSQL){
//            System.out.println("печаль в общем при вычитке");
//            generalWriteInFile.writeInFile("error when we read from clients");
//        }
//    }
}
