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
import src.control.MainFrame;
import src.model.Mesa;
import src.model.Orden;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class MesaDao extends AbstractDao<Mesa> {

    /**
     * Singleton lazy initialization
     */
    private static MesaDao dao;

    private MesaDao() {
        TABLE_NAME = "mesas";
        ID_COL_NAME = "idMesa";

    }

    public static synchronized MesaDao getInstance() {
        if (dao == null) {
            dao = new MesaDao();
        }
        return dao;
    }

    @Override
    public Mesa query(int id) {
        Mesa mesa = null;
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + id + "'";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    mesa = new Mesa(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    if (mesa.getIdOrden() > 0) {
                        mesa.setOrden(OrdenDao.getInstance().get(mesa.getId()));
                    }
                    table.put(mesa.getIdMesa(), mesa);
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return mesa;
    }

    @Override
    public HashMap<Integer, Mesa> query(int... ids) {
        HashMap<Integer, Mesa> mesasTempHashMap = new HashMap<>();
        if (SessionDB.connect() && ids.length > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Mesa mesa = new Mesa(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    if (mesa.getIdOrden() > 0) {
                        mesa.setOrden(OrdenDao.getInstance().get(mesa.getId()));
                    }
                    table.put(mesa.getIdMesa(), mesa);
                    mesasTempHashMap.put(mesa.getIdOrden(), mesa);
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql.toString());
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return mesasTempHashMap;
    }

    @Override
    public HashMap<Integer, Mesa> queryAll() {
        table.clear();
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME;
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Mesa mesa = new Mesa(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    if (mesa.getIdOrden() > 0) {
                        mesa.setOrden(OrdenDao.getInstance().get(mesa.getIdOrden()));
                    }
                    table.put(mesa.getIdMesa(), mesa);
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Mesa mesa) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "INSERT INTO " + TABLE_NAME + " VALUES(NULL, ?, ?, ?)";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, mesa.getMesa());
                pstmt.setInt(2, mesa.getCapacidad());
                pstmt.setInt(3, mesa.getIdOrden());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        mesa.setIdMesa(rs.getInt(1));
                        table.put(mesa.getIdMesa(), mesa);
                    }
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int update(Mesa mesa) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "UPDATE " + TABLE_NAME + " SET mesa = ?, capacidad = ?, idOrden = ? WHERE " + ID_COL_NAME + " = ?";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, mesa.getMesa());
                pstmt.setInt(2, mesa.getCapacidad());
                pstmt.setInt(3, mesa.getIdOrden());
                pstmt.setInt(4, mesa.getIdMesa());
                rows = pstmt.executeUpdate();
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int delete(Mesa mesa) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + mesa.getId() + "'";
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(mesa.getId());
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public ArrayList<Integer> getIdsOrdenesActivas() {
        ArrayList<Integer> activas = new ArrayList<>();
        if (SessionDB.connect()) {
            String sql = "SELECT idOrden FROM " + TABLE_NAME + " WHERE idOrden != 0";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    activas.add(rs.getInt(1));
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return activas;
    }

    public ArrayList<Orden> getOrdenesActivas() {
        int[] ids = getIdsOrdenesActivas().stream().mapToInt(Integer::intValue).toArray();
        return OrdenDao.getInstance().getSome(ids);
    }

}
