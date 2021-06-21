/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import engine.RailFenceCrypto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RFDecController implements Initializable {

    private RailFenceCrypto REF;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextField passText;

    @FXML
    private JFXTextArea encText;

    @FXML
    private JFXTextArea decText;

    @FXML
    void BackBtn(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
    }

    @FXML
    void Decrypt(ActionEvent event) {
        int counter = 0;
        char[] chars = passText.getText().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[i] == chars[j]) {
                    counter++;
                    break;
                }
            }
        }
        if (counter == 0) {
            REF = new RailFenceCrypto();
            String encStr = encText.getText();
            String passStr = passText.getText();
            int[] processedKey = REF.doProcessOnKey(passStr);

            Character[][] result = REF.decrypt(encStr, passStr, processedKey);
            String finalRes = "";
            for (Character[] x : result) {
                for (char y : x) {
                    finalRes = finalRes + y;
                }
            }
            if ("".equals(finalRes) || finalRes == null) {
                decText.setText("Wrong Pass key length");
            } else {
                decText.setText("The Decrypted message is the following: \n" + finalRes + "\n");
            }
        } else {
            decText.setText("There should be no duplicate characters in the pass Key");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        passText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                passText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        decText.setEditable(false);
    }

}
