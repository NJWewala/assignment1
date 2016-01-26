// Client.java
// Niresh Wewala, referenced from SimpleEchoClient from SYSC 3303 example. I have made further changes 
// that works for this assignment.
// Last edited: 21st January 2016.

import java.io.*;
import java.net.*;

public class Client {

   DatagramPacket sendPacket, receivePacket;
   DatagramSocket sendReceiveSocket;

   public Client()
   {
      try {
         // Construct a datagram socket and bind it to any available 
         // port on the local host machine. This socket will be used to
         // send and receive UDP Datagram packets.
         sendReceiveSocket = new DatagramSocket();
      } catch (SocketException se) {   // Can't create the socket.
         se.printStackTrace();
         System.exit(1);
      }
   }
   
   //function that creates a write request byte array
   public byte[] readRequest()
   {
	   byte aByte = (byte)0b00000000;
	   byte aByte1 = (byte)0b00000001;
	   byte arr[] = {aByte,aByte1};
	   byte filename[] = "testme.txt".getBytes();
	   byte arr2[] = {aByte};
	   byte mode[] = "octet".getBytes();
	   byte arr3[] = {aByte};
	   byte msg[] = new byte[arr.length + filename.length + arr2.length + mode.length + arr3.length];
		  
	   System.arraycopy(arr, 0, msg, 0, arr.length);
	   System.arraycopy(filename, 0, msg, arr.length,filename.length);
	   System.arraycopy(arr2, 0, msg, filename.length+2, arr2.length);
	   System.arraycopy(mode, 0, msg, filename.length+2+arr2.length, mode.length);
	   
	   return msg;
	}
   
   //function that creates a write request byte array
   public byte[] writeRequest()
   {
	   byte aByte = (byte)0b00000000;
	   byte aByte1 = (byte)0b00000010;
	   byte arr[] = {aByte,aByte1};
	   byte filename[] = "testme.txt".getBytes();
	   byte arr2[] = {aByte};
	   byte mode[] = "octet".getBytes();
	   byte arr3[] = {aByte};
	   byte msg[] = new byte[arr.length + filename.length + arr2.length + mode.length + arr3.length];
		  
	   System.arraycopy(arr, 0, msg, 0, arr.length);
	   System.arraycopy(filename, 0, msg, arr.length,filename.length);
	   System.arraycopy(arr2, 0, msg, filename.length+2, arr2.length);
	   System.arraycopy(mode, 0, msg, filename.length+2+arr2.length, mode.length);
	   
	   return msg;
	}
  
   //tried everything to send all 11 requests in one function, but couldn't make it, but it seems right as well
   //sends a read request to Intermediate and on to the server
   public void sendReadRequest()
   {
	  byte msg[] = readRequest();
	  // Prepare a DatagramPacket and send it via sendReceiveSocket
      // to port 68 on the destination (Intermediate) host.
      //  68 - the destination port number on the Intermediate host.
	  try {
		  sendPacket = new DatagramPacket(msg, msg.length, InetAddress.getLocalHost(), 68);
		  } catch (UnknownHostException e) {
			  e.printStackTrace();
			  System.exit(1);
			  }
      
      System.out.println("\nClient: Sending packet:");
      System.out.println("To host: " + sendPacket.getAddress());
      System.out.println("Destination host port: " + sendPacket.getPort());
      int len = sendPacket.getLength();
      System.out.println("Length: " + len);
      System.out.println("Containing: ");
      System.out.print("Byte array: ");
      for (byte c : sendPacket.getData()){
    	  System.out.format("%s ",c);
      }
      System.out.print("\nString array: ");
      System.out.println(new String(sendPacket.getData(),0,len)); // or could print "s"
	  
      // Send the datagram packet to the server via the send/receive socket. 
      try {
    	  sendReceiveSocket.send(sendPacket);
    	  } catch (IOException e) {
    		  e.printStackTrace();
    		  System.exit(1);
    		  }
      
      System.out.println("Client: Packet sent.\n");
      
      // Construct a DatagramPacket for receiving packets up 
      // to 100 bytes long (the length of the byte array).
      byte data[] = new byte[100];
      receivePacket = new DatagramPacket(data, data.length);

      try {
    	  // Block until a datagram is received via sendReceiveSocket.
    	  sendReceiveSocket.receive(receivePacket);
    	  } catch(IOException e) {
    		  e.printStackTrace();
    		  System.exit(1);
    		  }

      // Process the received datagram.
      System.out.println("Client: Packet received:");
      System.out.println("From host: " + receivePacket.getAddress());
      System.out.println("Host port: " + receivePacket.getPort());
      len = receivePacket.getLength();
      System.out.println("Length: " + len);
      System.out.println("Containing: ");
      byte fromHost[] = new byte[len];
      System.arraycopy(data, 0, fromHost, 0, fromHost.length);
      // Form a String from the byte array.
      System.out.print("Byte array: ");
      for (byte d : fromHost){
    	  System.out.format("%s ",d);
      }
      System.out.println();
      String received = new String(data,0,len);   
      System.out.println("String array: "+received);
      
      // Slow things down (wait 5 seconds) between sending back another packet
      try {
    	  System.out.println("\nSending next packet....");
          Thread.sleep(5000);
          } catch (InterruptedException e ) {
        	  e.printStackTrace();
        	  System.exit(1);
        	  } 
   }
   
