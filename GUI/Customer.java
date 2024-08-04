
/**
 * Users of the Bank System
 *
 * @author Isah Aminu
 * @version 4/15/2024
 */
import java.util.Random;
public class Customer 
{
    private String username;
    private String password;
    private String name;
    private int accountNumber;
    private double balance;

    public Customer(String username, String password, String name, int accountNumber, double balance) 
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getName()
    {
        return name;
    }
    
    public String getUsername() 
    {
        return username;
    }

    public String getPassword() 
    {
        return password;
    }
    
    public int getAccountNumber()
    {
        return accountNumber;
    }
    
    public double getBalance()
    {
        return balance;
    }
    
    public void setBalance(double deposit)
    {
        balance = balance + deposit;
    }

    public boolean isLoginValid(String usernameInput, String passwordInput) 
    {
        return username.equals(usernameInput) && password.equals(passwordInput);
    }
}
