package utils.dao.order;

import shop.Order;
import utils.connection.ConnectionBuilder;
import utils.connection.SimpleConnectionBuilder;
import utils.dao.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements DAO<Order> {

    private static final String EXCEPTION_MESSAGE = "404 Error! No connection to database!";

    private static final String INSERT
            = "INSERT INTO orders (order_id, customer_id, good_id, amount) VALUES (?, ?, ?, ?)";

    private static final String SELECT_ORDERS_BY_ID
            = "SELECT order_id, customer_id, good_id, amount FROM orders WHERE order_id=?";

    private static final String SELECT_ALL
            = "SELECT * FROM orders";




    private ConnectionBuilder builder = new SimpleConnectionBuilder();

    //add order with unique customer
    public Order create(Order order) {
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(INSERT);
            for (int i = 0; i < order.getAmount().size(); i++) {
                pst.setInt(1, order.getOrderId());
                pst.setInt(2, order.getCustomerId());
                pst.setInt(3, order.getGoodsId().get(i));
                pst.setInt(4, order.getAmount().get(i));
                pst.executeUpdate();
            }
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return order;
    }

    //get order
    public Order read(Long id) {
        Order order = null;
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_ORDERS_BY_ID);
            pst.setLong(1, id);
            ResultSet orderSet = pst.executeQuery();

            int orderId;
            int customerId;
            List<Integer> goods = new ArrayList<Integer>();
            List<Integer> amount = new ArrayList<Integer>();

            if (orderSet.next()) {
                orderId = orderSet.getInt("order_id");
                customerId = orderSet.getInt("customer_id");
                goods.add(orderSet.getInt("good_id"));
                amount.add(orderSet.getInt("amount"));
                while (orderSet.next()) {
                    goods.add(orderSet.getInt("good_id"));
                    amount.add(orderSet.getInt("amount"));
                }
                order = Order.of(orderId, customerId, goods, amount);
            }

            orderSet.close();
            pst.close();
        } catch (SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return order;
    }

    public List<Order> findAll() {
        return null;
    }

    public Order update(Order order) {
        return null;
    }

    public void delete(Long id) {

    }
}
