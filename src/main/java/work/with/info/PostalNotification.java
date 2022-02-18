package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLNonTransientException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class PostalNotification {
    private long packageID;
    private long messageID;
    private String notificationText;
    private String notificationStatus;

    public PostalNotification(long messageID, long packageID, String notificationText,String notificationStatus, WriteInFile generalWriteInFile){
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
        } catch(SQLNonTransientException eNTSQL){
            System.out.println("печаль с данными");
        }catch(SQLException eSQL) {
            System.out.println("печаль в общем");
        }

    }

    public static ArrayList getCreatedMessages(ConnectWithDB connectForSend, WriteInFile generalWriteInFile){
        ArrayList<PostalNotification> statusPackage= new ArrayList<>();
        try{
            connectForSend.stmt = connectForSend.conn.createStatement();
            String sql = "SELECT * FROM packages where status = 'new_message';";
            String tmpLink = null;
            ResultSet resultSet = connectForSend.stmt.executeQuery(sql);
            while(resultSet.next()){
                long idMessage = resultSet.getLong("messages_id");
                long idPackage = resultSet.getLong("id_package");
                String statusMes = resultSet.getString("status");
                String textMessage = resultSet.getString("text_message");

                statusPackage.add(new PostalNotification(idMessage, idPackage, textMessage, statusMes, generalWriteInFile));
                //должно создаваться сообщение в статусе новое
                tmpLink = ("messid: " + idMessage+" packid: "+ idPackage+" " +textMessage+" " + statusMes);
                generalWriteInFile.writeInFile("read package to send and change status:" + tmpLink);
            }
        } catch(SQLException eSQL){
            System.out.println("печаль в общем при вычитке");
            generalWriteInFile.writeInFile("error when we read from packages");
        }
        return statusPackage;
    }

//    public static boolean changeStatus(ConnectWithDB connectForSend, ArrayList<PostalPackage> statusPackage, WriteInFile generalWriteInFile){
//        boolean flag = false;
////        Iterator<PostalPackage> statusIter = statusPackage.iterator();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime ndt = LocalDateTime.now();
//        for(int i = 0; i < statusPackage.size(); i++){
//            flag = true;
//            System.out.println("1");
//            LocalDateTime dateTime = LocalDateTime.parse(statusPackage.get(i).date_change_status,dateTimeFormatter);
//            int sec1 = ndt.getSecond();
//            int sec2 = dateTime.getSecond();
//            if((sec1-sec2) <= 5){
//                System.out.println("2");
//                int myRand = (int)(Math.random()*2);
//                if(myRand == 1){
//                    System.out.println("3");
//                    PostalPackage tmp = new PostalPackage(statusPackage.get(i).id_package,
//                            "delivered_package",statusPackage.get(i).date_change_status, generalWriteInFile);
//                    statusPackage.set(i, tmp);
//                    PostalNotification postalNotification = new PostalNotification(connectForSend,
//                            statusPackage.get(i).id_package, "delivered_package", generalWriteInFile);
//                }
//            } else{
//                PostalPackage tmp = new PostalPackage(statusPackage.get(i).id_package,
//                        "overdue_package",statusPackage.get(i).date_change_status, generalWriteInFile);
//                statusPackage.set(i, tmp);
//                PostalNotification postalNotification = new PostalNotification(connectForSend,
//                        statusPackage.get(i).id_package, "overdue_package", generalWriteInFile);
//            }
//        }
//        flag = setChangeStatus(connectForSend, statusPackage,generalWriteInFile);
//        return flag;
//    }

//    private static boolean setChangeStatus(ConnectWithDB connectForSend, ArrayList<PostalPackage> statusPackage, WriteInFile generalWriteInFile){
//        String sql = "update packages " +
//                "set (status, date_change_status) = (?, ?::timestamp)" +
//                "where id_package = ?";
//        String line = " ";
//        boolean flag = false;
//        try{
//            System.out.println("+1");
//            connectForSend.prst = connectForSend.conn.prepareStatement(sql);//создание connect
//            System.out.println("+2");
//            for(int counter = 0; counter < statusPackage.size(); counter++){
//                System.out.println("+3");
//                connectForSend.prst.setLong(3, statusPackage.get(counter).id_package);
//                System.out.println("+4");
//                connectForSend.prst.setString(1, statusPackage.get(counter).status);
//                System.out.println("+5");
//                connectForSend.prst.setString(2, statusPackage.get(counter).date_change_status);
//                line = statusPackage.get(counter).id_package + " " + statusPackage.get(counter).status + " " +
//                        statusPackage.get(counter).date_change_status;
//                System.out.println("+6");
//                connectForSend.prst.addBatch();
//                flag = true;
//                generalWriteInFile.writeInFile("change status in table packages: " + line);
//            }
//            System.out.println("+7");
//            connectForSend.prst.executeBatch();
//            System.out.println("+8");
//
//        }catch(SQLException eSQL) {
//            System.out.println("печаль в общем");
//            generalWriteInFile.writeInFile("game over 0.3");
//            flag = false;
//        }
//        return flag;
//    }
}
