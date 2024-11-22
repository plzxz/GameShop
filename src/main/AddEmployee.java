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
public class AddEmployee extends javax.swing.JPanel {
    
    Employee employee;

    /**
     * Creates new form Order
     */
    public AddEmployee() {
        initComponents();
        setTableSelection();
    }

    public void setData() {
        try{
            
            int row = tblEmployee.getSelectedRow();
            
            int eId = Integer.parseInt(tblEmployee.getValueAt(row, 0).toString());

            employee = new EmployeeDA().getData(eId);
            
            txtEditId.setText(employee.getId() + "");
            txtEditFname.setText(employee.getfName());
            txtEditLname.setText(employee.getlName());
            txtEditTel.setText(employee.getTel());
            txtEditEmail.setText(employee.getEmail());
            txtEditAddess.setText(employee.getAddress());
            txtEditCity.setText(employee.getCity());
            txtEditZip.setText(employee.getZipCode() + "");
            if(employee.getRank().equals("Admin")) {
                cbEditRank.setSelectedIndex(1);
            }else {
                cbEditRank.setSelectedIndex(0);
            }
            txtEditPass.setText(employee.getPass());

        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Something Wrong", "Warning", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void updateData() {
    
        employee.setfName(txtEditFname.getText());
        employee.setlName(txtEditLname.getText());
        employee.setTel(txtEditTel.getText());
        employee.setEmail(txtEditEmail.getText());
        employee.setAddress(txtEditAddess.getText());
        employee.setCity(txtEditCity.getText());
        employee.setZipCode(Integer.parseInt(txtEditZip.getText()));
        employee.setPass(txtEditPass.getText());
        employee.setRank(cbEditRank.getSelectedItem().toString());
        
        new EmployeeDA().updateEmployee(employee);
        
    }
    
    public void addEmployee() {
    
        String rank = cbAddRank.getSelectedItem().toString();
        String fName = txtAddFname.getText();
        String lName = txtAddLname.getText();
        java.sql.Date birth = java.sql.Date.valueOf(birthDatePicker.getDate());
        java.sql.Date hire = java.sql.Date.valueOf(hireDatePicker.getDate());
        String tel = txtAddTel.getText();
        String email = txtAddEmail.getText();
        String address = txtAddAddress.getText();
        String city = txtAddCity.getText();
        int zip = Integer.parseInt(txtAddZipCode.getText());
        
        Employee aEmployee = new Employee(0, rank, fName, lName, hire, birth, tel, email, address, city, zip, "");
        
        new EmployeeDA().addMember(aEmployee);
        
    }
    
    public void updateSearch() {
        ArrayList<Object> data = new ArrayList<>();
        String sql = "";
        
        if(!txtSearch.getText().isBlank()) {
            sql += " AND EMP_ID= ?";
            data.add(txtSearch.getText());
        }

        Object[] aData = new Object[]{sql,data};
        
        if(sql.isBlank()) {
            tblEmployee.setModel(new EmployeeTable().getModel(null));
        }else {
            tblEmployee.setModel(new EmployeeTable().getModel(aData));
        }
    }
    
    
    public void clearAdd() {
        cbAddRank.setSelectedIndex(0);
        txtAddFname.setText("");
        txtAddLname.setText("");
        hireDatePicker.clear();
        birthDatePicker.clear();
        txtAddTel.setText("");
        txtAddEmail.setText("");
        txtAddAddress.setText("");
        txtAddCity.setText("");
        txtAddZipCode.setText("");
    }
    
    public void clearEdit() {
        txtEditId.setText("");
        txtEditFname.setText("");
        txtEditLname.setText("");
        txtEditTel.setText("");
        txtEditEmail.setText("");
        txtEditAddess.setText("");
        txtEditCity.setText("");
        txtEditZip.setText("");
        cbEditRank.setSelectedIndex(0);
        txtEditPass.setText("");
        
        isPassVisible = false;
        txtEditPass.setEchoChar('\u2022');
        btnEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png")));
    }
    
    public boolean isAddFilled() {
        if(!txtAddFname.getText().isBlank() && !txtAddLname.getText().isBlank() && !txtAddTel.getText().isBlank() && !txtAddEmail.getText().isBlank() && !txtAddAddress.getText().isBlank() && !txtAddCity.getText().isBlank() && !txtAddZipCode.getText().isBlank()) {
            
            if(hireDatePicker.getDate() == null) {
                JOptionPane.showMessageDialog(AddPane, "Hire Date not filled.", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if(birthDatePicker.getDate() == null) {
                JOptionPane.showMessageDialog(AddPane, "Birth Date not filled.", "Warning", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            return true;
        }else {
            JOptionPane.showMessageDialog(AddPane, "form not filled.", "Warning", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    private void setTableSelection() {
        tblEmployee.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent evt) {
                employeeSelectionPerformed(evt);
            }
        });
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
        btnAdd = new javax.swing.JButton();
        lbAddFname = new javax.swing.JLabel();
        txtAddFname = new javax.swing.JTextField();
        cbAddRank = new javax.swing.JComboBox<>();
        lbAddRank = new javax.swing.JLabel();
        lbAdd = new javax.swing.JLabel();
        lbAddLname = new javax.swing.JLabel();
        txtAddLname = new javax.swing.JTextField();
        hireDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        lbAddHireDate = new javax.swing.JLabel();
        lbAddBirthDate = new javax.swing.JLabel();
        birthDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        lbAddTel = new javax.swing.JLabel();
        txtAddTel = new javax.swing.JTextField();
        lbAddEmail = new javax.swing.JLabel();
        txtAddEmail = new javax.swing.JTextField();
        lbAddAddress = new javax.swing.JLabel();
        txtAddAddress = new javax.swing.JTextField();
        lbAddCity = new javax.swing.JLabel();
        txtAddCity = new javax.swing.JTextField();
        lbAddZipCode = new javax.swing.JLabel();
        txtAddZipCode = new javax.swing.JTextField();
        DescriptionPane = new javax.swing.JPanel();
        lbEditFirstName = new javax.swing.JLabel();
        txtEditFname = new javax.swing.JTextField();
        btnEditUpdate = new javax.swing.JButton();
        lbEditId = new javax.swing.JLabel();
        txtEditId = new javax.swing.JTextField();
        lbEditLastName = new javax.swing.JLabel();
        txtEditLname = new javax.swing.JTextField();
        lbEditRank = new javax.swing.JLabel();
        cbEditRank = new javax.swing.JComboBox<>();
        lbEditTel = new javax.swing.JLabel();
        lbEditEmail = new javax.swing.JLabel();
        txtEditEmail = new javax.swing.JTextField();
        lbEditAddess = new javax.swing.JLabel();
        txtEditAddess = new javax.swing.JTextField();
        lbEditCity = new javax.swing.JLabel();
        txtEditCity = new javax.swing.JTextField();
        lbEditZipCode = new javax.swing.JLabel();
        txtEditZip = new javax.swing.JTextField();
        lbEdit = new javax.swing.JLabel();
        txtEditTel = new javax.swing.JTextField();
        lbEditPass = new javax.swing.JLabel();
        txtEditPass = new javax.swing.JPasswordField();
        btnEye = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmployee = new javax.swing.JTable();
        HeaderPane = new javax.swing.JPanel();
        lbPanalName = new javax.swing.JLabel();
        lbSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();

        setBackground(new java.awt.Color(239, 239, 239));

        AddPane.setBackground(new java.awt.Color(255, 255, 255));
        AddPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnAddClear.setText("Clear");
        btnAddClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAddClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClearActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lbAddFname.setText("First Name :");
        lbAddFname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddFname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddFnameActionPerformed(evt);
            }
        });

        cbAddRank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "Admin" }));
        cbAddRank.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbAddRank.setText("Rank :");
        lbAddRank.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbAdd.setText("Add");
        lbAdd.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbAddLname.setText("Last Name : ");
        lbAddLname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddLname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddLname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddLnameActionPerformed(evt);
            }
        });

        lbAddHireDate.setText("Hire Date : ");
        lbAddHireDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbAddBirthDate.setText("Birth Date : ");
        lbAddBirthDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbAddTel.setText("Tel :");
        lbAddTel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddTel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddTelActionPerformed(evt);
            }
        });

        lbAddEmail.setText("Email :");
        lbAddEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddEmailActionPerformed(evt);
            }
        });

        lbAddAddress.setText("Address : ");
        lbAddAddress.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddAddress.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddAddress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddAddressActionPerformed(evt);
            }
        });

        lbAddCity.setText("City : ");
        lbAddCity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddCity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddCityActionPerformed(evt);
            }
        });

        lbAddZipCode.setText("Zip Code :");
        lbAddZipCode.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtAddZipCode.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtAddZipCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddZipCodeActionPerformed(evt);
            }
        });

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
                    .addComponent(lbAddZipCode)
                    .addComponent(lbAddCity)
                    .addComponent(lbAddAddress)
                    .addComponent(lbAddEmail)
                    .addComponent(lbAddTel)
                    .addComponent(lbAddBirthDate)
                    .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(AddPaneLayout.createSequentialGroup()
                            .addGap(43, 43, 43)
                            .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnAddClear, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(AddPaneLayout.createSequentialGroup()
                            .addComponent(lbAddRank)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbAddRank, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AddPaneLayout.createSequentialGroup()
                            .addComponent(lbAddFname)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtAddFname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(AddPaneLayout.createSequentialGroup()
                            .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbAddLname)
                                .addComponent(lbAddHireDate, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(hireDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAddLname, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAddTel, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(birthDatePicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AddPaneLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAddZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddCity, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAddEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(42, Short.MAX_VALUE))
        );
        AddPaneLayout.setVerticalGroup(
            AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddPaneLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lbAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAddRank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddRank))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddFname))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddLname))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(birthDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddBirthDate))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hireDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddHireDate))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddTel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddEmail))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddCity))
                .addGap(18, 18, 18)
                .addGroup(AddPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAddZipCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbAddZipCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAdd)
                .addGap(100, 100, 100)
                .addComponent(btnAddClear)
                .addGap(18, 18, 18))
        );

        DescriptionPane.setBackground(new java.awt.Color(255, 255, 255));
        DescriptionPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lbEditFirstName.setText("First Name :");
        lbEditFirstName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditFname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditFname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditFnameActionPerformed(evt);
            }
        });

        btnEditUpdate.setText("Update");
        btnEditUpdate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEditUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUpdateActionPerformed(evt);
            }
        });

        lbEditId.setText("Id :");
        lbEditId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditId.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditId.setEditable(false);

        lbEditLastName.setText("Last Name :");
        lbEditLastName.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditLname.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditLname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditLnameActionPerformed(evt);
            }
        });

        lbEditRank.setText("Rank : ");
        lbEditRank.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        cbEditRank.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employee", "Admin" }));
        cbEditRank.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        lbEditTel.setText("Tel : ");
        lbEditTel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        lbEditEmail.setText("Email :");
        lbEditEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditEmail.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditEmailActionPerformed(evt);
            }
        });

        lbEditAddess.setText("Addess :");
        lbEditAddess.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditAddess.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditAddess.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditAddessActionPerformed(evt);
            }
        });

        lbEditCity.setText("City : ");
        lbEditCity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditCity.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditCityActionPerformed(evt);
            }
        });

        lbEditZipCode.setText("Zip Code : ");
        lbEditZipCode.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditZip.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditZip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditZipActionPerformed(evt);
            }
        });

        lbEdit.setText("Edit");
        lbEdit.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditTel.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        txtEditTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEditTelActionPerformed(evt);
            }
        });

        lbEditPass.setText("Password : ");
        lbEditPass.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        txtEditPass.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        btnEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png"))); // NOI18N
        btnEye.setBorderPainted(false);
        btnEye.setContentAreaFilled(false);
        btnEye.setMinimumSize(new java.awt.Dimension(32, 32));
        btnEye.setPreferredSize(new java.awt.Dimension(32, 32));
        btnEye.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEyeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DescriptionPaneLayout = new javax.swing.GroupLayout(DescriptionPane);
        DescriptionPane.setLayout(DescriptionPaneLayout);
        DescriptionPaneLayout.setHorizontalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                    .addComponent(lbEditFirstName)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtEditFname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                                    .addComponent(lbEditTel)
                                    .addGap(70, 70, 70)
                                    .addComponent(txtEditTel, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                                .addComponent(lbEditId)
                                .addGap(72, 72, 72)
                                .addComponent(txtEditId, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lbEditLastName)
                        .addGap(18, 18, 18)
                        .addComponent(txtEditLname, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbEditRank)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbEditRank, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(btnEditUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                                .addComponent(lbEditAddess)
                                .addGap(18, 18, 18)
                                .addComponent(txtEditAddess, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbEditCity)
                                .addGap(18, 18, 18)
                                .addComponent(txtEditCity, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbEditZipCode)
                                .addGap(18, 18, 18)
                                .addComponent(txtEditZip, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DescriptionPaneLayout.createSequentialGroup()
                                .addComponent(lbEditEmail)
                                .addGap(56, 56, 56)
                                .addComponent(txtEditEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbEditPass)
                                .addGap(18, 18, 18)
                                .addComponent(txtEditPass, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEye, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(33, 33, 33))
            .addGroup(DescriptionPaneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbEdit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DescriptionPaneLayout.setVerticalGroup(
            DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DescriptionPaneLayout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(lbEdit)
                .addGap(18, 18, 18)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEditId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEditId))
                .addGap(18, 18, 18)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DescriptionPaneLayout.createSequentialGroup()
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbEditFirstName)
                            .addComponent(txtEditFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbEditLastName)
                            .addComponent(txtEditLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbEditRank)
                            .addComponent(cbEditRank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditUpdate))
                        .addGap(18, 18, 18)
                        .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbEditEmail)
                            .addComponent(txtEditEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(DescriptionPaneLayout.createSequentialGroup()
                            .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbEditPass)
                                .addComponent(txtEditPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(4, 4, 4))
                        .addComponent(btnEye, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEditTel)
                    .addComponent(txtEditTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(DescriptionPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbEditAddess)
                    .addComponent(txtEditAddess, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEditCity)
                    .addComponent(txtEditCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbEditZipCode)
                    .addComponent(txtEditZip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEmployee.setModel(new EmployeeTable().getModel(null));
        tblEmployee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmployee.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblEmployee.getTableHeader().setReorderingAllowed(false);
        tblEmployee.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(tblEmployee);

        HeaderPane.setBackground(new java.awt.Color(255, 255, 255));

        lbPanalName.setText("Employee");
        lbPanalName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lbSearch.setText("Search :");
        lbSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        txtSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setText("Serach");
        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(HeaderPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DescriptionPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(AddPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        btnSearchActionPerformed(evt);
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        updateSearch();
        clearEdit();
    }//GEN-LAST:event_btnSearchActionPerformed

    
    private void txtEditFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditFnameActionPerformed
        txtEditLname.requestFocus();
    }//GEN-LAST:event_txtEditFnameActionPerformed

    private void txtEditLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditLnameActionPerformed
        
        txtEditEmail.requestFocus();
    }//GEN-LAST:event_txtEditLnameActionPerformed

    private void txtEditEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditEmailActionPerformed
        txtEditTel.requestFocus();
    }//GEN-LAST:event_txtEditEmailActionPerformed

    private void txtEditTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditTelActionPerformed
        txtEditAddess.requestFocus();
    }//GEN-LAST:event_txtEditTelActionPerformed

    private void txtEditAddessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditAddessActionPerformed
        txtEditCity.requestFocus();
    }//GEN-LAST:event_txtEditAddessActionPerformed

    private void txtEditCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditCityActionPerformed
        txtEditZip.requestFocus();
    }//GEN-LAST:event_txtEditCityActionPerformed

    private void txtEditZipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEditZipActionPerformed
        
    }//GEN-LAST:event_txtEditZipActionPerformed

    private void btnEditUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUpdateActionPerformed
        updateData();
        updateSearch();
    }//GEN-LAST:event_btnEditUpdateActionPerformed

    
    private void txtAddFnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddFnameActionPerformed
        txtAddLname.requestFocus();
    }//GEN-LAST:event_txtAddFnameActionPerformed

    private void txtAddLnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddLnameActionPerformed
        txtAddTel.requestFocus();
    }//GEN-LAST:event_txtAddLnameActionPerformed

    private void txtAddTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddTelActionPerformed
        txtAddEmail.requestFocus();
    }//GEN-LAST:event_txtAddTelActionPerformed

    private void txtAddEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddEmailActionPerformed
        txtAddAddress.requestFocus();
    }//GEN-LAST:event_txtAddEmailActionPerformed

    private void txtAddAddressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddAddressActionPerformed
        txtAddCity.requestFocus();
    }//GEN-LAST:event_txtAddAddressActionPerformed

    private void txtAddCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddCityActionPerformed
        txtAddZipCode.requestFocus();
    }//GEN-LAST:event_txtAddCityActionPerformed

    private void txtAddZipCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddZipCodeActionPerformed
        btnAddActionPerformed(evt);
    }//GEN-LAST:event_txtAddZipCodeActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        if(isAddFilled()) {
            try {
                addEmployee();
                clearAdd();
                updateSearch();

            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    
    private void btnAddClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClearActionPerformed
        clearAdd();
    }//GEN-LAST:event_btnAddClearActionPerformed

    
    private boolean isPassVisible = false;
    private void btnEyeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEyeActionPerformed
        if(isPassVisible) {
            txtEditPass.setEchoChar('\u2022');
            btnEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/close.png")));
            
        }else {
            txtEditPass.setEchoChar('\u0000');
            btnEye.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/open.png")));
        }
        isPassVisible = !isPassVisible;
    }//GEN-LAST:event_btnEyeActionPerformed

    private void employeeSelectionPerformed(ListSelectionEvent evt) {
        try{
        if (!evt.getValueIsAdjusting()) {
            int selectedRow = tblEmployee.getSelectedRow();
            if (selectedRow != -1) {
                setData();
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
    private com.github.lgooddatepicker.components.DatePicker birthDatePicker;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddClear;
    private javax.swing.JButton btnEditUpdate;
    private javax.swing.JButton btnEye;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cbAddRank;
    private javax.swing.JComboBox<String> cbEditRank;
    private com.github.lgooddatepicker.components.DatePicker hireDatePicker;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbAddAddress;
    private javax.swing.JLabel lbAddBirthDate;
    private javax.swing.JLabel lbAddCity;
    private javax.swing.JLabel lbAddEmail;
    private javax.swing.JLabel lbAddFname;
    private javax.swing.JLabel lbAddHireDate;
    private javax.swing.JLabel lbAddLname;
    private javax.swing.JLabel lbAddRank;
    private javax.swing.JLabel lbAddTel;
    private javax.swing.JLabel lbAddZipCode;
    private javax.swing.JLabel lbEdit;
    private javax.swing.JLabel lbEditAddess;
    private javax.swing.JLabel lbEditCity;
    private javax.swing.JLabel lbEditEmail;
    private javax.swing.JLabel lbEditFirstName;
    private javax.swing.JLabel lbEditId;
    private javax.swing.JLabel lbEditLastName;
    private javax.swing.JLabel lbEditPass;
    private javax.swing.JLabel lbEditRank;
    private javax.swing.JLabel lbEditTel;
    private javax.swing.JLabel lbEditZipCode;
    private javax.swing.JLabel lbPanalName;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JTable tblEmployee;
    private javax.swing.JTextField txtAddAddress;
    private javax.swing.JTextField txtAddCity;
    private javax.swing.JTextField txtAddEmail;
    private javax.swing.JTextField txtAddFname;
    private javax.swing.JTextField txtAddLname;
    private javax.swing.JTextField txtAddTel;
    private javax.swing.JTextField txtAddZipCode;
    private javax.swing.JTextField txtEditAddess;
    private javax.swing.JTextField txtEditCity;
    private javax.swing.JTextField txtEditEmail;
    private javax.swing.JTextField txtEditFname;
    private javax.swing.JTextField txtEditId;
    private javax.swing.JTextField txtEditLname;
    private javax.swing.JPasswordField txtEditPass;
    private javax.swing.JTextField txtEditTel;
    private javax.swing.JTextField txtEditZip;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
