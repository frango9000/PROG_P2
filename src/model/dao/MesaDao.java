/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import src.model.Mesa;
import src.model.SessionDB;

/**
 *
 * @author NarF
 */
public class MesaDao implements Dao<Mesa> {

    private final HashMap<Integer, Mesa> mesas = new HashMap<>();

    @Override
    public Map<Integer, Mesa> queryAll() {
        mesas.clear();
        String sql = "SELECT * FROM mesas";
        if (SessionDB.connect()) {
            try (Statement ps = SessionDB.getConn().createStatement()) {
                ResultSet rs = ps.executeQuery(sql);
                while (rs.next()) {
                    Mesa mesa = new Mesa(rs.getInt(1), rs.getString(2));
                    int capacidad = rs.getInt(3);
                    if (!rs.wasNull()) {
                        mesa.setCapacidad(capacidad);
                    }
                    mesas.put(mesa.getIdMesa(), mesa);
                }
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                SessionDB.close();
            }
        }
        return mesas;
    }

    @Override
    public Mesa get(int idMesa) {
        return mesas.get(idMesa);
    }

    @Override
    public Map<Integer, Mesa> getAll() {
        return mesas;
    }

    @Override
    public int insert(Mesa mesa) {
        String sql = "INSERT INTO mesas VALUES(NULL, ?, ?)";
        String queryId = "SELECT idMesa FROM mesas WHERE mesa = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql);
             PreparedStatement idpstmt =SessionDB.getConn().prepareStatement(queryId)) {
            pstmt.setString(1, mesa.getMesa());
            pstmt.setInt(2, mesa.getCapacidad());
            rows = pstmt.executeUpdate();
            
            idpstmt.setString(1, mesa.getMesa());
            ResultSet rs =  idpstmt.executeQuery();
            if(rs.next())
                mesa.setIdMesa(rs.getInt(1));
            
            mesas.put(mesa.getIdMesa(), mesa);
        } catch (SQLException ex) {
            Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int update(Mesa mesa) {
        String sql = "UPDATE mesas SET mesa = ?, capacidad = ? WHERE idMesa = ?";
        SessionDB.connect();
        int rows = 0;
        try (PreparedStatement pstmt = SessionDB.getConn().prepareStatement(sql)) {
            pstmt.setString(1, mesa.getMesa());
            pstmt.setInt(2, mesa.getCapacidad());
            pstmt.setInt(3, mesa.getIdMesa());
            rows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MesaDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }

    @Override
    public int delete(Mesa mesa) {
        return delete(mesa.getIdMesa());
    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM mesas WHERE idMesa = '" + id + "'";
        SessionDB.connect();
        int rows = 0;
        try (Statement stmt = SessionDB.getConn().createStatement()) {
            rows = stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(OrdenDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            SessionDB.close();
        }
        return rows;
    }
}
