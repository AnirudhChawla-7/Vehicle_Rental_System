package vehiclerental;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AvailableVehicles {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Available Vehicles - Vehicle Rental System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Vehicle Type", "Brand", "Model", "Price Per Day"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        loadAvailableVehicles(tableModel);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void loadAvailableVehicles(DefaultTableModel tableModel) {
        try {
            Connect con = new Connect();
            String query = "SELECT VEHICLE_TYPE, BRAND, MODEL_NAME, PRICE_PER_DAY FROM VEHICLE";
            Statement stmt = con.s;
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                String type = rs.getString("VEHICLE_TYPE");
                String brand = rs.getString("BRAND");
                String model = rs.getString("MODEL_NAME");
                String price = rs.getString("PRICE_PER_DAY");
                tableModel.addRow(new Object[]{type, brand, model, price});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching data", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
