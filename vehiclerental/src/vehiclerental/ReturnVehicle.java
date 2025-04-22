package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnVehicle {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Return Car - Car Rental System");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel bookingIdLabel = new JLabel("Enter Booking ID:");
        JTextField bookingIdField = new JTextField(10);
        JButton returnButton = new JButton("Return Car");

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookingId = bookingIdField.getText().trim();
                if (bookingId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    Connection con = new Connect().c;
                    String query = "DELETE FROM RENTAL_INFO WHERE RENTAL_ID = ?";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, bookingId);
                    int rowsAffected = pst.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Car returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "No booking found with this ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error returning car.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(bookingIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(bookingIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(returnButton, gbc);

        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
