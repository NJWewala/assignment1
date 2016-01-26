// Niresh Wewala, my own exception that handles an invalid request to the server.
// Last edited: 21st January 2016.

@SuppressWarnings("serial")
class MyException extends Exception {
	   public MyException(String msg){
	      super(msg);
	   }
}
