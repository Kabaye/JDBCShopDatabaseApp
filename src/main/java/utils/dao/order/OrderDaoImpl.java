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
import java.util.Set;
import java.util.TreeSet;

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
            for (int i = 0; i < order.getAmounts().size(); i++) {
                pst.setInt(1, order.getOrderID());
                pst.setInt(2, order.getCustomerID());
                pst.setInt(3, order.getGoodsID().get(i));
                pst.setInt(4, order.getAmounts().get(i));
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

            int orderID;
            int customerID;
            List<Integer> goods = new ArrayList<>();
            List<Integer> amount = new ArrayList<>();

            if (orderSet.next()) {
                orderID = orderSet.getInt("order_id");
                customerID = orderSet.getInt("customer_id");
                goods.add(orderSet.getInt("good_id"));
                amount.add(orderSet.getInt("amount"));
                while (orderSet.next()) {
                    goods.add(orderSet.getInt("good_id"));
                    amount.add(orderSet.getInt("amount"));
                }
                order = Order.of(orderID, customerID, goods, amount);
            }

            orderSet.close();
            pst.close();
        } catch (SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return order;
    }

    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_ALL);
            ResultSet set = pst.executeQuery();
            addAll(set, orders);
        } catch (SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return orders;
    }

    public Order update(Order order) {
        return null;
    }

    public void delete(Long id) {

    }

    private void addAll(ResultSet set, List<Order> orders) throws SQLException {
        Set<Integer> orderIDs = new TreeSet<>();
        int customerID;
        List<Integer> goods;
        List<Integer> amounts;
        while (set.next()) {
            final int orderID = set.getInt("order_id");
            if (orderIDs.add(orderID)) {
                customerID = set.getInt("customer_id");
                goods = new ArrayList<>();
                goods.add(set.getInt("good_id"));
                amounts = new ArrayList<>();
                amounts.add(set.getInt("amount"));
                orders.add(Order.of(orderID, customerID, goods, amounts));
            } else {
                Order order = orders.stream()
                        .filter(elem -> elem.getOrderID() == orderID).findFirst().get();
                order.addGood(set.getInt("good_id"), set.getInt("amount"));
            }
        }
    }
}
