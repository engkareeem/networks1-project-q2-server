package com.example.networksh2_tcp_server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TCPServer");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        MyFileReader fileReader = new MyFileReader();
        System.out.println(fileReader.users.size());
        System.out.println(fileReader.login("koko111", "123123", "12", "2314").getDescription());
        fileReader = null;
        fileReader = new MyFileReader();
        System.out.println(fileReader.users.size());
//        launch();
    }
}