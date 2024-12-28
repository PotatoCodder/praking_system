package com.mycompany.parkingmanagemen_system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ParkingManagement {
    private static Connection connection;

    public static void main(String[] args) {
        connectDatabase();
        new LandingPage();
    }

    private static void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/parking_db", "root", "903100adrian");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    // Custom button with modern UI
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setBackground(new Color(70, 130, 180));
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2, true));
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Adding hover effect
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    setBackground(new Color(100, 150, 200));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    setBackground(new Color(70, 130, 180));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(new Color(100, 150, 200));
            } else {
                g.setColor(new Color(70, 130, 180));
            }
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }
    }

    // Landing Page with information
    static class LandingPage extends JFrame {
        public LandingPage() {
            setTitle("Parking Management System");
            setLayout(new BorderLayout());
            setBackground(new Color(230, 240, 255));

            // Information panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(3, 1));
            infoPanel.setBackground(new Color(230, 240, 255));

            JLabel systemName = new JLabel("Parking Management System", SwingConstants.CENTER);
            systemName.setFont(new Font("Segoe UI", Font.BOLD, 24));
            systemName.setForeground(new Color(70, 130, 180));

            JLabel systemInfo = new JLabel("<html>This system helps manage parking slots, allowing users to book and view available slots.</html>", SwingConstants.CENTER);
            systemInfo.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            RoundedButton enterButton = new RoundedButton("Enter System");

            // Adding components to the infoPanel
            infoPanel.add(systemName);
            infoPanel.add(systemInfo);
            infoPanel.add(enterButton);

            add(infoPanel, BorderLayout.CENTER);

            // Action on "Enter System" button
            enterButton.addActionListener(e -> {
                new LoginFrame();
                dispose();
            });

            setSize(500, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Register Frame with improved design
    static class RegisterFrame extends JFrame {
        public RegisterFrame() {
            setTitle("Register");
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);

            setBackground(new Color(230, 240, 255)); // Soft background color

            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(20);
            JButton registerButton = new RoundedButton("Register");
            JButton backButton = new RoundedButton("Back");

            usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(usernameLabel, gbc);

            gbc.gridx = 1;
            add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(passwordLabel, gbc);

            gbc.gridx = 1;
            add(passwordField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            add(registerButton, gbc);

            gbc.gridx = 1;
            add(backButton, gbc);

            registerButton.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (!username.isEmpty() && !password.isEmpty()) {
                    try {
                        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, 'user')");
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ps.executeUpdate();

                        JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new LoginFrame();
                        dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            });

            backButton.addActionListener(e -> {
                new LoginFrame();
                dispose();
            });

            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Login Frame with improved design
    static class LoginFrame extends JFrame {
        public LoginFrame() {
            setTitle("Login");
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(15, 15, 15, 15);

            setBackground(new Color(230, 240, 255));

            JLabel usernameLabel = new JLabel("Username:");
            JTextField usernameField = new JTextField(20);
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(20);
            JButton loginButton = new RoundedButton("Login");
            JButton registerButton = new RoundedButton("Register");

            usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(usernameLabel, gbc);

            gbc.gridx = 1;
            add(usernameField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(passwordLabel, gbc);

            gbc.gridx = 1;
            add(passwordField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            add(loginButton, gbc);

            gbc.gridx = 1;
            add(registerButton, gbc);

            loginButton.addActionListener(e -> {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (!username.isEmpty() && !password.isEmpty()) {
                    try {
                        PreparedStatement ps = connection.prepareStatement("SELECT role FROM users WHERE username = ? AND password = ?");
                        ps.setString(1, username);
                        ps.setString(2, password);

                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            String role = rs.getString("role");
                            if ("admin".equals(role)) {
                                new AdminFrame();
                            } else {
                                new UserFrame(username);
                            }
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            });

            registerButton.addActionListener(e -> {
                new RegisterFrame();
                dispose();
            });

            setSize(400, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // User Frame with improved design
    static class UserFrame extends JFrame {
        public UserFrame(String username) {
            setTitle("Parking Slots - User");
            setLayout(new GridLayout(5, 4, 10, 10));

            JButton[] slots = new JButton[20];

            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM slots");
                ResultSet rs = ps.executeQuery();

                int i = 0;
                while (rs.next()) {
                    int slotId = rs.getInt("id");
                    boolean isBooked = rs.getBoolean("is_booked");

                    slots[i] = new JButton("Slot " + (i + 1));
                    slots[i].setBackground(isBooked ? Color.RED : Color.GREEN);
                    slots[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    int finalI = i;

                    slots[i].addActionListener(e -> {
                        if (!isBooked) {
                            try {
                                PreparedStatement bookSlot = connection.prepareStatement("UPDATE slots SET is_booked = 1, booked_by = ?, booking_time = NOW() WHERE id = ?");
                                bookSlot.setString(1, username);
                                bookSlot.setInt(2, slotId);
                                bookSlot.executeUpdate();

                                JOptionPane.showMessageDialog(this, "Slot " + (finalI + 1) + " booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                slots[finalI].setBackground(Color.RED);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Slot already booked!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    });

                    add(slots[i]);
                    i++;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    // Admin Frame with improved design
    static class AdminFrame extends JFrame {
        public AdminFrame() {
            setTitle("Admin Dashboard");
            setLayout(new BorderLayout());

            JButton refreshButton = new RoundedButton("Refresh");

            JPanel slotsPanel = new JPanel(new GridLayout(5, 4, 10, 10));

            refreshButton.addActionListener(e -> {
                slotsPanel.removeAll();
                // Reload slot data here
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM slots");
                    ResultSet rs = ps.executeQuery();

                    int i = 0;
                    while (rs.next()) {
                        int slotId = rs.getInt("id");
                        boolean isBooked = rs.getBoolean("is_booked");

                        JButton slotButton = new JButton("Slot " + (i + 1));
                        slotButton.setBackground(isBooked ? Color.RED : Color.GREEN);
                        slotButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                        slotsPanel.add(slotButton);
                        i++;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                slotsPanel.revalidate();
                slotsPanel.repaint();
            });

            add(refreshButton, BorderLayout.NORTH);
            add(slotsPanel, BorderLayout.CENTER);

            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
            
                        // Add slots panel
            add(slotsPanel, BorderLayout.CENTER);

            // Initialize slot data for the first time
            refreshButton.doClick();

            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}

