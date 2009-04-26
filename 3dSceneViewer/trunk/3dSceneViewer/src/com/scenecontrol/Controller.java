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
	public void setObjectColorAmbient(String name, float r, float g, float b) throws IOException{
		commands+=Command.setObjectColorAmbient+" "+name+" "+r+" "+g+" "+b+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectColorDiffuse(String name, float r, float g, float b) throws IOException{
		commands+=Command.setObjectColorDiffuse+" "+name+" "+r+" "+g+" "+b+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setObjectColorSpecular(String name, float r, float g, float b) throws IOException{
		commands+=Command.setObjectColorSpecular+" "+name+" "+r+" "+g+" "+b+" "+Command.commandSeperator;

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
	/*
	 * *********************Camera Commands****************************************
	 */
	public void setCameraPosition( double x, double y, double z) throws IOException{
		commands+=Command.setCameraPosition+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}

	public void setCameraTarget( double x, double y, double z) throws IOException{
		commands+=Command.setCameraTarget+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}

	public void setCameraUpVector( double x, double y, double z) throws IOException{
		commands+=Command.setCameraUpVector+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setCameraFovNF( double Fov, double N, double F) throws IOException{
		commands+=Command.setCameraFovNF+" "+Fov+" "+N+" "+F+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setCameraIOD( double IOD) throws IOException{
		commands+=Command.setCameraIOD+" "+IOD+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	/*
	 * *********************Lighting Commands****************************************
	 */
	public void setGlobalLightValues(float r, float g, float b,float a) throws IOException {
		commands+=Command.setGlobalLightValues+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setClearColor(float r, float g, float b,float a) throws IOException {
		commands+=Command.setClearColor+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}

	public void setLightOptions(boolean local_viewer, boolean two_side) throws IOException {
		commands+=Command.setLightOptions+" "+local_viewer+" "+two_side+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setLightColorAmbient(String name, float r, float g, float b,float a) throws IOException {
		commands+=Command.setLightColorAmbient+" "+name+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setLightColorDiffuse(String name, float r, float g, float b,float a) throws IOException {
		commands+=Command.setLightColorDiffuse+" "+name+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setLightColorSpecular(String name, float r, float g, float b,float a) throws IOException {
		commands+=Command.setLightColorSpecular+" "+name+" "+r+" "+g+" "+b+" "+a+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setLightValues(String name,float intensity, float constant_attenuation_constant,
			float linear_attenuation_constant,float quad_attenuation_constant) throws IOException {

		commands+=Command.setLightValues+" "+name+" "+intensity+" "+constant_attenuation_constant+
		" "+linear_attenuation_constant+" "+quad_attenuation_constant+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}
	public void setLightPosition(String name,double x, double y, double z) throws IOException {

		commands+=Command.setLightPosition+" "+name+" "+x+" "+y+" "+z+" "+Command.commandSeperator;

		if(!bufferCommands){
			flushCommands();
		}
	}


	/*
	 * *********************Commands end****************************************
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
			controller.setObjectScale("ball2", 1, 2, 1);

			Thread.sleep(1000);
			controller.setObjectTransparency("ball2", 0.5f);

			Thread.sleep(1000);
			controller.setObjectColorDiffuse("ball2", 1, 0, 0);
			controller.setObjectColorAmbient("ball2", 1, 0, 0);
			controller.setObjectColorSpecular("ball2", 1, 0, 0);
			Thread.sleep(1000);


			controller.quit();
			controller.flushCommands();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
