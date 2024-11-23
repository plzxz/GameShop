/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import util.*;

/**
 *
 * @author Pete
 */
public class AddGame extends javax.swing.JPanel {
    
    Game game;
    
    /**
     * Creates new form Order
     */
    public AddGame() {
        initComponents();
        updateCB();
        updateTable();
        setTableSelection();
        lockGameData();
    }
    
    public void updateCB() {
        cbCategory.setModel(new ModelCombox().getModel("category", "category_name"));
        cbAddCategory.setModel(new ModelCombox().getModel("category", "category_name"));
        cbDesCategory.setModel(new ModelCombox().getModel("category", "category_name"));
    }
    
    public void updateTable() {
        tblStock.setModel(new GameTable().getModel(null));
    }
    
    public void setGameData() {
        try{
            
            int row = tblStock.getSelectedRow();
            
            String game_id = tblStock.getValueAt(row, 0).toString();
            
            game = new GameDA().getGameData(game_id);
            
            txtDesId.setText(game.getGameId());
            txtDesName.setText(game.getName());
            txtADescription.setText(game.getDes());
            
            if(game.getStatus().equals("Available")) {
                cbDesStatus.setSelectedIndex(0);
            }else {
                cbDesStatus.setSelectedIndex(1);
            }
            txtDesQuantity.setText(game.getQuantity()+"");
            cbDesCategory.setSelectedIndex(game.getCatId());
            txtDesPrice.setText(new DecimalFormat(",###.00").format(game.getPrice()));
            
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Something Wrong", "Warning", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
        }
    }
    
    public void updateGameData() {
        
        game.setGameId(txtDesId.getText());
        game.setName(txtDesName.getText());
        game.setDes(txtAAddDescription.getText());
        game.setCatId(cbDesCategory.getSelectedIndex());
        game.setStatus(cbDesStatus.getSelectedItem().toString());
        game.setQuantity(Integer.parseInt(txtDesQuantity.getText()));
        game.setPrice(Double.parseDouble(txtDesPrice.getText()));
        
        if(new GameDA().updateGame(game)) {
            JOptionPane.showMessageDialog(null, "Update product successfully.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
    
    public void addNewGame() {
        
        String gID = txtAddId.getText();
        String name = txtAddName.getText();
        int cID = cbAddCategory.getSelectedIndex();
        String des = txtAAddDescription.getText();
        int qnt = Integer.parseInt(txtAddQuantity.getText());
        String stat = cbAddStatus.getSelectedItem().toString();
        double price = Double.parseDouble(txtAddPrice.getText());
        
        Game aGame = new Game(0, gID, cID, name, des, qnt, stat, price);
    
        new GameDA().addGame(aGame);
    }
    
    public void clearAdd() {
        
        txtAddId.setText("");
        txtAddName.setText("");
        cbAddCategory.setSelectedIndex(0);
        cbAddStatus.setSelectedIndex(0);
        txtAAddDescription.setText("");
        txtAddQuantity.setText("");
        txtAddPrice.setText("");
        addQA = 0;
    }
    
    private void clearGameData() {
        game = null;
        txtDesId.setText("");
        txtDesName.setText("");
        txtADescription.setText("");
        txtDesQuantity.setText("");
        cbDesStatus.setSelectedIndex(0);
        txtDesPrice.setText("");
        cbDesCategory.setSelectedIndex(0);
    }
    
    private void lockGameData() {
        txtDesId.setEnabled(false);
        txtDesName.setEnabled(false);
        txtADescription.setEnabled(false);
        txtDesQuantity.setEnabled(false);
        cbDesStatus.setEnabled(false);
        txtDesPrice.setEnabled(false);
        cbDesCategory.setEnabled(false);
    }
    
    private void unlockGameData() {
        txtDesId.setEnabled(true);
        txtDesName.setEnabled(true);
        txtADescription.setEnabled(true);
        txtDesQuantity.setEnabled(true);
        cbDesStatus.setEnabled(true);
        txtDesPrice.setEnabled(true);
        cbDesCategory.setEnabled(true);
    }
    
    public void searchGame() {
        ArrayList<Object> data = new ArrayList<>();
        String sql = "";
        
        if(cbCategory.getSelectedItem() != null) {
            sql += " AND category_name= ?";
            data.add(cbCategory.getSelectedItem().toString());
        }
        if(!txtSearch.getText().isBlank()) {
            sql += " AND Game_ID= ?";
            data.add(txtSearch.getText());
        }

        Object[] aData = new Object[]{sql,data};
        
        if(sql.isBlank()) {
            tblStock.setModel(new GameTable().getModel(null));
        }else {
            tblStock.setModel(new GameTable().getModel(aData));
        }

    }
    
    private void setTableSelection() {
        tblStock.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                stockSelectionPerformed(evt);
            }
        });
    }
    
