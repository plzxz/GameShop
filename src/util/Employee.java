/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Date;

/**
 *
 * @author SP_ONE
 */
public class Employee {
    
    private int id;
    private String rank;
    private String fName;
    private String lName;
    private Date hireDate;
    private Date birthDate;
    private String tel;
    private String email;
    private String address;
    private String city;
    private int zipCode;
    private String pass;
    
    
    public Employee() {
    
    }
    
    public Employee(int id, String rank, String fName, String lName, Date hireDate, Date birthDate, String tel, String email, String address, String city,int zip, String pass) {
        setId(id);
        setRank(rank);
        setfName(fName);
        setlName(lName);
        setHireDate(hireDate);
        setBirthDate(birthDate);
        setTel(tel);
        setEmail(email);
        setCity(city);
        setAddress(address);
        setZipCode(zip);
        setPass(pass);
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
