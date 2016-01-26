// Intermediate.java
// Niresh Wewala, referenced from SimpleEchoClient-Server/ from SYSC 3303 example. I have made further changes 
// that works for this assignment.
// Last edited: 21st January 2016.

import java.io.IOException;
import java.net.*;

public class Intermediate {
	DatagramPacket sendPacket, receivePacket,receiveFromServer;
	DatagramSocket receiveSocket,sendReceiveSocket,sendSocket;

	public Intermediate()
	{
		try {
			 // Construct a datagram socket and bind it to port 68 
	         // on the local host machine. This socket will be used to
	         // receive UDP Datagram packets.
	         receiveSocket = new DatagramSocket(68);
	         
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send and receive UDP Datagram packets.
	         sendReceiveSocket = new DatagramSocket();
	        
	         // Construct a datagram socket and bind it to any available 
	         // port on the local host machine. This socket will be used to
	         // send UDP Datagram packets.
	         sendSocket = new DatagramSocket();
	         
	      } catch (SocketException se) {   // Can't create the socket.
	         se.printStackTrace();
	         System.exit(1);
	      }
		}
	
	//gets a request from Client and send it to Server on port 69
	public void fromClient()
	{
		  // Construct a DatagramPacket for receiving packets up 
		  // to 100 bytes long (the length of the byte array).
		  //RECEIVE PACKET FROM CLIENT (THIS IS WHERE HOST ACTS LIKE A SERVER)
		  byte data[] = new byte[100];
		  receivePacket = new DatagramPacket(data, data.length);
	    
		  
	      System.out.println("\nIntermediate: Waiting for Packet.\n");
	      // Block until a datagram packet is received from receiveSocket.
	      try {
	    	  System.out.println("Waiting..."); // waiting for a request from client
	    	  receiveSocket.receive(receivePacket);
	    	  } catch (IOException e) {
	    		  System.out.print("IO Exception: likely:");
	    		  System.out.println("Receive Socket Timed Out.\n" + e);
	    		  e.printStackTrace();
	    		  System.exit(1);
	    		  }

	      // Process the received datagram.
	      System.out.println("Intermediate: Packet received:");
	      System.out.println("From host: " + receivePacket.getAddress());
	      System.out.println("Host port: " + receivePacket.getPort());
	      int len = receivePacket.getLength();
	      System.out.println("Length: " + len);
	      System.out.println("Containing: " );
	      byte gotData[] = new byte[len];
	      System.arraycopy(data, 0, gotData, 0, gotData.length);
	      // Form a String from the byte array.
	      System.out.print("Byte array: ");
	      for (byte c : gotData){
	    	  System.out.format("%s ",c);
	      }
	      System.out.println();
	      String received = new String(gotData,0,len);   
	      System.out.println("String array: "+received+"\n");
	      
	      // Slow things down (wait 5 seconds)
	      try {
	    	  Thread.sleep(5000);
	    	  } catch (InterruptedException e ) {
	    		  e.printStackTrace();
	    		  System.exit(1);
	    		  }
	      
	      //HOST SENDING PACKET TO SERVER (HOST ACTS LIKE THE CLIENT)
	      try {
	    	  sendPacket = new DatagramPacket(gotData, gotData.length,
					  					InetAddress.getLocalHost(), 69);
	    	  } catch (UnknownHostException e1) {
	    		  e1.printStackTrace();
	    		  System.exit(1);
	    		  }
	      
	      System.out.println( "Intermediate: Sending packet:");
	      System.out.println("To host: " + sendPacket.getAddress());
	      System.out.println("Destination host port: " + sendPacket.getPort());
	      len = sendPacket.getLength();
	      System.out.println("Length: " + len);
	      System.out.print("Containing: \n");
	      System.out.print("Byte array: ");
	      for (byte c : gotData){
	    	  System.out.format("%s ",c);
	    	  }
	      System.out.print("\nString array: ");
	      System.out.println(new String(sendPacket.getData(),0,len));
	      
	      try {
	    	  sendReceiveSocket.send(sendPacket);
	    	  } catch (IOException e) {
	    		  e.printStackTrace();
	    		  System.exit(1);}

	      System.out.println("Intermediate: packet sent"); 
	      
	}
	
	//gets the response from Server and send it back to client on the receiving port
	public void sendBack()
	{
		  // Construct a DatagramPacket for receiving packets up 
		  // to 100 bytes long (the length of the byte array).
		  //RECEIVE PACKET FROM CLIENT (THIS IS WHERE HOST ACTS LIKE A SERVER)
		  byte dataS[] = new byte[100];
		  receiveFromServer = new DatagramPacket(dataS, dataS.length);
	    
		  
	      System.out.println("\nIntermediate: Waiting for Packet.\n");
	      // Block until a datagram packet is received from receiveSocket.
	      try {
	    	  System.out.println("Waiting..."); // waiting for a response from Server
	    	  sendReceiveSocket.receive(receiveFromServer);
	    	  } catch (IOException e) {
	    		  System.out.print("IO Exception: likely:");
	    		  System.out.println("Receive Socket Timed Out.\n" + e);
	    		  e.printStackTrace();
	    		  System.exit(1);
	    		  }

	      // Process the received datagram.
	      System.out.println("Intermediate: Packet received:");
	      System.out.println("From host: " + receiveFromServer.getAddress());
	      System.out.println("Host port: " + receiveFromServer.getPort());
	      int len1 = receiveFromServer.getLength();
	      System.out.println("Length: " + len1);
	      System.out.println("Containing: " );
	      byte fromServer[] = new byte[len1];
	      System.arraycopy(dataS, 0, fromServer, 0, fromServer.length);
	      // Form a String from the byte array.
	      System.out.print("Byte array: ");
	      for (byte c : fromServer){
	    	  System.out.format("%s ",c);
	    	  }
	      System.out.println();
	      String received1 = new String(fromServer,0,len1);   
	      System.out.println("String array: "+received1+"\n");
	      
	      // Slow things down (wait 5 seconds)
	      try {
	          Thread.sleep(5000);
	          } catch (InterruptedException e ) {
	        	  e.printStackTrace();
	        	  System.exit(1);
	        	  }
	      
	      //FORMS A PACKET TO SEND BACK TO CLIENT
	      sendPacket = new DatagramPacket(fromServer, fromServer.length,
	    		  receivePacket.getAddress(), receivePacket.getPort());
	
	      System.out.println( "\nIntermediate to Client: Sending packet:");
	      System.out.println("To host: " + sendPacket.getAddress());
	      System.out.println("Destination host port: " + sendPacket.getPort());
	      len1 = sendPacket.getLength();
	      System.out.println("Length: " + len1);
	      System.out.println("Containing: ");
	      System.out.print("Byte array: ");
	      for (byte s : fromServer){
	    	  System.out.format("%s ",s);
	    	  }
	      System.out.print("\nString array: ");
	      System.out.println(new String(fromServer,0,len1));
	      
	      try {
	    	  sendSocket.send(sendPacket);
	    	  } catch (IOException e) {
	    		  e.printStackTrace();
	    		  System.exit(1);
	    		  }
	      
	      System.out.println("Host to client: packet sent");
	}
	
	public static void main(String args[])
	{
		Intermediate c = new Intermediate();
		//intermediate runs "forever"
		while(true){
			c.fromClient();
			c.sendBack();
			
		}
	}
}
