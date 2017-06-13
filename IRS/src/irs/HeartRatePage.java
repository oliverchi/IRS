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
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Oliver
 */



public class HeartRatePage extends Patient implements Initializable {
    User user = new User();
    Patient patient = new Patient();
    Result result = new Result(null,null,0);//default set to avaid NULL POINTER ERROR
    Parameters para = new Parameters("00000");//default parameters store in a row PRIMARY KEY as '00000'
    
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
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker lastDatePicker;
    @FXML
    private Button displayBtn;
    @FXML
    private Button changeRecordsBtn;
    @FXML
    private Button recommendBtn;
    @FXML
    private Button insertRecordBtn;
    @FXML
    private TextField minField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField pField;
    @FXML
    private TextField kField;
    @FXML
    private Button changeParametersBtn;
    @FXML
    private Label recordMsg;
    @FXML
    private Label dateErrorMsg;
    @FXML
    private TableView<Result> tableView;
    @FXML
    private Label recommMsg;
    @FXML
    private TextField recordIDField;
    @FXML
    private TextField recordDateField;
    @FXML
    private TextField recordRateField;

    public HeartRatePage() {
    }
    
    
    
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
        
        setTableView();//initialise table
    }   
    
    @FXML
    private void searchBtnOnAction(ActionEvent event) { 
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
        if ( patientIDCheck(pidField.getText())) {//true: ID existed so can't insert
            patientMsg.setText("Incorrect patient ID, please try again!");//check if the same patient ID exist
            return false;
        }
        return true;
    }
    
    @FXML
    private void insertBtnOnAction(ActionEvent event) {  
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
    private void deleteBtnOnAction(ActionEvent event) {  
        patientMsg.setText("");//reset message
        String pid = pidField.getText();
        if ( patientIDCheck(pid)) {//true: ID existed so can delete 
            if(alertMsg("Are you OK to delete informaiton of this patient")){//pop up alert window
                if (deletePatient(pid) ) {//delete that patient's information from database
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
        
        Optional<ButtonType> btn = alert.showAndWait();
        if (btn.isPresent() && btn.get() == ButtonType.OK) {
            return true;
        } 
        if (btn.isPresent() && btn.get() == ButtonType.CANCEL) {
            return false;
        } 
        return false;
    }
    
    //EMPTY PREVIOUS INPUT
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
    
    //DISPLAY ONE PATIENT'S ALL INFORMATION
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
        setHeartRateParameters(p.getID()); 
        recommMsg.setText("");
        dateErrorMsg.setText("");
        //recordMsg.setText("");        
    }
    
    //SETUP ALL TOOLTIPS HERE
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
                "*as displayed values*" 
        );
        insertBtn.setTooltip(tooltip6);
        
        //set tooltip for DISPLAY Button
        final Tooltip tooltip7 = new Tooltip();
        tooltip7.setText(
            "display patient\n" 
        );
        displayBtn.setTooltip(tooltip7);
        
        //set tooltip for ChangeRecord Button
        final Tooltip tooltip8 = new Tooltip();
        tooltip8.setText(
            "change record\n" +
                "*as input value*"
        );
        changeRecordsBtn.setTooltip(tooltip8);
    
        //set tooltip for reommend Button
        final Tooltip tooltip9 = new Tooltip();
        tooltip9.setText(
            "recommend\n" +
                "*if test today*"
        );
        recommendBtn.setTooltip(tooltip9);
        
        //set tooltip for insert record Button
        final Tooltip tooltip10 = new Tooltip();
        tooltip10.setText(
            "insert record\n" +
                "*as input values*"
        );
        insertRecordBtn.setTooltip(tooltip10);
    
        //set tooltip for insert record Button
        final Tooltip tooltip11 = new Tooltip();
        tooltip11.setText(
            "change parameters\n" +
                "*as input values*"
        );
        changeParametersBtn.setTooltip(tooltip11);
    
    }

    @FXML
    private void keyInputOnTextField(KeyEvent event) {
        patientMsg.setText("");//reset message
    }
    
    @FXML
    private void displayBtnOnAction(ActionEvent event) {
        
        switch(checkDatesValid()){
            case "NULL":
                dateErrorMsg.setText("select the days before clicking button");
                break;
            case "ONEDAY":
                if (patient.getID() == null ){//check patient ID not null
                    dateErrorMsg.setText("Error: haven't select one patient");
                    break;
                }                    
                if (patientIDCheck(patient.getID())){//if patient exist, do display action
                    result = result.getResult(patient.getID(),
                            startDatePicker.getValue().toString());
                    if (result == null) {
                        dateErrorMsg.setText("No data in wrong date, please recheck!");
                    }else{                        
                        ObservableList<Result> r = 
                                FXCollections.observableArrayList(
                                        new Result(result.getPatientID(),
                                                result.getDate() ,
                                                result.getHeartRate()));//add data for table                        
                        tableView.setItems(r); //display table                       
                    }
                } else {
                    dateErrorMsg.setText("Error: haven't select one patient");
                }                    
                break;
            case "NORMAL":
                if (patient.getID() == null ){//check patient ID not null
                    dateErrorMsg.setText("Error: haven't select one patient");
                    break;
                }
                if (patientIDCheck(patient.getID())){//if patient exist, do display action                    
                    ObservableList<Result> rList = result.getResults(//call getResults(string,LocalDate,LocalDate)
                            patient.getID(),startDatePicker.getValue(), 
                            lastDatePicker.getValue());//get days's heart rate from database
                    if (rList == null) {
                        dateErrorMsg.setText("No data in wrong dates, please recheck!");
                    }else{
                        tableView.setItems(rList);//display table  
                    }
                } else {
                    dateErrorMsg.setText("Error: haven't select one patient");
                }
                break;
            case "WRONG":
                dateErrorMsg.setText("Error: start day is after last day");
                break;            
            default:
                dateErrorMsg.setText("System Error: please try it later");
                break;
        }
    }

    @FXML
    private void changeRecordsBtnOnAction(ActionEvent event) {
        if(recordIDField.getText().isEmpty() 
                || recordDateField.getText().isEmpty() 
                || recordRateField.getText().isEmpty()) {
            recordMsg.setText("Attention!: check all inputs before change a record");
        }else{
            if(result.changeResult(new Result(recordIDField.getText(), 
                    recordDateField.getText(),
                    Integer.valueOf(recordRateField.getText()) ))){
                ObservableList<Result> r = FXCollections.observableArrayList(
                        new Result(recordIDField.getText(),
                                recordDateField.getText(),
                                Integer.valueOf(recordRateField.getText()) ));
                tableView.setItems(r);//display table 
                recordMsg.setText("CHANGE RECORD SUCCESSFULLY. "
                        + "SHOW CHANGED RECORD IN TABLE.");
            }else{
                recordMsg.setText("DATA ERROR: PLEASE RECHECK YOUR INPUT IF VALID");
            }
        } 
    }

    @FXML
    private void recommendBtnOnAction(ActionEvent event) {
        if( recommendTest() && recommMsg.getText().equals("") )
            recommMsg.setText("SUGGESTION:\nNO NEED TEST TODAY!");
    }

    @FXML
    private void insertRecordBtnOnAction(ActionEvent event) {
        if(recordIDField.getText().isEmpty() 
                || recordDateField.getText().isEmpty() 
                || recordRateField.getText().isEmpty()) {
            recordMsg.setText("Attention!: check all inputs before insert a record");
        }else{
            if(result.setResult(new Result(recordIDField.getText(), 
                    recordDateField.getText(),
                    Integer.valueOf(recordRateField.getText()) ))){
                ObservableList<Result> r = FXCollections.observableArrayList(
                        new Result(recordIDField.getText(),
                                recordDateField.getText(),
                                Integer.valueOf(recordRateField.getText()) ));
                tableView.setItems(r);//display table 
                recordMsg.setText("INSERT RECORD SUCCESSFULLY. "
                        + "SHOW NEW RECORD IN TABLE.");
            }else{
                recordMsg.setText("DATA ERROR: PLEASE RECHECK YOUR INPUT IF VALID");
            }
        }        
    }

    @FXML
    private void changeParametersBtnOnAction(ActionEvent event) {
        if(minField.getText().isEmpty() || pField.getText().isEmpty()
                || maxField.getText().isEmpty() || kField.getText().isEmpty()) {
            recordMsg.setText("Attention!: check all inputs before change parameters");
        }else{
            if(para.setParameters(new Parameters(patient.getID(),
                    Integer.valueOf(minField.getText()),                     
                    Integer.valueOf(maxField.getText()),
                    Integer.valueOf(kField.getText()),
                    Integer.valueOf(pField.getText())))){
                recordMsg.setText("INSERT THIS PATIENT'S PARAMETERS SUCCESSFULLY!");
                setHeartRateParameters(patient.getID());
            }else if (para.changeParameters(new Parameters(patient.getID(),
                    Integer.valueOf(minField.getText()),                     
                    Integer.valueOf(maxField.getText()),
                    Integer.valueOf(kField.getText()),
                    Integer.valueOf(pField.getText())))){
                recordMsg.setText("CHANGE THIS PATIENT'S PARAMETERS SUCCESSFULLY!");
                setHeartRateParameters(patient.getID());
            }else {
                    recordMsg.setText("DATA ERROR: PLEASE RECHECK PRARMETER INPUTS IF VALID");
            }
            
        } 
    }
    
    //CHECK IF DATE IS VALID
    private String checkDatesValid(){
        if(startDatePicker.getValue() == null || lastDatePicker.getValue() == null)
            return "NULL";//lack of input
        LocalDate startDate = startDatePicker.getValue();
        LocalDate lastDate = lastDatePicker.getValue();
        if(startDate.isBefore(lastDate)) return "NORMAL";//correct input
        if(startDate.isEqual(lastDate)) return "ONEDAY";//start date is last date
        return "WRONG";//start date is after last date
    }
    
    //DISPLAY TABLE
    private void setTableView(){
        TableColumn idCol = new TableColumn("Patient ID");
        TableColumn dateCol = new TableColumn("Test Date");
        TableColumn rateCol = new TableColumn("Heart Rate Reading");
        rateCol.setMinWidth(140);
        tableView.getColumns().addAll(idCol, dateCol, rateCol);
        idCol.setCellValueFactory(
            new PropertyValueFactory<>("patientID")
        );
        dateCol.setCellValueFactory(
            new PropertyValueFactory<>("date")
        );
        rateCol.setCellValueFactory(
            new PropertyValueFactory<>("heartRate")
        );
    }
    
    //GET PARAMETERS FOR PATIENT FROM DATABASE
    @SuppressWarnings("empty-statement")
    private void setHeartRateParameters(String pid){
        para = para.getParameters(pid);
        minField.setText(String.valueOf(para.getMIN() ) );
        maxField.setText(String.valueOf(para.getMAX() ) );
        pField.setText(String.valueOf(para.getP() ) );;
        kField.setText(String.valueOf(para.getK() ) );;
    }
    
    //CHECK IF NEED TEST TODAY
    //SET RECOMMEND MESSAGE
    private boolean recommendTest(){
        String pid = patient.getID();
        recommMsg.setText("");
        if (pid == null ){//check patient ID not null
            recommMsg.setText("ERROR:\nNO PATIENT SELECTED");
            return false;
        }        
        LocalDate today = LocalDate.now();
        //para = para.getParameters(pid);//get parameters from database(NO NEED) 
        long k = (long)(para.getK());
        if (patientIDCheck(pid)){//if patient exist, do next action                    
            ObservableList<Result> rList = result.getResults(//call getResults(string,LocalDate,LocalDate)
                pid,today.minusDays(k), today);//get days's heart rate from database
            if (rList == null) {
                recommMsg.setText("NO RESULT EXISTED.\nNEED TEST TODAY!");
            }else{
                tableView.setItems(rList);//display results in table                               
                if (rList.size() < (k*para.getP()/100)){
                    recommMsg.setText("NOT ENOUGH\nRESULTS EXISTED.\nNEED TEST TODAY!");
                } else {
                    rList.forEach(r->{
                        if (r.getHeartRate() < para.getMIN() 
                                || r.getHeartRate() > para.getMAX() )
                            recommMsg.setText("UNSTEADY RESULT EXISTED.\nNEED TEST TODAY!"); 
                        //CAN'T RETURN FALSE SO NEED recommMsg value CHECK TO DETERMINE IF NEED TEST                       
                    });                    
                }
            }
        } else {
            recommMsg.setText("ERROR:\nSELECTED NOT EXISTED.");
        }
        return true;    
    }
}
