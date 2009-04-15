package com.scenecontrol;


import java.net.*;
import java.io.*;

public class Controller {
	private boolean bufferCommands=true;
	private Socket socket=null;
	private BufferedReader input=null;
	private DataOutputStream output=null;
	String commands="";
	public Controller(int port,String host) throws UnknownHostException, IOException{
		socket=new Socket(host,port);
		init();
	}
	public Controller(int port) throws UnknownHostException, IOException {
		socket=new Socket("127.0.0.1",port);
		init();
	}
	public Controller() throws UnknownHostException, IOException {
		int port=4567;
		socket=new Socket("127.0.0.1",port);
		init();
	}
	private void init() throws IOException{
		if(socket.isConnected()){
			output=new DataOutputStream(socket.getOutputStream());
			input=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
	}

	public void close() throws IOException{
		if(socket.isConnected()){
			socket.close();
		}
	}

	public void loadScene(String path) throws IOException{
		commands+=Command.loadScene+" "+path+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}

	public void setObjectPosition(String name, double x, double y, double z) throws IOException{
		commands+=Command.setObjectPosition+" "+name+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectRotation(String name, double i, double j, double k,double angle) throws IOException{
		commands+=Command.setObjectRotation+" "+name+" "+i+" "+j+" "+k+" "+angle+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * quits the application and closes the socket
	 * @throws IOException
	 */
	public void quit() throws IOException{
		commands+=Command.quit+" "+Command.commandSeperator;
		flushCommands();
		socket.close();
	}

	public void flushCommands() throws IOException{
		if(socket.isConnected()){
			output.writeBytes(commands);
			commands="";
		}
		else{
			System.out.println("error socket is not connected");
		}
	}
	public boolean isBufferCommands() {
		return bufferCommands;
	}
	/**
	 * clears all commands that are currently buffered and sets the buffer commands flag
	 * @param bufferCommands
	 */
	public void setBufferCommands(boolean bufferCommands) {
		this.bufferCommands = bufferCommands;
		commands="";
	}
	public static void main(String args[]) throws InterruptedException{
		try {
			Controller controller=new Controller(6789);
			int i=0;
			while(true)
			{
				controller.setObjectRotation("ball1", 0, 1,0,i/10.);
				controller.setObjectRotation("ball2", 1, 0,0,i/10.);
				controller.flushCommands();
				i+=30;
				Thread.sleep(10);
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
