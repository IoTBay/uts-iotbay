/*
 * UTS Introduction to Software Development
 * IOT Bay - Assignment 2
 * @author Rhys Hanrahan 11000801
 */
package uts.isd.model.dao;
import java.sql.Connection;


/** 
* Super class of DAO classes that stores the database information 
*  
*/

public abstract class DB {   

protected String URL = "jdbc:derby://localhost:1527/iotdb";//replace this string with your jdbc:derby local host url   
protected String db = "iotdb";//name of the database   
protected String dbuser = "iotdb";//db root user   
protected String dbpass = "iotdb"; //db root password   
protected String driver = "org.apache.derby.jdbc.ClientDriver"; //jdbc client driver - built in with NetBeans   
protected static Connection conn; //connection null-instance to be initialized in sub-classes

}
