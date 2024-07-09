package com.example.proiectmip;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class UserController extends LogginUserController implements Initializable {

    @FXML
    private Button btnAddToCart;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnViewCart;


    @FXML
    private ImageView clickedImage;
    @FXML
    private HBox cardHolder;
    private List<Product> recentlyViewed;
    private List<Product> allShoes;

    @FXML
    private ComboBox<String> clickedProductSizes;

private MyListener myListener;
    @FXML
    private Label clickedProductName;

    @FXML
    private Label clickedProductPrice;

    @FXML
    private GridPane allProducts;
     private int idProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       recentlyViewed = new ArrayList<>(RecentlyViewed());
       allShoes = new ArrayList<>(Shoes());
       if(allShoes.size()>0) {
           SetChosenShoe(allShoes.get(0));
           myListener = new MyListener() {
               @Override
               public void onClickListener(Product product) {
                   SetChosenShoe(product);
                   idProduct = product.GetId();

                   String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
                   try {
                       try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                               PreparedStatement insert = connection.prepareStatement("insert into MIP_recently_viewed values (?,?)");
                               insert.setInt(1, user.GetId());
                               insert.setInt(2, idProduct);
                               insert.executeUpdate();
                               cardHolder.getChildren().clear();
                           recentlyViewed = new ArrayList<>(RecentlyViewed());
                           for(Product value : recentlyViewed)
                           {
                               FXMLLoader fxmlLoader = new FXMLLoader();
                               fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
                               AnchorPane cardBox = fxmlLoader.load();
                               CardController cardController = fxmlLoader.getController();
                               cardController.SetData(value);
                               cardHolder.getChildren().add(cardBox);
                           }

                       } catch (SQLException e) {
                           e.printStackTrace();
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           };
       }
       int column = 0;
       int row = 3;
        try {
       for(Product value : recentlyViewed)
       {
           FXMLLoader fxmlLoader = new FXMLLoader();
           fxmlLoader.setLocation(getClass().getResource("Card.fxml"));
           AnchorPane cardBox = fxmlLoader.load();
           CardController cardController = fxmlLoader.getController();
           cardController.SetData(value);
           cardHolder.getChildren().add(cardBox);
       }
       for(Product shoe : allShoes)
       {
           FXMLLoader fxmlLoader = new FXMLLoader();
           fxmlLoader.setLocation(getClass().getResource("Shoe.fxml"));
           AnchorPane shoeBox = fxmlLoader.load();
           ShoeController shoeController = fxmlLoader.getController();
           shoeController.SetData(shoe,myListener);
          if(column == 3)
          {
              column = 0;
              row+=100;
          }
          allProducts.add(shoeBox,column++,row);
          GridPane.setMargin(shoeBox,new Insets(10));

       }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Product> RecentlyViewed() {
        List<Product> listaRecentlyViewed = new ArrayList<>();
        //change table to select from
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                //products.getItems().clear();
                PreparedStatement select = connection.prepareStatement("select * from MIP_recently_viewed inner join MIP_product on MIP_recently_viewed.id_product=MIP_product.id_product where id_user = ?");
                select.setInt(1, user.GetId());
                ResultSet resultSelect = select.executeQuery();
                while (resultSelect.next()) {
                    Product product = new Product();
                    product.SetDenumire(resultSelect.getString("name"));
                    product.SetDescription(resultSelect.getString("description"));
                    product.SetPrice(resultSelect.getString("price"));
                    product.SetTip(resultSelect.getString("type"));
                    product.SetBrand(resultSelect.getString("brand"));
                    product.SetSizes(resultSelect.getString("size"));
                    product.SetId(resultSelect.getInt("id_product"));
                    InputStream is=resultSelect.getBinaryStream("image");
                    OutputStream os=new FileOutputStream("photo.jpg");
                    byte[] contents =new byte[1024];
                    int size=0;
                    while((size = is.read(contents) )!= -1)
                    {
                        os.write(contents,0,size);
                    }
                    product.SetImage(new Image("file:photo.jpg"));
                    listaRecentlyViewed.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaRecentlyViewed;
    }
    private List<Product> Shoes() {
        List<Product> listaAllShoes = new ArrayList<>();
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                //products.getItems().clear();
                PreparedStatement select = connection.prepareStatement("select * from MIP_product");
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
                    InputStream is=resultSelect.getBinaryStream("image");
                    OutputStream os=new FileOutputStream("photo.jpg");
                    byte[] contents =new byte[1024];
                    int size=0;
                    while((size = is.read(contents) )!= -1)
                    {
                        os.write(contents,0,size);
                    }
                    product.SetImage(new Image("file:photo.jpg"));
                    listaAllShoes.add(product);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAllShoes;
    }
    private void SetChosenShoe(Product product)
    {
       clickedImage.setImage(product.GetImage());
       clickedProductSizes.getItems().clear();
       if(product.GetSizes()!=null) {
           String[] sizes = ReturnSizes(product);
           clickedProductSizes.getItems().addAll(sizes);
       }
       clickedProductName.setText(product.GetDenumire());
       clickedProductPrice.setText(product.GetPrice());

    }

    private String[] ReturnSizes(Product product)
    {
        String availableSizes = product.GetSizes();
        String[] result = availableSizes.split(",");
        return result;
    }

    @FXML
    void AddToCart(MouseEvent event) {
        String connectionUrl = "jdbc:sqlserver://DESKTOP-U1IQENG\\SQLEXPRESS01;database=User;trustServerCertificate=true;";
        try {
            try (Connection connection = DriverManager.getConnection(connectionUrl, "sa", "1234");) {
                PreparedStatement insert = connection.prepareStatement("insert into MIP_cart values (?,?)");
                insert.setInt(1, user.GetId());
                insert.setInt(2, idProduct);
                insert.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("SUCCESSFUL");
                alert.setHeaderText(null);
                alert.setContentText("ADDED TO CART!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void PlaceOrder(MouseEvent event) throws IOException{
        Stage stage = (Stage) btnPlaceOrder.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Order.fxml"));
        primaryStage.setTitle("ORDER");
        primaryStage.setScene(new Scene(root, 600,400));
        primaryStage.show();
    }

    @FXML
    void ViewCart(MouseEvent event) throws IOException {
        Stage stage = (Stage) btnViewCart.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Cart.fxml"));
        primaryStage.setTitle("CART");
        primaryStage.setScene(new Scene(root,  600,400));
        primaryStage.show();
    }
}