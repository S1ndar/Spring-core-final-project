package console;

import operation.Commands;
import operation.OperationCommand;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OperationConsoleListener {
    private final ConsoleInput consoleInput;
    private final Map<Commands,OperationCommand> commands;

    public OperationConsoleListener(ConsoleInput consoleInput, List<OperationCommand> commands) {
        this.consoleInput = consoleInput;
        this.commands = commands.stream().collect(Collectors.toMap(OperationCommand::getCommand, Function.identity()));
    }

    public void listenUpdates() {
        while (true) {
            System.out.println("\nAvailable operations: " + "\n"+Arrays.toString(Commands.values()));

            String rawCommand = consoleInput.readString("Enter operation command: ");
            if (rawCommand == null || rawCommand.isBlank()) {
                System.out.println("Operation cannot be empty");
                continue;
            }
            String normalCommand = rawCommand.toUpperCase().trim();
            if (normalCommand.equals("EXIT")){
                System.out.println("Closing application...");
                break;
            }
            processNextOperation(normalCommand);
        }
    }

    private void processNextOperation(String normalCommand) {
        try {
            Commands command = Commands.fromString(normalCommand);
            OperationCommand operationCommand = commands.get(command);
            if (operationCommand == null) {
                    System.out.println("No command found for operation: " + command);
                    return;
            }
            operationCommand.execute();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
