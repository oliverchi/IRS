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

/**
 * FXML Controller class
 *
 * @author Oliver
 */



public class HeartRatePage implements Initializable {
    User user = new User();
    
    @FXML
    private Button btn;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user = IRS.getInstance().getUser();
    }    
    
    @FXML
    void btnOnAction(ActionEvent event) {  
        System.out.println(user.getName());System.out.println(user.getKey());
    }
    
}
