package operation.commands;

import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;
import user.UserService;

@Component
public class AccountCloseCommand implements OperationCommand {
    private final UserService userService;
    private final ConsoleInput consoleInput;

    public AccountCloseCommand(ConsoleInput consoleInput, UserService userService) {
        this.consoleInput = consoleInput;
        this.userService = userService;
    }

    @Override
    public void execute() {
        int accountId = consoleInput.readInt("Account ID: ");
        System.out.println("Account "+accountId+" closed. "+userService.closeAccountForUser(accountId));
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_CLOSE;
    }
}
