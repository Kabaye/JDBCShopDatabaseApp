package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class Order {
    private int orderID;
    private int customerID;
    private List<Integer> IDs;
    private List<Integer> goodIDs;
    private List<Integer> amounts;


    public Order(int orderID, int customerID, List<Integer> goodIDs, List<Integer> amounts) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.goodIDs = goodIDs;
        this.amounts = amounts;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customerID=" + customerID +
                ", goodIDs=" + goodIDs +
                ", amounts=" + amounts +
                '}';
    }

    public void addGood(int goodID, int amount)
    {
        goodIDs.add(goodID);
        amounts.add(amount);
    }

}