    private boolean isNumber(String str) {
        if(str.matches("\\d+")) {
            return true;
        }else{
            return false;
        }
    }
    
    private boolean isEditFilled() {
        if(!txtDesId.getText().isBlank() && !txtDesName.getText().isBlank() && !txtDesPrice.getText().isBlank() &&!txtDesQuantity.getText().isBlank()) {
            
            if(!isNumber(txtDesPrice.getText())) {
                JOptionPane.showMessageDialog(AddPane, "Price is not number", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if(!isNumber(txtDesQuantity.getText())) {
                JOptionPane.showMessageDialog(AddPane, "Quantity is not number", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if(cbDesCategory.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(AddPane, "Not select Category.", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        }else{
            JOptionPane.showMessageDialog(AddPane, "Form is not filled", "Warning", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private boolean isAddFilled() {
        if(!(txtAddId.getText().isBlank()&&txtAddName.getText().isBlank()&&txtAddQuantity.getText().isBlank()&&txtAddPrice.getText().isBlank()&&txtAddQuantity.getText().isBlank()&cbAddCategory.getSelectedIndex()==0)) {
            
            if(!isNumber(txtAddPrice.getText())) {
                JOptionPane.showMessageDialog(AddPane, "Price is not number", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if(!isNumber(txtAddQuantity.getText())) {
                JOptionPane.showMessageDialog(AddPane, "Price is not number", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true;
        }else {
            JOptionPane.showMessageDialog(AddPane, "Form is not filled", "Warning", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddPane = new javax.swing.JPanel();
        btnAddClear = new javax.swing.JButton();
        lbAddQuantity = new javax.swing.JLabel();
        txtAddQuantity = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        lbAddName = new javax.swing.JLabel();
        txtAddName = new javax.swing.JTextField();
        btnAddPlus = new javax.swing.JButton();
        btnAddMinus = new javax.swing.JButton();
        lbAddId = new javax.swing.JLabel();
        txtAddId = new javax.swing.JTextField();
        cbAddCategory = new javax.swing.JComboBox<>();
        lbAddCategory = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAAddDescription = new javax.swing.JTextArea();
        lbAddDescription = new javax.swing.JLabel();
        lbAdd = new javax.swing.JLabel();
        lbAddName1 = new javax.swing.JLabel();
        txtAddPrice = new javax.swing.JTextField();
        lbAddStatus = new javax.swing.JLabel();
        cbAddStatus = new javax.swing.JComboBox<>();
        DescriptionPane = new javax.swing.JPanel();
        lbDesQuantity = new javax.swing.JLabel();
        txtDesQuantity = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtADescription = new javax.swing.JTextArea();
        lbDesStatus = new javax.swing.JLabel();
        lbDesPrice = new javax.swing.JLabel();
        txtDesPrice = new javax.swing.JTextField();
        lbDesName = new javax.swing.JLabel();
        txtDesName = new javax.swing.JTextField();
        lbDesDescription = new javax.swing.JLabel();
        lbDesCategory = new javax.swing.JLabel();
        cbDesCategory = new javax.swing.JComboBox<>();
        btnDesUpdate = new javax.swing.JButton();
        lbDesId = new javax.swing.JLabel();
        txtDesId = new javax.swing.JTextField();
        cbDesStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        HeaderPane = new javax.swing.JPanel();
        lbPanalName = new javax.swing.JLabel();
        lbSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        cbCategory = new javax.swing.JComboBox<>();
        lbCategory = new javax.swing.JLabel();

        setBackground(new java.awt.Color(239, 239, 239));

        AddPane.setBackground(new java.awt.Color(255, 255, 255));
        AddPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnAddClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddClear.setText("Clear");
        btnAddClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClearActionPerformed(evt);
            }
        });

        lbAddQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbAddQuantity.setText("Quantity : ");

        txtAddQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddQuantityActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lbAddName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbAddName.setText("Name :");

        txtAddName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnAddPlus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddPlus.setText("+");
        btnAddPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPlusActionPerformed(evt);
            }
        });

        btnAddMinus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddMinus.setText("-");
        btnAddMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMinusActionPerformed(evt);
            }
        });

        lbAddId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbAddId.setText("ID :");

        txtAddId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        cbAddCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbAddCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lbAddCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbAddCategory.setText("Category :");

        txtAAddDescription.setColumns(20);
        txtAAddDescription.setRows(5);
        jScrollPane2.setViewportView(txtAAddDescription);

        lbAddDescription.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbAddDescription.setText("Description :");

        lbAdd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbAdd.setText("Add");

        lbAddName1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbAddName1.setText("Price :");

        txtAddPrice.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbAddStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbAddStatus.setText("Status :");

        cbAddStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbAddStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Unavailable" }));

        javax.swing.GroupLayout AddPaneLayout = new javax.swing.GroupLayout(AddPane);
        AddPane.setLayout(AddPaneLayout);
        AddPaneLayout.setHorizontalGroup(
            AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddPaneLayout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(lbAdd)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(AddPaneLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AddPaneLayout.createSequentialGroup()
                        .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAddClear, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AddPaneLayout.createSequentialGroup()
                                .addComponent(lbAddCategory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbAddCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddPaneLayout.createSequentialGroup()
                        .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAddName)
                            .addComponent(lbAddId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAddId)
                            .addComponent(txtAddName))
                        .addGap(76, 76, 76))
                    .addGroup(AddPaneLayout.createSequentialGroup()
                        .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddPaneLayout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbAddDescription)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AddPaneLayout.createSequentialGroup()
                                .addComponent(lbAddQuantity)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtAddQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddPlus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddMinus))
                            .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(AddPaneLayout.createSequentialGroup()
                                    .addComponent(lbAddStatus)
                                    .addGap(18, 18, 18)
                                    .addComponent(cbAddStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddPaneLayout.createSequentialGroup()
                                    .addComponent(lbAddName1)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtAddPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        AddPaneLayout.setVerticalGroup(
            AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddPaneLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lbAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddId))
                .addGap(18, 25, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddName))
                .addGap(18, 25, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAddCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddCategory))
                .addGap(18, 25, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddName1))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAddStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddStatus))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPlus)
                    .addComponent(btnAddMinus)
                    .addComponent(txtAddQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddQuantity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(lbAddDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addGap(198, 198, 198)
                .addComponent(btnAddClear)
                .addGap(18, 18, 18))
        );

