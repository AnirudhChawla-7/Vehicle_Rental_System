package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserHome {
    public static void main(String[] args) {
        JFrame frame = new JFrame("User Home - Car Rental System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Background Panel with Black & Yellow Gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = Color.BLACK;
                Color endColor = Color.CYAN;
                GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
                g2d.setPaint(gradientPaint);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        // Transparent Panel for Buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Creating Styled Buttons
        JButton rentCarButton = createStyledButton("🚗 Rent Vehicle", e -> RentVehicle.main(null));
        JButton cancelBookingButton = createStyledButton("❌ Cancel Booking", e -> CancelBooking.main(null));
        JButton checkBookingButton = createStyledButton("🔍 Check Booking", e -> CheckBooking.main(null));
        JButton checkAvailabilityButton = createStyledButton("✅ Check Availability", e -> AvailableVehicles.main(null));
        JButton returnCarButton = createStyledButton("🔄 Return Vehicle", e -> ReturnVehicle.main(null));

        // Adding Buttons to the Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(rentCarButton, gbc);
        gbc.gridy++;
        buttonPanel.add(cancelBookingButton, gbc);
        gbc.gridy++;
        buttonPanel.add(checkBookingButton, gbc);
        gbc.gridy++;
        buttonPanel.add(checkAvailabilityButton, gbc);
        gbc.gridy++;
        buttonPanel.add(returnCarButton, gbc);

        // Adding Panels to Frame
        backgroundPanel.add(buttonPanel);
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }

    // Styled Button Method
    private static JButton createStyledButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect (Yellow)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.CYAN);
                button.setForeground(Color.BLACK);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK);
                button.setForeground(Color.WHITE);
            }
        });

        button.addActionListener(actionListener);
        return button;
    }
}
