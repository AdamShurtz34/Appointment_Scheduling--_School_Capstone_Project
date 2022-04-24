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
import java.sql.*;
import java.time.*;

// this class controls the "Edit Appointment" GUI page / file
// this class edits the appointment in the records class
public class EditAppointmentController {
    // fxml fields
    @FXML private ComboBox StartTime;
    @FXML private ComboBox EndTime;
    @FXML private DatePicker StartDate;
    @FXML private DatePicker EndDate;
    @FXML private TextField AppointmentID;
    @FXML private TextField Title;
    @FXML private TextField Description;
    @FXML private TextField Location;
    @FXML private TextField Type;
    @FXML private TextField CustomerID;
    @FXML private TextField UserID;
    @FXML private Label blankError;
    @FXML private Label overlappingError;
    @FXML private Label userError;
    @FXML private ComboBox selectContact;

    // get the selected appointment
    private static Appointment appointment;
    public static void getSelectedAppointment(Appointment selectedAppointment) { appointment = selectedAppointment; }
    // class variables
    private static ObservableList<String> times  = FXCollections.observableArrayList("00:00", "00:30", "01:00", "01:30",
            "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30",
            "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00","11:30", "12:00", "14:00", "14:30", "15:00",
            "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00", "23:30");
    private static ObservableList<Contact> allContacts = Records.getAllContacts();

