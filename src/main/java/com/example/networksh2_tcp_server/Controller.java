package com.example.networksh2_tcp_server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField listeningPortField;
    @FXML
    ComboBox<String> interfacesComboBox;
    @FXML
    TextArea onlineUsersArea,statusArea;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(String inter: Functions.getInterfaces()) {
            System.out.println("test");
            interfacesComboBox.getItems().add(inter);
        }
        System.out.println("test");
    }

    public void listeningButtonClicked() {

    }

}
