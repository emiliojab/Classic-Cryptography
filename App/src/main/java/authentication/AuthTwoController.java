/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import DBConnection.DBHandler;
import User.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import engine.SendEmail;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author User
 */
public class AuthTwoController implements Initializable {

    private String secLevelTwo = "";

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private JFXButton nextBtn;

    private DBHandler handler;

    private Connection dbConnection;

    private PreparedStatement pst;

    protected Service<Void> bckThread;

    User user;
    String email = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        progressBar.setVisible(false);
        handler = new DBHandler();
        user = authentication.AuthOneController.user;
    }

    // Assigning IDs to each image to save selections to database
    @FXML
    public void steveImgClick(MouseEvent e) {
        secLevelTwo += "steve";
    }

    @FXML
    public void stratoImgClick(MouseEvent e) {
        secLevelTwo += "strato";
    }

    @FXML
    public void tayImgClick(MouseEvent e) {
        secLevelTwo += "tay";
    }

    @FXML
    public void teleImgClick(MouseEvent e) {
        secLevelTwo += "tele";
    }

    @FXML
    public void feAcouImgClick(MouseEvent e) {
        secLevelTwo += "feAcou";
    }

    @FXML
    public void gibImgClick(MouseEvent e) {
        secLevelTwo += "gib";
    }

    @FXML
    public void ibAcouImgClick(MouseEvent e) {
        secLevelTwo += "ibAcou";
    }

    @FXML
    public void martinImgClick(MouseEvent e) {
        secLevelTwo += "martin";
    }

    @FXML
    public void sonicImgClick(MouseEvent e) {
        secLevelTwo += "sonic";
    }

    @FXML
    public void nxtBtnClick(ActionEvent e) throws IOException {

        progressBar.setVisible(true);
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.seconds(2));
        pt.setOnFinished(ex -> {
            System.out.println("Sign Up successful.");

        });
        pt.play();

        bckThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        String q = "select * from `login`.`users` where `name`=? and `password`=? and `secleveltwo` = ?";
                        dbConnection = handler.getConnection();

                        try {
                            pst = dbConnection.prepareStatement(q);
                            pst.setString(1, user.getName());
                            pst.setString(2, user.getPassword());
                            pst.setString(3, secLevelTwo);
                            ResultSet result = pst.executeQuery();

                            int count = 0;
                            while (result.next()) {
                                count++;
                                email = result.getString("emailaddress");
                            }

                            if (count == 1) {
                                SendEmail sendemail = new SendEmail(email);
                                Platform.runLater(() -> {
                                    try {

                                        System.out.println("Sign up successful");
                                        nextBtn.getScene().getWindow().hide();
                                        Parent root = FXMLLoader.load(getClass().getResource("/authFXML/authThree.fxml"));
                                        Scene scene = new Scene(root);
                                        Stage signupStage = new Stage();
                                        signupStage.setScene(scene);
                                        signupStage.show();
                                        signupStage.setResizable(false);
                                    } catch (Exception ex) {
                                        Logger.getLogger(SignUpTwoController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                });

                            } else {
                                Platform.runLater(() -> {

                                    try {
                                        nextBtn.getScene().getWindow().hide();
                                        Parent root = FXMLLoader.load(getClass().getResource("/authFXML/authOne.fxml"));
                                        Scene scene = new Scene(root);
                                        Stage signupStage = new Stage();
                                        signupStage.setScene(scene);
                                        signupStage.show();
                                        signupStage.setResizable(false);
                                    } catch (IOException ex) {
                                        Logger.getLogger(AuthTwoController.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                });
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

        bckThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e) {
                System.out.println("Sign up successful");

            }
        });

        bckThread.restart();

    }

}
