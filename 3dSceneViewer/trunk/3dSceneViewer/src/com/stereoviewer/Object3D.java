package com.stereoviewer;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3d;

import com.sun.opengl.util.GLUT;

import com.joglobj.OBJScene;


/**
 * Class for each object being shown
 * @author Jesse Fish
 *
 */
public class Object3D {

	private String name;
	private String path;

	private String parent;

	private Vector3d position;
	private Vector3d rotationVector;
	private double rotationAngle;

	private Vector3d scale;

	private boolean draw=true;

	//need a model class implementation
	private OBJScene model;

	private LinkedList<Object3D> children=new LinkedList<Object3D> ();

	/**
	 * 
	 * @param name
	 * @param path
	 * @param parent
	 * @param posx
	 * @param posy
	 * @param posz
	 * @param rotx
	 * @param roty
	 * @param rotz
	 * @param rotationAngle
	 */
	public Object3D(String name,String path, String parent,double posx,double posy,double posz,double rotx,double roty,double rotz, double rotationAngle,double scalex,double scaley,double scalez)
	{
		this.path=path;
		this.parent=parent;
		this.name=name;
		position=new Vector3d(posx,posy,posz);
		rotationVector=new Vector3d(rotx,roty,rotz);
		this.rotationAngle=rotationAngle;

		scale=new Vector3d(scalex, scaley, scalez);

		model=new OBJScene();
	}

	/**
	 * draws this object and all of its children
	 * @param gl
	 * @param glu
	 * @param glut
	 */
	public void draw(GL gl, GLU glu, GLUT glut)
	{
		gl.glPushMatrix();
		//draw this object

		gl.glTranslated(position.x, position.y, position.z);
		gl.glRotated(rotationAngle, rotationVector.x, rotationVector.y, rotationVector.z);

		
		//draw the model
		if(draw){
			gl.glPushMatrix();
			gl.glScaled(scale.x, scale.y, scale.z);
			model.draw(gl, glu, glut);
			gl.glPopMatrix();
		}
		
		ListIterator <Object3D> runner=children.listIterator();
		while(runner.hasNext())
		{
			Object3D temp=runner.next();
			temp.draw(gl, glu, glut);
		}

		//pop the matrix
		gl.glPopMatrix();;
	}

	public void initModel(GL gl,GLU glu){
		model.load(path, gl, glu);
	}

	/**
	 * sets the position of the object to the input position
	 * @param posx
	 * @param posy
	 * @param posz
	 */
	public void setPosition(double posx,double posy,double posz)
	{
		position=new Vector3d(posx,posy,posz);
	}
	
	public Vector3d getPosition() {
		return position;
	}

	/**
	 * sets the rotation of the object to the input position
	 * @param posx
	 * @param posy
	 * @param posz
	 */
	public void setRotation(double rotx,double roty,double rotz, double angle)
	{
		rotationVector=new Vector3d(rotx,roty,rotz);
		rotationAngle=angle;
	}
	
	public Vector3d getRotation() {
		return rotationVector;
	}
	
	public double getAngle() {
		return rotationAngle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	
	public void setScale(Vector3d scale) {
		this.scale = scale;
	}

	public Vector3d getScale() {
		return scale;
	}

	/**
	 * adds the passed Object3D argument to this Object3D's child list
	 * @param child the object to be added to the child list
	 */
	public void addAsChild(Object3D child){
		children.add(child);
	}

	/**
	 * puts the object in the child list of its parent
	 * @param objects the hashtable that all objects are stored in
	 */
	public void linkToParent(Hashtable<String,Object3D> objects){
		if(objects.containsKey(parent)){
			objects.get(parent).addAsChild(this);
		}
		else{
			System.out.println("Parent does not exist");
		}
	}

	public OBJScene getModel() {
		return model;
	}

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	
}
