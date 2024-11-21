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
import javax.swing.table.DefaultTableModel;
import org.hsqldb.result.ResultMetaData;

/**
 *
 * @author SP_ONE
 */
public class EmployeeTable { //copy for another table
    
    private Connection conn = DBConnection.conn; //change
    
    public DefaultTableModel getModel() {
    
        DefaultTableModel model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        String sql = "SELECT EMP_ID AS ID, Rank, First_Name AS [First Name], Last_Name AS [Last Name], Emp_Tel AS Tel, Emp_Email AS Email FROM [employee]";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
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
