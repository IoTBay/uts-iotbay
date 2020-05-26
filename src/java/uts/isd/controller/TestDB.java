package uts.isd.controller;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import uts.isd.model.dao.*;

public class TestDB{

    private static Scanner in = new Scanner(System.in);
    private DBConnector connector;
    private Connection conn;
    private DBManager db;
    
    public static void main(String[] args) throws SQLException {
        (new TestDB()).runQueries();
    }
    
    public TestDB(){
        try{
            connector = new DBConnector();
            conn = connector.openConnection();
            db = new DBManager(conn);
        } catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
   
    private char readChoice(){
        System.out.print("Operation CRUDS or * to exit: ");
        return in.nextLine().charAt(0);
    }
    
   private void runQueries() throws SQLException {
       char c;
       while ((c = readChoice()) != '*'){
           switch(c) {
               case 'C':
                   testAdd();
                   break;
               case 'R':
                   testRead();
                   break;
               case 'U':
                   testUpdate();
                   break;
               case "D":
                   testDelete();
                   break;
               case 'S':
                   showAll();
                   break;
               default:
                    System.out.println("unknown command");
                    break;
           }
       }
   }
   
   private void testAdd(){
       System.out.print("Product name: ");
       String name = in.nextLine();
       System.out.print("Product description: ");
       String description = in.nextLine();
       System.out.print("Product initial Quantity: ");
       String initialQuantity = in.nextLine();
       System.out.print("Product price: ");
       String price = in.nextLine();
       System.out.print("Product id: ");
       String id = in.nextLine();
       try {
           db.addProduct(name, description, initialQuantity, price, id);
       } catch (SQLException ex){
           Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE,null,ex);
       }
       System.out.println("Product added to the database");
   }
   
   private void testRead(){
       System.out.print("Product name: ");
       String name = in.nextLine();
       System.out.print("Product id: ");
       String id = in.nextLine();
   }
   
   private void testUpdate(){
   
   }
   
   private void testDelete(){
   
   }
   
   private void showAll(){
   
   }

}
    
    
    
   

 


