package com.example.proiectmip;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CardController {
    @FXML
    private AnchorPane bckCard;
    @FXML
    private ImageView imgProductCard;
    @FXML
    private Label txtProductNameCard;

    private String colors[] = {"DCDCDC","808080","D3D3D3","A9A9A9","C0C0C0"};

    public void SetData(Product product)
    {
        imgProductCard.setImage(product.GetImage());
        txtProductNameCard.setText(product.GetDenumire());
        bckCard.setStyle("-fx-background-color: #"+ colors[(int)(Math.random()*colors.length)]+
                ";-fx-background-radius: 20");
    }
}
