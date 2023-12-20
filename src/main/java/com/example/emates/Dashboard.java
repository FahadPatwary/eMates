package com.example.emates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Dashboard {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void switchtoPayment(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Payment2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    public void switchtoExpenses(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("InputCost2.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    public void switchtofoodmenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FoodMenu.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    public void switchtoChat(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Chat.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    public void switchtoBulletBoard(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Bulletboard.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    public void switchtoLogin(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
    @FXML
    Label NameLabelFx;
    @FXML
    Label MobileLabelFx;
    @FXML
    Label IDLabelFx;
    @FXML
    Label TimeFx;

    private String dataRet(String path){
        try {
            File file = new File (path);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            return line;
        }catch (Exception e){

        }
        return "Nothing Found";
    }
    @FXML
    Label totalAmount;
    @FXML
    Label paymentLabel;
    @FXML
    Label AvailBlnc;
    @FXML
    public void initialize() {
        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
            String formattedDate = myDateObj.format(myFormatObj);
            TimeFx.setText(formattedDate);

            File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] sline = line.split("&");

            br.close();

            String name = sline[0];
            NameLabelFx.setText(name);
            String a = dataRet("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\totalAmount.txt");
            String b =dataRet("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\totalExpenses.txt");
            totalAmount.setText(a);
            paymentLabel.setText(b);
            double c =Double.parseDouble(a)-Double.parseDouble(b);
            AvailBlnc.setText(String.valueOf(c));
//            MobileLabelFx.setText(sline[1]);
//            IDLabelFx.setText(sline[2]);
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception in a proper way
        }
    }

    }

