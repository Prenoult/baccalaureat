package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBManager {
    private static final Logger LOG = Logger.getLogger(DBManager.class.getName());

    public static String URI;
    public static String USER;
    public static String PASSWORD;

    public static Connection CONNECTION = null;

    public static void connect() {
        if (CONNECTION == null){
            try {
                Class.forName( "com.mysql.jdbc.Driver" );
            } catch ( ClassNotFoundException e ) {
                LOG.warning(e.getMessage());
                return;
            }
        }
        try {
            CONNECTION = DriverManager.getConnection( URI, USER, PASSWORD );
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            return;
        }
        return;
    }

    public static void quit() {
        if ( CONNECTION != null )
            try {
                CONNECTION.close();
            } catch (SQLException e) {
            }
        CONNECTION = null;
    }
}
