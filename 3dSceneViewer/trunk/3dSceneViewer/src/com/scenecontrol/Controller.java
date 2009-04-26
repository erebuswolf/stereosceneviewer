package com.scenecontrol;


import java.net.*;
import java.io.*;

public class Controller {
	private boolean bufferCommands=true;
	private Socket socket=null;
	//unused iput reader if we wanted 2 way communication;
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
	public void setObjectScale(String name, double x, double y, double z) throws IOException{
		commands+=Command.setObjectScale+" "+name+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectColorAmbient(String name, float i, float j, float k) throws IOException{
		commands+=Command.setObjectColorAmbient+" "+name+" "+i+" "+j+" "+k+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectColorDiffuse(String name, float i, float j, float k) throws IOException{
		commands+=Command.setObjectColorDiffuse+" "+name+" "+i+" "+j+" "+k+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectColorSpecular(String name, float i, float j, float k) throws IOException{
		commands+=Command.setObjectColorSpecular+" "+name+" "+i+" "+j+" "+k+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectTransparency(String name, float i) throws IOException{
		commands+=Command.setObjectTransparency+" "+name+" "+i+" "+Command.commandSeperator;

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
			controller.loadScene("data/Scene1.xml");
			controller.flushCommands();

			controller.setObjectTransparency("ball2", 0.5f);
			controller.setObjectColorDiffuse("ball2", 1, 0, 0);
			controller.setObjectColorAmbient("ball2", 1, 0, 0);
			controller.setObjectColorSpecular("ball2", 1, 0, 0);
			controller.flushCommands();

			int i=0;
			while(true)
			{
				controller.setObjectRotation("ball1", 0, 1,0,i/10.);
				controller.setObjectRotation("ball2", 1, 0,0,i/10.);
				controller.flushCommands();
				i+=30;
				Thread.sleep(10);
			}
	//		controller.quit();
		//	controller.flushCommands();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
