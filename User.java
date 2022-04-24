package Project;

import java.time.LocalDateTime;


// this class creates user objects
public class User {
    // class variables
    private int userID;
    private String userName, password, createdBy, updatedBy;
    private LocalDateTime creationDateTime, updatedDateTime;

    // initialze the object, set the values
    public User(int USERID, String USERNAME, String PASSWORD, LocalDateTime CREATION, String CREATEDBY, LocalDateTime UPDATED, String UPDATEDBY)
    {
        this.userID = USERID;
        this.userName = USERNAME;
        this.password = PASSWORD;
        this.creationDateTime = CREATION;
        this.createdBy = CREATEDBY;
        this.updatedDateTime = UPDATED;
        this.updatedBy = UPDATEDBY;
    }

    // getters and setters
    public void setUserID (int USERID) {userID = USERID;}
    public int getUserID(){ return userID; }
    //--------------------------------------------------
    public void setUserName(String USERNAME) {userName = USERNAME;}
    public String getUserName(){return userName;}
    //-----------------------------------------------------
    public void setPassword(String PASSWORD){password = PASSWORD;}
    public String getPassword(){return password;}
    //---------------------------------------------------
    public void setCreationDateTime(LocalDateTime CREATION){creationDateTime = CREATION;}
    public LocalDateTime getCreationDateTime(){return creationDateTime;}
    //------------------------------------------------------
    public void setCreatedBy(String CREATEDBY){createdBy = CREATEDBY;}
    public String getCreatedBy(){return createdBy;}
    //--------------------------------------------------------
    public void setUpdatedDateTime(LocalDateTime UPDATED){updatedDateTime = UPDATED;}
    public LocalDateTime getUpdatedDateTime(){return updatedDateTime;}
    //-----------------------------------------------------\
    public void setUpdatedBy(String UPDATEDBY){updatedBy = UPDATEDBY;}
    public String getUpdatedBy(){return updatedBy;}

}
