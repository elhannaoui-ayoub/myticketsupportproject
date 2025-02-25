package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

public class TicketManagementUI extends JFrame {
    private String userRole;
    private static final String API_URL = "http://localhost:8081/tickets/new"; // Replace with actual URL
    private static final String GET_TICKETS_URL = "http://localhost:8081/tickets/list"; // API to fetch tickets

    public TicketManagementUI(String userRole) {
        this.userRole = userRole;
        setTitle("Ticket Management - " + userRole);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Layout and components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        // Title Label
        JLabel titleLabel = new JLabel("Welcome, " + userRole, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(5, 5, 5, 5);
        btnGbc.fill = GridBagConstraints.HORIZONTAL;
        btnGbc.gridx = 0;
        btnGbc.gridy = 0;
        if (!getEmployeeIdBasedOnRole().equals("3")) {
            // Create Ticket Button
            JButton createTicketButton = new JButton("Create Ticket");
            createTicketButton.addActionListener(e -> showCreateTicketForm());
            buttonPanel.add(createTicketButton, btnGbc);
        }
        // View Tickets Button
        JButton viewTicketsButton = new JButton("View Tickets");
        viewTicketsButton.addActionListener(e -> showTicketsList());
        btnGbc.gridy = 1;
        buttonPanel.add(viewTicketsButton, btnGbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);
    }

    // Method to display the "Create Ticket" form
    private void showCreateTicketForm() {
        JDialog createTicketDialog = new JDialog(this, "Create New Ticket", true);
        createTicketDialog.setSize(400, 500);
        createTicketDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Title field
        JLabel titleLabel = new JLabel("Title:");
        gbc.gridy = 0;
        createTicketDialog.add(titleLabel, gbc);
        JTextField titleField = new JTextField();
        gbc.gridy = 1;
        createTicketDialog.add(titleField, gbc);

        // Description field
        JLabel descriptionLabel = new JLabel("Description:");
        gbc.gridy = 2;
        createTicketDialog.add(descriptionLabel, gbc);
        JTextField descriptionField = new JTextField();
        gbc.gridy = 3;
        createTicketDialog.add(descriptionField, gbc);

        // Priority dropdown
        JLabel priorityLabel = new JLabel("Priority:");
        gbc.gridy = 4;
        createTicketDialog.add(priorityLabel, gbc);
        JComboBox<String> priorityComboBox = new JComboBox<>(new String[]{"LOW", "MEDIUM", "HIGH"});
        gbc.gridy = 5;
        createTicketDialog.add(priorityComboBox, gbc);

        // Category dropdown
        JLabel categoryLabel = new JLabel("Category:");
        gbc.gridy = 6;
        createTicketDialog.add(categoryLabel, gbc);
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"NETWORK", "HARDWARE", "SOFTWARE", "OTHER"});
        gbc.gridy = 7;
        createTicketDialog.add(categoryComboBox, gbc);

        // Submit Button
        JButton submitButton = new JButton("Submit Ticket");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String description = descriptionField.getText();
                String priority = (String) priorityComboBox.getSelectedItem();
                String category = (String) categoryComboBox.getSelectedItem();

