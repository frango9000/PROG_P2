/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

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
                    table.put(servido.getId(), servido);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
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
            StringBuilder sql = new StringBuilder(String.format("SELECT * FROM %s WHERE %s IN( 0", TABLE_NAME, ID_COL_NAME));
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Servido servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    table.put(servido.getId(), servido);
                    servidosTemp.put(servido.getId(), servido);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
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
                    table.put(servido.getId(), servido);
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
                        servido.setId(rs.getInt(1));
                        table.put(servido.getId(), servido);
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
                pstmt.setInt(3, servido.getId());
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
    public int updateDao(Servido servido) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, servido.getId());
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    servido.setIdOrden(rs.getInt(2));
                    servido.setIdProducto(rs.getInt(3));
                    rows++;
                    table.put(servido.getId(), servido);
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

    public ArrayList<Integer> queryIdsByIdOrden(int idOrden) {
        ArrayList<Integer> idsServidos = new ArrayList<>();
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                String sql = String.format("SELECT %s FROM %s WHERE idOrden = '%d'", ID_COL_NAME, TABLE_NAME, idOrden);
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    idsServidos.add(rs.getInt(1));
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idsServidos;
    }

    public ArrayList<Integer> queryIdsByIdsOrden(ArrayList<Integer> idsOrden) {
        ArrayList<Integer> idsServidos = new ArrayList<>();
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                StringBuilder sql = new StringBuilder(String.format("SELECT %s FROM %s WHERE idOrden IN( 0", ID_COL_NAME, TABLE_NAME));
                for (int id : idsOrden) {
                    sql.append(", ").append(id);
                }
                sql.append(" )");
                ResultSet rs = stmt.executeQuery(sql.toString());
                while (rs.next()) {
                    idsServidos.add(rs.getInt(1));
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idsServidos;
    }

    public ArrayList<Servido> getByIdOrden(int idOrden) {
        return getSome(queryIdsByIdOrden(idOrden));
    }

}
