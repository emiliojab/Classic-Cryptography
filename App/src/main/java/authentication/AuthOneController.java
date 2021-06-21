/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import DBConnection.DBHandler;
import User.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AuthOneController implements Initializable {

    @FXML
    private JFXTextField usrTxt;

    @FXML
    private JFXPasswordField passTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private Label wrngPassLbl;

    @FXML
    private JFXButton signUpBtn;

    private DBHandler handler;

    private Connection dbConnection;

    private PreparedStatement pst;

    private Service<Void> bckgroundThread;

    static User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setStyle("-fx-background-color: green");
        progressBar.setVisible(false);
        wrngPassLbl.setVisible(false);
        handler = new DBHandler();
    }

    @FXML
    public void loginAction(ActionEvent e) {
        wrngPassLbl.setVisible(false);
        progressBar.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(1.5));
        pt.setOnFinished(ex -> {
            System.out.println("Login successful.");

        });
        pt.play();

        bckgroundThread = new Service<Void>() {

            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws IOException {
                        System.out.println("-1");

                        try {
                            String q1 = "select * from `login`.`users` where `name`=? and `password`=?";
                            dbConnection = handler.getConnection();

                            pst = dbConnection.prepareStatement(q1);
                            pst.setString(1, usrTxt.getText());
                            pst.setString(2, passTxt.getText());
                            ResultSet result = pst.executeQuery();

                            int count = 0;
                            while (result.next()) {
                                count++;
                            }

                            if (count == 1) {

                                user = new User(usrTxt.getText(), passTxt.getText());
                                
                                Platform.runLater(() -> {
                                    Stage decStage = new Stage();
                                    Parent root;
                                    try {
                                        loginBtn.getScene().getWindow().hide();
                                        root = FXMLLoader.load(getClass().getResource("/authFXML/authTwo.fxml"));
                                        Scene scene = new Scene(root);
                                        decStage.setScene(scene);
                                        decStage.show();
                                        decStage.setResizable(false);
                                    } catch (IOException ex) {
                                        Logger.getLogger(AuthOneController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });

                            } else if (count == 0) {
                                System.out.println("3");
                                wrngPassLbl.setVisible(true);
                                usrTxt.setText("");
                                passTxt.setText("");
                            }

                            pst.close();
                            dbConnection.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                        return null;
                    }
                };
            }

        };

        bckgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("back buffer Done");
                progressBar.setVisible(false);

            }
        });

        bckgroundThread.restart();
    }

    @FXML
    public void signupAction(ActionEvent e) throws IOException {

        signUpBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/authFXML/SignUp.fxml"));
        Scene scene = new Scene(root, 330, 300);
        Stage loginStage = new Stage();
        loginStage.setScene(scene);
        loginStage.show();
        loginStage.setResizable(false);

    }
}
