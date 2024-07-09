package com.example.proiectmip;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProfileController extends LogginEmployeeController implements Initializable {

    @FXML
    private Button btnBackEmployee;
    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblSalary;

    @FXML
    private Label lblHiringDate;

    @FXML
    void BackEmployee(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnBackEmployee.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Employee.fxml"));
        primaryStage.setTitle("WELCOME!");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                PreparedStatement select = connection.prepareStatement("select * from MIP_employees where code = ?");
                select.setString(1, Integer.toString(employee.GetCode()));
                Employee profile = new Employee();
                ResultSet resultSelect = select.executeQuery();
                while (resultSelect.next()) {
                    profile.SetLastName(resultSelect.getString("last_name"));
                    profile.SetFirstName(resultSelect.getString("first_name"));
                    profile.SetEmail(resultSelect.getString("email"));
                    profile.SetPassword(resultSelect.getString("password"));
                    profile.SetSalary(Double.parseDouble(resultSelect.getString("salary")));
                    profile.SetHiringDate(resultSelect.getString("hiring_date"));
                    profile.SetCode(Integer.parseInt(resultSelect.getString("code")));
                }
                lblLastName.setText(profile.GetLastName());
                lblFirstName.setText(profile.GetFirstName());
                lblSalary.setText("$"+Double.toString(profile.GetSalary()));
                lblHiringDate.setText(profile.GetHiringDate());
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}