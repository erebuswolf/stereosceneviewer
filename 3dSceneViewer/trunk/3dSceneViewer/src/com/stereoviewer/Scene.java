package com.stereoviewer;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.ListIterator;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

/**
 * Class for the scene being displayed
 * @author Jesse Fish
 *
 */
/**
 * @author jesse
 *
 */
public class Scene {
	
	public static Scene emptyScene(){
		return new Scene();
	}

	///reserved string keyword for knowing whether the object is a child of the scene
	public static final String rootName="ROOT";
	public static final String on="on";
	public static final String off="off";
	///camera of the scene
	private Camera camera=null;
	///the title for the scene
	private String title=null;

	private boolean renderStereo=false;
	
	/**
	 * hashtable of all the objects in the entire scene.
	 * This allows the objects to be accessed by name
	 */
	private Hashtable<String,Object3D> objects=new Hashtable<String,Object3D>();
	
	/**
	 * lights of all the objects in the entire scene.
	 * This allows the objects to be accessed by name
	 */
	private Hashtable<String,SceneLight> lightHash=new Hashtable<String,SceneLight>();
	/**
	 * lights of all the objects in the entire scene.
	 * This allows the objects to be accessed by name
	 */
	private LinkedList<SceneLight> lightList=new LinkedList<SceneLight>();
	
	/**
	 * arraylist of objects that are root to the scene.
	 */
	private LinkedList <Object3D> sceneObjects=new LinkedList <Object3D> ();
	
	/**
	 * 
	 */
	private LinkedList <Object3D> allObjects=new LinkedList <Object3D> ();

	/**
	 * Creates an empty blank scene 
	 */
	public Scene(){
		this.title="empty scene";
		this.camera=new Camera(0, 0,-10, 0, 0,0, 0, 1, 0, 45, .01, 50,0);
	}
	/**
	 * Sets the title and camera for the scene. Goes through all the objects
	 * in the scene and adds them to a hashtable. Then goes through that list again
	 * and links all child and parent objects to each other. 
	 * @param title The title for the scene to be displayed in the title bar
	 * @param camera The camera for the scene
	 * @param allObjects a List of all objects that are in the scene
	 */
	public Scene(String title,boolean renderStereo, Camera camera, LinkedList <Object3D> allObjects,LinkedList<SceneLight>lightList){
		this.title=title;
		this.renderStereo=renderStereo;
		this.allObjects=allObjects;
		this.camera=camera;
		this.lightList=lightList;
		//hash all objects by name
		ListIterator <Object3D> runner=allObjects.listIterator();
		while(runner.hasNext())
		{
			Object3D temp=runner.next();
			objects.put(temp.getName(), temp);
		}
		//link all the objects to each other
		linkObjects();
		
		//hash all lights by name
		ListIterator <SceneLight> runner2=lightList.listIterator();
		while(runner2.hasNext())
		{
			SceneLight temp=runner2.next();
			lightHash.put(temp.getName(), temp);
		}
	}

	/**
	 * draws all the objects in the scene
	 * 
	 * achieves this by drawing each object and then pushes that draw matrix and draw the children
	 * then pops the matrix
	 * 
	 * draws the scene for each eye
	 * 
	 * @param gl
	 * @param glu
	 * @param glut
	 */
	public void draw(GL gl, GLU glu, GLUT glut)
	{

		//draw the back left buffer
		gl.glDrawBuffer(GL.GL_BACK_LEFT);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		camera.translateLeftEye(gl, glu, glut);
		gl.glPushMatrix();

		//draw all the objects here
		ListIterator <Object3D> runner=sceneObjects.listIterator();
		while(runner.hasNext())
		{
			Object3D temp=runner.next();
			temp.draw(gl, glu, glut);
		}
		gl.glPopMatrix();

		//draw the back right buffer
		gl.glDrawBuffer(GL.GL_BACK_RIGHT);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		camera.translateRightEye(gl, glu, glut);
		gl.glPushMatrix();

		//draw all the objects again here
		runner=sceneObjects.listIterator();
		while(runner.hasNext())
		{
			Object3D temp=runner.next();
			temp.draw(gl, glu, glut);
		}
		gl.glPopMatrix();
	}

	public Camera getCamera() {
		return camera;
	}


	public String getTitle() {
		return title;
	}

	/**
	 * method to retrieve objects by name
	 * @param name name of the object
	 */
	public Object3D getObject(String name){
		return objects.get(name);
	}

	/**
	 * links all the objects to their parent objects
	 * configures all the child lists for every object
	 */
	public void linkObjects(){
		//connect all parent child object links
		ListIterator <Object3D> runner=allObjects.listIterator();
		while(runner.hasNext())
		{
			Object3D temp=runner.next();
			if(temp.getParent().equals(rootName)){
				sceneObjects.add(temp);
			}
			else if(objects.get(temp.getParent())!=null){
				objects.get(temp.getParent()).addAsChild(temp);
			}
		}
	}
	
	/**
	 * model loading requires a valid GL context so we must do it here.
	 * @param gl
	 * @param glu
	 */
	public void initModels(GL gl,GLU glu){
		ListIterator <Object3D> runner=allObjects.listIterator();
		while(runner.hasNext())
		{
			Object3D temp=runner.next();
			temp.initModel(gl, glu);
		}
	}
	
	/**
	 * resets the light values for the scene
	 * @param gl
	 * @param glu
	 */
	public void setLighting(GL gl,GLU glu){
		SceneLight.initGlobalLighting(gl, glu);
		ListIterator <SceneLight> runner=lightList.listIterator();
		while(runner.hasNext())
		{
			SceneLight temp=runner.next();
			temp.init(gl, glu);
		}
	}
	
	public boolean isRenderStereo() {
		return renderStereo;
	}
	public LinkedList<SceneLight> getLightList() {
		return lightList;
	}
	public Hashtable<String, SceneLight> getLightHash() {
		return lightHash;
	}

}

