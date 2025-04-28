/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;  

import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.sql.*;
import com.toedter.calendar.JDateChooser; // calendar date picker library
import dao.ConnectionProvider;
import java.text.SimpleDateFormat;
import utility.BDUtility;

public class ViewAttendance1 extends JFrame {
     
    JTable table;
    DefaultTableModel model;
    JTextField searchField;
    JDateChooser fromDateChooser, toDateChooser;
    JButton searchButton, filterButton, refreshButton,closeButton;
    JLabel lblPresent, lblAbsent, presentLBL, absentLBL;
    JCheckBox checkContact, checkAddress, checkCountry, checkEmail;

    public ViewAttendance1() {
        
        setTitle("View Attendance");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 750);
        setLocationRelativeTo(null);
        
        setUndecorated(true); // Hides the default title bar
        setResizable(false);
        
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK)); 

        closeButton = new JButton("X");
        closeButton.setBounds(1160, 5, 30, 30);  // Position it at the top-right corner
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        closeButton.addActionListener(e -> dispose()); // Close action
        add(closeButton);
        
        // Top Panel for Search and Filter
        JPanel topPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        fromDateChooser = new JDateChooser();
        fromDateChooser.setDateFormatString("yyyy-MM-dd");
        fromDateChooser.setPreferredSize(new Dimension(120, 25));

        toDateChooser = new JDateChooser();
        toDateChooser.setDateFormatString("yyyy-MM-dd");
        toDateChooser.setPreferredSize(new Dimension(120, 25));

        filterButton = new JButton("Filter by Date");

        refreshButton = new JButton("Refresh");

        topPanel.add(new JLabel("Search (Name or Email):"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        topPanel.add(new JLabel("From:"));
        topPanel.add(fromDateChooser);
        topPanel.add(new JLabel("To:"));
        topPanel.add(toDateChooser);
        topPanel.add(filterButton);

        topPanel.add(refreshButton);

        add(topPanel, BorderLayout.NORTH);

        // Table Model
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Contact");
        model.addColumn("Address");
        model.addColumn("Country");
        model.addColumn("Email");
        model.addColumn("Date");
        model.addColumn("Check-In");
        model.addColumn("Check-Out");
        model.addColumn("Work Duration");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel for Attendance Summary (Present and Absent counts)
        JPanel bottomPanel = new JPanel();
        lblPresent = new JLabel("Present: ");
        lblAbsent = new JLabel("Absent: ");
        presentLBL = new JLabel("0");
        absentLBL = new JLabel("0");

        bottomPanel.add(lblPresent);
        bottomPanel.add(presentLBL);
        bottomPanel.add(lblAbsent);
        bottomPanel.add(absentLBL);

        add(bottomPanel, BorderLayout.SOUTH);

        // Checkbox Panel for Column Visibility
        JPanel checkboxPanel = new JPanel();
        checkContact = new JCheckBox("Contact");
        checkAddress = new JCheckBox("Address");
        checkCountry = new JCheckBox("Country");
        checkEmail = new JCheckBox("Email");

        // Set all checkboxes to true initially (columns visible by default)
        checkContact.setSelected(true);
        checkAddress.setSelected(true);
        checkCountry.setSelected(true);
        checkEmail.setSelected(true);

        checkboxPanel.add(checkContact);
        checkboxPanel.add(checkAddress);
        checkboxPanel.add(checkCountry);
        checkboxPanel.add(checkEmail);

        add(checkboxPanel, BorderLayout.WEST);

        // Load full data at start
        loadAttendanceData("", "", "");

        // Action Listeners
        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            loadAttendanceData(searchText, "", "");
        });

        filterButton.addActionListener(e -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fromDate = (fromDateChooser.getDate() != null) ? sdf.format(fromDateChooser.getDate()) : "";
            String toDate = (toDateChooser.getDate() != null) ? sdf.format(toDateChooser.getDate()) : "";
            loadAttendanceData("", fromDate, toDate);
        });

        refreshButton.addActionListener(e -> {
            searchField.setText("");
            fromDateChooser.setDate(null);
            toDateChooser.setDate(null);
            loadAttendanceData("", "", "");
        });

        // Add action listeners to checkboxes to dynamically show or hide columns
        checkContact.addActionListener(e -> updateColumnVisibility());
        checkAddress.addActionListener(e -> updateColumnVisibility());
        checkCountry.addActionListener(e -> updateColumnVisibility());
        checkEmail.addActionListener(e -> updateColumnVisibility());

        setVisible(true);
    }

    private void updateColumnVisibility() {
        // Set visibility based on checkbox state
        table.getColumnModel().getColumn(2).setMinWidth(checkContact.isSelected() ? 50 : 0);
        table.getColumnModel().getColumn(3).setMinWidth(checkAddress.isSelected() ? 50 : 0);
        table.getColumnModel().getColumn(4).setMinWidth(checkCountry.isSelected() ? 50 : 0);
        table.getColumnModel().getColumn(5).setMinWidth(checkEmail.isSelected() ? 50 : 0);
        
        // If the column is hidden, set width to 0
        table.getColumnModel().getColumn(2).setMaxWidth(checkContact.isSelected() ? Integer.MAX_VALUE : 0);
        table.getColumnModel().getColumn(3).setMaxWidth(checkAddress.isSelected() ? Integer.MAX_VALUE : 0);
        table.getColumnModel().getColumn(4).setMaxWidth(checkCountry.isSelected() ? Integer.MAX_VALUE : 0);
        table.getColumnModel().getColumn(5).setMaxWidth(checkEmail.isSelected() ? Integer.MAX_VALUE : 0);

        // Force table to update
        table.revalidate();
        table.repaint();
    }

    private void loadAttendanceData(String search, String fromDate, String toDate) {
        try {
            Connection con = ConnectionProvider.getCon();
            String query = "SELECT userattendance.userid, userdetails.name, userdetails.contact, userdetails.address, userdetails.country, userdetails.email, userattendance.date, userattendance.checkin, userattendance.checkout, userattendance.workduration " +
                    "FROM userattendance " +
                    "INNER JOIN userdetails ON userattendance.userid = userdetails.id";

            boolean whereAdded = false;

            if (!search.isEmpty()) {
                query += " WHERE (userdetails.name LIKE ? OR userdetails.email LIKE ?)";
                whereAdded = true;
            }

            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                if (whereAdded) {
                    query += " AND";
                } else {
                    query += " WHERE";
                }
                query += " userattendance.date BETWEEN ? AND ?";
            }

            query += " ORDER BY userattendance.date DESC, userattendance.checkin ASC";

            PreparedStatement stmt = con.prepareStatement(query);

            // Set parameters dynamically
            int paramIndex = 1;
            if (!search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
                stmt.setString(paramIndex++, "%" + search + "%");
            }

            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                stmt.setString(paramIndex++, fromDate);
                stmt.setString(paramIndex++, toDate);
            }

            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0); // Clear existing rows

            long presentCount = 0;
            long absentCount = 0;

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("userid"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("address"),
                        rs.getString("country"),
                        rs.getString("email"),
                        rs.getString("date"),
                        rs.getString("checkin"),
                        rs.getString("checkout"),
                        rs.getString("workduration")
                });

                // Calculate present and absent counts
                if (rs.getString("checkout") == null) {
                    absentCount++;
                } else {
                    presentCount++;
                }
            }

            // Update present and absent counts
            presentLBL.setText(String.valueOf(presentCount));
            absentLBL.setText(String.valueOf(absentCount));

            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading attendance data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAttendance1());
    }
}
