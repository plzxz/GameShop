/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import util.*;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Pete
 */
public class AddOrder extends javax.swing.JPanel {

    Game game;
    int EmpID = 0;
    double aTotal = 0;
    double discount = 0;
    
    Bill aBill;
    
    /**
     * Creates new form Order
     */
    public AddOrder() {
        initComponents();
        setTableSelection();
    }
    
    public void setGameData() {
        try{
            
            int row = tblStock.getSelectedRow();
            
            String game_id = tblStock.getValueAt(row, 0).toString();
            
            game = new GameDA().getGameData(game_id);
            
            txtDesId.setText(game.getGameId());
            txtDesName.setText(game.getName());
            txtADescription.setText(game.getDes());
            txtDesStatus.setText(game.getStatus());
            txtDesQuantity.setText(game.getQuantity()+"");
            txtDesPrice.setText(new DecimalFormat(",###.00").format(game.getPrice()));
            txtCartQuantity.setText("1");
            
            checkQuantity();
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Something Wrong", "Warning", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
        }
    }
    
    public void updateTable() {
        tblStock.setModel(new GameTable().getModel(null));
    }
    
    public void updateSearch() {
        searchGame();
    }
    
    public void setEmpID(int id) {
        this.EmpID = id;
    }
    
    public void updateCB() {
        cbCategory.setModel(new ModelCombox().getModel("category","category_name"));
    }
    
    public void clearGameData() {
        txtDesId.setText("");
        txtDesName.setText("");
        txtADescription.setText("");
        txtDesStatus.setText("");
        txtDesQuantity.setText("");
        txtDesPrice.setText("");
        txtCartQuantity.setText("");
        cartQA = 0;
    }
    
    public void checkQuantity() {
        String gStat = game.getStatus();
        if(gStat.equalsIgnoreCase("Unavailable")) {
            txtCartQuantity.setEnabled(false);
            btnCartAdd.setEnabled(false);
            btnCartPlus.setEnabled(false);
            btnCartMinus.setEnabled(false);
            txtCartQuantity.setText("");
            cartQA = 0;
        }else {
            txtCartQuantity.setEnabled(true);
            btnCartAdd.setEnabled(true);
            btnCartPlus.setEnabled(true);
            btnCartMinus.setEnabled(true);
        }
    }
    
    public void unlockQuantity() {
        txtCartQuantity.setEnabled(true);
        btnCartAdd.setEnabled(true);
        btnCartPlus.setEnabled(true);
        btnCartMinus.setEnabled(true);
    }
    
    
    private void searchGame() {
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
    
    private void clearCart() {
        ((DefaultTableModel)tblCart.getModel()).setRowCount(0);
        aTotal = 0;
        discount = 0;
        txtCartTotal.setText("");
        lbCartDisCount.setText("");
    }
    
    private void addCart() {
    
        
        String gID = game.getGameId();
        String gName = game.getName();
        int gQ = Integer.parseInt(txtCartQuantity.getText());
        String gPrice = game.getPrice()+"";
        
        
        ((DefaultTableModel)tblCart.getModel()).addRow(new Object[] {gID, gName, gQ, gPrice});
        setTotal();
    }
    
    private void setTotal() {
        
        int rowCount = tblCart.getRowCount();
        
        aTotal = 0;
        for(int i = 0; i<rowCount; i++) {
            int aQauntity = Integer.parseInt(tblCart.getValueAt(i, 2).toString());
            double aPrice = Double.parseDouble(tblCart.getValueAt(i, 3).toString());
            
            aTotal += aQauntity * aPrice;
        }
        
        discount = 0;
        
        if(!txtCartMember.getText().equals("1") && !txtCartMember.getText().isBlank()) {
            discount = aTotal * 0.15;
            lbCartDisCount.setText("  -"+new DecimalFormat(",###.00").format(discount));
            aTotal -= discount;
        }else {
            lbCartDisCount.setText("");
        }
  
        txtCartTotal.setText(new DecimalFormat(",###.00").format(aTotal));
    }
    
    
    private void processOrder() {
        
        if(txtCartMember.getText().isBlank()) {
            txtCartMember.setText("1");
        }
        
        int rowCount = tblCart.getRowCount();

        Member mem = new MemberDA().getData(Integer.parseInt(txtCartMember.getText()));
        Employee emm = new EmployeeDA().getData(EmpID);
        ArrayList<Game> aGame = new ArrayList<>();
        java.sql.Date date = java.sql.Date.valueOf(java.time.LocalDate.now());
        double billTotal = aTotal;

        ArrayList<Integer> amount = new ArrayList<>();
        
        
        for(int i = 0; i < rowCount; i++) {
            
            aGame.add(new GameDA().getGameData(tblCart.getValueAt(i, 0).toString()));
            amount.add(Integer.valueOf(tblCart.getValueAt(i, 2).toString()));
            
            int cal = aGame.get(i).getQuantity() - amount.get(i);
            aGame.get(i).setQuantity(cal);
            
        }
        
        aBill = new Bill(mem, emm, aGame, amount, date, aTotal, discount);
        

        new BillDA().addBill(aBill);
    }
    
    
    
    
    private void setTableSelection() {
        tblStock.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                stockSelectionPerformed(evt);
            }
        });
        
