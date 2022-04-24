package Project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;


// this class controls the "Add Customer" GUI page/file
// adds a customer to the records class
public class AddCustomerController {
    // fxml fields
    @FXML private ComboBox<String> Country;
    @FXML private ComboBox<String> DivisionField;
    @FXML private TextField CustomerIDField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalField;
    @FXML private TextField phoneField;
    @FXML private Label blankError;
    // class variables
    private static ObservableList<Customer> allCustomers = Records.getAllCustomers();

    // set up / initialize the page/file
    public void initialize()
    {
        // get the country ID of the united states so that we can get all of the states (divisions) that are connected
        int countryID = 0;
        ObservableList<Country> countries = Records.getAllCountries();
        for (Country country: countries)
        {
            if(country.getCountry().equals("United States"))
            {
                countryID = country.getCountryID();
            }
        }
        // get the customerID to put in the field - add 1 to the current highest customer ID
        int max = 0;
        for (Customer customer: allCustomers)
        {
            if (customer.getCustomerID() > max)
            {
                max = customer.getCustomerID();
            }
        }
        // fill fields with values
        CustomerIDField.setText(String.valueOf(max + 1));
        Country.setItems(Records.getAllCountryNames());
        Country.setValue("United States");
        DivisionField.setItems(Records.getAllDivisionNames(countryID));
        DivisionField.setValue("Utah");
        blankError.setText("");
    }


    // action taken when you switch the country from the country drop down combo box
    // get the new country ID and get all the divisions that are connected to the country
    // fill the divisions combo box
    public void switchCountry(ActionEvent event) throws IOException
    {
        int countryID = 0;
        ObservableList<Country> countries = Records.getAllCountries();
        String countryName2 = Country.getValue();
        for (Country country: countries)
        {
            if (country.getCountry().equals(countryName2))
            {
                countryID = country.getCountryID();
            }
        }
        DivisionField.setItems(Records.getAllDivisionNames(countryID));
    }


    // action taken when the save button is clicked
    // create a customer object and add it to the records class
    // go back to the main window
    public void addCustomer(ActionEvent event) throws IOException
    {
        try {
            int divisionID = 0;
            ObservableList<FirstLevelDivision> divisions = Records.getAllFirstLevelDivisions();
            int custID = Integer.parseInt(CustomerIDField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String postalCode = postalField.getText();
            String phone = phoneField.getText();
            String division = DivisionField.getValue();

            for (FirstLevelDivision Division : divisions) {
                if (Division.getDivision().equals(division)) {
                    divisionID = Division.getDivisionID();
                }
            }

            if(CustomerIDField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(nameField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(addressField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(postalField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(phoneField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(DivisionField.getValue() == null)
            {
                throw new IllegalArgumentException();
            }

            //add customer to records
            Customer customer = new Customer(custID, name, address, postalCode, phone, LocalDateTime.now(), Records.getCurrentUser().getUserName(), LocalDateTime.now(), Records.getCurrentUser().getUserName(), divisionID);
            Records.addCustomer(customer);

            //add customer to database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule" + "?connectionTimeZone = SERVER", "sqlUser", "Passw0rd!");
            PreparedStatement insertCustomer = connection.prepareStatement("insert into customers values(?,?,?,?,?,?,?,?,?,?)");
            insertCustomer.setInt(1, customer.getCustomerID());
            insertCustomer.setString(2, customer.getFullName());
            insertCustomer.setString(3, customer.getAddress());
            insertCustomer.setString(4, customer.getPostalCode());
            insertCustomer.setString(5, customer.getPhoneNumber());
            insertCustomer.setTimestamp(6, Timestamp.valueOf(customer.getCreationDateTime()));
            insertCustomer.setString(7, customer.getCreated_By_User());
            insertCustomer.setTimestamp(8, Timestamp.valueOf(customer.getUpdatedDateTime()));
            insertCustomer.setString(9, customer.getUpdated_By_User());
            insertCustomer.setInt(10, customer.getDivisionID());
            insertCustomer.executeUpdate();
            connection.close();

            Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        }
        catch (IllegalArgumentException | SQLException e) {
            blankError.setText("Make sure all fields are filled");
        }
    }

    // action taken when the cancel button is clicked
    // go back to the main window
    public void cancelButton(ActionEvent event) throws IOException
    {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
