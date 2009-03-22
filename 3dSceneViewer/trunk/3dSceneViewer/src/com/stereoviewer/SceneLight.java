package com.stereoviewer;

public class SceneLight {

	/*
	******************static/library section ******************************
	*/
	private static double [] global_light_values;
	/**
	 * Sets the global light values for the scene
	 * @param global_light_values a double array of size 4 with red, green, blue, and alpha values respectively
	 */
	public static void setGlobalLighting(double [] input_global_light_values){
		global_light_values=input_global_light_values;
	}
	
	public static double[] getGlobalLighting() {
		return global_light_values;
	}


	/*
	******************member/object section ******************************
	*/
	private String name;
	private Vector3d position;
	private Vector3d rotationVector;
	private double rotationAngle;
	
	
}
