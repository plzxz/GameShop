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
public class GameDA {
    
    Connection conn = DBConnection.conn;
    
    public GameDA() {}
    
    public Game getGameData(String id) {
        
        Game game = new Game(0,null,0,null,null,0,null,0);
        
        String sql = "SELECT * FROM product WHERE Game_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                int pId = rs.getInt("product_ID");
                String gID = rs.getString("Game_ID");
                int catID = rs.getInt("Category_ID");
                String name = rs.getString("product_name");
                String des = rs.getString("product_description");
                int quan = rs.getInt("product_quantity");
                String status = rs.getString("product_status");
                double price = rs.getDouble("product_price");
                
                game = new Game(pId, gID, catID, name, des, quan, status, price);
            }
            
            return game;
        
        }catch(SQLException e) {
            e.printStackTrace();
            return game;
        }
        
    }
    
    public void updateGame(Game game) {
        
        String sql = "UPDATE product SET Category_ID= ?, product_name= ?, product_description= ?, product_quantity= ?, product_status= ?, product_price= ? WHERE product_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, game.getCatId());
            stmt.setString(2, game.getName());
            stmt.setString(3, game.getDes());
            stmt.setInt(4, game.getQuantity());
            stmt.setString(5, game.getStatus());
            stmt.setDouble(6, game.getPrice());
            stmt.setInt(7, game.getId());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Update Product successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
}
