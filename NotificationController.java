package Project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

// this class controls the notification GUI page / file
public class NotificationController {
    // fxml file fields
    @FXML Label numberOfAppointments;
    @FXML TextArea displayAppointments;
    // get a list of upcoming appointments (15 minutes) and other class variables
    private static ObservableList<Appointment> upcomingAppointments;
    public static void setUpcomingAppointments(ObservableList<Appointment> APPOINTMENTS){upcomingAppointments = APPOINTMENTS;}
    private String appointments;

    // set up the page
    public void initialize()
    {
        //display the upcoming appointments in the text area
        numberOfAppointments.setText(String.valueOf(upcomingAppointments.size()));
        for (Appointment appointment: upcomingAppointments)
        {
            appointments = appointments + "\nAppointment ID: " + appointment.getAppointment_ID() + "   When: " + appointment.getStartDateTime();
        }
        displayAppointments.setText(appointments);

    }

    // close button
    public void CloseButton(ActionEvent event) throws IOException
    {
        // go to the main window page
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
