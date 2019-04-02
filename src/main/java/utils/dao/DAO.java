package utils.dao;

import java.util.List;

public interface DAO<T> {
    T create (T t);
    T read(Long id);
    List<T> findAll();
    T update(T t);
    void delete(Long id);
}
