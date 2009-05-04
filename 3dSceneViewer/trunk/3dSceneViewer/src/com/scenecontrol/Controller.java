package com.scenecontrol;


import java.net.*;
import java.io.*;

import javax.vecmath.Color4f;

import com.stereoviewer.SceneLight;
import com.stereoviewer.SceneViewer;

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
	/*
	 * *********************Commands start****************************************
	 */

	/**
	 * Loads the designated scene into the viewer
	 * @param path path to the scene to load
	 * @throws IOException
	 */
	public void loadScene(String path) throws IOException{
		commands+=Command.loadScene+" "+path+Command.commandSeperator;

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
	/*
	 * *********************Object Commands****************************************
	 */
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param z
	 * @throws IOException
	 */
	public void setObjectPosition(String name, double x, double y, double z) throws IOException{
		commands+=Command.setObjectPosition+" "+name+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param z
	 * @param angle
	 * @throws IOException
	 */
	public void setObjectRotation(String name, double x, double y, double z,double angle) throws IOException{
		commands+=Command.setObjectRotation+" "+name+" "+x+" "+y+" "+z+" "+angle+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param z
	 * @throws IOException
	 */
	public void setObjectScale(String name, double x, double y, double z) throws IOException{
		commands+=Command.setObjectScale+" "+name+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 * @throws IOException
	 */
	public void setObjectColorAmbient(String name, float r, float g, float b) throws IOException{
		commands+=Command.setObjectColorAmbient+" "+name+" "+r+" "+g+" "+b+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 * @throws IOException
	 */
	public void setObjectColorDiffuse(String name, float r, float g, float b) throws IOException{
		commands+=Command.setObjectColorDiffuse+" "+name+" "+r+" "+g+" "+b+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 * @throws IOException
	 */
	public void setObjectColorSpecular(String name, float r, float g, float b) throws IOException{
		commands+=Command.setObjectColorSpecular+" "+name+" "+r+" "+g+" "+b+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param i
	 * @throws IOException
	 */
	public void setObjectTransparency(String name, float i) throws IOException{
		commands+=Command.setObjectTransparency+" "+name+" "+i+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param draw
	 * @throws IOException
	 */
	public void setObjectDraw(String name, boolean draw) throws IOException{
		commands+=Command.setObjectDraw+" "+name+" "+draw+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/*
	 * *********************Camera Commands****************************************
	 */
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @throws IOException
	 */
	public void setCameraPosition( double x, double y, double z) throws IOException{
		commands+=Command.setCameraPosition+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @throws IOException
	 */
	public void setCameraTarget( double x, double y, double z) throws IOException{
		commands+=Command.setCameraTarget+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @throws IOException
	 */
	public void setCameraUpVector( double x, double y, double z) throws IOException{
		commands+=Command.setCameraUpVector+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param Fov
	 * @param N
	 * @param F
	 * @throws IOException
	 */
	public void setCameraFovNF( double Fov, double N, double F) throws IOException{
		commands+=Command.setCameraFovNF+" "+Fov+" "+N+" "+F+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param IOD
	 * @throws IOException
	 */
	public void setCameraIOD( double IOD) throws IOException{
		commands+=Command.setCameraIOD+" "+IOD+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}

	/*
	 * *********************Lighting Commands****************************************
	 */
	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @throws IOException
	 */
	public void setGlobalLightValues(float r, float g, float b,float a) throws IOException {
		commands+=Command.setGlobalLightValues+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @throws IOException
	 */
	public void setClearColor(float r, float g, float b,float a) throws IOException {
		commands+=Command.setClearColor+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param local_viewer
	 * @param two_side
	 * @throws IOException
	 */
	public void setLightOptions(boolean local_viewer, boolean two_side) throws IOException {
		commands+=Command.setLightOptions+" "+local_viewer+" "+two_side+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @throws IOException
	 */
	public void setLightColorAmbient(String name, float r, float g, float b,float a) throws IOException {
		commands+=Command.setLightColorAmbient+" "+name+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @throws IOException
	 */
	public void setLightColorDiffuse(String name, float r, float g, float b,float a) throws IOException {
		commands+=Command.setLightColorDiffuse+" "+name+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 * @throws IOException
	 */
	public void setLightColorSpecular(String name, float r, float g, float b,float a) throws IOException {
		commands+=Command.setLightColorSpecular+" "+name+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param enable
	 * @throws IOException
	 */
	public void setLightOn(String name,boolean enable) throws IOException {
		commands+=Command.setLightOn+" "+name+" "+enable+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param intensity
	 * @param constant_attenuation_constant
	 * @param linear_attenuation_constant
	 * @param quad_attenuation_constant
	 * @throws IOException
	 */
	public void setLightValues(String name,float intensity, float constant_attenuation_constant,
			float linear_attenuation_constant,float quad_attenuation_constant) throws IOException {

		commands+=Command.setLightValues+" "+name+" "+intensity+" "+constant_attenuation_constant+
		" "+linear_attenuation_constant+" "+quad_attenuation_constant+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param z
	 * @throws IOException
	 */
	public void setLightPosition(String name,double x, double y, double z) throws IOException {
		commands+=Command.setLightPosition+" "+name+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}

	/*
	 * *********************Commands end****************************************
	 */
	/**
	 * 
	 * @throws IOException
	 */
	public void flushCommands() throws IOException{
		if(socket.isConnected()){
			output.writeBytes(commands);
			commands="";
		}
		else{
			System.out.println("error socket is not connected");
		}
	}
	/**
	 * 
	 * @return true or false as to whether the bufferCommands flag is enabled
	 */
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

			controller.setBufferCommands(false);

			controller.loadScene("data/Scene1.xml");

			Thread.sleep(1000);
			controller.setObjectScale("pound2", 1, 2, 1);

			Thread.sleep(1000);
			controller.setObjectTransparency("pound2", 0.5f);

			Thread.sleep(1000);
			controller.setObjectColorDiffuse("pound2", 1, 0, 0);
			controller.setObjectColorAmbient("pound2", 1, 0, 0);
			controller.setObjectColorSpecular("pound2", 1, 0, 0);
			Thread.sleep(1000);

			controller.setCameraUpVector(1, 1, 0);
			Thread.sleep(1000);

			controller.setCameraPosition(0, 100, 1);
			Thread.sleep(1000);
			controller.setCameraTarget(1, 0, 1);
			Thread.sleep(1000);

			controller.quit();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