    // set up / initialize the page
    public void initialize()
    {
        ObservableList<String> contactNames = FXCollections.observableArrayList();
        // get values from the selected appointment object and fill fields
        AppointmentID.setText(String.valueOf(appointment.getAppointment_ID()));
        CustomerID.setText(String.valueOf(appointment.getCustomer_ID()));
        Title.setText(appointment.getTitle());
        Description.setText(appointment.getDescription());
        Location.setText(appointment.getLocation());
        Type.setText(appointment.getType());
        UserID.setText(String.valueOf(appointment.getUser_ID()));

        int contactID = appointment.getContact();
        String contactName = "";
        for (Contact contact:allContacts)
        {
            contactNames.add(contact.getContactName());
            if(contact.getContactID() == contactID)
            {
                contactName = contact.getContactName();
            }
        }
        selectContact.setItems(contactNames);
        selectContact.setValue(contactName);

        StartDate.setValue(appointment.getStartDateTime().toLocalDate());
        EndDate.setValue(appointment.getEndDateTime().toLocalDate());

        // get localdatetime (should be in eastern)
        // convert to zoneddatetime in eastern
        // convert to local zoneddatetime
        // set local time to combobox
        LocalDateTime startDateTime = appointment.getStartDateTime();
        LocalDateTime endDateTime = appointment.getEndDateTime();
        ZonedDateTime zonedStart = startDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedStart2 = zonedStart.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));
        ZonedDateTime zonedEnd = endDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedEnd2 = zonedEnd.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));
        LocalTime starttime = zonedStart2.toLocalTime();
        LocalTime endtime = zonedEnd2.toLocalTime();
        String startTime = starttime.toString();
        String endTime = endtime.toString();
        StartTime.setItems(times);
        EndTime.setItems(times);
        StartTime.setValue(startTime);
        EndTime.setValue(endTime);

        blankError.setText("");
        overlappingError.setText("");
        userError.setText("");
    }

    // action taken when the save button is clicked
    // get values from fields and 'set' them to the object
    public void SaveButton(ActionEvent event) throws IOException
    {
        try {
            //get field values
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


            ZonedDateTime zonedStart = startDateTime.atZone(ZoneId.of(ZoneId.systemDefault().getId()));
            ZonedDateTime zonedStart2 = zonedStart.withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime zonedEnd = endDateTime.atZone(ZoneId.of(ZoneId.systemDefault().getId()));
            ZonedDateTime zonedEnd2 = zonedEnd.withZoneSameInstant(ZoneId.of("America/New_York"));

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


            int contactID = 1;
            String contactName = selectContact.getValue().toString();
            for (Contact contact: allContacts)
            {
                if(contact.getContactName().equals(contactName))
                {
                    contactID = contact.getContactID();
                }
            }



            String userID = UserID.getText();
            int userID2 = Integer.parseInt(userID);
            if(userID2 > Records.getAllUsers().size())
            {
                throw new NumberFormatException();
            }

            //check for overlapping appointments for the user
            ObservableList<Appointment> allAppointments = Records.getAllAppointments();
            for (Appointment appointment2: allAppointments) {
                if (appointment2.getUser_ID() == userID2)
                {
                    LocalDateTime startEST = appointment2.getStartDateTime();
                    LocalDateTime endEST = appointment2.getEndDateTime();
                    ZonedDateTime zonedStartEST = startEST.atZone(ZoneId.of("America/New_York"));
                    ZonedDateTime zonedStartLOCAL = zonedStartEST.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));
                    ZonedDateTime zonedEndEST = endEST.atZone(ZoneId.of("America/New_York"));
                    ZonedDateTime zonedEndLOCAL = zonedEndEST.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));
                    LocalTime starttime = zonedStartLOCAL.toLocalTime();
                    LocalTime endtime = zonedEndLOCAL.toLocalTime();
                    // see if start time conflicts
                    if ((timeStart2.compareTo(starttime) >= 0) && (timeStart2.compareTo(endtime) < 0)) {
                        if (appointment2.getStartDateTime().toLocalDate().equals(dateStart)) {
                            overlappingError.setText("The selected time overlaps with another appointment");
                            throw new Exception();
                        }
                    }
                    // see if end time conflicts
                    if ((timeEnd2.compareTo(starttime) > 0) && (timeEnd2.compareTo(endtime) <= 0)) {
                        if (appointment2.getStartDateTime().toLocalDate().equals(dateStart)) {
                            overlappingError.setText("The selected time overlaps with another appointment");
                            throw new Exception();
                        }
                    }
                    // see if another appointment starts and ends between the new appointment
                    if ((starttime.compareTo(timeStart2) >= 0) && (endtime.compareTo(timeEnd2) <= 0)) {
                        if (appointment2.getStartDateTime().toLocalDate().equals(dateStart)) {
                            overlappingError.setText("The selected time overlaps with another appointment");
                            throw new Exception();
                        }
                    }
                }
            }


            // set values to the appointment object
            appointment.setTitle(title);
            appointment.setDescription(description);
            appointment.setLocation(location);
            appointment.setType(type);

            appointment.setStartDateTime(zonedStart2.toLocalDateTime());
            appointment.setEndDateTime(zonedEnd2.toLocalDateTime());
            appointment.setUpdatedDateTime(LocalDateTime.now());
            appointment.setUpdatedBy(Records.getCurrentUser().getUserName());

            appointment.setContact(contactID);
            appointment.setCustomer_ID(Integer.parseInt(CustomerID.getText()));
            appointment.setUser_ID(Integer.parseInt(UserID.getText()));

            // + "?connectionTimeZone = SERVER"
            // update values in database
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule", "sqlUser", "Passw0rd!");
                PreparedStatement edit = connection.prepareStatement("UPDATE appointments set Title = ?, Description = ?, " +
                        "Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Contact_ID = ?, " +
                        "User_ID = ?, Customer_ID = ? where Appointment_ID = ?");
                edit.setString(1, title);
                edit.setString(2, description);
                edit.setString(3, location);
                edit.setString(4, type);
                edit.setTimestamp(5, Timestamp.valueOf(zonedStart2.toLocalDateTime()));
                edit.setTimestamp(6, Timestamp.valueOf(zonedEnd2.toLocalDateTime()));
                edit.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                edit.setString(8, Records.getCurrentUser().getUserName());
                edit.setInt(9, contactID);
                edit.setInt(10, Integer.parseInt(UserID.getText()));
                edit.setInt(11, Integer.parseInt(CustomerID.getText()));
                edit.setInt(12, appointment.getAppointment_ID());
                edit.executeUpdate();
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            // back to main window
            Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        }
        catch (NumberFormatException x){
            userError.setText("UserID does not exist");
            overlappingError.setText("");
            blankError.setText("");
        }
        catch (IllegalArgumentException e) {
            blankError.setText("Make sure all fields are filled");
            userError.setText("");
            overlappingError.setText("");
        }
        catch (Exception x){
            blankError.setText("");
            userError.setText("");
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
