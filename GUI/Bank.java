
/**
 * The class for Bank objects
 *
 * @author Isah Aminu
 * @version 4/17/2024
 */
import java.io.*;
import java.util.ArrayList;

public class Bank 
{
    private ArrayList<Customer> customers;
    private String filename; 
    private String name;

    public Bank(String name) 
    {
        this.name = name;
        filename = "customers.txt";
        customers = new ArrayList<Customer>();
        loadDataFromFile();
    }

    public void loadDataFromFile() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];
                String name = data[2];
                int accountNumber = Integer.parseInt(data[3]);
                double balance = Double.parseDouble(data[4]);
                Customer customer = new Customer(username, password, name,accountNumber, balance);
                customers.add(customer);
            }
        } 
        catch (IOException e) 
        {
            
            e.printStackTrace();
        }

    }

    public void saveDataToFile() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) 
        {
            for (Customer customer : customers) 
            {
                writer.write(customer.getUsername() + "," + customer.getPassword() + "," +
                customer.getName() + "," + customer.getAccountNumber() + "," + customer.getBalance());
                writer.newLine();
            }
        } 
        catch (IOException e) 
        {
            
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) 
    {
        customers.add(customer);
        saveDataToFile();
    }

    
    public Customer findCustomerByAccountNumber(int accountNumber) 
    {
        for (Customer customer : customers) 
        {
            if (customer.getAccountNumber() == accountNumber) 
            {
                return customer;
            }
        }
        return null;
    }

    public ArrayList<Customer> getCustomers() 
    {
        return customers;
    }
}
