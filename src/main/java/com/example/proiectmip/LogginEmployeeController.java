package com.example.proiectmip;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LogginEmployeeController {

    @FXML
    private TextField txtLogginEmployeeCode;

    @FXML
    private PasswordField txtLogginEmployeePassword;

    @FXML
    private Button btnBackLogginEmployee;

    @FXML
    private Button btnLoggingEmployee;

    static Employee employee = new Employee();

    @FXML
    void BackLogginEmployee(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnBackLogginEmployee.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("EmployeeUser.fxml"));
        primaryStage.setTitle("WELCOME!");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @FXML
    void LogginEmployee(MouseEvent event) {
        employee.SetCode(Integer.parseInt(txtLogginEmployeeCode.getText()));
        employee.SetPassword(txtLogginEmployeePassword.getText());
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl,"sa","1234");) {
                PreparedStatement select = connection.prepareStatement("select count(id_employee) from MIP_employees where  code = ? and password = ?");
                select.setString(1, Integer.toString(employee.GetCode()));
                select.setString(2, employee.GetPassword());
                ResultSet resultSelect = select.executeQuery();
                String number = "";
                while (resultSelect.next()) {
                    number = resultSelect.getString(1);
                    System.out.println(number);
                }
                if(Integer.parseInt(number) == 0)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("ONE OR MANY FIELD MAY NOT MATCH!");
                    alert.setContentText("Code or password may be wrong! Try again!");

                    alert.showAndWait();
                }
                else {
                    Stage stage = (Stage) btnLoggingEmployee.getScene().getWindow();
                    stage.close();
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("Employee.fxml"));
                    primaryStage.setTitle("LOGGIN EMPLOYEE");
                    primaryStage.setScene(new Scene(root,  600,400));
                    primaryStage.show();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
