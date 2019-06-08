/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.dao;

import src.model.IPersistable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author NarF
 * @param <T>
 */
public interface IDao<T extends IPersistable> {

    T query(int id);

    HashMap<Integer, T> query(ArrayList<Integer> ids);

    HashMap<Integer, T> queryAll();

    T get(int id);

    ArrayList<T> getSome(ArrayList<Integer> ids);

    HashMap<Integer, T> getMapOf(ArrayList<Integer> ids);

    HashMap<Integer, T> getAll();

    int insert(T objecT);

    int update(T objecT);

    int updateDao(T objectT);

    int delete(T objecT);

    int delete(int id);

    int deleteSome(ArrayList<T> toDelete);
}
