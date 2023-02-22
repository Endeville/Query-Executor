package GUI;


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class GUI extends JFrame {

    private JTextArea query;
    private JPanel panel;
    private JLabel result;
    private JButton runBtn;
    private BorderLayout layout;
    private Connector connector;
    public GUI() throws SQLException {
        super("Query window");

        panel=new JPanel(new GridLayout(50, 100));
        layout=new BorderLayout();
        panel.setLayout(layout);

        query=new JTextArea(15 , 40);

        query.setWrapStyleWord(true);
        query.setLineWrap(true);


        panel.add(query, BorderLayout.PAGE_START);

        result=new JLabel("RESULT WILL APPEAR HERE");
        result.setSize(300, 100);
        panel.add(result);

        runBtn=new JButton("RUN");
        runBtn.setSize(100, 50);
        panel.add(runBtn, BorderLayout.PAGE_END);

        connector=new Connector();
        runBtn.addActionListener(e-> {
            try {
                result.setText(connector.executeSelect(query.getText().trim()));
            } catch (SQLException ex) {
                result.setText(ex.getMessage());
            }
        });

        pack();
        add(panel);
    }


    private static class Connector {
        public static final String CONNECTION_STRING="jdbc:mysql://localhost:3306/telecommunication_companies";
        private static Connection CONNECTION;

        public Connector() throws SQLException {
            CONNECTION= getConnection();
        }

        public String executeSelect(String input) throws SQLException {
            var result= CONNECTION.prepareStatement(input)
                    .executeQuery();

            var string=new StringBuilder();

            while(result.next()){
                string.append(result.getString("first_name"));
            }

            return string.toString();
        }

        private static Connection getConnection() throws SQLException {
            String username="root";
            String password="12345";

            Properties properties=new Properties();
            properties.setProperty("user", username);
            properties.setProperty("password", password);

            return DriverManager.getConnection(CONNECTION_STRING, properties);
        }
    }
}