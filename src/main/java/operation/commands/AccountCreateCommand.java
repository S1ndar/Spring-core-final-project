package operation.commands;

import account.Account;
import account.AccountService;
import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;
import user.UserService;

@Component
public class AccountCreateCommand implements OperationCommand {
    private final UserService userService;
    private final ConsoleInput consoleInput;

    public AccountCreateCommand(UserService userService, ConsoleInput consoleInput) {
        this.userService = userService;
        this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        int userId = consoleInput.readInt("Enter user ID: ");
        Account account = userService.createAccountForUser(userId);
        System.out.println("Account created: id=" + account.getId()
                + ", userId=" + account.getUserId()
                + ", balance=" + account.getMoneyAmount());
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_CREATE;
    }
}
