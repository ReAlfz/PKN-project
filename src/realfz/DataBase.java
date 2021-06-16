package realfz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public static Connection connection;

    public static Connection getDatafromDataBase() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost/pkn-project";
            String user = "root";
            String pass = "";

            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = (Connection) DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
