package com.example.emates;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatController {

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
    public void switchtoPayment(ActionEvent e) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Payment2.fxml"));
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
    private TextField messageField;

    @FXML
    private TextField chatname;

    @FXML
    private VBox chatspace;

    private String chatDataFilePath = "src/main/resources/com/example/emates/chatdata.txt";
    private List<ClientHandler> clients = new ArrayList<>();

    private PrintWriter serverWriter;

    public List<ClientHandler> getClients() {
        return clients;
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public void initialize() {
        // Load existing messages from file on application startup
        List<String> existingMessages = loadMessagesFromFile();
        for (String message : existingMessages) {
//            String[] parts = message.split("&&");
//            if (parts.length == 2) {
//                String username = parts[0];
//                String text = parts[1];
//
//                // Create and add a new TextField
//
//            }
            Label textField = createMessageTextField(message);

            chatspace.getChildren().add(textField);
        }

        // Start the server in a new thread
        Thread serverThread = new Thread(this::startServer);
        serverThread.start();
    }

    @FXML
    private void sendButtonClicked() throws Exception{
        File file = new File("C:\\Users\\Dell\\Desktop\\eMates\\src\\main\\resources\\com\\example\\emates\\tempUser.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        String[] sline = line.split("&");

        br.close();
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm");
        String formattedDate = myDateObj.format(myFormatObj);

        String username =sline[0]+" ("+formattedDate+") ";

        String message = messageField.getText();

        // Send the message to the server
        sendToServer(username + "&&" + message);

        // Save the message to file and create and add a new TextField
        broadcastMessage(username + ": " + message);

        // Clear the input field
        messageField.clear();
    }

    public void sendToServer(String message) {
        // Synchronize access to serverWriter to avoid concurrent modification
        synchronized (this) {
            if (serverWriter != null) {
                serverWriter.println(message);
            }
        }
    }

    public void saveMessageToFile(String message) {
        // Combine username and message with "&&" separator
        String formattedMessage = message;

        // Save the formatted message to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(chatDataFilePath, true))) {
            writer.write(formattedMessage);
            writer.newLine();  // Add a newline character after each message
            System.out.println("Message saved: " + formattedMessage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save message to file: " + formattedMessage);
        }
    }

    private List<String> loadMessagesFromFile() {
        List<String> existingMessages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(chatDataFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingMessages.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingMessages;
    }

    private Label createMessageTextField(String message) {
        Label textField = new Label(message);

        return textField;
    }

    private void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(5556);
            Socket clientSocket;

            // Accept client connections
            while (true) {
                clientSocket = serverSocket.accept();
                serverWriter = new PrintWriter(clientSocket.getOutputStream(), true);

                // Create a new ClientHandler for each client
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);

                // Create a thread to handle messages from this client
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastMessage(String formattedMessage) {
        // Save the formatted message to file
        saveMessageToFile(formattedMessage);

        // Execute UI-related code on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Check if a TextField with the same message already exists
            boolean messageExists = chatspace.getChildren().stream()
                    .filter(node -> node instanceof TextField)
                    .map(node -> ((TextField) node).getText())
                    .anyMatch(text -> text.equals(formattedMessage));

            // If the message doesn't exist, create and add a new TextField
            if (!messageExists) {
                Label textField = createMessageTextField(formattedMessage);
                chatspace.getChildren().add(textField);
            }
        });
    }

    public void setWriter(PrintWriter writer) {
        // Not implemented in the provided code
        // You can add implementation if needed
    }

}
