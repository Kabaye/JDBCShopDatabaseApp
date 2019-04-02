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

    @Override
    public String toString() {
        return "Customer ID: " + customerID +
                ", customer surname and name: \'" + surnameName +
                "\', phone: +" + phone + ";";
    }
}
