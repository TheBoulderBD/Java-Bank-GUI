
/**
 * Controls interactions between gui and other objects
 *
 * @author Isah Aminu
 * @version 4/15/2024
 */

import java.util.Random;

public class Controller 
{
    private Bank bank;

    public Controller(Bank bank) 
    {
        this.bank = bank;
    }
    

    public boolean loginUser(String username, String password) 
    {
        Customer customer = findCustomer(username);
        if (customer != null && customer.isLoginValid(username, password)) 
        {
            return true;
        }
        return false;
    }
    
    public Customer findCustomer(String username) 
    {
        for (Customer customer : bank.getCustomers()) 
        {
            if (customer.getUsername().equals(username)) 
            {
                return customer;
            }
        }
        return null;
    }
    
    public void addUser(String username, String password, String name, int accountNumber, double balance) 
    {
        Customer customer = new Customer(username, password, name, accountNumber, balance);
        bank.addCustomer(customer);
    }

    public int generateAccountNumber() 
    {
        Random random = new Random();
        int acctNum = random.nextInt(900000) + 100000;
        while (bank.findCustomerByAccountNumber(acctNum) != null) 
        {
            acctNum = random.nextInt(900000) + 100000; 
        }
        return acctNum;
    }
    
    public void setBalance(double deposit, Customer customer)
    {
        customer.setBalance(deposit);
        bank.saveDataToFile();
    }
}
