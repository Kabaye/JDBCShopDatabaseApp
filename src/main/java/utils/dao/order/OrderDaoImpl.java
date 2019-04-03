package utils.dao.order;

import shop.Order;
import utils.connection.ConnectionBuilder;
import utils.connection.SimpleConnectionBuilder;
import utils.dao.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OrderDaoImpl implements DAO<Order> {

    private static final String EXCEPTION_MESSAGE = "404 Error! No connection to database!";

    private static final String INSERT
            = "INSERT INTO orders (order_id, customer_id, good_id, amount,id) VALUES (?, ?, ?, ?,?)";

    private static final String SELECT_ORDERS_BY_ID
            = "SELECT order_id, customer_id, good_id, amount, id FROM orders WHERE order_id=?";

    private static final String SELECT_ALL
            = "SELECT * FROM orders";

    private static final String UPDATE
            = "UPDATE orders SET good_id=?, amount=? where id=? and order_id=? and customer_id=?";

    private static final String GET_MAX_ID = "SELECT MAX(id) as MAX_ID FROM orders";

    private static final String DELETE
            = "DELETE FROM orders WHERE order_id=?";


    private ConnectionBuilder builder = new SimpleConnectionBuilder();

    //add order
    @Override
    public Order create(Order order) {
        try {
            Connection con = builder.getConnection();

            PreparedStatement pst = con.prepareStatement(GET_MAX_ID);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                int counter = set.getInt("MAX_ID") + 1;
                pst.close();
                set.close();
                pst = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                for (int i = 0; i < order.getAmounts().size(); i++) {
                    pst.setInt(1, order.getOrderID());
                    pst.setInt(2, order.getCustomerID());
                    pst.setInt(3, order.getGoodIDs().get(i));
                    pst.setInt(4, order.getAmounts().get(i));
                    pst.setInt(5, counter++);
                    pst.executeUpdate();
                    pst.close();
                }
                con.close();
            }
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return order;
    }

    //get order
    @Override
    public Order read(long orderID) {
        Order order = null;
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_ORDERS_BY_ID);
            pst.setLong(1, orderID);
            ResultSet orderSet = pst.executeQuery();

            int orderID1;
            int customerID;
            List<Integer> IDs = new ArrayList<>();
            List<Integer> goods = new ArrayList<>();
            List<Integer> amount = new ArrayList<>();

            if (orderSet.next()) {
                orderID1 = orderSet.getInt("order_id");
                customerID = orderSet.getInt("customer_id");
                IDs.add(orderSet.getInt("id"));
                goods.add(orderSet.getInt("good_id"));
                amount.add(orderSet.getInt("amount"));
                while (orderSet.next()) {
                    IDs.add(orderSet.getInt("id"));
                    goods.add(orderSet.getInt("good_id"));
                    amount.add(orderSet.getInt("amount"));
                }
                order = Order.of(orderID1, customerID, IDs, goods, amount);
            }

            orderSet.close();
            pst.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return order;
    }

    @Override
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

    @Override
    public Order update(Order order) {
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(UPDATE);
            for (int i = 0; i < order.getGoodIDs().size(); i++) {
                pst.setInt(1, order.getGoodIDs().get(i));
                pst.setInt(2, order.getAmounts().get(i));
                pst.setInt(3, order.getIDs().get(i));
                pst.setInt(4, order.getOrderID());
                pst.setInt(5, order.getCustomerID());
                pst.executeUpdate();
            }
            pst.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return order;
    }

    //удаляю весь заказ((
    @Override
    public void delete(long orderID) {
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(DELETE);
            pst.setLong(1, orderID);
            pst.executeUpdate();
            pst.close();
            con.close();
        } catch (SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
    }

    private void addAll(ResultSet set, List<Order> orders) throws SQLException {
        Set<Integer> orderIDs = new TreeSet<>();
        int customerID;
        List<Integer> goods;
        List<Integer> amounts;
        List<Integer> IDs;
        while (set.next()) {
            final int orderID = set.getInt("order_id");
            if (orderIDs.add(orderID)) {
                customerID = set.getInt("customer_id");
                IDs = new ArrayList<>();
                IDs.add(set.getInt("id"));
                goods = new ArrayList<>();
                goods.add(set.getInt("good_id"));
                amounts = new ArrayList<>();
                amounts.add(set.getInt("amount"));
                orders.add(Order.of(orderID, customerID, IDs, goods, amounts));
            } else {
                Order order = orders.stream()
                        .filter(elem -> elem.getOrderID() == orderID).findFirst().get();
                order.addGood(set.getInt("good_id"), set.getInt("amount"));
            }
        }
    }
}
