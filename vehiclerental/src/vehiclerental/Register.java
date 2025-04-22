package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Vehicle Rental System - Register");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.YELLOW);
        JTextField userText = new JTextField(15);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.YELLOW);
        JPasswordField passText = new JPasswordField(15);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.YELLOW);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setForeground(Color.YELLOW);

        // Register button action
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passText.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Fields cannot be empty!");
                } else {
                    Integer userId = registerUser(username, password);
                    if (userId != null) {
                        messageLabel.setText("Registration Successful!");
                        JOptionPane.showMessageDialog(frame, "User Registered!\nYour Unique ID: " + userId, "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose(); // Close register window
                        Login.main(null); // Open login page
                    } else {
                        messageLabel.setText("Username already exists!");
                    }
                }
            }
        });

        // UI Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);
        gbc.gridx = 1;
        panel.add(userText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passLabel, gbc);
        gbc.gridx = 1;
        panel.add(passText, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(messageLabel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to register user in DB and return user ID
    private static Integer registerUser(String username, String password) {
        try {
            Connect con = new Connect();

            // Check if the username already exists
            String checkQuery = "SELECT * FROM LOGIN WHERE USERNAME=?";
            PreparedStatement checkPst = con.c.prepareStatement(checkQuery);
            checkPst.setString(1, username);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                return null; // Username already exists
            }

            // Insert new user and retrieve generated ID
            String insertQuery = "INSERT INTO LOGIN (USERNAME, PASSWORD) VALUES (?, ?)";
            PreparedStatement insertPst = con.c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertPst.setString(1, username);
            insertPst.setString(2, password);
            insertPst.executeUpdate();

            // Get the generated user ID
            ResultSet generatedKeys = insertPst.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // Return unique user ID
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Error
    }
}
