import java.util.ArrayList;

class Account {
    private String name; 

    private double balance;

    private String uuid; 

    private User holder;

    private ArrayList<Transaction> transactionList;

    public Account(String name, User holder, Bank bank){
        this.name = name;
        this.holder = holder;
        this.uuid = bank.getNewAccountUUID();
        this.transactionList = new ArrayList<Transaction>();
    }

    public String getUuid() {
        return this.uuid;
    } 

    public String getName() {
        return this.name;
    }

    public User getHolder() {
        return this.holder;
    }

    public ArrayList<Transaction> getTransactions() {
        return this.transactionList;
    }

    public String getSummaryLine() {
        double balance = this.getBalance();

        if(this.balance >= 0) {
            return String.format("%s : £%.2f : %s", this.uuid, balance, this.name);
        }
        else {
            return String.format("%s : £(%.2f) : %s", this.uuid, -balance, this.name);
        }
    }  

    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactionList) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s\n", this.uuid);
        for (int t = this.transactionList.size() - 1; t>=0; --t){
            System.out.println(this.transactionList.get(t).getSummaryLine());
        } 
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {
        Transaction transaction = new Transaction(amount, memo, this);
        this.transactionList.add(transaction);
    }
}