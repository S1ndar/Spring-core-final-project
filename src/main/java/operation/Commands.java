package operation;

public enum Commands {
    USER_CREATE,
    SHOW_ALL_USERS,
    ACCOUNT_CREATE,
    ACCOUNT_DEPOSIT,
    ACCOUNT_WITHDRAW,
    ACCOUNT_TRANSFER,
    ACCOUNT_CLOSE,
    EXIT;

    public static Commands fromString(String value) {
        for (Commands command : Commands.values()) {
            if (command.name().equalsIgnoreCase(value)) {
                return command;
            }
        }
        throw new IllegalArgumentException("Unknown command: '" + value + "'. Please choose from the available list.");
    }
}
