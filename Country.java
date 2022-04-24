package Project;

import java.time.LocalDateTime;

// this class creates country objects
// countries are not created in the program, they are created when pulled from the database
public class Country {
    // class variables
    int CountryID;
    String Country, CreatedBy, UpdatedBy;
    LocalDateTime CreationDateTime, UpdatedDateTime;

    // initialize country object, set values
    public Country(int COUNTRYID, String COUNTRY, LocalDateTime CREATION, String CREATEDBY, LocalDateTime UPDATED, String UPDATEDBY)
    {
        this.CountryID = COUNTRYID;
        this.Country = COUNTRY;
        this.CreationDateTime = CREATION;
        this.CreatedBy = CREATEDBY;
        this.UpdatedDateTime = UPDATED;
        this.UpdatedBy = UPDATEDBY;
    }

    //GETTERS AND SETTERS
    public void setCountryID(int COUNTRYID){CountryID = COUNTRYID;}
    public int getCountryID(){return CountryID;}

    public void setCountry(String COUNTRY){Country = COUNTRY;}
    public String getCountry(){return Country;}

    public void setCreationDateTime(LocalDateTime CREATION){CreationDateTime = CREATION;}
    public LocalDateTime getCreationDateTime(){return CreationDateTime;}

    public void setCreatedBy(String CREATEDBY){CreatedBy = CREATEDBY;}
    public String getCreatedBy(){return CreatedBy;}

    public void setUpdatedDateTime(LocalDateTime UPDATED){UpdatedDateTime = UPDATED;}
    public LocalDateTime getUpdatedDateTime(){return UpdatedDateTime;}

    public void setUpdatedBy(String UPDATEDBY){UpdatedBy = UPDATEDBY;}
    public String getUpdatedBy(){return UpdatedBy;}


}
