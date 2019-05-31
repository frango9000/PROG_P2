/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author NarF
 * @param <T>
 */
public interface Dao<T> {
    
    Optional<T> get(int id);
     
    Map<Integer,T> queryAll();
     
    void save(T t);
     
    void update(T t, String[] params);
     
    void delete(T t);
}
