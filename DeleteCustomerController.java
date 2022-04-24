package Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// this class controls the "Delete Customer" GUI page / file
// deletes the selected customer from the records and database
public class DeleteCustomerController {
    // get the selected customer for deleting
    private static Customer customer;
    public static void getSelectedCustomer(Customer selectedCustomer) { customer = selectedCustomer; }
    private int customerID = customer.getCustomerID();

    // action taken when the delete button is clicked
    public void deleteButton(ActionEvent event) throws IOException
    {
        // delete customer from database
        try
        {
            int customerID = customer.getCustomerID();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule" + "?connectionTimeZone = SERVER", "sqlUser", "Passw0rd!");
            PreparedStatement delete = connection.prepareStatement("delete from customers where Customer_ID = ?");
            delete.setInt(1,customerID);
            delete.execute();
            connection.close();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }

        // delete customer from records class
        try {
            Records.deleteCustomer(customer);

            // go back to main window
            Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cancelButton(ActionEvent event) throws IOException
    {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
