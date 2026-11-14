package account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final int defaultAmount;
    private final double transferCommission;
    public AccountProperties(
            @Value("${account.default-amount}")
            int defaultAmount,
            @Value("${account.transfer-commission}")
            double transferCommission
    ) {
        if (defaultAmount < 0) {
            throw new IllegalArgumentException(
                    "Default account amount cannot be negative: " + defaultAmount
            );
        }
        if (!Double.isFinite(transferCommission) || transferCommission < 0.0 || transferCommission > 1.0) {
            throw new IllegalArgumentException(
                    "Transfer commission must be a finite number between 0 and 1: " + transferCommission
            );
        }
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public int getDefaultAmount() {
        return defaultAmount;
    }
    public double getTransferCommission() {
        return transferCommission;
    }

}
