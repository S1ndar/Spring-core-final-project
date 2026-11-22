package account;

public record TransferResult(
        Account fromAccount,
        Account toAccount,
        int amount,
        int amountToTransfer,
        boolean sameUser,
        int commission
) {}