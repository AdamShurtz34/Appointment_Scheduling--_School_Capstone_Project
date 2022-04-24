package Project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// this class controls the "Delete Appointment" GUI page / file
// this class deletes the appointment from the records class as well as the database
public class DeleteAppointmentController {
    // get the selected appointment for deleting
    private static Appointment appointment;
    public static void getSelectedAppointment(Appointment selectedAppointment) { appointment = selectedAppointment; }

    // action taken when the delete button is clicked
    public void deleteButton(ActionEvent event) throws IOException
    {
        int appointmentID = appointment.getAppointment_ID();
        String appointmentType = appointment.getType();

        DeletedNotificationController.getAppointmentID(appointmentID);
        DeletedNotificationController.getAppointmentType(appointmentType);
        // this section deletes the appointment from the database
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/client_schedule" + "?connectionTimeZone = SERVER", "sqlUser", "Passw0rd!");
            PreparedStatement delete = connection.prepareStatement("delete from appointments where Appointment_ID = ?");
            delete.setInt(1,appointmentID);
            delete.execute();
            connection.close();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        // this section deletes the appointment from the customer class
        ObservableList<Customer> customers = Records.getAllCustomers();
        for (Customer customer: customers)
        {
            if (appointment.getCustomer_ID() == customer.getCustomerID())
            {
                customer.deleteAppointment(appointment);
            }
        }
        // this deletes the appointment from the records class
        try {
            Records.deleteAppointment(appointment);
            // go back to the main window
            Parent deletedWindow = FXMLLoader.load(getClass().getResource("DeletedNotification.fxml"));
            Scene deletedWindowScene = new Scene(deletedWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(deletedWindowScene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
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
