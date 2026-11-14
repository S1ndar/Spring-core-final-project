package operation.commands;

import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;
import user.UserService;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
        System.out.println(userService.showAllUsers());
    }

    @Override
    public Commands getCommand() {
        return Commands.SHOW_ALL_USERS;
    }
}
