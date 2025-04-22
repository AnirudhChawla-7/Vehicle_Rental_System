package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class AddVehicle {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add Vehicle - Vehicle Rental System");
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel typeLabel = new JLabel("Vehicle Type:");
        String[] vehicleTypes = {"Sedan", "SUV", "Hatchback", "Truck", "Van", "Bike", "Scooter"};
        JComboBox<String> typeDropdown = new JComboBox<>(vehicleTypes);
        
        JLabel brandLabel = new JLabel("Brand:");
        JTextField brandText = new JTextField(15);
        
        JLabel modelLabel = new JLabel("Model Name:");
        JTextField modelText = new JTextField(15);
        
        JLabel priceLabel = new JLabel("Price per Day:");
        JTextField priceText = new JTextField(10);
        
        JButton addButton = new JButton("Add Vehicle");
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        addButton.addActionListener((ActionEvent e) -> {
            String vehicleType = (String) typeDropdown.getSelectedItem();
            String brand = brandText.getText();
            String model = modelText.getText();
            String price = priceText.getText();

            if (vehicleType != null && !brand.isEmpty() && !model.isEmpty() && !price.isEmpty()) {
                if (addCarToDatabase(vehicleType, brand, model, price)) {
                    messageLabel.setText("Vehicle Added Successfully!");
                    messageLabel.setForeground(Color.GREEN);
                    JOptionPane.showMessageDialog(frame, "Vehicle Added Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    brandText.setText("");
                    modelText.setText("");
                    priceText.setText("");
                } else {
                    messageLabel.setText("Error Adding Vehicle");
                    messageLabel.setForeground(Color.RED);
                }
            } else {
                messageLabel.setText("All fields are required!");
                messageLabel.setForeground(Color.RED);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(typeLabel, gbc);
        gbc.gridx = 1;
        panel.add(typeDropdown, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(brandLabel, gbc);
        gbc.gridx = 1;
        panel.add(brandText, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(modelLabel, gbc);
        gbc.gridx = 1;
        panel.add(modelText, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(priceLabel, gbc);
        gbc.gridx = 1;
        panel.add(priceText, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(messageLabel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean addCarToDatabase(String vehicleType, String brand, String model, String price) {
        try {
            Connect con = new Connect();
            String query = "INSERT INTO VEHICLE (VEHICLE_TYPE, BRAND, MODEL_NAME, PRICE_PER_DAY) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.c.prepareStatement(query);
            pst.setString(1, vehicleType);
            pst.setString(2, brand);
            pst.setString(3, model);
            pst.setString(4, price);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
