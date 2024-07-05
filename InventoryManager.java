// InventoryManager.java

package com.suven.inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.print.*;

public class InventoryManager extends JFrame implements ActionListener {
    JTextField itemField, locationField, serialField, priceField, dateField, storeField, noteField;
    JCheckBox markedCheckbox;
    JButton newBtn, deleteBtn, saveBtn, previousBtn, nextBtn, printBtn, photoBtn, exitBtn;
    JComboBox<String> locationCombo;
    JTextArea noteArea;
    JPanel infoPanel, navPanel, actionPanel, photoPanel;
    JLabel photoLabel;
    JFileChooser fileChooser;
    InventoryItem[] inventory;
    int currentIndex = 0, totalEntries = 0;
    static final int MAX_ENTRIES = 100;

    public InventoryManager() {
        setTitle("Inventory Manager");
        setLayout(new BorderLayout());

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(9, 2));
        
        infoPanel.add(new JLabel("Item:"));
        itemField = new JTextField();
        infoPanel.add(itemField);

        infoPanel.add(new JLabel("Location:"));
        locationCombo = new JComboBox<>();
        locationCombo.setEditable(true);
        infoPanel.add(locationCombo);

        infoPanel.add(new JLabel("Serial Number:"));
        serialField = new JTextField();
        infoPanel.add(serialField);

        infoPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        infoPanel.add(priceField);

        infoPanel.add(new JLabel("Date Purchased:"));
        dateField = new JTextField();
        infoPanel.add(dateField);

        infoPanel.add(new JLabel("Store/Website:"));
        storeField = new JTextField();
        infoPanel.add(storeField);

        infoPanel.add(new JLabel("Note:"));
        noteField = new JTextField();
        infoPanel.add(noteField);

        markedCheckbox = new JCheckBox("Marked?");
        infoPanel.add(markedCheckbox);

        photoPanel = new JPanel();
        photoLabel = new JLabel();
        photoPanel.add(photoLabel);

        navPanel = new JPanel();
        newBtn = new JButton("New");
        deleteBtn = new JButton("Delete");
        saveBtn = new JButton("Save");
        previousBtn = new JButton("Previous");
        nextBtn = new JButton("Next");
        printBtn = new JButton("Print");
        photoBtn = new JButton("Photo");
        exitBtn = new JButton("Exit");

        navPanel.add(newBtn);
        navPanel.add(deleteBtn);
        navPanel.add(saveBtn);
        navPanel.add(previousBtn);
        navPanel.add(nextBtn);
        navPanel.add(printBtn);
        navPanel.add(photoBtn);
        navPanel.add(exitBtn);

        add(infoPanel, BorderLayout.CENTER);
        add(photoPanel, BorderLayout.EAST);
        add(navPanel, BorderLayout.SOUTH);

        newBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        saveBtn.addActionListener(this);
        previousBtn.addActionListener(this);
        nextBtn.addActionListener(this);
        printBtn.addActionListener(this);
        photoBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        inventory = new InventoryItem[MAX_ENTRIES];
        fileChooser = new JFileChooser();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitApp();
            }
        });

        loadInventory();
        if (totalEntries > 0) {
            showEntry(currentIndex);
        } else {
            toggleButtons(false);
        }

        setSize(700, 400);
        setVisible(true);
    }

    private void loadInventory() {
        try {
            BufferedReader inputFile = new BufferedReader(new FileReader("inventory.txt"));
            totalEntries = Integer.parseInt(inputFile.readLine());
            for (int i = 0; i < totalEntries; i++) {
                inventory[i] = new InventoryItem();
                inventory[i].description = inputFile.readLine();
                inventory[i].location = inputFile.readLine();
                inventory[i].serialNumber = inputFile.readLine();
                inventory[i].marked = Boolean.parseBoolean(inputFile.readLine());
                inventory[i].purchasePrice = inputFile.readLine();
                inventory[i].purchaseDate = inputFile.readLine();
                inventory[i].purchaseStore = inputFile.readLine();
                inventory[i].note = inputFile.readLine();
                inventory[i].photoFile = inputFile.readLine();
            }
            int locationCount = Integer.parseInt(inputFile.readLine());
            for (int i = 0; i < locationCount; i++) {
                locationCombo.addItem(inputFile.readLine());
            }
            inputFile.close();
        } catch (Exception ex) {
            totalEntries = 0;
            currentIndex = 0;
        }
    }

    private void showEntry(int index) {
        itemField.setText(inventory[index].description);
        locationCombo.setSelectedItem(inventory[index].location);
        serialField.setText(inventory[index].serialNumber);
        markedCheckbox.setSelected(inventory[index].marked);
        priceField.setText(inventory[index].purchasePrice);
        dateField.setText(inventory[index].purchaseDate);
        storeField.setText(inventory[index].purchaseStore);
        noteField.setText(inventory[index].note);
        if (inventory[index].photoFile != null) {
            photoLabel.setIcon(new ImageIcon(inventory[index].photoFile));
        } else {
            photoLabel.setIcon(null);
        }
        toggleButtons(true);
    }

    private void toggleButtons(boolean enable) {
        deleteBtn.setEnabled(enable);
        previousBtn.setEnabled(enable);
        nextBtn.setEnabled(enable);
        printBtn.setEnabled(enable);
    }

    private void exitApp() {
        if (JOptionPane.showConfirmDialog(null, "Any unsaved changes will be lost.\nAre you sure you want to exit?", "Exit Program", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            return;
        saveInventory();
        System.exit(0);
    }

    private void saveInventory() {
        try {
            PrintWriter outputFile = new PrintWriter(new BufferedWriter(new FileWriter("inventory.txt")));
            outputFile.println(totalEntries);
            for (int i = 0; i < totalEntries; i++) {
                outputFile.println(inventory[i].description);
                outputFile.println(inventory[i].location);
                outputFile.println(inventory[i].serialNumber);
                outputFile.println(inventory[i].marked);
                outputFile.println(inventory[i].purchasePrice);
                outputFile.println(inventory[i].purchaseDate);
                outputFile.println(inventory[i].purchaseStore);
                outputFile.println(inventory[i].note);
                outputFile.println(inventory[i].photoFile);
            }
            outputFile.println(locationCombo.getItemCount());
            for (int i = 0; i < locationCombo.getItemCount(); i++) {
                outputFile.println(locationCombo.getItemAt(i));
            }
            outputFile.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newBtn) {
            // handle new button action
        } else if (e.getSource() == deleteBtn) {
            // handle delete button action
        } else if (e.getSource() == saveBtn) {
            // handle save button action
        } else if (e.getSource() == previousBtn) {
            // handle previous button action
        } else if (e.getSource() == nextBtn) {
            // handle next button action
        } else if (e.getSource() == printBtn) {
            // handle print button action
        } else if (e.getSource() == photoBtn) {
            // handle photo button action
        } else if (e.getSource() == exitBtn) {
            exitApp();
        }
    }

    public static void main(String[] args) {
        new InventoryManager();
    }
}

class InventoryItem {
    String description;
    String location;
    boolean marked;
    String serialNumber;
    String purchasePrice;
    String purchaseDate;
    String purchaseStore;
    String note;
    String photoFile;
}
