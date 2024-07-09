package com.example.proiectmip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.regex.Pattern;


public class SignupUserController {

    @FXML
    private Button btnBackSignUp;
    @FXML
    private Button btnSignUpUser;
    @FXML
    private Hyperlink linkGoToUser;
    @FXML
    private DatePicker pickDate;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private TextField txtSignUpEmail;
    @FXML
    private TextField txtSignUpFirstName;
    @FXML
    private TextField txtSignUpLastName;
    @FXML
    private PasswordField txtSignUpPassword;

    User user = new User();

    @FXML
    void GoToLogginUser(MouseEvent event) throws IOException {
        Stage stage = (Stage) linkGoToUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("LogginUser.fxml"));
        primaryStage.setTitle("LOGGIN USER");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @FXML
    void BackSignUp(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnBackSignUp.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("EmployeeUser.fxml"));
        primaryStage.setTitle("WELCOME!");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }


    @FXML
    void SelectDate(ActionEvent event) {
        LocalDate data = pickDate.getValue();
    }

    @FXML
    void InsertUser(MouseEvent event) {
        if (Pattern.compile("^[a-zA-Z]*$").matcher(txtSignUpLastName.getText()).matches())
            user.SetLastName(txtSignUpLastName.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("LAST NAME WRONG!");
            alert.setContentText("LastName must contain only letters!");
            alert.showAndWait();
        }
        if (Pattern.compile("^[a-zA-Z]*$").matcher(txtSignUpFirstName.getText()).matches())
            user.SetFirstName(txtSignUpFirstName.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("FIRST NAME WRONG!");
            alert.setContentText("FirstName must contain only letters!");
            alert.showAndWait();
        }
        if (Pattern.compile("[a-zA-Z0-9_+&*-]*@" + "yahoo.com$").matcher(txtSignUpEmail.getText()).matches())
            user.SetEmail(txtSignUpEmail.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("EMAIL WRONG!");
            alert.setContentText("Email must be [...]@yahoo.com!");
            alert.showAndWait();
        }
        user.SetBirthdate(pickDate.getValue());
        user.SetPassword(txtSignUpPassword.getText());
        if (user.GetPassword() == null || user.GetFirstName() == null || user.GetLastName() == null || user.GetEmail() == null || user.GetBirthdate().toString() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("ERROR!");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
        } else {
            if (txtSignUpPassword.getText().equals(txtConfirmPassword.getText())) {
                String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
                try {
                    try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                        PreparedStatement select = connection.prepareStatement("select count(id_user) from MIP_users where email = ?");
                        select.setString(1, user.GetEmail());
                        ResultSet resultSelect = select.executeQuery();
                        String number = "";
                        while (resultSelect.next()) {
                            number = resultSelect.getString(1);
                        }
                        if (Integer.parseInt(number) == 0) {
                            PreparedStatement insert = connection.prepareStatement("insert into MIP_users values (?, ?, ?, ?, ?)");
                            insert.setString(1, user.GetLastName());
                            insert.setString(2, user.GetFirstName());
                            insert.setString(3, user.GetEmail());
                            insert.setString(4, user.GetPassword());
                            insert.setDate(5, Date.valueOf((LocalDate) user.GetBirthdate()));
                            insert.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("SUCCESS!");
                            alert.setHeaderText("WELCOME!");
                            alert.showAndWait();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("ERROR");
                            alert.setHeaderText("EMAIL HAS ALREADY BEEN USED!");
                            alert.setContentText("Try again with another email!");
                            alert.showAndWait();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("PASSWORDS DON'T MATCH!");
                alert.setContentText("Passwords must be identical!");
                alert.showAndWait();
            }
        }
    }
}
