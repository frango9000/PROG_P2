/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.model.DateTimeFormat;
import src.model.Orden;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class OrdenDao implements Dao<Orden> {

    private final HashMap<Integer, Orden> ordenes = new HashMap<>();

    /**
     * Singleton lazy initialization
     */
    private static OrdenDao dao;

    private OrdenDao() {
    }

    public static synchronized OrdenDao getInstance() {
        if (dao == null) {
            dao = new OrdenDao();
        }
        return dao;
    }

    @Override
    public HashMap<Integer, Orden> queryAll() {
        ordenes.clear();
        String sql = "SELECT * FROM ordenes";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement()) {
                ResultSet rs = ps.executeQuery(sql);
                while (rs.next()) {
                    Orden orden = new Orden(rs.getInt(1), rs.getString(2), rs.getFloat(4), rs.getInt(5));
                    String cierre = rs.getString(3);
                    if (!rs.wasNull()) {
                        orden.setCierre(DateTimeFormat.dbStringToLocalDateTime(cierre));
                    }
                    ordenes.put(orden.getIdOrden(), orden);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return ordenes;
    }

    @Override
    public Orden get(int idOrden) {
        return ordenes.get(idOrden);
    }

    @Override
    public HashMap<Integer, Orden> getAll() {
        return ordenes;
    }

    @Override
    public int insert(Orden orden) {
        String sql = "INSERT INTO ordenes VALUES(NULL, ?, ?, ?, ?)";
        String queryId = "SELECT idOrden FROM ordenes WHERE apertura = ?, and idMesa = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql);
                PreparedStatement idpstmt = SessionDB.getConn().prepareStatement(queryId)) {
            pstmt.setString(1, orden.getAperturaToDbString());
            pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
            pstmt.setFloat(3, orden.getTotal());
            pstmt.setInt(4, orden.getIdMesa());
            rows = pstmt.executeUpdate();

            idpstmt.setString(1, orden.getAperturaToDbString());
            idpstmt.setInt(2, orden.getIdMesa());
            ResultSet rs = idpstmt.executeQuery();
            if (rs.next()) {
                orden.setIdOrden(rs.getInt(1));
            }

            ordenes.put(orden.getIdOrden(), orden);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int update(Orden orden) {
        String sql = "UPDATE ordenes SET apertura = ?, cierre = ?, total = ?, idMesa = ? WHERE idOrden = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setString(1, orden.getAperturaToDbString());
            pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
            pstmt.setFloat(3, orden.getTotal());
            pstmt.setInt(4, orden.getIdMesa());
            pstmt.setInt(5, orden.getIdOrden());
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int delete(Orden orden) {
        return delete(orden.getIdOrden());
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM mesas WHERE idMesa = '" + id + "'";
        SessionDB.connect();
        int rows = 0;
        try (Statement stmt = SessionDB.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }
}
