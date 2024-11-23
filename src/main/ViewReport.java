/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import util.*;

/**
 *
 * @author Pete
 */
public class ViewReport extends javax.swing.JPanel {

    /**
     * Creates new form Order
     */
    public ViewReport() {
        initComponents();
        UpdateTotal();
    }
    
    public void updateTable() {
        tblReport.setModel(new ReportTable().getModel(null));
        cbCategory.setModel(new ModelCombox().getModel("category", "category_name"));
        UpdateTotal();
    }
    
    public void updateCB() {
        cbCategory.setModel(new ModelCombox().getModel("category", "category_name"));
    }
    
    public void UpdateTotal() {
        
        Set<String> countId = new HashSet<>();
        double total = 0;
        double dis = 0;
        int rowCount = tblReport.getRowCount();
        txtRowCount.setText(rowCount+"");
        
        for(int i = 0; i<rowCount ; i++) {
            
            String id = tblReport.getValueAt(i, 0).toString();
            double countTotal = Double.parseDouble(tblReport.getValueAt(i, 9).toString());
            double countDis = Double.parseDouble(tblReport.getValueAt(i, 8).toString());
            
            if(!countId.contains(id)) {
                countId.add(id);
                total += countTotal;
                dis += countDis;
            }
        }
        txtTotal.setText(new DecimalFormat(",###.00").format(total));
        txtDiscount.setText(new DecimalFormat(",###.00").format(dis));
    }
    
