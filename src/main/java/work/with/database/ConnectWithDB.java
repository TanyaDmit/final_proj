package work.with.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectWithDB {
    public ConnectWithDB(ArrayList<String> dataClient){
        connect(dataClient);
    }
    private boolean connect(ArrayList<String> dataClient){
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
            preparedStatement = insert_table(preparedStatement, connection, dataClient);
            //create_table(stmt);
            //print_table(stmt);

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

    public static PreparedStatement insert_table(PreparedStatement prst, Connection conn, ArrayList<String> dataClient) throws SQLException {
        String sql = "INSERT INTO clients (last_name, first_name, patronymic, email, telephone) "
                + "VALUES (?, ?, ?, ?, ?);";
        Iterator<String> iter =  dataClient.iterator();
        iter.next();
        int len = 5;
        prst = conn.prepareStatement(sql);//создание connect
        for(int counter = 1; counter <= len; counter++){
            prst.setString(counter, iter.next());
        }
        prst.executeUpdate();
        return prst;
    }
}
