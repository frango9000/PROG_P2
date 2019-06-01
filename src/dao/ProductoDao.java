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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.model.Producto;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class ProductoDao implements Dao<Producto> {

    private final HashMap<Integer, Producto> productos = new HashMap<>();

    /**
     * Singleton lazy initialization
     */
    private static Dao productoDao;

    private ProductoDao() {
        productoDao.queryAll();
    }

    public static synchronized Dao getProductoDao() {
        if (productoDao == null) {
            productoDao = new ProductoDao();
        }
        return productoDao;
    }

    @Override
    public HashMap<Integer, Producto> queryAll() {
        productos.clear();
        String sql = "SELECT * FROM productos";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement()) {
                ResultSet rs = ps.executeQuery(sql);
                while (rs.next()) {
                    Producto producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4));
                    productos.put(producto.getIdProducto(), producto);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return productos;
    }

    @Override
    public Producto get(int idProducto) {
        return productos.get(idProducto);
    }

    @Override
    public HashMap<Integer, Producto> getAll() {
        return productos;
    }

    @Override
    public int insert(Producto producto) {
        String sql = "INSERT INTO productos VALUES(NULL, ?, ?, ?)";
        String queryId = "SELECT idProducto FROM productos WHERE producto = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql);
                PreparedStatement idpstmt = SessionDB.getConn().prepareStatement(queryId)) {
            pstmt.setString(1, producto.getProducto());
            pstmt.setInt(2, producto.getIdCategoria());
            pstmt.setFloat(3, producto.getPrecio());
            rows = pstmt.executeUpdate();

            idpstmt.setString(1, producto.getProducto());
            ResultSet rs = idpstmt.executeQuery();
            if (rs.next()) {
                producto.setIdProducto(rs.getInt(1));
            }

            productos.put(producto.getIdProducto(), producto);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int update(Producto producto) {
        String sql = "UPDATE productos SET producto = ?, precio = ?, idCategoria = ? WHERE idProducto = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setString(1, producto.getProducto());
            pstmt.setFloat(2, producto.getPrecio());
            pstmt.setInt(3, producto.getIdCategoria());
            pstmt.setInt(4, producto.getIdProducto());
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int delete(Producto producto) {
        return delete(producto.getIdProducto());
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM productos WHERE idProducto = '" + id + "'";
        SessionDB.connect();
        int rows = 0;
        try (Statement stmt = SessionDB.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }
}
