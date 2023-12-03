package com.example.networksh2_tcp_server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField listeningPortField;
    @FXML
    ComboBox<String> interfacesComboBox;
    @FXML
    TextArea onlineUsersArea,statusArea;

    public static Stage currentStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(String inter: Functions.getInterfaces()) {
            interfacesComboBox.getItems().add(inter);
        }
        listeningPortField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                listeningPortField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if(newValue.length() > 4) {
                listeningPortField.setText(oldValue);
            }
        });
    }

    public void listeningButtonClicked() {

        if(!listeningPortField.getText().isEmpty()) {
            int port = Integer.parseInt(listeningPortField.getText());
            ReceiverTCP.updatePort(port);
        }
    }


}
