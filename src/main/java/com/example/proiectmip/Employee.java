package com.example.proiectmip;

public class Employee extends User{
    private double salary;
    private String hiringDate;
    private int code;

    public double GetSalary()
    {
        return salary;
    }

    public String GetHiringDate()
    {
        return hiringDate;
    }

    public int GetCode()
    {
        return code;
    }

    public void SetSalary(double salary)
    {
        this.salary = salary;
    }

    public void SetHiringDate(String date)
    {
        hiringDate = date;
    }

    public void SetCode(int code)
    {
        this.code = code;
    }

    public Employee()
    {

    }
}
