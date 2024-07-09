package com.example.proiectmip;

import javafx.fxml.FXML;

import java.time.LocalDate;


public class User {
    private String lastName;
    private String firstName;
    private LocalDate birthdate;
    private String email;
    private String password;
    private Integer id;
    public void SetId(Integer id)
    {
        this.id = id;
    }
    public Integer GetId()
    {
        return id;
    }

    public String GetLastName()
    {
        return lastName;
    }

    public String GetFirstName()
    {
        return firstName;
    }

    public LocalDate GetBirthdate()
    {
        return birthdate;
    }

    public String GetEmail()
    {
        return email;
    }

    public String GetPassword()
    {
        return password;
    }

    public void SetLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void SetFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void SetBirthdate(LocalDate birthdate)
    {
        this.birthdate = birthdate;
    }

    public void SetEmail(String email)
    {
        this.email = email;
    }

    public void SetPassword(String password)
    {
        this.password = password;
    }

    public User()
    {
    }

}
