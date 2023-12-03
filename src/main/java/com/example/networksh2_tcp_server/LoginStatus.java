package com.example.networksh2_tcp_server;

public enum LoginStatus {
    WRONG_PASSWORD(-1, "Wrong Password"),
    USER_CREATED(0, "New User has been added"),
    LOGIN_SUCCESS(1, "Login successfully");

    private final int statusCode;
    private final String description;

    LoginStatus(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getDescription() {
        return description;
    }

    public static String getStatusString(int statusCode) {
        for (LoginStatus status : LoginStatus.values()) {
            if (status.getStatusCode() == statusCode) {
                return status.getDescription();
            }
        }
        return "Unknown status code";
    }
}