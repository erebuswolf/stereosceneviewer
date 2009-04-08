package com.scenecontrol;

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
	public static final String setCameraPosition="setCameraPosition";
	public static final String setCameraTarget="setCameraTarget";
	public static final String setCameraUpVector="setCameraUpVector";
	public static final String setCameraFovNF="setCameraFovNF";
	public static final String setCameraIOD="setCameraIOD";
	//light controls
	public static final String setGlobalLightValues="setGlobalLightValues";
	public static final String setLightValues="setLightValues";
	public static final String setLightPosition="setLightPosition";
	public static final String setLightRotation="setLightRotation";
	public static final String quit="quit";

	/**
	 * executes a single command onto the scene
	 * @param command
	 * @param sceneViewer
	 * @return a possible reply for data
	 */

	public static String executeCommand(String command, SceneViewer sceneViewer){
		String [] parsed=command.split("\\s");
		for(int i=0;i<parsed.length;i++){
			parsed[i]=parsed[i].trim();
		}
//object position command
		if(parsed[0].equals(setObjectPosition)){
			String tochange=parsed[1];
			double a=Double.valueOf(parsed[2]);
			double b=Double.valueOf(parsed[3]);
			double c=Double.valueOf(parsed[4]);
			if(sceneViewer.getScene().getObject(tochange)!=null){
				sceneViewer.getScene().getObject(tochange).setPosition(a, b, c);
			}
			else{
				System.out.println("error object "+tochange+" does not exist");
			}
		}
		//object rotation command
		else if(parsed[0].equals(setObjectRotation)){
			sceneViewer.getScene().getObject(parsed[1]).setRotation(Double.valueOf(parsed[2]), Double.valueOf(parsed[3]), Double.valueOf(parsed[4]),Double.valueOf(parsed[5]));
		}
		//quit command
		else if(parsed[0].equals(quit)){
			sceneViewer.quit();
		}
		else{
			System.out.println("command "+parsed[0]+" does not exist");
		}
		return "";
	}
	/**
	 * executes a sequence of commands separated by the commandSeperator string
	 * @param commands
	 * @param scene
	 */
	public static void executeCommands(String commands, SceneViewer sceneViewer){
		String [] commandArray=commands.split(commandSeperator);
		for(int i=0;i<commandArray.length;i++){
			executeCommand(commandArray[i],sceneViewer);
		}
	}
}
