package com.example.networksh2_tcp_server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.exit;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("TCPServer");
        stage.setScene(scene);
        stage.show();
        Controller.currentStage = stage;
        stage.setOnCloseRequest(event -> {
            try {
                exit(0);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    public static void main(String[] args) {
        launch();
    }
}