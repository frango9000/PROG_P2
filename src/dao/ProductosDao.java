/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.model.Producto;
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
public final class ProductosDao extends AbstractDao<Producto> {

    /**
     * Singleton lazy initialization
     */
    private static ProductosDao productoDao;

    private ProductosDao() {
        TABLE_NAME = "productos";
        ID_COL_NAME = "idProducto";
    }

    public static synchronized ProductosDao getInstance() {
        if (productoDao == null) {
            productoDao = new ProductosDao();
        }
        return productoDao;
    }

    @Override
    public Producto query(int id) {
        Producto producto = null;
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, id);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    table.put(producto.getId(), producto);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return producto;
    }

    @Override
    public HashMap<Integer, Producto> query(ArrayList<Integer> ids) {
        HashMap<Integer, Producto> productosTemp = new HashMap<>();
        if (SessionDB.connect() && ids.size() > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    table.put(producto.getId(), producto);
                    productosTemp.put(producto.getId(), producto);
                }
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(OrdenesDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return productosTemp;
    }

    @Override
    public HashMap<Integer, Producto> queryAll() {
        table.clear();
        if (SessionDB.connect()) {
            String sql = String.format("SELECT * FROM %s", TABLE_NAME);
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    table.put(producto.getId(), producto);
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ProductosDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return table;
    }

    @Override
    public int insert(Producto producto) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("INSERT INTO %s VALUES(NULL, ?, ?, ?)", TABLE_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, producto.getProducto());
                pstmt.setFloat(2, producto.getPrecio());
                pstmt.setInt(3, producto.getIdCategoria());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setId(rs.getInt(1));
                        table.put(producto.getId(), producto);
                    }
                }
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ProductosDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int update(Producto producto) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("UPDATE %s SET producto = ?, precio = ?, idCategoria = ? WHERE %s = ?", TABLE_NAME, ID_COL_NAME);
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, producto.getProducto());
                pstmt.setFloat(2, producto.getPrecio());
                pstmt.setInt(3, producto.getIdCategoria());
                pstmt.setInt(4, producto.getId());
                rows = pstmt.executeUpdate();
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ProductosDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int updateDao(Producto producto) {
        int rows = 0;
        if (producto.getId() > 0) {
            if (SessionDB.connect()) {
                String sql = String.format("SELECT * FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, producto.getId());
                try (Statement ps = SessionDB.getConn().createStatement();
                     ResultSet rs = ps.executeQuery(sql)) {
                    if (rs.next()) {
                        producto.setProducto(rs.getString(2));
                        producto.setPrecio(rs.getFloat(3));
                        producto.setIdCategoria(rs.getInt(4));
                        table.put(producto.getId(), producto);
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
