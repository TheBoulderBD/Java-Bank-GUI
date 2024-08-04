
/**
 * Main method that runs code for Bank System
 *
 * @author Isah Aminu
 * @version 4/15/2024
 */
import javax.swing.*;

public class Driver
{
    public static void main(String[] args) 
    {
        Bank bank = new Bank("Chase");
        Controller controller = new Controller(bank);
        Swingy gui = new Swingy(controller);
        SwingUtilities.invokeLater(gui);
        bank.saveDataToFile();
    }
}
