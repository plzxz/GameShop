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
public class MemberDA {
    
    private Connection conn = DBConnection.conn;
    
    public MemberDA() {}
    
    public Member getData(int id) {
        
        Member member = new Member();
        
        String sql = "SELECT * FROM customer WHERE customer_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                int mID = rs.getInt("customer_ID");
                String fname = rs.getString("Cus_Fname");
                String lname = rs.getString("Cus_Lname");
                String cont = rs.getString("Cus_contact");
                
                member = new Member(mID, fname, lname, cont);
            }
            
            return member;
        
        }catch(SQLException e) {
            e.printStackTrace();
            return member;
        }
        
    }
    
    public void addMember(Member member) {
            
            
            String sql = "INSERT INTO customer (Cus_Fname, Cus_Lname, Cus_contact) VALUES (?, ?, ?)";
            
            try(PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            
                stmt.setString(1, member.getFname());
                stmt.setString(2, member.getLname());
                stmt.setString(3, member.getContact());
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Add Member successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(SQLException e) {
                JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
        }
    
//    public void updateGame(Game game) {
//        
//        String sql = "UPDATE product SET Game_ID= ?, Category_ID= ?, product_name= ?, product_description= ?, product_quantity= ?, product_status= ?, product_price= ? WHERE product_ID= ?";
//        
//        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
//            
//            stmt.setString(1, game.getGameId());
//            stmt.setInt(2, game.getCatId());
//            stmt.setString(3, game.getName());
//            stmt.setString(4, game.getDes());
//            stmt.setInt(5, game.getQuantity());
//            stmt.setString(6, game.getStatus());
//            stmt.setDouble(7, game.getPrice());
//            stmt.setInt(8, game.getId());
//            
//            stmt.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Update Product successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
//        
//        }catch(SQLException e) {
//            JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
//    }
    
        
    
}
