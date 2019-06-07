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
        if (table.containsKey(id)) {
            return table.get(id);
        } else {
            return query(id);
        }
    }

    @Override
    public ArrayList<T> getSome(int... ids) {
        query(ids);
        ArrayList<T> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (table.containsKey(ids[i])) {
                T t = table.get(ids[i]);
                list.add(t);
            }
        }
        return list;
    }

    @Override
    public HashMap<Integer, T> getMapOf(int... ids) {
        HashMap<Integer, T> filteredHashMap = new HashMap<>();
        ArrayList<Integer> idsToQuery = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            if (table.containsKey(ids[i])) {
                T t = table.get(ids[i]);
                filteredHashMap.put(t.getId(), t);
            } else {
                idsToQuery.add(ids[i]);
            }
        }
        if (idsToQuery.size() > 0) {
            int[] tempList = idsToQuery.stream().mapToInt(Integer::intValue).toArray();
            HashMap<Integer, T> found = query(tempList);
            filteredHashMap.putAll(found);
        }
        return filteredHashMap;
    }

    @Override
    public HashMap<Integer, T> getAll() {
        return table;
    }

    @Override
    public int delete(T t) {
        int rows = 0;
        if (SessionDB.connect()) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL_NAME + " = '" + t.getId() + "'";
            try (Statement stmt = SessionDB.getConn().createStatement()) {
                rows = stmt.executeUpdate(sql);
                table.remove(t.getId());
                if (MainFrame.SQL_DEBUG) {
                    System.out.println(sql);
                }
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

}
