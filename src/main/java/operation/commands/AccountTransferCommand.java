package operation.commands;

import account.AccountService;
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
        System.out.println(accountService.accountTransfer(fromId, toId, amount));
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_TRANSFER;
    }
}
