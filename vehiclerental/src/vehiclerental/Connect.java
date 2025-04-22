package vehiclerental;

import java.sql.*;

public class Connect {
    public Connection c;
    public Statement s;

    public Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql:///CARRENTAL", "root", "LUCIF3R_7");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
