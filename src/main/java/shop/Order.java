package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class Order {
    private int orderId;
    private int customerId;
    private List<Integer> goodsId;
    private List<Integer> amounts;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", goodsId=" + goodsId +
                ", amounts=" + amounts +
                '}';
    }

    public void addGood(int goodID, int amount)
    {
        goodsId.add(goodID);
        amounts.add(amount);
    }

}
