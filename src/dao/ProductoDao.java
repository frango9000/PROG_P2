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
import src.model.Producto;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class ProductoDao extends AbstractDao<Producto> {

    /**
     * Singleton lazy initialization
     */
    private static ProductoDao productoDao;

    private ProductoDao() {
        TABLE_NAME = "productos";
        ID_COL_NAME = "idProductos";
    }

    public static synchronized ProductoDao getInstance() {
        if (productoDao == null) {
            productoDao = new ProductoDao();
        }
        return productoDao;
    }

    @Override
    public Producto query(int id) {
        Producto producto = null;
        if (SessionDB.connect()) {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + id + "'";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                if (rs.next()) {
                    producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    table.put(producto.getIdProducto(), producto);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return producto;
    }

    @Override
    public HashMap<Integer, Producto> query(int... ids) {
        HashMap<Integer, Producto> productosTemp = new HashMap<>();
        if (SessionDB.connect() && ids.length > 0) {
            StringBuilder sql = new StringBuilder("SELECT * FROM " + TABLE_NAME + " WERE " + ID_COL_NAME + " IN( 0");
            for (int id : ids) {
                sql.append(", ").append(id);
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql.toString())) {
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    table.put(producto.getIdProducto(), producto);
                    productosTemp.put(producto.getIdProducto(), producto);
                }
            } catch (SQLException ex) {
                Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
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
            String sql = "SELECT * FROM " + TABLE_NAME + "";
            try (Statement ps = SessionDB.getConn().createStatement();
                    ResultSet rs = ps.executeQuery(sql)) {
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    table.put(producto.getIdProducto(), producto);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, sql, ex);
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
            String sql = "INSERT INTO " + TABLE_NAME + " VALUES(NULL, ?, ?, ?)";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, producto.getProducto());
                pstmt.setInt(2, producto.getIdCategoria());
                pstmt.setFloat(3, producto.getPrecio());
                rows = pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        producto.setIdProducto(rs.getInt(1));
                        table.put(producto.getIdProducto(), producto);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, sql, ex);
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
            String sql = "UPDATE " + TABLE_NAME + " SET producto = ?, precio = ?, idCategoria = ? WHERE " + ID_COL_NAME + " = ?";
            try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
                pstmt.setString(1, producto.getProducto());
                pstmt.setFloat(2, producto.getPrecio());
                pstmt.setInt(3, producto.getIdCategoria());
                pstmt.setInt(4, producto.getIdProducto());
                rows = pstmt.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int delete(Producto producto) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + producto.getId() + "'";
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(producto.getId());
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }
}
