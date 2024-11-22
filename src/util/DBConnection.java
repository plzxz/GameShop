package util;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection conn;

    public DBConnection() {
        connect();
    }
    
    public static void connect() { //static Connection connect() 

        try {
            Path currentRelativePath = Paths.get("");
            String dbPath = currentRelativePath.toAbsolutePath().toString() + "/GameShopDatabase.accdb";
            String url = "jdbc:ucanaccess://" + dbPath;

            conn = DriverManager.getConnection(url);
            System.out.println("Connection established"); // change
        }catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
    
    public static Connection connected() { //static Connection connect() 

        Connection acon = null;
        
        try {
            Path currentRelativePath = Paths.get("");
            String dbPath = currentRelativePath.toAbsolutePath().toString() + "/GameShopDatabase.accdb";
            String url = "jdbc:ucanaccess://" + dbPath;

            acon = DriverManager.getConnection(url);
            System.out.println("Connection established"); // change
        }catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        
        return acon;
    }
    
}
