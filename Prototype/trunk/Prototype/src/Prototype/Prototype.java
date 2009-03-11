package Prototype;


import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;

import javax.swing.*;
import javax.media.opengl.*;
import java.awt.*;
import java.awt.event.*;


/**!
 * Class for the window the scene is seen in
 * @author Jesse Fish
 *
 */
public class Prototype extends JFrame{
	private static final int DEFAULT_WIDTH = 640;
	private static final int DEFAULT_HEIGHT = 480;
	public static final float depthZ = 5.0f;                    //depth of the object drawing
	public static final double screenZ = 10.0;                 //screen projection plane
	public static double cubepos = 5.0;                

	public static final double IOD = 0.5;                     //intraocular distance

	FPSAnimator animator;


	boolean fullscreen = false;
	boolean displayChanged = false;
	
	GraphicsEnvironment ge=null;
	GraphicsDevice gd=null;
	GraphicsDevice myDevice;
	public DisplayMode dm, dm_old;


	public Prototype()
	{
		super();
		
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		// Save old displaymode and get new one to play with.
		dm_old = gd.getDisplayMode();
		dm = dm_old;
		myDevice=gd;
		
		GLCapabilities capabilities=new GLCapabilities();
		capabilities.setStereo(true);
		GLCanvas drawArea=new GLCanvas(capabilities);

	//	dofullscreen();
		
		animator=new FPSAnimator(drawArea,60);
		drawArea.addGLEventListener(new Refresher());
		add(drawArea);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		animator.start();
		this.setLocationRelativeTo(null); // Center the frame
		this.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		this.setVisible(true);
		//this.setLocation(300, 300);
	

	}
	public boolean dofullscreen()
	{
		this.setUndecorated( true );
		if( gd.isFullScreenSupported() )
		{
			System.out.println("Fullscreen...");//ddd
			try {
				gd.setFullScreenWindow( this );
				fullscreen = true; 
			} catch( Exception e ) {
				gd.setFullScreenWindow( null );
				fullscreen = false; 
			}
			// Once an application is in full-screen exclusive mode, 
			// it may be able to take advantage of actively setting the display mode.
			if( fullscreen &&
					gd.isDisplayChangeSupported() ) 
			{
				// Change displaymode here [..]
				try {
					myDevice.setDisplayMode( dm );
					displayChanged = true;
				} catch( Exception e ) {
					myDevice.setDisplayMode( dm_old );
					displayChanged = false;
				}
			}
		}
		return fullscreen;
	}


	public void start()
	{
		animator.start();
	}

	public void stop()
	{
		animator.stop();
	}

	public static void main(String args[])
	{
		// Create a frame
		//		JFrame frame = new JFrame("Applet is in the frame");

		// Create an instance of the applet
		Prototype frame = new Prototype();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Add the applet to the frame
		//	frame.add(applet, BorderLayout.CENTER);


		// Display the frame
		/*frame.setLocationRelativeTo(null); // Center the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		frame.setVisible(true);
		frame.setLocation(300, 300);*/
	}
}

class Refresher implements GLEventListener
{
	private GLU glu;
	private GLUT glut;
	public void display(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
		final GL gl = gLDrawable.getGL();

		//draw the back left buffer
		gl.glDrawBuffer(GL.GL_BACK_LEFT);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		glu.gluLookAt(-Prototype.IOD/2, 0, 0, 0, 0, Prototype.screenZ, 0, 1, 0);

		gl.glPushMatrix();

		gl.glTranslatef(0.0f, 0.0f, (float) (Prototype.cubepos));               //translate to screenplane
		glut.glutWireCube(1.0f);

		gl.glPopMatrix();

		//draw the back right buffer
		gl.glDrawBuffer(GL.GL_BACK_RIGHT);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		glu.gluLookAt(Prototype.IOD/2, 0, 0, 0, 0, Prototype.screenZ, 0, 1, 0);

		gl.glPushMatrix();

		gl.glTranslatef(0.0f, 0.0f, (float) (Prototype.cubepos));          //translate to screenplane
		glut.glutWireCube(1.0f);

		gl.glPopMatrix();

	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	public void init(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);              // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background		

		glu = new GLU();
		glut=new GLUT();
	}

	public void reshape(GLAutoDrawable gLDrawable,int x, int y, int width, int height) {

		final GL gl = gLDrawable.getGL();
		if (height <= 0) // avoid a divide by zero error!
		{height = 1;}
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 200.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}

