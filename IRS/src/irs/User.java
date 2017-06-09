/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Oliver
 */
public class User extends Data{
    
    private String username;//at least 6 characters
    private String password;//at least one letter, one number and 8 characters
    private String staffID;//6 characters like AB1001
    private String position;//must be one of below: 
                            //RN(registered nurse),CN(clinic nurse),
                            //NM(nurse manager),
                            //ND(nurse director),DR(GP or specialist)
    
    public User(){
    }
    
    public User(String name, String key, String id, String position){
        this.username = name;
        this.password = key;
        this.staffID = id;
        this.position = position;
    }
    
    public String getName(){
        return this.username;
    }
    
    public String getKey(){
        return this.password;
    }
    
    public String getID(){
        return this.staffID;
    }
    
    public String getPosition(){
        return this.position;
    }
    
    public void setName(String name){
        this.username = name;
    }
    
        
    public void setKey(String key){
        this.password = key;
    }
    
    public void setID(String id){
        this.staffID = id;
    }
    
    public void setPosition(String pos){
        this.position = pos;
    }
    
    //if inputs are all valid, set return message as ""; otherwise, return error message
    public String checkIfValid(String name, String password, String id, String pos){
        //define the default return message as ""
        String msg = "";
        
        //check if user name has enough length
        if (name.length()<6) { 
            msg = "User name "
                + "should be at least six characters. Please try again!";
            return msg;
        }
        
        //check password if valid
        if (password.length() < 8 ){
            msg = "Password should be at least eight characters. Please try again!";
            return msg;
        } else if (!password.matches(".*\\d+.*")){//decide if no number include
            msg = "Password should contain at least one number. Please try again!";
            return msg;
        } else if (!password.matches(".*[a-zA-Z].*")){//decide if no letter include
            msg = "Password should at least contain one letter. Please try again!";
            return msg;
        } else {            
        }
        
        //check ID if correct; Actually this part should include check in database later.
        if ( id.length()!=6 ) {
            msg = "Staff ID input is incorrect. Please try again!";
            return msg;
        }
        
        //check position input if correct
        if ( pos.isEmpty() ) {
            msg = "Please don't forget entering your position!";
            return msg;
        }      
        
        return msg;
    }
    
    //check if user name is database and password is matched;
    //if return true, means input matches the correct information.
    public boolean loginCheck(String name, String pwd){
        //connect to database, search if name occurs in table login
        try{           
            Connection conn = connect();//connect() from Data.java
            PreparedStatement pst = conn.prepareStatement("Select * from login"
                    + " where username=? and password=?");
            pst.setString(1, name); 
            pst.setString(2, pwd);
            ResultSet rs = pst.executeQuery();  
            disconnect(conn);//close the connection to DB
            return rs.next(); //true means matched and have data from above retrivement
          
        } catch(SQLException e){
              return false;//error
        }        
    }  

    //check if user name is database;
    public boolean userCheck(String name){
        //connect to database, search if name occurs in table login
        try{           
            Connection conn = connect();//connect() from Data.java
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login "
                    + "WHERE username = " + name);  
            disconnect(conn);//close the connection to DB
            return rs.next();//true means that username exists.
            
        } catch(SQLException e){
              return false;//error
        } 
    }  
    
    //A staff only can have one user account
    //check if staff ID is database;
    public boolean staffIDCheck(String id){
        //connect to database, search if name occurs in table login
        try{           
            Connection conn = connect();//connect() from Data.java
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM login "
                    + "WHERE staffid = " + id);  
            disconnect(conn);//close the connection to DB
            return rs.next();//true means that staffID exists.
            
        } catch(SQLException e){
              return false;//error
        } 
    }
    
    //before store data, should check duplicated data first;
    //store new user information into DB;
    //return boolean value indicates the action successful or not.
    public boolean insertNewUser(String name, String pwd, String staffID, String position){
        
        if (userCheck(name)|| staffIDCheck(staffID)) return false;//user account exist

        //connect to database, search if name occurs in table login
        try{           
            Connection conn = connect();//connect() from Data.java
            PreparedStatement pst = conn.prepareStatement("INSERT INTO login "
                    + "(username, password, staffid, position) VALUES "
                    + "(?, ?, ?, ?)");
            pst.setString(1, name); 
            pst.setString(2, pwd);
            pst.setString(3, staffID);
            pst.setString(4, position);
            int i = pst.executeUpdate();
            disconnect(conn);//close the connection to DB
            if(i != 0) return true;//return 1. 1 row operation is done.
            return false;//return 0; no action is taken.
        } catch(SQLException e){
              return false;//error
        }        
    }
}
