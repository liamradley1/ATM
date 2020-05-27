/* User.java */
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class User {
    String firstName;
    String lastName;
    String uuid;
    byte hashedPin[];
    ArrayList<Account> accounts;
    Bank bank;

    public User(String firstName, String lastName, String pin, Bank bank) {
        this.firstName = firstName;
        this.lastName = lastName;

        // Store the SHA-256 hash of pin rather than its plaintext value.
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            this.hashedPin = sha.digest(pin.getBytes());

        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.err.println("Caught NoSuchAlgorithmException.");
            System.exit(1);
        }
        
        this.uuid = bank.getNewUserUUID();

        this.accounts = new ArrayList<Account>();

        System.out.printf("New user %s, %s with ID %s created.\n", lastName, firstName, this.uuid);


    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public byte[] getHashedPin() {
        return this.hashedPin;
    }

    public String getUuid() { 
        return this.uuid;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public boolean validatePin(String pinAttempt) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            return MessageDigest.isEqual(sha.digest(pinAttempt.getBytes()), this.hashedPin);
        }
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.err.println("Caught NoSuchAlgorithmException.");
            System.exit(1);
        }
        return false;
    }

    public void printAccountsSummary() {
        System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
        for (int i = 0; i < this.accounts.size(); ++i) {
            System.out.printf("%d) %s \n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts(){
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIndx) {
        this.accounts.get(acctIndx).printTransHistory();
    }

    public double getAcctBalance(int acctIndex) {
        return this.accounts.get(acctIndex).getBalance();
    }

    public String getAcctUUID(int acctIndx) {
        return this.accounts.get(acctIndx).getUuid();
    }

    public void addAcctTransaction(int acctIndx, double amount, String memo) {
        this.accounts.get(acctIndx).addTransaction(amount, memo);
    }
}