/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
protected Connection conn; //connection null-instance to be initialized in sub-classes

}
