package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class Customer {
    private int customerID;
    private String surnameName;
    private long phone;
    private PaymentData paymentData;

    public Customer(String surnameName, long phone, PaymentData paymentData) {
        this.surnameName = surnameName;
        this.phone = phone;
        this.paymentData = paymentData;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID = " + customerID +
                ", surnameName = '" + surnameName + '\'' +
                ", phone = " + phone +
                ", paymentData = " + paymentData +
                '}';
    }
}
