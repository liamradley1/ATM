import java.util.Date;

class Transaction {
    
    private double amount; 

    private Date timestamp;

    private String memo;

    private Account account;

    public Transaction (double amount , Account account) {
        this.amount = amount; 
        this.account = account; 
        this.timestamp = new Date();
        this.memo = "";
    }

    public Transaction (double amount, String memo , Account account) {
        this(amount, account);
        this.memo = memo;
    }

    public Transaction (Account account, double amount, String memo){
        this(amount, account);
        this.memo = memo;
    }
    public double getAmount(){
        return this.amount;
    }

    public String getSummaryLine() {
        if(this.amount >=0) {
            return String.format("%s : £%.2f : %s", this.timestamp.toString(), this.amount, this.memo);
        }
        else {
            return String.format("%s : £(%.2f) : %s", this.timestamp.toString(), -this.amount, this.memo);
        }
    }


}