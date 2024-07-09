package com.example.proiectmip;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

import java.util.Vector;

public class Product {
    private final StringProperty denumire = new SimpleStringProperty();

    public final StringProperty denumireProperty() {
        return denumire;
    }

    public final String GetDenumire() {
        return denumire.get();
    }

    public final void  SetDenumire(String denumire) {
        this.denumire.set(denumire);
    }


    private final StringProperty description = new SimpleStringProperty();

    public final StringProperty descriptionProperty() {
        return description;
    }

    public final String GetDescription() {
        return description.get();
    }

    public final void  SetDescription(String description) {
        this.description.set(description);
    }

    private Image image;

    public Image GetImage()
    {
        return image;
    }
    public void SetImage(Image image)
    {
        this.image = image;
    }

    private final StringProperty tip = new SimpleStringProperty();

    public final StringProperty tipProperty() {
        return tip;
    }
    public final void SetTip(String tip)
    {
        this.tip.set(tip);
    }
    public final String GetTip() {
        return tip.get();
    }
    private final StringProperty brand = new SimpleStringProperty();

    public final StringProperty brandProperty() {
        return brand;
    }
    public final void SetBrand(String brand)
    {
        this.brand.set(brand);
    }
    public final String GetBrand() {
        return brand.get();
    }

    private Integer id;
    public Integer GetId()
    {
        return id;
    }
    public void SetId(Integer id)
    {
        this.id=id;
    }

    private final StringProperty sizes = new SimpleStringProperty();

    public final StringProperty sizesProperty() {
        return sizes;
    }
    public final void SetSizes(String size)
    {
        sizes.set(size);
    }
    public final String GetSizes() {
        return sizes.get();
    }
    private final StringProperty pret = new SimpleStringProperty();


    public final StringProperty pretProperty() {
        return pret;
    }

    public final void SetPrice(String pret)
    {
        this.pret.set(pret);
    }

    public final String GetPrice() {
        return pret.get();
    }

    Product()
    {

    }
}
