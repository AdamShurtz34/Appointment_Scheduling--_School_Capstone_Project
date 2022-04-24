package Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DeletedNotificationController {

    @FXML Label appointmentID;
    @FXML Label appointmentType;
    private static int AppointmentID;
    private static String AppointmentType;
    public static void getAppointmentID(int appointmentid) { AppointmentID = appointmentid; }
    public static void getAppointmentType(String appointmenttype) { AppointmentType = appointmenttype; }

    // display the appointment ID and type that was deleted
    public void initialize()
    {
        appointmentID.setText(String.valueOf(AppointmentID));
        appointmentType.setText(AppointmentType);
    }

    // action taken when the close button is clicked
    public void closeButton(ActionEvent event) throws IOException
    {
        // go back to the main window
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }
}
