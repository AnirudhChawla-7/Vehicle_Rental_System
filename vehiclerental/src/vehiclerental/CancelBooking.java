package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CancelBooking {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cancel Booking - Car Rental System");
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel bookingLabel = new JLabel("Enter Rental ID:");
        JTextField bookingField = new JTextField(10);
        JButton cancelButton = new JButton("Cancel Booking");
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rentalId = bookingField.getText().trim();
                if (!rentalId.isEmpty()) {
                    int result = cancelBooking(rentalId);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Booking cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid Rental ID or Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a Rental ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(bookingLabel, gbc);
        gbc.gridx = 1;
        panel.add(bookingField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(cancelButton, gbc);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static int cancelBooking(String rentalId) {
        int rowsAffected = 0;
        try {
            Connect con = new Connect();
            String query = "DELETE FROM RENTAL_INFO WHERE RENTAL_ID = ?";
            PreparedStatement pst = con.c.prepareStatement(query);
            pst.setString(1, rentalId);
            rowsAffected = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
