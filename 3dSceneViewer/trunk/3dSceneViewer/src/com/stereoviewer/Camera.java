package com.stereoviewer;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3d;


import com.sun.opengl.util.GLUT;

/**
 * 
 * @author Jesse Fish
 *
 */
public class Camera {
	///camera position vector
	private Vector3d cameraPosition;
	///camera target vector
	private Vector3d cameraTarget;
	///camear up vector
	private Vector3d cameraUp;

	private double field_of_view;
	///distance of the near clipping plane
	private double znear;
	///distance of the far clipping plane
	private double zfar;
	///the Intra ocular distance, or distance between the right and left camera eyes
	private double eyespread;
	///flag set to true when field_of_view znear or zfar are changed so the proper updates can take place
	private boolean changedValues=false;
	
	/**
	 * 
	 * @param camposx the x position of the camera
	 * @param camposy the y position of the camera
	 * @param camposz the z position of the camera
	 * @param camtargetx the x position of the target the camera is looking at
	 * @param camtargety the y position of the target the camera is looking at
	 * @param camtargetz the z position of the target the camera is looking at
	 * @param camupx the x value of the camera's up vector
	 * @param camupy the y value of the camera's up vector
	 * @param camupz the z value of the camera's up vector
	 * @param field_of_view The field of view of the camera
	 * @param znear The near clipping plane distance
	 * @param zfar the far clipping plane distance
	 * @param eyespread the intra ocular distance, or distance between the right and left eyes of the camera 
	 */
	public Camera(double camposx, double camposy, double camposz, double camtargetx, double camtargety,double camtargetz, double camupx, double camupy, double camupz, double field_of_view, double znear, double zfar,double eyespread)
	{
		cameraPosition=new Vector3d(camposx,camposy,camposz);

		cameraTarget=new Vector3d(camtargetx,camtargety,camtargetz);

		cameraUp=new Vector3d(camupx,camupy,camupz);

		this.field_of_view=field_of_view;

		this.znear=znear;
		this.zfar=zfar;
		this.eyespread=eyespread;
	}
	
	/**
	 * 
	 * @param camposx the x position of the camera
	 * @param camposy the y position of the camera
	 * @param camposz the z position of the camera
	 * @return
	 */
	public void setPosition(double camposx, double camposy, double camposz)
	{
		cameraPosition=new Vector3d(camposx,camposy,camposz);
	}
	
	/**
	 * 
	 * @param camtargetx the x position of the target the camera is looking at
	 * @param camtargety the y position of the target the camera is looking at
	 * @param camtargetz the z position of the target the camera is looking at
	 * @return
	 */
	public void setTarget(double camtargetx, double camtargety, double camtargetz)
	{
		cameraTarget=new Vector3d(camtargetx,camtargety,camtargetz);
	}
	
	/**
	 * 
	 * @param camupx the x value of the camera's up vector
	 * @param camupy the y value of the camera's up vector
	 * @param camupz the z value of the camera's up vector
	 * @return
	 */
	public void setUpVector(double camupx, double camupy, double camupz)
	{
		cameraUp=new Vector3d(camupx,camupy,camupz);
	}
	
	/**
	 * performs the gl camera transformations for the left eye
	 */
	public void translateLeftEye(GL gl, GLU glu, GLUT glut)
	{
		//calculate the right vector
		
		Vector3d left=new Vector3d();
		Vector3d temp1=(Vector3d)cameraTarget.clone();
		temp1.scale(-1);
		Vector3d temp2=(Vector3d)cameraPosition.clone();
		
		temp2.add(temp1);
		left.cross(cameraUp,temp2);
		
		left.normalize();
		left.scale(-eyespread/2.);
		glu.gluLookAt(left.x +cameraPosition.x, left.y+cameraPosition.y, left.z+cameraPosition.z, left.x +cameraTarget.x,left.y + cameraTarget.y,left.z + cameraTarget.z, cameraUp.x, cameraUp.y, cameraUp.z);
	}
	/**
	 * performs the gl camera transformations for the right eye
	 */
	public void translateRightEye(GL gl, GLU glu, GLUT glut)
	{
		//calculate the right vector
		Vector3d right=new Vector3d();
		Vector3d temp1=(Vector3d)cameraTarget.clone();
		temp1.scale(-1);
		Vector3d temp2=(Vector3d)cameraPosition.clone();
		
		temp2.add(temp1);
		right.cross(cameraUp,temp2);
		
		right.normalize();
		right.scale(eyespread/2.);
		glu.gluLookAt(right.x +cameraPosition.x, right.y+cameraPosition.y, right.z+cameraPosition.z,right.x + cameraTarget.x, right.y +cameraTarget.y, right.z +cameraTarget.z, cameraUp.x, cameraUp.y, cameraUp.z);
	}

	public double getZnear() {
		return znear;
	}

	public double getZfar() {
		return zfar;
	}

	public double getField_of_view() {
		return field_of_view;
	}

	public Vector3d getCameraPosition() {
		return cameraPosition;
	}

	public void setCameraPosition(Vector3d cameraPosition) {
		this.cameraPosition = cameraPosition;
	}

	public Vector3d getCameraTarget() {
		return cameraTarget;
	}

	public void setCameraTarget(Vector3d cameraTarget) {
		this.cameraTarget = cameraTarget;
	}

	public Vector3d getCameraUp() {
		return cameraUp;
	}

	public void setCameraUp(Vector3d cameraUp) {
		this.cameraUp = cameraUp;
	}

	public double getEyespread() {
		return eyespread;
	}

	public void setEyespread(double eyespread) {
		this.eyespread = eyespread;
	}

	public void setField_of_view(double field_of_view) {
		this.field_of_view = field_of_view;
		this.changedValues=true;
	}

	public void setZnear(double znear) {
		this.znear = znear;
		this.changedValues=true;
	}

	public void setZfar(double zfar) {
		this.zfar = zfar;
		this.changedValues=true;
	}

	public boolean isChangedValues() {
		return changedValues;
	}

	public void setChangedValues(boolean changedValues) {
		this.changedValues = changedValues;
	}
	
	
	public Vector3d getPosition() {
		return cameraPosition;
	}
	
	public Vector3d getTarget() {
		return cameraTarget;
	}
	
	public Vector3d getUpVector() {
		return cameraUp;
	}
}
