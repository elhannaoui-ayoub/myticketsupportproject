package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("IT Support Ticket System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1; // Expand buttons horizontally

        // Buttons
        JButton employee1Button = new JButton("Employee 1");
        JButton employee2Button = new JButton("Employee 2");
        JButton itSupportButton = new JButton("IT Support");

        // Add actions
        employee1Button.addActionListener(e -> openTicketUI("Employee1"));
        employee2Button.addActionListener(e -> openTicketUI("Employee2"));
        itSupportButton.addActionListener(e -> openTicketUI("ITSupport"));

        // Add buttons to layout
        gbc.gridy = 0;
        add(employee1Button, gbc);

        gbc.gridy = 1;
        add(employee2Button, gbc);

        gbc.gridy = 2;
        add(itSupportButton, gbc);

        setVisible(true);
    }

    private void openTicketUI(String userRole) {
        SwingUtilities.invokeLater(() -> {
            TicketManagementUI ticketUI = new TicketManagementUI(userRole);
            ticketUI.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

