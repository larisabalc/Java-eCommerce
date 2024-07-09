package com.example.proiectmip;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
    private final StringProperty id = new SimpleStringProperty();

    public final StringProperty idProperty() {
        return id;
    }
    public final void SetId(String id)
    {
        this.id.set(id);
    }
    public final String GetId() {
        return id.get();
    }

    private final StringProperty status = new SimpleStringProperty();

    public final StringProperty statusProperty() {
        return status;
    }
    public final void SetStatus(String status)
    {
        this.status.set(status);
    }
    public final String GetStatus() {
        return status.get();
    }

    private final StringProperty dateIn = new SimpleStringProperty();

    public final StringProperty dateInProperty() {
        return dateIn;
    }
    public final void SetDateIn(String dateIn)
    {
        this.dateIn.set(dateIn);
    }
    public final String GetDateIn() {
        return dateIn.get();
    }

    private final StringProperty dateOut = new SimpleStringProperty();

    public final StringProperty dateOutProperty() {
        return dateOut;
    }
    public final void SetDateOut(String dateOut)
    {
        this.dateOut.set(dateOut);
    }
    public final String GetDateOut() {
        return dateOut.get();
    }

}
