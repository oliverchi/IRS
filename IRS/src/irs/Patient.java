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
import java.time.LocalDate;
import javafx.scene.control.Alert;

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
            
            if ( rs.next() ){
                nextid = rs.getString("id");
                nextid = Integer.toString(Integer.parseInt(nextid, 10) + 1);                
            }
            disconnect(conn);//close the connection to DB
            return nextid;
            
        } catch(SQLException e){
              return "";//error
        }    
    }
    
       
    
    //check if patient ID is database;
    public boolean patientIDCheck(String pid){
        if (!pid.matches("\\d\\d\\d\\d\\d")) return false;//if not five digits, then input error

        //connect to database, search if name occurs in table patient
        try{           
            Connection conn = connect();//connect() from Data.java
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM patient WHERE id = " + "'"+pid+"'";
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                disconnect(conn);//close the connection to DB
                return false;//true means matched and have data from above retrivement
            }
            disconnect(conn);//close the connection to DB
            return true; 
            
        } catch(SQLException e){
            System.out.println("error in patient id check");
            System.out.println(e.toString());
            return false;//error
        } 
    }
    
    
    //check if a valid date
    public boolean checkBirthdayValid(String birthday) {
        if(!birthday.matches("\\d\\d\\d\\d-\\d+-\\d+")) return false;//check date format        
        if (LocalDate.parse(birthday).getYear() >= (LocalDate.now().getYear() - 150) &&
                LocalDate.parse(birthday).getYear() <= (LocalDate.now().getYear() - 1)){
            return true;//not valid date
        } else {
            return false;//valid date        
        }
    }
    
    //check if mobile number is 10 digitals
    public boolean checkPhoneValid(String phone) {
        if (phone.matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")) {
            return true;
        } else {
            return false;
        }
    }
    
    //check if address string contains number
    public boolean checkAddressValid(String street) {
        if (street.matches(".*\\d+.*")) {
            return true;
        } else {
            return false;
        }
    }
    
    //check if city string doesn't contain number
    public boolean checkCityValid(String city) {
        if (!city.matches(".*\\d+.*")) {
            return true;
        } else {
            return false;
        }
    }    
    
    //check if postcode string is 4 digitals
    public boolean checkPostcodeValid(String postcode) {
        if (postcode.matches("\\d\\d\\d\\d")) {
            return true;
        } else {
            return false;
        }
    }
    
    //before store data, should check duplicated data first;
    //store new patient information into DB;
    //return boolean value indicates the action successful or not
    public boolean insertNewPatient(Patient p){
        //connect to database, do update
        try{           
            Connection conn = connect();//connect() from Data.java
            PreparedStatement pst = conn.prepareStatement("INSERT INTO patient "
                    + "(id, firstName, lastName, gender, dob, phone, street, "
                    + "city, state, postcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, p.getID()); 
            pst.setString(2, p.getFirstName());
            pst.setString(3, p.getLastName());
            pst.setString(4, p.getGender());
            pst.setString(5, p.getDOB().toString()); //System.out.println(p.getDOB().toString());
            pst.setString(6, p.getPhone());
            pst.setString(7, p.getStreet());
            pst.setString(8, p.getCity());
            pst.setString(9, p.getState()); 
            pst.setString(10, p.getPostcode());            
            pst.executeUpdate();
            
            disconnect(conn);//close the connection to DB
            
        } catch(SQLException e){
            System.out.println("error in insert new patient");
            System.out.println(e.toString());
            return false;//error
        }   
        
        return true;
    }
    
    
    //check patient ID if exist first before do deletation
    //delete one patient 
    public boolean deletePatient( String pid){
        //connect to database, do update
        try{           
            Connection conn = connect();//connect() from Data.java        
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM patient WHERE id =" + "'"+pid+"'";
            stmt.executeUpdate(sql);
            disconnect(conn);//close the connection to DB
        } catch(SQLException e){
            System.out.println(e.toString());
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
        Patient p = new Patient();       
        
        //connect to database, search one patient
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM patient "
                    + "WHERE " + attributeName +" = ?", 
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("SELECT * FROM patient "
                    + "WHERE " + attributeName +" = ?");            
            pst.setString(1, value);            
            ResultSet rs = pst.executeQuery();            
            while (rs.next()) {
                rs.first();
                p.setID(rs.getString("id"));
                p.firstName = rs.getString("firstname");
                p.lastName = rs.getString("lastName");
                p.gender = rs.getString("gender");
                p.dob = rs.getDate("dob");
                p.phone = rs.getString("phone");
                p.city = rs.getString("city");
                p.street = rs.getString("street");
                p.state = rs.getString("state");
                p.postcode = rs.getString("postcode");
                disconnect(conn);//close the connection to DB
                return p;
            }
            disconnect(conn);//close the connection to DB
            return null;
        } catch(SQLException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.toString());
            System.out.println("SQLException happens in searchPatient() of class Patient");  
            return null;//error
        }               
    }
    
    //search patient through one attribute and its value
    public Patient searchPatient(String attributeName1, String value1, 
            String attributeName2, String value2){
        Patient p = new Patient();    

        //connect to database, search one patient
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM patient "
                    + "WHERE " + attributeName1 + " = ? and " 
                    + attributeName2 + " = ?", ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY); 
            pst.setString(1, value1);
            pst.setString(2, value2);
            ResultSet rs = pst.executeQuery();            
            while (rs.next()) {
                rs.first();
                p.setID(rs.getString("id"));
                p.firstName = rs.getString("firstname");
                p.lastName = rs.getString("lastName");
                p.gender = rs.getString("gender");
                p.dob = rs.getDate("dob");
                p.phone = rs.getString("phone");
                p.city = rs.getString("city");
                p.street = rs.getString("street");
                p.state = rs.getString("state");
                p.postcode = rs.getString("postcode");
                disconnect(conn);//close the connection to DB
                return p;
            }
            disconnect(conn);//close the connection to DB
            return null;
        } catch(SQLException e){
            System.out.println("SQLException happens in searchPatient2() of class Patient");  
            return null;//error
        }        
    }   
 
}
