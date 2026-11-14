package user;

import account.Account;
import account.AccountService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        if  (loginIsExist) {
            throw new IllegalArgumentException("User already exists");
        }

        int userId = idCounter++;
        User user = new User(userId,login,new ArrayList<>());
        Account defaultAccount = accountService.createDefaultAccount(userId);
        user.getAccountList().add(defaultAccount);
        users.put(userId, user);
        return user;
    }

    public Account createAccountForUser(int userId) {
        User user = users.get(userId);
        Account account = accountService.createAnotherAccount(userId);
        user.getAccountList().add(account);
        return account;
    }

    public Account closeAccountForUser(int accountId) {

        Account accountToClose = accountService.getAccountById(accountId);
        User user = users.get(accountToClose.getUserId());

        if (user.getAccountList().size() <= 1) {
            throw new IllegalStateException("Cannot close the only account for user ID=" + user.getId());
        }

        user.getAccountList().remove(accountToClose);
        Account finalAccount = user.getAccountList().getFirst();

        accountService.accountTransfer(accountToClose.getId(), finalAccount.getId(), accountToClose.getMoneyAmount());
        return accountService.closeAccount(accountId);
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
        return users;
    }





}
