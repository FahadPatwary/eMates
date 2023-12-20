package com.example.emates;
// ClientHandler.java

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ChatController chatController;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket clientSocket, ChatController chatController) {
        this.clientSocket = clientSocket;
        this.chatController = chatController;
        try {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = reader.readLine();
                if (message == null) {
                    break;
                }
                System.out.println("Received: " + message);

                // Broadcast the message to all clients
                for (ClientHandler client : chatController.getClients()) {
                    client.sendMessage(message);
                }

                // Save the message to file
                chatController.saveMessageToFile(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            chatController.removeClient(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
