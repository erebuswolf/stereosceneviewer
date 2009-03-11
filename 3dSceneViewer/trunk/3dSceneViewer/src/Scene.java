import java.awt.Color;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Hashtable;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

/**
 * Class for the scene being displayed
 * @author Jesse Fish
 *
 */
public class Scene {

	///reserved string keyword for knowing whether the object is a child of the scene
	public static final String rootName="ROOT";
	///camera of the sscene
	private Camera camera=null;
	///the clear color for the scene
	private Color clearColor=Color.black;
	///the title for the scene
	private String title=null;
	
	
	/**
	 * hashtable of all the objects in the entire scene.
	 * This allows the objects to be accessed by name
	 */
	private Hashtable<String,Object3D> objects=new Hashtable<String,Object3D>();
	
	/**
	 * arraylist of objects that are root to the scene.
	 */
	private LinkedList <Object3D> sceneObjects=new LinkedList <Object3D> ();
	
	/**
	 * this constructor doesn't really do a lot
	 */
	public Scene()
	{
		
	}
	
	/**
	 * Parses the xlm file at the path parameter using the sax api and creates a camera and objects for the scene
	 * adds objects root to the scene to the sceneObjects list
	 * adds all objects to the objects hashtable, hashed by name
	 * 
	 * @param path the path to the scene file to be loaded
	 */
	public void loadScene(String path)
	{
		
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
		for(Object3D object:sceneObjects){
			object.draw(gl, glu, glut);
		}
		gl.glPopMatrix();

		
		//draw the back right buffer
		gl.glDrawBuffer(GL.GL_BACK_RIGHT);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		camera.translateRightEye(gl, glu, glut);
		gl.glPushMatrix();

		//draw all the objects again here
		for(Object3D object:sceneObjects){
			object.draw(gl, glu, glut);
		}
		gl.glPopMatrix();
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Color getClearColor() {
		return clearColor;
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
	 * loads a dummy test scene bypassing xml loading
	 */
	public void fakeLoad(){
		title="fake scene";
		clearColor=Color.black;
		camera=new Camera(0.,0.,-10.,0.,0.,0.,0.,1.,0.,45.,1.,20.,1.);
		Object3D ball1=new Object3D("ball1","","ROOT",2,0,1,0,0,1,45);
		Object3D ball2=new Object3D("ball2","","ball1",-2,0,0,0,1,0,90);
		
		objects.put(ball1.getName(), ball1);
		objects.put(ball2.getName(), ball2);
		
		linkObjects();	
	}
	
	/**
	 * links all the objects to their parent objects
	 * configures all the child lists for every object
	 */
	public void linkObjects(){
		Collection <Object3D>tempOjbectList= objects.values();
		for(Object3D object:tempOjbectList){
			if(object.getParent().equals(Scene.rootName)){
				sceneObjects.add(object);
			}
			else{
				object.linkToParent(objects);
			}
		}
	}
	
}
