package com.example.proiectmip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class OrderController extends LogginUserController implements Initializable{


    @FXML
    private CheckBox check;

    @FXML
    private ComboBox<String> comboBoxPayment;

    @FXML
    private ComboBox<String> comboBoxShipment;

    @FXML
    private Button btnCart;

    @FXML
    private Button btnMenu;
    @FXML
    private Label lblPrice;

    @FXML
    private TextField txtAdress;

    @FXML
    private TextField txtCardNr;

    @FXML
    private TextField txtCvv;

    @FXML
    private TextField txtPhone;
    @FXML
    private Pane paneCard;

    Double TotalPrice()
    {
        double sum=0;
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try
        {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                //products.getItems().clear();
                PreparedStatement select = connection.prepareStatement("select price from MIP_cart inner join MIP_product on MIP_product.id_product=MIP_cart.id_product where id_user = ?");
                select.setInt(1, user.GetId());
                ResultSet resultSelect = select.executeQuery();
                while (resultSelect.next()) {
                    sum+=resultSelect.getDouble("price");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return sum;
    }

    @FXML
    void GoBackToCart(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnCart.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Cart.fxml"));
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @FXML
    void GoBackMenu(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnMenu.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    @FXML
    void SelectedCard(ActionEvent event) {
            if(comboBoxPayment.getSelectionModel().getSelectedItem() == "Card")
            {
            paneCard.setVisible(true);
            }
    }

    @FXML
    void InsertOrder(MouseEvent event) {
    if(check.isSelected()) {
    String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
    try {
        try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
            PreparedStatement insert = connection.prepareStatement("insert into MIP_order values (?,?,?,?, ?, ?, ?, ?, ?,?)");
            insert.setDate(1, Date.valueOf(LocalDate.now()));
            insert.setDate(2, Date.valueOf(LocalDate.now().plusDays(3)));
            insert.setString(3, "registered");
            insert.setString(4, txtAdress.getText());
            insert.setString(5, txtPhone.getText());
            insert.setString(6, txtCardNr.getText());
            insert.setString(7, txtCvv.getText());
            insert.setString(8, comboBoxPayment.getSelectionModel().getSelectedItem());
            insert.setString(9, comboBoxShipment.getSelectionModel().getSelectedItem());
            insert.setInt(10, user.GetId());
            insert.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCESSFUL");
            alert.setHeaderText(null);
            alert.setContentText("ORDER PLACED!");
            alert.showAndWait();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}else
{
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("ERROR");
    alert.setHeaderText(null);
    alert.setContentText("MUST CHECK TERMS & CONDITIONS!");
    alert.showAndWait();
}
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblPrice.setText(TotalPrice().toString());
        paneCard.setVisible(false);
        String[] payment={"Card","Cash"};
        comboBoxPayment.getItems().addAll(payment);
        String[] shipment={"Standard","Premium"};
        comboBoxShipment.getItems().addAll(shipment);
    }
}