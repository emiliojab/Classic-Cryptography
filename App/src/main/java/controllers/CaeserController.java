package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaeserController implements Initializable {

    @FXML
    private JFXButton brtfrceBtn;
    
    @FXML
    private JFXButton csrBackBtn;

    @FXML
    private void encryptAction(ActionEvent event) throws IOException {
//        encBtn.getScene().getWindow().hide();
        Stage encStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Enc.fxml"));
        Scene scene = new Scene(root);
        encStage.setScene(scene);
        encStage.show();
        encStage.setResizable(false);
    }

    
    @FXML
    public void dectryptAction(ActionEvent event) throws IOException {
        Stage decStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dec.fxml"));
        Scene scene = new Scene(root);
        decStage.setScene(scene);
        decStage.show();
        decStage.setResizable(false);
    }

    @FXML
    public void bruteForceAction(ActionEvent event) throws IOException {
        Stage brtStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/BruteForce.fxml"));
        Scene scene = new Scene(root);
        brtStage.setScene(scene);
        brtStage.show();
        brtStage.setResizable(false);
    }
    
    @FXML
    public void csrBackBtn(ActionEvent event) throws IOException{
        csrBackBtn.getScene().getWindow().hide();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
