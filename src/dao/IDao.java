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
public interface IDao<T extends IIdentifiable> {

    T query(int id);

    HashMap<Integer, T> query(int... ids);

    HashMap<Integer, T> queryAll();

    T get(int id);
    
    ArrayList<T> getSome(int... ids);

    HashMap<Integer, T> getMapOf(int... ids);

    HashMap<Integer, T> getAll();

    int insert(T objecT);

    int update(T objecT);

    int delete(T objecT);

    int delete(int id);
}
