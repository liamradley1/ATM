import java.util.ArrayList;
import java.util.Random;
class Bank {

    public String name;
    
    public String sortCode;

    private ArrayList<User> users;

    private ArrayList<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.users = new ArrayList<User>();
    }

    public String getNewUserUUID() {
        String uuid; 
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        do {
            uuid = "";
            for (int c = 0; c < len; ++c) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.compareTo(u.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        }   while (nonUnique == true);
        
        return uuid;
    }

    public String getName() {
        return this.name;
    }
    
    public String getSortCode() {
        return this.sortCode;
    }

    public String getNewAccountUUID() {
        String uuid; 
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        do {
            uuid = "";
            for (int c = 0; c < len; ++c) {
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            nonUnique = false;
            for (Account a : this.accounts) {
                if (uuid.compareTo(a.getUuid()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        }   while (nonUnique == true);


        return uuid;
    }

    public void addAccount (Account account) {
        this.accounts.add(account);
    }

    public User addUser(String firstName, String lastName, String pin) {
        User user = new User(firstName, lastName, pin, this);
        this.users.add(user);

        Account account1 = new Account("Savings", user, this);
        Account account2 = new Account("Current", user, this);
        user.addAccount(account1);
        user.addAccount(account2);
        this.addAccount(account1);
        this.addAccount(account2);


        return user;
    }


    public User userLogin(String useruuid, String pin) {
        for (User u : this.users) {
            if (u.getUuid().compareTo(useruuid) == 0 && u.validatePin(pin) == true) {
                return u;
            }
        }
        return null;
    }
}