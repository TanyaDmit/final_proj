package work.with.database;

import work.with.files.WriteInFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectWithDB {
    public Connection conn = null;
    public PreparedStatement prst = null;

    public ConnectWithDB(WriteInFile generalWriteInFile){
        connectStart(generalWriteInFile);
    }

    public ConnectWithDB(ArrayList<String> dataClient, WriteInFile generalWriteInFile){
        connectStart(dataClient, generalWriteInFile);
    }

    private void connectStart(WriteInFile generalWriteInFile){
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "dtk13zpuub");
            conn.setAutoCommit(false);
            System.out.println("Opened database successfully");
            generalWriteInFile.writeInFile(" start connect with database");
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("error when program connect with database");
            System.exit(0);
        }
    }

    public void setDisconnect(WriteInFile generalWriteInFile){
        connectEnd(generalWriteInFile);
    }

    private void connectEnd(WriteInFile generalWriteInFile){
        try{
            prst.close();
            conn.commit();
            conn.close();
            generalWriteInFile.writeInFile(" end connect with database");
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("error when program connect with database");
            System.exit(0);
        }
    }

    private boolean connectStart(ArrayList<String> dataClient, WriteInFile generalWriteInFile){
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "dtk13zpuub");
            conn.setAutoCommit(false);
            System.out.println("Opened database successfully");

            Iterator<String> iterconn =  dataClient.iterator();
            String tmp = iterconn.next();
            System.out.println(tmp);
            switch (tmp) {
                case "REGISTRPEOPLE":
                    prst = insert_table_client(prst, conn, dataClient, generalWriteInFile);
                    break;
                case "REGISTRPOSTALOFFICE":
                    prst = insert_table_office(prst, conn, dataClient, generalWriteInFile);
                    break;
                case "REGISTRPACKAGE":
                    prst = insert_table_package(prst, conn, dataClient, generalWriteInFile);
                    break;
                default:
                    System.out.println("i don`t know, i can`t understand");
                    break;
            }
//            preparedStatement = insert_table(preparedStatement, connection, dataClient);

            prst.close();
            conn.commit();
            conn.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("error when program connect with database");
            System.exit(0);
        }
        return true;
    }

    public static PreparedStatement insert_table_client(PreparedStatement prst, Connection conn, ArrayList<String> dataClient, WriteInFile generalWriteInFile) throws SQLException {
        String sql = "INSERT INTO clients (last_name, first_name, patronymic, email, telephone) "
                + "VALUES (?, ?, ?, ?, ?);";
        Iterator<String> iter =  dataClient.iterator();
        iter.next();
        int len = 5;
        String line = " ";
        String tmp = null;
        prst = conn.prepareStatement(sql);//создание connect
        for(int counter = 1; counter <= len; counter++){
            tmp = iter.next();
            prst.setString(counter, tmp);
            line = line + tmp + " ";
        }
        prst.executeUpdate();
        generalWriteInFile.writeInFile("add the record in table clients: " + line);
        return prst;
    }

    public static PreparedStatement insert_table_office(PreparedStatement prst, Connection conn, ArrayList<String> dataClient, WriteInFile generalWriteInFile) throws SQLException {
        String sql = "INSERT INTO postal_offices (num_office, description_office) "
                + "VALUES (?::int, ?);";
        Iterator<String> iter =  dataClient.iterator();
        iter.next();
        int len = 2;
        String line = " ";
        String tmp = null;
        prst = conn.prepareStatement(sql);//создание connect
        for(int counter = 1; counter <= len; counter++){
            tmp = iter.next();
            prst.setString(counter, tmp);
            line = line + tmp + " ";
        }
        prst.executeUpdate();
        generalWriteInFile.writeInFile("add the record in table postal_offices: " + line);
        return prst;
    }

    public static PreparedStatement insert_table_package(PreparedStatement prst, Connection conn, ArrayList<String> dataClient, WriteInFile generalWriteInFile) throws SQLException {
        String sql = "INSERT INTO packages (telephone_sender, num_office_recipient, telephone, last_name, first_name, " +
                "patronymic, date_of_create, status)"
                + "VALUES (?, ?::int, ?, ?, ?, ?, ?::timestamp,'new_package');";
        Iterator<String> iter =  dataClient.iterator();
        iter.next();
        int len = 7;
        String line = " ";
        String tmp = null;
        prst = conn.prepareStatement(sql);//создание connect
        for(int counter = 1; counter <= len; counter++){
            tmp = iter.next();
            prst.setString(counter, tmp);
            line = line + tmp + " ";
        }
        prst.executeUpdate();
        generalWriteInFile.writeInFile("add the record in table packages: " + line);
        return prst;
    }
}
