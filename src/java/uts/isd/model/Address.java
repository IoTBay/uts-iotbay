/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 1
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model;

import java.sql.ResultSet;
import java.util.Date;
import javax.servlet.ServletRequest;
import uts.isd.model.dao.DBCustomer;
import uts.isd.model.dao.IAddress;
import uts.isd.model.dao.ICustomer;
import uts.isd.util.Logging;

/**
 * Address model
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-16
 */
public class Address {
    
    private int id;
    private int customerId;
    private int userId;
    private int defaultShippingAddress; //Is this address the customer's default shipping address?
    private int defaultBillingAddress; //Is this address the customer's default billing address?    
    private String addressPrefix1;
    private int streetNumber;
    private String streetName;
    private String streetType;
    private String suburb;
    private String state;
    private String postcode;
    private String country;
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private int modifiedBy;

    public Address() { }
    
    /**
     * This constructor takes an SQL ResultSet and grabs the values from the DB Record
     * to populate each property in the user model.
     * 
     * @param rs The SQL ResultSet row to populate values from.
     */
    public Address(ResultSet rs) {
        try
        {
            this.id = rs.getInt("ID");
            this.customerId = rs.getInt("CustomerID");
            this.userId = rs.getInt("UserID");
            this.defaultBillingAddress = rs.getInt("DefaultBillingAddress");
            this.defaultShippingAddress = rs.getInt("DefaultShippingAddress");
            this.addressPrefix1 = rs.getString("AddressPrefix1");
            this.streetNumber = rs.getInt("StreetNumber");
            this.streetName = rs.getString("StreetName");
            this.streetType = rs.getString("StreetType");
            this.suburb = rs.getString("Suburb");
            this.state = rs.getString("State");
            this.postcode = rs.getString("PostCode");
            this.country = rs.getString("Country");
            
            this.createdDate = rs.getDate("CreatedDate");
            this.createdBy = rs.getInt("CreatedBy");
            this.modifiedDate = rs.getDate("ModifiedDate");
            this.modifiedBy = rs.getInt("ModifiedBy");            
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to load Address from ResultSet for ID", e);
        }
    }
    
    /**
     * This method populates this instance's properties based on form inputs.
     * 
     * @param request The controller's HTTPServlet POST request properties.
     * 
     */
    public void loadRequest(ServletRequest request)
    {
        if (request.getParameter("id") != null)
            this.id = Integer.parseInt(request.getParameter("id"));
        
        if (request.getParameter("customerId") != null)
            this.customerId = Integer.parseInt(request.getParameter("customerId"));
        
        if (request.getParameter("userId") != null)
            this.userId = Integer.parseInt(request.getParameter("userId"));
        
        if (request.getParameter("defaultShippingAddress") != null)
            this.defaultShippingAddress = 1;
        else
            this.defaultShippingAddress = 0;
        
        if (request.getParameter("defaultBillingAddress") != null)
            this.defaultBillingAddress = 1;
        else
            this.defaultBillingAddress = 0;
        
        this.addressPrefix1 = request.getParameter("addressPrefix1");
        this.streetNumber = Integer.parseInt(request.getParameter("streetNumber"));
        this.streetName = request.getParameter("streetName");
        this.streetType = request.getParameter("streetType");
        this.suburb = request.getParameter("suburb");
        this.state = request.getParameter("state");
        this.postcode = request.getParameter("postcode");
        this.country = request.getParameter("country");
        
        this.createdDate = new Date();
        this.modifiedDate = new Date();
        this.createdBy = 0;
        this.modifiedBy = 0;
    }
    
    public boolean add(IAddress db, Customer customer)
    {
        try
        {
            //Assumes the Address object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean added = db.addAddress(this, customer);
            //Always close DB when done.
            return added;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to add address", e);
            return false;
        }        
    }
    
    public boolean update(IAddress db, Customer customer)
    {
        try
        {
            //Assumes the Address object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean updated = db.updateAddress(this, customer);
            //Always close DB when done.
            return updated;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to update address", e);
            return false;
        }        
    }
    
    public boolean delete(IAddress db)
    {
        try
        {
            //Assumes the User object (this) has been populated already.
            //Takes object properties and inserts into DB.
            boolean deleted = db.deleteAddressById(this.id);
            //Always close DB when done.
            return deleted;
        }
        catch (Exception e)
        {
            Logging.logMessage("Failed to delete address", e);
            return false;
        }        
    }
    
    public String getFullAddress()
    {
        return (this.getAddressPrefix1().isEmpty() ? "" : this.getAddressPrefix1()+", ") +
                this.getStreetNumber()+" "+this.getStreetName() +" "+this.getStreetType() + ", "+
                this.getSuburb()+", "+this.getState()+" "+this.getPostcode()+", "+this.getCountry();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setDefaultShippingAddress(int defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }

    public int getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    public void setDefaultBillingAddress(int defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
    }

    public String getAddressPrefix1() {
        return addressPrefix1;
    }

    public void setAddressPrefix1(String addressPrefix1) {
        this.addressPrefix1 = addressPrefix1;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetType() {
        return streetType;
    }

    public void setStreetType(String streetType) {
        this.streetType = streetType;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public Date getModifiedDate() {
        return this.modifiedDate;
    }
    
    public Customer getCreatedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.createdBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
    
    public Customer getModifiedBy() {
        try
        {
            ICustomer dbCustomer = new DBCustomer();
            Customer c = dbCustomer.getCustomerById(this.modifiedBy);
            return c;
        }
        catch (Exception e)
        {
            return new Customer();
        }
    }
    
    //https://gist.github.com/whit3hawks/3b507d7005448eebb9c9e78ce853c254
    public static final String[] COUNTRIES = new String[]{ "Australia", "United States", "United Kingdom", "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};

    public static final String[] STATES = new String[] { "New South Wales", "Victoria", "Queensland", "Tasmania", "South Australia", "Western Australia", "Northern Territory", "Australian Capital Territory", "Non-Australian State" };
}
