package de.unidue.inf.is.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.db2.jcc.DB2Driver;


public final class DBUtil {

    private DBUtil() {
    }


    static {
        com.ibm.db2.jcc.DB2Driver driver = new DB2Driver();
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new Error("Laden des Datenbanktreiber nicht möglich");
        }
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        Properties properties = new Properties();
        properties.setProperty("user", "dbp59");
        properties.setProperty("password", "uvoe9ooz");
        Connection connection = DriverManager.getConnection("jdbc:db2://helios.is.inf.uni-due.de:50059/PROJECT:currentSchema = dbp59;", properties);
        return connection;
    }

    public static Connection getConnection(String database) throws SQLException {
        final String url = "jdbc:db2:" + database;
        return DriverManager.getConnection(url);
    }


    // Diese Methode benutzen, um sich von außerhalb der Uni mit der DB zu verbinden
    public static Connection getExternalConnection(String database) throws SQLException {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("settings.properties");
            // Zugangsdaten aus der Properties-Datei lesen
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String user = properties.getProperty("gruppenname");
        String pass = properties.getProperty("passwort");
        String rechnername = properties.getProperty("rechnername");

        String gruppennummer = user.substring(user.length() - 2, user.length());

        if (input != null) {
            final String url = "jdbc:db2://" + rechnername + ".is.inf.uni-due.de:500" + gruppennummer + "/" + database + ":currentSchema = " + user + ";";
            Connection connection = DriverManager.getConnection(url, user, pass);
            return connection;
        } else {
            final String url = "jdbc:db2://helios.is.inf.uni-due.de:50059/PROJECT:currentSchema=dbp59;";
            Connection connection = DriverManager.getConnection(url, "dbp59", "uvoe9ooz");
            return connection;
        }
    }


    public static boolean checkDatabaseExistsExternal(String database) {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = createConnection()) {
            exists = true;
        } catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        } catch(ClassNotFoundException c){

        }

        return exists;
    }


    public static boolean checkDatabaseExists(String database) {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getConnection(database)) {
            exists = true;
        } catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }

}
