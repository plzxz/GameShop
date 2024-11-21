/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.hsqldb.result.ResultMetaData;

/**
 *
 * @author SP_ONE
 */
public class GameTable { //copy for another table
    
    private Connection conn = DBConnection.conn; //change
    
    public DefaultTableModel getModel(Object[] data) {
    
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        ArrayList<Object> aData = new ArrayList<>();
        
        String sql = "SELECT Game_ID AS ID, category_name AS Category, product_name AS Name, product_quantity AS Quantity, product_status AS Status FROM category INNER JOIN product ON category.category_ID = product.Category_ID WHERE 1=1 ";
        
        if(data != null) {
            sql += data[0];
            aData = (ArrayList<Object>)data[1];
        }
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if(data != null) {
                for (int i = 0; i < aData.size(); i++) {
                    stmt.setObject(i + 1, aData.get(i));
                }
            }
            
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            
            int columCount = meta.getColumnCount();
            for(int i = 1; i <= columCount; i++) {
                model.addColumn(meta.getColumnLabel(i));
            }

            while(rs.next()) {
                Object[] row = new Object[columCount];
                for(int i = 1; i <= columCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
            
            return model;
        }catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
}
