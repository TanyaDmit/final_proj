package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class PostalPackage {
    private long id_package;
    private String status;
    private String date_change_status;

    public PostalPackage(long id_package, String status, String date_change_status, WriteInFile generalWriteInFile){
        this.id_package = id_package;
        this.status = status;
        this.date_change_status = date_change_status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostalPackage that = (PostalPackage) o;
        return Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    public PostalPackage(ConnectWithDB generalConnectWithDB, ArrayList<String> dataPackage, WriteInFile generalWriteInFile){
        String sql = "INSERT INTO packages (telephone_sender, num_office_recipient, telephone, last_name, first_name, " +
                "patronymic, status)"
                + "VALUES (?, ?::int, ?, ?, ?, ?, 'new_package');";
        Iterator<String> iter =  dataPackage.iterator();
        iter.next();
        int len = 6;
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
        }catch(SQLException eSQL) {
            System.out.println("can`t add data in table packages");
            generalWriteInFile.writeInFile("can`t add data in table packages");
            generalConnectWithDB.setReConnect(generalWriteInFile, true);
        }

    }

    public static ArrayList getSendPackage(ConnectWithDB connectForSend, WriteInFile generalWriteInFile){
        ArrayList<PostalPackage> statusPackage= new ArrayList<>();
        String sql = "SELECT to_char(date_of_create, 'YYYY-MM-DD HH24:MI:SS') as d1, " +
                "status," +
                "id_package FROM packages where status = 'new_package';";
        try{
            String tmpLink = null;
            connectForSend.prst = connectForSend.conn.prepareStatement(sql);
            ResultSet resultSet = connectForSend.prst.executeQuery();
            while(resultSet.next()){
                long idPackage = resultSet.getLong("id_package");
                String statusP = resultSet.getString("status");
                String dateOfCreate = resultSet.getString("d1");

                statusPackage.add(new PostalPackage(idPackage, statusP, dateOfCreate, generalWriteInFile));
                tmpLink = (idPackage+" "+ statusP+" " +dateOfCreate+" ");
                generalWriteInFile.writeInFile("read package to send and change status:" + tmpLink);
            }
//            Iterator<PostalPackage> statusIter = statusPackage.iterator();
//            while(statusIter.hasNext()){
//                PostalPackage.coutPostalPackage(statusIter.next());
//            }
        } catch(SQLException eSQL){
            System.out.println("error when we read from packages");
            generalWriteInFile.writeInFile("error when we read from packages");
        }
        return statusPackage;
    }

    public static boolean changeStatus(ConnectWithDB connectForSend, ArrayList<PostalPackage> statusPackage, WriteInFile generalWriteInFile){
        boolean flag = false;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ndt = LocalDateTime.now();
        String tmpForTime = " ";
        for(int i = 0; i < statusPackage.size(); i++){
            flag = true;
            tmpForTime = " ";
            LocalDateTime dateTime = LocalDateTime.parse(statusPackage.get(i).date_change_status,dateTimeFormatter);
            int sec1 = ndt.getSecond();
            int sec2 = dateTime.getSecond();
            if((sec1-sec2) <= 5){
                int myRand = (int)(Math.random()*5);
                if(myRand == 3){
                    tmpForTime += ndt;
                    PostalPackage tmp = new PostalPackage(statusPackage.get(i).id_package,
                            "delivered_package",tmpForTime, generalWriteInFile);
                    statusPackage.set(i, tmp);
                    PostalNotification postalNotification = new PostalNotification(connectForSend,
                            statusPackage.get(i).id_package, "delivered_package", generalWriteInFile);
                }
            } else{
                tmpForTime += ndt;
                PostalPackage tmp = new PostalPackage(statusPackage.get(i).id_package,
                        "overdue_package",tmpForTime, generalWriteInFile);
                statusPackage.set(i, tmp);
                PostalNotification postalNotification = new PostalNotification(connectForSend,
                        statusPackage.get(i).id_package, "overdue_package", generalWriteInFile);
            }
        }
        flag = setChangeStatus(connectForSend, statusPackage,generalWriteInFile);
        return flag;
    }

    private static boolean setChangeStatus(ConnectWithDB connectForSend, ArrayList<PostalPackage> statusPackage, WriteInFile generalWriteInFile){
        String sql = "update packages " +
                "set (status, date_change_status) = (?, ?::timestamp)" +
                "where id_package = ?";
        String line = " ";
        boolean flag = false;
        try{
            connectForSend.prst = connectForSend.conn.prepareStatement(sql);
            for(int counter = 0; counter < statusPackage.size(); counter++){
                connectForSend.prst.setLong(3, statusPackage.get(counter).id_package);
                connectForSend.prst.setString(1, statusPackage.get(counter).status);
                connectForSend.prst.setString(2, statusPackage.get(counter).date_change_status);
                line = statusPackage.get(counter).id_package + " " + statusPackage.get(counter).status + " " +
                        statusPackage.get(counter).date_change_status;
                connectForSend.prst.addBatch();
                flag = true;
                generalWriteInFile.writeInFile("change status in table packages: " + line);
            }
            connectForSend.prst.executeBatch();
        }catch(SQLException eSQL) {
            System.out.println("can`t sent changed status in table packages");
            generalWriteInFile.writeInFile("can`t sent changed status in table packages");
            flag = false;
        }
        return flag;
    }

    public static void coutPostalPackage(PostalPackage tmp){
        LocalDateTime ndt = LocalDateTime.now();
        System.out.println(tmp.id_package+" "+tmp.status+" "+tmp.date_change_status+" | "+ndt);
    }

    public static void coutPostalPackage(ConnectWithDB connectForRead, WriteInFile generalWriteInFile){
        String sql = "SELECT * FROM packages;";
        try{
            String tmpLink = null;
            connectForRead.prst = connectForRead.conn.prepareStatement(sql);
            ResultSet resultSet = connectForRead.prst.executeQuery();
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
            System.out.println("error when we read from packages all data");
            generalWriteInFile.writeInFile("error when we read from packages all data");
        }
    }
}
