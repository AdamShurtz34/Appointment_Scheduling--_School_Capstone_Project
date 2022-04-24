package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


// this class controls the "main window" GUI page / file
// the main window is where most actions that this program does takes place
// this page is the landing pad after logging in and is the return page from most other pages
public class MainWindowController {
    // fxml file fields
    @FXML private TableView<Customer> allCustomers;
    @FXML private TableColumn<Customer, Integer> Customer_ID;
    @FXML private TableColumn<Customer, String> Name;
    @FXML private TableColumn<Customer, String> Address;
    @FXML private TableColumn<Customer, String> Division;
    @FXML private TableColumn<Customer, String> Country;
    @FXML private TableColumn<Customer, String> Postal;
    @FXML private TableColumn<Customer, String> Phone;

    @FXML private TableView<Appointment> upcomingAppointments;
    @FXML private TableColumn<Appointment, Integer> AppointmentID;
    @FXML private TableColumn<Appointment, String> Title;
    @FXML private TableColumn<Appointment, String> Description;
    @FXML private TableColumn<Appointment, String> Location;
    @FXML private TableColumn<Appointment, String> Contact;
    @FXML private TableColumn<Appointment, String> Type;
    @FXML private TableColumn<Appointment, String> Start;
    @FXML private TableColumn<Appointment, String> End;
    @FXML private TableColumn<Appointment, Integer> CustomerID;

    @FXML private RadioButton Month;
    @FXML private RadioButton Week;
    @FXML private ToggleGroup appointmentToggle;

    @FXML private Label selectCustomerError;
    @FXML private Label selectCustomerErrorTwo;
    @FXML private Label selectAppointmentError;

    @FXML TextField customerSearch;
    @FXML TextField appointmentSearch;


