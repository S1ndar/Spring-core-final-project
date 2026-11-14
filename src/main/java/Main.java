import account.Account;
import account.AccountService;
import config.ApplicationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.User;
import user.UserService;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);


        AccountService accountService = context.getBean(AccountService.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println(userService.userCreate("Adil"));
        System.out.println(userService.userCreate("Vadil"));
        System.out.println(userService.createAccountForUser(1));


        System.out.println(accountService.accountDeposit(1,300));
        System.out.println(accountService.accountTransfer(1,2,100));
        System.out.println(accountService.accountTransfer(1,3,100));

        System.out.println(userService.showAllUsers());

        System.out.println(userService.closeAccountForUser(3));
        System.out.println(userService.showAllUsers());


        context.close();
    }
}
