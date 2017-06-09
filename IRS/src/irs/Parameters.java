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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Oliver
 */
public class Parameters extends Data{
    private final SimpleStringProperty patientID;
    private final SimpleIntegerProperty min;//min value in healthy range of heart rate
    private final SimpleIntegerProperty max;//max value in healthy range of heart rate
    private final SimpleIntegerProperty k;//days of monitoring patient's heart rates 
    private final SimpleIntegerProperty p;//p% is an acceptable percentage of past test numbers / past days
    
    public Parameters(String pid){
        this.patientID = new SimpleStringProperty(pid);
        this.min = new SimpleIntegerProperty(60);
        this.max = new SimpleIntegerProperty(100);
        this.k = new SimpleIntegerProperty(7);
        this.p = new SimpleIntegerProperty(100);
    }  
    
    public Parameters(String pid, int min, int max, int k, int p){
        this.patientID = new SimpleStringProperty(pid);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
        this.k = new SimpleIntegerProperty(k);
        this.p = new SimpleIntegerProperty(p);
    }
    
    public String getID(){return patientID.get();}
    public int getMIN(){return min.get();}
    public int getMAX(){return max.get();}
    public int getK(){return k.get();}
    public int getP(){return p.get();}
    
    public void setID(String id){patientID.set(id);}
    public void setMIN(int i){min.set(i);}
    public void setMAX(int i){max.set(i);}
    public void setK(int i){k.set(i);}
    public void setP(int i){p.set(i);}
    
    
    //retrieve parameters from database according to patient ID
    public Parameters getParameters(String pid){
        Parameters parameters = new Parameters(pid);
        
        //connect to database, retrive one row of table result
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM parameters "
                    + "WHERE patientID = ?");
            pst.setString(1, pid);             
            ResultSet rs = pst.executeQuery();
            disconnect(conn);//close the connection to DB
            while (rs.next()) {
                parameters.min.set(rs.getInt("min"));  
                parameters.max.set(rs.getInt("max"));  
                parameters.k.set(rs.getInt("k"));  
                parameters.p.set(rs.getInt("p")); 
            }
            return parameters;
        } catch(SQLException e){
            System.out.println("SQLException happens in getParameters() of class Parameters");  
        }
        return parameters;
    }
    
    //insert parameter into database
    public boolean setParameters(Parameters parameters){
        //connect to database, retrive one row of table parameters
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("INSERT INTO parameters "
                    + "(patientID, minrate, maxrate, k, p) VALUES (?, ?, ?, ?, ?)");
            pst.setString(1, parameters.getID()); 
            pst.setString(2, String.valueOf(parameters.getMIN()) );
            pst.setString(3, String.valueOf(parameters.getMAX()) );
            pst.setString(4, String.valueOf(parameters.getK()) );
            pst.setString(5, String.valueOf(parameters.getP()) );
            
            int b = pst.executeUpdate();
            disconnect(conn);//close the connection to DB
            if (b != 0) return true;
            return false;
        } catch(SQLException e){
            System.out.println("SQLException happens in setParameters() of class Parameters");  
            return false;//error
        }        
    }
    
    
}
