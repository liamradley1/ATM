import java.util.ArrayList;
class Banks {
    ArrayList<Bank> banks;
    
    public Banks() {
        this.banks = new ArrayList<Bank>();
    }

    public ArrayList<Bank>getBanks() {
        return this.banks;
    }

    public void addBank(Bank bank) {
        this.banks.add(bank);
    }
}