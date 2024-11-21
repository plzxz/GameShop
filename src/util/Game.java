/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author SP_ONE
 */
public class Game {
    
    private String id;
    private int catId;
    private String name;
    private String des;
    private int quantity;
    private String status;
    private double price;
    
    
        
    public Game(String id, int catId, String name, String des, int quantity, String status, double price) {
        setId(id);
        setCatId(catId);
        setName(name);
        setDes(des);
        setQuantity(quantity);
        setStatus(status);
        setPrice(price);
    }
    
    public void setGame(String id, int catId, String name, String des, int quantity, String status, double price) {
        setId(id);
        setCatId(catId);
        setName(name);
        setDes(des);
        setQuantity(quantity);
        setStatus(status);
        setPrice(price);
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    

}
