/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author SP_ONE
 */
public class Member {

    private int id;
    private String fName;
    private String lName;
    private String contact;
    
    
    public Member(int id, String fName, String lName, String contact) {
        setId(id);
        setFname(fName);
        setLname(lName);
        setContact(contact);
    }
    
    public void setMember(int id, String fName, String lName, String contact) {
        setId(id);
        setFname(fName);
        setLname(lName);
        setContact(contact);
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fName;
    }

    public void setFname(String Fname) {
        this.fName = Fname;
    }

    public String getLname() {
        return lName;
    }

    public void setLname(String Lname) {
        this.lName = Lname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    
}
