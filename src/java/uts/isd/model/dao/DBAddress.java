/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uts.isd.model.Address;
import uts.isd.model.Customer;
import uts.isd.util.Logging;

/**
 * DBManager is the primary DAO class to interact with the database. 
 * Complete the existing methods of this classes to perform CRUD operations with the db.
 * Each entity/table has it's own implementation.
 * 
 * In our case the DBManager class implements a repository pattern interface for the entity.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-02
 */
public class DBAddress implements IAddress{
    
    private Connection conn;
        
    public DBAddress() throws SQLException, ClassNotFoundException { 
        DBConnector connector = new DBConnector();
        this.conn = connector.openConnection();
    }
    
    public void close() throws SQLException {
        this.conn.close();
    }
    
    //Use this to show all of the addresses in the database - use this for displaying in table
    @Override
    public List<Address> getAllAddresses() 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Addresses");
            ResultSet rs = p.executeQuery();
            
            //Build list of address objects to return
            List<Address> addresses = new ArrayList<Address>();
            
            while (rs.next())
            {
                addresses.add(new Address(rs));
            }
            return addresses;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllAddresses", e);
            return null;
        }
    }
    
    @Override
    public List<Address> getAllAddressesByCustomerId(int id)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Addresses WHERE CustomerID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            
            //Build list of address objects to return
            List<Address> addresses = new ArrayList<Address>();
            
            while (rs.next())
            {
                addresses.add(new Address(rs));
            }
            return addresses;
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getAllAddressesByCustomerId", e);
            return null;
        }
    }
    
    public Address getDefaultBillingAddressByUserId(int userId)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Addresses WHERE UserID = ? AND DefaultBillingAddress = 1");
            p.setInt(1, userId);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getDefaultBillingAddressByUserId returned no records for UserID: "+userId);
                return null; //No records returned
            }
            return new Address(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getDefaultBillingAddressByUserId", e);
            return null;
        }
    }
    
    public Address getDefaultShippingAddressByUserId(int userId)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Addresses WHERE UserID = ? AND DefaultShippingAddress = 1");
            p.setInt(1, userId);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getDefaultShippingAddressByUserId returned no records for UserID: "+userId);
                return null; //No records returned
            }
            return new Address(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getDefaultShippingAddressByUserId", e);
            return null;
        }
    }
    
    @Override
    public boolean addAddress(Address a, Customer customer)
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("INSERT  INTO APP.Addresses (CustomerID, UserID, DefaultShippingAddress, DefaultBillingAddress, AddressPrefix1, StreetNumber, StreetName, StreetType, Suburb, State, PostCode, Country, CreatedDate, CreatedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            p.setInt(1, a.getCustomerId());
            p.setInt(2, a.getUserId());
            p.setInt(3, a.getDefaultShippingAddress());
            p.setInt(4, a.getDefaultBillingAddress());
            p.setString(5, a.getAddressPrefix1());
            p.setInt(6, a.getStreetNumber());
            p.setString(7, a.getStreetName());
            p.setString(8, a.getStreetType());
            p.setString(9, a.getSuburb());
            p.setString(10, a.getState());
            p.setString(11, a.getPostcode());
            p.setString(12, a.getCountry());

            p.setTimestamp(13, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(14, customer.getId());
            //Was insert successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to addAddress", e);
            return false;
        }
    }

    //Updating an existing address in the database - this method only changes the stored values, it does not check address exists 
    @Override
    public boolean updateAddress(Address a, Customer customer) {
        try {
            PreparedStatement p = this.conn.prepareStatement("UPDATE APP.Addresses SET CustomerID = ?, UserID = ?, DefaultShippingAddress = ?, DefaultBillingAddress = ?, AddressPrefix1 = ?, StreetNumber = ?, StreetName = ?, StreetType = ?, Suburb = ?, State = ?, PostCode = ?, Country = ?, ModifiedDate = ?, ModifiedBy = ? WHERE ID = ?");
            p.setInt(1, a.getCustomerId());
            p.setInt(2, a.getUserId());
            p.setInt(3, a.getDefaultShippingAddress());
            p.setInt(4, a.getDefaultBillingAddress());
            p.setString(5, a.getAddressPrefix1());
            p.setInt(6, a.getStreetNumber());
            p.setString(7, a.getStreetName());
            p.setString(8, a.getStreetType());
            p.setString(9, a.getSuburb());
            p.setString(10, a.getState());
            p.setString(11, a.getPostcode());
            p.setString(12, a.getCountry());

            p.setTimestamp(13, new java.sql.Timestamp(new java.util.Date().getTime()));
            p.setInt(14, customer.getId());
            p.setInt(15, a.getId());
            //Was update successful?
            int result = p.executeUpdate();
            return (result > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to update Address", e);
            return false;
        }
    }
    
    @Override
    public Address getAddressById(int id) 
    {
        try {
            PreparedStatement p = this.conn.prepareStatement("SELECT * FROM APP.Addresses WHERE ID = ?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            if (!rs.next())
            {
                System.out.println("getAddressById returned no records for ID: "+id);
                return null; //No records returned
            }
            return new Address(rs);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to getUserById", e);
            return null;
        }
    }

    //Delete addresses 
    @Override
    public boolean deleteAddressById(int id) {
        try {
            PreparedStatement p = this.conn.prepareStatement("DELETE FROM APP.Addresses WHERE ID = ?");
            p.setInt(1, id);
            //Was update successful?
            return (p.executeUpdate() > 0);
        }
        catch (Exception e)
        {
            Logging.logMessage("Unable to deleteAddressById", e);
            return false;
        }
    }
}

    