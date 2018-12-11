package com.company;

import java.io.*;
import java.util.*;
/*This is the main database used.All the stock database handling methods are implemented here.
 */
public  class StockDatabase {

	/*Three separate hashmaps fo symbol price and client data.
	Client data is usd to keep client bid records with their names and bid prices.For this we use a linked list.
	*/
	private Map<String, String> symbolList;
	private Map<String, String> priceList;
	private Map<String, LinkedList<Bid_Details>> ClientData;



	private String[] fields; //Sting array

	public StockDatabase(String csvFile, String key, String name, String price) {//constructer inlcuding the csv file and keys in the csv file
		FileReader fileRd = null;
		BufferedReader reader = null;

		try {
			fileRd = new FileReader(csvFile);
			reader = new BufferedReader(fileRd);

	    /* read the CSV file's first line which has 
	     * the names of fields. 
	     */
			String header = reader.readLine();
			fields = header.split(",");// keep field names

			// find where the key and the value are
			int keyIndex = findIndexOf(key);
			int nameIndex = findIndexOf(name);
			int priceIndex = findIndexOf(price);

			if (keyIndex == -1 || nameIndex == -1 || priceIndex == -1)
				throw new IOException("CSV file does not have data");
			// note how you can throw a new exception

         /*creating three separate new Hashmaps */
			symbolList = new HashMap<>();
			priceList = new HashMap<>();
			ClientData = new HashMap<>();

	    /* read each line, getting it split by , 
	     * use the indexes to get the key and value 
	     */
			String[] tokens;
			for (String line = reader.readLine();
				 line != null;
				 line = reader.readLine()) {
				tokens = line.split(",");

				symbolList.put(tokens[keyIndex], tokens[nameIndex]);
				priceList.put(tokens[keyIndex], tokens[priceIndex]);
				ClientData.put(tokens[keyIndex], new LinkedList<Bid_Details>());

			}

			if (fileRd != null) fileRd.close();
			if (reader != null) reader.close();

			// I can catch more than one exceptions
		} catch (IOException e) {
			System.out.println(e);
			System.exit(-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Malformed CSV file");
			System.out.println(e);
		}
	}

	private int findIndexOf(String key) {     //method to find where the key is
		for (int i = 0; i < fields.length; i++)
			if (fields[i].equals(key)) return i;
		return -1;
	}

	//To find the name of the company.
	public String findName(String key) {
		return symbolList.get(key);
	}
	//To find the price of the company.
	public String findPrice(String key) {
		return priceList.get(key);
	}
   //To get the client information according to the symbol.This method should be synchronised as many users can bid at the same  time.
	public synchronized LinkedList<Bid_Details> getHistory(String key) {
		return ClientData.get(key);
	}
 // To update the bidding value of the relevant symbol.This also should be synchronised as many users can do this at the same time.
	public synchronized boolean updatePrice(String symbol, String Current_Bid) {
		String test;
		test= priceList.get(symbol); //get the price of the symbol required in the csv file
		if(test.equals(symbol)) //check if it equals the clients bid.
			return false;
		else if (Double.parseDouble(test)>Double.parseDouble(Current_Bid)) //check if clients bid is more than the value in the csv file.
			return false;
		else
			priceList.put(symbol,Current_Bid);
		return true;
	}
	// database can be updated by setting the new price and putting it to the pricelist.The relevant symbol must also be an input.
	public boolean updatePrice_in_Database(String key,String price){
		String test;
		test=priceList.get(key);
		if(test.equals(price))
			return false;
		else
			priceList.put(key,price);
		return true;
	}


}