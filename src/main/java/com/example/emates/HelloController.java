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

import java.io.*;

public class HelloController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void switchtoSignup(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML void checkouttoDash(){

    }

    @FXML
    TextField mobile;
    @FXML
    PasswordField passwordd;
    @FXML
    public void userLogin(ActionEvent e) throws Exception{

        String mobileNum = mobile.getText();
        String passwordData = passwordd.getText();
        File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\userData.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String data;
        while ((data = br.readLine()) != null){

            String[] sdata = data.split("&");
            if (sdata.length >= 4 && sdata[2].equals(mobileNum) && sdata[3].equals(passwordData)) {
                File file2 = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
                FileWriter fw = new FileWriter(file2);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(sdata[0] + "&" + " " + "&" + " ");
                bw.close();

                root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }
        if (mobileNum.equals("Admin")&& passwordData.equals("Admin123")) {
            File file2 = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
            FileWriter fw = new FileWriter(file2);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Admin" + "&" + " " + "&" + " ");
            bw.close();
            root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }



        br.close();
    }

}