package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class MainServer { 

    /* Some constants */     
    public static final int BASE_PORT = 2000;  // do not change


    /* local data for the server 
     * Every main server is defined in terms of the port it 
     * listens and the database of allowed users 
     */ 
    private ServerSocket serverSocket=null;  // server Socket for main server 
    private StockDatabase DB_Stock = null;   // who are allowed to bid
	private LinkedList<Bid_Details> List; // To keep bid information of clients
    public MainServer(int socket, StockDatabase users) {
	this.DB_Stock = users;

	try { 
	    this.serverSocket = new ServerSocket(socket); 
	} catch (IOException e) { 
	    System.out.println(e); 
	}
    }

    /* each server will provide the following functions to 
     * the public. Note that these are non-static 
     */ 
    public boolean isAuthorized(String Symbol) { // To check whether the symbol is correctly entered.This method need not be synchronised as many users can bid on the same symbol at the same time
	return this.DB_Stock.findName(Symbol) != null;
    }    

    public String getSymbol(String symbol) { //Method to find the relevant name of the Symbol.

	return this.DB_Stock.findName(symbol);
    }
	public String getPrice(String symbol) {//Method to get the relevant price for the relavant symbol.

		return this.DB_Stock.findPrice(symbol);
	}

	public StockDatabase getDB(){
		return DB_Stock;
	} //To return the database

	public synchronized boolean LinkUpdate(String Symbol , Bid_Details data , String CurrentBid) { //adds bidding values and names of the clients to relevant lists of relevant symbols.
		try{
			if(this.DB_Stock.updatePrice(Symbol, CurrentBid)){ // checks if the bid is greater than the current value and if so return true.
				List = DB_Stock.getHistory(Symbol);	     //required list of the relevant symbol.
				List.add(data);							//add data to the linked list
			}
			else return false;
		}
		catch(NullPointerException e){
			return false;

		}
		return true;
	}





    public void server_loop() { // method to listen to the socket.
	try { 
	    while(true) { 
		Socket socket = this.serverSocket.accept(); //accepting a socket
		ConnectionServer worker = new ConnectionServer(this); //creating an instance of connection server called worker.
		worker.handleConnection(socket); // invoking the method handle connection.(threading is used here.
	    }
	} catch(IOException e) { 
	    System.out.println(e);
	}
    }
}


	




