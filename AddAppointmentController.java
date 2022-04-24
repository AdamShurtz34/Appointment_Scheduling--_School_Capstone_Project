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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.*;

// the purpose of this class is to control the "Add Appointment" GUI page/file
// this class adds appointments to the records class
public class AddAppointmentController {
    // fxml file fields
    @FXML ComboBox StartTime;
    @FXML ComboBox EndTime;
    @FXML private DatePicker StartDate;
    @FXML private DatePicker EndDate;
    @FXML private TextField Appointment_ID;
    @FXML private TextField Title;
    @FXML private TextField Description;
    @FXML private TextField Location;
    @FXML private TextField Type;
    @FXML private TextField Customer_ID;
    @FXML private TextField UserID;
    @FXML private Label blankError;
    @FXML private Label overlappingError;
    @FXML private Label userExist;
    @FXML private ComboBox selectContact;
    //variables used in this class
    private static ObservableList<Appointment> allAppointments = Records.getAllAppointments();
    private static Customer customer;
    public static void getSelectedCustomer(Customer selectedCustomer) { customer = selectedCustomer; }
    int indexC = customer.getCustomerID();
    private static ObservableList<String> times  = FXCollections.observableArrayList("00:00", "00:30", "01:00", "01:30",
            "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30",
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00","11:30", "12:00", "14:00", "14:30", "15:00",
            "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00", "23:30");
    private static ObservableList<Contact> allContacts = Records.getAllContacts();

    // initialize the fxml file
    public void initialize()
    {
        // get all contact names and add it to the drop down selection
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        for (Contact contact: allContacts)
        {
            contactNames.add(contact.getContactName());
        }
        selectContact.setItems(contactNames);
        //set up the appointment ID - add one to the current highest ID for next ID
        int max = 0;
        for (Appointment appointment: allAppointments)
        {
             if (appointment.getAppointment_ID() > max)
             {
                 max = appointment.getAppointment_ID();
             }
        }
        // set values into fields
        Appointment_ID.setText(String.valueOf(max + 1));
        Customer_ID.setText(String.valueOf(indexC));
        UserID.setText(String.valueOf(Records.getCurrentUser().getUserID()));

        StartTime.setItems(times);
        EndTime.setItems(times);

        blankError.setText("");
        overlappingError.setText("");
        userExist.setText("");

    }


