package operation.commands;

import account.Account;
import account.AccountService;
import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;


@Component
public class AccountWithdrawCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    public AccountWithdrawCommand(AccountService accountService, ConsoleInput consoleInput) {
        this.accountService = accountService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int accountId = consoleInput.readInt("Account ID: ");
        int amount = consoleInput.readInt("Amount: ");
        Account account = accountService.accountWithdraw(accountId, amount);
        System.out.println("Withdrawn " + amount + " from account " + account.getId()
                + ". New balance: " + account.getMoneyAmount());
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_WITHDRAW;
    }
}
