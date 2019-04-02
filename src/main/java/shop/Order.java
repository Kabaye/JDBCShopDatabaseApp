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
    private List<Integer> goodIDs;
    private List<Integer> amounts;

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
