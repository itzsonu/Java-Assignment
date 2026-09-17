public class BankAccount {

    public static void main(String[] args) {
        BankAccount a = new BankAccount("A001", "Alice", 1000.0);
        BankAccount b = new BankAccount("B001", "Bob");

        a.deposit(250.0);
        a.withdraw(400.0);
        a.transferTo(b, 100.0);

        System.out.printf("A balance=%.2f%n", a.getBalance());
        System.out.printf("B balance=%.2f%n", b.getBalance());
        System.out.println("A transactionCount=" + a.getTransactionCount());
        System.out.println();
        System.out.println(a.statement());
    }

    private final String accountNumber;
    private final String holder;
    private double balance;
    private int transactionCount;
    private final String[] ledger = new String[20];

    public BankAccount(String accountNumber, String holder) {
        this(accountNumber, holder, 0.0);
    }

    public BankAccount(String accountNumber, String holder, double openingBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be blank");
        }
        if (holder == null || holder.trim().isEmpty()) {
            throw new IllegalArgumentException("Holder cannot be blank");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.holder = holder;
        this.balance = round2(openingBalance);
        this.transactionCount = 0;
        record("OPEN balance=" + String.format("%.2f", this.balance));
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance = round2(balance + amount);
        record("DEPOSIT " + String.format("%.2f", amount)
             + " balance=" + String.format("%.2f", balance));
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        balance = round2(balance - amount);
        record("WITHDRAW " + String.format("%.2f", amount)
             + " balance=" + String.format("%.2f", balance));
    }

    public void transferTo(BankAccount target, double amount) {
        if (target == null) {
            throw new IllegalArgumentException("Target account cannot be null");
        }
        if (target == this) {
            throw new IllegalArgumentException("Cannot transfer to self");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        this.withdraw(amount);
        target.deposit(amount);
    }

    public String statement() {
        StringBuilder sb = new StringBuilder();
        sb.append("Account: ").append(accountNumber).append("\n");
        sb.append("Holder: ").append(holder).append("\n");
        sb.append("Opening balance: ").append(String.format("%.2f", openingBalance())).append("\n");
        sb.append("Current balance: ").append(String.format("%.2f", balance)).append("\n");
        sb.append("Transactions:\n");
        for (int i = 0; i < transactionCount; i++) {
            sb.append(i + 1).append(". ").append(ledger[i]).append("\n");
        }
        return sb.toString();
    }

    private double openingBalance() {
        if (transactionCount == 0) {
            return balance;
        }
        String first = ledger[0];
        int idx = first.indexOf("balance=");
        if (idx >= 0) {
            return Double.parseDouble(first.substring(idx + 8));
        }
        return 0.0;
    }

    private void record(String entry) {
        if (transactionCount < ledger.length) {
            ledger[transactionCount] = entry;
        }
        transactionCount++;
    }

    private static double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}