package console;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: invalid number format. Please enter an integer.");
            }
        }
    }

    public String readString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: input cannot be empty. Please try again.");
        }
    }
}
