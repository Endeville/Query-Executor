import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;

import java.sql.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.stream.Collectors;


public class Telecoms extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JPanel contentPane, fieldsPanel, functionsPanel;
    private JTable table;

    private JComboBox tableSelect;
    private JTextField textField, textField_1, textField_2, textField_3, textField_4;
    private JLabel tableLabel, lbl1, lbl2, lbl3, lbl4, lbl5;

    private Connection databaseConnection;

    private Table currentTable;

    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    private static final String URL = "jdbc:mysql://localhost:3306/telecommunication_companies";

    int q, i, id, deleteItem;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            try {
                Telecoms frame = new Telecoms();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Telecoms() throws SQLException {
        setTitle("\u0424\u041E\u041D\u041E\u0422\u0415\u041A\u0410");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 862, 418);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel_1 = new JLabel("Telecoms");
        lblNewLabel_1.setForeground(Color.BLUE);
        lblNewLabel_1.setFont(new Font("Verdana Pro Black", Font.BOLD | Font.ITALIC, 53));
        lblNewLabel_1.setBounds(363, 28, 381, 56);
        contentPane.add(lblNewLabel_1);

        try {
            this.databaseConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No connection");
            throw new RuntimeException(e.getCause());
        }

        initializeFields();
        displayTableContent(getInitialData());
        addFunctionButtons();


    }

    private void selectFunctionality() {
        currentTable = Table.valueOf(Objects.requireNonNull(tableSelect.getSelectedItem()).toString().toUpperCase());

        var query = new StringBuilder("select * from " + currentTable.name());
        var additional = false;
        var restrictions = new HashMap<String, String>();

        if (lbl1.getText() != null && !textField.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_1, textField.getText().trim());
        }
        if (lbl2.getText() !=null && !textField_1.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_2, textField_1.getText().trim());
        }
        if (lbl3.getText() != null && !textField_2.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_3, textField_2.getText().trim());
        }
        if (lbl4.getText() != null && !textField_3.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_4, textField_3.getText().trim());
        }
        if (lbl5.getText() != null && !textField_4.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_5, textField_4.getText().trim());
        }

        if (additional) {
            query
                    .append(" where ")
                    .append(restrictions.entrySet()
                            .stream()
                            .map(r -> String.format(" `%s` like '%%%s%%' ", r.getKey(), r.getValue()))
                            .collect(Collectors.joining("and")));
        }

        try {
            var statement = databaseConnection.prepareStatement(query.toString());

            displayTableContent(statement.executeQuery());
            updateFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void updateFields() {
        lbl1.setText(currentTable.column_1);
        lbl2.setText(currentTable.column_2);
        lbl3.setText(currentTable.column_3);
        lbl4.setText(currentTable.column_4);
        lbl5.setText(currentTable.column_5);

    }

    private void initializeFields() {
        fieldsPanel = new JPanel();
        fieldsPanel.setBounds(10, 62, 274, 400);
        contentPane.add(fieldsPanel);
        fieldsPanel.setLayout(null);

        tableLabel = new JLabel("TABLE");
        tableLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        tableLabel.setBounds(10, 0, 83, 45);
        fieldsPanel.add(tableLabel);

        tableSelect = new JComboBox();
        for (Table value : Table.values()) {
            tableSelect.addItem(value.name().toLowerCase());
        }
        tableSelect.setFont(new Font("Tahoma", Font.ITALIC, 14));
        tableSelect.setBounds(103, 10, 161, 32);
        if (currentTable == null) {
            tableSelect.setSelectedIndex(0);
            currentTable=Table.valueOf(Objects.requireNonNull(tableSelect.getSelectedItem()).toString().toUpperCase());
        }
        fieldsPanel.add(tableSelect);

        lbl1 = new JLabel(currentTable.column_1);
        lbl1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl1.setBounds(10, 50, 83, 45);
        fieldsPanel.add(lbl1);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.ITALIC, 14));
        textField.setBounds(103, 60, 161, 32);
        fieldsPanel.add(textField);
        textField.setColumns(10);

        lbl2 = new JLabel(currentTable.column_2);
        lbl2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl2.setBounds(10, 100, 83, 45);
        fieldsPanel.add(lbl2);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
        textField_1.setColumns(10);
        textField_1.setBounds(103, 110, 161, 32);
        fieldsPanel.add(textField_1);

        lbl3 = new JLabel(currentTable.column_3);
        lbl3.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl3.setBounds(10, 150, 83, 45);
        fieldsPanel.add(lbl3);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Tahoma", Font.ITALIC, 14));
        textField_2.setColumns(10);
        textField_2.setBounds(103, 160, 161, 32);
        fieldsPanel.add(textField_2);

        lbl4 = new JLabel(currentTable.column_4);
        lbl4.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl4.setBounds(10, 200, 83, 45);
        fieldsPanel.add(lbl4);

        textField_3 = new JTextField();
        textField_3.setFont(new Font("Tahoma", Font.ITALIC, 14));
        textField_3.setColumns(10);
        textField_3.setBounds(103, 210, 161, 32);
        fieldsPanel.add(textField_3);


        lbl5 = new JLabel(currentTable.column_5);
        lbl5.setFont(new Font("Tahoma", Font.BOLD, 14));
        lbl5.setBounds(10, 250, 83, 45);
        fieldsPanel.add(lbl5);

        textField_4 = new JTextField();
        textField_4.setFont(new Font("Tahoma", Font.ITALIC, 14));
        textField_4.setColumns(10);
        textField_4.setBounds(103, 260, 161, 32);
        fieldsPanel.add(textField_4);
    }

    private void displayTableContent(ResultSet rs) throws SQLException {
        table = new JTable();

        var tableModel = new ArrayList<String[]>();
        tableModel.add(new String[]{null, null, null, null, null});
        tableModel.add(new String[]{
                currentTable.column_1 == null ? "" : currentTable.column_1,
                currentTable.column_2 == null ? "" : currentTable.column_2,
                currentTable.column_3 == null ? "" : currentTable.column_3,
                currentTable.column_4 == null ? "" : currentTable.column_4,
                currentTable.column_5 == null ? "" : currentTable.column_5
        });
        tableModel.add(new String[]{null, null, null, null, null});


        while (rs.next()) {
            tableModel.add(new String[]{
                    currentTable.column_1 == null ? "" : rs.getString(currentTable.column_1),
                    currentTable.column_2 == null ? "" : rs.getString(currentTable.column_2),
                    currentTable.column_3 == null ? "" : rs.getString(currentTable.column_3),
                    currentTable.column_4 == null ? "" : rs.getString(currentTable.column_4),
                    currentTable.column_5 == null ? "" : rs.getString(currentTable.column_5)
            });
        }


        table.setModel(new DefaultTableModel(
                tableModel
                        .toArray(new String[][]{}),
                new String[]{
                        currentTable.column_1,
                        currentTable.column_2,
                        currentTable.column_3,
                        currentTable.column_4,
                        currentTable.column_5}
        ));
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setBounds(294, 105, 371, 266);
        contentPane.add(table);
        contentPane.revalidate();
    }

    private ResultSet getInitialData() throws SQLException {
        var statement = databaseConnection.prepareStatement("select * from " + currentTable.name());
        return statement.executeQuery();
    }

    private void addFunctionButtons() {
        functionsPanel = new JPanel();
        functionsPanel.setBounds(675, 105, 163, 266);
        contentPane.add(functionsPanel);
        functionsPanel.setLayout(null);

        JButton btnNewButton = new JButton("ADD");
        btnNewButton.addActionListener(e->{
            addFunctionality();
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        btnNewButton.setBounds(10, 20, 151, 30);
        functionsPanel.add(btnNewButton);

        JButton btnDelete = new JButton("DELETE");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        btnDelete.setBounds(10, 74, 151, 30);
        functionsPanel.add(btnDelete);

        JButton btnNewButton_2 = new JButton("UPDATE");
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        btnNewButton_2.setBounds(10, 128, 151, 30);
        functionsPanel.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("SELECT");
        btnNewButton_3.addActionListener(e -> {
            selectFunctionality();
        });
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        btnNewButton_3.setBounds(10, 181, 151, 30);
        functionsPanel.add(btnNewButton_3);
    }

    private void addFunctionality() {
        currentTable = Table.valueOf(Objects.requireNonNull(tableSelect.getSelectedItem()).toString().toUpperCase());


        var additional = false;
        var restrictions = new LinkedHashMap<String, String>();

        if (lbl1.getText() != null && !textField.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_1, textField.getText().trim());
        }
        if (lbl2.getText() !=null && !textField_1.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_2, textField_1.getText().trim());
        }
        if (lbl3.getText() != null && !textField_2.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_3, textField_2.getText().trim());
        }
        if (lbl4.getText() != null && !textField_3.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_4, textField_3.getText().trim());
        }
        if (lbl5.getText() != null && !textField_4.getText().trim().isBlank()) {
            additional = true;
            restrictions.put(currentTable.column_5, textField_4.getText().trim());
        }

        var query = new StringBuilder("insert into " + currentTable.name());

        if (additional) {
            query
                    .append("(")
                    .append(restrictions.keySet()
                            .stream()
                            .map(s->String.format("`%s`", s))
                            .collect(Collectors.joining(", ")))
                    .append(")")
                    .append(" values (")
                    .append(restrictions.values()
                            .stream()
                            .map(s -> String.format("'%s'", s))
                            .collect(Collectors.joining(", ")))
                    .append(")");
        }

        try {
            var statement = databaseConnection.prepareStatement(query.toString());
            statement.executeUpdate();
            emptyFields();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void emptyFields(){
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        textField_4.setText("");
    }
}





