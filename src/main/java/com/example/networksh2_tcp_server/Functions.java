package com.example.networksh2_tcp_server;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


import java.io.*;

import java.net.SocketException;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import java.util.Enumeration;

public class Functions {

    public static MyFileReader fileReader = new MyFileReader();

    public static ArrayList<String> getInterfaces(){
        ArrayList<String> interfaces = new ArrayList<>();

        try {

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();


            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if(networkInterface.getInetAddresses().hasMoreElements()){
                    String interfaceAddress = networkInterface.getInetAddresses().nextElement().getHostAddress();
                    String name = networkInterface.getName().replaceAll("wlan\\d*", "Wi-Fi").replaceAll("lo\\d*", "LocalHost").replaceAll("eth\\d*", "Ethernet");
                    if(interfaceAddress.matches("\\d+.\\d+.\\d+.\\d+")){
                        interfaces.add(name + ": " + interfaceAddress);
                    }
                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return interfaces;
    }




    public static void sendTCP(String message, User user) {

        try {
            System.out.println(message + " " + user);
            Socket socket = new Socket(user.ip, Integer.parseInt(user.port));

            DataOutputStream outputStream = new DataOutputStream(
                    socket.getOutputStream());

            outputStream.writeUTF(message);
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendStatus(LoginStatus status,String ip, String port) {
        User user = new User("","",ip,port);
        if(status == LoginStatus.WRONG_PASSWORD) {
            sendTCP("CMD@Error@The entered password is wrong",user);
        } else if(status == LoginStatus.LOGIN_SUCCESS) {
            sendTCP("CMD@Success@Login successful",user);
        } else if(status == LoginStatus.USER_CREATED) {
            sendTCP("CMD@Success@User Registered",user);
        } else {
            sendTCP("CMD@Error@Bad Request", user);
        }
    }

    public static void broadcastActiveUsers() {
        StringBuilder message = new StringBuilder("CMD@Notify");
        TextArea onlineUsersArea = (TextArea) Controller.currentStage.getScene().lookup("#onlineUsersArea");
        onlineUsersArea.setText("");
        for(User user: MyFileReader.users) {
            if(!user.isOnline()) continue;
            message.append("@").append(user);
            onlineUsersArea.appendText(user + "\n");
        }
        for(User user: MyFileReader.users) {
            if(!user.isOnline()) continue;
            sendTCP(message.toString(),user);
        }

    }
    public static void changeStatus(String text) {
        TextArea statusArea = (TextArea) Controller.currentStage.getScene().lookup("#statusArea");
        TextArea historyArea = (TextArea) Controller.currentStage.getScene().lookup("#statusHistoryArea");
        statusArea.setText(text);
        historyArea.appendText(text + "\n");

    }


}