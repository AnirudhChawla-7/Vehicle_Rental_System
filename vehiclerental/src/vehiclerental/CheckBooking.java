package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CheckBooking {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Check Booking - Vehicle Rental System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID Number:");
        JTextField idField = new JTextField(String.valueOf(Session.userId), 15);
        idField.setEditable(false); // Make field non-editable

        JButton checkButton = new JButton("Check Booking");

        JTextArea resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idNumber = idField.getText().trim();
                if (idNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "ID number is missing!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Connect con = new Connect();
                    String query = "SELECT * FROM RENTAL_INFO WHERE ID_NUMBER = ?";
                    PreparedStatement pst = con.c.prepareStatement(query);
                    pst.setString(1, idNumber);
                    ResultSet rs = pst.executeQuery();

                    StringBuilder resultText = new StringBuilder();
                    while (rs.next()) {
                        resultText.append("Customer Name: ").append(rs.getString("CUSTOMER_NAME")).append("\n");
                        resultText.append("Rental ID: ").append(rs.getString("RENTAL_ID")).append("\n");
                        resultText.append("Brand: ").append(rs.getString("BRAND")).append("\n");
                        resultText.append("Model Name: ").append(rs.getString("MODEL_NAME")).append("\n");
                        resultText.append("Price Per Day: $").append(rs.getString("PRICE_PER_DAY")).append("\n\n");
                    }

                    if (resultText.length() == 0) {
                        resultArea.setText("No booking found for your ID.");
                    } else {
                        resultArea.setText(resultText.toString());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error retrieving booking details.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(checkButton, gbc);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
