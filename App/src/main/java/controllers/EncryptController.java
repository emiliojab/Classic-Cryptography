/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import engine.CaeserCrypto;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author User
 */
public class EncryptController implements Initializable {

    private CaeserCrypto CP;

    @FXML
    private JFXTextField keyTxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextArea plainTxt;

    @FXML
    private JFXTextArea encryptedTxt;

    public void encrBtn(ActionEvent event) {
        CP = new CaeserCrypto();
        String text = plainTxt.getText();
        int key = Integer.parseInt((keyTxt.getText()));
        encryptedTxt.setText(CP.Encrypt(text, key).toString());
    }

    @FXML
    public void BackBtn(ActionEvent event) throws IOException{
        backBtn.getScene().getWindow().hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // force the field to be numeric only
        keyTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    keyTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        encryptedTxt.setEditable(false);
    }
    
    
}
