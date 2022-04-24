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
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;



public class CustomerReportController {
    // Fxml file fields
    @FXML TextArea CountryField;
    @FXML TextArea MonthField;
    // class variables
    private static ObservableList<Customer> allCustomers = Records.getAllCustomers();
    private int JanuaryCount;
    private int FebruaryCount;
    private int MarchCount;
    private int AprilCount;
    private int MayCount;
    private int JuneCount;
    private int JulyCount;
    private int AugustCount;
    private int SeptemberCount;
    private int OctoberCount;
    private int NovemberCount;
    private int DecemberCount;

    // set up text areas with appointment data
    public void initialize()
    {
        // count all appointments by month
        for (Customer customer: allCustomers)
        {
            if(customer.getCreationDateTime().getMonth().name().equals("January"))
            {
                JanuaryCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("FEBRUARY"))
            {
                FebruaryCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("MARCH"))
            {
                MarchCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("APRIL"))
            {
                AprilCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("MAY"))
            {
                MayCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("JUNE"))
            {
                JuneCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("JULY"))
            {
                JulyCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("AUGUST"))
            {
                AugustCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("SEPTEMBER"))
            {
                SeptemberCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("OCTOBER"))
            {
                OctoberCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("NOVEMBER"))
            {
                NovemberCount++;
            }
            if(customer.getCreationDateTime().getMonth().name().equals("DECEMBER"))
            {
                DecemberCount++;
            }
        }
        // set month text area with data
        MonthField.setText("January: " + JanuaryCount + "\n" + "February: " + FebruaryCount + "\n" + "March: " + MarchCount + "\n" +
                "April: " + AprilCount + "\n" + "May: " + MayCount + "\n" + "June: " + JuneCount + "\n" +
                "July: " + JulyCount + "\n" + "August: " + AugustCount + "\n" + "September: " + SeptemberCount + "\n" +
                "October: " + OctoberCount + "\n" + "November: " + NovemberCount + "\n" + "December: " + DecemberCount + "\n");


        //get all appointments by type, put them into a key/value pair in a hashmap
        //iterate through the allAppointments list
        //if the key already exists in the hashmap, add 1 to the value
        //if the key does not exist yet in the hashmap, put it into the hashmap with a value of 1
        HashMap<String, Integer> countries = new HashMap<String, Integer>();
        for (Customer customer: allCustomers)
        {
            String country = customer.getCountryName();
            if(countries.containsKey(country))
            {
                int num = countries.get(country);
                num++;
                countries.put(country, num);
            }
            else
            {
                countries.put(country, 1);
            }
        }
        // create a string with all of the values in the hashmap
        // below is a lambda expression, it is used to efficiently
        // go through each value in the hashmap and add it to the final
        // output string that is used to display the report in the text field.
        AtomicReference<String> outPut = new AtomicReference<>("");
        countries.forEach((key,value) -> outPut.set(outPut + key + ": " + value + "\n"));
        CountryField.setText(outPut.toString());
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
