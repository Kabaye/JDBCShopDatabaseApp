package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class PaymentData {
    private int customerId;
    private long bankAccount;
    private String accountCurrency;

    public static PaymentData getBasicInstance()
    {
        return new PaymentData(0,"BYN");
    }

    public PaymentData(long bankAccount, String accountCurrency) {
        this.bankAccount = bankAccount;
        this.accountCurrency = accountCurrency;
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "bankAccount = " + bankAccount +
                ", accountCurrency = '" + accountCurrency + '\'' +
                '}';
    }
}
