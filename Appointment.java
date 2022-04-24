package Project;

import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// this class creates an appointment object
public class Appointment {
    // class variables
    private int Appointment_ID, Customer_ID, Contact_ID, User_ID;
    private String Title, Description, Location, Type, CreatedBy, UpdatedBy;
    private LocalDateTime StartDateTime, EndDateTime, CreationDateTime, UpdatedDateTime;

    // initialize the object and set all the values to variables
    public Appointment(int APT_ID, String TITLE, String DESCRIPTION, String LOCATION, String TYPE, LocalDateTime STARTDATETIME,
                       LocalDateTime ENDDATETIME, LocalDateTime CREATION, String CREATEDBY, LocalDateTime UPDATED,
                       String UPDATEDBY, int CUST_ID, int USER, int CONTACT)
    {
        this.Appointment_ID = APT_ID;
        this.Title = TITLE;
        this.Description = DESCRIPTION;
        this.Location = LOCATION;
        this.Type = TYPE;
        this.StartDateTime = STARTDATETIME;
        this.EndDateTime = ENDDATETIME;
        this.CreationDateTime = CREATION;
        this.CreatedBy = CREATEDBY;
        this.UpdatedDateTime = UPDATED;
        this.UpdatedBy = UPDATEDBY;
        this.Customer_ID = CUST_ID;
        this.User_ID = USER;
        this.Contact_ID = CONTACT;
    }


//GETTERS AND SETTERS FOR ALL VALUES / VARIABLES
    public void setAppointment_ID(int APTID) { Appointment_ID = APTID; }

    public int getAppointment_ID()
    {
        return Appointment_ID;
    }
//------------------------------------------------------------
    public void setTitle(String TITLE) { Title = TITLE; }

    public String getTitle() { return Title; }
//----------------------------------------------------------
    public void setDescription(String DESCRIPTION) { Description = DESCRIPTION; }

    public String getDescription() { return Description; }
//----------------------------------------------------------------
    public void setLocation(String LOCATION) { Location = LOCATION; }

    public String getLocation() { return Location; }
//----------------------------------------------------------------

    public void setType(String TYPE) { Type = TYPE; }

    public String getType() { return Type; }
//-----------------------------------------------------------------
    public String getStartDateTimeUser()
    {
        ZonedDateTime zonedStart = StartDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedStart2 = zonedStart.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));

        var FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy ' at ' hh:mm a");
        return zonedStart2.format(FORMAT);
    }

    public String getEndDateTimeUser()
    {
        ZonedDateTime zonedEnd = EndDateTime.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime zonedEnd2 = zonedEnd.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().getId()));

        var FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy ' at ' hh:mm a");
        return zonedEnd2.format(FORMAT);
    }
//-------------------------------------------------------------------
    public void setStartDateTime(LocalDateTime STARTDATE){StartDateTime = STARTDATE;}
    public LocalDateTime getStartDateTime() { return StartDateTime; }
    //------------------------------------------------------
    public void setEndDateTime(LocalDateTime ENDDATE){EndDateTime = ENDDATE;}
    public LocalDateTime getEndDateTime(){return EndDateTime;}
    //-------------------------------------------------------------
    public void setCreationDateTime(LocalDateTime CREATION){CreationDateTime = CREATION;}
    public LocalDateTime getCreationDateTime(){return CreationDateTime;}
    //------------------------------------------------------
    public void setCreatedBy(String CREATEDBY){CreatedBy = CREATEDBY;}
    public String getCreatedBy(){return CreatedBy;}
    //--------------------------------------------------------
    public void setUpdatedDateTime(LocalDateTime UPDATED){UpdatedDateTime = UPDATED;}
    public LocalDateTime getUpdatedDateTime(){return UpdatedDateTime;}
    //-----------------------------------------------------\
    public void setUpdatedBy(String UPDATEDBY){UpdatedBy = UPDATEDBY;}
    public String getUpdatedBy(){return UpdatedBy;}

    //-------------------------------------------------------------------------------
    //------------------------------------------------------------------------
    public void setCustomer_ID(int CUSTID) { Customer_ID = CUSTID; }
    public int getCustomer_ID() { return Customer_ID; }
    //----------------------------------------------------------------------
    public void setUser_ID(int USERID){ User_ID = USERID;}
    public int getUser_ID(){return User_ID;}
    //-----------------------------------------------------------------------
    public void setContact(int CONTACT) { Contact_ID = CONTACT; }
    public int getContact() { return Contact_ID; }
    //----------------------------------------------------------------------
    public String getContactName()
    {
        ObservableList<Contact> allContacts = Records.getAllContacts();
        String contactName = "";
        for (Contact contact: allContacts)
        {
            if(Contact_ID == contact.getContactID())
            {
                contactName = contact.getContactName();
            }
        }
        return contactName;
    }

}
