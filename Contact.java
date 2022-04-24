package Project;


// this class creates a contact object
public class Contact {
    //class variables
    int ContactID;
    String ContactName, Email;

    // initialize the object, set all values to variables
    public Contact(int CONTACTID, String CONTACTNAME, String EMAIL)
    {
        this.ContactID = CONTACTID;
        this.ContactName = CONTACTNAME;
        this.Email = EMAIL;
    }

    // GETTERS AND SETTERS
    public void setContactID(int CONTACTID){ContactID = CONTACTID;}
    public int getContactID(){return ContactID;}

    public void setContactName(String CONTACTNAME){ContactName = CONTACTNAME;}
    public String getContactName(){return ContactName;}

    public void setEmail(String EMAIL){Email = EMAIL;}
    public String getEmail(){return Email;}
}
