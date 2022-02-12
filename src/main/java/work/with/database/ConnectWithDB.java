package work.with.database;

import work.with.files.WriteInFile;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectWithDB {
    public ConnectWithDB(ArrayList<String> dataClient, WriteInFile generalWriteInFile){
        connect(dataClient, generalWriteInFile);
    }
    private boolean connect(ArrayList<String> dataClient, WriteInFile generalWriteInFile){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "dtk13zpuub");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

//            String sql = "INSERT INTO clients (last_name, first_name, patronymic, email, telephone) "
//                    + "VALUES (?, ?, ?, ?, ?);";

//            preparedStatement = connection.prepareStatement(sql);//создание connect
//            preparedStatement.setString(1, "Дмитраш");
//            preparedStatement.setString(2, "Таня");
//            preparedStatement.setString(3, "Константиновна");
//            preparedStatement.setString(4, "gthyt@gmail.com");
//            preparedStatement.setString(5, "+380661117744");
//            preparedStatement.executeUpdate();
            Iterator<String> iterconn =  dataClient.iterator();
            String tmp = iterconn.next();
            System.out.println(tmp);
            switch (tmp) {
                case "REGISTRPEOPLE":
                    preparedStatement = insert_table_client(preparedStatement, connection, dataClient, generalWriteInFile);
                    break;
                case "REGISTRPOSTALOFFICE":
                    preparedStatement = insert_table_office(preparedStatement, connection, dataClient, generalWriteInFile);
                    break;
                case "REGISTRPACKAGE":
                    preparedStatement = insert_table_package(preparedStatement, connection, dataClient, generalWriteInFile);
                    break;
                default:
                    System.out.println("i don`t know, i can`t understand");
                    break;
            }
//            preparedStatement = insert_table(preparedStatement, connection, dataClient);

            preparedStatement.close();
            connection.commit();
            connection.close();
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
                "patronymic, date_of_create)"
                + "VALUES (?, ?::int, ?, ?, ?, ?, ?::date);";
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
