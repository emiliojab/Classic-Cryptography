/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import engine.CaeserCrypto;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author User
 */
public class BruteForceController implements Initializable {

    private CaeserCrypto CP;

    @FXML
    private JFXButton backBtn;

    @FXML
    private JFXTextArea plainTxt;

    @FXML
    private JFXListView<String> listView;

    ObservableList<String> list = FXCollections.observableArrayList();

    @FXML
    public void BackBtn(ActionEvent event) throws IOException {
        backBtn.getScene().getWindow().hide();
    }

    @FXML
    public void BruteForce(ActionEvent event) {
        list.clear();

        CP = new CaeserCrypto();
        String text = plainTxt.getText();
        String id;
        String bfTxt = "";
        // print out the result using the Caeser Decryption 26 times
        for (int i = 1; i <= 26; i++) {
            bfTxt = CP.Decrypt(text, i).toString();
            id = ("Key " + i + ": ");
            list.add(id + bfTxt);
        }
        listView.setItems(list);
    }

    @FXML
    public void ExportBtn(ActionEvent e) throws IOException {

        //pops a textdialog to ask the user to add a name to the file
        TextInputDialog td = new TextInputDialog("Enter File Name");
        td.setTitle("Save File");
        td.setHeaderText("File Name:");
        final Button ok = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
        ok.addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    String filename = td.getEditor().getText();
                    File file = new File("C:\\Users\\User\\Desktop\\" + filename + ".txt");
                    // if the filename already exists, ask the user to enter another name
                    while (!file.createNewFile()) {
                        
                        td.setTitle("Save File");
                        td.setHeaderText("File name Already exists!");
                        filename = td.getEditor().getText();
                        file = new File("C:\\Users\\User\\Desktop\\" + filename + ".txt");
                        td.showAndWait();
                    }
                    // write the output to the file
                    FileWriter fw = new FileWriter(file);
                    for (int i = 0; i < list.size(); i++) {
                        fw.write(list.get(i) + "\n");
                    }
                    fw.close();
                } catch (IOException ex) {
                    System.out.println(Arrays.toString(ex.getStackTrace()));
                }
            }
        });

        final Button cancel = (Button) td.getDialogPane().lookupButton(ButtonType.CANCEL);
        cancel.addEventFilter(ActionEvent.ACTION, event
                -> {
            td.close();
        });

        td.showAndWait();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setEditable(false);
    }

}
