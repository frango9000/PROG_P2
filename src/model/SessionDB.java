package src.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Session se encagra de mantener la informacion de una conexion a una DB
 * especifica, Para multiples mases de datos crearemos multiples objetos
 * SessionDB
 *
 * @author fsancheztemprano
 */
public abstract class SessionDB {

    private static Connection conn;
    private static final String DB_URL = "jdbc:sqlite:PROG_P2/resources/host.db";
    private static final File DB_FILE = new File(DB_URL);

    /**
     * Getter para la clase Conexion
     *
     * @return Conexion
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * devuelve true si, el archivo con el que fue inicializado esta clase,
     * existe
     *
     * @return BOOLEAN
     */
    public static boolean exists() {
        return DB_FILE.exists();
    }

    /**
     * establece la conexion a la DB
     * 
     * @return true si la conexion fue establecida correctamente
     */
    public static boolean connect() {
        boolean success = false;
        conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connection to " + conn.getMetaData().getDriverName() + " has been established.");
            success = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return success;
    }

    /**
     * Finaliza una conexion a la DB
     */
    public static void close() {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Connection has been terminated.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Devuelve el numero de tablas en una DB
     *
     * @return integer: el numero de tablas en la base de datos
     */
    public static int numOfTables() {
        String sql = "SELECT name FROM  sqlite_master  WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
        int count = 0;
        connect();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            count = rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close();
        }
        return count;
    }

    /**
     * Devuelve Una lista con los nombres de las tablas en una DB
     *
     * @return
     */
    public static ArrayList<String> listTables() {
        String sql = "SELECT name FROM  sqlite_master  WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
        ArrayList<String> tableNames = new ArrayList<>();
        connect();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tableNames.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            close();
        }
        return tableNames;
    }

    /**
     * println de la lista de tablas de una DB
     */
    public static void printTables() {
        ArrayList<String> tablenames = listTables();
        tablenames.forEach((name) -> System.out.println(name));
    }
}
