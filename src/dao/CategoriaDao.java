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
import src.model.Categoria;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public final class CategoriaDao implements Dao<Categoria> {

    private final HashMap<Integer, Categoria> categorias = new HashMap<>();

    /**
     * Singleton lazy initialization
     */
    private static Dao dao;

    private CategoriaDao() {
        dao.queryAll();
    }

    public static synchronized Dao getCategoriaDao() {
        if (dao == null) {
            dao = new CategoriaDao();
        }
        return dao;
    }

    @Override
    public HashMap<Integer, Categoria> queryAll() {
        categorias.clear();
        String sql = "SELECT * FROM categorias";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement()) {
                ResultSet rs = ps.executeQuery(sql);
                while (rs.next()) {
                    Categoria cat = new Categoria(rs.getInt(1), rs.getString(2));
                    categorias.put(cat.getIdCategoria(), cat);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return categorias;
    }

    @Override
    public Categoria get(int idCategoria) {
        return categorias.get(idCategoria);
    }

    @Override
    public HashMap<Integer, Categoria> getAll() {
        return categorias;
    }

    @Override
    public int insert(Categoria categoria) {
        String sql = "INSERT INTO categorias VALUES(NULL, ?)";
        String queryId = "SELECT idCategoria FROM categorias WHERE categoria = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql);
                PreparedStatement idpstmt = SessionDB.getConn().prepareStatement(queryId)) {
            pstmt.setString(1, categoria.getCategoria());
            rows = pstmt.executeUpdate();

            idpstmt.setString(1, categoria.getCategoria());
            ResultSet rs = idpstmt.executeQuery();
            if (rs.next()) {
                categorias.put(categoria.getIdCategoria(), categoria);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int update(Categoria categoria) {

        String sql = "UPDATE categorias SET categoria = ? WHERE idCategoria = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setString(1, categoria.getCategoria());
            pstmt.setInt(2, categoria.getIdCategoria());
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int delete(Categoria categoria) {
        return delete(categoria.getIdCategoria());
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM categorias WHERE idCategoria = '" + id + "'";
        SessionDB.connect();
        int rows = 0;
        try (Statement stmt = SessionDB.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }
}
