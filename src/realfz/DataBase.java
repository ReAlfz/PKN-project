package realfz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBase {
    public static Connection connection;
    public static Statement statement;

    public static Statement getDatafromDataBase(){
        try {
            String url = "jdbc:mysql://localhost/pkn-project";
            String user = "root";
            String pass = "";
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }
}
