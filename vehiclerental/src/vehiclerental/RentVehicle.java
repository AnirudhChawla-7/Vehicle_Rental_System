package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class RentVehicle {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rent Vehicle - Vehicle Rental System");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Customer Name:");
        JTextField nameField = new JTextField(Session.username, 10); // Auto-filled with logged-in username
        nameField.setEditable(false);

        JLabel idLabel = new JLabel("ID Number:");
        JTextField idField = new JTextField(String.valueOf(Session.userId), 10); // Auto-filled with user ID
        idField.setEditable(false);

        JLabel brandLabel = new JLabel("Select Brand:");
        JComboBox<String> brandDropdown = new JComboBox<>(getBrands());

        JLabel modelLabel = new JLabel("Select Model:");
        JComboBox<String> modelDropdown = new JComboBox<>();

        JLabel priceLabel = new JLabel("Price per Day:");
        JTextField priceField = new JTextField(10);
        priceField.setEditable(false);

        JButton rentButton = new JButton("Rent Car");

        brandDropdown.addActionListener(e -> {
            String selectedBrand = (String) brandDropdown.getSelectedItem();
            modelDropdown.removeAllItems();
            for (String model : getModels(selectedBrand)) {
                modelDropdown.addItem(model);
            }
        });

        modelDropdown.addActionListener(e -> {
            String selectedBrand = (String) brandDropdown.getSelectedItem();
            String selectedModel = (String) modelDropdown.getSelectedItem();
            if (selectedModel != null) {
                priceField.setText(getPrice(selectedBrand, selectedModel));
            }
        });

        rentButton.addActionListener(e -> {
            String customerName = nameField.getText().trim();
            String idNumber = idField.getText().trim();
            String selectedBrand = (String) brandDropdown.getSelectedItem();
            String selectedModel = (String) modelDropdown.getSelectedItem();
            String price = priceField.getText().trim();

            if (customerName.isEmpty() || idNumber.isEmpty() || selectedBrand == null ||
                    selectedModel == null || price.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter all details before renting.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String rentalId = UUID.randomUUID().toString().substring(0, 8); // Short rental ID

            try {
                Connect con = new Connect();
                String query = "INSERT INTO RENTAL_INFO (CUSTOMER_NAME, ID_NUMBER, RENTAL_ID, BRAND, MODEL_NAME, PRICE_PER_DAY) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.c.prepareStatement(query);
                pst.setString(1, customerName);
                pst.setString(2, idNumber);
                pst.setString(3, rentalId);
                pst.setString(4, selectedBrand);
                pst.setString(5, selectedModel);
                pst.setString(6, price);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Vehicle rented successfully!\nRental ID: " + rentalId, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(brandLabel, gbc);
        gbc.gridx = 1;
        panel.add(brandDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(modelLabel, gbc);
        gbc.gridx = 1;
        panel.add(modelDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(priceLabel, gbc);
        gbc.gridx = 1;
        panel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(rentButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String[] getBrands() {
        ArrayList<String> brands = new ArrayList<>();
        try {
            Connect con = new Connect();
            String query = "SELECT DISTINCT BRAND FROM VEHICLE";
            ResultSet rs = con.s.executeQuery(query);
            while (rs.next()) {
                brands.add(rs.getString("BRAND"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brands.toArray(new String[0]);
    }

    private static String[] getModels(String brand) {
        ArrayList<String> models = new ArrayList<>();
        try {
            Connect con = new Connect();
            String query = "SELECT MODEL_NAME FROM VEHICLE WHERE BRAND=?";
            PreparedStatement pst = con.c.prepareStatement(query);
            pst.setString(1, brand);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                models.add(rs.getString("MODEL_NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return models.toArray(new String[0]);
    }

    private static String getPrice(String brand, String model) {
        String price = "";
        try {
            Connect con = new Connect();
            String query = "SELECT PRICE_PER_DAY FROM VEHICLE WHERE BRAND=? AND MODEL_NAME=?";
            PreparedStatement pst = con.c.prepareStatement(query);
            pst.setString(1, brand);
            pst.setString(2, model);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                price = rs.getString("PRICE_PER_DAY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }
}
