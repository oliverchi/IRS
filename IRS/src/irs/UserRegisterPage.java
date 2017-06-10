/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.io.IOException;
import static java.lang.System.exit;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Oliver
 */
public class UserRegisterPage extends User implements Initializable {
    //initialise one user object for this page
    User user = new User();
    
    @FXML
    private Button registerBtn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField keyField;
    @FXML
    private TextField idField;
    @FXML
    private TextField positionField;
    @FXML
    private Label errorMsg;
    @FXML
    private Button exitBtn;

    /**
     * initialises the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
        //get user object from main page
        user = IRS.getInstance().getUser();          
        user = user.getUser(user);
    }    

    @FXML
    private void registerOnAction(ActionEvent event) {
        String name = nameField.getText();
        String password = keyField.getText();
        String id = idField.getText();
        String pos = positionField.getText();
        
        //check all inputs if valid
        String msg = checkIfValid(name, password, id, pos);
        
        //if all input valid, register this user otherwise display error message
        if (msg.equals("")) {
            if ( insertNewUser(name, password, id, pos) ){
                //INSERT USER SUCCESSFULLY
                //CHANGE exit Button name
                errorMsg.setText("RIGESTER NEW USER SUCCESSFULLY! *** Log in system by clicking LOGIN button ***");
                exitBtn.setText("  LOGIN  ");
            }else{
                //INSERT USER UNSUCCESSFULLY
                errorMsg.setText("RIGESTER NEW USER UNSUCCESSFULLY, PLEASE TRY IT LATER");
            }
        }else{
            errorMsg.setText(msg);//deliver error message
        }
    }

    @FXML
    private void exitBtnOnAction(ActionEvent event) {
        String btnName = exitBtn.getText();
        if (btnName.equals("   EXIT   ")) exit(0);
        if (btnName.equals("  LOGIN  ")) {
            try {
                    //put values into user object to pass to next page
                    user.setName(nameField.getText());
                    user.setKey(keyField.getText());
                    user.setID(idField.getText());
                    user.setPosition(positionField.getText());
                    
                    //switch scene to HeartRatePage
                    Parent loginPage = FXMLLoader.load(getClass()
                            .getResource("HeartRatePage.fxml"));                
                    Scene heartRateScene = new Scene(loginPage);                
                    Stage loginStage = (Stage) ((Node) event.getSource())
                            .getScene().getWindow();
                    loginStage.setScene(heartRateScene);
                    loginStage.setX(0);//set scene position as (0,0)
                    loginStage.setY(0);
                    loginStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(UserRegisterPage.class.getName()).log(Level.SEVERE, null, ex);
                }
        } 
    }
     
}
