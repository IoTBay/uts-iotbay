package uts.isd.controller;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpSession;

public class Validator implements Serializable{ 

   private String namePattern = "([A-Z][a-z]+[\\s])+[A-Z][a-z]*";       
   private String idPattern = "[a-z0-9]{4,}";       
              
   public Validator(){
   }       

   public boolean validate(String pattern, String input){       
      Pattern regEx = Pattern.compile(pattern);       
      Matcher match = regEx.matcher(input);       
      return match.matches(); 
   }       

   public boolean checkEmpty(String name){       
      return  name.isEmpty();   
   }

   public boolean validateName(String name){                       
      return validate(namePattern,name);   
   }
       
   public boolean validateId(String id){
      return validate(idPattern,id); 
   } 
   
   public void clear(HttpSession session){
       session.setAttribute("idErr", "Enter id");
       session.setAttribute("nameErr", "Enter name");
   }
}

