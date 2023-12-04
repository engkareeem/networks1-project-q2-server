package com.example.networksh2_tcp_server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    TextField listeningPortField;
    @FXML
    ComboBox<String> interfacesComboBox;
    @FXML
    TextArea onlineUsersArea,statusArea,statusHistoryArea;
    @FXML
    Button listeningButton;

    public boolean isListening = false;
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
        ReceiverTCP.init();
    }

    public void listeningButtonClicked() throws IOException {
        if(!isListening) {
            if(!listeningPortField.getText().isEmpty()) {
                int port = Integer.parseInt(listeningPortField.getText());
                ReceiverTCP.updatePort(port);
                listeningButton.setText("Stop Listening");
                isListening = true;
            }
        } else {
            ReceiverTCP.stopPort();
            listeningButton.setText("Start Listening");
            isListening = false;
        }
    }
    public void clearButtonClicked() {
        statusHistoryArea.clear();
    }


}
