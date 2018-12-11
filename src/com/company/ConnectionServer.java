package com.company;




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionServer implements Runnable {
	/*Three states of clients WAIt_NAME,WAIT_SYMBOL,USER_INPUT is declared.*/
    public static final int WAIT_NAME = 0;
    public static final int WAIT_SYMBOL = 1;
    public static final int USER_INPUT = 2;
/*Some messages used at the console for clients*/

	public static final String WAIT_SYMBOL_MSG = "Enter a valid Symbol to proceed.\n";
	public static final String MSG_POSTED      = "Bid again or type quit\n";
    public static final String INVALID         = "Enter Valid Data.\n";



    // per connection variables
    private Socket mySocket; // connection socket per thread 
    private int currentState; 
    private String Company;
    private String CurrentPrice;
    private String ClientName;

    private String symbol;
    private MainServer mainServer;
/*all connections are through the main server. hence "this.main server = main server is used.*/
    public ConnectionServer(MainServer mainServer) { 
	this.mySocket = null; // we will set this later 
	this.currentState = WAIT_NAME;
	this.Company = null;
	this.CurrentPrice = null;
	this.ClientName = null;
	this.mainServer = mainServer;
		this.symbol = null;

    }

    public boolean handleConnection(Socket socket) { 
	this.mySocket = socket; 
	Thread newThread = new Thread(this); //threading is used to do things parallely.
	newThread.start(); 
	return true; 
    }

    public void run() { // can not use "throws .." interface is different

//try part explains what is done when the rnu method or the connection server instance is created at the main server.
	try {
		BufferedReader in= null;
		PrintWriter out = null;

		in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
	    out = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));

		double Input_Bid;
		Bid_Details temp;

	    String line, outline ;

        out.print("Hello");
		/*Has three cases.
		1.user enter name.
		2.user enters symbol.
		user enters the bid value.
		when user enters quit the user logs out.*/
		for(line = in.readLine();line != null && !line.equals("quit"); line = in.readLine()) {

		switch(this.currentState) {

		case WAIT_NAME: // user has entered the name


			this.ClientName = line; //assigning user name to client.

			outline = this.ClientName + "!!" +WAIT_SYMBOL_MSG; // message to client asking for symbol
			this.currentState = WAIT_SYMBOL;break;  //changing current state in order to switch to the case = WAIT_SYMBOL



				case WAIT_SYMBOL: //user has entered the symbol
					if(mainServer.isAuthorized(line)) {// checks if it matches with a symbol in the csv file.
						this.currentState = USER_INPUT;//changing current state.
						symbol = line; //assigning the user entered value as the symbol.

			Company   =this.mainServer.getSymbol(symbol); //invoking a main server function to get the relevant company name for the symbol.(The idea of Hashmap is used here.)
			CurrentPrice   = this.mainServer.getPrice(symbol); //invoking a main server function to get the relevant price for the symbol.(The idea of Hashmap is used here.)
			outline = "Current price of "+ Company+" is " + CurrentPrice+" .Enter a bid.\n"; //message to the client.
		    }
		    else { 
			outline = INVALID; //if details are wrong this will be initiated.
		    }
		    break;

		case USER_INPUT: //user has entered a bid value.
			try{
				Input_Bid=Double.parseDouble(line);//casting it to double and assigning it to a variable
			}
			catch (NumberFormatException e) {//exception handling
				outline="Enter a valid BID to proceed!\n";
				break;
			}
			CurrentPrice=String.valueOf(Input_Bid); //casting it to string and assignig to current price
			temp=new Bid_Details(CurrentPrice,this.ClientName); //creating a temporary data set of type Bid_Details which keeps client records.

			if(mainServer.LinkUpdate(this.symbol,temp,CurrentPrice))//checking if the bid is valid,if so update the client record list.
			{
				outline="Your Bid is posted.Latest Bid is :"+this.mainServer.getPrice(symbol)+"\n";
				out.print(outline);
				outline = MSG_POSTED;
			}
			else {
				outline="Please Enter a Valid Bid!\n";
				out.print(outline);
				outline="Latest Bid is :"+this.mainServer.getPrice(symbol)+"\n";
			}
			break;
		default: 
		    System.out.println("ERROR.Enter valid data.");
		    return; 
		}

			out.print(outline); // Send the said message

		out.flush(); // flush to network

	    }

	    // close everything 
	    out.close(); 
	    in.close();
	    this.mySocket.close(); 
	} // try 	     
	catch (IOException e) { 
	    System.out.println(e); 
	} 
    }

}

    
    

