/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package irs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Oliver
 */
public class IRS extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //window Title
        primaryStage.setTitle("Intelligent Heart Recommendation System");
        
        //GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //two Text and two Field for input data of login
        Text scenetitle = new Text("Welcome");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0, 2, 1);
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        //two Button for login and new user
        Button btn = new Button("  Sign in  ");
        Button btn2 = new Button("New user");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(btn, btn2);
        grid.add(hbBtn, 1, 4);            
        
        //error message Text
        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 6);
        GridPane.setColumnSpan(actiontarget, 2);
        GridPane.setHalignment(actiontarget, HPos.RIGHT);
        actiontarget.setId("actiontarget");
            
        //Login Button Action Listener
        //switch scene to HeartRatePage
        btn.setOnAction((ActionEvent e) -> {
            try {
                Parent loginPage = FXMLLoader.load(getClass()
                        .getResource("HeartRatePage.fxml"));                
                Scene heartRateScene = new Scene(loginPage);                
                Stage loginStage = (Stage) ((Node) e.getSource())
                        .getScene().getWindow();
                loginStage.setScene(heartRateScene);
                loginStage.setX(0);
                loginStage.setY(0);
                loginStage.show();
            } catch (IOException ex) {
                Logger.getLogger(IRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //New User Button Action Listener
        //switch scene to UserRegisterPage
        btn2.setOnAction ((ActionEvent e) -> {
            try {
                Parent loginPage = FXMLLoader.load(getClass()
                        .getResource("UserRegisterPage.fxml"));                
                Scene userRegisterScene = new Scene(loginPage);                
                Stage loginStage = (Stage) ((Node) e.getSource())
                        .getScene().getWindow();
                loginStage.setScene(userRegisterScene);
                loginStage.setX(0);
                loginStage.setY(0);
                loginStage.show();
            } catch (IOException ex) {
                Logger.getLogger(IRS.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Scene definition
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(IRS.class.getResource("css/Login.css").toExternalForm());
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //create DB and tables and insert test data
        /*
        System.out.println("start creating DB");
        new Data().createTables();
        System.out.println("End of process creating DB");
        */
        launch(args);
    }
    
}
