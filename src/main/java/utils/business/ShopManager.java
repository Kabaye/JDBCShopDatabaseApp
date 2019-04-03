package utils.business;

import shop.Customer;
import shop.Order;
import utils.dao.DAO;
import utils.dao.customer.CustomerDaoImpl;
import utils.dao.order.OrderDaoImpl;

import java.util.List;

public class ShopManager {
    private DAO<Order> orderDAO = new OrderDaoImpl();
    private DAO<Customer> customerDAO = new CustomerDaoImpl();

    public Order addOrder(int customerId, List<Integer> goodsId, List<Integer> amounts) {
        Order order = null;
        if ((goodsId.size() != amounts.size()) || (goodsId.size() == 0 && amounts.size() == 0)) {
            System.out.println("You try to add invalid data to order (size of list of goods not equal to size of list of amounts OR they are empty), please check your data and try again.");
        } else {
            if (((CustomerDaoImpl) customerDAO).hasID(customerId)) {
                order = orderDAO.create(new Order(customerId, goodsId, amounts));
            } else {
                System.out.println("There is no customer with such id. Add information about you!");
            }
        }
        return order;
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

    public void deleteOrder(int id) {

    }

}
