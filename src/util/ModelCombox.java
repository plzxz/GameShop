/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author SP_ONE
 */
public class ModelCombox {
    
    private Connection conn = DBConnection.conn; //change
    
    public DefaultComboBoxModel getModel(String table, String field) {
    
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        String sql = "SELECT * FROM " + table;
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            
                model.addElement(null);
            while(rs.next()) {
                model.addElement(rs.getString(field));
            }
            
            return model;
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
