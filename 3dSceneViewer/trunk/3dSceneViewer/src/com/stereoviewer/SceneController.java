package com.stereoviewer;

import com.scenecontrol.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class SceneController extends Thread{
	private	ServerSocket server;
	private Socket socket;
	private BufferedReader input;
	private DataOutputStream output=null;
	private boolean quit=false;
	SceneViewer master;

	public SceneController(int port, SceneViewer master) throws IOException{
		this.master=master;
		server = new ServerSocket(port); 
	}

	public void init() throws IOException{
		socket = server.accept(); 
		input= new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output=new DataOutputStream(socket.getOutputStream());
	}

	public void run(){
		try {
			init();
			System.out.println("Client connected");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(!quit){
			try {
				String temp=input.readLine();
				if(temp!=null){
					Command.executeCommand(temp, master);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isQuit() {
		return quit;
	}

	public void setQuit(boolean quit) {
		this.quit = quit;
	}

}
