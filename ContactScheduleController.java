package Project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

// this class controls the Contact Schedule GUI page/file
// displays all contacts with their respective appointments
public class ContactScheduleController {
    // fxml file field
    @FXML TextArea ContactsField;
    // class variables
    private static ObservableList<Contact> allContacts = Records.getAllContacts();
    private static ObservableList<Appointment> allAppointments = Records.getAllAppointments();



    // sets up the page - display all contacts with their appointments
    public void initialize()
    {
        // outPut is the final string that will be set to the text area
        String outPut = "";
        // iterate through all contacts
        for (Contact contact: allContacts)
        {
            // get the name of the contact
            String contactName = contact.getContactName();
            // string that has the name with a new line
            String contactAndAppointments = contactName + "\n";
            // for each contact, go through all appointments, if the appointment belongs to the contact,
            // add the appointment details to the contactAndAppointments string followed by a new line
            for (Appointment appointment: allAppointments)
            {
                if(appointment.getContact() == contact.getContactID())
                {
                    contactAndAppointments = contactAndAppointments + "       Appointment ID: " + appointment.getAppointment_ID() +
                            "   Title: " + appointment.getTitle() + "   Type: " + appointment.getType() + "   Description: " +
                            appointment.getDescription() + "   Start: " + appointment.getStartDateTime() + "   End: " + appointment.getEndDateTime() +
                            "   Customer ID: " + appointment.getCustomer_ID() + "\n";
                }
            }
            // add the string which contains a contact with all its contacts to the final output string
            outPut = outPut + contactAndAppointments + "\n" + "\n";
        }
        // set the final output string to the text area
        ContactsField.setText(outPut);
    }



    // action that takes place when close button is clicked
    public void closeButton(ActionEvent event) throws IOException
    {
        //go back to the main screen
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
