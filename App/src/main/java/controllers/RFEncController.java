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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

/**
 * FXML Controller class
 *
 * @author User
 */
public class RFEncController implements Initializable {

    private RailFenceCrypto REF;

    @FXML
    private JFXTextField passText;

    @FXML
    private JFXTextField paddingTxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextArea plainTxt;

    @FXML
    private JFXTextArea encryptedTxt;

    @FXML
    void BackBtn(ActionEvent event) {
        backBtn.getScene().getWindow().hide();
    }

    @FXML
    void Encrypt(ActionEvent event) {
        int counter = 0;
        char[] chars = passText.getText().toUpperCase().toCharArray();
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
            String plainStr = plainTxt.getText();
            String passStr = passText.getText();
            char padStr = paddingTxt.getText().toUpperCase().charAt(0);
            int[] processedKey = REF.doProcessOnKey(passStr);

            String result = REF.result(REF.encrypt(plainStr, passStr, processedKey, padStr));

            encryptedTxt.setText("The Encrypted message is the following: \n" + result + "\n");
        } else {
            encryptedTxt.setText("There should be no duplicates in the pass key");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {

        passText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                passText.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });

        // allows only the input of one character to the padding field
        paddingTxt.setTextFormatter(new TextFormatter<>((Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null;
            } else {
                return change;
            }
        }));

        encryptedTxt.setEditable(false);
    }

}
