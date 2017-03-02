/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.sql.Date;

/**
 *
 * @author Oliver
 */
public class Patient {
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String postcode;
    //private Result result; 
    
    //default construct without any input
    public Patient(){              
    }
    
    //construct with all inputs when initalising patient account;
    //only result information is not available at beginning
    public Patient(String fN, String lN, String sex, Date birth, String phone, 
            String street, String city, String state, String postcode){
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
    
    /*
    public Result getResult(){
        return this.result;
    }        
    
    public void setResult(Result r){
        this.result = r;
    }
    */
}
