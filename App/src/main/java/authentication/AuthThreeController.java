/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class AuthThreeController implements Initializable {

    @FXML
    private JFXTextField codeTxt;

    String random;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        random = engine.SendEmail.getRand();
    }

    @FXML
    public void ckeckBtnPress(ActionEvent e) {
        if (codeTxt.getText().equals(random)) {
            try {
                codeTxt.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainLobby.fxml"));
                Scene scene = new Scene(root);
                Stage signupStage = new Stage();
                signupStage.setScene(scene);
                signupStage.show();
                signupStage.setResizable(false);
            } catch (Exception ex) {
                Logger.getLogger(SignUpTwoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                codeTxt.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/authFXML/authOne.fxml"));
                Scene scene = new Scene(root);
                Stage signupStage = new Stage();
                signupStage.setScene(scene);
                signupStage.show();
                signupStage.setResizable(false);
            } catch (Exception ex) {
                Logger.getLogger(SignUpTwoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
