package com.stereoviewer;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3d;

public class SceneLight {

	/*
	 ******************static/library section ******************************
	 */
	private final static int light1=GL.GL_LIGHT1;
	
	private static Color4f global_light_values= new Color4f( 0.2f, 0.2f, 0.2f, 1.0f);
	private static Color4f clear_color=new Color4f( 0f, 0f, 0f, 1.0f);
	private static boolean local_viewer=false;
	private static boolean two_side=false;
	
	private static boolean GlobalValuesHaveChanged=false;

	public static Color4f getGlobal_light_values() {
		return global_light_values;
	}
	/**
	 * Sets the global light values for the scene
	 * @param global_light_values a Color4f object to represent the global lighting
	 */
	public static void setGlobal_light_values(Color4f global_light_values) {
		SceneLight.global_light_values = global_light_values;
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

	public static void initGlobalLighting(GL gl, GLU glu){

		// make sure that this line is copied into the change ambient lighting method
		gl.glLightModelfv( GL.GL_LIGHT_MODEL_AMBIENT,getGlobalLightingfv(), 0 );

		// make sure that these lines are copied into the change viewer model lighting methods
		int local_viewer;
		if(SceneLight.isLocal_viewer()){
			local_viewer=GL.GL_TRUE;
		}
		else{
			local_viewer=GL.GL_FALSE;
		}
		gl.glLightModeli( GL.GL_LIGHT_MODEL_LOCAL_VIEWER,local_viewer);

		int two_side;
		if(SceneLight.isTwo_side()){
			two_side=GL.GL_TRUE;
		}
		else{
			two_side=GL.GL_FALSE;
		}
		gl.glLightModeli( GL.GL_LIGHT_MODEL_TWO_SIDE, two_side);
		
		GlobalValuesHaveChanged=false;
	}

	public static boolean isGlobalValuesHaveChanged() {
		return GlobalValuesHaveChanged;
	}

	public static void setGlobalValuesHaveChanged(boolean globalValuesHaveChanged) {
		GlobalValuesHaveChanged = globalValuesHaveChanged;
	}


	/*
	 ******************member/object section ******************************
	 */
	private String name;
	private int light_number;
	private Vector3d position;
	private Vector3d direction;	
	private Color4f ambient;
	private Color4f diffuse;
	private Color4f specular;
	private float spot_Cutoff;
	private boolean enable=true;
	private float intensity;
	private float constant_attenuation_constant;
	private float linear_attenuation_constant;
	private float quad_attenuation_constant;
	private boolean valuesHaveChanged=false;

	//3 color values, ambient, diffuse and specular
	public SceneLight(String name, int light_number, 
			Color4f ambient, Color4f diffuse, Color4f specular, 
			double posx,double posy,double posz,
			double directionx,double directiony,double directionz,
			float spot_Cutoff,float intensity, 
			float constant_attenuation_constant,float linear_attenuation_constant,
			float quad_attenuation_constant){
		
		this.name=name;
		this.light_number=light_number;
		position=new Vector3d(posx,posy,posz);
		direction=new Vector3d(directionx,directiony,directionz);
		this.ambient=ambient;
		this.diffuse=diffuse;
		this.specular=specular;
		this.spot_Cutoff=spot_Cutoff;
		this.intensity=intensity;
		this.constant_attenuation_constant=constant_attenuation_constant;
		this.linear_attenuation_constant=linear_attenuation_constant;
		this.quad_attenuation_constant=quad_attenuation_constant;
	}
	public void init(GL gl, GLU glu){
		if(enable){
			gl.glEnable(light1+light_number);
		}
		else{
			gl.glDisable(light1+light_number);
		}
		
		float[]tempamb={ambient.x,ambient.y,ambient.z,ambient.w};
		gl.glLightfv(light1+light_number, GL.GL_AMBIENT, tempamb,0);

		float[]tempdiff={diffuse.x,diffuse.y,diffuse.z,diffuse.w};
		gl.glLightfv(light1+light_number, GL.GL_DIFFUSE, tempdiff,0);

		float[]tempspec={specular.x,specular.y,specular.z,specular.w};
		gl.glLightfv(light1+light_number, GL.GL_DIFFUSE, tempspec,0);

		float[] temppos={(float) position.x,(float) position.y,(float) position.z};
		gl.glLightfv(light1+light_number, GL.GL_POSITION, temppos,0);

		float[] tempdir={(float) direction.x,(float) direction.y,(float) direction.z};
		gl.glLightfv(light1+light_number, GL.GL_SPOT_DIRECTION, tempdir,0);

		gl.glLightf(light1+light_number, GL.GL_SPOT_CUTOFF, spot_Cutoff);

		gl.glLightf(light1+light_number, GL.GL_SPOT_EXPONENT, intensity);

		gl.glLightf(light1+light_number, GL.GL_CONSTANT_ATTENUATION,this.constant_attenuation_constant );
		gl.glLightf(light1+light_number, GL.GL_LINEAR_ATTENUATION,this.linear_attenuation_constant );
		gl.glLightf(light1+light_number, GL.GL_QUADRATIC_ATTENUATION,this.quad_attenuation_constant );
		valuesHaveChanged=false;
	}
	public int getLight_number() {
		return light_number;
	}

	public void setLight_number(int light_number) {
		this.light_number = light_number;
	}

	public Vector3d getPosition() {
		return position;
	}

	public void setPosition(Vector3d position) {
		this.position = position;
		valuesHaveChanged=true;
	}

	public double getSpot_Cutoff() {
		return spot_Cutoff;
	}

	public void setSpot_Cutoff(float spot_Cutoff) {
		this.spot_Cutoff = spot_Cutoff;
		valuesHaveChanged=true;
	}

	public String getName() {
		return name;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
		valuesHaveChanged=true;
	}

	public Vector3d getDirection() {
		return direction;
	}

	public void setDirection(Vector3d direction) {
		this.direction = direction;
		valuesHaveChanged=true;
	}

	public Color4f getAmbient() {
		return ambient;
	}

	public void setAmbient(Color4f ambient) {
		this.ambient = ambient;
		valuesHaveChanged=true;
	}

	public Color4f getDiffuse() {
		return diffuse;
	}

	public void setDiffuse(Color4f diffuse) {
		this.diffuse = diffuse;
		valuesHaveChanged=true;
	}

	public Color4f getSpecular() {
		return specular;
	}

	public void setSpecular(Color4f specular) {
		this.specular = specular;
		valuesHaveChanged=true;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
		valuesHaveChanged=true;
	}

	public float getConstant_attenuation_constant() {
		return constant_attenuation_constant;
	}

	public void setConstant_attenuation_constant(float constant_attenuation_constant) {
		this.constant_attenuation_constant = constant_attenuation_constant;
		valuesHaveChanged=true;
	}

	public float getLinear_attenuation_constant() {
		return linear_attenuation_constant;
	}

	public void setLinear_attenuation_constant(float linear_attenuation_constant) {
		this.linear_attenuation_constant = linear_attenuation_constant;
		valuesHaveChanged=true;
	}

	public float getQuad_attenuation_constant() {
		return quad_attenuation_constant;
	}

	public void setQuad_attenuation_constant(float quad_attenuation_constant) {
		this.quad_attenuation_constant = quad_attenuation_constant;
		valuesHaveChanged=true;
	}

	public boolean isValuesHaveChanged() {
		return valuesHaveChanged;
	}

	public void setValuesHaveChanged(boolean valuesHaveChanged) {
		this.valuesHaveChanged = valuesHaveChanged;
		valuesHaveChanged=true;
	}
}
