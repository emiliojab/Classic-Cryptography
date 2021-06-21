/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 *
 * @author User
 */
public class DBHandler extends Configs {

    Connection dbConnection;

    public Connection getConnection() {

        Properties prop = new Properties();
        prop.setProperty("user", dbUser);
        prop.setProperty("password", dbPass);
        prop.setProperty("useSSL", "false");
        prop.setProperty("autoReconnect", "true");

        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            dbConnection = DriverManager.getConnection(connectionString,prop);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return dbConnection;
    }
}
