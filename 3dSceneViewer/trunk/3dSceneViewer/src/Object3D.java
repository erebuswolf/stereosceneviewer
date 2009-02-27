import java.awt.Color;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

/**
 * Class for each object being shown
 * @author Jesse Fish
 *
 */
public class Object3D {

	private String name;
	private String path;

	private Color color;
	private double transparency;

	private String parent;

	private Vector3d position;

	private Vector3d rotationVector;
	private double rotationAngle;
	

	//need a model class implementation
	//private Model model

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
	 * @param color
	 * @param transparency
	 */
	public Object3D(String name,String path, String parent,double posx,double posy,double posz,double rotx,double roty,double rotz, double rotationAngle, Color color, double transparency)
	{
		this.path=path;
		this.parent=parent;
		this.color=color;
		this.transparency=transparency;
		this.name=name;
		position=new Vector3d(posx,posy,posz);
		rotationVector=new Vector3d(rotx,roty,rotz);
		this.rotationAngle=rotationAngle;
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
		gl.glTranslated(position.getI(), position.getJ(), position.getK());
		gl.glRotated(rotationAngle, rotationVector.getI(), rotationVector.getJ(), rotationVector.getK());
		glut.glutWireSphere(1, 20, 20);
		
		//draw children
		for(Object3D object:children){
			object.draw(gl, glu, glut);
		}
		
		//pop the matrix
		gl.glPopMatrix();
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
	
	/**
	 * sets the rotation of the object to the input position
	 * @param posx
	 * @param posy
	 * @param posz
	 */
	public void setRotation(double rotx,double roty,double rotz)
	{
		rotationVector=new Vector3d(rotx,roty,rotz);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setColor(Color color)
	{
		this.color=color;
	}

	public String getParent() {
		return parent;
	}
	
	public void setTransparency(double transparency) {
		this.transparency = transparency;
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
}
