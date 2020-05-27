import java.util.Scanner;

class Machine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Bank bank = new Bank("Bank of Radley");
        User user = bank.addUser("Liam", "Radley", "1234");

        User curUser;
        while(true) {

            curUser = Machine.mainMenuPrompt(bank, scanner);

            Machine.printUserMenu(curUser, scanner);


        }

    }
    public static User mainMenuPrompt(Bank bank, Scanner scanner) {
        String userID;
        String pin;
        User authUser;

        do {
            System.out.printf("\n\nWelcome to %s\n\n", bank.getName());
            System.out.print("Enter user ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter pin: ");
            pin = scanner.nextLine();

            authUser = bank.userLogin(userID, pin);
            if (authUser == null) {
                System.out.println("Incorrect user ID/pin combination.\n" + "Please try again.");
            }
        } while(authUser == null);
        
        return authUser;
    }    
    public static void printUserMenu(User user, Scanner scanner) {
        user.printAccountsSummary();

        int choice; 

        do {
            System.out.printf("Welcome %s, what would you like to do? \n", user.getFirstName());
            System.out.println(" 1) Show transaction history");
            System.out.println(" 2) Make a withdrawal");
            System.out.println(" 3) Make a deposit");
            System.out.println(" 4) Make a transfer");
            System.out.println(" 5) Quit");
            System.out.println(" Enter choice: ");
            choice = scanner.nextInt();

            if (choice <1 || choice > 5) {
                System.out.println("Invalid choice. Please choose 1-5");
            } 
        } while (choice < 1 || choice > 5);

        switch (choice) {

            case 1:
            Machine.showTransHistory(user, scanner);
            break;

            case 2:
            Machine.withdrawFunds(user, scanner);
            break; 

            case 3: 
            Machine.depositFunds(user, scanner);
            break; 

            case 4:
            Machine.transferFunds(user, scanner);
            break;

            case 5:
            scanner.nextLine();
            break;
        }

        if (choice !=5) {
            Machine.printUserMenu(user, scanner);
        }
    }

    public static void showTransHistory(User user, Scanner scanner) {
        int acct; 
        do {
            System.out.printf("Enter the number (1-%d) of the account \n" +"whose transactions you want to see: ", user.numAccounts());
            acct = scanner.nextInt()-1;
            if(acct < 0 || acct >= user.numAccounts()){
                System.out.println("Invalid choice. Please try again.");
            }
        } while(acct < 0 || acct >= user.numAccounts());

        user.printAcctTransHistory(acct);
    }

    public static void transferFunds(User user, Scanner scanner) {
        int toAcct;
        int fromAcct;
        double amount; 
        double acctBal;

        do {
            System.out.printf("Enter the number (1-%d) of the account \n" + "to transfer from: ", user.numAccounts());
            fromAcct = scanner.nextInt() - 1;
            if(fromAcct < 0 || fromAcct >= user.numAccounts()) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= user.numAccounts()); 

        acctBal = user.getAcctBalance(fromAcct);
        do {
            System.out.printf("Enter the number (1-%d) of the account \n" + "to transfer to: ", user.numAccounts());
            toAcct = scanner.nextInt() - 1;
            if(toAcct < 0 || toAcct >= user.numAccounts()) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= user.numAccounts()); 

        do {
            System.out.println("Enter how much money you would like to send: \n");
            amount = scanner.nextDouble();
            if (amount > acctBal) {
                System.out.println("This will put you into beyond your arranged overdraft.\n" + "Please enter a different amount.");
            }
            else if (amount <= 0) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount <= 0 || amount > acctBal);

        user.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", user.getAcctUUID(toAcct)));
        user.addAcctTransaction(toAcct, amount, String.format("Transfer from account %s", user.getAcctUUID(fromAcct)));
    }

    public static void withdrawFunds(User user, Scanner scanner) {
        int fromAcct;
        double amount; 
        double acctBal;
        String memo;

        do {
            System.out.printf("Enter the number (1-%d) of the account \n" + "to withdraw from: ", user.numAccounts());
            fromAcct = scanner.nextInt() - 1;
            if(fromAcct < 0 || fromAcct >= user.numAccounts()) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= user.numAccounts()); 

        acctBal = user.getAcctBalance(fromAcct);

        do {
            System.out.println("Enter how much money you would like to withdraw: \n");
            amount = scanner.nextDouble();
            if (amount > acctBal) {
                System.out.println("This will put you into beyond your arranged overdraft.\n" + "Please enter a different amount.");
            }
            else if (amount <= 0) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount <= 0 || amount > acctBal);
        scanner.nextLine();

        System.out.println("Enter a memo:");
        memo = scanner.nextLine();

        user.addAcctTransaction(fromAcct, -1*amount, memo);

    }

    public static void depositFunds(User user, Scanner scanner) {
        int toAcct;
        double amount; 
        String memo; 

        do {
            System.out.printf("Enter the number (1-%d) of the account \n" + "to deposit into: ", user.numAccounts());
            toAcct = scanner.nextInt() - 1;
            if(toAcct < 0 || toAcct >= user.numAccounts()) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (toAcct < 0 || toAcct >= user.numAccounts()); 

        do {
            System.out.println("Enter how much money you would like to deposit: \n");
            amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount <= 0);
        scanner.nextLine();

        System.out.println("Enter a memo:");
        memo = scanner.nextLine();

        user.addAcctTransaction(toAcct, amount, memo);

    }
}

