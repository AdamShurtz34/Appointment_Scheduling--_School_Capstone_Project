package Project;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;


// this is the class that starts up the application, as well as closes it down
public class Main extends Application {

    // set up the initial GUI page, which is the login form
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
        primaryStage.setTitle("Capstone Project");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    // this is the main method, where the program begins and ends
    public static void main(String[] args) {

        //upload data from database into the program - the records class
        try {
            // get all customers from the data base and upload them into the records class
            //  + "?connectionTimeZone = SERVER"
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
            PreparedStatement statementcustomer = connection.prepareStatement("select * from customers");
            ResultSet customerResults = statementcustomer.executeQuery();
            while (customerResults.next()) {
                int customerID = customerResults.getInt("Customer_ID");
                String customerName = customerResults.getString("Customer_Name");
                String address = customerResults.getString("Address");
                String postalCode = customerResults.getString("Postal_code");
                String phone = customerResults.getString("Phone");

                Timestamp creationDateTimeField = customerResults.getTimestamp("Create_Date");
                LocalDateTime creationDateTime = creationDateTimeField.toLocalDateTime();

                String createdBy = customerResults.getString("Created_By");

                Timestamp updatedDateTimeField = customerResults.getTimestamp("Last_Update");
                LocalDateTime updatedDateTime = updatedDateTimeField.toLocalDateTime();

                String updatedBy = customerResults.getString("Last_Updated_By");
                int divisionID = customerResults.getInt("Division_ID");
                Customer customer = new Customer(customerID, customerName, address, postalCode, phone, creationDateTime, createdBy, updatedDateTime, updatedBy, divisionID);
                Records.addCustomer(customer);
            }
            customerResults.close();

            //get all appointments and upload them to the records class
            PreparedStatement statementappointment = connection.prepareStatement("select * from appointments");
            ResultSet appointmentResults = statementappointment.executeQuery();
            ObservableList<Customer> customers = Records.getAllCustomers();
            while (appointmentResults.next()) {
                int appointmentID = appointmentResults.getInt("Appointment_ID");
                String title = appointmentResults.getString("Title");
                String description = appointmentResults.getString("Description");
                String location = appointmentResults.getString("Location");
                String type = appointmentResults.getString("Type");

                Object startDateTimeField = appointmentResults.getObject("Start");
                LocalDateTime startDateTime = (LocalDateTime) startDateTimeField;

                Object endDateTimeField = appointmentResults.getObject("End");
                LocalDateTime endDateTime = (LocalDateTime) endDateTimeField;

                Timestamp creationDateTimeField = appointmentResults.getTimestamp("Create_Date");
                LocalDateTime creationDateTime = creationDateTimeField.toLocalDateTime();

                String createdBy = appointmentResults.getString("Created_By");

                Timestamp updatedDateTimeField = appointmentResults.getTimestamp("Last_Update");
                LocalDateTime updatedDateTime = updatedDateTimeField.toLocalDateTime();

                String updatedBy = appointmentResults.getString("Last_Updated_By");

                int customerID = appointmentResults.getInt("Customer_ID");
                int userID = appointmentResults.getInt("User_ID");
                ;
                int contactID = appointmentResults.getInt("Contact_ID");
                ;


                Appointment appointment = new Appointment(appointmentID, title, description, location, type, startDateTime,
                        endDateTime, creationDateTime, createdBy, updatedDateTime, updatedBy, customerID, userID, contactID);
                Records.addAppointment(appointment);
                for (Customer customer: customers)
                {
                    if (customer.getCustomerID() == customerID)
                    {
                        customer.addAppointment(appointment);
                    }
                }
            }
            appointmentResults.close();

            // get all the users of the program and upload to the records class
            PreparedStatement statementuser = connection.prepareStatement("select * from users");
            ResultSet userResults = statementuser.executeQuery();
            while (userResults.next()) {
                int userID = userResults.getInt("User_ID");
                String userName = userResults.getString("User_Name");
                String password = userResults.getString("Password");

                Timestamp creationDateTimeField = userResults.getTimestamp("Create_Date");
                LocalDateTime creationDateTime = creationDateTimeField.toLocalDateTime();

                String createdBy = userResults.getString("Created_By");

                Timestamp updatedDateTimeField = userResults.getTimestamp("Last_Update");
                LocalDateTime updatedDateTime = updatedDateTimeField.toLocalDateTime();

                String updatedBy = userResults.getString("Last_Updated_By");

                User user = new User(userID, userName, password, creationDateTime, createdBy, updatedDateTime, updatedBy);
                Records.addUser(user);
            }
            userResults.close();

            // upload all of the contacts and add them to the records class
            PreparedStatement statementcontact = connection.prepareStatement("select * from contacts");
            ResultSet contactResults = statementcontact.executeQuery();
            while (contactResults.next()) {
                int contactID = contactResults.getInt("Contact_ID");
                String contactName = contactResults.getString("Contact_Name");
                String contactEmail = contactResults.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                Records.addContact(contact);
            }
            contactResults.close();

            // upload all countries to the records class in the program
            PreparedStatement statementcountry = connection.prepareStatement("select * from countries");
            ResultSet countryResults = statementcountry.executeQuery();
            while (countryResults.next()) {
                int countryID = countryResults.getInt("Country_ID");
                String countryName = countryResults.getString("Country");

                Timestamp creationDateTimeField = countryResults.getTimestamp("Create_Date");
                LocalDateTime creationDateTime = creationDateTimeField.toLocalDateTime();

                String createdBy = countryResults.getString("Created_By");

                Timestamp updatedDateTimeField = countryResults.getTimestamp("Last_Update");
                LocalDateTime updatedDateTime = updatedDateTimeField.toLocalDateTime();

                String updatedBy = countryResults.getString("Last_Updated_By");

                Country country = new Country(countryID, countryName, creationDateTime, createdBy, updatedDateTime, updatedBy);
                Records.addCountry(country);
            }
            countryResults.close();

            // upload all country divisions and add to the records class
            PreparedStatement statementFirstLevelDivision = connection.prepareStatement("select * from first_level_divisions");
            ResultSet firstResults = statementFirstLevelDivision.executeQuery();
            while (firstResults.next()) {
                int countryID = firstResults.getInt("Country_ID");
                String divisionName = firstResults.getString("Division");
                int divisionID = firstResults.getInt("Division_ID");

                Timestamp creationDateTimeField = firstResults.getTimestamp("Create_Date");
                LocalDateTime creationDateTime = creationDateTimeField.toLocalDateTime();

                String createdBy = firstResults.getString("Created_By");

                Timestamp updatedDateTimeField = firstResults.getTimestamp("Last_Update");
                LocalDateTime updatedDateTime = updatedDateTimeField.toLocalDateTime();

                String updatedBy = firstResults.getString("Last_Updated_By");

                FirstLevelDivision division = new FirstLevelDivision(divisionID, divisionName, creationDateTime, createdBy, updatedDateTime, updatedBy, countryID);
                Records.addFirstLevelDivision(division);
            }
            firstResults.close();
        } catch (Exception x) {
            x.printStackTrace();
        }


//-----------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------

        // create the test user account if it does not exist
        //launch the GUI program, opens the login page that was loaded at the top of this method
        ObservableList<User> allUsers = Records.getAllUsers();
        int count = 0;
        for (User user: allUsers)
        {
            if(user.getUserName().equals("test"))
            {
                if(user.getPassword().equals("test"))
                {
                    count = 1;
                }
            }
        }
        if(count == 0)
        {
            User newUser = new User(Records.getAllUsers().size()+1, "test", "test", LocalDateTime.now(), "Tester", LocalDateTime.now(), "Tester");
            Records.addNewUser(newUser);
        }


        launch(args);


//-----------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------

        // enter new data from the program into database
        // this program does not add new countries or divisions to the database
        try {
            //insert new customers into database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule" + "?connectionTimeZone = SERVER", "sqlUser", "Passw0rd!");
            //"jdbc:mysql://localhost:3306/JavaConnection" + "?useLegacyDatetimeCode=false&server Timezone=UTC"

            //insert new contacts into database
            PreparedStatement insertContact = connection.prepareStatement("insert into contacts values(?,?,?)");
            ObservableList<Contact> newContacts = Records.getRecentContacts();
            for (Contact contact : newContacts)
            {
                insertContact.setInt(1, contact.getContactID());
                insertContact.setString(2, contact.getContactName());
                insertContact.setString(3, contact.getEmail());

                insertContact.executeUpdate();
            }


        //insert new users into database
            PreparedStatement insertUser = connection.prepareStatement("insert into users values(?,?,?,?,?,?,?)");
            ObservableList<User> newUsers = Records.getRecentUsers();
            for (User user: newUsers)
            {
                insertUser.setInt(1, user.getUserID());
                insertUser.setString(2, user.getUserName());
                insertUser.setString(3, user.getPassword());
                insertUser.setTimestamp(4, Timestamp.valueOf(user.getCreationDateTime()));
                insertUser.setString(5, user.getCreatedBy());
                insertUser.setTimestamp(6, Timestamp.valueOf(user.getUpdatedDateTime()));
                insertUser.setString(7, user.getUpdatedBy());

                insertUser.executeUpdate();
            }

            connection.close();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }
}
