/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.model.Orden;
import src.model.Servido;
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
public final class ServidoDao extends AbstractDao<Servido> {

    /**
     * Singleton lazy initialization
     */
    private static ServidoDao dao;

    private ServidoDao() {
        TABLE_NAME = "servidos";
        ID_COL_NAME = "idServido";
    }

    public static synchronized ServidoDao getInstance() {
        if (dao == null) {
            dao = new ServidoDao();
        }
        return dao;
    }

    @Override
    public Servido query(int id) {
        Servido servido = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    table.put(servido.getIdServido(), servido);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return servido;
    }

    @Override
    public HashMap<Integer, Servido> query(ArrayList<Integer> ids) {
        HashMap<Integer, Servido> servidosTemp = new HashMap<>();
        if (SessionDB.connect() && ids.size() > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Servido servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    table.put(servido.getIdServido(), servido);
                    servidosTemp.put(servido.getIdServido(), servido);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return servidosTemp;
    }

    @Override
    public HashMap<Integer, Servido> queryAll() {
        table.clear();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Servido servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    table.put(servido.getIdServido(), servido);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Servido servido) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("INSERT INTO %s VALUES(NULL, ?, ?)", TABLE_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setInt(1, servido.getIdOrden());
                pstmt.setInt(2, servido.getIdProducto());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        servido.setIdServido(rs.getInt(1));
                        table.put(servido.getIdServido(), servido);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int update(Servido servido) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("UPDATE %s SET idOrden = ?, idProducto = ? WHERE %s = ?", TABLE_NAME, ID_COL_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setInt(1, servido.getIdOrden());
                pstmt.setInt(2, servido.getIdProducto());
                pstmt.setInt(3, servido.getIdServido());
                rows = pstmt.executeUpdate();
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateDao(Servido objectT) {
        return 0;
    }



    public int deleteSome(ArrayList<Servido> toDelete) {
        int rows = 0;
        if (SessionDB.connect() && toDelete.size() > 0) {
            StringBuilder sql = new StringBuilder("DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( 0");
            for (Servido servido : toDelete) {
                sql.append(", ").append(servido.getIdServido());
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement()) {
                rows = ps.executeUpdate(sql.toString());
                toDelete.forEach(e -> {
                    table.remove(e.getId());
                });
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public ArrayList<Servido> queryByOrden(Orden orden) {
        ArrayList<Servido> servidosOf = new ArrayList<>();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE idOrden = '%d'", TABLE_NAME, orden.getIdOrden());
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    Servido servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    servido.setProducto(ProductosDao.getInstance().get(servido.getIdProducto()));
                    servidosOf.add(servido);
                    table.put(servido.getId(), servido);
                }
                orden.setServidos(servidosOf);
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return servidosOf;
    }


    public ArrayList<ArrayList<Servido>> queryByOrden(ArrayList<Orden> ordenes) {
        ArrayList<ArrayList<Servido>> carritos = new ArrayList<>(ordenes.size());
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                for (Orden orden : ordenes) {
                    ArrayList<Servido> servidosOf = new ArrayList<>();
                    String sql = String.format("SELECT * FROM %s WHERE idOrden = '%d'", TABLE_NAME, orden.getIdOrden());
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        Servido servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                        servido.setProducto(ProductosDao.getInstance().get(servido.getIdProducto()));
                        servidosOf.add(servido);
                        table.put(servido.getId(), servido);
                    }
                    carritos.add(servidosOf);
                    orden.setServidos(servidosOf);
                    printSql(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return carritos;
    }

}
