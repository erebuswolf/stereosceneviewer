package com.scenecontrol;


import java.util.ArrayList;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

import com.joglobj.OBJMesh;
import com.stereoviewer.SceneViewer;;

public class Command {
	public static final String commandSeperator="\n";
	//object controls
	public static final String setObjectPosition="setObjectPosition";
	public static final String setObjectRotation="setObjectRotation";
	public static final String setObjectScale="setObjectScale";
	public static final String setObjectColorAmbient="setObjectColorAmbient";
	public static final String setObjectColorDiffuse="setObjectColorDiffuse";
	public static final String setObjectColorSpecular="setObjectColorSpecular";
	public static final String setObjectTransparency="setObjectTransparency";
	//camera controls
	public static final String setCameraPosition="setCameraPosition";//need to impliment!
	public static final String setCameraTarget="setCameraTarget";//need to impliment!
	public static final String setCameraUpVector="setCameraUpVector";//need to impliment!
	public static final String setCameraFovNF="setCameraFovNF";//need to impliment!
	public static final String setCameraIOD="setCameraIOD";//need to impliment!
	//light controls
	public static final String setGlobalLightValues="setGlobalLightValues";//need to impliment!
	public static final String setLightValues="setLightValues";//need to impliment!
	public static final String setLightPosition="setLightPosition";//need to impliment!
	public static final String setLightRotation="setLightRotation";//need to impliment!
	public static final String loadScene="loadScene";
	public static final String quit="quit";

	/**
	 * executes a single command onto the scene
	 * @param command
	 * @param sceneViewer
	 * @return a possible reply for data
	 * @throws Exception 
	 */

	public static String executeCommand(String command, SceneViewer sceneViewer) throws Exception{
		String [] parsed=command.split("\\s");
		for(int i=0;i<parsed.length;i++){
			parsed[i]=parsed[i].trim();
		}
		//load scene
		if(parsed[0].equals(loadScene)){
			String path="";
			for(int i=1;i<parsed.length;i++){
				path+=parsed[i]+" ";
			}
			loadScene(parsed[1], sceneViewer);
		}
		//object position command
		else if(parsed[0].equals(setObjectPosition)){
			String tochange=parsed[1];
			double a=Double.valueOf(parsed[2]);
			double b=Double.valueOf(parsed[3]);
			double c=Double.valueOf(parsed[4]);
			setObjectPosition(tochange,a,b,c,sceneViewer);
		}
		//object rotation command
		else if(parsed[0].equals(setObjectRotation)){
			setObjectRotation(parsed[1],Double.valueOf(parsed[2]),Double.valueOf(parsed[3]),Double.valueOf(parsed[4]),Double.valueOf(parsed[5]),sceneViewer);
		}
		//color commands
		else if(parsed[0].equals(setObjectColorAmbient)){
			setObjectColorAmbient(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),sceneViewer);
		}
		else if(parsed[0].equals(setObjectColorDiffuse)){
			setObjectColorDiffuse(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),sceneViewer);
		}
		else if(parsed[0].equals(setObjectColorSpecular)){
			setObjectColorSpecular(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),sceneViewer);
		}
		else if(parsed[0].equals(setObjectTransparency)){
			setObjectTransparency(parsed[1],Float.valueOf(parsed[2]), sceneViewer);
		}
		else if(parsed[0].equals(setObjectScale)){
			setObjectScale(parsed[1],Double.valueOf(parsed[2]),Double.valueOf(parsed[3]),Double.valueOf(parsed[4]), sceneViewer);
		}
		//quit command
		else if(parsed[0].equals(quit)){
			quit(sceneViewer);
		}
		else{
			System.out.println("command "+parsed[0]+" does not exist");
		}
		return "";
	}

	public static void loadScene(String path,SceneViewer sceneViewer) throws Exception {
		path=path.trim();
		sceneViewer.loadScene(path);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void setObjectPosition(String name, double a, double b, double c,SceneViewer sceneViewer) {
		if(sceneViewer.getScene().getObject(name)!=null){
			sceneViewer.getScene().getObject(name).setPosition(a, b, c);
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	public static void setObjectRotation(String name, double i, double j, double k,double angle,SceneViewer sceneViewer){
		if(sceneViewer.getScene().getObject(name)!=null){
			sceneViewer.getScene().getObject(name).setRotation(i, j, k,angle);
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	public static void setObjectColorAmbient(String name, float i, float j, float k,SceneViewer sceneViewer){
		if(sceneViewer.getScene().getObject(name)!=null){
			ArrayList<OBJMesh>  temp=sceneViewer.getScene().getObject(name).getModel().get_meshList();
			for(int l=0;l<temp.size();l++){
				temp.get(l).getMaterial().setAmbient(new Color3f(i, j,k));
			}
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	public static void setObjectColorDiffuse(String name, float i, float j, float k,SceneViewer sceneViewer){
		if(sceneViewer.getScene().getObject(name)!=null){
			ArrayList<OBJMesh>  temp=sceneViewer.getScene().getObject(name).getModel().get_meshList();
			for(int l=0;l<temp.size();l++){
				temp.get(l).getMaterial().setDiffuse(new Color3f(i, j,k));
			}
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	public static void setObjectColorSpecular(String name, float i, float j, float k,SceneViewer sceneViewer){
		if(sceneViewer.getScene().getObject(name)!=null){
			ArrayList<OBJMesh>  temp=sceneViewer.getScene().getObject(name).getModel().get_meshList();
			for(int l=0;l<temp.size();l++){
				temp.get(l).getMaterial().setSpecular(new Color3f(i, j,k));
			}
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	public static void setObjectTransparency(String name, float i,SceneViewer sceneViewer){
		if(sceneViewer.getScene().getObject(name)!=null){
			ArrayList<OBJMesh>  temp=sceneViewer.getScene().getObject(name).getModel().get_meshList();
			for(int l=0;l<temp.size();l++){
				temp.get(l).getMaterial().setAlpha(i);
			}
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	/**
	 * quits the application and closes the socket
	 */
	public static void quit(SceneViewer sceneViewer) {
		sceneViewer.quit();
	}

	public static void setObjectScale(String name, double a, double b, double c,SceneViewer sceneViewer) {
		if(sceneViewer.getScene().getObject(name)!=null){
			sceneViewer.getScene().getObject(name).setScale(new Vector3d(a, b, c));
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}




	/**
	 * executes a sequence of commands separated by the commandSeperator string
	 * @param commands
	 * @param scene
	 * @throws Exception 
	 */
	public static void executeCommands(String commands, SceneViewer sceneViewer) throws Exception{
		String [] commandArray=commands.split(commandSeperator);
		for(int i=0;i<commandArray.length;i++){
			executeCommand(commandArray[i],sceneViewer);
		}
	}
}
