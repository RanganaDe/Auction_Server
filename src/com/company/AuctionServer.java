package com.company;
/**
 * E/13/058 M.D.R.A.M De Silva
 * co225 mini project :AuctionServer
 */


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.Timer;
/*This is where the Gui operations are handled and this is where the main method is also included.One must run this file in order to run the application*/
public class AuctionServer extends JFrame implements ActionListener{

    // database object is created at the beging to handle the csv file and client bid records.
    private  static StockDatabase allowedUsers = new StockDatabase("stocks.csv","Symbol","Security Name","Price");
    //mainserve object is created to handle its functions.
    private static MainServer server = new MainServer(MainServer.BASE_PORT, allowedUsers);

    private Bid_Details bidInfo;
    private LinkedList<Bid_Details> list;
    private String temp1, UpdateBid, SymbolName;
    private int count=0, temp2 =0;
    private Timer timer; //To give some time to update

    public AuctionServer() {
        initComponents();
        timer = new Timer(500, this); //updates every 500ms.
        timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String symbol= SymbolText.getText();
        BidHistory(symbol);
        repaint();
    }

/* method to generate the past bid values of clients of a symbol.*/

    private void BidHistory(String symbol){
        list = allowedUsers.getHistory(symbol);
        try{
            for (int i = temp2; i < list.size() ; i++){
                bidInfo = list.get(i);
                TextAreaSymbol.append(BidInfo_Converted_To_Text(bidInfo) );
                Price.setText(bidInfo.value);
                if (i== list.size()-1)
                    temp2 = list.size();
            }
        }
        catch(Exception e){ }
    }
/*converting bid details into string so that it can be shown in the GUI*/
    private String BidInfo_Converted_To_Text(Bid_Details bidinfo){
        count++;
        if(bidinfo.name.equals("SERVER"))
            temp1=count+". "+bidinfo.name+" sets Bid price to "+bidinfo.value+"\n" ;

        else
            temp1=count+"."+bidinfo.name+" bids for "+bidinfo.value+"\n" ;
        return temp1;
    }
    /* GUI was created using netbeens gui tool to save some time instead of hardcoding it.*/
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        SymbolSelect = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TextAreaSymbol = new javax.swing.JTextArea();
        Update = new javax.swing.JButton();
        Search = new javax.swing.JButton();
        SymbolText = new javax.swing.JTextField();
        UpdateVal = new javax.swing.JTextField();
        Price = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SymbolSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FB","VRTU", "MSFT","GOOGL","YHOO","XLNX","TSLA","TXN"}));


        SymbolSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SymbolSelectActionPerformed(evt);
            }
        });

        TextAreaSymbol.setColumns(20);
        TextAreaSymbol.setRows(5);
        jScrollPane1.setViewportView(TextAreaSymbol);

        Update.setText("UPDATE PRICE");
        Update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateActionPerformed(evt);
            }
        });

        Search.setText("SEARCH");
        Search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SearchActionPerformed(evt);
            }
        });

        SymbolText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SymbolTextActionPerformed(evt);
            }
        });

        UpdateVal.setText("");

        Price.setText("PRICE");
        Name.setText("COMPANY NAME");

        exit.setText("EXIT");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(SymbolSelect, 0, 120, Short.MAX_VALUE)
                                        .addComponent(SymbolText))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Search)
                                .addGap(31, 31, 31)
                                .addComponent(Update)
                                .addGap(40, 40, 40)
                                .addComponent(UpdateVal, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(31, 31, 31)
                                                .addComponent(exit)
                                                .addContainerGap())
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(Name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addContainerGap())
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(35, 35, 35))))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(SymbolText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(SymbolSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(Search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(Update, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(UpdateVal, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(20, 20, 20)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(43, 43, 43)
                                                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(Price, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(exit)))
                                .addContainerGap(24, Short.MAX_VALUE))
        );


        SymbolSelect.getAccessibleContext().setAccessibleName("Item");
        SymbolText.getAccessibleContext().setAccessibleName("symbolList");
        SymbolText.getAccessibleContext().setAccessibleDescription("");

        getAccessibleContext().setAccessibleName("SymbolBox");

        pack();
    }
    private void SymbolSelectActionPerformed(java.awt.event.ActionEvent evt) {
        String text=(String)SymbolSelect.getSelectedItem();
        SymbolText.setText(text);
        TextAreaSymbol.setText("");
        temp2 =0; count=0;

        String CompanyName = allowedUsers.findName(text);
        String StockPrice  =allowedUsers.findPrice(text);
        Name.setText(CompanyName);
        Price.setText(StockPrice);
    }
//when update button is clicked ,the relevan symbol price gets updated.
    private void UpdateActionPerformed(java.awt.event.ActionEvent evt) {
        UpdateBid = UpdateVal.getText();
        SymbolName = SymbolText.getText();
        if(server.isAuthorized(SymbolName)){
            allowedUsers.updatePrice_in_Database(SymbolName, UpdateBid);
            list = allowedUsers.getHistory(SymbolName);
            bidInfo = new Bid_Details(UpdateBid,"SERVER");
            Price.setText(UpdateBid);
            list.add(bidInfo);
            BidHistory(SymbolName);
        }

    }
    //by clicking search,the symbol in the SymbolText is searched and all its past bid records and present bid value is shown in the gui .
    private void SearchActionPerformed(java.awt.event.ActionEvent evt) {
        String SymbolName = SymbolText.getText();
        if(server.isAuthorized(SymbolName)){
            String CompanyName = allowedUsers.findName(SymbolName);
            String StockPrice  =allowedUsers.findPrice(SymbolName);
            Name.setText(CompanyName);
            Price.setText(StockPrice);
            TextAreaSymbol.setText("");
            temp2 =0; count=0;
        }
        else{
            String	Msg3="Enter A Valid Symbol to proceed.";
            JOptionPane.showMessageDialog (null,Msg3 , "Invalid!",JOptionPane.PLAIN_MESSAGE);
        }

           }

    private void SymbolTextActionPerformed(java.awt.event.ActionEvent evt) {

    }


    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AuctionServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AuctionServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AuctionServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AuctionServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AuctionServer().setVisible(true);
            }

        });
        server.server_loop();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton Search;
    private javax.swing.JComboBox<String> SymbolSelect;
    private javax.swing.JTextField SymbolText;
    private javax.swing.JTextArea TextAreaSymbol;
    private javax.swing.JButton Update;
    private javax.swing.JTextField UpdateVal;
    private javax.swing.JButton exit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel Price;
    private javax.swing.JLabel Name;





}
