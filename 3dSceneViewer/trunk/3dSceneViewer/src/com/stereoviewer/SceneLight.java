package com.stereoviewer;

public class SceneLight {

	/*
	******************static/library section ******************************
	*/
	private static float [] global_light_values= { 0.2f, 0.2f, 0.2f, 1.0f};
	private static float [] clear_color={0f,0f,0f,1f};
	private static boolean local_viewer=false;
	private static boolean two_side=false;
	
	
	/**
	 * Sets the global light values for the scene
	 * @param global_light_values a double array of size 4 with red, green, blue, and alpha values respectively
	 */
	public static void setGlobalLighting(float [] global_light_values){
		SceneLight.global_light_values=global_light_values;
	}
	
	public static float[] getGlobalLighting() {
		return global_light_values;
	}
	
	public static float[] getClear_color() {
		return clear_color;
	}

	public static void setClear_color(float[] clear_color) {
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
	private Vector3d position;
	private Vector3d rotationVector;
	private double rotationAngle;
	
	
}
