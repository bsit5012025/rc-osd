package org.rocs.osd.data.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    public static final String URL = "jdbc:oracle:thin:@localhost:1521:orclDB";

    public static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";

    public static final String USERNAME = "rcosd";

    public static final String PASSWORD = "Changeme0";


    public static Connection getConnection() {
        try {
            Class.forName(ORACLE_DRIVER).newInstance();
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