                if (title.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(createTicketDialog, "Please fill in all fields.");
                } else {
                    // Send POST request to API
                    sendTicketToAPI(title, description, priority, category);
                    createTicketDialog.dispose(); // Close the form
                }
            }
        });

        gbc.gridy = 8;
        createTicketDialog.add(submitButton, gbc);

        // Show form
        createTicketDialog.setLocationRelativeTo(this);
        createTicketDialog.setVisible(true);
    }

    // Send ticket details to backend using POST request
    private void sendTicketToAPI(String title, String description, String priority, String category) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dynamically set employee_id based on the user role
            String employeeId = getEmployeeIdBasedOnRole();  // Get employee_id dynamically
            String jsonInputString = String.format(
                    "{\"title\":\"%s\", \"description\":\"%s\", \"priority\":\"%s\", \"category\":\"%s\", \"employee_id\":\"%s\"}",
                    title, description, priority, category, employeeId);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(this, "Ticket created successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create ticket.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating ticket: " + e.getMessage());
        }
    }

    // Method to get the employee_id based on the user role
    private String getEmployeeIdBasedOnRole() {
        if ("Employee1".equals(userRole)) {
            return "1";  // Set employee_id to 1 if role is Employee 1
        } else if ("Employee2".equals(userRole)) {
            return "2";  // Set employee_id to 2 if role is Employee 2
        }
        return "3";  // Default case (if no role matches)
    }

    // Method to display the list of tickets
    private void showTicketsList() {
        JDialog ticketsDialog = new JDialog(this, "View Tickets", true);
        ticketsDialog.setSize(700, 500);
        ticketsDialog.setLayout(new BorderLayout());

        // Table Setup
        String[] columnNames = {"ID", "Title", "Description", "Priority", "Category", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable ticketsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ticketsTable);
        ticketsDialog.add(scrollPane, BorderLayout.CENTER);

        // Fetch and populate tickets from API
        JSONArray allTickets = fetchTicketsFromAPI(tableModel);

        // 🔍 Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField ticketIdField = new JTextField(10);
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"All", "NEW", "IN_PROGRESS", "RESOLVED"});
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Ticket ID:"));
        searchPanel.add(ticketIdField);
        searchPanel.add(new JLabel("Status:"));
        searchPanel.add(statusComboBox);
        searchPanel.add(searchButton);

        ticketsDialog.add(searchPanel, BorderLayout.NORTH);

        // Bottom Panel with Buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton viewDetailsButton = new JButton("View Details");
        JButton addCommentButton = new JButton("Add Comment");

        if (userRole.equals("ITSupport")) {
            JButton changeStatusButton = new JButton("Change Status");
            changeStatusButton.addActionListener(e -> changeTicketStatus(ticketsTable, tableModel));
            bottomPanel.add(changeStatusButton);
        }

        bottomPanel.add(viewDetailsButton);
        if (getEmployeeIdBasedOnRole().equals("3")) {
            bottomPanel.add(addCommentButton);
        }

        ticketsDialog.add(bottomPanel, BorderLayout.SOUTH);

        // 🔍 Action for "Search" Button
        searchButton.addActionListener(e -> {
            String ticketId = ticketIdField.getText().trim();
            String status = statusComboBox.getSelectedItem().toString();
            filterTickets(tableModel, allTickets, ticketId, status);
        });

        // Action for "View Details" Button
        viewDetailsButton.addActionListener(e -> {
            int selectedRow = ticketsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ticketsDialog, "Please select a ticket.");
                return;
            }

            String id = tableModel.getValueAt(selectedRow, 0).toString();
            String title = tableModel.getValueAt(selectedRow, 1).toString();
            String description = tableModel.getValueAt(selectedRow, 2).toString();
            String priority = tableModel.getValueAt(selectedRow, 3).toString();
            String category = tableModel.getValueAt(selectedRow, 4).toString();
            String status = tableModel.getValueAt(selectedRow, 5).toString();

            showTicketDetails(id, title, description, priority, category, status);
        });

        // Action for "Add Comment" Button
        addCommentButton.addActionListener(e -> {
            int selectedRow = ticketsTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ticketsDialog, "Please select a ticket.");
                return;
            }

            String ticketId = tableModel.getValueAt(selectedRow, 0).toString();
            showCommentForm(ticketId);
        });

        ticketsDialog.setLocationRelativeTo(this);
        ticketsDialog.setVisible(true);
    }

    private void filterTickets(DefaultTableModel tableModel, JSONArray allTickets, String ticketId, String status) {
        // Clear table before filtering
        tableModel.setRowCount(0);

        for (int i = 0; i < allTickets.length(); i++) {
            JSONObject ticket = allTickets.getJSONObject(i);
            String id = String.valueOf(ticket.getInt("id"));
            String title = ticket.getString("title");
            String description = ticket.getString("description");
            String priority = ticket.getString("priority");
            String category = ticket.getString("category");
            String ticketStatus = ticket.getString("status");

            boolean idMatches = ticketId.isEmpty() || id.equals(ticketId);
            boolean statusMatches = status.equals("All") || ticketStatus.equals(status);

            if (idMatches && statusMatches) {
                String employeeId = getEmployeeIdBasedOnRole();
                int ticketEmployeeId = ticket.getInt("employee_id");
                if (employeeId.equals("3") || Integer.toString(ticketEmployeeId).equals(employeeId)) {
                tableModel.addRow(new Object[]{id, title, description, priority, category, ticketStatus});}
            }
        }
    }

    private JSONArray fetchTicketsFromAPI(DefaultTableModel tableModel) {
        JSONArray ticketsArray = new JSONArray();
        String apiUrl = "http://localhost:8081/tickets/list";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ticketsArray = new JSONArray(new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
                String employeeId = getEmployeeIdBasedOnRole();
                for (int i = 0; i < ticketsArray.length(); i++) {
                    JSONObject ticket = ticketsArray.getJSONObject(i);
                    int ticketEmployeeId = ticket.getInt("employee_id");
                    if (employeeId.equals("3") || Integer.toString(ticketEmployeeId).equals(employeeId)) {
                    String id = String.valueOf(ticket.getInt("id"));
                    String title = ticket.getString("title");
                    String description = ticket.getString("description");
                    String priority = ticket.getString("priority");
                    String category = ticket.getString("category");
                    String status = ticket.getString("status");

                    tableModel.addRow(new Object[]{id, title, description, priority, category, status});}
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching tickets.");
        }

        return ticketsArray; // Return tickets for filtering
    }


    // Method to change ticket status
    private void changeTicketStatus(JTable ticketsTable, DefaultTableModel tableModel) {
        int selectedRow = ticketsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a ticket to change its status.");
            return;
        }

        String ticketId = tableModel.getValueAt(selectedRow, 0).toString();
        String currentStatus = tableModel.getValueAt(selectedRow, 5).toString();

        // Show dialog to select new status
        String[] statuses = {"NEW", "IN_PROGRESS", "RESOLVED"};
        String newStatus = (String) JOptionPane.showInputDialog(this,
                "Select new status:", "Change Status",
                JOptionPane.PLAIN_MESSAGE, null, statuses, currentStatus);

        if (newStatus != null && !newStatus.equals(currentStatus)) {
            // Update status via API
            boolean success = updateTicketStatus(ticketId, newStatus);

            if (success) {
                // Update the table row status
                tableModel.setValueAt(newStatus, selectedRow, 5);
                JOptionPane.showMessageDialog(this, "Ticket status updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update status.");
            }
        }
    }

    // Method to update ticket status via API
    private boolean updateTicketStatus(String ticketId, String newStatus) {
        try {
            URL url = new URL("http://localhost:8081/tickets/edit"); // Replace with actual API URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonPayload = String.format("{\"id\": \"%s\",\"it_support_id\": \"%s\", \"status\": \"%s\"}", ticketId,getEmployeeIdBasedOnRole() ,newStatus);
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Show Ticket Details in a Separate Dialog
    private void showTicketDetails(String id, String title, String description, String priority, String category, String status) {
        JDialog detailsDialog = new JDialog(this, "Ticket Details", true);
        detailsDialog.setSize(400, 500); // Adjusted size
        detailsDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Column alignment
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;

        // Ticket details labels
        detailsDialog.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        detailsDialog.add(new JLabel(id), gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0.3;
        detailsDialog.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        detailsDialog.add(new JLabel(title), gbc);

        gbc.gridx = 0; gbc.gridy++;
        detailsDialog.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        detailsDialog.add(new JLabel(description), gbc);

        gbc.gridx = 0; gbc.gridy++;
        detailsDialog.add(new JLabel("Priority:"), gbc);
        gbc.gridx = 1;
        detailsDialog.add(new JLabel(priority), gbc);

        gbc.gridx = 0; gbc.gridy++;
        detailsDialog.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        detailsDialog.add(new JLabel(category), gbc);

        gbc.gridx = 0; gbc.gridy++;
        detailsDialog.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        detailsDialog.add(new JLabel(status), gbc);

        // === Comments Section ===
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2; // Span across two columns
        detailsDialog.add(new JLabel("Comments:"), gbc);

        JTextArea commentsArea = new JTextArea(5, 30);
        commentsArea.setEditable(false);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        JScrollPane commentsScroll = new JScrollPane(commentsArea);
        gbc.gridy++;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        detailsDialog.add(commentsScroll, gbc);

        // Fetch comments
        fetchCommentsForTicket(id, commentsArea);

        // === History Section ===
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        detailsDialog.add(new JLabel("History of Status Changes:"), gbc);

        JTextArea historyArea = new JTextArea(5, 30);
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        gbc.gridy++;
        gbc.weighty = 0.5;
        detailsDialog.add(historyScroll, gbc);

        // Fetch history
        fetchHistoryForTicket(id, historyArea);

        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setVisible(true);
    }

    private void fetchHistoryForTicket(String ticketId, JTextArea historyArea) {
        String apiUrl = "http://localhost:8081/history/list"; // Replace with actual API URL to get history by ticket ID
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder history = new StringBuilder();
                JSONArray jsonHistory = new JSONArray(new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));

                for (int i = jsonHistory.length()-1; i >=0 ; i--) {
                    JSONObject historyEntry = jsonHistory.getJSONObject(i);

                    // Ensure this entry belongs to the requested ticket
                    if (Integer.valueOf(ticketId) != historyEntry.getJSONObject("ticket").getInt("id")) continue;

                    String oldStatus = historyEntry.getString("oldStatus");
                    String newStatus = historyEntry.getString("newStatus");
                    LocalDateTime dateTime = LocalDateTime.parse(historyEntry.getString("changeDate"));

                    // Format "yyyy-MM-dd HH:mm"
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedDate = dateTime.format(formatter);

                    history.append("User ").append(getEmployeeIdBasedOnRole()).append(" [").append(formattedDate).append("] ")
                            .append("Status changed from '").append(oldStatus)
                            .append("' to '").append(newStatus).append("'.\n\n");
                }

                historyArea.setText(history.toString());
            } else {
                historyArea.setText("No history available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            historyArea.setText("Error fetching history.");
        }
    }

    private void fetchCommentsForTicket(String ticketId, JTextArea commentsArea) {
        String apiUrl = "http://localhost:8081/comments/list"; // Replace with actual API URL to get comments by ticket ID
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl ).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuilder comments = new StringBuilder();
                JSONArray jsonComments = new JSONArray(new String(connection.getInputStream().readAllBytes(), StandardCharsets.UTF_8));

                for (int i = 0; i < jsonComments.length(); i++) {

                    JSONObject comment = jsonComments.getJSONObject(i);
                    if(Integer.valueOf(ticketId)!=comment.getJSONObject("ticket").getInt("id")) continue;
                    String content = comment.getString("content");
                    String userId = String.valueOf(comment.getInt("user_id"));
                    LocalDateTime dateTime = LocalDateTime.parse(comment.getString("timestamp"));

                    // Formatter pour obtenir "yyyy-MM-dd HH:mm"
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String formattedDate = dateTime.format(formatter);
                    comments.append("User ").append(userId).append(" - (").append(formattedDate).append(") :").append(content).append("\n\n");
                }

                commentsArea.setText(comments.toString());
            } else {
                commentsArea.setText("No comments available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            commentsArea.setText("Error fetching comments.");
        }
    }



    // Show Add Comment Form
    private void showCommentForm(String ticketId) {
        JDialog commentDialog = new JDialog(this, "Add Comment", true);
        commentDialog.setSize(400, 250);
        commentDialog.setLayout(new BorderLayout());

        // Panel for input
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.add(new JLabel("Enter your comment:"), BorderLayout.NORTH);

        JTextArea commentTextArea = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(commentTextArea);
        inputPanel.add(scrollPane, BorderLayout.CENTER);
        commentDialog.add(inputPanel, BorderLayout.CENTER);

        // Panel for Submit Button
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);
        commentDialog.add(buttonPanel, BorderLayout.SOUTH);

        // Action for Submit Button
        submitButton.addActionListener(e -> {
            String comment = commentTextArea.getText().trim();
            if (comment.isEmpty()) {
                JOptionPane.showMessageDialog(commentDialog, "Comment cannot be empty.");
                return;
            }

            // Send POST request to API
            boolean success = sendCommentToAPI(ticketId,getEmployeeIdBasedOnRole() ,comment);

            if (success) {
                JOptionPane.showMessageDialog(commentDialog, "Comment added successfully!");
                commentDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(commentDialog, "Failed to add comment. Please try again.");
            }
        });

        commentDialog.setLocationRelativeTo(this);
        commentDialog.setVisible(true);
    }

    // Function to send POST request
    private boolean sendCommentToAPI(String ticketId, String userId, String comment) {
        String apiUrl = "http://localhost:8081/comments/new"; // Replace with actual API URL
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON payload including ticket_id and user_id
            String jsonPayload = "{"
                    + "\"ticket_id\": \"" + ticketId + "\","
                    + "\"user_id\": \"" + userId + "\","
                    + "\"content\": \"" + comment + "\""
                    + "}";
            System.out.println("hhh");
            System.out.println(jsonPayload);
            // Send request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read response
            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 201) {
                return true; // Comment added successfully
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false; // Failed to add comment
    }



    // Fetch tickets from the backend API and populate the table


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TicketManagementUI("Employee1").setVisible(true);
        });
    }
}
