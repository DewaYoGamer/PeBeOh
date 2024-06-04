package aplikasilowongankerja;
import java.sql.*;

public class handleMySQL {
    public Connection connect() {
        Connection conn = null;
        try {
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