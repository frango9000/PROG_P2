/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.control.MainFrame;
import src.model.IPersistable;
import src.model.SessionDB;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NarF
 * @param <T>
 */
public abstract class AbstractDao<T extends IPersistable> implements IDao<T> {

    protected final HashMap<Integer, T> table = new HashMap<>();
    protected String TABLE_NAME;
    protected String ID_COL_NAME;

    @Override
    public T get(int id) {
        if (id > 0) {
            if (table.containsKey(id)) {
                return table.get(id);
            } else {
                return query(id);
            }
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<T> getSome(ArrayList<Integer> ids) {
        ArrayList<T> list = new ArrayList<>();
        if (ids.size() > 0) {
            ArrayList<Integer> idsToQuery = new ArrayList<>();
            for (int id : ids) {
                if (!table.containsKey(id)) {
                    idsToQuery.add(id);
                }
            }
            if (ids.size() > 0) {
                query(idsToQuery);
            }
            for (int id : ids) {
                list.add(table.get(id));
            }
        }
        return list;
    }

    @Override
    public HashMap<Integer, T> getMapOf(ArrayList<Integer> ids) {
        HashMap<Integer, T> filteredHashMap = new HashMap<>();
        if (ids.size() > 0) {
            ArrayList<T> objs = getSome(ids);
            for (T t : objs) {
                filteredHashMap.put(t.getId(), t);
            }
        }
        return filteredHashMap;
    }

    @Override
    public HashMap<Integer, T> getCache() {
        return table;
    }

    @Override
    public int delete(T t) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = String.format("DELETE FROM %s WHERE %s = '%d'", TABLE_NAME, ID_COL_NAME, t.getId());
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(t.getId());
                printSql(sql);
            } catch (SQLException ex) {
                Logger.getLogger(AbstractDao.class.getName()).log(Level.SEVERE, sql, ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    @Override
    public int delete(int id) {
        return delete(table.get(id));
    }

    @Override
    public int deleteSome(ArrayList<T> toDelete) {
        int rows = 0;
        if (SessionDB.connect() && toDelete.size() > 0) {
            StringBuilder sql = new StringBuilder("DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " IN( 0");
            for (T t : toDelete) {
                sql.append(", ").append(t.getId());
            }
            sql.append(" )");
            try (Statement ps = SessionDB.getConn().createStatement()) {
                rows = ps.executeUpdate(sql.toString());
                toDelete.forEach(e -> {
                    table.remove(e.getId());
                });
                printSql(sql.toString());
            } catch (SQLException ex) {
                Logger.getLogger(AbstractDao.class.getName()).log(Level.SEVERE, sql.toString(), ex);
            } finally {
                SessionDB.close();
            }
        }
        return rows;
    }

    static void printSql(String sql) {
        if (MainFrame.SQL_DEBUG) {
            System.out.println(sql);
        }
    }
}
