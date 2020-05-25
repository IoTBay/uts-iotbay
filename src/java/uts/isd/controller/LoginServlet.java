/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uts.isd.model.User;
import uts.isd.model.dao.DBUser;
import uts.isd.model.dao.IUser;

/**
 *
 * @author rhys
 */
public class LoginServlet extends HttpServlet 
{


    @Override   
    protected void doPost(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException 
    {       
        //1- retrieve the current session
        request.getAttribute("username");

        //2- create an instance of the Validator class    

        //3- capture the posted email      

        //4- capture the posted password    

        //5- retrieve the manager instance from session      
         
        User user = null;       

        try {
            IUser db = new DBUser();
            db.authenticateUser(request.getAttribute("username"), request.getAttribute("password"));
        
               //6- find user by email and password
        } catch (Exception ex) {           

              Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);       

        }


         if (     /*7-   validate email  */   ) {           

                  //8-set incorrect email error to the session           

                  //9- redirect user back to the login.jsp     

          } else if ( /*10-   validate password  */ ) {                  

                  //11-set incorrect password error to the session           

                  //12- redirect user back to the login.jsp          

           } else if (user != null) {                     

                  //13-save the logged in user object to the session           

                  //14- redirect user to the loginWelcome.jsp     

           } else {                       

                  //15-set user does not exist error to the session           

                  //16- redirect user back to the login.jsp       

           }   

  }
