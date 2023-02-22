

import GUI.GUI;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        var gui=new GUI();

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setLocationByPlatform(true);
        gui.setSize(500, 400);
        gui.setVisible(true);
    }
}