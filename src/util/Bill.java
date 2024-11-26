/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author SP_ONE
 */
public class Bill {
    
        
    private int id;
    private Member mem;
    private Employee emp;
    private ArrayList<Game> game;
    private ArrayList<Integer> amount;
    private Date date;
    private double total;
    private double discount;
    private double pay;
    private double change;
    
    public Bill() {
    
    }
    
    
    public Bill(Member member, Employee employee, ArrayList<Game> game, ArrayList<Integer> amount, Date date, double total, double discount) {
        setMember(member);
        setEmployee(employee);
        setGame(game);
        setAmount(amount);
        setDate(date);
        setTotal(total);
        setDiscount(discount);
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Member getMember() {
        return mem;
    }

    public void setMember(Member mem) {
        this.mem = mem;
    }
    
    public Employee getEmployee() {
        return emp;
    }
    
    public void setEmployee(Employee emp) {
        this.emp = emp;
    }

    public ArrayList<Game> getGame() {
        return game;
    }

    public void setGame(ArrayList<Game> game) {
        this.game = game;
    }
    
    public ArrayList<Integer> getAmount() {
        return amount;
    }

    public void setAmount(ArrayList<Integer> amount) {
        this.amount = amount;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
}
