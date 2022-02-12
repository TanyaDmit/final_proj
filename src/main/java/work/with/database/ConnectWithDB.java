package work.with.database;

import java.sql.*;

public class ConnectWithDB {
    public ConnectWithDB(){
        connect();
    }
    private boolean connect(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "dtk13zpuub");
            connection.setAutoCommit(false);
            System.out.println("Opened database successfully");

            String data1 = "1";

            String sql = "INSERT INTO clients (last_name, first_name, patronymic, email, telephone) "
                    + "VALUES (?, ?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(sql);//создание connect
            preparedStatement.setString(1, "Дмитраш");
            preparedStatement.setString(2, "Таня");
            preparedStatement.setString(3, "Константиновна");
            preparedStatement.setString(4, "gthyt@gmail.com");
            preparedStatement.setString(5, "+380661117744");
            preparedStatement.executeUpdate();
            //insert_table(preparedStatement, connection);
            //create_table(stmt);
            //print_table(stmt);

            preparedStatement.close();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return true;
    }

    public static void insert_table(Statement stmt) throws SQLException {
        String sql = "INSERT INTO clients (last_name, first_name, patronymic, email, telephone) "
                + "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
        stmt.executeUpdate(sql);
    }
}
