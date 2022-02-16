package work.with.info;

import work.with.database.ConnectWithDB;
import work.with.files.WriteInFile;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.Period;
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

    public static ArrayList getSendPackage(ConnectWithDB connectForSend, WriteInFile generalWriteInFile){
        ArrayList<PostalPackage> statusPackage= new ArrayList<>();
        try{
            connectForSend.stmt = connectForSend.conn.createStatement();
            String sql = "SELECT to_char(date_of_create, 'YYYY-MM-DD HH24:MI:SS') as d1, " +
                    "status," +
                    "id_package FROM packages;";
            String tmpLink = null;
            ResultSet resultSet = connectForSend.stmt.executeQuery(sql);
            while(resultSet.next()){
                long idPackage = resultSet.getLong("id_package");
                String statusP = resultSet.getString("status");
                String dateOfCreate = resultSet.getString("d1");//?

                statusPackage.add(new PostalPackage(idPackage, statusP, dateOfCreate, generalWriteInFile));
                //должно создаваться сообщение в статусе новое
                tmpLink = (idPackage+" "+ statusP+" " +dateOfCreate+" ");
//                System.out.println(tmpLink);
                generalWriteInFile.writeInFile("read package to send and change status:" + tmpLink);
            }
            Iterator<PostalPackage> statusIter = statusPackage.iterator();
            while(statusIter.hasNext()){
                PostalPackage.coutPostalPackage(statusIter.next());
            }
        } catch(SQLException eSQL){
            System.out.println("печаль в общем при вычитке");
            generalWriteInFile.writeInFile("error when we read from packages");
        }
        return statusPackage;
    }

    public static ArrayList changeStatus(ConnectWithDB connectForSend, ArrayList<PostalPackage> statusPackage, WriteInFile generalWriteInFile){
        Iterator<PostalPackage> statusIter = statusPackage.iterator();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ndt = LocalDateTime.now();
            for(int i = 0; i < statusPackage.size(); i++){
                if(statusPackage.get(i).status.equals("new_package")){
                    System.out.println("1");
                    LocalDateTime dateTime = LocalDateTime.parse(statusPackage.get(i).date_change_status,dateTimeFormatter);
                    int sec1 = ndt.getSecond();
                    int sec2 = dateTime.getSecond();
                    if((sec1-sec2) <= 5){
                        System.out.println("2");
                        int myRand = (int)(Math.random()*2);
                        if(myRand == 1){
                            System.out.println("3");
                            PostalPackage tmp = new PostalPackage(statusPackage.get(i).id_package,
                                    "delivered_package",statusPackage.get(i).date_change_status, generalWriteInFile);
                            statusPackage.set(i, tmp);
                        }
                    }
                    else{
                        PostalPackage tmp = new PostalPackage(statusPackage.get(i).id_package,
                                "overdue_package",statusPackage.get(i).date_change_status, generalWriteInFile);
                        statusPackage.set(i, tmp);
                    }
                }
            }
//        }
        return statusPackage;
    }

    public static void coutPostalPackage(PostalPackage tmp){
        LocalDateTime ndt = LocalDateTime.now();
        System.out.println(tmp.id_package+" "+tmp.status+" "+tmp.date_change_status+" | "+ndt);
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
            generalWriteInFile.writeInFile("error when we read from packages");
        }
    }
}