    // Actions that are taken when the save button is clicked (add appointment)
    public void SaveButton(ActionEvent event) throws IOException
    {
        try
        {
            // get the values from all fields
            int appointmentID = Integer.parseInt(Appointment_ID.getText());
            String title = Title.getText();
            String description = Description.getText();
            String location = Location.getText();
            String type = Type.getText();
            //convert start date and time into LocalDateTime class
            if(StartDate.getValue() == null)
            {
                throw new IllegalArgumentException();
            }
            LocalDate dateStart = StartDate.getValue();
            if(StartTime.getValue() == null)
            {
                throw new IllegalArgumentException();
            }
            String timeStart = StartTime.getValue().toString();
            int hourStart = Integer.parseInt(Character.toString(timeStart.charAt(0)) + Character.toString(timeStart.charAt(1)));
            int minuteStart = Integer.parseInt(Character.toString(timeStart.charAt(3)) + Character.toString(timeStart.charAt(4)));
            LocalTime timeStart2 = LocalTime.of(hourStart, minuteStart);
            LocalDateTime startDateTime = LocalDateTime.of(dateStart, timeStart2);
            //convert end date and time into LocalDateTime class
            if(EndDate.getValue() == null)
            {
                throw new IllegalArgumentException();
            }
            LocalDate dateEnd = EndDate.getValue();
            if(EndTime.getValue() == null)
            {
                throw new IllegalArgumentException();
            }
            String timeEnd = EndTime.getValue().toString();
            int hourEnd = Integer.parseInt(Character.toString(timeEnd.charAt(0)) + Character.toString(timeEnd.charAt(1)));
            int minuteEnd = Integer.parseInt(Character.toString(timeEnd.charAt(3)) + Character.toString(timeEnd.charAt(4)));
            LocalTime timeEnd2 = LocalTime.of(hourEnd, minuteEnd);
            LocalDateTime endDateTime = LocalDateTime.of(dateEnd, timeEnd2);

            //------------------------------------------------------------------------------------------------------------------------------
            // check for empty fields
            if(title.equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(description.equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(location.equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(type.equals(""))
            {
                throw new IllegalArgumentException();
            }


            // check to make sure selected time falls within 8 am and 10 pm ESt
            ZonedDateTime zonedStart = startDateTime.atZone(ZoneId.of(ZoneId.systemDefault().getId()));
            ZonedDateTime zonedStart2 = zonedStart.withZoneSameInstant(ZoneId.of("America/New_York"));

            LocalTime dayStart = LocalTime.of(8, 0);
            LocalTime dayEnd = LocalTime.of(22, 0);
            if(zonedStart2.toLocalTime().compareTo(dayStart) < 0)
            {
                // selected appointment time is before 8 am EST
                overlappingError.setText("selected time is before business hours EST");
                throw new Exception();

            }
            if(zonedStart2.toLocalTime().compareTo(dayEnd) > 0)
            {
                // selected appointment time is after 10 pm EST
                overlappingError.setText("selected time is after business hours EST");
                throw new Exception();
            }

            ZonedDateTime zonedEnd = endDateTime.atZone(ZoneId.of(ZoneId.systemDefault().getId()));
            ZonedDateTime zonedEnd2 = zonedEnd.withZoneSameInstant(ZoneId.of("America/New_York"));


            // check to make sure that the User ID and Contact ID exist in the database and that they are filled
            String contactName = selectContact.getValue().toString();
            int contactID = 1;
            for (Contact contact: allContacts)
            {
                if (contactName == contact.getContactName())
                {
                    contactID = contact.getContactID();
                }
            }

            int customerID = Integer.parseInt(Customer_ID.getText());

            String userID = UserID.getText();
            int userID2 = Integer.parseInt(userID);
            if(userID2 > Records.getAllUsers().size())
            {
                throw new NumberFormatException();
            }


            //check for overlapping appointments for the user
            for (Appointment appointment: allAppointments)
            {
                if(appointment.getUser_ID() == userID2)
                {
                    LocalDateTime startEST = appointment.getStartDateTime();
                    LocalDateTime endEST = appointment.getEndDateTime();
                    ZonedDateTime zonedStartEST = startEST.atZone(ZoneId.of("America/New_York"));
                    ZonedDateTime zonedStartLOCAL = zonedStartEST.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));
                    ZonedDateTime zonedEndEST = endEST.atZone(ZoneId.of("America/New_York"));
                    ZonedDateTime zonedEndLOCAL = zonedEndEST.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));
                    LocalTime starttime = zonedStartLOCAL.toLocalTime();
                    LocalTime endtime = zonedEndLOCAL.toLocalTime();
                    // see if start time conflicts
                    if((timeStart2.compareTo(starttime) >= 0) && (timeStart2.compareTo(endtime) < 0))
                    {
                        if(appointment.getStartDateTime().toLocalDate().equals(dateStart))
                        {
                            overlappingError.setText("The selected time overlaps with another appointment");
                            throw new Exception();
                        }
                    }
                    // see if end time conflicts
                    if((timeEnd2.compareTo(starttime) > 0) && (timeEnd2.compareTo(endtime) <= 0))
                    {
                        if(appointment.getStartDateTime().toLocalDate().equals(dateStart))
                        {
                            overlappingError.setText("The selected time overlaps with another appointment");
                            throw new Exception();
                        }
                    }
                    // see if another appointment starts and ends between the new appointment
                    if((starttime.compareTo(timeStart2) >= 0) && (endtime.compareTo(timeEnd2) <= 0))
                    {
                        if(appointment.getStartDateTime().toLocalDate().equals(dateStart))
                        {
                            overlappingError.setText("The selected time overlaps with another appointment");
                            throw new Exception();
                        }
                    }
                }
            }


            // add appointment to records
            Appointment appointment = new Appointment(appointmentID, title, description, location, type, zonedStart2.toLocalDateTime(),
                    zonedEnd2.toLocalDateTime(), LocalDateTime.now(), Records.getCurrentUser().getUserName(), LocalDateTime.now(),
                    Records.getCurrentUser().getUserName(), customerID, userID2, contactID);
            Records.addAppointment(appointment);
            customer.addAppointment(appointment);

            // + "?connectionTimeZone = SERVER"
            // add appointment to database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
            PreparedStatement insertAppointment = connection.prepareStatement("insert into appointments values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            insertAppointment.setInt(1, appointmentID);
            insertAppointment.setString(2, title);
            insertAppointment.setString(3, description);
            insertAppointment.setString(4, location);
            insertAppointment.setString(5, type);
            insertAppointment.setTimestamp(6, Timestamp.valueOf(zonedStart2.toLocalDateTime()));
            insertAppointment.setTimestamp(7, Timestamp.valueOf(zonedEnd2.toLocalDateTime()));
            insertAppointment.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            insertAppointment.setString(9, Records.getCurrentUser().getUserName());
            insertAppointment.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            insertAppointment.setString(11, Records.getCurrentUser().getUserName());
            insertAppointment.setInt(12, customerID);
            insertAppointment.setInt(13, userID2);
            insertAppointment.setInt(14, contactID);
            insertAppointment.executeUpdate();
            connection.close();

            // go back to the main screen
            Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        }
        catch (NumberFormatException x){
            userExist.setText("UserID does not exist");
            overlappingError.setText("");
            blankError.setText("");
        }
        catch (IllegalArgumentException e)
        {
            blankError.setText("Make sure all fields are filled");
            overlappingError.setText("");
            userExist.setText("");
        }
        catch (Exception x){
            blankError.setText("");
            userExist.setText("");
        }
    }




    // action that takes place when cancel button is clicked
    public void cancelButton(ActionEvent event) throws IOException
    {
        //go back to the main screen
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
