package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Vehicle Rental System - Login");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        JTextField userText = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passText = new JPasswordField(15);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

        loginButton.addActionListener(e -> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            if (authenticateUser(username, password)) {
                messageLabel.setText("Login Successful!");
                messageLabel.setForeground(Color.GREEN);
                JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();

                // Redirect based on user type
                if (username.toLowerCase().contains("admin")) {
                    AdminHome.main(null);
                } else {
                    UserHome.main(null); // Create UserHome if not already present
                }
            } else {
                messageLabel.setText("Invalid Credentials");
                messageLabel.setForeground(Color.RED);
            }
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            Register.main(null);
        });

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
        panel.add(loginButton, gbc);

        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        gbc.gridy = 4;
        panel.add(messageLabel, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean authenticateUser(String username, String password) {
        try {
            Connect con = new Connect();
            String query = "SELECT * FROM LOGIN WHERE USERNAME=? AND PASSWORD=?";
            PreparedStatement pst = con.c.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Session.userId = rs.getInt("id"); // save ID to session
                Session.username = rs.getString("USERNAME");
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
