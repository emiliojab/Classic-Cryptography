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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author User
 */
public class DecryptController implements Initializable {

    CaeserCrypto CP;

    @FXML
    private JFXTextField keyTxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextArea cipherTxt;

    @FXML
    private JFXTextArea decryptedTxt;

    
    
    public void Decrypt(ActionEvent event) {
        CP = new CaeserCrypto();
        String text = cipherTxt.getText();
        int key = Integer.parseInt((keyTxt.getText()));

        decryptedTxt.setText(CP.Decrypt(text, key).toString());
    }

    @FXML
    public void BackBtn(ActionEvent event) throws IOException {
        backBtn.getScene().getWindow().hide();
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // forces the field to accept only numerical inputs
        keyTxt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                keyTxt.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        decryptedTxt.setEditable(false);
    }

}
