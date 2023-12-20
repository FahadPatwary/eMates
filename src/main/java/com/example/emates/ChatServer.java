package com.example.emates;

// ChatServer.java

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private List<ClientHandler> clients = new ArrayList<>();
    private ChatController chatController;  // Declare a ChatController instance

    public static void main(String[] args) {
        new ChatServer().startServer();
    }

    private void startServer() {
        // Initialize the ChatController instance
        chatController = new ChatController();

        try {
            ServerSocket serverSocket = new ServerSocket(5556);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Pass the ChatController instance to ClientHandler
                ClientHandler clientHandler = new ClientHandler(clientSocket, chatController);
                clients.add(clientHandler);

                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
