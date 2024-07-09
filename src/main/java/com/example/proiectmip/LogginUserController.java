package com.example.proiectmip;

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

public class LogginUserController {

    @FXML
    private Button btnLogginUser;
    @FXML
    private TextField txtLogginUserEmail;

    @FXML
    private PasswordField txtLogginUserPassword;

    @FXML
    private Button btnBackLogginUser;

    static User user = new User();

    @FXML
    void BackLogginUser(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnBackLogginUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("SignupUser.fxml"));
        primaryStage.setTitle("SIGNUP USER");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @FXML
    void LoginUser(MouseEvent event) {
        user.SetEmail(txtLogginUserEmail.getText());
        user.SetPassword(txtLogginUserPassword.getText());
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl,"sa","1234");) {
                PreparedStatement select = connection.prepareStatement("select id_user from MIP_users where  email = ? and password = ?");
                select.setString(1, user.GetEmail());
                select.setString(2, user.GetPassword());
                ResultSet resultSelect = select.executeQuery();
                Integer number = 0;
                while (resultSelect.next()) {
                    user.SetId(Integer.parseInt(resultSelect.getString(1)));
                    number++;
                    System.out.println(number);
                }
                if(number == 0)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("ONE OR MANY FIELD MAY NOT MATCH!");
                    alert.setContentText("Email or password may be wrong! Try again!");

                    alert.showAndWait();
                }
                else
                {
                    Stage stage = (Stage) btnLogginUser.getScene().getWindow();
                    stage.close();
                    Stage primaryStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
                    primaryStage.setTitle("SIGNUP USER");
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
