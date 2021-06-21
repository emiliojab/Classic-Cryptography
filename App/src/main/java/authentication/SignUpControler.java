/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authentication;

import DBConnection.DBHandler;
import User.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 *
 * @author User
 */
public class SignUpControler implements Initializable {

    @FXML
    private JFXTextField usrTxt;

    @FXML
    private JFXPasswordField passTxt;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXProgressBar progressBar;

    @FXML
    private JFXButton signUpBtn;

    @FXML
    private Label wrngPassLbl1;

    @FXML
    private Label wrngPassLbl2;

    @FXML
    private Label wrngUserName;

    @FXML
    private JFXTextField emailTxt;

    @FXML
    private Label emailLabel;

    private DBHandler handler;

    private Connection dbConnection;

    private PreparedStatement pst;

    protected Service<Void> bckThread;

    static User user;
    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressBar.setVisible(false);
        wrngPassLbl1.setVisible(false);
        wrngPassLbl2.setVisible(false);
        wrngUserName.setVisible(false);
        emailLabel.setVisible(false);
        handler = new DBHandler();

    }

    @FXML
    public void backAction(ActionEvent e) throws IOException {

        backBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/authFXML/authOne.fxml"));
        Scene scene = new Scene(root);
        Stage signupStage = new Stage();
        signupStage.setScene(scene);
        signupStage.show();
        signupStage.setResizable(false);
    }

    @FXML
    public void signupAction(ActionEvent e) {
        wrngPassLbl1.setVisible(false);
        wrngPassLbl2.setVisible(false);
        wrngUserName.setVisible(false);
        emailLabel.setVisible(false);
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
                        Matcher match = EMAIL_ADDRESS_PATTERN.matcher(emailTxt.getText());
                        if (match.find()) {

                            String q = "select * from `login`.`users` where `name`=?";
                            dbConnection = handler.getConnection();

                            try {
                                pst = dbConnection.prepareStatement(q);
                                pst.setString(1, usrTxt.getText());
                                ResultSet result = pst.executeQuery();

                                int count = 0;
                                while (result.next()) {
                                    count++;
                                }
                                Pattern letter = Pattern.compile("[a-zA-z]");
                                Pattern digit = Pattern.compile("[0-9]");
                                Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

                                String password = passTxt.getText();

                                Matcher hasLetter = letter.matcher(password);
                                Matcher hasDigit = digit.matcher(password);
                                Matcher hasSpecial = special.matcher(password);

                                if (count == 1) {
                                    progressBar.setVisible(false);
                                    wrngUserName.setVisible(true);

                                } else if (password.length() <= 8 || !hasDigit.find() || !hasLetter.find() || !hasSpecial.find()) {
                                    wrngPassLbl1.setVisible(true);
                                    wrngPassLbl2.setVisible(true);
                                } else if (count == 0) {

                                    user = new User(usrTxt.getText(), passTxt.getText());
                                    String q1 = "INSERT INTO `login`.users(`name`,`password`, `emailaddress`)"
                                            + "VALUES(?,?,?)";
                                    dbConnection = handler.getConnection();

                                    pst = dbConnection.prepareStatement(q1);
                                    pst.setString(1, usrTxt.getText());
                                    pst.setString(2, passTxt.getText());
                                    pst.setString(3, emailTxt.getText());
                                    pst.executeUpdate();
                                    pst.close();
                                    dbConnection.close();
                                    usrTxt.setText("");
                                    passTxt.setText("");
                                    Platform.runLater(() -> {
                                        try {
                                            System.out.println("Sign up successful");
                                            signUpBtn.getScene().getWindow().hide();
                                            Parent root = FXMLLoader.load(getClass().getResource("/authFXML/SignUpTwo.fxml"));
                                            Scene scene = new Scene(root, 844, 721);
                                            Stage signupStage = new Stage();
                                            signupStage.setScene(scene);
                                            signupStage.show();
                                            signupStage.setResizable(false);
                                        } catch (Exception ex) {
                                            Logger.getLogger(SignUpTwoController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    });
                                }

                                pst.close();
                                dbConnection.close();
                            } catch (SQLException ex) {
                                ex.printStackTrace();

                            }

                            return null;
                        } else {
                            emailLabel.setVisible(true);
                            return null;

                        }
                    }
                };
            }
        };

        bckThread.setOnSucceeded(
                new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent e
            ) {
                System.out.println("Sign up successful");
                progressBar.setVisible(false);

            }
        }
        );

        bckThread.restart();

    }

}
