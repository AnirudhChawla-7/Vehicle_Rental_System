package vehiclerental;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHome {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Home - Car Rental System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set window to full screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with a yellow and black gradient background
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Color.CYAN, getWidth(), getHeight(), Color.BLACK);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setOpaque(false);

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.BLACK);
        menuBar.setForeground(Color.CYAN);
        JLabel titleLabel = new JLabel("Welcome to Vehicle Rental Service");
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        menuBar.add(titleLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Create buttons with a black theme
        JButton addCarButton = createButton("Add Vehicle");
        JButton rentCarButton = createButton("Rent Vehicle");
        JButton cancelBookingButton = createButton("Cancel Booking");
        JButton availableCarsButton = createButton("Available Vehicles");
        JButton rentedCarsButton = createButton("Rented Vehicles");
        JButton addUserButton = createButton("Add User");

        // Add buttons to panel
        panel.add(addCarButton, gbc);
        gbc.gridy++;
        panel.add(rentCarButton, gbc);
        gbc.gridy++;
        panel.add(cancelBookingButton, gbc);
        gbc.gridy++;
        panel.add(availableCarsButton, gbc);
        gbc.gridy++;
        panel.add(rentedCarsButton, gbc);
        gbc.gridy++;
        panel.add(addUserButton, gbc);

        // Add action listeners to open respective windows
        addCarButton.addActionListener(e -> AddVehicle.main(null));
        rentCarButton.addActionListener(e -> RentVehicle.main(null));
        cancelBookingButton.addActionListener(e -> CancelBooking.main(null));
        availableCarsButton.addActionListener(e -> AvailableVehicles.main(null));
        rentedCarsButton.addActionListener(e -> RentedVehicles.main(null));
        addUserButton.addActionListener(e -> Register.main(null));

        frame.setJMenuBar(menuBar);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50)); // Larger button size
        button.setBackground(Color.BLACK);
        button.setForeground(Color.CYAN);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
        return button;
    }
}