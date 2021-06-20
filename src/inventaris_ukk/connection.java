/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventaris_ukk;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class connection {
    public static Connection connectFunction() throws SQLException{
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/inventaris_ukk";
        String user = "root";
        String pass = "";
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection Succeded");
        } catch (ClassNotFoundException ex) {
            System.out.println("Connection Failed");
        }
        
        return conn;
    }
}
