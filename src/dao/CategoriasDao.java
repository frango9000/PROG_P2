/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.model.Categoria;
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
public final class CategoriasDao extends AbstractDao<Categoria> {

    /**
     * Singleton lazy initialization
     */
    private static CategoriasDao dao;

    private CategoriasDao() {
        TABLE_NAME = "categorias";
        ID_COL_NAME = "idCategoria";
    }

    public static synchronized CategoriasDao getInstance() {
        if (dao == null) {
            dao = new CategoriasDao();
        }
        return dao;
    }

    @Override
    public Categoria query(int id) {
        Categoria categoria = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    table.put(categoria.getId(), categoria);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return categoria;
    }

    @Override
    public HashMap<Integer, Categoria> query(ArrayList<Integer> ids) {
        HashMap<Integer, Categoria> categoriasTemp = new HashMap<>();
        if (SessionDB.connect() && ids.size() > 0) {
            StringBuilder sql = new StringBuilder(String.format("SELECT * FROM %s WHERE %s IN( 0", TABLE_NAME, ID_COL_NAME));
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    table.put(categoria.getId(), categoria);
                    categoriasTemp.put(categoria.getId(), categoria);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
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
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(rs.getInt(1), rs.getString(2));
                    table.put(categoria.getId(), categoria);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriasDao.class.getName()).log(Level.SEVERE, sql, ex);
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
            String sql = String.format("INSERT INTO %s VALUES(NULL, ?)", TABLE_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, categoria.getCategoria());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        categoria.setId(rs.getInt(1));
                        table.put(categoria.getId(), categoria);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriasDao.class.getName()).log(Level.SEVERE, sql, ex);
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
            String sql = String.format("UPDATE %s SET categoria = ? WHERE %s = ?", TABLE_NAME, ID_COL_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, categoria.getCategoria());
                pstmt.setInt(2, categoria.getId());
                rows = pstmt.executeUpdate();
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriasDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateDao(Categoria categoria) {
        int rows = 0;
        if (categoria.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, categoria.getId());
                try (Statement ps = SessionDB.getConn().createStatement();
                        ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        categoria.setCategoria(rs.getString(2));
                        rows++;
                        table.put(categoria.getId(), categoria);
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
