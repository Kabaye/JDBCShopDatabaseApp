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
    private int phone;
    private PaymentData paymentData;

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
