package com.educacionit.dao;

import java.util.List;

public interface DAOInterface<T> {
    
    T save(T object);
    
    T getById(Integer id);
    
    List<T> getAll();
    
    T update(Integer id, T object);
    
    void delete(Integer id);

}
