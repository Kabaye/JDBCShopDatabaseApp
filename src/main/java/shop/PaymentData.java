package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class PaymentData {
    private int customerId;
    private int bankAccount;
    private String accountCurrency;
}
