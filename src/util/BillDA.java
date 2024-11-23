/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author SP_ONE
 */
public class BillDA {
    
    private Connection conn = DBConnection.conn;
    
    public BillDA() {}
    
    public Bill getBillData(int id) {
        
        Bill aBill = new Bill();
        ArrayList<Game> aGame = new ArrayList<>();
        ArrayList<Integer> amount = new ArrayList<>();
        
        String sql = "SELECT Bill.*, Amount FROM Bill INNER JOIN Bill_detail ON Bill.Bill_ID = Bill_detail.Bill_ID WHERE Bill_ID= ?";
        
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                
                Employee emp = new EmployeeDA().getData(rs.getInt("EMP_ID"));
                Member mem = new MemberDA().getData(rs.getInt("C_ID"));
                java.sql.Date bilDate = rs.getDate("Dill_Date");
                double total = rs.getDouble("Total_price");
                double discount = rs.getDouble("Discount");
                
                do {
                    
                    aGame.add(new GameDA().getGame(rs.getInt("Product_ID")));
                    amount.add(rs.getInt("amount"));

                }while(rs.next());

                aBill = new Bill(mem, emp, aGame, amount, bilDate, total, discount);
            }  

            return aBill;
        
        }catch(SQLException e) {
            JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return aBill;
        }
        
    }
    
    public boolean addBill(Bill aBill) {

        try {
            
            conn.setAutoCommit(false);
            
            int orderID = 0;

            String billSql = "INSERT INTO Bill (EMP_ID, C_ID, Dill_Date, Total_price, Discount) VALUES (?, ?, ?, ?, ?)";

            try(PreparedStatement oStmt = conn.prepareStatement(billSql,PreparedStatement.RETURN_GENERATED_KEYS)) {

                oStmt.setInt(1, aBill.getEmployee().getId());
                oStmt.setInt(2, aBill.getMember().getId());
                oStmt.setDate(3, aBill.getDate());
                oStmt.setDouble(4, aBill.getTotal());
                oStmt.setDouble(5, aBill.getDiscount());
                
                oStmt.executeUpdate();
                
                try (ResultSet generatedKeys = oStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        orderID = generatedKeys.getInt(1);
                        System.out.println(orderID);
                    }
                }
             }
            
            
            String detailSql = "INSERT INTO Bill_detail (Bill_ID, Product_ID, Amount) VALUES (?, ?, ?)";
            
            try(PreparedStatement dStmt = conn.prepareStatement(detailSql)) {
                
                for(int i=0; i< aBill.getGame().size(); i++) {
                    
                    dStmt.setInt(1, orderID);
                    dStmt.setInt(2, aBill.getGame().get(i).getId());
                    dStmt.setInt(3, aBill.getAmount().get(i));
                    
                    dStmt.addBatch();
                }
                
                int[] results = dStmt.executeBatch();
                System.out.println("execute : "+ results);
            }
            
            for(int i=0; i< aBill.getGame().size(); i++) {
                new GameDA().updateGame(aBill.getGame().get(i));
            }      
                conn.commit();
            
            JOptionPane.showMessageDialog(null, "Add Order successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return true;
        
        }catch(SQLException e) {
            try{
                JOptionPane.showMessageDialog(null, "Transaction failed. Roll back.", "Warning", JOptionPane.ERROR_MESSAGE);
                conn.rollback();
            }catch(SQLException rollBackEx) {
                JOptionPane.showMessageDialog(null, "Failed to roll back transaction.", "Warning", JOptionPane.ERROR_MESSAGE);
                rollBackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Something wrong.", "Warning", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch(SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
    
}
