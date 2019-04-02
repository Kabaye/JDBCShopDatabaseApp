package shop;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class Good {
    private int goodId;
    private String goodName;
    private int price;

    @Override
    public String toString() {
        return "Good ID: " + goodId +
                ", good name: \'" + goodName +
                "\', price: " + price + ";";
    }
}
