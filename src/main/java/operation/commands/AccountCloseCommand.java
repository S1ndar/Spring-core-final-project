package operation.commands;

import account.Account;
import account.TransferResult;
import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;
import user.CloseAccountResult;
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
        CloseAccountResult result = userService.closeAccountForUser(accountId);

        StringBuilder sb = new StringBuilder();
        sb.append("Account ").append(result.closedAccountId()).append(" closed.");

        if (result.transferResult() != null) {
            TransferResult t = result.transferResult();
            sb.append(" Balance transferred to account ").append(result.targetAccountId())
                    .append(": ").append(t.amountToTransfer()).append(" (commission ").append(t.commission()).append(")");
        } else {
            sb.append(" Balance was zero, nothing to transfer.");
        }

        System.out.println(sb);
    }

    @Override
    public Commands getCommand() {
        return Commands.ACCOUNT_CLOSE;
    }
}
