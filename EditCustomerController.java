package Project;

import javafx.collections.FXCollections;
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


// this class controls the "Edit Customer" GUI page / file
// edits the customer by setting values to the customer object
public class EditCustomerController {
    // fxml file fields
    @FXML private ComboBox<String> Country;
    @FXML private ComboBox<String> DivisionField;
    @FXML private TextField CustomerIDField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalField;
    @FXML private TextField phoneField;
    @FXML private Label blankError;
    // get the selected customer
    private static Customer customer;
    public static void getSelectedCustomer(Customer selectedCustomer) { customer = selectedCustomer; }

    // set up the page, set all the fields with the values from the selected customer
    public void initialize()
    {
        int countryID = 0;
        ObservableList<Country> countries = Records.getAllCountries();
        CustomerIDField.setText(String.valueOf(customer.getCustomerID()));
        nameField.setText(customer.getFullName());
        addressField.setText(customer.getAddress());
        postalField.setText(String.valueOf(customer.getPostalCode()));
        phoneField.setText(String.valueOf(customer.getPhoneNumber()));


        for (Country country: countries)
        {
            if(country.getCountry().equals(customer.getCountryName()))
            {
                countryID = country.getCountryID();
            }
        }
        Country.setItems(Records.getAllCountryNames());
        Country.setValue(customer.getCountryName());
        DivisionField.setItems(Records.getAllDivisionNames(countryID));
        DivisionField.setValue(customer.getDivisionName());
        blankError.setText("");
    }

    // action taken when the country is changed through the combo box drop down
    // change the division options to match the country
    public void switchCountry(ActionEvent event) throws IOException
    {
        int countryID = 0;
        ObservableList<Country> countries = Records.getAllCountries();
        ObservableList<FirstLevelDivision> divisions = Records.getAllFirstLevelDivisions();
        ObservableList<String> displayDivisions = FXCollections.observableArrayList();
        String countryName2 = Country.getValue();
        for (Country country: countries)
        {
            if (country.getCountry().equals(countryName2))
            {
                countryID = country.getCountryID();
            }
        }
        for (FirstLevelDivision Division: divisions)
        {
            if (Division.getCountryID() == countryID)
            {
                displayDivisions.add(Division.getDivision());
            }
        }
        DivisionField.setItems(displayDivisions);
    }


    // action taken when the save button is clicked
    // get the values from fields and set them to the customer object
    public void editCustomer(ActionEvent event) throws IOException
    {
        try {
            int divisionID = 0;
            ObservableList<FirstLevelDivision> divisions = Records.getAllFirstLevelDivisions();
            String division = DivisionField.getValue();
            int custID = Integer.parseInt(CustomerIDField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
            String postalCode = postalField.getText();
            String phone = phoneField.getText();


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

            //update values in records
            customer.setFullName(name);
            customer.setAddress(address);
            customer.setPostalCode(postalCode);
            customer.setPhoneNumber(phone);
            customer.setUpdatedDateTime(LocalDateTime.now());
            customer.setUpdated_By_User(Records.getCurrentUser().getUserName());
            for (FirstLevelDivision Division : divisions) {
                if (Division.getDivision().equals(division)) {
                    divisionID = Division.getDivisionID();
                }
            }
            customer.setDivisionID(divisionID);

            // update values in database
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule" + "?connectionTimeZone = SERVER", "sqlUser", "Passw0rd!");
                PreparedStatement edit = connection.prepareStatement("UPDATE customers set Customer_Name = ?, Address = ?, " +
                        "Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? where Customer_ID = ?");
                edit.setString(1, name);
                edit.setString(2, address);
                edit.setString(3, postalCode);
                edit.setString(4, phone);
                edit.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                edit.setString(6, Records.getCurrentUser().getUserName());
                edit.setInt(7, divisionID);
                edit.setInt(8, customer.getCustomerID());
                edit.executeUpdate();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            // go back to the main window
            Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        }
        catch (IllegalArgumentException e) {
            blankError.setText("Make sure all fields are filled");
        }
    }


    // action taken when the cancel button is clicked
    public void cancelButton(ActionEvent event) throws IOException
    {
        // go back to the main window
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