        DescriptionPane.setBackground(new java.awt.Color(255, 255, 255));
        DescriptionPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lbDesQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesQuantity.setText("Quantity :");

        txtDesQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtADescription.setColumns(20);
        txtADescription.setRows(5);
        jScrollPane3.setViewportView(txtADescription);

        lbDesStatus.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesStatus.setText("Status :");

        lbDesPrice.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesPrice.setText("Price :");

        txtDesPrice.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbDesName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesName.setText("Name :");

        txtDesName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDesName.setEnabled(false);

        lbDesDescription.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesDescription.setText("Description :");

        lbDesCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbDesCategory.setText("Category :");

        cbDesCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbDesCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btnDesUpdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnDesUpdate.setText("Update");
        btnDesUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesUpdateActionPerformed(evt);
            }
        });

        lbDesId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesId.setText("Id :");

        txtDesId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        cbDesStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbDesStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Available", "Unavailable" }));

        javax.swing.GroupLayout DescriptionPaneLayout = new javax.swing.GroupLayout(DescriptionPane);
        DescriptionPane.setLayout(DescriptionPaneLayout);
        DescriptionPaneLayout.setHorizontalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDesDescription)
                    .addComponent(lbDesName)
                    .addComponent(lbDesId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3)
                            .addComponent(txtDesName, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                        .addComponent(lbDesStatus)
                                        .addGap(25, 25, 25)
                                        .addComponent(cbDesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbDesQuantity)
                                            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(lbDesPrice)))
                                        .addGap(12, 12, 12)
                                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtDesQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                .addComponent(lbDesCategory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cbDesCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDesUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(43, 43, 43))
                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                        .addComponent(txtDesId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        DescriptionPaneLayout.setVerticalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DescriptionPaneLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbDesId))
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbDesCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDesUpdate))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDesStatus)
                            .addComponent(cbDesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDesQuantity)
                            .addComponent(txtDesQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDesPrice)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDesName)
                            .addComponent(txtDesName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbDesCategory))
                        .addGap(18, 18, 18)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDesDescription)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15))
        );

        tblStock.setModel(new javax.swing.table.DefaultTableModel(
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
        tblStock.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblStock.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblStock.getTableHeader().setReorderingAllowed(false);
        tblStock.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(tblStock);

        HeaderPane.setBackground(new java.awt.Color(255, 255, 255));

        lbPanalName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbPanalName.setText("Game");

        lbSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbSearch.setText("Search :");

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSearch.setText("Serach");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoryActionPerformed(evt);
            }
        });

        lbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbCategory.setText("Category :");

        javax.swing.GroupLayout HeaderPaneLayout = new javax.swing.GroupLayout(HeaderPane);
        HeaderPane.setLayout(HeaderPaneLayout);
        HeaderPaneLayout.setHorizontalGroup(
            HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPaneLayout.createSequentialGroup()
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPaneLayout.createSequentialGroup()
                        .addGap(592, 592, 592)
                        .addComponent(lbPanalName))
                    .addGroup(HeaderPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbCategory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSearch)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        HeaderPaneLayout.setVerticalGroup(
            HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbPanalName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCategory))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(DescriptionPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(HeaderPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DescriptionPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(AddPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        searchGame();
        clearGameData();
        lockGameData();
    }//GEN-LAST:event_cbCategoryActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        searchGame();
        clearGameData();
        lockGameData();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchGame();
        clearGameData();
        lockGameData();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDesUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesUpdateActionPerformed
        if(isEditFilled()) {
            updateGameData();
            searchGame();
        }
    }//GEN-LAST:event_btnDesUpdateActionPerformed

    private void btnAddClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClearActionPerformed
        clearAdd();
    }//GEN-LAST:event_btnAddClearActionPerformed

    int addQA = 0;
    private void btnAddPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPlusActionPerformed
        if(txtAddQuantity.getText().isBlank()) {
            txtAddQuantity.setText("0");
        }
        if(isNumber(txtAddQuantity.getText())) {
            int cartQ = Integer.parseInt(txtAddQuantity.getText()) + 1;
            txtAddQuantity.setText(cartQ+"");
            addQA = cartQ;
        }else {
            txtAddQuantity.setText(addQA+"");
        }
    }//GEN-LAST:event_btnAddPlusActionPerformed

    private void btnAddMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMinusActionPerformed
        if(txtAddQuantity.getText().isBlank()) {
            txtAddQuantity.setText("0");
        }
        if(isNumber(txtAddQuantity.getText())) {
            int cartQ = Integer.parseInt(txtAddQuantity.getText()) - 1;
            txtAddQuantity.setText(cartQ+"");
            addQA = cartQ;
        }else {
            txtAddQuantity.setText(addQA+"");
        }
    }//GEN-LAST:event_btnAddMinusActionPerformed

    private void txtAddQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddQuantityActionPerformed
        if(txtAddQuantity.getText().isBlank()) {
            txtAddQuantity.setText("0");
        }
        if(isNumber(txtAddQuantity.getText())) {
            int cartQ = Integer.parseInt(txtAddQuantity.getText());
            txtAddQuantity.setText(cartQ+"");
            addQA = cartQ;
        }else {
            txtAddQuantity.setText(addQA+"");
        }
    }//GEN-LAST:event_txtAddQuantityActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if(isAddFilled()) {
            try {
                addNewGame();
                clearAdd();
                updateTable();

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_btnAddActionPerformed

    
    private void stockSelectionPerformed(ListSelectionEvent evt) {
        try{
        if (!evt.getValueIsAdjusting()) {
            int selectedRow = tblStock.getSelectedRow();
            if (selectedRow != -1) {
                setGameData();
                unlockGameData();
            }
        }
        }catch(IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddPane;
    private javax.swing.JPanel DescriptionPane;
    private javax.swing.JPanel HeaderPane;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddClear;
    private javax.swing.JButton btnAddMinus;
    private javax.swing.JButton btnAddPlus;
    private javax.swing.JButton btnDesUpdate;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbAddCategory;
    private javax.swing.JComboBox<String> cbAddStatus;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JComboBox<String> cbDesCategory;
    private javax.swing.JComboBox<String> cbDesStatus;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbAddCategory;
    private javax.swing.JLabel lbAddDescription;
    private javax.swing.JLabel lbAddId;
    private javax.swing.JLabel lbAddName;
    private javax.swing.JLabel lbAddName1;
    private javax.swing.JLabel lbAddQuantity;
    private javax.swing.JLabel lbAddStatus;
    private javax.swing.JLabel lbCategory;
    private javax.swing.JLabel lbDesCategory;
    private javax.swing.JLabel lbDesDescription;
    private javax.swing.JLabel lbDesId;
    private javax.swing.JLabel lbDesName;
    private javax.swing.JLabel lbDesPrice;
    private javax.swing.JLabel lbDesQuantity;
    private javax.swing.JLabel lbDesStatus;
    private javax.swing.JLabel lbPanalName;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JTable tblStock;
    private javax.swing.JTextArea txtAAddDescription;
    private javax.swing.JTextArea txtADescription;
    private javax.swing.JTextField txtAddId;
    private javax.swing.JTextField txtAddName;
    private javax.swing.JTextField txtAddPrice;
    private javax.swing.JTextField txtAddQuantity;
    private javax.swing.JTextField txtDesId;
    private javax.swing.JTextField txtDesName;
    private javax.swing.JTextField txtDesPrice;
    private javax.swing.JTextField txtDesQuantity;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
