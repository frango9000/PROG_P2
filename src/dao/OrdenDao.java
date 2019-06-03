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
import java.util.logging.Level;
import java.util.logging.Logger;
import src.model.DateTimeFormat;
import src.model.Orden;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class OrdenDao extends AbstractDao<Orden> {

    /**
     * Singleton lazy initialization
     */
    private static OrdenDao dao;

    private OrdenDao() {
        TABLE_NAME = "ordenes";
        ID_COL_NAME = "idOrden";
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
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + id + "'";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    orden = new Orden(rs.getInt(1), rs.getString(2), rs.getFloat(4));
                    String cierre = rs.getString(3);
                    if (!rs.wasNull()) {
                        orden.setCierre(DateTimeFormat.dbStringToLocalDateTime(cierre));
                    }
                    table.put(orden.getIdOrden(), orden);
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
    public HashMap<Integer, Orden> query(int... ids) {
        HashMap<Integer, Orden> ordenesTemp = new HashMap<>();
        if (SessionDB.connect() && ids.length > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN ( 0");
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
                    table.put(orden.getIdOrden(), orden);
                    ordenesTemp.put(orden.getIdOrden(), orden);
                    System.out.println(orden);
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
        table.clear();
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME + "";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Orden orden = new Orden(rs.getInt(1), rs.getString(2), rs.getFloat(4));
                    String cierre = rs.getString(3);
                    if (!rs.wasNull()) {
                        orden.setCierre(DateTimeFormat.dbStringToLocalDateTime(cierre));
                    }
                    table.put(orden.getIdOrden(), orden);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Orden orden) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "INSERT INTO " + TABLE_NAME + " VALUES(NULL, ?, ?, ?)";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, orden.getAperturaToDbString());
                pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
                pstmt.setFloat(3, orden.getTotal());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orden.setIdOrden(rs.getInt(1));
                        table.put(orden.getIdOrden(), orden);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int update(Orden orden) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "UPDATE " + TABLE_NAME + " SET apertura = ?, cierre = ?, total = ? WHERE " + ID_COL_NAME + " = ?";
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
        }
        return rows;
    }

    @Override
    public int delete(Orden orden) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + orden.getId() + "'";
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(orden.getId());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

}
