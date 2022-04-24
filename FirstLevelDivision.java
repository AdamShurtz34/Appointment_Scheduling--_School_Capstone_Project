package Project;

import java.time.LocalDateTime;

// this class creates the objects of the divions for the countries
// objects for this class are not created in the program, they are created when importing data from the database
public class FirstLevelDivision {
    // class variables
    int DivisionID, CountryID;
    String Division, CreatedBy, UpdatedBy;
    LocalDateTime CreationDateTime, UpdatedDateTime;

    // initialize the object, set values
    public FirstLevelDivision(int DIVISIONID, String DIVISION, LocalDateTime CREATION, String CREATEDBY, LocalDateTime UPDATED, String UPDATEDBY, int COUNTRYID)
    {
        this.DivisionID = DIVISIONID;
        this.Division = DIVISION;
        this.CreationDateTime = CREATION;
        this.CreatedBy = CREATEDBY;
        this.UpdatedDateTime = UPDATED;
        this.UpdatedBy = UPDATEDBY;
        this.CountryID = COUNTRYID;
    }

    //GETTERS AND SETTERS
    public void setCountryID(int COUNTRYID){CountryID = COUNTRYID;}
    public int getCountryID(){return CountryID;}

    public void setDivisionID(int DIVISIONID){DivisionID = DIVISIONID;}
    public int getDivisionID(){return DivisionID;}

    public void setDivision(String DIVISION){Division = DIVISION;}
    public String getDivision(){return Division;}

    public void setCreationDateTime(LocalDateTime CREATION){CreationDateTime = CREATION;}
    public LocalDateTime getCreationDateTime(){return CreationDateTime;}

    public void setCreatedBy(String CREATEDBY){CreatedBy = CREATEDBY;}
    public String getCreatedBy(){return CreatedBy;}

    public void setUpdatedDateTime(LocalDateTime UPDATED){UpdatedDateTime = UPDATED;}
    public LocalDateTime getUpdatedDateTime(){return UpdatedDateTime;}

    public void setUpdatedBy(String UPDATEDBY){UpdatedBy = UPDATEDBY;}
    public String getUpdatedBy(){return UpdatedBy;}
}
