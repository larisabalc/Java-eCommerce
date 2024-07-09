package com.example.proiectmip;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private TabPane tab;
    @FXML
    private Tab tabViewProducts;

    @FXML
    private Tab tabModifyProduct;

    @FXML
    private Tab tabViewOrders;

    @FXML
    private Tab tabModifyOrder;

    @FXML
    private TableColumn<Product,String> columnName;

    @FXML
    private TableColumn<Product,String> columnSize;

    @FXML
    private TableColumn<Product, String> columnPrice;

    @FXML
    private TableColumn<Product, String> columnBrand;

    @FXML
    private TableColumn<Product, String> columnType;

    @FXML
    private TableColumn<Product, String> columnDelete;

    @FXML
    private TableColumn<Order, String> columnDateIn;

    @FXML
    private TableColumn<Order, String> columnDateOut;

    @FXML
    private TableColumn<Order, String> columnIdOrder;

    @FXML
    private TableColumn<Order, String> columnStatus;


    @FXML
    private TableView orders;
    @FXML
    private TableView products;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdateStatus;

    @FXML
    private Button btnModify;

    @FXML
    private Button btnProfile;

    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private ComboBox<String> comboBoxBrand;

    @FXML
    private ComboBox<String> comboBoxBrandUpdate;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private ComboBox<String> comboBoxTypeUpdate;


    @FXML
    private Tab tabAddProduct;


    @FXML
    private TextArea txtDescription;

    @FXML
    private TextArea txtDescriptionUpdate;

    @FXML
    private TextField txtIdUpdate;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtModelUpdate;

    @FXML
    private TextField txtPath;

    @FXML
    private TextField txtPathUpdate;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtPriceUpdate;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtSizeUpdate;
    @FXML
    private TextField txtsearchBar;

    @FXML
    void SearchProduct(ActionEvent event) {
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                products.getItems().clear();
                PreparedStatement select = connection.prepareStatement("select * from MIP_product where name = ? or brand = ? or type =?");
                select.setString(1, txtsearchBar.getText());
                select.setString(2, txtsearchBar.getText());
                select.setString(3, txtsearchBar.getText());
                ResultSet resultSelect = select.executeQuery();
                while (resultSelect.next()) {
                    Product product = new Product();
                    product.SetDenumire(resultSelect.getString("name"));
                    product.SetDescription(resultSelect.getString("description"));
                    product.SetPrice(resultSelect.getString("price"));
                    product.SetSizes(resultSelect.getString("size"));
                    product.SetTip(resultSelect.getString("type"));
                    product.SetBrand(resultSelect.getString("brand"));
                    product.SetId(resultSelect.getInt("id_product"));
                    listProducts.add(product);
                }
                columnName.setCellValueFactory(new PropertyValueFactory<Product, String>("denumire"));
                columnPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("pret"));
                columnSize.setCellValueFactory(new PropertyValueFactory<Product, String>("sizes"));
                columnBrand.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
                columnType.setCellValueFactory(new PropertyValueFactory<Product, String>("tip"));
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
                                                        PreparedStatement select = connection.prepareStatement("delete from MIP_product where id_product=?");
                                                        select.setInt(1, product.GetId());
                                                        select.executeUpdate();
                                                        System.out.println("DELETED!");
                                                        products.getItems().clear();
                                                        products.setItems(listProducts);
                                                        products.refresh();
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
                columnDelete.setCellFactory(btnAddToCart);
                products.setItems(listProducts);
                products.refresh();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ViewProfile(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnProfile.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ProfileEmployee.fxml"));
        primaryStage.setTitle("EMPLOYEE");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();

    }


    @FXML
    void InsertProduct(MouseEvent event) {
        Product product = new Product();
        product.SetDenumire(txtModel.getText());
        product.SetPrice(txtPrice.getText());
        product.SetSizes(txtSize.getText());
        product.SetDescription(txtDescription.getText());
        product.SetBrand(comboBoxBrand.getSelectionModel().getSelectedItem().toString());
        product.SetTip(comboBoxType.getSelectionModel().getSelectedItem().toString());
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                System.out.println("Success!");
                PreparedStatement select = connection.prepareStatement("select count(id_product) from MIP_product where name=? and description = ? and price = ? and type = ? and brand = ?");
                select.setString(1, product.GetDenumire());
                select.setString(2, product.GetDescription());
                select.setString(3, product.GetPrice());
                select.setString(4, product.GetTip());
                select.setString(5, product.GetBrand());
                ResultSet resultSelect = select.executeQuery();
                String number = "0";
                while (resultSelect.next()) {
                    number = resultSelect.getString(1);
                }
                if (Integer.parseInt(number) == 0) {
                    PreparedStatement insert = connection.prepareStatement("insert into MIP_product values (?,?,?,?,?,?,?)");
                    insert.setString(1, product.GetDenumire());
                    insert.setString(2, product.GetDescription());
                    insert.setString(3, product.GetPrice());
                    insert.setString(4, product.GetTip());
                    insert.setString(5, product.GetBrand());
                    InputStream in = new FileInputStream(txtPath.getText());
                    insert.setBlob(6, in);
                    insert.setString(7,product.GetSizes());
                    insert.executeUpdate();

                    products.getItems().clear();
                    products.setItems(listProducts);
                    products.refresh();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("SUCCESSFUL");
                    alert.setHeaderText(null);
                    alert.setContentText("PRODUCT HAS BEEN REGISTERED SUCCESSFULLY!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ERROR");
                    alert.setHeaderText(null);
                    alert.setContentText("PRODUCT HAS ALREADY BEEN REGISTERED!");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void UpdateProduct(MouseEvent event) {

        Product product = (Product)products.getSelectionModel().getSelectedItem() ;
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                PreparedStatement insert = connection.prepareStatement("update MIP_product set name = ?, description = ?, price =?, type=?, brand =?, image=?,size=? where id_product = ?");
                insert.setString(1, txtModelUpdate.getText());
                insert.setString(2, txtDescriptionUpdate.getText());
                insert.setString(3, txtPriceUpdate.getText());
                insert.setString(4, comboBoxTypeUpdate.getSelectionModel().getSelectedItem());
                insert.setString(5, comboBoxBrandUpdate.getSelectionModel().getSelectedItem());
                InputStream in = new FileInputStream(txtPathUpdate.getText());
                insert.setBlob(6, in);
                insert.setString(7, txtSizeUpdate.getText());
                insert.setString(8, product.GetId().toString());
                insert.executeUpdate();

                products.getItems().clear();
                products.setItems(listProducts);
                products.refresh();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESSFUL");
                alert.setHeaderText(null);
                alert.setContentText("PRODUCT HAS BEEN MODIFIED!");
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ChangeStatus(MouseEvent event) {
        Order order = (Order)orders.getSelectionModel().getSelectedItem();
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                PreparedStatement insert = connection.prepareStatement("update MIP_order set delivered_status = ? where id_order = ?");
                insert.setString(1, comboBoxStatus.getSelectionModel().getSelectedItem());
                insert.setString(2, order.GetId());
                insert.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESSFUL");
                alert.setHeaderText(null);
                alert.setContentText("ORDER HAS BEEN MODIFIED!!");
                alert.showAndWait();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ObservableList<Product> listProducts = FXCollections.observableArrayList();
    ObservableList<Order> listOrders = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        String[] types={"Everyday","Running","Boots","Special"};
        comboBoxType.getItems().addAll(types);
        comboBoxTypeUpdate.getItems().addAll(types);
        String[] brands={"Nike","Jordan","Converse","Adidas","Vans"};
        comboBoxBrand.getItems().addAll(brands);
        comboBoxBrandUpdate.getItems().addAll(brands);
        String[] status={"registered","preparing","sent","delivered"};
        comboBoxStatus.getItems().addAll(status);
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                products.getItems().clear();
                PreparedStatement select = connection.prepareStatement("select * from MIP_product ");
                ResultSet resultSelect = select.executeQuery();
                while (resultSelect.next()) {
                    Product product = new Product();
                    product.SetDenumire(resultSelect.getString("name"));
                    product.SetDescription(resultSelect.getString("description"));
                    product.SetPrice(resultSelect.getString("price"));
                    product.SetSizes(resultSelect.getString("size"));
                    product.SetTip(resultSelect.getString("type"));
                    product.SetBrand(resultSelect.getString("brand"));
                    product.SetId(resultSelect.getInt("id_product"));
                    listProducts.add(product);
                }
                columnName.setCellValueFactory(new PropertyValueFactory<Product, String>("denumire"));
                columnPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("pret"));
                columnSize.setCellValueFactory(new PropertyValueFactory<Product, String>("sizes"));
                columnBrand.setCellValueFactory(new PropertyValueFactory<Product, String>("brand"));
                columnType.setCellValueFactory(new PropertyValueFactory<Product, String>("tip"));
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
                                                        PreparedStatement select = connection.prepareStatement("delete from MIP_product where id_product=?");
                                                        select.setInt(1, product.GetId());
                                                        select.executeUpdate();
                                                        System.out.println("DELETED!");
                                                        products.getItems().clear();
                                                        products.setItems(listProducts);
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
                columnDelete.setCellFactory(btnAddToCart);
                products.getItems().clear();
                products.setItems(listProducts);
                products.refresh();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        products.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    tab.getSelectionModel().select(tabModifyProduct);
                }
            }
        });

        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                //products.getItems().clear();
                PreparedStatement select = connection.prepareStatement("select id_order,date_in,date_out,delivered_status from MIP_order");
                ResultSet resultSelect = select.executeQuery();
                while (resultSelect.next()) {
                    Order order = new Order();
                    order.SetId(resultSelect.getString("id_order"));
                    order.SetDateIn(resultSelect.getString("date_in"));
                    order.SetDateOut(resultSelect.getString("date_out"));
                    order.SetStatus(resultSelect.getString("delivered_status"));
                    listOrders.add(order);
                }
                columnDateIn.setCellValueFactory(new PropertyValueFactory<Order, String>("dateIn"));
                columnDateOut.setCellValueFactory(new PropertyValueFactory<Order, String>("dateOut"));
                columnStatus.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
                orders.getItems().clear();
                orders.setItems(listOrders);
                orders.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    tab.getSelectionModel().select(tabModifyOrder);
                }
            }
        });
    }

}
