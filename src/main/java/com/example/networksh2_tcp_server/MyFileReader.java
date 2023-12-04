package com.example.networksh2_tcp_server;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class MyFileReader {
    static List<User> users;
    static String fileName;
    public MyFileReader() {
        fileName = "input.txt";
        updateData();
    }

    public void updateData(){
        users = readDataFromFile(fileName);
    }


    public LoginStatus login(String username, String password, String ip, String port){
        for (User user: users ){
            if(user.username.equalsIgnoreCase(username)){
                if(user.password.equalsIgnoreCase(password)){
                    user.ip = ip;
                    user.port = port;
                    return LoginStatus.LOGIN_SUCCESS;
                }else{
                    return LoginStatus.WRONG_PASSWORD;
                }
            }
        }
        addUser(username, password, ip, port);
        return LoginStatus.USER_CREATED;
    }
    public void logout(String username){
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).username.equalsIgnoreCase(username)){
                users.remove(i);
                break;
            }
        }
    }

    public void addUser(String username, String password, String ip, String port) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write("\n" + username + "," + password);
            fileWriter.close();
            users.add(new User(username, password, ip, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private List<User> readDataFromFile(String filename) {
        List<User> usersList = new ArrayList<>();

        try  {
            Scanner reader = new Scanner(new File(filename));

            String line;
            while (reader.hasNext()) {
                line = reader.nextLine();
                String[] data = line.split(",");
                String username = data[0];
                String password = data[1];


                User user = new User(username, password);
                usersList.add(user);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usersList;
    }
}

class User {
    public String username;
    public String password;

    public String ip;

    public String port;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        ip = "NONE";
        port = "NONE";

    }

    public User(String username, String password, String ip, String port) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", this.username,this.ip,this.port);
    }
}