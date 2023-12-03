module com.example.networksh2_tcp_server {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.networksh2_tcp_server to javafx.fxml;
    exports com.example.networksh2_tcp_server;
}