//        tblCart.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent evt) {
//                cartSelectionPerformed(evt);
//            }
//        });
    }
    
    private boolean isNumber(String str) {
        if(str.matches("\\d+")) {
            return true;
        }else{
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

        CartPane = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCart = new javax.swing.JTable();
        lbCartQuantity = new javax.swing.JLabel();
        txtCartQuantity = new javax.swing.JTextField();
        btnCartPlus = new javax.swing.JButton();
        btnCartMinus = new javax.swing.JButton();
        btnCartAdd = new javax.swing.JButton();
        btnCartRemove = new javax.swing.JButton();
        lbCartMember = new javax.swing.JLabel();
        txtCartMember = new javax.swing.JTextField();
        btnCartMember = new javax.swing.JButton();
        btnCartPrint = new javax.swing.JButton();
        btnCartProcess = new javax.swing.JButton();
        btnCartClear = new javax.swing.JButton();
        txtCartTotal = new javax.swing.JTextField();
        lbCartTotal = new javax.swing.JLabel();
        lbCartMemCheck = new javax.swing.JLabel();
        lbCartDisCount = new javax.swing.JLabel();
        DescriptionPane = new javax.swing.JPanel();
        lbDesName = new javax.swing.JLabel();
        txtDesName = new javax.swing.JTextField();
        lbDesDescription = new javax.swing.JLabel();
        lbDesQuantity = new javax.swing.JLabel();
        txtDesQuantity = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtADescription = new javax.swing.JTextArea();
        lbDesStatus = new javax.swing.JLabel();
        txtDesStatus = new javax.swing.JTextField();
        lbDesPrice = new javax.swing.JLabel();
        txtDesPrice = new javax.swing.JTextField();
        lbDesId = new javax.swing.JLabel();
        txtDesId = new javax.swing.JTextField();
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

        CartPane.setBackground(new java.awt.Color(255, 255, 255));
        CartPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tblCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Game id", "Game Name", "Quantity", "price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCart.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCart.getTableHeader().setReorderingAllowed(false);
        tblCart.setAutoCreateRowSorter(true);
        jScrollPane3.setViewportView(tblCart);

        lbCartQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbCartQuantity.setText("Quantity :");

        txtCartQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtCartQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCartQuantityActionPerformed(evt);
            }
        });

        btnCartPlus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartPlus.setText("+");
        btnCartPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartPlusActionPerformed(evt);
            }
        });

        btnCartMinus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartMinus.setText("-");
        btnCartMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartMinusActionPerformed(evt);
            }
        });

        btnCartAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartAdd.setText("Add");
        btnCartAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartAddActionPerformed(evt);
            }
        });

        btnCartRemove.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartRemove.setText("Remove");
        btnCartRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartRemoveActionPerformed(evt);
            }
        });

        lbCartMember.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbCartMember.setText("Membership :");

        txtCartMember.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtCartMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCartMemberActionPerformed(evt);
            }
        });

        btnCartMember.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartMember.setText("Check Member");
        btnCartMember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartMemberActionPerformed(evt);
            }
        });

        btnCartPrint.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartPrint.setText("Print Receipt");
        btnCartPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartPrintActionPerformed(evt);
            }
        });

        btnCartProcess.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartProcess.setText("Proccess");
        btnCartProcess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartProcessActionPerformed(evt);
            }
        });

        btnCartClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCartClear.setText("Clear");
        btnCartClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCartClearActionPerformed(evt);
            }
        });

        txtCartTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtCartTotal.setEditable(false);

        lbCartTotal.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbCartTotal.setText("Total : ");

        lbCartMemCheck.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbCartMemCheck.setToolTipText("");
        lbCartMemCheck.setMaximumSize(new java.awt.Dimension(32, 32));
        lbCartMemCheck.setMinimumSize(new java.awt.Dimension(32, 32));
        lbCartMemCheck.setPreferredSize(new java.awt.Dimension(32, 32));

        lbCartDisCount.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbCartDisCount.setMaximumSize(new java.awt.Dimension(97, 28));
        lbCartDisCount.setMinimumSize(new java.awt.Dimension(97, 28));
        lbCartDisCount.setName(""); // NOI18N
        lbCartDisCount.setPreferredSize(new java.awt.Dimension(97, 28));

        javax.swing.GroupLayout CartPaneLayout = new javax.swing.GroupLayout(CartPane);
        CartPane.setLayout(CartPaneLayout);
        CartPaneLayout.setHorizontalGroup(
            CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(CartPaneLayout.createSequentialGroup()
                .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CartPaneLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(CartPaneLayout.createSequentialGroup()
                                    .addComponent(btnCartAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnCartRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(CartPaneLayout.createSequentialGroup()
                                    .addComponent(lbCartQuantity)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCartQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCartPlus)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnCartMinus))
                                .addGroup(CartPaneLayout.createSequentialGroup()
                                    .addGap(43, 43, 43)
                                    .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnCartPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCartMember, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnCartProcess, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(btnCartClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(CartPaneLayout.createSequentialGroup()
                                .addComponent(lbCartMember)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCartMember, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbCartMemCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(CartPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbCartTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCartTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbCartDisCount, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        CartPaneLayout.setVerticalGroup(
            CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CartPaneLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCartTotal)
                    .addComponent(txtCartTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbCartDisCount, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(CartPaneLayout.createSequentialGroup()
                        .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCartQuantity)
                            .addComponent(txtCartQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCartPlus)
                            .addComponent(btnCartMinus))
                        .addGap(18, 18, 18)
                        .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCartAdd)
                            .addComponent(btnCartRemove))
                        .addGap(18, 18, 18)
                        .addGroup(CartPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCartMember)
                            .addComponent(txtCartMember, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lbCartMemCheck, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCartMember)
                .addGap(33, 33, 33)
                .addComponent(btnCartProcess)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCartPrint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(btnCartClear)
                .addGap(18, 18, 18))
        );

        DescriptionPane.setBackground(new java.awt.Color(255, 255, 255));
        DescriptionPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lbDesName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesName.setText("Name :");

        txtDesName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDesName.setEditable(false);

        lbDesDescription.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesDescription.setText("Description :");

        lbDesQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesQuantity.setText("Quantity :");

        txtDesQuantity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDesQuantity.setEditable(false);

        txtADescription.setColumns(20);
        txtADescription.setRows(5);
        txtADescription.setEditable(false);
        jScrollPane2.setViewportView(txtADescription);

        lbDesStatus.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesStatus.setText("Status :");

        txtDesStatus.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDesStatus.setEditable(false);

        lbDesPrice.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesPrice.setText("Price :");

        txtDesPrice.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDesPrice.setEditable(false);

        lbDesId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDesId.setText("Id :");

        txtDesId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtDesId.setEditable(false);

        javax.swing.GroupLayout DescriptionPaneLayout = new javax.swing.GroupLayout(DescriptionPane);
        DescriptionPane.setLayout(DescriptionPaneLayout);
        DescriptionPaneLayout.setHorizontalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDesDescription)
                    .addComponent(lbDesName)
                    .addComponent(lbDesId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                            .addComponent(txtDesName))
                        .addGap(18, 18, 18)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDesQuantity)
                            .addComponent(lbDesPrice)
                            .addComponent(lbDesStatus))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDesQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtDesId, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(268, Short.MAX_VALUE))
        );
        DescriptionPaneLayout.setVerticalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DescriptionPaneLayout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDesId)
                    .addComponent(txtDesId, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDesStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDesStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDesQuantity)
                            .addComponent(txtDesQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDesPrice)
                            .addComponent(txtDesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDesName)
                            .addComponent(txtDesName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDesDescription)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(21, 21, 21))
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
        tblStock.setModel(new GameTable().getModel(null));
        tblStock.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblStock.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblStock.getTableHeader().setReorderingAllowed(false);
        tblStock.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(tblStock);

        HeaderPane.setBackground(new java.awt.Color(255, 255, 255));

        lbPanalName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbPanalName.setText("Order");

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
        cbCategory.setModel(new ModelCombox().getModel("category","category_name"));
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
                .addComponent(CartPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(HeaderPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(HeaderPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DescriptionPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CartPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        clearGameData();
        searchGame();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        clearGameData();
        searchGame();
    }//GEN-LAST:event_txtSearchActionPerformed

    private void cbCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoryActionPerformed
        clearGameData();
        searchGame();
    }//GEN-LAST:event_cbCategoryActionPerformed

    int cartQA = 0;
    private void txtCartQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCartQuantityActionPerformed
        if(isNumber(txtCartQuantity.getText())&& game!= null) {
            int cartQ = Integer.parseInt(txtCartQuantity.getText());
            if(cartQ>=game.getQuantity()) {
                JOptionPane.showMessageDialog(CartPane, "Out of stock.", "Info", JOptionPane.INFORMATION_MESSAGE);
                txtCartQuantity.setText(cartQA+"");
            }else {
                cartQA = cartQ;
                txtCartQuantity.setText(cartQ+"");
                btnCartPlus.setEnabled(true);
            }
        }else {
            txtCartQuantity.setText("0");
        }
    }//GEN-LAST:event_txtCartQuantityActionPerformed

    private void btnCartPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartPlusActionPerformed
        if(isNumber(txtCartQuantity.getText())&& game!= null) {
            int cartQ = Integer.parseInt(txtCartQuantity.getText()) + 1;
            if(cartQ>=game.getQuantity()) {
                JOptionPane.showMessageDialog(CartPane, "Out of stock.", "Info", JOptionPane.INFORMATION_MESSAGE);
                btnCartPlus.setEnabled(false);
            }else {
                cartQA = cartQ;
                txtCartQuantity.setText(cartQ+"");
                btnCartPlus.setEnabled(true);
            }
        }else {
            txtCartQuantity.setText("0");
        }
    }//GEN-LAST:event_btnCartPlusActionPerformed

    private void btnCartMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartMinusActionPerformed
        if(isNumber(txtCartQuantity.getText())&& game!= null) {
            int cartQ = Integer.parseInt(txtCartQuantity.getText()) - 1;
            if(cartQ>=game.getQuantity()) {
                JOptionPane.showMessageDialog(CartPane, "Out of stock.", "Info", JOptionPane.INFORMATION_MESSAGE);
                btnCartPlus.setEnabled(false);
            }else {
                cartQA = cartQ;
                txtCartQuantity.setText(cartQ+"");
                btnCartPlus.setEnabled(true);
            }
        }else {
            txtCartQuantity.setText("0");
        }
    }//GEN-LAST:event_btnCartMinusActionPerformed

    private void btnCartAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartAddActionPerformed
        if(!txtCartQuantity.getText().equals("0")) {
            addCart();
        }
        
    }//GEN-LAST:event_btnCartAddActionPerformed

    private void btnCartRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartRemoveActionPerformed
        try{
            int selectRow = tblCart.getSelectedRow();
            ((DefaultTableModel)tblCart.getModel()).removeRow(selectRow);
            setTotal();
        }catch(IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }
    }//GEN-LAST:event_btnCartRemoveActionPerformed

    private void txtCartMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCartMemberActionPerformed
        btnCartMemberActionPerformed(evt);
    }//GEN-LAST:event_txtCartMemberActionPerformed

    private void btnCartMemberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartMemberActionPerformed
        
        if(new MemberDA().isMember(Integer.parseInt(txtCartMember.getText()))) {
            lbCartMemCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/accept.png")));
            setTotal();
        }else {
            lbCartMemCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/remove.png")));
            txtCartMember.setText("");
        }
    }//GEN-LAST:event_btnCartMemberActionPerformed

    private void btnCartProcessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartProcessActionPerformed
        processOrder();
    }//GEN-LAST:event_btnCartProcessActionPerformed

    private void btnCartPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartPrintActionPerformed
        
    }//GEN-LAST:event_btnCartPrintActionPerformed

    private void btnCartClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCartClearActionPerformed
        clearCart();
        cartQA = 0;
        txtCartQuantity.setText("");
        txtCartMember.setText("");
        lbCartMemCheck.setIcon(null);
    }//GEN-LAST:event_btnCartClearActionPerformed
  
    private void stockSelectionPerformed(ListSelectionEvent evt) {
        try{
        if (!evt.getValueIsAdjusting()) {
            int selectedRow = tblStock.getSelectedRow();
            if (selectedRow != -1) {
                setGameData();
            }
        }
        }catch(IndexOutOfBoundsException e) {
//            e.printStackTrace();
        }
    }
    
