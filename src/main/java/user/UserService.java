package user;

import account.Account;
import account.AccountService;
import account.TransferResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserService {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    private final AccountService accountService;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public User userCreate(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Login cannot be null or blank.");
        }

        boolean loginIsExist = users.values().stream()
                .anyMatch(existingUser -> existingUser.getLogin().equals(login));
        if (loginIsExist) {
            throw new IllegalArgumentException("User already exists");
        }

        int userId = idCounter++;
        User user = new User(userId, login, new ArrayList<>());
        Account defaultAccount = accountService.createDefaultAccount(userId);
        user.getAccountList().add(defaultAccount);
        users.put(userId, user);
        return user;
    }

    public Account createAccountForUser(int userId) {
        User user = getUserById(userId);
        Account account = accountService.createAnotherAccount(userId);
        user.getAccountList().add(account);
        return account;
    }

    public CloseAccountResult closeAccountForUser(int accountId) {
        Account accountToClose = accountService.getAccountById(accountId);
        User user = getUserById(accountToClose.getUserId());

        if (user.getAccountList().size() <= 1) {
            throw new IllegalStateException("Cannot close the only account for user "
                    + user.getLogin() + ", ID= " + user.getId());
        }

        Account targetAccount = user.getAccountList().stream()
                .filter(account -> account.getId() != accountId)
                .min(Comparator.comparingInt(Account::getId))
                .orElseThrow(() -> new IllegalStateException("No target account found."));

        TransferResult transferResult = null;
        if (accountToClose.getMoneyAmount() > 0) {
            transferResult = accountService.accountTransfer(
                    accountToClose.getId(),
                    targetAccount.getId(),
                    accountToClose.getMoneyAmount()
            );
        }

        user.getAccountList().remove(accountToClose);
        accountService.closeAccount(accountId);

        return new CloseAccountResult(accountId, user.getId(), targetAccount.getId(), transferResult);
    }

    public User getUserById(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be positive.");
        }
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID= " + userId + " not found.");
        }
        return user;
    }

    public Map<Integer, User> showAllUsers() {
        return Map.copyOf(users);
    }
}