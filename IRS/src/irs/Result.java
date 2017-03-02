/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
/**
 *
 * @author Oliver
 */
public class Result {
    
    private HashMap<Date, Integer> heartRate;
            
    //default constructor 
    public Result(){
        heartRate = new HashMap<>();
    }
    
    //construct Result in specific days
    public Result(int days){
        heartRate = new HashMap<>(days);
    }
    
    //check if have this key in heartRate
    public Boolean checkDate(Date date){    
        if (this.heartRate.containsKey(date)) return TRUE;//if occurs, return 1;
        return FALSE;    
    }
    
    //return results in a period (days)
    //before call this method 
    //should check if start or end date is over the range using checkDate method
    public HashMap<Date, Integer> getResults(Date startDate, Date endDate){
        //convert SQL.Date to LocalDate and calculate days between start and end
        LocalDate start = startDate.toLocalDate();
        LocalDate end = endDate.toLocalDate();
        long days = ChronoUnit.DAYS.between(start, end);
        
        //initial the return data structure
        HashMap<Date, Integer> results = new HashMap<>((int)days);
        
        //set all values from heartRate between given start and end dates   
        do{
            results.putIfAbsent(Date.valueOf(start), 
                    this.heartRate.get(Date.valueOf(start)));
            start.plusDays(1);
        }while(!start.isAfter(end));
        
        return results;
    }
}
