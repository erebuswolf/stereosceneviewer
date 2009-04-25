package com.stereoviewer;

import javax.media.opengl.GL;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;

public class SceneLight {

	/*
	******************static/library section ******************************
	*/
	private static Color4f global_light_values= new Color4f( 0.2f, 0.2f, 0.2f, 1.0f);
	private static Color4f clear_color=new Color4f( 0f, 0f, 0f, 1.0f);
	private static boolean local_viewer=false;
	private static boolean two_side=false;
	private final static int light1=GL.GL_LIGHT1;
	
	/**
	 * Sets the global light values for the scene
	 * @param global_light_values a double array of size 4 with red, green, blue, and alpha values respectively
	 */
	public static void setGlobalLighting(Color4f global_light_values){
		SceneLight.global_light_values=global_light_values;
	}
	
	public static Color4f getGlobalLighting() {
		return global_light_values;
	}
	public static float[] getGlobalLightingfv() {
		float[] temp= { global_light_values.x,global_light_values.y,global_light_values.z,global_light_values.w};
		return temp;
	}
	
	public static Color4f getClear_color() {
		return clear_color;
	}

	public static void setClear_color(Color4f clear_color) {
		SceneLight.clear_color = clear_color;
	}

	public static boolean isLocal_viewer() {
		return local_viewer;
	}

	public static void setLocal_viewer(boolean local_viewer) {
		SceneLight.local_viewer = local_viewer;
	}

	public static boolean isTwo_side() {
		return two_side;
	}

	public static void setTwo_side(boolean two_side) {
		SceneLight.two_side = two_side;
	}


	/*
	******************member/object section ******************************
	*/
	private String name;
	private int light_number;
	private Vector3d position;
	private Vector3d rotationVector;
	private Color4f color;
	private double rotationAngle;
	private double spot_Cutoff;
	//3 color values, ambient, diffuse and specular
	public SceneLight(String name, int light_number, Color4f color, double posx,double posy,double posz,double rotx,double roty,double rotz, double rotationAngle,double spot_Cutoff){
		this.name=name;
		position=new Vector3d(posx,posy,posz);
		rotationVector=new Vector3d(rotx,roty,rotz);
		this.rotationAngle=rotationAngle;
		
		
	}
	
	
	
}
