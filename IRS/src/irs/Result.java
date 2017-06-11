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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author Oliver
 */
public class Result extends Data{
    private final SimpleStringProperty patientID;
    private final SimpleStringProperty date;
    private final SimpleIntegerProperty heartRate; 
    
    public Result(String pid, String date, int rate) {
        this.patientID = new SimpleStringProperty(pid);
        this.date= new SimpleStringProperty(date);
        this.heartRate= new SimpleIntegerProperty(rate);
    }
 
    public String getPatientID() {
        return patientID.get();
    }
 
    public void setPatientID(String pid) {
        patientID.set(pid);
    }
 
    public String getDate() {
        return date.get();
    }
 
    public void setDate(String d) {
        date.set(d);
    }
 
    public int getHeartRate() {
        return heartRate.get();
    }
 
    public void setHeartRate(int r) {
        heartRate.set(r);
    }
    
    //get one patient's heart rate record according to given patient ID and date
    public Result getResult(String pid, String date){   
        Result result = new Result(null,null,0);
        
        //connect to database, retrive one row of table result
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("SELECT result FROM results "
                    + "WHERE id = ? AND date = ?");
            pst.setString(1, pid); 
            pst.setString(2, date);            
            ResultSet rs = pst.executeQuery(); 
            
            while (rs.next()) {
                result.setHeartRate(rs.getInt("result"));    
                result.setDate(date);
                result.setPatientID(pid);
            }
            disconnect(conn);//close the connection to DB
            return result;//return the retrieved value            
        } catch(SQLException e){
            System.out.println("SQLException happens in getResult() of class Result"); 
            System.out.println(e.toString());
            return null;//error
        }
    }
    
    //set one patient's heart rate record to DB
    public boolean setResult(Result result){ 
        //connect to database, retrive one row of table result
        try{           
            Connection conn = connect();//connect() from Data.java        
            PreparedStatement pst = conn.prepareStatement("INSERT INTO results "
                    + "(id, date, result) VALUES (?, ?, ?)");
            pst.setString(1, result.getPatientID()); 
            pst.setString(2, result.getDate());
            pst.setString(3, String.valueOf(result.getHeartRate()));                       
            int b = pst.executeUpdate();
            disconnect(conn);//close the connection to DB
            if (b != 0) return true;
            return false;
        } catch(SQLException e){
            System.out.println("SQLException happens in setResult() of class Result");
            System.out.println(e.toString());
            return false;//error
        }        
    }
    
    
    //get one patient's heart rate records according to given patient ID, start date and end date
    /*public ObservableList<Result> getResults(String pid, LocalDate startDate,
            LocalDate finishDate){
        
        ObservableList<Result> results = FXCollections.observableArrayList();
        ObservableList<String> r1 = FXCollections.observableArrayList();
        ObservableList<String> r2 = FXCollections.observableArrayList();
        ObservableList<Integer> r3 = FXCollections.observableArrayList();        
        Result result = new Result();
        
        //connect to database, retrive one row of table result
        try{           
            Connection conn = connect();//connect() from Data.java        
            
            //retrieve all values from database between given start and end dates   
            do{
                PreparedStatement pst = conn.prepareStatement("SELECT * FROM results "
                        + "WHERE id = ? AND date = ?");
                pst.setString(1, pid); 
                pst.setString(2, startDate.toString());                
                ResultSet rs = pst.executeQuery();
                
                while (rs.next()) {                    
                    result.setHeartRate(rs.getInt("result")); 
                    result.setDate(rs.getDate("date").toString());                   
                    result.setPatientID(rs.getString("id"));                     
                    results.add(result); 
                    r1.add(result.patientID);
                    r2.add(result.date);
                    r3.add(result.heartRate);                    
                }
                
                startDate = startDate.plusDays(1);//increment of date until over last day
                //System.out.println(startDate.toString());
                                
            }while(!startDate.isAfter(finishDate));//until date is over last day
                        
            disconnect(conn);//close the connection to DB
            
            //for(Result r  : results){System.out.println(r.date);}
            for (int i = 0; i < results.size(); i++){
                results.get(i).setPatientID(r1.get(i));
                results.get(i).setDate(r2.get(i));
                results.get(i).setHeartRate(r3.get(i));
                results.forEach((r)->{System.out.println(r.patientID);});
                results.forEach((r)->{System.out.println(r.date);});
                results.forEach((r)->{System.out.println(r.heartRate);});
            }        
            //results.forEach((r)->{System.out.println(r.patientID);});
            //results.forEach((r)->{System.out.println(r.date);});
            //results.forEach((r)->{System.out.println(r.heartRate);});
            return results;//return the retrieved value  
        } catch(SQLException e){
            System.out.println("SQLException happens in getResults() of class Result");  
            System.out.println(e.toString());
            return null;//error
        }  
    }*/ 
    
    //get one patient's heart rate records according to given patient ID, start date and end date
    public Map<String, Integer> getResults(String pid,  LocalDate startDate,
            LocalDate finishDate){
        Map<String, Integer> ratePairs = new HashMap<>(); 
                              
        //connect to database, retrive one row of table result
        try{           
            Connection conn = connect();//connect() from Data.java        
            
            //retrieve all values from database between given start and end dates   
            do{
                PreparedStatement pst = conn.prepareStatement("SELECT result FROM results "
                        + "WHERE id = ? AND date = ?");
                pst.setString(1, pid); 
                pst.setString(2, startDate.toString());                
                ResultSet rs = pst.executeQuery();
                
                while (rs.next()) {                    
                    ratePairs.put(startDate.toString(), rs.getInt("result"));
                }
                
                startDate = startDate.plusDays(1);//increment of date until over last day
                //System.out.println(startDate.toString());
                                
            }while(!startDate.isAfter(finishDate));//until date is over last day
                        
            disconnect(conn);//close the connection to DB
            return ratePairs;
        } catch(SQLException e){
            System.out.println("SQLException happens in getResults() of class Result");  
            System.out.println(e.toString());
            return null;//error
        }  
    }   
}
    

