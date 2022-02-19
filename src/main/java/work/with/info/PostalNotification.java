package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.util.ArrayList;

public class PostalNotification {
    private long packageID;
    private long messageID;
    private String notificationText;
    private String notificationStatus;
    private static WriteInFile messageWriteInFile = new WriteInFile("messageResult.txt");

    public PostalNotification(long messageID, long packageID, String notificationText,String notificationStatus){
        this.messageID = messageID;
        this.packageID = packageID;
        this.notificationText = notificationText;
        this.notificationStatus = notificationStatus;
    }

    public PostalNotification(ConnectWithDB generalConnectWithDB, long num_pac, String st_pac, WriteInFile generalWriteInFile){
        String sql = "INSERT INTO messages (num_package, text_message, status) "
                + "VALUES (?, ?, ?);";
        String line = " new message: ";
        try{
            generalConnectWithDB.prst = generalConnectWithDB.conn.prepareStatement(sql);//создание connect
            generalConnectWithDB.prst.setLong(1, num_pac);
            generalConnectWithDB.prst.setString(2, st_pac);
            generalConnectWithDB.prst.setString(3, "new_message");
            generalConnectWithDB.prst.executeUpdate();
            generalWriteInFile.writeInFile("add the record in table messages: " + line + num_pac + " " +
                    st_pac + " status: new_message");
        } catch(SQLException eSQL) {
            System.out.println("error when i start add message in table messages");
            generalWriteInFile.writeInFile("error when i start add message in table messages");
        }

    }

    public static ArrayList getSendNotifications(ConnectWithDB connectForSend, WriteInFile generalWriteInFile){
        ArrayList<PostalNotification> stMessage= new ArrayList<>();
        String sql = "SELECT * FROM messages where status = 'new_message';";
        try{
            String tmpLink = null;
            connectForSend.prst = connectForSend.conn.prepareStatement(sql);
            ResultSet resultSet = connectForSend.prst.executeQuery();
            while(resultSet.next()){
                long idMessage = resultSet.getLong("messages_id");
                long idPackage = resultSet.getLong("num_package");
                String textMessage = resultSet.getString("text_message");
                String statusMes = resultSet.getString("status");
                stMessage.add(new PostalNotification(idMessage, idPackage, textMessage, statusMes));
                tmpLink = ("messid: " + idMessage+" packid: "+ idPackage+" " +textMessage+" " + statusMes);
                generalWriteInFile.writeInFile("read message to send and change status:" + tmpLink);
            }
        } catch(SQLException eSQL){
            System.out.println("error when we read from messages");
            generalWriteInFile.writeInFile("error when we read from messages");
        }
        return stMessage;
    }

    public static boolean changeStatus(ConnectWithDB connectForSend, ArrayList<PostalNotification> stMessage, WriteInFile generalWriteInFile){
        boolean flag = false;
        for(int i = 0; i < stMessage.size(); i++){
            flag = true;
            PostalNotification tmp = new PostalNotification(stMessage.get(i).messageID,
                    stMessage.get(i).packageID, stMessage.get(i).notificationText, "sent_message");
            stMessage.set(i, tmp);
            messageWriteInFile.writeInFile("notification: package with id " +
                    stMessage.get(i).packageID + " is " + stMessage.get(i).notificationText);
        }
        flag = setChangeStatus(connectForSend, stMessage, generalWriteInFile);
        return flag;
    }

    private static boolean setChangeStatus(ConnectWithDB connectForSend, ArrayList<PostalNotification> stMessage, WriteInFile generalWriteInFile){
        String sql = "update messages " +
                "set status = ?" +
                "where messages_id = ?";
        String line = " ";
        boolean flag = false;
        try{
            connectForSend.prst = connectForSend.conn.prepareStatement(sql);
            for(int counter = 0; counter < stMessage.size(); counter++){
                connectForSend.prst.setLong(2, stMessage.get(counter).messageID);
                connectForSend.prst.setString(1, stMessage.get(counter).notificationStatus);
                line = stMessage.get(counter).messageID + " " + stMessage.get(counter).notificationStatus;
                connectForSend.prst.addBatch();
                flag = true;
                generalWriteInFile.writeInFile("change status in table messages: " + line);
            }
            connectForSend.prst.executeBatch();
        }catch(SQLException eSQL) {
            System.out.println("can`t sent changed status in table messages");
            generalWriteInFile.writeInFile("can`t sent changed status in table messages");
            flag = false;
        }
        if(!flag){
            try {
                messageWriteInFile.close();
            } catch(Exception ex){
                System.out.println("ошибка при закрытии файла для сообщений");
            }
        }
        return flag;
    }

    public static void coutPostalNotification(ConnectWithDB connectForRead, WriteInFile generalWriteInFile){
        String sql = "SELECT * FROM messages;";
        try{
            String tmpLink = null;
            connectForRead.prst = connectForRead.conn.prepareStatement(sql);
            ResultSet resultSet = connectForRead.prst.executeQuery();
            while(resultSet.next()){
                long messages_id = resultSet.getLong("messages_id");
                long num_package = resultSet.getLong("num_package");
                String text_message = resultSet.getString("text_message");
                String status = resultSet.getString("status");

                tmpLink = (messages_id+" " + num_package + " "+
                        text_message + " "+status);
                System.out.println(tmpLink);
                generalWriteInFile.writeInFile("output messages on screen:" + tmpLink);
            }
        } catch(SQLException eSQL){
            System.out.println("error when we read from messages all data");
            generalWriteInFile.writeInFile("error when we read from messages all data");
        }
    }
}
