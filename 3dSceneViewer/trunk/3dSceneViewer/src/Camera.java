import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;


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
		Vector3d left=Vector3d.crossProduct(cameraUp, Vector3d.add(cameraPosition,Vector3d.scalarMult(cameraTarget, -1) ));
		left.normalize();
		left.scale(-eyespread/2.);
		glu.gluLookAt(left.getI() +cameraPosition.getI(), left.getJ()+cameraPosition.getJ(), left.getK()+cameraPosition.getK(), left.getI() +cameraTarget.getI(),left.getJ() + cameraTarget.getJ(),left.getK() + cameraTarget.getK(), cameraUp.getI(), cameraUp.getJ(), cameraUp.getK());
	}
	/**
	 * performs the gl camera transformations for the right eye
	 */
	public void translateRightEye(GL gl, GLU glu, GLUT glut)
	{
		//calculate the right vector
		Vector3d right=Vector3d.crossProduct(cameraUp, Vector3d.add(cameraPosition,Vector3d.scalarMult(cameraTarget, -1) ));
		right.normalize();
		right.scale(eyespread/2.);
		glu.gluLookAt(right.getI() +cameraPosition.getI(), right.getJ()+cameraPosition.getJ(), right.getK()+cameraPosition.getK(),right.getI() + cameraTarget.getI(), right.getJ() +cameraTarget.getJ(), right.getK() +cameraTarget.getK(), cameraUp.getI(), cameraUp.getJ(), cameraUp.getK());
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
}
