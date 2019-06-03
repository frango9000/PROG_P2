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
import java.util.ArrayList;
import java.util.HashMap;
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
    public Orden query(int id) {
        Orden orden = null;
        String sql = "SELECT * FROM ordenes WHERE idOrden = '" + id + "'";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    orden = new Orden(rs.getInt(1), rs.getString(2), rs.getFloat(4));
                    String cierre = rs.getString(3);
                    if (!rs.wasNull()) {
                        orden.setCierre(DateTimeFormat.dbStringToLocalDateTime(cierre));
                    }
                    ordenes.put(orden.getIdOrden(), orden);
                    System.out.println(orden.toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return orden;
    }

    @Override
    public ArrayList<Orden> query(int... ids) {
        ArrayList<Orden> ordenesTemp = new ArrayList<>();
        if (SessionDB.connect() && ids.length > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM ordenes WERE idOrden IN( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Orden orden = new Orden(rs.getInt(1), rs.getString(2), rs.getFloat(4));
                    String cierre = rs.getString(3);
                    if (!rs.wasNull()) {
                        orden.setCierre(DateTimeFormat.dbStringToLocalDateTime(cierre));
                    }
                    ordenes.put(orden.getIdOrden(), orden);
                    ordenesTemp.add(orden);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return ordenesTemp;
    }

    @Override
    public HashMap<Integer, Orden> queryAll() {
        ordenes.clear();
        String sql = "SELECT * FROM ordenes";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Orden orden = new Orden(rs.getInt(1), rs.getString(2), rs.getFloat(4));
                    String cierre = rs.getString(3);
                    if (!rs.wasNull()) {
                        orden.setCierre(DateTimeFormat.dbStringToLocalDateTime(cierre));
                    }
                    ordenes.put(orden.getIdOrden(), orden);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return ordenes;
    }

    @Override
    public Orden get(int idOrden) {
        if (ordenes.containsKey(idOrden)) {
            return ordenes.get(idOrden);
        } else {
            return query(idOrden);
        }

    }

    @Override
    public ArrayList<Orden> get(int... ids) {
        ArrayList<Orden> ordenesTemp = new ArrayList<>();
        ArrayList<Integer> idsToQuery = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (ordenes.containsKey(ids[i])) {
                ordenesTemp.add(ordenes.get(ids[i]));
            } else {
                idsToQuery.add(ids[i]);
            }
        }
        if (idsToQuery.size() > 0) {
            int[] ids2q = idsToQuery.stream().mapToInt(Integer::intValue).toArray();
            ordenesTemp.addAll(query(ids2q));
        }
        return ordenesTemp;
    }

    @Override
    public HashMap<Integer, Orden> getAll() {
        return ordenes;
    }

    @Override
    public int insert(Orden orden) {
        String sql = "INSERT INTO ordenes VALUES(NULL, ?, ?, ?)";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setString(1, orden.getAperturaToDbString());
            pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
            pstmt.setFloat(3, orden.getTotal());
            rows = pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    orden.setIdOrden(rs.getInt(1));
                    ordenes.put(orden.getIdOrden(), orden);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int update(Orden orden) {
        String sql = "UPDATE ordenes SET apertura = ?, cierre = ?, total = ? WHERE idOrden = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setString(1, orden.getAperturaToDbString());
            pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
            pstmt.setFloat(3, orden.getTotal());
            pstmt.setInt(4, orden.getIdOrden());
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
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
        String sql = "DELETE FROM ordenes WHERE idOrden = '" + id + "'";
        SessionDB.connect();
        int rows = 0;
        try (Statement stmt = SessionDB.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }
}
