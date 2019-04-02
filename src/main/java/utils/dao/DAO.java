package utils.dao;

import java.util.List;

public interface DAO<T> {
    T create (T t);
    T read(int id);
    List<T> findAll();
    T update(T t);
    void delete(int id);
}
