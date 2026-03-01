package org.rocs.osd.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;

// This class is used create a connection to the database.
public class ConnectionHelper {

    // Database URL
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:oracleDB";

    // Oracle JDBC driver
    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    // Database username
    public static final String USERNAME = "rcosd";

    // Database password
    public static final String PASSWORD = "Changeme0";

    // This method is used to get a database connection
    public static Connection getConnection() {
        try {
            // This will load the database driver
            Class.forName(ORACLE_DRIVER).newInstance();
            // Return a connection to the database
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            // Throws an error if the connection fails
            throw new RuntimeException(e);
        }

    }

}
