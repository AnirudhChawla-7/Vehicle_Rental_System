package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RentedVehicles {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rented Vehicles - Vehicle Rental System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        String[] columnNames = {"Customer Name", "ID Number", "Rental ID", "Brand", "Model Name", "Price Per Day"};
        String[][] data = getRentedVehicles();
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static String[][] getRentedVehicles() {
        try {
            Connect con = new Connect();
            String query = "SELECT CUSTOMER_NAME, ID_NUMBER, RENTAL_ID, BRAND, MODEL_NAME, PRICE_PER_DAY FROM RENTAL_INFO";
            Statement stmt = con.c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery(query);
            
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();
            
            String[][] data = new String[rowCount][6];
            int i = 0;
            while (rs.next()) {
                data[i][0] = rs.getString("CUSTOMER_NAME");
                data[i][1] = rs.getString("ID_NUMBER");
                data[i][2] = rs.getString("RENTAL_ID");
                data[i][3] = rs.getString("BRAND");
                data[i][4] = rs.getString("MODEL_NAME");
                data[i][5] = rs.getString("PRICE_PER_DAY");
                i++;
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }
    }
}
