public class BankTest {

    public void testOpenAccount() {
        Bank bank = new Bank(6060);
        bank.run();
        String accountName = "John Doe";
        double initialBalance = 100.0;
        String accountNumber = bank.createAccount(accountName, initialBalance);

        // check if account was created
        if (accountNumber.equals(null)) {
            System.out.println("Failed to open account");
            return;
        }

        // check if account details are correct
        Bank.Account account = bank.getAccount(accountName);
        if (account == null || !account.getName().equals(accountName) ||
                account.getBalance() != initialBalance) {
            System.out.println("Account details are incorrect");
            return;
        }

        System.out.println("Account opened successfully");
    }

    public void testDeposit() {
        Bank bank = new Bank(6060);
        bank.run();
        String accountName = "Jane Smith";
        double initialBalance = 0.0;
        String accountNumber = bank.createAccount(accountName, initialBalance);

        // check if deposit was successful
        double amount = 50.0;
        bank.getAccount(accountName).deposit(amount);
        Bank.Account account = bank.getAccount(accountName);
        if (account == null || account.getBalance() != amount) {
            System.out.println("Deposit failed");
            return;
        }

        System.out.println("Deposit successful");
    }

    public void testWithdraw() throws Bank.InsufficientFundsException {
        Bank bank = new Bank(6060);
        bank.run();
        String accountName = "Bob Johnson";
        double initialBalance = 100.0;
        String accountNumber = bank.createAccount(accountName, initialBalance);

        // check if withdrawal was successful
        double amount = 50.0;
        bank.getAccount(accountName).withdraw(amount);
        Bank.Account account = bank.getAccount(accountName);
        if (account == null || account.getBalance() != initialBalance - amount) {
            System.out.println("Withdrawal failed");
            return;
        }

        System.out.println("Withdrawal successful");
    }

    public void testTransfer() throws Bank.InsufficientFundsException {
        Bank bank = new Bank(6060);
        bank.run();
        bank.createAccount("account1", 100);
        bank.createAccount("account2", 100);
        bank.transfer("account1", "account2", 100);
        System.out.println("Transfer successful");
        double balance1 = bank.getBalance("account1");
        double balance2 = bank.getBalance("account2");
        System.out.println("new balances:");
        System.out.println(balance1);
        System.out.println(balance2);
    }

    public static void main(String[] args) throws Bank.InsufficientFundsException {
        BankTest test = new BankTest();
        test.testOpenAccount();
        test.testDeposit();
        test.testWithdraw();
        test.testTransfer();
    }
}
