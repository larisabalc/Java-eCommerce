package com.example.proiectmip;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CartController extends LogginUserController implements Initializable {

    @FXML
    private TableView productCart;
    @FXML
    private Button btnBackUser;

    @FXML
    private Button btnGoToOrder;

    @FXML
    private TableColumn<Product, String> colDelete;

    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, String> colPrice;


    @FXML
    void GoBackUser(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnBackUser.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }


    @FXML
    void GoToOrder(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnGoToOrder.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Order.fxml"));
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }

    ObservableList<Product> listProducts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try
    {
        try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
            //products.getItems().clear();
            PreparedStatement select = connection.prepareStatement("select * from MIP_cart inner join MIP_product on MIP_product.id_product=MIP_cart.id_product where id_user = ?");
            select.setInt(1, user.GetId());
            ResultSet resultSelect = select.executeQuery();
            while (resultSelect.next()) {
                Product product = new Product();
                product.SetDenumire(resultSelect.getString("name"));
                product.SetDescription(resultSelect.getString("description"));
                product.SetPrice(resultSelect.getString("price"));
                product.SetTip(resultSelect.getString("type"));
                product.SetBrand(resultSelect.getString("brand"));
                product.SetId(resultSelect.getInt("id_product"));
                listProducts.add(product);
            }
            colName.setCellValueFactory(new PropertyValueFactory<Product, String>("denumire"));
            colPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("pret"));
            Callback<TableColumn<Product, String>, TableCell<Product, String>> btnAddToCart
                    = //
                    new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
                        @Override
                        public TableCell call(final TableColumn<Product, String> param) {
                            final TableCell<Product, String> cell = new TableCell<Product, String>() {

                                final Button btn = new Button("DELETE");

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        btn.setOnAction(event -> {
                                            Product product = getTableView().getItems().get(getIndex());
                                            String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
                                            try {
                                                try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                                                    System.out.println("Success!");
                                                    PreparedStatement select = connection.prepareStatement("delete from MIP_cart where id_product=?");
                                                    select.setInt(1, product.GetId());
                                                    select.executeUpdate();
                                                    System.out.println("DELETED!");
                                                    productCart.getItems().clear();
                                                    productCart.getItems().addAll(listProducts);
                                                    productCart.refresh();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                                        setGraphic(btn);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };
            colDelete.setCellFactory(btnAddToCart);
            productCart.setItems(listProducts);
            productCart.refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } catch(Exception e)
    {
        e.printStackTrace();
    }
}
}
