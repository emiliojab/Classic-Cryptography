package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MainLobbyController implements Initializable {

    
    public void caeserCiBtn(ActionEvent e) throws IOException{
        Stage encStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Caeser.fxml"));
        Scene scene = new Scene(root);
        encStage.setScene(scene);
        encStage.show();
        encStage.setResizable(false);
    }
    
    public void railFenceCBtn(ActionEvent e) throws IOException{
        Stage encStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/RailFence.fxml"));
        Scene scene = new Scene(root);
        encStage.setScene(scene);
        encStage.show();
        encStage.setResizable(false);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
