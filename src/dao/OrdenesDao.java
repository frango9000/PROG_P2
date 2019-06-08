/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.model.Orden;
import src.model.SessionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NarF
 */
public final class OrdenesDao extends AbstractDao<Orden> {

    /**
     * Singleton lazy initialization
     */
    private static OrdenesDao dao;

    public static synchronized OrdenesDao getInstance() {
        if (dao == null) {
            dao = new OrdenesDao();
        }
        return dao;
    }

    private OrdenesDao() {
        TABLE_NAME = "ordenes";
        ID_COL_NAME = "idOrden";
    }

    @Override
    public Orden query(int id) {
        Orden orden = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    orden = new Orden(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                    table.put(orden.getId(), orden);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return orden;
    }

    @Override
    public HashMap<Integer, Orden> query(ArrayList<Integer> ids) {
        HashMap<Integer, Orden> ordenesTemp = new HashMap<>();
        if (SessionDB.connect() && ids.size() > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN ( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Orden orden = new Orden(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                    table.put(orden.getId(), orden);
                    ordenesTemp.put(orden.getId(), orden);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
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
            SessionDB.setAutoclose(false);
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Orden orden = new Orden(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4));
                    table.put(orden.getId(), orden);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.setAutoclose(true);
            }
        }
        return table;
    }

    @Override
    public int insert(Orden orden) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("INSERT INTO %s VALUES(NULL, ?, ?, ?)", TABLE_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, orden.getAperturaToDbString());
                pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
                pstmt.setFloat(3, orden.getTotal());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orden.setId(rs.getInt(1));
                        table.put(orden.getId(), orden);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
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
            String sql = String.format("UPDATE %s SET apertura = ?, cierre = ?, total = ? WHERE %s = ?", TABLE_NAME, ID_COL_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, orden.getAperturaToDbString());
                pstmt.setString(2, orden.isClosed() ? null : orden.getCierreToDbString());
                pstmt.setFloat(3, orden.getTotal());
                pstmt.setInt(4, orden.getId());
                rows = pstmt.executeUpdate();
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateDao(Orden orden) {
        int rows = 0;
        if (orden.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, orden.getId());
                try (Statement ps = SessionDB.getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        orden.setAperturaDB(rs.getString(2));
                        orden.setCierreDB(rs.getString(3));
                        orden.setTotal(rs.getFloat(4));
                        rows++;
                        table.put(orden.getId(), orden);
                    }
                    printSql(sql);
                } catch (SQLException ex) {
                    Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
                } finally {
                    SessionDB.close();
                }
            }
        }
        return rows;
    }
}
