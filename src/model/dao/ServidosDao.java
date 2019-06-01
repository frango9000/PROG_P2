/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class ServidosDao {

    private ServidosDao() {
    }

    public static ArrayList<Integer> getIdsProductos(int idOrden) {
        ArrayList<Integer> productos = new ArrayList<>();
        String sql = "SELECT idProducto FROM servidos WHERE idOrden = '" + idOrden + "'";
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    productos.add(rs.getInt(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServidosDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return productos;
    }

    public static int insert(int idOrden, int idProducto) {
        String sql = "INSERT INTO servidos VALUES ('" + idOrden + "', '" + idProducto + "')";
        int rows = 0;
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidosDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    public static int delete(int idOrden, int idProducto) {
        String sql = "DELETE FROM servidos WHERE idOrden = '" + idOrden + "' AND idProducto = '" + idProducto + "' LIMIT 1";
        int rows = 0;
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidosDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }
}
