// Server.java
// Niresh Wewala, referenced from SimpleEchoServer from SYSC 3303 example. I have made further changes 
// that works for this assignment.
// Last edited: 21st January 2016.

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Server {

   DatagramPacket sendPacket, receivePacket;
   DatagramSocket sendSocket, receiveSocket;

   public Server()
   {
      try {
         // Construct a datagram socket and bind it to port 5000 
         // on the local host machine. This socket will be used to
         // receive UDP Datagram packets.
         receiveSocket = new DatagramSocket(69);
         
         // to test socket timeout (2 seconds)
         //receiveSocket.setSoTimeout(2000);
      } catch (SocketException se) {
         se.printStackTrace();
         System.exit(1);
      } 
   }

   public void receiveAndEcho() throws MyException
   {
      //server runs "forever" but closes its created socket at the end of every send response.
	  while(true) 
	  {
		  // Construct a DatagramPacket for receiving packets up 
	      // to 100 bytes long (the length of the byte array). 
		  byte data[] = new byte[100];
		  receivePacket = new DatagramPacket(data, data.length);
	      System.out.println("\nServer: Waiting for Packet.\n");
	
	      // Block until a datagram packet is received from receiveSocket.
	      try { 
	    	  System.out.println("Waiting..."); // so we know we're waiting
	    	  receiveSocket.receive(receivePacket);
	         } catch (IOException e) {
	        	 System.out.print("IO Exception: likely:");
	        	 System.out.println("Receive Socket Timed Out.\n" + e);
	        	 e.printStackTrace();
	        	 System.exit(1);
	        	 }
	      
	      // Process the received datagram.
	      System.out.println("Server: Packet received:");
	      System.out.println("From host: " + receivePacket.getAddress());
	      System.out.println("Host port: " + receivePacket.getPort());
	      int len = receivePacket.getLength();
	      System.out.println("Length: " + len);
	      System.out.println("Containing: " );
	      byte fromHost[] = new byte[len];
	      System.arraycopy(data, 0, fromHost, 0, fromHost.length);
	      // Form a String from the byte array.
	      System.out.print("Byte array: ");
	      for (byte c : fromHost){
	    	  System.out.format("%s ",c);
	    	  }
	      System.out.println();
	      String received1 = new String(fromHost,0,len);   
	      System.out.println("String array: "+received1+"\n");
	      
	      // Slow things down (wait 5 seconds)
	      try {
	    	  Thread.sleep(5000);
	    	  } catch (InterruptedException e ) {
	    		  e.printStackTrace();
	    		  System.exit(1);
	    		  }
	      
	      //hard-coded byte array to check receiving packet from intermediate
	      byte validRead[] = {0,3,0,1};
	      byte validWrite[] = {0,4,0,0};
	      byte beginsWith[] = {0,1,116,101,115,116,109,101,46,116,120,116,0,111,99,116,101,116,0};
	      byte beginsWith1[] = {0,2,116,101,115,116,109,101,46,116,120,116,0,111,99,116,101,116,0};
  
	      try{
		      if(Arrays.equals(fromHost, beginsWith)){  
				  //System.out.println("pass");
				  sendPacket = new DatagramPacket(validRead, validRead.length,
						  	InetAddress.getLocalHost(), receivePacket.getPort());
				  }else if (Arrays.equals(fromHost, beginsWith1)){
					  //System.out.println("pass");
					  sendPacket = new DatagramPacket(validWrite, validWrite.length,
							  InetAddress.getLocalHost(), receivePacket.getPort());
					  }else{
						  throw new MyException("Invalid Read (or) Write Request!");
						  }
		      } catch (UnknownHostException e) {
		    	  e.printStackTrace();
		    	  System.exit(1);
		    	  }

	      System.out.println( "Server: Sending packet:");
	      System.out.println("To host: " + sendPacket.getAddress());
	      System.out.println("Destination host port: " + sendPacket.getPort());
	      len = sendPacket.getLength();
	      System.out.println("Length: " + len);
	      System.out.println("Containing: ");
	      System.out.print("Byte array: ");
	      for (byte d : sendPacket.getData()){
	    	  System.out.format("%s ",d);
	      }
	      System.out.print("\nString array: ");
	      System.out.println(new String(sendPacket.getData(),0,len));
	      
	      try {
	    	  // Construct a datagram socket and bind it to any available 
	          // port on the local host machine. This socket will be used to
	          // send UDP Datagram packets.
	    	  sendSocket = new DatagramSocket();
	          sendSocket.send(sendPacket);
	          } catch (IOException e) {
	        	  e.printStackTrace();
	        	  System.exit(1);
	        	  }
	
	      System.out.println("Server: packet sent");
	      
	      // We're finished, so close the created send socket.
	      sendSocket.close();
	  }
   }

   public static void main( String args[] )
   {
	   Server c = new Server();
	   try {
		   c.receiveAndEcho();
		} catch (MyException e) {
			e.printStackTrace();
			}	
   }
   
}
