/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.control.MainFrame;
import src.model.Mesa;
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
public final class MesasDao extends AbstractDao<Mesa> {

    /**
     * Singleton lazy initialization
     */
    private static MesasDao dao;

    private MesasDao() {
        TABLE_NAME = "mesas";
        ID_COL_NAME = "idMesa";

    }

    public static synchronized MesasDao getInstance() {
        if (dao == null) {
            dao = new MesasDao();
        }
        return dao;
    }

    @Override
    public Mesa query(int id) {
        Mesa mesa = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    mesa = new Mesa(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    table.put(mesa.getId(), mesa);
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
                    table.put(mesa.getId(), mesa);
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
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Mesa mesa = new Mesa(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                    table.put(mesa.getId(), mesa);
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesasDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Mesa mesa) {
        int newId = 0;
        if (SessionDB.connect()) {
            String sql = String.format("INSERT INTO %s VALUES(NULL, ?, ?, ?)", TABLE_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, mesa.getMesa());
                pstmt.setInt(2, mesa.getCapacidad());
                pstmt.setInt(3, mesa.getIdOrden());
                newId = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        newId = rs.getInt(1);
                        mesa.setId(newId);
                        table.put(mesa.getId(), mesa);
                    }
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesasDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return newId;
    }

    @Override
    public int update(Mesa mesa) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("UPDATE %s SET mesa = ?, capacidad = ?, idOrden = ? WHERE %s = ?", TABLE_NAME, ID_COL_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, mesa.getMesa());
                pstmt.setInt(2, mesa.getCapacidad());
                pstmt.setInt(3, mesa.getIdOrden());
                pstmt.setInt(4, mesa.getId());
                rows = pstmt.executeUpdate();
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesasDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateDao(Mesa mesa) {
        int rows = 0;
        if (mesa.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, mesa.getId());
                try (Statement ps = SessionDB.getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        mesa.setMesa(rs.getString(2));
                        mesa.setCapacidad(rs.getInt(3));
                        mesa.setIdOrden(rs.getInt(4));
                        table.put(mesa.getId(), mesa);
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
        }
        return rows;
    }

    public ArrayList<Integer> getIdsOrdenesActivas() {
        ArrayList<Integer> activas = new ArrayList<>();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT idOrden FROM %s WHERE idOrden != 0", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    activas.add(rs.getInt(1));
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MesasDao.class.getName()).log(Level.SEVERE, sql, ex);
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
