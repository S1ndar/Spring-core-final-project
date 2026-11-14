package operation.commands;

import console.ConsoleInput;
import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;
import user.User;
import user.UserService;

@Component
public class UserCreateCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final UserService userService;

    public UserCreateCommand(ConsoleInput consoleInput, UserService userService) {
        this.consoleInput = consoleInput;
        this.userService = userService;
    }

    @Override
    public void execute() {
        String login = consoleInput.readString("Enter login for new user:");
        User user = userService.userCreate(login);
        System.out.println("User "+login+" was created "+user);
    }

    @Override
    public Commands getCommand() {
        return Commands.USER_CREATE;
    }
}
