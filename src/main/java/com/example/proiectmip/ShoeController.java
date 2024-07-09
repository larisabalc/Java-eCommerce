package com.example.proiectmip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ShoeController {
    @FXML
    private Label productName;
    @FXML
    private ImageView imgShoe;

    Product product;
    @FXML
    private void click(MouseEvent mouseEvent)
    {
        myListener.onClickListener(product);
    }
    private MyListener myListener;
    public void SetData(Product product, MyListener myListener) {
        /*Image image = new Image(getClass().getResourceAsStream(product.GetImage()));
        imgProductCard.setImage(image);*/
        this.product=product;
        this.myListener = myListener;
        productName.setText(product.GetDenumire());
        imgShoe.setImage(product.GetImage());
    }

}
