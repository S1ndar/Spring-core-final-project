package account;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccountService {
    private final Map<Integer, Account> accounts = new HashMap<>();
    private int idCounter = 1;

    private final AccountProperties accountProperties;

    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }

    public Account createDefaultAccount(int userId) {
        return accountCreate(userId, accountProperties.getDefaultAmount());
    }
    public Account createAnotherAccount(int userId) {
        return accountCreate(userId,0);
    }

    private Account accountCreate(int userId,int initBalance){

        int accountId = idCounter++;
        Account account = new Account(accountId,userId,initBalance);
        accounts.put(accountId, account);
        return account;
    }

    public Account closeAccount(int accountId){

        Account account = accounts.get(accountId);
        accounts.remove(accountId);
        return account;
    }

    public String accountDeposit(int accountId, int amount){
        validateAmount(amount);

        Account account = accounts.get(accountId);
        account.setMoneyAmount(account.getMoneyAmount()+amount);
        return "Deposited "+amount+ " to account "+account.getId() + " New balance: " +  account.getMoneyAmount();
    }

    public String accountWithdraw(int accountId, int amount){
        validateAmount(amount);

        Account account = accounts.get(accountId);

        if (account.getMoneyAmount() < amount) {
            throw new IllegalStateException("Insufficient funds on account " + accountId + ". Current balance: " + account.getMoneyAmount());
        }

        account.setMoneyAmount(account.getMoneyAmount()-amount);
        return "Withdrawn " + amount + " from account " + account.getId() + ". New balance: " + account.getMoneyAmount();
    }

    public String accountTransfer(int fromId, int toId, int amount){
        validateAmount(amount);

        if (fromId == toId) {
            throw new IllegalArgumentException("Cannot transfer money to the same account (ID= " + fromId + ").");
        }

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);

        if (fromAccount.getMoneyAmount() < amount) {
            throw new IllegalStateException("Insufficient funds for transfer on account " + fromId + ". Current balance: " + fromAccount.getMoneyAmount());
        }

        boolean isSameUser = fromAccount.getUserId() == toAccount.getUserId();
        double commission = isSameUser ? 0.0 : accountProperties.getTransferCommission();

        int commissionInAmount = (int) Math.round(commission * amount);
        int amountToTransfer = amount - commissionInAmount;

        fromAccount.setMoneyAmount(fromAccount.getMoneyAmount()-amount);
        toAccount.setMoneyAmount(toAccount.getMoneyAmount()+amountToTransfer);

        String messageAboutCommission = isSameUser ? " (no commission, same user)"
                : " commission "+commissionInAmount +" recipient receiveed: "+amountToTransfer;

        return "Transfer completed from account "+fromId +" to account "+ toId+" . Amount: "+amount+messageAboutCommission;

    }

    public Account getAccountById(int accountId) {
        if (accountId < 0){
            throw new IllegalArgumentException("Id must be positive");
        }

        Account account = accounts.get(accountId);

        if (account == null) {
            throw new IllegalArgumentException("Account with ID=" + accountId + " not found.");
        }
        return account;
    }

    private void validateAmount(int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}