   public void sendWriteRequest()
   {
	  byte msg1[] = writeRequest();
	  // Prepare a DatagramPacket and send it via sendReceiveSocket
      // to port 68 on the destination (Intermediate) host.
      //  68 - the destination port number on the Intermediate host.
	  try {
		  sendPacket = new DatagramPacket(msg1, msg1.length, InetAddress.getLocalHost(), 68);
		  } catch (UnknownHostException e) {
			  e.printStackTrace();
			  System.exit(1);
			  }
	  
	  System.out.println("\nClient: Sending packet:");
      System.out.println("To host: " + sendPacket.getAddress());
      System.out.println("Destination host port: " + sendPacket.getPort());
      int len = sendPacket.getLength();
      System.out.println("Length: " + len);
      System.out.println("Containing: ");
      System.out.print("Byte array: ");
      for (byte c : sendPacket.getData()){
    	  System.out.format("%s ",c);
      }
      System.out.print("\nString array: ");
      System.out.println(new String(sendPacket.getData(),0,len));
      
      // Send the datagram packet to the server via the send/receive socket. 
      try {
    	  sendReceiveSocket.send(sendPacket);
    	  } catch (IOException e) {
    		  e.printStackTrace();
    		  System.exit(1);
    		  }
      
      System.out.println("Client: Packet sent.\n");
      
      // Construct a DatagramPacket for receiving packets up 
      // to 100 bytes long (the length of the byte array).
      byte data[] = new byte[100];
      receivePacket = new DatagramPacket(data, data.length);

      try {
    	  // Block until a datagram is received via sendReceiveSocket.
    	  sendReceiveSocket.receive(receivePacket);
    	  } catch(IOException e) {
    		  e.printStackTrace();
    		  System.exit(1);
    		  }

      // Process the received datagram.
      System.out.println("Client: Packet received:");
      System.out.println("From host: " + receivePacket.getAddress());
      System.out.println("Host port: " + receivePacket.getPort());
      len = receivePacket.getLength();
      System.out.println("Length: " + len);
      System.out.println("Containing: ");
      byte fromHost[] = new byte[len];
      System.arraycopy(data, 0, fromHost, 0, fromHost.length);
      // Form a String from the byte array.
      System.out.print("Byte array: ");
      for (byte d : fromHost){
    	  System.out.format("%s ",d);
      }
      System.out.println();
      String received = new String(data,0,len);   
      System.out.println("String array: "+received);
      
      // Slow things down (wait 5 seconds) between sending back another packet
      try {
    	  System.out.println("\nSending next packet....");
          Thread.sleep(5000);
          } catch (InterruptedException e ) {
        	  e.printStackTrace();
        	  System.exit(1);
        	  }
   }
   
   public void inValidRequest()
   {
	   byte inValid[] = {4,5,6}; 
	   // Prepare a DatagramPacket and send it via sendReceiveSocket
	   // to port 68 on the destination (Intermediate) host.
	   //  68 - the destination port number on the Intermediate host.
	   try {
		   sendPacket = new DatagramPacket(inValid, inValid.length, InetAddress.getLocalHost(), 68); 
		   } catch (UnknownHostException e) {
			   e.printStackTrace();
			   System.exit(1);
			   }
	   
	   System.out.println("\nClient: Sending packet:");
	   System.out.println("To host: " + sendPacket.getAddress());
	   System.out.println("Destination host port: " + sendPacket.getPort());
	   int len = sendPacket.getLength();
	   System.out.println("Length: " + len);
	   System.out.println("Containing: ");
	   System.out.print("Byte array: ");
	   for (byte c : sendPacket.getData()){
		   System.out.format("%s ",c);
		   }
	   System.out.print("\nString array: ");
	   System.out.println(new String(sendPacket.getData(),0,len));
	   
	   // Send the datagram packet to the server via the send/receive socket. 
	   try {
		   sendReceiveSocket.send(sendPacket);
		   } catch (IOException e) {
			   e.printStackTrace();
			   System.exit(1);
			   }
	   
	   System.out.println("Client: Packet sent.\n");
	   
	   // Construct a DatagramPacket for receiving packets up 
	   // to 100 bytes long (the length of the byte array).
	   byte data[] = new byte[100];
	   receivePacket = new DatagramPacket(data, data.length);

	   try {
		   // Block until a datagram is received via sendReceiveSocket. 
		   sendReceiveSocket.receive(receivePacket);
		   } catch(IOException e) {
			   e.printStackTrace();
			   System.exit(1);
			   }
	   
	   // Process the received datagram.
	   System.out.println("Client: Packet received:");
	   System.out.println("From host: " + receivePacket.getAddress());
	   System.out.println("Host port: " + receivePacket.getPort());
	   len = receivePacket.getLength();
	   System.out.println("Length: " + len);
	   System.out.println("Containing: ");
	   byte fromHost[] = new byte[len];
	   System.arraycopy(data, 0, fromHost, 0, fromHost.length);
	   // Form a String from the byte array.
	   System.out.print("Byte array: ");
	   for (byte d : fromHost){
		   System.out.format("%s ",d);
		   }
	   System.out.println();
	   String received = new String(data,0,len);
	   System.out.println("String array: "+received);
	   
	   // Slow things down (wait 5 seconds) between sending back another packet
	   try {
		   System.out.println("\nSending next packet....");
		   Thread.sleep(5000);
		   } catch (InterruptedException e ) {
			   e.printStackTrace();
			   System.exit(1);
			   }
   }
   
   public static void main(String args[])
   {
	   Client c = new Client();
	   for(int i = 0; i < 5; i++){
		   c.sendReadRequest();
	   }
	   for(int j = 0; j < 5; j++){
		   c.sendWriteRequest();
	   }
	   c.inValidRequest();
   }
   
}
