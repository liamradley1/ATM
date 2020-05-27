import java.util.Scanner;

class OutOfProfileTransfer {
    Scanner scanner = new Scanner(System.in);

    public static void transfer(User user, Scanner scanner) {
        int toAcct;       
        int sortCode;
        int accountNumber;
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
        boolean isValid = true;
        Bank bank2;
        do {
            System.out.println("Enter the sort code of the account to transfer to: ");
            sortCode = scanner.nextInt();
            bank2 = sortCodeFinder(sortCode);
            if(bank2 == null) {
                System.out.println("Invalid sort code. Please try again.");
                isValid = false;
            }
        } while (isValid = false);
            Account account2;
        do {
            System.out.println("Enter the account number of the account to transfer to: ");
            accountNumber = scanner.nextInt();
            account2 = accountFinder(accountNumber, bank2);
            if(account2 == null) {
                System.out.println("Invalid account number. Please try again.");
                isValid = false;
            }
        } while (isValid = false);

        do {
            System.out.println("Enter how much money you would like to send: \n");
            amount = scanner.nextDouble();
            if (amount > acctBal) {
                System.out.println("This will put you into beyond your arranged overdraft.\n" + "Please enter a different amount.");
            }
            else if (amount < 0) {
                System.out.println("Invalid amount. Please try again.");
            }

        } while(amount < 0 || amount > acctBal);

        user.addAcctTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", user.getAcctUUID(toAcct)));
        user.addAcctTransaction(account2, amount, String.format("Transfer from account %s", user.getAcctUUID(fromAcct)));
    }
}