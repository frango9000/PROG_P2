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
import src.model.Servido;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class ServidoDao implements Dao<Servido> {

    private final HashMap<Integer, Servido> servidos = new HashMap<>();

    /**
     * Singleton lazy initialization
     */
    private static ServidoDao dao;

    private ServidoDao() {
    }

    public static synchronized ServidoDao getInstance() {
        if (dao == null) {
            dao = new ServidoDao();
        }
        return dao;
    }

    @Override
    public HashMap<Integer, Servido> queryAll() {
        servidos.clear();
        String sql = "SELECT * FROM servidos";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement()) {
                ResultSet rs = ps.executeQuery(sql);
                while (rs.next()) {
                    Servido servido = new Servido(rs.getInt(1), rs.getInt(2), rs.getInt(3));
                    servidos.put(servido.getIdServido(), servido);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return servidos;
    }

    @Override
    public Servido get(int idServido) {
        return servidos.get(idServido);
    }

    @Override
    public HashMap<Integer, Servido> getAll() {
        return servidos;
    }

    @Override
    public int insert(Servido servido) {
        String sql = "INSERT INTO servidos VALUES(NULL, ?, ?)";
        String queryId = "SELECT MAX(idServido) FROM servidos WHERE idOrden = ? and idProducto = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql);
             PreparedStatement idpstmt = SessionDB.getConn().prepareStatement(queryId)) {
            pstmt.setInt(1, servido.getIdOrden());
            pstmt.setInt(2, servido.getIdProducto());
            rows = pstmt.executeUpdate();

            idpstmt.setInt(1, servido.getIdOrden());
            idpstmt.setInt(2, servido.getIdProducto());
            ResultSet rs = idpstmt.executeQuery();
            if (rs.next()) {
                servido.setIdServido(rs.getInt(1));
                servidos.put(servido.getIdServido(), servido);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql+"\n"+queryId, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int update(Servido servido) {

        String sql = "UPDATE servidos SET idOrden = ?, idProducto = ? WHERE idServido = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setInt(1, servido.getIdOrden());
            pstmt.setInt(2, servido.getIdProducto());
            pstmt.setInt(3, servido.getIdServido());
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int delete(Servido servido) {
        return delete(servido.getIdServido());
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM servidos WHERE idServido = '" + id + "'";
        SessionDB.connect();
        int rows = 0;
        try (Statement stmt = SessionDB.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }
    
    
    public ArrayList<Integer> getIdsProductos(int idOrden) {
        ArrayList<Integer> productos = new ArrayList<>();
        String sql = "SELECT idProducto FROM servidos WHERE idOrden = '" + idOrden + "'";
        if (SessionDB.connect()) {
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    productos.add(rs.getInt(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(ServidoDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return productos;
    }

}