    public void checkSearch() {
        String sql = "";
        ArrayList<Object> data = new ArrayList<>();
        
        if(cbCategory.getSelectedItem() != null) {
            sql += " AND category_name= ?";
            data.add(cbCategory.getSelectedItem().toString());
        }
        if(!txtGameID.getText().isBlank()) {
            sql += " AND Game_ID= ?";
            data.add(txtGameID.getText());
        }
        if(!txtEmployeeID.getText().isBlank()) {
            sql += " AND Emp_ID= ?";
            data.add(txtEmployeeID.getText());
        }
        if(!txtMemberID.getText().isBlank()) {
            sql += " AND Customer_ID= ?";
            data.add(txtMemberID.getText());
        }
        if(datePicker.getDate() != null) {
            sql += " AND Dill_Date= ?";
            data.add(java.sql.Date.valueOf(datePicker.getDate()));
        }
        
        Object[] aData = new Object[]{sql,data};
        
        if(sql.isBlank()) {
            tblReport.setModel(new ReportTable().getModel(null));
        }else {
            tblReport.setModel(new ReportTable().getModel(aData));
        }
        UpdateTotal();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DescriptionPane = new javax.swing.JPanel();
        lbRow = new javax.swing.JLabel();
        txtRowCount = new javax.swing.JTextField();
        lbTotal = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        lbDiscount = new javax.swing.JLabel();
        txtDiscount = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReport = new javax.swing.JTable();
        HeaderPane = new javax.swing.JPanel();
        lbPanalName = new javax.swing.JLabel();
        lbGameID = new javax.swing.JLabel();
        txtGameID = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        cbCategory = new javax.swing.JComboBox<>();
        lbCategory = new javax.swing.JLabel();
        lbGameName = new javax.swing.JLabel();
        txtEmployeeID = new javax.swing.JTextField();
        lbMemberID = new javax.swing.JLabel();
        txtMemberID = new javax.swing.JTextField();
        datePicker = new com.github.lgooddatepicker.components.DatePicker();
        lbDate = new javax.swing.JLabel();
        btnPrint = new javax.swing.JButton();

        setBackground(new java.awt.Color(239, 239, 239));

        DescriptionPane.setBackground(new java.awt.Color(255, 255, 255));
        DescriptionPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lbRow.setText("row :");
        lbRow.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtRowCount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtRowCount.setEditable(false);

        lbTotal.setText("Total : ");
        lbTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtTotal.setEditable(false);

        lbDiscount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDiscount.setText("Discount  : ");

        txtDiscount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDiscount.setEditable(false);

        javax.swing.GroupLayout DescriptionPaneLayout = new javax.swing.GroupLayout(DescriptionPane);
        DescriptionPane.setLayout(DescriptionPaneLayout);
        DescriptionPaneLayout.setHorizontalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lbRow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtRowCount, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbDiscount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbTotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        DescriptionPaneLayout.setVerticalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DescriptionPaneLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbTotal)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDiscount)
                            .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbRow)
                        .addComponent(txtRowCount, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9))
        );

        tblReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblReport.setModel(new ReportTable().getModel(null));
        tblReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblReport.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblReport.getTableHeader().setReorderingAllowed(false);
        tblReport.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(tblReport);

        HeaderPane.setBackground(new java.awt.Color(255, 255, 255));

        lbPanalName.setText("Report");
        lbPanalName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lbGameID.setText("Game ID :");
        lbGameID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtGameID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGameID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGameIDActionPerformed(evt);
            }
        });

        btnSearch.setText("Serach");
        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbCategory.setModel(new ModelCombox().getModel("category", "category_name"));
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        lbCategory.setText("Category :");
        lbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbGameName.setText("Employee ID : ");
        lbGameName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtEmployeeID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmployeeID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmployeeIDActionPerformed(evt);
            }
        });

        lbMemberID.setText("Member ID :");
        lbMemberID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtMemberID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMemberID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMemberIDActionPerformed(evt);
            }
        });

        lbDate.setText("Date :");
        lbDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        btnPrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPrint.setText("Print receipt");
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout HeaderPaneLayout = new javax.swing.GroupLayout(HeaderPane);
        HeaderPane.setLayout(HeaderPaneLayout);
        HeaderPaneLayout.setHorizontalGroup(
            HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbPanalName)
                        .addGap(502, 502, 502))
                    .addGroup(HeaderPaneLayout.createSequentialGroup()
                        .addComponent(lbCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbGameID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGameID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbGameName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbMemberID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)))
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        HeaderPaneLayout.setVerticalGroup(
            HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPaneLayout.createSequentialGroup()
                        .addComponent(lbPanalName)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPaneLayout.createSequentialGroup()
                        .addComponent(btnPrint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbGameID)
                    .addComponent(txtGameID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCategory)
                    .addComponent(lbGameName)
                    .addComponent(txtEmployeeID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbMemberID)
                    .addComponent(txtMemberID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDate))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(HeaderPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(DescriptionPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DescriptionPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        checkSearch();
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void txtGameIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGameIDActionPerformed
        checkSearch();
    }//GEN-LAST:event_txtGameIDActionPerformed

    private void txtEmployeeIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmployeeIDActionPerformed
        checkSearch();
    }//GEN-LAST:event_txtEmployeeIDActionPerformed

    private void txtMemberIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMemberIDActionPerformed
        checkSearch();
    }//GEN-LAST:event_txtMemberIDActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        checkSearch();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
        if(tblReport.getSelectedRow() != -1) {
            new PrintReceipt(new BillDA().getBillData( Integer.parseInt(tblReport.getValueAt(tblReport.getSelectedRow(), 0).toString())), true).setLocationRelativeTo(this);
        }
    }//GEN-LAST:event_btnPrintActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DescriptionPane;
    private javax.swing.JPanel HeaderPane;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbCategory;
    private com.github.lgooddatepicker.components.DatePicker datePicker;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCategory;
    private javax.swing.JLabel lbDate;
    private javax.swing.JLabel lbDiscount;
    private javax.swing.JLabel lbGameID;
    private javax.swing.JLabel lbGameName;
    private javax.swing.JLabel lbMemberID;
    private javax.swing.JLabel lbPanalName;
    private javax.swing.JLabel lbRow;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JTable tblReport;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtEmployeeID;
    private javax.swing.JTextField txtGameID;
    private javax.swing.JTextField txtMemberID;
    private javax.swing.JTextField txtRowCount;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
