/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import java.util.ArrayList;
import java.util.HashMap;
import src.model.IIdentifiable;

/**
 *
 * @author NarF
 * @param <T>
 */
public abstract class AbstractDao<T extends IIdentifiable> implements IDao<T> {

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
    public int delete(int id) {
        return delete(table.get(id));
    }

}
