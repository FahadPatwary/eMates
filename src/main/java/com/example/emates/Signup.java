package com.example.emates;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class Signup {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void switchtoLogin(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    TextField mobile;
    @FXML
    PasswordField passwordData;
    @FXML
    TextField name;
    @FXML
    TextField id;
    @FXML
    Label NameWarn;
    private String generateUniqueId() {
        return UUID.randomUUID().toString();
    }

    @FXML
    public void storeUser(){
        String nameD = name.getText();
        String idD = id.getText();
        String mobileNum=mobile.getText();
        String passwordDataD=passwordData.getText();
        String uniqueId = generateUniqueId();

        Thread registrationThread = new Thread(() -> {
            try {
                // Simulate a time-consuming task
                Thread.sleep(1000);

                // Write data to the file
                try {
                    File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\userData.txt");
                    FileWriter fw = new FileWriter(file,true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(nameD + "&" + idD + "&" + mobileNum + "&" + passwordDataD + "&" + uniqueId);
                    bw.newLine();
                    bw.close();

                    System.out.println("Data entry successful");
                } catch (Exception e) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        registrationThread.start();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showInfo(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
