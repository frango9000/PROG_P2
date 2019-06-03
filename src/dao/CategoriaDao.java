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
import src.model.Categoria;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class CategoriaDao extends AbstractDao<Categoria> {

    /**
     * Singleton lazy initialization
     */
    private static CategoriaDao dao;

    private CategoriaDao() {
        TABLE_NAME = "categorias";
        ID_COL_NAME = "idCategoria";
    }

    public static synchronized CategoriaDao getInstance() {
        if (dao == null) {
            dao = new CategoriaDao();
        }
        return dao;
    }

    @Override
    public Categoria query(int id) {
        Categoria categoria = null;
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + id + "'";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    table.put(categoria.getIdCategoria(), categoria);
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
        return categoria;
    }

    @Override
    public HashMap<Integer, Categoria> query(int... ids) {
        HashMap<Integer, Categoria> categoriasTemp = new HashMap<>();
        if (SessionDB.connect() && ids.length > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    table.put(categoria.getIdCategoria(), categoria);
                    categoriasTemp.put(categoria.getIdCategoria(), categoria);
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return categoriasTemp;
    }

    @Override
    public HashMap<Integer, Categoria> queryAll() {
        table.clear();
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME + "";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    table.put(categoria.getIdCategoria(), categoria);
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Categoria categoria) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "INSERT INTO " + TABLE_NAME + " VALUES(NULL, ?)";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, categoria.getCategoria());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setIdCategoria(rs.getInt(1));
                        table.put(categoria.getIdCategoria(), categoria);
                    }
                }
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int update(Categoria categoria) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "UPDATE " + TABLE_NAME + " SET categoria = ? WHERE " + ID_COL_NAME + " = ?";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, categoria.getCategoria());
                pstmt.setInt(2, categoria.getIdCategoria());
                rows = pstmt.executeUpdate();
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int delete(Categoria categoria) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + categoria.getId() + "'";
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(categoria.getId());
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

}
