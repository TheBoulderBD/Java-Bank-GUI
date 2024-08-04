
/**
 * Layout of GUI for Bank System
 *
 * @author Isah Aminu
 * @version 4/21/2024
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Swingy implements ActionListener, Runnable
{
    private JFrame frame;
    private JPanel loginPanel;
    private JPanel accountPanel;
    private JPanel registerPanel;
    private JPanel depositPanel;
    private Controller controller;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    private JLabel nameLabel;
    private JLabel accountNumberLabel;
    private JLabel balanceLabel;
    private JLabel depositLabel;
    private JTextField depositField;
    private JTextField newUsernameInput;
    private JPasswordField newPasswordInput;

    public Swingy(Controller controller) 
    {
        this.controller = controller;

        // Frame setup
        frame = new JFrame("Bank System");
        frame.setSize(600,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Login screen
        loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        usernameInput = new JTextField(20);
        passwordInput = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);


        JButton registerSwitchButton = new JButton("Create New User");
        registerSwitchButton.addActionListener(this);

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameInput);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordInput);
        loginPanel.add(loginButton);
        loginPanel.add(registerSwitchButton);

        // Account information screen
        accountPanel = new JPanel(new GridLayout(3, 3));
        nameLabel = new JLabel();
        accountNumberLabel = new JLabel();
        balanceLabel = new JLabel();
        JLabel spaceLabel = new JLabel();
        spaceLabel.setText(" ");
        
        
        
        JButton depositSwitchButton = new JButton("Deposit");
        depositSwitchButton.addActionListener(this);
        JButton accountBackButton = new JButton("Back");
        accountBackButton.addActionListener(this);
        
        accountPanel.add(nameLabel);
        accountPanel.add(accountNumberLabel);
        accountPanel.add(balanceLabel);
        accountPanel.add(spaceLabel);
        accountPanel.add(depositSwitchButton);
        accountPanel.add(accountBackButton);
        
        // Deposit Screen
        depositPanel = new JPanel(new GridLayout(2,1)); 
        depositLabel = new JLabel("Deposit Amount:");
        depositField = new JTextField(10);
        
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        JButton depositBackButton = new JButton("Back");
        depositBackButton.addActionListener(this);
        
        depositPanel.add(depositLabel);
        depositPanel.add(depositField);
        depositPanel.add(depositButton);
        depositPanel.add(depositBackButton);

        // User Creation Screen
        registerPanel = new JPanel(new GridLayout(4, 2));
        JLabel newUsernameLabel = new JLabel("New Username:");
        JLabel newPasswordLabel = new JLabel("New Password:");
        JLabel newNameLabel = new JLabel("Name:");
        newUsernameInput = new JTextField(20);
        newPasswordInput = new JPasswordField(20);
        JTextField newNameField = new JTextField(20);
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        JButton registerBackButton = new JButton("Back");
        registerBackButton.addActionListener(this);
        
        
        registerPanel.add(newUsernameLabel);
        registerPanel.add(newUsernameInput);
        registerPanel.add(newPasswordLabel);
        registerPanel.add(newPasswordInput);
        registerPanel.add(newNameLabel);
        registerPanel.add(newNameField);
        registerPanel.add(registerButton);
        registerPanel.add(registerBackButton);

        // Card Layout to switch between screens
        frame.setLayout(new CardLayout());
        frame.add(loginPanel, "loginPanel");
        frame.add(accountPanel, "accountPanel");
        frame.add(registerPanel, "registerPanel");
        frame.add(depositPanel, "depositPanel");

        // Login panel is the beginning
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        cardLayout.show(frame.getContentPane(), "loginPanel");

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
        
        if ( e.getActionCommand().equals("Login") ) 
        {
            String inputUsername = usernameInput.getText();
            String inputPassword = new String(passwordInput.getPassword());
    
            if ( controller.loginUser(inputUsername, inputPassword) ) 
            {
                // Switch account screen
                cardLayout.show(frame.getContentPane(), "accountPanel");
                Customer customer = controller.findCustomer(inputUsername);
                displayAccountInfo(customer.getName(), customer.getAccountNumber(), customer.getBalance());
            } 
            else 
            {
                // Show error message or handle invalid credentials
                JOptionPane.showMessageDialog(frame, "Invalid username or password");
            }
            
        }
        else if ( e.getActionCommand().equals("Create New User") ) 
        {
            // Switch to creation screen
            cardLayout.show(frame.getContentPane(), "registerPanel");
        }
        else if ( e.getActionCommand().equals("Back") )
        {
            cardLayout.show(frame.getContentPane(), "loginPanel");
        }
            
        else if ( e.getActionCommand().equals("Register") ) 
        {
            String newUsername = newUsernameInput.getText();
            String newPassword = new String(newPasswordInput.getPassword());
            String newName = newUsernameInput.getText();
            int acctNum = controller.generateAccountNumber();
            if ( !newUsername.isEmpty() && !newPassword.isEmpty() && !newName.isEmpty() ) 
            {
                controller.addUser(newUsername, newPassword, newName, acctNum, 0.0);
                JOptionPane.showMessageDialog(frame, "Registration Successful");
                
                cardLayout.show(frame.getContentPane(), "loginPanel");
            } 
            else 
            {
                JOptionPane.showMessageDialog(frame, "All fields must have input");
            }
            
        }
        else if (e.getActionCommand().equals("Deposit")) 
        {  
            cardLayout.show(frame.getContentPane(), "depositPanel");
            
            String depositAmountStr = depositField.getText();
            
            if (!depositAmountStr.isEmpty()) 
            {
                double depositAmount = 0.0;
                try
                {
                    depositAmount = Double.parseDouble(depositAmountStr);
                }
                catch (NumberFormatException f)
                {
                    JOptionPane.showMessageDialog(frame, "Invalid deposit");
                }
                Customer customer = controller.findCustomer(usernameInput.getText());
                controller.setBalance(depositAmount, customer);
                
                balanceLabel.setText("Balance: $" + customer.getBalance());
                
                depositField.setText("");
                cardLayout.show(frame.getContentPane(), "accountPanel");
            } 
            else 
            {
                
                JOptionPane.showMessageDialog(frame, "Please enter a deposit amount");
            }
        }
    }


    private void displayAccountInfo(String name, int accountNumber, double balance) 
    {
        nameLabel.setText("Name: " + name);
        accountNumberLabel.setText("Account Number: " + accountNumber);
        balanceLabel.setText("Balance: $" + balance);
    }
    
    
    public void run()
    {
        
    }
}
