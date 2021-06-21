/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

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
public class RailFenceController implements Initializable {

    @FXML
    private JFXButton rfBackBtn;
    
    @FXML
    public void encryptAction(ActionEvent event) throws IOException {
        Stage encStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/RFEnc.fxml"));
        Scene scene = new Scene(root);
        encStage.setScene(scene);
        encStage.show();
        encStage.setResizable(false);
    }

    @FXML
    public void dectryptAction(ActionEvent event) throws IOException {
        Stage decStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/RFDec.fxml"));
        Scene scene = new Scene(root);
        decStage.setScene(scene);
        decStage.show();
        decStage.setResizable(false);
    }

    
    @FXML
    public void BackBtn(ActionEvent event) throws IOException{
        rfBackBtn.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
