package operation.commands;

import account.Account;
import account.AccountService;
import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final AccountService accountService;

    public AccountDepositCommand(ConsoleInput consoleInput, AccountService accountService) {
        this.consoleInput = consoleInput;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        int accountId = consoleInput.readInt("Enter account id:");
        int deposit = consoleInput.readInt("Enter amount:");
        System.out.println(accountService.accountDeposit(accountId, deposit));
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_DEPOSIT;
    }
}