//    private void cartSelectionPerformed(ListSelectionEvent evt) {
//        if (!evt.getValueIsAdjusting()) {
//            int selectedRow = tblCart.getSelectedRow();
//            if (selectedRow != -1) {
//                
//            }
//        }
//    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CartPane;
    private javax.swing.JPanel DescriptionPane;
    private javax.swing.JPanel HeaderPane;
    private javax.swing.JButton btnCartAdd;
    private javax.swing.JButton btnCartClear;
    private javax.swing.JButton btnCartMember;
    private javax.swing.JButton btnCartMinus;
    private javax.swing.JButton btnCartPlus;
    private javax.swing.JButton btnCartPrint;
    private javax.swing.JButton btnCartProcess;
    private javax.swing.JButton btnCartRemove;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbCartDisCount;
    private javax.swing.JLabel lbCartMemCheck;
    private javax.swing.JLabel lbCartMember;
    private javax.swing.JLabel lbCartQuantity;
    private javax.swing.JLabel lbCartTotal;
    private javax.swing.JLabel lbCategory;
    private javax.swing.JLabel lbDesDescription;
    private javax.swing.JLabel lbDesId;
    private javax.swing.JLabel lbDesName;
    private javax.swing.JLabel lbDesPrice;
    private javax.swing.JLabel lbDesQuantity;
    private javax.swing.JLabel lbDesStatus;
    private javax.swing.JLabel lbPanalName;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JTable tblCart;
    private javax.swing.JTable tblStock;
    private javax.swing.JTextArea txtADescription;
    private javax.swing.JTextField txtCartMember;
    private javax.swing.JTextField txtCartQuantity;
    private javax.swing.JTextField txtCartTotal;
    private javax.swing.JTextField txtDesId;
    private javax.swing.JTextField txtDesName;
    private javax.swing.JTextField txtDesPrice;
    private javax.swing.JTextField txtDesQuantity;
    private javax.swing.JTextField txtDesStatus;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
