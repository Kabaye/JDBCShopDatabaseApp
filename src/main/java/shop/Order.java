package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class Order {
    private int orderId;
    private int customerId;
    private int goodId;
    private int amount;
}
