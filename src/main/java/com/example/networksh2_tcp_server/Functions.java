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
                    System.out.println(interfaceAddress);
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

    public static void broadcastActiveUsers() {
        StringBuilder message = new StringBuilder("CMD@Notify");
        for(User user: MyFileReader.users) {
            message.append(String.format("@%s:%s:%s", user.username, user.ip, user.port));
        }
        for(User user: MyFileReader.users) {
            sendTCP(message.toString(),user);
        }
    }
    public static void changeStatus(String text, String ip, String port) {
        TextArea textArea = (TextArea) Controller.currentStage.getScene().lookup("#statusArea");
        textArea.setText(text + " IP = " + ip + ", Port = " + port);
    }


}