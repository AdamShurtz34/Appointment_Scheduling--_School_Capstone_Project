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


// this class controls the "Add Contact" GUI page/file
// this class adds contacts to the records class
public class AddContactController {
    // FXML file fields
    @FXML TextField ContactIDField;
    @FXML TextField ContactNameField;
    @FXML TextField ContactEmailField;
    @FXML Label blankError;
    // class variables
    private static ObservableList<Contact> allContacts = Records.getAllContacts();

    // set up and initialize the page/file
    public void initialize()
    {
        // set up the contact ID - add one to current highest contact ID
        int max = 0;
        for (Contact contact: allContacts)
        {
            if (contact.getContactID() > max)
            {
                max = contact.getContactID();
            }
        }
        ContactIDField.setText(String.valueOf(max + 1));
        blankError.setText("");
    }


    // action taken when save button is clicked
    public void saveButton(ActionEvent event) throws IOException
    {
        try {
            // get data from the fields
            int contactID = Integer.parseInt(ContactIDField.getText());
            String contactName = ContactNameField.getText();
            String contactEmail = ContactEmailField.getText();
            // create contact and add to records
            if(contactName.equals(""))
            {
                throw new IllegalArgumentException();
            }
            if(contactEmail.equals(""))
            {
                throw new IllegalArgumentException();
            }

            Contact contact = new Contact(contactID, contactName, contactEmail);
            Records.addNewContact(contact);
            // back to main window
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
