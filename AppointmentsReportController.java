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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class AppointmentsReportController {
    // Fxml file fields
    @FXML TextArea TypeField;
    @FXML TextArea MonthField;
    @FXML ComboBox<String> selectType;
    @FXML ComboBox<String> selectMonth;
    @FXML Label Num;
    // class variables
    private static ObservableList<Appointment> allAppointments = Records.getAllAppointments();
    private static ObservableList<String> months = FXCollections.observableArrayList("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST",
            "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
    private static ObservableList<String> Types = FXCollections.observableArrayList();
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
        Num.setText("");
        //get all appointments by type, put them into a key/value pair in a hashmap
        //iterate through the allAppointments list
        //if the key already exists in the hashmap, add 1 to the value
        //if the key does not exist yet in the hashmap, put it into the hashmap with a value of 1
        HashMap<String, Integer> types = new HashMap<String, Integer>();
        for (Appointment appointment: allAppointments)
        {
            String type = appointment.getType();
            if(types.containsKey(type))
            {
                int num = types.get(type);
                num++;
                types.put(type, num);
            }
            else
            {
                types.put(type, 1);
            }
        }
        //below is a lambda expression that simplfies the process
        //of taking each unique key from the hashmap and adding it to an observable list
        types.forEach((key,value) -> Types.add(key));
        selectType.setItems(Types);
        selectMonth.setItems(months);


        /*
        // create a string with all of the values in the hashmap
        // below is a lambda expression, it is used to efficiently
        // go through each value in the hashmap and add it to the final
        // output string that is used to display the report in the text field.
        AtomicReference<String> outPut = new AtomicReference<>("");
        types.forEach((key,value) -> outPut.set(outPut + key + ": " + value + "\n"));
        TypeField.setText(outPut.toString());


        // count all appointments by month
        for (Appointment appointment: allAppointments)
        {
            if(appointment.getStartDateTime().getMonth().name().equals("JANUARY"))
            {
                JanuaryCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("FEBRUARY"))
            {
                FebruaryCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("MARCH"))
            {
                MarchCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("APRIL"))
            {
                AprilCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("MAY"))
            {
                MayCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("JUNE"))
            {
                JuneCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("JULY"))
            {
                JulyCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("AUGUST"))
            {
                AugustCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("SEPTEMBER"))
            {
                SeptemberCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("OCTOBER"))
            {
                OctoberCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("NOVEMBER"))
            {
                NovemberCount++;
            }
            if(appointment.getStartDateTime().getMonth().name().equals("DECEMBER"))
            {
                DecemberCount++;
            }
        }
        // set month text area with data
        MonthField.setText("January: " + JanuaryCount + "\n" + "February: " + FebruaryCount + "\n" + "March: " + MarchCount + "\n" +
                "April: " + AprilCount + "\n" + "May: " + MayCount + "\n" + "June: " + JuneCount + "\n" +
                "July: " + JulyCount + "\n" + "August: " + AugustCount + "\n" + "September: " + SeptemberCount + "\n" +
                "October: " + OctoberCount + "\n" + "November: " + NovemberCount + "\n" + "December: " + DecemberCount + "\n");
         */
    }


    public void Change (ActionEvent event) throws IOException
    {
        // get number of appointments by selected time and month
        if(selectType.getValue() == null || selectMonth.getValue() == null)
        {
            Num.setText("");
        }
        else {
            String type = selectType.getValue();
            String month = selectMonth.getValue();
            int num = 0;
            for (Appointment appointment: allAppointments)
            {
                if(appointment.getType().equals(type))
                {
                    if(appointment.getStartDateTime().getMonth().name().equals(month))
                    {
                        num++;
                    }
                }
            }
            Num.setText(String.valueOf(num));
        }
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
