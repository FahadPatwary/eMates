package com.example.emates;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class FoodMenu {


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    public void switchtoDashboard(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }
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
    private ComboBox<String> selectMeal;

    @FXML
    private DatePicker datePicker;
    @FXML
    public void initialize() {
            selectMeal.getItems().addAll("Lunch","Dinner");
        updateMealto();
        //updateMealtoo();
    }
    @FXML
    VBox mealBoard;
    private Label createMessageTextField(String message) {
        Label textField = new Label(message);

        return textField;
    }
public void updateMealto(){
        try{

            File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\mealData.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String data;
            mealBoard.getChildren().removeAll();
            while((data= br.readLine())!=null){

                mealBoard.getChildren().add(createMessageTextField(data));

            }
        }catch (Exception e){

        }
}
    public void updateMealtoo(){
        try{

            File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\mealData2.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String data= br.readLine();
            mealBoard.getChildren().removeAll();
           // while((data= br.readLine())!=null){

                mealBoard.getChildren().add(createMessageTextField(data));

           // }
        }catch (Exception e){

        }
    }

    @FXML
    private void StoreInfo(){
        String a = selectMeal.getValue();
        String b =String.valueOf( datePicker.getValue());
        try{
            File file2 = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
            FileReader fr = new FileReader(file2);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] sline = line.split("&");
            File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\mealData.txt");
            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sline[0]+" : "+a+" - "+b);
            bw.newLine();
            bw.close();
            File file3 = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\mealData2.txt");
            FileWriter fw2 = new FileWriter(file3);
            BufferedWriter bw2 = new BufferedWriter(fw2);
            bw2.write(sline[0]+" : "+a+" - "+b);

            bw2.close();
            //updateMealto();
            updateMealtoo();
        }catch (Exception e){

        }

    }
}
