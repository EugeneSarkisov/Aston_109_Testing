package ru.aston.crud_impl.run;

import ru.aston.crud_impl.service.UserService;

import java.util.Scanner;

public class Runner {
    public static final Scanner SCANNER = new Scanner(System.in);
    public static UserService userService = new UserService();
    public static int operationSelector;
    public static String anotherOperation;
    public static boolean isLastOperation = false;
    public static boolean isSelectorCorrect = false;

    public static void main(String[] args) {
        while (!isLastOperation) {
            System.out.println("Welcome! Here is the list of available operations.");
            System.out.println("1. Get user info");
            System.out.println("2. Create new user");
            System.out.println("3. Update user info");
            System.out.println("4. Delete user");
            System.out.print("Please, select the operation (1-4): ");
            operationSelector = SCANNER.nextInt();
            SCANNER.nextLine();
            switch (operationSelector) {
                case 1 -> {
                    System.out.print("Please, type the username: ");
                    System.out.println(userService.getUserInfo(SCANNER.nextLine()).toString());
                }
                case 2 -> {
                    System.out.print("Please, type the username, email and age of user: ");
                    userService.createUser(SCANNER.nextLine(), SCANNER.nextLine(), SCANNER.nextInt());
                    SCANNER.nextLine();
                }
                case 3 -> {
                    System.out.print("Please, type the existent user username, his new username, email and age: ");
                    userService.updateUserInfo(SCANNER.nextLine(), SCANNER.nextLine(), SCANNER.nextLine(), SCANNER.nextInt());
                    SCANNER.nextLine();
                }
                case 4 -> {
                    System.out.print("Please, type the username: ");
                    userService.deleteUser(SCANNER.nextLine());
                }
                default -> System.out.println("Something went wrong... Please, try another input next time!");
            }
            System.out.print("Would you like to try another operation? (Y/N): ");
            while (!isSelectorCorrect){
                anotherOperation = SCANNER.nextLine();
                if(anotherOperation.equals("Y")){
                    isSelectorCorrect = true;
                    isLastOperation = false;
                } else if (anotherOperation.equals("N")) {
                    isSelectorCorrect = true;
                    isLastOperation = true;
                } else {
                    System.out.println("Error! Please, check your input and try again: ");
                }
            }
        }
    }
}
