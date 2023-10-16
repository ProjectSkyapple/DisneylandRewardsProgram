public class PlatinumCustomer extends Customer {
    private int bonusBucks;

    public PlatinumCustomer(String first, String last, String id, double amount, int bonusBucks) {
        super(first, last, id, amount);
        this.bonusBucks = bonusBucks;
    }

    public int getBonusBucks() {
        return bonusBucks;
    }

    public void setBonusBucks(int bonusBucks) {
        this.bonusBucks = bonusBucks;
    }
}
