/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author Oliver
 */



public class HeartRatePage extends Patient implements Initializable {
    User user = new User();
    Patient patient = new Patient();
    
    @FXML
    private Button searchBtn;    
    @FXML
    private Button insertBtn;  
    @FXML
    private Button deleteBtn;
    @FXML
    private TextField pidField;    
    @FXML
    private TextField fnField;    
    @FXML
    private TextField lnField;    
    @FXML
    private TextField dobField;    
    @FXML
    private TextField genderField;    
    @FXML
    private TextField phoneField;    
    @FXML
    private TextField streetField;    
    @FXML
    private TextField cityField;    
    @FXML
    private TextField stateField;    
    @FXML
    private TextField postcodeField;    
    @FXML
    private Label patientMsg;
    @FXML
    private Label welcomeMsg;
    
    
    
    /**
     * initialises the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user = IRS.getInstance().getUser();          
        user = user.getUser(user);
        welcomeMsg.setText( "welcome, " + user.getID() );
        
        setTooltip();//set tooltips 
    }   
    
    @FXML
    void keyInputOnTextField(ActionEvent event){
        patientMsg.setText("");//reset message
    }
    
    @FXML
    void searchBtnOnAction(ActionEvent event) { 
        patientMsg.setText("");//reset message
        //initialise state indicator
        int indicator = 0;//no input
        
        //value indicator
        if( pidField.getText().isEmpty() ){
            if ( !phoneField.getText().isEmpty() ) indicator = 5;//has input of phone
            if( !fnField.getText().isEmpty() && !lnField.getText().isEmpty() )  
                if (indicator!=5) {
                    indicator = 7;//input of name
                } else {
                    indicator = 1;//has input of patient name and phone
                }
        } else if( fnField.getText().isEmpty() && lnField.getText().isEmpty() ){
            indicator = 2;//has input of id 
            if ( !phoneField.getText().isEmpty() ) indicator = 6;//has input of id and phone
        } else if ( phoneField.getText().isEmpty() ) {
            indicator = 3;//has input of id and name
        } else {
            indicator = 4;//has input of id, name and phone
        }
        
        //search according to different inputs
        switch(indicator){
            case 0:
                patientMsg.setText("no input");
                break;
                
            case 1://search name and phone
                patient = searchPatient("firstname", fnField.getText(),"lastname", 
                        lnField.getText());
                if ( patient == null ){
                    patient = searchPatient("phone", phoneField.getText());
                    if (patient == null){
                        patientMsg.setText("patient's information like name and phone number is incorrect");
                    } else {
                        patientMsg.setText("");
                        display(patient);
                    }
                } else {
                    patientMsg.setText("");
                    display(patient);
                }
                break;
                
            case 2://search id
                patient = searchPatient("id", pidField.getText());
                if (patient == null){
                    patientMsg.setText("patient's information like ID is incorrect,please try again");
                } else {
                    patientMsg.setText("");
                    display(patient);
                }
                break;
                
            case 3://search name and id
                patient = searchPatient("firstname", fnField.getText(),"lastname", 
                        lnField.getText()); 
                if ( patient == null ){
                    patient = searchPatient("id", pidField.getText());
                    if (patient == null){
                        patientMsg.setText("patient's information like name is incorrect,please try again");
                    } else {
                        patientMsg.setText("");
                    display(patient);
                    }
                } else {
                    patientMsg.setText("");
                    display(patient);
                }
                break;            
                    
            case 4://search id, name and phone
                patient = searchPatient("id", pidField.getText());
                if (patient == null){
                    patient = searchPatient("firstname", fnField.getText(),"lastname", 
                        lnField.getText());
                    if (patient == null ){
                        patient = searchPatient("phone", phoneField.getText());
                        if (patient == null){
                            patientMsg.setText("patient's information like ID is incorrect,please search again");
                            resetTextField();
                        } else {
                            patientMsg.setText("");
                            display(patient);
                        }
                    } else {
                        patientMsg.setText("");
                        display(patient);
                    }
                } else {
                    patientMsg.setText("");
                    display(patient);
                }
                break;                
                
            case 5://search phone
                patient = searchPatient("phone", phoneField.getText());
                if ( patient == null){
                    patientMsg.setText("patient's information like phone number is incorrect,please search again");
                } else {
                    patientMsg.setText("");
                    display(patient);
                }
                break;                
                
            case 6://search phone and id
                patient = searchPatient("phone", phoneField.getText());
                if ( searchPatient("phone", phoneField.getText()) == null){
                    patient = searchPatient("id", pidField.getText());
                    if (patient == null){
                    patientMsg.setText("patient's information like phone number is incorrect,please try again");
                    } else {
                    patientMsg.setText("");
                    display(patient);
                    }
                } else {
                    patientMsg.setText("");
                    display(patient);
                }                
                break;                
                
            case 7://search name 
                patient = searchPatient("firstname", fnField.getText(),"lastname", 
                        lnField.getText());
                if (patient == null ){
                    patientMsg.setText("patient's information like name is incorrect,please search again");
                } else {
                    patientMsg.setText("");
                    display(patient);
                }
                break;                
                
            default:
                break; 
        }
        
    }
    
    private boolean checkIfAllInputsAvailable(){
        //check date input
        if (!checkBirthdayValid(dobField.getText()) ){
            patientMsg.setText("Incorrect date of birth, please try again!");
                return false;
        }
        //check phone if 10 digits
        if(!checkPhoneValid(phoneField.getText())){
            System.out.println(phoneField.getText());
            patientMsg.setText("Incorrect data of phone, please try again!");
            return false;
        }
        //check street format
        if (!checkAddressValid(streetField.getText())){
            patientMsg.setText("Incorrect street input, please try again!");
            return false;
        }
        //check city format
        if(!checkCityValid(cityField.getText())){
            patientMsg.setText("Incorrect city input, please try again!");
            return false;
        }
        //check postcode format        
        if (!checkPostcodeValid(postcodeField.getText())){
            patientMsg.setText("Incorrect post code format, please try again!");
            return false;
        }        
        //check ID input
        if ( !patientIDCheck(pidField.getText())) {//true: ID existed so can't insert
            patientMsg.setText("Incorrect patient ID, please try again!");//check if the same patient ID exist
            return false;
        }
        return true;
    }
    
    @FXML
    void insertBtnOnAction(ActionEvent event) {  
        patientMsg.setText("");//reset message
        //check valid of input values 
        if (checkIfAllInputsAvailable()){
            //get all values for new patient
            patient.setID(pidField.getText());
            patient.setFirstName(fnField.getText());
            patient.setLastName(lnField.getText());
            patient.setDOB(Date.valueOf(dobField.getText()));
            patient.setCity(cityField.getText());
            patient.setGender(genderField.getText());
            patient.setPhone(phoneField.getText());
            patient.setStreet(streetField.getText());
            patient.setState(stateField.getText());
            patient.setPostcode(postcodeField.getText());            
            //insert into database
            if (insertNewPatient(patient)){
                patientMsg.setText("INSERT NEW PATIENT " + patient.getID() +" SUCCESSFULLY!");
                resetTextField();
            }else{
                patientMsg.setText("Database Error, please try later");
            }                 
        }
    }
    
    @FXML
    void deleteBtnOnAction(ActionEvent event) {  
        patientMsg.setText("");//reset message
        String pid = pidField.getText();
        if ( !patientIDCheck(pid)) {//true: ID existed so can delete 
            if(alertMsg("Are you OK to delete informaiton of this patient")){
                if (deletePatient(pid) ) {
                    patientMsg.setText("DELETE PATIENT ID "+ pid +" SUCCESSFULLY!");
                    resetTextField();
                }else{
                    patientMsg.setText("UNABLE TO DELETE PATIENT ID "+ pid + 
                            ", PLEASE TRY LATER!");
                }
            }
        }
    }
    
    private boolean alertMsg(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("CONFIRMATION");
        alert.setHeaderText("ATTENTION! YOUR ACTION IS NOT REVERSABLE");
        alert.setContentText(msg);
        alert.showAndWait();
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        } 
        if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            return false;
        } 
        return false;
    }
    
    private void resetTextField(){
        pidField.setText("");
        fnField.setText("");
        lnField.setText("");
        dobField.setText("");
        phoneField.setText("");
        genderField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        postcodeField.setText("");
    }
    
    //display one patient one page
    public void display(Patient p){
        pidField.setText(p.getID());
        fnField.setText(p.getFirstName());
        lnField.setText(p.getLastName());
        dobField.setText(p.getDOB().toString());
        phoneField.setText(p.getPhone());
        genderField.setText(p.getGender());
        streetField.setText(p.getStreet());
        stateField.setText(p.getState());
        cityField.setText(p.getCity());
        postcodeField.setText(p.getPostcode());        
    }
    
    //setup all tooltips here
    void setTooltip(){
        //set tooltip for patient ID Field
        final Tooltip tooltip1 = new Tooltip();
        tooltip1.setText(
            "Patient ID \n"
                    + "5 digits in length\n"                    
        );
        pidField.setTooltip(tooltip1);
        
        //set tooltip for delete Button
        final Tooltip tooltip2 = new Tooltip();
        tooltip2.setText(
            "Delete patient\n" +
                "***using its ID" 
        );
        deleteBtn.setTooltip(tooltip2);
        
        //set tooltip for gender Field
        final Tooltip tooltip3 = new Tooltip();
        tooltip3.setText(
            "Male: \"M\"\n" +
                "Female: \"F\"" 
        );
        genderField.setTooltip(tooltip3);
        
        //set tooltip for DOB Field
        final Tooltip tooltip4 = new Tooltip();
        tooltip4.setText(
            "FORMAT:\n" +
                "YYYY-MM-DD" 
        );
        dobField.setTooltip(tooltip4);
        
        //set tooltip for search Button
        final Tooltip tooltip5 = new Tooltip();
        tooltip5.setText(
            "Search patient\n" +
                "***using its ID or name or phone" 
        );
        searchBtn.setTooltip(tooltip5);
        
        //set tooltip for insert Button
        final Tooltip tooltip6 = new Tooltip();
        tooltip6.setText(
            "Insert patient\n" +
                "***as displayed values" 
        );
        insertBtn.setTooltip(tooltip6);
    }
}
