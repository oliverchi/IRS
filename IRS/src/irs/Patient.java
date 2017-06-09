/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Oliver
 */
public class Patient extends Data{
    private String id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String postcode;

    
    //default construct without any input
    public Patient(){              
    }
    
    //construct with all inputs when initalising patient account;
    //only result information is not available at beginning
    public Patient(String id, String fN, String lN, String sex, Date birth, String phone, 
            String street, String city, String state, String postcode){
        this.id = id;
        this.firstName = fN;
        this.lastName = lN;
        this.gender = sex;
        this.dob = birth;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }
    
    public String getFirstName(){
        return this.firstName;
    }
    
    public String getLastName(){
        return this.lastName;
    }
    
    public String getGender(){
        return this.gender;
    }
    
    public Date getDOB(){
        return this.dob;
    }
    
    public String getStreet(){
        return this.street;
    }
    
    public String getCity(){
        return this.city;
    }
    
    public String getState(){
        return this.state;
    }
    
    public String getPostcode(){
        return this.postcode;
    }
    
    public String getPhone(){
        return this.phone;
    }
    
    public void setFirstName(String a){
        this.firstName = a;
    }
    
    public void setLastName(String a){
        this.lastName = a;
    }
    
    public void setGender(String a){
        this.gender = a;
    }
    
    public void setDOB(Date a){
        this.dob = a;
    }
    
    public void setStreet(String a){
        this.street = a;
    }
    
    public void setCity(String a){
        this.city = a;
    }
    
    public void setState(String a){
        this.state = a;
    }
    
    public void setPostcode(String a){
        this.postcode = a;
    }
    
    public void setPhone(String a){
        this.phone = a;
    }    
    
    public String getID(){
        return this.id;
    }
    
    public void setID(String a){
        this.id = a;
    }
    
    //find out next available ID for new patient;
    //if error happened, then put return value as "";
    //return string is the next available ID.
    public String nextID(){
        String nextid = "";
        try{           
            Connection conn = connect();//connect() from Data.java
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS id FROM patient");  
            disconnect(conn);//close the connection to DB
            if ( rs.next() ){
                nextid = rs.getString("id");
                nextid = Integer.toString(Integer.parseInt(nextid, 10) + 1);                
            }
            return nextid;
            
        } catch(SQLException e){
              return "";//error
        }    
    }
    
       
    
    //check if patient ID is database;
    public boolean patientIDCheck(String pid){
        //connect to database, search if name occurs in table patient
        try{           
            Connection conn = connect();//connect() from Data.java
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient "
                    + "WHERE id = " + pid);  
            disconnect(conn);//close the connection to DB
            return rs.next();//true means that staffID exists.
            
        } catch(SQLException e){
              return false;//error
        } 
    }
    
    //before store data, should check duplicated data first;
    //store new patient information into DB;
    //return boolean value indicates the action successful or not
    public boolean insertNewPatient(String pid, String fn, String ln, 
            String gender,String birth, String phone, String street, 
            String city, String state, String pcode){
        
        if ( patientIDCheck(id)) return false;//the same patient ID exist

        //connect to database, do update
        try{           
            Connection conn = connect();//connect() from Data.java
            PreparedStatement pst = conn.prepareStatement("INSERT INTO patient "
                    + "(id, firstName, lastName, gender, dob, phone, street, "
                    + "city, state, postcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, pid); 
            pst.setString(2, fn);
            pst.setString(3, ln);
            pst.setString(4, gender);
            pst.setString(5, birth); 
            pst.setString(6, phone);
            pst.setString(7, street);
            pst.setString(8, city);
            pst.setString(9, state); 
            pst.setString(10, pcode);            
            pst.executeUpdate();
            
            disconnect(conn);//close the connection to DB
            
        } catch(SQLException e){
              return false;//error
        }   
        
        return true;
    }
    
    
    //check patient ID if exist first before do deletation
    //delete one patient 
    public boolean deletePatient( String pid){
        //patient ID check
        if ( !patientIDCheck(id)) return false;//this patient ID does't exist

        //connect to database, do update
        try{           
            Connection conn = connect();//connect() from Data.java        
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM Registration WHERE id =" + pid;
            stmt.executeUpdate(sql);
            disconnect(conn);//close the connection to DB
        } catch(SQLException e){
              return false;//error
        }
        return true;
    }
    
    //no need; Duplicate with serchPatient(string,string)
    //retrieve one patient when patient ID is known
    /*
    public Patient getPatient(String pid){
        Patient p = null;

        //patient ID check (I think no need to check because the action will be duplicate if do check)
        //if ( !patientIDCheck(id)) return p;//this patient ID does't exist

        //connect to database, search one patient
        try{           
            Connection conn = connect();//connect() from Data.java        
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM patient "
                    + "WHERE id = " + pid);
            disconnect(conn);//close the connection to DB
            while (rs.next()) {
                p.id = pid;
                p.firstName = rs.getString("fistname");
                p.lastName = rs.getString("lastName");
                p.gender = rs.getString("gender");
                p.dob = rs.getDate("dob");
                p.phone = rs.getString("phone");
                p.city = rs.getString("city");
                p.street = rs.getString("street");
                p.state = rs.getString("state");
                p.postcode = rs.getString("postcode");
            }
        } catch(SQLException e){
              return null;//error
        }
        return p;
    }*/
    
    
    //search patient through one attribute and its value
    public Patient searchPatient(String attributeName, String value){
        Patient p = null;       

        //connect to database, search one patient
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM patient "
                    + "WHERE ? = ?");
            pst.setString(1, attributeName); 
            pst.setString(2, value);            
            ResultSet rs = pst.executeQuery();
            disconnect(conn);//close the connection to DB
            while (rs.next()) {
                p.id = rs.getString("id");
                p.firstName = rs.getString("fistname");
                p.lastName = rs.getString("lastName");
                p.gender = rs.getString("gender");
                p.dob = rs.getDate("dob");
                p.phone = rs.getString("phone");
                p.city = rs.getString("city");
                p.street = rs.getString("street");
                p.state = rs.getString("state");
                p.postcode = rs.getString("postcode");
            }
        } catch(SQLException e){
            System.out.println("SQLException happens in searchPatient() of class Patient");  
            return null;//error
        }
        return p;        
    }
}
