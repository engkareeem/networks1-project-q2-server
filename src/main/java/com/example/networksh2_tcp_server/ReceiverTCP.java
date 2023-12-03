package com.example.networksh2_tcp_server;

import javafx.application.Platform;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ReceiverTCP {
    private static int listeningPort;
    private static ServerSocket serverSocket;

    private static Thread mainThread;

    static {
        try {
            serverSocket = new ServerSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init(){
        mainThread = new Thread(ReceiverTCP::receiveTCP); // Start the udp listener with 1234 as default port
        mainThread.start();
    }

    public static void updatePort(int port){
        if(port == listeningPort) return;
        listeningPort = port;
        try {
            mainThread.interrupt();
            serverSocket.close();
            serverSocket = new ServerSocket(listeningPort);
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void receiveTCP() {

        while (!Thread.interrupted()) {
            Socket socket;

            try {
                socket = serverSocket.accept();
                new Thread(() -> {
                    try {
                        DataInputStream inputStream = new DataInputStream(
                                new BufferedInputStream(socket.getInputStream()));
                        String msg = inputStream.readUTF();

                        if(msg.contains("login")) {
                            String username = msg.split("@")[1];
                            String password = msg.split("@")[2];
                            String ip       = msg.split("@")[3];
                            String port     = msg.split("@")[4];

                            LoginStatus success = Functions.fileReader.login(username,password,ip,port);
                            if(success == LoginStatus.LOGIN_SUCCESS || success == LoginStatus.USER_CREATED) {
                                Functions.broadcastActiveUsers();
                                // TODO: Login success (OK)
                            } else {
                                // TODO: Login error
                            }
                        } else if(msg.contains("logout")) {
                            String username = msg.split("@")[1];
                            Functions.fileReader.logout(username);
                            Functions.broadcastActiveUsers();
                        } else {
                            // TODO: Bad request
                        }


                        inputStream.close();
                        socket.close();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } catch (IOException e) {
                continue;
//                throw new RuntimeException(e);
            }
        }
    }

    public static void processCommand(String cmd, String ip, String port) {
        if(cmd.contains("delete@")) {
            // TODO: delete a msg
            String id = cmd.split("@")[0];
//            Functions.deleteMessage(id);
//            Functions.changeStatus("A Message deleted by", ip, port);
        }else if(cmd.contains("deleteAll@")){
            //TODO: delete all user message
//            Functions.deleteAllMessages();
//            Functions.changeStatus("All messages deleted by", ip, port);

        }
    }



}