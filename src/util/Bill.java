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
    private Date date;
    private double total;
    
    
    public Bill(Member member, Employee employee, ArrayList<Game> game, Date date, double total) {
        setMember(member);
        setEmployee(employee);
        setGame(game);
        setDate(date);
        setTotal(total);
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

    
    
}
