package com.scenecontrol;


import java.util.ArrayList;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;

import com.joglobj.OBJMesh;
import com.stereoviewer.SceneLight;
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
	public static final String setObjectDraw="setObjectDraw";
	//camera controls
	public static final String setCameraPosition="setCameraPosition";
	public static final String setCameraTarget="setCameraTarget";
	public static final String setCameraUpVector="setCameraUpVector";
	public static final String setCameraFovNF="setCameraFovNF";
	public static final String setCameraIOD="setCameraIOD";

	//light controls
	public static final String setGlobalLightValues="setGlobalLightValues";
	public static final String setClearColor="setClearColor";
	public static final String setLightOptions="setLightOptions";
	public static final String setLightColorAmbient="setLightColorAmbient";
	public static final String setLightColorDiffuse="setLightColorDiffuse";
	public static final String setLightColorSpecular="setLightColorSpecular";
	public static final String setLightValues="setLightValues";
	public static final String setLightOn="setLightOn";
	public static final String setLightPosition="setLightPosition";
	public static final String setLightDirection="setLightDirection";


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

		/*
		 * *********************Commands start****************************************
		 */
		//load scene
		if(parsed[0].equals(loadScene)){
			String path="";
			for(int i=1;i<parsed.length;i++){
				path+=parsed[i]+" ";
			}
			loadScene(parsed[1], sceneViewer);
		}
		//quit command
		else if(parsed[0].equals(quit)){
			quit(sceneViewer);
		}
		/*
		 * *********************Object Commands****************************************
		 */
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
		else if(parsed[0].equals(setObjectDraw)){
			setObjectDraw(parsed[1],Boolean.valueOf(parsed[2]),sceneViewer);
		}	
		/*
		 * *********************Camera Commands****************************************
		 */
		else if(parsed[0].equals(setCameraPosition)){
			double a=Double.valueOf(parsed[1]);
			double b=Double.valueOf(parsed[2]);
			double c=Double.valueOf(parsed[3]);
			setCameraPosition(a,b,c,sceneViewer);
		}
		else if(parsed[0].equals(setCameraTarget)){
			double a=Double.valueOf(parsed[1]);
			double b=Double.valueOf(parsed[2]);
			double c=Double.valueOf(parsed[3]);
			setCameraTarget(a,b,c,sceneViewer);
		}
		else if(parsed[0].equals(setCameraUpVector)){
			double a=Double.valueOf(parsed[1]);
			double b=Double.valueOf(parsed[2]);
			double c=Double.valueOf(parsed[3]);
			setCameraUpVector(a,b,c,sceneViewer);
		}
		else if(parsed[0].equals(setCameraFovNF)){
			double a=Double.valueOf(parsed[1]);
			double b=Double.valueOf(parsed[2]);
			double c=Double.valueOf(parsed[3]);
			setCameraFovNF(a,b,c,sceneViewer);
		}
		else if(parsed[0].equals(setCameraIOD)){
			double iod=Double.valueOf(parsed[1]);
			setCameraIOD(iod,sceneViewer);
		}

		/*
		 * *********************Lighting Commands****************************************
		 */
		else if(parsed[0].equals(setGlobalLightValues)){
			setGlobalLightValues(Float.valueOf(parsed[1]), Float.valueOf(parsed[2]), Float.valueOf(parsed[3]),Float.valueOf(parsed[4]),sceneViewer);
		}
		else if(parsed[0].equals(setClearColor)){
			setClearColor(Float.valueOf(parsed[1]), Float.valueOf(parsed[2]), Float.valueOf(parsed[3]),Float.valueOf(parsed[4]),sceneViewer);
		}
		else if(parsed[0].equals(setLightOptions)){
			setLightOptions(Boolean.valueOf(parsed[1]), Boolean.valueOf(parsed[2]));
		}
		else if(parsed[0].equals(setLightColorAmbient)){
			setLightColorAmbient(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),Float.valueOf(parsed[5]),sceneViewer);
		}
		else if(parsed[0].equals(setLightColorDiffuse)){
			setLightColorDiffuse(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),Float.valueOf(parsed[5]),sceneViewer);
		}
		else if(parsed[0].equals(setLightColorSpecular)){
			setLightColorSpecular(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),Float.valueOf(parsed[5]),sceneViewer);
		}
		else if(parsed[0].equals(setLightValues)){
			setLightValues(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),Float.valueOf(parsed[5]),sceneViewer);
		}
		else if(parsed[0].equals(setLightOn)){
			setLightOn(parsed[1],Boolean.valueOf(parsed[2]),sceneViewer);
		}
		else if(parsed[0].equals(setLightPosition)){
			setLightPosition(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),sceneViewer);
		}
		else if(parsed[0].equals(setLightDirection)){
			setLightDirection(parsed[1],Float.valueOf(parsed[2]), Float.valueOf(parsed[3]), Float.valueOf(parsed[4]),Float.valueOf(parsed[5]),sceneViewer);
		}
		else{
			System.out.println("command "+parsed[0]+" does not exist");
		}
		return "";
	}
	/*
	 * *********************Commands start****************************************
	 */
	public static void loadScene(String path,SceneViewer sceneViewer) throws Exception {
		path=path.trim();
		sceneViewer.loadScene(path);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * quits the application and closes the socket
	 */
	public static void quit(SceneViewer sceneViewer) {
		sceneViewer.quit();
	}
	/*
	 * *********************Object Commands****************************************
	 */
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

	public static void setObjectScale(String name, double a, double b, double c,SceneViewer sceneViewer) {
		if(sceneViewer.getScene().getObject(name)!=null){
			sceneViewer.getScene().getObject(name).setScale(new Vector3d(a, b, c));
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	public static void setObjectDraw(String name, boolean draw,SceneViewer sceneViewer) {
		if(sceneViewer.getScene().getObject(name)!=null){
			sceneViewer.getScene().getObject(name).setDraw(draw);
		}
		else{
			System.out.println("error object "+name+" does not exist");
		}
	}
	
	/*
	 * *********************Camera Commands****************************************
	 */
	public static void setCameraPosition( double a, double b, double c,SceneViewer sceneViewer) {
		sceneViewer.getScene().getCamera().setPosition(a, b, c);
	}

	public static void setCameraTarget( double a, double b, double c,SceneViewer sceneViewer) {
		sceneViewer.getScene().getCamera().setTarget(a, b, c);
	}
	public static void setCameraUpVector( double a, double b, double c,SceneViewer sceneViewer) {
		sceneViewer.getScene().getCamera().setUpVector(a, b, c);
	}
	public static void setCameraFovNF( double field_of_view, double znear, double zfar,SceneViewer sceneViewer) {
		sceneViewer.getScene().getCamera().setField_of_view(field_of_view);
		sceneViewer.getScene().getCamera().setZnear(znear);
		sceneViewer.getScene().getCamera().setZfar(zfar);
	}
	public static void setCameraIOD(double IOD,SceneViewer sceneViewer){
		sceneViewer.getScene().getCamera().setEyespread(IOD);
	}
	/*
	 * *********************Lighting Commands****************************************
	 */
	public static void setGlobalLightValues(float a, float b, float c,float d,SceneViewer sceneViewer) {
		SceneLight.setGlobal_light_values(new Color4f(a,b,c,d));
	}
	public static void setClearColor(float a, float b, float c,float d,SceneViewer sceneViewer) {
		SceneLight.setClear_color(new Color4f(a,b,c,d));
	}

	public static void setLightOptions(boolean local_viewer, boolean two_side) {
		SceneLight.setLocal_viewer(local_viewer);
		SceneLight.setTwo_side(two_side);
	}
	public static void setLightColorAmbient(String name,float a, float b, float c,float d,SceneViewer sceneViewer) {
		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setAmbient(new Color4f(a,b,c,d));
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}

	public static void setLightColorDiffuse(String name,float a, float b, float c,float d,SceneViewer sceneViewer) {
		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setDiffuse(new Color4f(a,b,c,d));
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}

	public static void setLightColorSpecular(String name,float a, float b, float c,float d,SceneViewer sceneViewer) {
		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setSpecular(new Color4f(a,b,c,d));
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}

	public static void setLightValues(String name,float intensity, float constant_attenuation_constant,
			float linear_attenuation_constant,float quad_attenuation_constant,SceneViewer sceneViewer) {

		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setIntensity(intensity);
			temp.setConstant_attenuation_constant(constant_attenuation_constant);
			temp.setLinear_attenuation_constant(linear_attenuation_constant);
			temp.setQuad_attenuation_constant(quad_attenuation_constant);
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}

	public static void setLightOn( String name,boolean enable,SceneViewer sceneViewer) {
		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setEnable(enable);
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}
	public static void setLightPosition( String name,double a, double b, double c,SceneViewer sceneViewer) {
		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setPosition(new Vector3d(a,b,c));
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}
	
	public static void setLightDirection( String name,double a, double b, double c,float spot_Cutoff,SceneViewer sceneViewer) {
		SceneLight temp=sceneViewer.getScene().getLightHash().get(name);
		if(temp!=null){
			temp.setSpot_Cutoff(spot_Cutoff);
			temp.setDirection(new Vector3d(a,b,c));
		}
		else{
			System.out.println("error light "+name+" does not exist");
		}
	}
	
	/*
	 * *********************Commands end****************************************
	 */

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
