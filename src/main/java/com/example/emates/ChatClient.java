package com.example.emates;
// ChatClient.java (Client side)

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    @FXML
    private TextField messageField;

    private PrintWriter clientWriter;

    public void initialize() {
        // Connect to the server
        try {
            Socket socket = new Socket("192.168.0.169", 5555); // Use the correct server IP and port
            clientWriter = new PrintWriter(socket.getOutputStream(), true);

            // Create a thread to listen for messages from the server
            Thread clientThread = new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Handle incoming messages from the server (e.g., update UI)
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sendButtonClicked() {
        String message = messageField.getText();
        // Send the message to the server
        clientWriter.println(message);

        // Clear the input field
        messageField.clear();
    }
}
