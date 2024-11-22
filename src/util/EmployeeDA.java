/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author SP_ONE
 */
public class EmployeeDA {
    
    private Connection conn = DBConnection.conn;
    
    public EmployeeDA() {}
    
    public Employee getData(int id) {
        
        Employee employee = new Employee();
        
        String sql = "SELECT * FROM employee WHERE EMP_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                int eID = rs.getInt("EMP_ID");
                String rank = rs.getString("Rank");
                String fName = rs.getString("First_Name");
                String lName = rs.getString("Last_Name");
                java.sql.Date hireDate = rs.getDate("Hire_Date");
                java.sql.Date birthDate = rs.getDate("Birth_Date");
                String tel = rs.getString("Emp_Tel");
                String email = rs.getString("Emp_Email");
                String address = rs.getString("Address");
                String city = rs.getString("City");
                int zipCode = rs.getInt("Zip_Code");
                String pass = rs.getString("Pass");
                
                
                employee = new Employee(eID, rank, fName, lName, hireDate, birthDate, tel, email, address, city, zipCode, pass);
            }
            
            return employee;
        
        }catch(SQLException e) {
            e.printStackTrace();
            return employee;
        }
        
    }
    
    public void addMember(Employee employee) {
            
            
            String sql = "INSERT INTO employee "+
                    "(Rank, First_Name, Last_Name, Hire_Date, Birth_Date, Emp_Tel, Emp_Email, Address, City, Zip_Code, Pass) "+
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try(PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            
                stmt.setString(1, employee.getRank());
                stmt.setString(2, employee.getfName());
                stmt.setString(3, employee.getlName());
                stmt.setDate(4, employee.getHireDate());
                stmt.setDate(5, employee.getBirthDate());
                stmt.setString(6, employee.getTel());
                stmt.setString(7, employee.getEmail());
                stmt.setString(8, employee.getAddress());
                stmt.setString(9, employee.getCity());
                stmt.setInt(10, employee.getZipCode());
                stmt.setString(11, employee.getPass());
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Add Employee successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(SQLException e) {
                JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
        }
    
    public void updateEmployee(Employee employee) {
        
        String sql = "UPDATE employee SET Rank= ?, First_Name= ?, Last_Name= ?, Emp_Tel= ?, Emp_Email= ?, Address= ?, City= ?, Zip_Code= ?, Pass= ?"+
                " WHERE EMP_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, employee.getRank());
            stmt.setString(2, employee.getfName());
            stmt.setString(3, employee.getlName());
            stmt.setString(4, employee.getTel());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getAddress());
            stmt.setString(7, employee.getCity());
            stmt.setInt(8, employee.getZipCode());
            stmt.setString(9, employee.getPass());
            stmt.setInt(10, employee.getId());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Update Employee successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
        
    
}
