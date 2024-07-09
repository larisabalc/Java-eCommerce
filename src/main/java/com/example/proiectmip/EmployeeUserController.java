package com.example.proiectmip;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeUserController {

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnQuit;

    @FXML
    void Quit(MouseEvent event) {
        Stage stage = (Stage) btnQuit.getScene().getWindow();
        stage.close();
    }

    @FXML
    void pickedEmployee(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnEmployee.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogginEmployee.fxml"));
        primaryStage.setTitle("LOGGIN EMPLOYEE");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @FXML
    void pickedUser(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("SignupUser.fxml"));
        primaryStage.setTitle("SIGNUP USER");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

}