    // sets up the page with all necessary data
    public void initialize()
    {
        // get the list of customers and appointments from the records class
        ObservableList<Customer> customers = Records.getAllCustomers();
        ObservableList<Appointment> appointments = Records.getAllAppointments();
        // show just the appointments for the current/logged-in user
        //ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
        //for (Appointment appointment2 : appointments)
        //{
        //    if (appointment2.getUser_ID() == Records.getCurrentUser().getUserID())
        //    {
        //        userAppointments.add(appointment2);
        //    }
        //}

        // initialize the appointment selection of "month" to show the appointments for the upcoming month in the
        // appointments table
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        LocalDateTime currentDateTime = LocalDateTime.now();
        for (Appointment appointment: appointments)
        {
            LocalDateTime upcomingAppointment = appointment.getStartDateTime();
            Duration difference = Duration.between(currentDateTime, upcomingAppointment);
            if (difference.toDays() <= 30 && difference.toMinutes() >= 0)
            {
                monthAppointments.add(appointment);
            }
        }

        // set up the tables and fill them with the customer and appointment data
        Customer_ID.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
        Name.setCellValueFactory(new PropertyValueFactory<Customer, String>("fullName"));
        Address.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        Division.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));
        Country.setCellValueFactory(new PropertyValueFactory<Customer, String>("countryName"));
        Postal.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        Phone.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));

        // establish search filter and table of the Parts Table
        FilteredList<Customer> filteredCustomers = new FilteredList<>(customers, p -> true);
        customerSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomers.setPredicate(customer -> {
                // display every part if filter is empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (customer.getFullName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by name
                }
                else if (String.valueOf(customer.getCustomerID()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by ID
                }
                return false; // if no match
            });
        });

        SortedList<Customer> sortedCustomers = new SortedList<>(filteredCustomers);
        sortedCustomers.comparatorProperty().bind(allCustomers.comparatorProperty());
        allCustomers.setItems(sortedCustomers);



        AppointmentID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));
        Title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        Description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        Location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        Contact.setCellValueFactory(new PropertyValueFactory<Appointment, String>("ContactName"));
        Type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
        Start.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartDateTimeUser"));
        End.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndDateTimeUser"));
        CustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));

        // establish search filter and table of the Products Table
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(appointments, p -> true);
        appointmentSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                // display every product if empty
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (appointment.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by name
                }
                else if (String.valueOf(appointment.getAppointment_ID()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter by ID
                }
                return false; // if no match
            });
        });

        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredAppointments);
        sortedAppointments.comparatorProperty().bind(upcomingAppointments.comparatorProperty());
        upcomingAppointments.setItems(sortedAppointments);



        //allCustomers.setItems(customers);
        //upcomingAppointments.setItems(monthAppointments);

        // set up the month/week radio button toggle for the appointments
        appointmentToggle = new ToggleGroup();
        this.Month.setToggleGroup(appointmentToggle);
        this.Week.setToggleGroup(appointmentToggle);
        this.Month.setSelected(true);

        selectCustomerError.setText("");
        selectAppointmentError.setText("");
        selectCustomerErrorTwo.setText("");
    }


    // action taken when the add appointment button is clicked
    public void AddAppointmentButton(ActionEvent event) throws IOException
    {
        try
        {
            // get the add appointment window loaded
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddAppointment.fxml"));
            // pass the selected customer to the add appointment class
            Customer selectedCustomer = allCustomers.getSelectionModel().getSelectedItem();
            AddAppointmentController.getSelectedCustomer(selectedCustomer);
            // go to the add appointment page
            Parent AddAppointment = loader.load();
            Scene AddAppointmentScene = new Scene(AddAppointment);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(AddAppointmentScene);
            window.show();
        }
        catch (IOException e) {
            selectCustomerErrorTwo.setText("Please select a customer");
            selectAppointmentError.setText("");
            selectCustomerError.setText("");
        }
    }

    // action taken when the add customer button is clicked
    public void AddCustomerButton(ActionEvent event) throws IOException
    {
        // go to the add customer page
        Parent addCustomer = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene addCustomerScene = new Scene(addCustomer);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addCustomerScene);
        window.show();
    }

    // the edit appointment button
    public void EditAppointmentButton(ActionEvent event) throws IOException
    {
        try{
            // load the edit appointment page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditAppointment.fxml"));
            // pass the selected appointment to the edit appointment class
            Appointment selectedAppointment = upcomingAppointments.getSelectionModel().getSelectedItem();
            EditAppointmentController.getSelectedAppointment(selectedAppointment);
            // go to the edit apponitment page
            Parent EditAppointment = loader.load();
            Scene EditAppointmentScene = new Scene(EditAppointment);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(EditAppointmentScene);
            window.show();
        }
        catch (IOException e) {
            selectAppointmentError.setText("Please select an appointment");
            selectCustomerError.setText("");
            selectCustomerErrorTwo.setText("");
        }
    }


    // edit customer button
    public void EditCustomerButton(ActionEvent event) throws IOException
    {
        try{
            // load the edit customer page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditCustomer.fxml"));
            // pass the selected customer
            Customer selectedCustomer = allCustomers.getSelectionModel().getSelectedItem();
            EditCustomerController.getSelectedCustomer(selectedCustomer);
            // display the edit customer page
            Parent EditCustomer = loader.load();
            Scene EditCustomerScene = new Scene(EditCustomer);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(EditCustomerScene);
            window.show();
        }
        catch (IOException e) {
            selectCustomerError.setText("Please select a customer");
            selectAppointmentError.setText("");
            selectCustomerErrorTwo.setText("");
        }
    }

    // delete appointment button
    public void DeleteAppointmentButton(ActionEvent event) throws IOException
    {
        try{
            // load the page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DeleteAppointment.fxml"));
            // pass the selected appointment
            Appointment selectedAppointment = upcomingAppointments.getSelectionModel().getSelectedItem();
            DeleteAppointmentController.getSelectedAppointment(selectedAppointment);
            // display the page
            Parent DeleteAppointment = loader.load();
            Scene DeleteAppointmentScene = new Scene(DeleteAppointment);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(DeleteAppointmentScene);
            window.show();
        }
        catch (IOException e) {
        selectAppointmentError.setText("Please select an appointment");
        selectCustomerError.setText("");
        selectCustomerErrorTwo.setText("");
        }
    }

    // delete customer button
    public void DeleteCustomerButton(ActionEvent event) throws IOException
    {
        try{
            // load the page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DeleteCustomer.fxml"));
            // pass the selected customer to delete
            Customer selectedCustomer = allCustomers.getSelectionModel().getSelectedItem();
            DeleteCustomerController.getSelectedCustomer(selectedCustomer);
            // display the page
            Parent DeleteCustomer = loader.load();
            Scene DeleteCustomerScene = new Scene(DeleteCustomer);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(DeleteCustomerScene);
            window.show();
        }
        catch (IOException e) {
            selectCustomerError.setText("Please select a customer");
            selectAppointmentError.setText("");
            selectCustomerErrorTwo.setText("");
        }

    }

    // view customer button
    public void ViewCustomerButton(ActionEvent event) throws IOException {
        try {
            // load the page
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ViewCustomer.fxml"));
            // pass the selected customer to display
            Customer selectedCustomer = allCustomers.getSelectionModel().getSelectedItem();
            ViewCustomerController.getSelectedCustomer(selectedCustomer);
            // display the page
            Parent ViewCustomer = loader.load();
            Scene ViewCustomerScene = new Scene(ViewCustomer);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(ViewCustomerScene);
            window.show();
        }
        catch (IOException e) {
            selectCustomerError.setText("Please select a customer");
            selectAppointmentError.setText("");
            selectCustomerErrorTwo.setText("");
        }
    }

    // create user button
    public void CreateUserButton(ActionEvent event) throws IOException
    {
        // go to the add user page
        Parent AddUser = FXMLLoader.load(getClass().getResource("AddUser.fxml"));
        Scene AddUserScene = new Scene(AddUser);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(AddUserScene);
        window.show();
    }

    // create contact button
    public void CreateContactButton(ActionEvent event) throws IOException
    {
        // go to the add contact page
        Parent AddContact = FXMLLoader.load(getClass().getResource("AddConctact.fxml"));
        Scene AddContactScene = new Scene(AddContact);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(AddContactScene);
        window.show();
    }


    // show appointments report button
    public void appointmentsReportButton(ActionEvent event) throws IOException
    {
        // go to the appointments report page
        Parent appointmentReport = FXMLLoader.load(getClass().getResource("AppointmentsReport.fxml"));
        Scene appointmentReportScene = new Scene(appointmentReport);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(appointmentReportScene);
        window.show();
    }

    // show contact schedule report button
    public void contactScheduleButton(ActionEvent event) throws IOException
    {
        // go to the appointments report page
        Parent contactReport = FXMLLoader.load(getClass().getResource("ContactSchedule.fxml"));
        Scene contactReportScene = new Scene(contactReport);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(contactReportScene);
        window.show();
    }


    // show customers report button
    public void customerReportButton(ActionEvent event) throws IOException
    {
        // go to the appointments report page
        Parent customerReport = FXMLLoader.load(getClass().getResource("CustomerReport.fxml"));
        Scene customerReportScene = new Scene(customerReport);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(customerReportScene);
        window.show();
    }

    // this method switches the view of upcoming appointments table
    // toggles between "month" view and "week" view
    public void SwitchAppointmentView(ActionEvent event) throws IOException
    {
        if (this.appointmentToggle.getSelectedToggle().equals(this.Month))
        {
            // code to display the "month" view
            ObservableList<Appointment> appointments = Records.getAllAppointments();
            ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
            LocalDateTime currentDateTime = LocalDateTime.now();
            for (Appointment appointment: appointments)
            {
                LocalDateTime upcomingAppointment = appointment.getStartDateTime();
                Duration difference = Duration.between(currentDateTime, upcomingAppointment);
                if (difference.toDays() <= 30 && difference.toMinutes() >= 0)
                {
                    monthAppointments.add(appointment);
                }
            }

            AppointmentID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));
            Title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            Description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            Location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
            Contact.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            Type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
            Start.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartDateTimeUser"));
            End.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndDateTimeUser"));
            CustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));
            upcomingAppointments.setItems(monthAppointments);
        }

        // code to display the "week" view
        if (this.appointmentToggle.getSelectedToggle().equals(this.Week))
        {
            ObservableList<Appointment> appointments = Records.getAllAppointments();
            ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
            LocalDateTime currentDateTime = LocalDateTime.now();
            for (Appointment appointment: appointments)
            {
                LocalDateTime upcomingAppointment = appointment.getStartDateTime();
                Duration difference = Duration.between(currentDateTime, upcomingAppointment);
                if (difference.toDays() <= 7 && difference.toMinutes() >= 0)
                {
                    weekAppointments.add(appointment);
                }
            }

            AppointmentID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Appointment_ID"));
            Title.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
            Description.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
            Location.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
            Contact.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Contact"));
            Type.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Type"));
            Start.setCellValueFactory(new PropertyValueFactory<Appointment, String>("StartDateTimeUser"));
            End.setCellValueFactory(new PropertyValueFactory<Appointment, String>("EndDateTimeUser"));
            CustomerID.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("Customer_ID"));
            upcomingAppointments.setItems(weekAppointments);
        }
    }

    // exit program button
    public void ExitButton(ActionEvent event) throws IOException
    {
        // close out of the GUI program
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
