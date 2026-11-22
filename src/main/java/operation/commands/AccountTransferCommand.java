package operation.commands;

import account.AccountService;
import account.TransferResult;
import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountTransferCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;
    public AccountTransferCommand(AccountService accountService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int fromId = consoleInput.readInt("From: id");
        int toId = consoleInput.readInt("To: id");
        int amount = consoleInput.readInt("Amount: ");

        TransferResult result = accountService.accountTransfer(fromId, toId, amount);

        String commissionMsg = result.sameUser()
                ? " (no commission, same user)"
                : " commission " + result.commission() + " recipient received: " + result.amountToTransfer();

        System.out.println("Transfer completed from account " + result.fromAccount().getId()
                + " to account " + result.toAccount().getId()
                + " . Amount: " + result.amount() + commissionMsg);
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_TRANSFER;
    }
}
