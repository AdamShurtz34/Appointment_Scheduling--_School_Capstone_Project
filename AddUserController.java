package Project;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

// this class controls the "Add User" GUI page/file
// adds a user to the database
public class AddUserController {
    // class variables and fxml fields
    private static ObservableList<User> allUsers = Records.getAllUsers();
    @FXML TextField UserIDField;
    @FXML TextField UsernameField;
    @FXML TextField PasswordField;
    @FXML Label blankError;

    // set up / initialize the fxml page/field
    // get next user ID value and put in field
    public void initialize()
    {
        int max = 0;
        for (User user: allUsers)
        {
            if (user.getUserID() > max)
            {
                max = user.getUserID();
            }
        }
        UserIDField.setText(String.valueOf(max + 1));
        blankError.setText("");
    }


    // action taken when save button is clicked
    // gets date from the fields
    // create new user and add to records
    // goes back to login form page
    public void saveButton(ActionEvent event) throws IOException
    {
        try {
            if(UsernameField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(PasswordField.getText().equals(""))
            {
                throw new IllegalArgumentException();
            }

            User user = new User(Integer.parseInt(UserIDField.getText()), UsernameField.getText(), PasswordField.getText(), LocalDateTime.now(), UsernameField.getText(), LocalDateTime.now(), UsernameField.getText());
            Records.addNewUser(user);

            Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
            Scene mainWindowScene = new Scene(mainWindow);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainWindowScene);
            window.show();
        }
        catch (IllegalArgumentException e) {
            blankError.setText("Make sure all fields are filled");
        }
    }


    // action taken when cancel button is clicked
    // go back to login form page
    public void cancelButton(ActionEvent event) throws IOException
    {
        Parent mainWindow = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene mainWindowScene = new Scene(mainWindow);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(mainWindowScene);
        window.show();
    }

}
