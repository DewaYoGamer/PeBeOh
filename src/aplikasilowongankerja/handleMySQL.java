package aplikasilowongankerja;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class handleMySQL {
    public Connection connect() {
        Connection conn = null;
        try {
            // Replace with your database url, username and password
            String url = "jdbc:mysql://localhost:3306/db_pbo";
            String user = "root";
            String password = "";

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}