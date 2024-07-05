package com.suven.consultancy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoanAssistantApp extends JFrame implements ActionListener {
    JLabel labelLoanBalance, labelInterestRate, labelNumPayments, labelMonthlyPayment, labelAnalysis;
    JTextField textLoanBalance, textInterestRate, textNumPayments, textMonthlyPayment;
    JButton btnComputePayment, btnNewAnalysis, btnToggleNumPayments, btnToggleMonthlyPayment, btnExit;
    JTextArea textAreaAnalysis;
    Font fontLabel, fontButton;
    Boolean isNumPaymentsEditable = false, isMonthlyPaymentEditable = true;

    LoanAssistantApp() {
        super("                                                                          Loan Assistant App");

        textAreaAnalysis = new JTextArea("");
        textAreaAnalysis.setBounds(400, 40, 300, 150);
        textAreaAnalysis.setFont(new Font("Segoe Script", Font.PLAIN, 14));
        textAreaAnalysis.setForeground(Color.BLACK);
        textAreaAnalysis.setEditable(false);
        textAreaAnalysis.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(textAreaAnalysis);

        fontLabel = new Font("Arial", Font.PLAIN, 16);
        fontButton = new Font("SANS_SERIF", Font.BOLD, 13);

        labelAnalysis = new JLabel("Loan Analysis: ");
        labelLoanBalance = new JLabel("Loan Balance");
        labelInterestRate = new JLabel("Interest Rate");
        labelNumPayments = new JLabel("Number of Payments");
        labelMonthlyPayment = new JLabel("Monthly Payment");

        textLoanBalance = new JTextField();
        textInterestRate = new JTextField();
        textNumPayments = new JTextField();
        textMonthlyPayment = new JTextField();

        btnComputePayment = new JButton("Compute Monthly Payment");
        btnNewAnalysis = new JButton("New Loan Analysis");

        setLayout(null);
        setSize(800, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        labelAnalysis.setBounds(400, 0, 150, 50);
        labelAnalysis.setFont(fontLabel);
        add(labelAnalysis);

        labelLoanBalance.setBounds(20, 0, 100, 50);
        labelLoanBalance.setFont(fontLabel);
        add(labelLoanBalance);

        labelInterestRate.setBounds(20, 30, 150, 50);
        labelInterestRate.setFont(fontLabel);
        add(labelInterestRate);

        labelNumPayments.setBounds(20, 60, 150, 50);
        labelNumPayments.setFont(fontLabel);
        add(labelNumPayments);

        labelMonthlyPayment.setBounds(20, 90, 150, 50);
        labelMonthlyPayment.setFont(fontLabel);
        add(labelMonthlyPayment);

        btnComputePayment.setBounds(50, 140, 250, 30);
        btnComputePayment.setFont(fontButton);
        add(btnComputePayment);

        btnNewAnalysis.setBounds(75, 190, 200, 30);
        btnNewAnalysis.setFont(fontButton);
        btnNewAnalysis.setEnabled(false);
        add(btnNewAnalysis);

        btnComputePayment.addActionListener(this);
        btnNewAnalysis.addActionListener(this);

        textLoanBalance.setBounds(170, 15, 100, 20);
        textLoanBalance.setFont(fontLabel);
        textLoanBalance.setHorizontalAlignment(JTextField.RIGHT);
        add(textLoanBalance);

        textInterestRate.setBounds(170, 45, 100, 20);
        textInterestRate.setHorizontalAlignment(JTextField.RIGHT);
        textInterestRate.setFont(fontLabel);
        add(textInterestRate);

        textNumPayments.setBounds(170, 80, 100, 20);
        textNumPayments.setHorizontalAlignment(JTextField.RIGHT);
        textNumPayments.setFont(fontLabel);
        add(textNumPayments);

        textMonthlyPayment.setBounds(170, 110, 100, 20);
        textMonthlyPayment.setHorizontalAlignment(JTextField.RIGHT);
        textMonthlyPayment.setFont(fontLabel);
        textMonthlyPayment.setEditable(false);
        textMonthlyPayment.setForeground(Color.BLACK);
        textMonthlyPayment.setBackground(Color.YELLOW);
        add(textMonthlyPayment);

        btnToggleNumPayments = new JButton("X");
        btnToggleNumPayments.setBounds(300, 70, 50, 25);
        btnToggleNumPayments.setFont(fontButton);
        add(btnToggleNumPayments);

        btnToggleMonthlyPayment = new JButton("X");
        btnToggleMonthlyPayment.setBounds(300, 110, 50, 25);
        btnToggleMonthlyPayment.setFont(fontButton);
        add(btnToggleMonthlyPayment);
        btnToggleMonthlyPayment.setVisible(false);

        btnToggleNumPayments.addActionListener(this);
        btnToggleMonthlyPayment.addActionListener(this);

        btnExit = new JButton("Exit");
        btnExit.setBounds(500, 220, 100, 30);
        btnExit.setFont(fontButton);
        add(btnExit);
        btnExit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnComputePayment) {
            try {
                if (textInterestRate.getText().equals("") || textInterestRate.getText().equals("0")) {
                    JOptionPane.showMessageDialog(null, "Interest Rate cannot be 0%");
                }
                if ((textLoanBalance.getText().equals("") || textInterestRate.getText().equals("") || textNumPayments.getText().equals("")) && (textLoanBalance.getText().equals("") || textInterestRate.getText().equals("") || textMonthlyPayment.getText().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Fill All The Required Details");
                }
                if (textMonthlyPayment.getText().equals("")) {
                    float loanBalance = Float.parseFloat(textLoanBalance.getText());
                    float interestRate = Float.parseFloat(textInterestRate.getText());
                    float numPayments = Float.parseFloat(textNumPayments.getText());
                    float monthlyInterest = interestRate / 1200;
                    float monthlyPayment = (float) (monthlyInterest * loanBalance / (1 - Math.pow((1 + monthlyInterest), -numPayments)));
                    textMonthlyPayment.setText(monthlyPayment + "");
                    String analysis = "Loan Amount : $" + loanBalance + "\n";
                    analysis += "Interest Rate : " + monthlyInterest * 1200 + "%\n\n";
                    analysis += numPayments + " Payments of : $" + monthlyPayment;
                    textAreaAnalysis.setText(analysis);
                }
                if (textNumPayments.getText().equals("")) {
                    float loanBalance = Float.parseFloat(textLoanBalance.getText());
                    float interestRate = Float.parseFloat(textInterestRate.getText());
                    float monthlyPayment = Float.parseFloat(textMonthlyPayment.getText());
                    float monthlyInterest = interestRate / 1200;
                    float numPayments = (float) (-(Math.log10(1 - monthlyInterest * loanBalance / monthlyPayment)) / Math.log10(1 + monthlyInterest));
                    textNumPayments.setText(numPayments + "");
                    String analysis = "Loan Amount : $" + loanBalance + "\n";
                    analysis += "Interest Rate : " + monthlyInterest * 1200 + "%\n\n";
                    analysis += numPayments + " Payments of : $" + monthlyPayment;
                    textAreaAnalysis.setText(analysis);
                }
                btnComputePayment.setEnabled(false);
                btnNewAnalysis.setEnabled(true);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        if (e.getSource() == btnNewAnalysis) {
            if (isMonthlyPaymentEditable) {
                textMonthlyPayment.setText(null);
            }
            if (isNumPaymentsEditable) {
                textNumPayments.setText(null);
            }
            btnComputePayment.setEnabled(true);
            btnNewAnalysis.setEnabled(false);
        }
        if (e.getSource() == btnToggleNumPayments) {
            btnToggleNumPayments.setVisible(false);
            btnToggleMonthlyPayment.setVisible(true);
            textMonthlyPayment.setEditable(true);
            textNumPayments.setEditable(false);
            textNumPayments.setBackground(Color.YELLOW);
            textMonthlyPayment.setBackground(Color.white);
            isNumPaymentsEditable = true;
            isMonthlyPaymentEditable = false;
            textNumPayments.setText(null);

            btnComputePayment.setEnabled(true);
            btnNewAnalysis.setEnabled(false);
        }
        if (e.getSource() == btnToggleMonthlyPayment) {
            btnToggleNumPayments.setVisible(true);
            btnToggleMonthlyPayment.setVisible(false);
            textMonthlyPayment.setEditable(false);
            textNumPayments.setEditable(true);
            textNumPayments.setBackground(Color.white);
            textMonthlyPayment.setBackground(Color.yellow);
            isNumPaymentsEditable = false;
            isMonthlyPaymentEditable = true;
            textMonthlyPayment.setText(null);

            btnComputePayment.setEnabled(true);
            btnNewAnalysis.setEnabled(false);
        }
        if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new LoanAssistantApp();
    }
}
