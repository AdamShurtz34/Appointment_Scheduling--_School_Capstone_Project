package Project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

// this class controls the view customer GUI page / file
public class ViewCustomerController {
    // fxml file fields
    @FXML private TableView<Appointment> customerAppointments;
    @FXML private TableColumn<Appointment, Integer> AppointmentID;
    @FXML private TableColumn<Appointment, String> Title;
    @FXML private TableColumn<Appointment, String> Description;
    @FXML private TableColumn<Appointment, String> Location;
    @FXML private TableColumn<Appointment, String> Contact;
    @FXML private TableColumn<Appointment, String> Type;
    @FXML private TableColumn<Appointment, String> Start;
    @FXML private TableColumn<Appointment, String> End;
    @FXML private TableColumn<Appointment, Integer> CustomerID;

    @FXML private TextField Country;
    @FXML private TextField DivisionField;
    @FXML private TextField CustomerIDField;
    @FXML private TextField FullNameField;
    @FXML private TextField AddressField;
    @FXML private TextField PostalCodeField;
    @FXML private TextField PhoneNumberField;

    // get the selected customer to view
    private static Customer customer;
    public static void getSelectedCustomer(Customer selectedCustomer) { customer = selectedCustomer; }

    // set up the fields with the data from the customer
    // fields cannot be changed since this is for viewing purposes only
    public void initialize()
    {
        CustomerIDField.setText(String.valueOf(customer.getCustomerID()));
        FullNameField.setText(customer.getFullName());
        AddressField.setText(customer.getAddress());
        PostalCodeField.setText(String.valueOf(customer.getPostalCode()));
        PhoneNumberField.setText(customer.getPhoneNumber());
        Country.setText(customer.getCountryName());
        DivisionField.setText(customer.getDivisionName());


        ObservableList<Appointment> appointments = customer.getAllAppointments();

        AppointmentID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<Appointment, String>("ContactName"));
        Type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
        Start.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartDateTimeUser"));
        End.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndDateTimeUser"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));

        customerAppointments.setItems(appointments);
    }


    // close button
    public void closeButton(ActionEvent event) throws IOException
    {
        // return back to the main window page
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
