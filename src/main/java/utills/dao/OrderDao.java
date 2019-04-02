package utills.dao;

import shop.Order;

import java.util.List;

public interface OrderDao {
    Order create(Order order);
    Order read(Long id);
    List<Order> findAll();
    Order update(Order order);
    void delete(Long id);
}
