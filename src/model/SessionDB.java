package src.model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.control.MainFrame;

/**
 * Clase Session se encagra de mantener la informacion de una conexion a una DB
 * especifica, Para multiples mases de datos crearemos multiples objetos
 * SessionDB
 *
 * @author fsancheztemprano
 */
public final class SessionDB {

    private static Connection conn;
    private static String dbUrl = "jdbc:sqlite:" + "src/src/resources/host.db";
    private static File dbFile = new File(dbUrl.substring(12));
    private static boolean autoclose = true;

    private SessionDB() {
    }

    /**
     * Getter para la clase Conexion
     *
     * @return Conexion
     */
    public static Connection getConn() {
        return conn;
    }

    public static void setDbUrl(String url) {
        dbUrl = "jdbc:sqlite:" + url;
        dbFile = new File(url);
    }

    public static void setDbFile(File dbFile) {
        SessionDB.dbFile = dbFile;
        dbUrl = "jdbc:sqlite:" + dbFile.getAbsolutePath();
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static File getDbFile() {
        return dbFile;
    }

    /**
     * devuelve true si, el archivo con el que fue inicializado esta clase,
     * existe
     *
     * @return BOOLEAN
     */
    public static boolean exists() {
        return dbFile.exists();
    }

    /**
     * establece la conexion a la DB
     *
     * @return true si la conexion fue establecida correctamente
     */
    public static boolean connect() {
        boolean success = false;
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(dbUrl);
                if (MainFrame.SQL_CONN) {
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " has been established.");
                }
            } else {
                if (MainFrame.SQL_CONN) {
                    System.out.println("Connection to " + conn.getMetaData().getDriverName() + " already active.");
                }
            }
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
            if (autoclose) {
                if (conn != null || !conn.isClosed()) {
                    conn.close();
                    if (MainFrame.SQL_CONN) {
                        System.out.println("Connection has been closed.");
                    }
                } else {
                    if (MainFrame.SQL_CONN) {
                        System.out.println("Connection was already closed.");
                    }
                }
            } else {
                System.out.println("Keeping conection alive.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * este metodo es para consultas en secuencia, al ejecutarlo
     * [setAutoclose(false)] se conecta a la DB y despues de todos los accesos a
     * la BD debe ser llamado nuevamente para asegurar la desconexion.
     *
     * Ejemplo SessionDB.setAutoclose(false);
     *
     * CategoriaDao.getInstance().queryAll();
     * ProductoDao.getInstance().queryAll(); categorizarProductos();
     * ArrayList<Orden> ordenesActivas =
     * MesaDao.getInstance().getOrdenesActivas();
     * ServidoDao.getInstance().query(ordenesActivas);
     * MesaDao.getInstance().queryAll();
     *
     * SessionDB.setAutoclose(true);
     *
     * @param autoclose
     */
    public static void setAutoclose(boolean autoclose) {
        if (autoclose) {
            close();
        } else {
            connect();
        }
        SessionDB.autoclose = autoclose;
        if (MainFrame.SQL_CONN) {
            System.out.println("Autoclose? " + autoclose);
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
            if (MainFrame.SQL_DEBUG) {
                System.out.println(sql);
            }
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
            if (MainFrame.SQL_DEBUG) {
                System.out.println(sql);
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

    public static int crearTablas() {
        int rows = 0;
        File sqlFile = new File("src/src/model/Tables.sql");
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            SessionDB.connect();
            for (String cmd : cmds) {
                try (Statement stmt = SessionDB.getConn().createStatement()) {
                    rows += stmt.executeUpdate(cmd.trim());
                    if (MainFrame.SQL_DEBUG) {
                        System.out.println(cmd);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, cmd, ex);
                }
            }
        } catch (FileNotFoundException ignored) {
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    public static int insertarDemoData() {
        int rows = 0;
        File sqlFile = new File("src/src/model/DemoData.sql");
        StringBuilder sqlcmd = new StringBuilder();
        try (Scanner scan = new Scanner(new BufferedInputStream(new FileInputStream(sqlFile)))) {
            while (scan.hasNext()) {
                sqlcmd.append(scan.nextLine()).append("\n");
            }
            String multicmd = sqlcmd.toString();
            String[] cmds = multicmd.split(";");
            SessionDB.connect();
            for (String cmd : cmds) {
                try (Statement stmt = SessionDB.getConn().createStatement()) {
                    stmt.executeUpdate(cmd.trim());
                    if (MainFrame.SQL_DEBUG) {
                        System.out.println(cmd);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SessionDB.class.getName()).log(Level.SEVERE, cmd, ex);
                }
            }
        } catch (FileNotFoundException ignored) {
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    /**
     * devuelve true si la estructura de la DB activa es valida (coincide con la
     * inicializada)
     *
     * @return true si es valida
     */
    public static boolean isValid() {
        ArrayList<String> tables = listTables();
        StringBuilder tablesString = new StringBuilder();
        tables.forEach(cnsmr -> tablesString.append(cnsmr).append("\n"));
        String model = "categorias\n"
                + "ordenes\n"
                + "mesas\n"
                + "productos\n"
                + "servidos\n";
        return model.matches(tablesString.toString());
    }
}
