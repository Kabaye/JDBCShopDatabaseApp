package utils.dao.customer;

import shop.Customer;
import shop.PaymentData;
import utils.connection.ConnectionBuilder;
import utils.connection.SimpleConnectionBuilder;
import utils.dao.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements DAO<Customer> {

    private static final String EXCEPTION_MESSAGE = "404 Error! No connection to database!";

    private static final String INSERT
            = "INSERT INTO customers (customer_id, surname_name, phone) VALUES (?, ?, ?)";
    private static final String INSERT_BANK_DATA
            = "INSERT INTO payment_data (customer_id, bank_account,account_currency) VALUES (?,?,?)";

    private static final String SELECT_CUSTOMER_AND_BANK_DATA_BY_CUSTOMER_ID
            = "SELECT customers.customer_id, surname_name,phone,bank_account,account_currency FROM customers INNER JOIN payment_data ON customers.customer_id = payment_data.customer_id where customers.customer_id=?";

    private static final String SELECT_ALL
            = "SELECT * FROM customers";

    private static final String UPDATE
            = "UPDATE customers SET surname_name=?,phone-? where customer_id=?";

    private static final String GET_MAX_ID = "SELECT MAX(customer_id) as MAX_ID FROM customers";

    private static final String DELETE
            = "DELETE FROM customers WHERE customer_id=?";

    private ConnectionBuilder builder = new SimpleConnectionBuilder();

    @Override
    public Customer create(Customer customer) {
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(GET_MAX_ID);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                int counter = set.getInt("MAX_ID") + 1;

                pst.close();
                set.close();

                pst = con.prepareStatement(INSERT);
                pst.setInt(1, counter);

                customer.setCustomerID(counter);
                customer.getPaymentData().setCustomerId(counter);

                pst.setString(2, customer.getSurnameName());
                pst.setLong(3, customer.getPhone());
                pst.executeUpdate();

                pst.close();

                pst = con.prepareStatement(INSERT_BANK_DATA);
                pst.setInt(1, customer.getCustomerID());
                pst.setLong(2, customer.getPaymentData().getBankAccount());
                pst.setString(3, customer.getPaymentData().getAccountCurrency());
                pst.executeUpdate();

                pst.close();
                con.close();
            }
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return customer;
    }

    @Override
    public Customer read(long customerID) {
        Customer customer = null;
        try {
            Connection con = builder.getConnection();
            PreparedStatement pst = con.prepareStatement(SELECT_CUSTOMER_AND_BANK_DATA_BY_CUSTOMER_ID);
            pst.setLong(1, customerID);
            ResultSet set = pst.executeQuery();
            if (set.next()) {
                int customerID1 = set.getInt("customer_id");
                String surnameName = set.getString("surname_name");
                long phone = set.getLong("phone");

                PaymentData paymentData = PaymentData.of(customerID1, set.getLong("bank_account"), set.getString("account_currency"));
                customer = Customer.of(customerID1, surnameName, phone, paymentData);
            }

            set.close();
            pst.close();
            con.close();
        } catch (
                SQLException exc) {
            System.out.println(EXCEPTION_MESSAGE);
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        return customers;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
