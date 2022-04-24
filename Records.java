package Project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// this class is the data hub for the program
// all data from the database is loaded into lists in this class at the start of this program
// all objects created while the program is running is stored here
// when closing out of the program, all new data that was created and stored here is added to the database
public class Records {

    // the Lists for all data relevant to this program
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();

    private static ObservableList<User> recentUsers = FXCollections.observableArrayList();
    private static ObservableList<Contact> recentContacts = FXCollections.observableArrayList();

    // when logged in, data of the current user is stored here
    private static User currentUser;

//-----------------------------------------------------------------------------------------------------------
    // These methods add data (objects) to their respective lists - the lists that store ALL data
    public static void addCustomer(Customer newCustomer) { allCustomers.add(newCustomer);}

    public static void addAppointment(Appointment newAppointment) { allAppointments.add(newAppointment);}

    public static void addUser(User newUser) {allUsers.add(newUser);}

    public static void addContact(Contact newContact) {allContacts.add(newContact);}

    public static void addCountry(Country newCountry) {allCountries.add(newCountry);}

    public static void addFirstLevelDivision(FirstLevelDivision newFirstLevelDivision) {allFirstLevelDivisions.add(newFirstLevelDivision);}


//-----------------------------------------------------------------------------------------------------------------
    // These methods add data (objects) to lists - these lists specifically only house data (objects) during the runtime of the program
    // at the close of the program, data from these lists are added to the database

    public static void addNewUser(User newUser) {allUsers.add(newUser); recentUsers.add(newUser);}

    public static void addNewContact(Contact newContact) {allContacts.add(newContact); recentContacts.add(newContact);}

//-------------------------------------------------------------------------------------------------------------
    // these methods remove data (objects) from both the recently created list and the ALL list
    public static void deleteCustomer(Customer selectedCustomer) { allCustomers.remove(selectedCustomer); }

    public static void deleteAppointment(Appointment selectedAppointment) { allAppointments.remove(selectedAppointment);}

    public static void deleteUser(User selectedUser) {allUsers.remove((selectedUser)); recentUsers.remove((selectedUser));}

    public static void deleteContact(Contact selectedContact) {allContacts.remove((selectedContact)); recentContacts.remove((selectedContact));}

    //-------------------------------------------------------------------------------------------------------------------

    // these are the getters for the ALL lists
    public static ObservableList<Customer> getAllCustomers()
    {
        return allCustomers;
    }

    public static ObservableList<Appointment> getAllAppointments()
    {
        return allAppointments;
    }

    public static ObservableList<User> getAllUsers() {return allUsers;}

    public static ObservableList<Contact> getAllContacts() {return allContacts;}

    public static ObservableList<Country> getAllCountries() {return allCountries;}

    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() {return allFirstLevelDivisions;}

    public static ObservableList<String> getAllCountryNames()
    {
        ObservableList<String> displayCountries = FXCollections.observableArrayList();
        for (Country country: allCountries)
        {
            displayCountries.add(country.getCountry());
        }
        return displayCountries;
    }

    public static ObservableList<String> getAllDivisionNames(int countryID)
    {
        ObservableList<String> displayDivisions = FXCollections.observableArrayList();
        for (FirstLevelDivision Division: allFirstLevelDivisions)
        {
            if(Division.getCountryID() == countryID)
            {
                displayDivisions.add(Division.getDivision());
            }
        }
        return displayDivisions;
    }

    // these are the getters for the recently added objects lists


    public static ObservableList<User> getRecentUsers() {return recentUsers;}

    public static ObservableList<Contact> getRecentContacts() {return recentContacts;}


    //-------------------------------------------------------------------------
    // getter and setter for the current user
    public static void setCurrentUser(User currentuser) {currentUser = currentuser;}
    public static User getCurrentUser(){return currentUser;}
}
