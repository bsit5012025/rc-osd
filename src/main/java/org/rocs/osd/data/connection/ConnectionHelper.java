package org.rocs.osd.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is used create a connection to the database.
 */
public final class ConnectionHelper {

    /**
     * Database URL.
     */
    public static final String URL =
            "jdbc:oracle:thin:@localhost:1521:oracleDB";

    /**
     * Oracle JDBC driver.
     */
    public static final String ORACLE_DRIVER =
            "oracle.jdbc.driver.OracleDriver";

    /**
     * Database username.
     */
    public static final String USERNAME = "rcosd";

    /**
     * Database password.
     */
    public static final String PASSWORD = "Changeme0";

    private ConnectionHelper() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * This method is used to get a database connection.
     *
     *  @return a Connection object representing the database connection.
     */
    public static Connection getConnection() {
        try {

            Class.forName(ORACLE_DRIVER).newInstance();


            return DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
