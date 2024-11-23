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
    
    private Connection conn = DBConnection.conn;
    
    public GameDA() {}
    
    public Game getGameData(String id) {
        
        Game game = new Game();
        
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
    
    public Game getGame(int id) {
        
        Game game = new Game();
        
        String sql = "SELECT * FROM product WHERE product_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
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
    
    public boolean updateGame(Game game) {
        
        String sql = "UPDATE product SET Game_ID= ?, Category_ID= ?, product_name= ?, product_description= ?, product_quantity= ?, product_status= ?, product_price= ? WHERE product_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, game.getGameId());
            stmt.setInt(2, game.getCatId());
            stmt.setString(3, game.getName());
            stmt.setString(4, game.getDes());
            stmt.setInt(5, game.getQuantity());
            stmt.setString(6, game.getStatus());
            stmt.setDouble(7, game.getPrice());
            stmt.setInt(8, game.getId());
            
            stmt.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Update Product successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
    
        public void addGame(Game game) {
            
            
            String sql = "INSERT INTO Product (Game_ID, Category_ID, product_name, product_description, product_quantity, product_status, product_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try(PreparedStatement stmt = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            
                stmt.setString(1, game.getGameId());
                stmt.setInt(2, game.getCatId());
                stmt.setString(3, game.getName());
                stmt.setString(4, game.getDes());
                stmt.setInt(5, game.getQuantity());
                stmt.setString(6, game.getStatus());
                stmt.setDouble(7, game.getPrice());
                
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Add Product successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
                
            }catch(SQLException e) {
                JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
            
        }
    
}
