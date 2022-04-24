package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// this class controls the "Login Form" GUI page/file
// can create a user
// checks login credentials
public class LoginFormController {
    // fxml file fields
    private @FXML TextField userNameField;
    private @FXML TextField passwordField;
    private @FXML Label errorMessage;
    private @FXML Label Locations;
    private @FXML Label loginLabel;
    private @FXML Label userIDLabel;
    private @FXML Label passwordLabel;
    private @FXML Button SignIn;
    private @FXML Button Exit;



    public void initialize()
    {
        //if language of location is french, change text of login form to read in french
        Locale location = Locale.getDefault();
        String language = location.getLanguage();
        if(language.equals("fr"))
        {
            loginLabel.setText("Connexion");
            userIDLabel.setText("Identifiant d'utilisateur");
            passwordLabel.setText("Mot de passe");
            SignIn.setText("S'identifier");
            Exit.setText("Sortir");
        }

        // other initialzing items
        errorMessage.setText("");
        ZonedDateTime zonedStart = LocalDateTime.now().atZone(ZoneId.of(ZoneId.systemDefault().getId()));
        var FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy  hh:mm a   z");
        Locations.setText(zonedStart.format(FORMAT));
    }

    // action taken when the sign in button is clicked
    public void SignInButton(ActionEvent event) throws IOException {
        try
        {
            // get the username and password entered into the fields
            String username = userNameField.getText();
            String password = passwordField.getText();

            // validate the username and password, take appropriate actions
            // first go through the user records
            ObservableList<User> users = Records.getAllUsers();
            int count = 1;
            int count2 = 0;
            for (User user : users) {
                // if a user records' username and password match the entered values
                // establish the current user of the system
                // create a list of all appointments for that user
                if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                    Records.setCurrentUser(user);
                    ObservableList<Appointment> appointments = Records.getAllAppointments();

                    ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
                    for (Appointment appointment2 : appointments) {
                        if (appointment2.getUser_ID() == Records.getCurrentUser().getUserID()) {
                            userAppointments.add(appointment2);
                        }
                    }
                    // create a list of all appointments taking place in the next 15 minutes for the user
                    ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    for (Appointment appointment : userAppointments) {
                        LocalDateTime upcomingAppointment = appointment.getStartDateTime();
                        Duration difference = Duration.between(currentDateTime, upcomingAppointment);
                        if (difference.toMinutes() <= 15 && difference.toMinutes() >= 0) {
                            upcomingAppointments.add(appointment);
                        }
                    }

                    // code to append data to the login report txt file upon successful login
                    File logins = new File("login_activity.txt");
                    logins.createNewFile();
                    FileWriter writeToFile = new FileWriter("login_activity.txt", true);
                    writeToFile.append("Successful login     User: " + userNameField.getText() + "     Time: " + LocalDateTime.now() + "\n");
                    writeToFile.close();

                    count2 = 1;

                    // send the data to the controller of the "Notification" page
                    NotificationController.setUpcomingAppointments(upcomingAppointments);

                    // go to the notification page to notify user of upcoming appointments
                    Parent notificationWindow = FXMLLoader.load(getClass().getResource("Notification.fxml"));
                    Scene notificationWindowScene = new Scene(notificationWindow);

                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(notificationWindowScene);
                    window.show();
                }
                //count is used to make sure that this unsuccessful message is not added to the doc til the end of the user list,
                //so it doesn't add to unsuccessful login attempt every time a user does not match in the users list
                count++;
            }

            if (count2 == 0) {
                File logins = new File("login_activity.txt");
                logins.createNewFile();
                FileWriter writeToFile = new FileWriter("login_activity.txt", true);
                writeToFile.append("Unsuccessful login     Attempted Username: " + userNameField.getText() + "     Attempted Password: " + passwordField.getText() + "     Time: " + LocalDateTime.now() + "\n");
                writeToFile.close();

                // error message - no matching login credentials
                throw new Exception();

            }
        }
        catch (Exception e)
        {
            Locale location = Locale.getDefault();
            String language = location.getLanguage();
            if(language.equals("fr"))
            {
                errorMessage.setText("identifiant ou mot de passe incorrect");
            }
            else {
                errorMessage.setText("Incorrect Username or Password");
            }
        }
    }


    // action when the the exit button is clicked
    public void ExitButton(ActionEvent event) throws IOException
    {
        // close out of the GUI program
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
}
