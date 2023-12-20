package com.example.emates;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment2 {
    private String FILE_PATH_PAY = "C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\paymentData.txt";
    private Stage stage;
    private Scene scene;
    private Parent root;

    private ObservableList<Payment> paymentsList = FXCollections.observableArrayList();

    @FXML
    private TextArea DesFx;
    @FXML
    private TableView<Payment> paymentTableView;
    @FXML
    private TextField amountTf;
    @FXML
    private Label totalAmountDisplay;

    public static class Payment {
        private int sl;
        private final String amount;
        private final String description;
        private final String date;

        public Payment(int sl, String amount, String description, String date) {
            this.sl = sl;
            this.amount = amount;
            this.description = description;
            this.date = date;
        }

        public int getSl() {
            return sl;
        }

        public void setSl(int a) {
            this.sl = a;
        }

        public String getAmount() {
            return amount;
        }

        public String getDescription() {
            return description;
        }

        public String getDate() {
            return date;
        }
    }
    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        TableColumn<Payment, Integer> slColumn = new TableColumn<>("SL.");
        slColumn.setCellValueFactory(new PropertyValueFactory<>("sl"));
        slColumn.setPrefWidth(50);
        TableColumn<Payment, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amountColumn.setPrefWidth(100);
        TableColumn<Payment, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setPrefWidth(200);
        TableColumn<Payment, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(150);
        loadDataFirstTime();
        paymentTableView.getColumns().addAll(slColumn, amountColumn, descriptionColumn, dateColumn);
        paymentTableView.setItems(paymentsList);
        updateTableView();
        try{
            File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] sline = line.split("&");

            br.close();

            if(sline[0].equals("Admin")){

            }else{
                deleteButton.setVisible(false);
            }
        }catch (Exception e){

        }
    }

    @FXML
    public void switchtoDashboard(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchtoExpenses(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("InputCost2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchtofoodmenu(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FoodMenu.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchtoChat(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Chat.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchtoBulletBoard(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Bulletboard.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchtoLogin(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void updateTableView() {
        paymentTableView.setItems(paymentsList);
        updateTotalAmountDisplay();
    }

    @FXML
    private void loadDataFirstTime() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH_PAY))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("&");
                int paymentNo = Integer.parseInt(parts[0]);
                String paymentDescription = parts[1];
                String paymentAmount = parts[2];
                String date = parts[3];
                Payment entry = new Payment(paymentNo, paymentDescription, paymentAmount, date);
                paymentsList.add(entry);
            }
            updateTotalAmountDisplay();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateTotalAmountDisplay() {
        double totalAmount = paymentsList.stream()
                .mapToDouble(payment -> Double.parseDouble(payment.getAmount()))
                .sum();
        try{
            File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\totalAmount.txt");
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(totalAmount));
            bw.close();
        }catch (Exception e){
            System.out.println("Error");
        }
        totalAmountDisplay.setText(String.valueOf(totalAmount));
    }

    @FXML
    private void savePayment() {
        String amount = amountTf.getText();
        String description = DesFx.getText();
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        String formattedDate = myDateObj.format(myFormatObj);

        Payment newPayment = new Payment(paymentsList.size() + 1, amount, description, formattedDate);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH_PAY, true))) {
            int sl = newPayment.sl;
            bw.write(sl + "&" + amount + "&" + description + "&" + formattedDate + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!isValidAmount(amount)) {
            showAlert("Invalid Input", "Please enter a valid numeric amount.");
            return;
        }

        paymentsList.add(newPayment);

        amountTf.clear();
        DesFx.clear();

        updateTotalAmountDisplay();
        updateTableView();
    }

    @FXML
    private void deletePayment() {
        Payment selectedPayment = paymentTableView.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            int selectedIndex = paymentsList.indexOf(selectedPayment);

            paymentsList.remove(selectedPayment);

            for (int i = selectedIndex; i < paymentsList.size(); i++) {
                paymentsList.get(i).setSl(i + 1);
            }

            updateTotalAmountDisplay();
            updateTableView();
            updateDataFile(); // Add this line to update the file after deletion
        }
    }
    private void updateDataFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH_PAY))) {
            for (Payment entry : paymentsList) {
                bw.write(entry.getSl() + "&" + entry.getAmount() + "&" + entry.getDescription() + "&" + entry.getDate() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isValidAmount(String amount) {
        return amount.matches("\\d+(\\.\\d+)?");
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void switchTo(String fxml) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) DesFx.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
