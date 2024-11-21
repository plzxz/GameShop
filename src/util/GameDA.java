/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author SP_ONE
 */
public class GameDA {
    
    Connection conn = DBConnection.conn;
    
    public GameDA() {}
    
    public Game getGameData(String id) {
        
        Game game = new Game(null,0,null,null,0,null,0);
        
        String sql = "SELECT * FROM product WHERE Game_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                String gID = rs.getString("Game_ID");
                int catID = rs.getInt("Category_ID");
                String name = rs.getString("product_name");
                String des = rs.getString("product_description");
                int quan = rs.getInt("product_quantity");
                String status = rs.getString("product_status");
                double price = rs.getDouble("product_price");
                
                game = new Game(gID, catID, name, des, quan, status, price);
            }
            
            return game;
        
        }catch(SQLException e) {
            e.printStackTrace();
            return game;
        }
        
    }
    
    public void updateGame(Game game) {
    
    }
    
}
