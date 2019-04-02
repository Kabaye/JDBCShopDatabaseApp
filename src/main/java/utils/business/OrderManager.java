package utils.business;

import shop.Order;
import utils.dao.DAO;
import utils.dao.order.OrderDaoImpl;

import java.util.List;

public class OrderManager {
    private DAO<Order> dao = new OrderDaoImpl();

    public Order addOrder(int customerId, List<Integer> goodsId, List<Integer> amounts) {
        return null;
    }

    public Order getOrder(int id) {
        return null;
    }

    public List<Order> findAllOrders() {
        return null;
    }

    public Order updateOrder() {
        return null;
    }

    public void deleteOrder(int id){

    }

}
