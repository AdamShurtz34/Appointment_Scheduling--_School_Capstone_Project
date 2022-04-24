package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

// this class creates a customer object
public class Customer {

    // class variables
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private int customerID, divisionID;
    //private long phoneNumber;
    private String fullName, address, phoneNumber, postalCode;
    private LocalDateTime creationDateTime, updatedDateTime;
    private String created_By_User, updated_By_User;

    // Constructor, initialize customer object and set values
    public Customer(int CUST_ID, String FULL_NAME, String ADDRESS, String POSTAL_CODE, String PHONE_NUMBER, LocalDateTime CREATION, String CREATEDBY, LocalDateTime UPDATED, String UPDATEDBY, int DIVISIONID)
    {
        this.customerID = CUST_ID;
        this.fullName = FULL_NAME;
        this.address = ADDRESS;
        this.postalCode = POSTAL_CODE;
        this.phoneNumber = PHONE_NUMBER;
        this.creationDateTime = CREATION;
        this.created_By_User = CREATEDBY;
        this.updatedDateTime = UPDATED;
        this.updated_By_User = UPDATEDBY;
        this.divisionID = DIVISIONID;
    }

// GETTERS AND SETTERS
    public void setCustomerID(int CUSTID) { customerID = CUSTID; }
    public int getCustomerID() { return customerID; }
//-------------------------------------
    public void setFullName(String NAME) { fullName = NAME; }
    public String getFullName() { return fullName; }
//-----------------------------------
    public void setAddress(String ADDRESS) { address = ADDRESS; }
    public String getAddress() { return address; }
//---------------------------------
    public void setPostalCode(String CODE) { postalCode = CODE; }
    public String getPostalCode() { return postalCode; }
//----------------------------------
    public void setPhoneNumber(String PHONE) { phoneNumber = PHONE; }
    public String getPhoneNumber() { return phoneNumber; }
//----------------------------------
    public void setDivisionID(int DIVISIONID){divisionID = DIVISIONID;}
    public int getDivisionID(){return divisionID;}

    //-----------------------------------------------------------------------
    public void setCreationDateTime(LocalDateTime CREATION){ creationDateTime = CREATION; }
    public LocalDateTime getCreationDateTime(){ return creationDateTime; }
    //-----------------------------------------------------------------------------
    public void setCreated_By_User(String CREATEDBY){created_By_User = CREATEDBY; }
    public String getCreated_By_User(){ return created_By_User; }
    //--------------------------------------------------------------------------
    public void setUpdatedDateTime(LocalDateTime UPDATED){ updatedDateTime = UPDATED; }
    public LocalDateTime getUpdatedDateTime() {return updatedDateTime;}
    //--------------------------------------------------------------------------------
    public void setUpdated_By_User(String UPDATEDBY) { updated_By_User = UPDATEDBY;}
    public String getUpdated_By_User() {return updated_By_User;}



    public void addAppointment(Appointment appointment)
    {
        appointments.add(appointment);
    }

    public void deleteAppointment(Appointment selectedAppointment)
    {
        appointments.remove(selectedAppointment);
    }

    public ObservableList<Appointment> getAllAppointments()
    {
        return appointments;
    }


    public String getCountryName()
    {
        ObservableList<FirstLevelDivision> divisions = Records.getAllFirstLevelDivisions();
        int countryID = 0;
        for (FirstLevelDivision Division : divisions)
        {
            if (Division.getDivisionID() == divisionID)
            {
                countryID = Division.getCountryID();
            }
        }
        ObservableList<Country> countries = Records.getAllCountries();
        String countryName = "";
        for (Country country : countries)
        {
            if (country.getCountryID() == countryID)
            {
                countryName = country.getCountry();
            }
        }
        return countryName;
    }

    public String getDivisionName()
    {
        ObservableList<FirstLevelDivision> divisions = Records.getAllFirstLevelDivisions();
        String divisionName = "";
        for (FirstLevelDivision Division : divisions)
        {
            if (Division.getDivisionID() == divisionID)
            {
                divisionName = Division.getDivision();
            }
        }
        return  divisionName;
    }
}
