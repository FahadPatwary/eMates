package com.example.emates;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BulletBoardController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void switchtoDashBoard(ActionEvent e) throws IOException {
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
    public void switchtoLogin(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }


    @FXML
    private TextField noticename_fl;

//    @FXML
//    private TextField user_field;

    @FXML
    private TextArea noticedes_field;

    @FXML
    private VBox noticespace;

    @FXML
    public void initialize() {
        // Load existing notices from file on application startup
        loadNoticesFromFile();
    }

    @FXML
    public void addButtonClicked(ActionEvent event) throws Exception {
        String noticeName = noticename_fl.getText();
        String noticeDescription = noticedes_field.getText();
        File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        String[] sline = line.split("&");
        System.out.println(sline[0]);
        br.close();

        String username =sline[0];

        // Save user input data to file
        saveNoticeToFile(noticeName, noticeDescription, username);

        // Add a new TitledPane
        TitledPane newNotice = createTitledPane(noticeName, noticeDescription, username);
        noticespace.getChildren().add(newNotice);

        // Clear the input fields
        noticename_fl.clear();
        noticedes_field.clear();
        //user_field.clear();
    }

    private void loadNoticesFromFile() {
        List<String> existingNotices = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\notice.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingNotices.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Update UI on the JavaFX Application Thread
        Platform.runLater(() -> {
            for (String notice : existingNotices) {
                String[] parts = notice.split(" & ");
                if (parts.length == 3) {
                    String noticeName = parts[0];
                    String noticeDescription = parts[1];
                    String username = parts[2];

                    TitledPane newNotice = createTitledPane(noticeName, noticeDescription, username);
                    noticespace.getChildren().add(newNotice);
                }
            }
        });
    }

    private void saveNoticeToFile(String noticeName, String noticeDescription, String username) {
        try (FileWriter writer = new FileWriter("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\notice.txt", true)) {
            // Append the user input data to notice.txt with "&" separated values
            writer.write(noticeName + " & " + noticeDescription + " & " + username + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TitledPane createTitledPane(String noticeName, String noticeDescription, String username) {
        TitledPane newNotice = new TitledPane();
        newNotice.setText(noticeName);

        VBox noticeDetails = new VBox();
        Label descriptionLabel = new Label("Description: " + noticeDescription);
        Label userLabel = new Label("Username: " + username);
        noticeDetails.getChildren().addAll(descriptionLabel, userLabel);

        newNotice.setContent(noticeDetails);

        return newNotice;
    }
}
