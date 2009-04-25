package com.stereoviewer;

import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;

import com.sun.opengl.util.FPSAnimator;
import com.sun.opengl.util.GLUT;

import javax.swing.JFrame;
import javax.vecmath.Color4f;

/**
 * Class for the window the scene is seen in
 * @author Jesse Fish
 *
 */
public class SceneViewer extends JFrame{
	private static final long serialVersionUID = 1L;

	private static boolean do3d=false;

	private Scene scene;

	private FPSAnimator animator;

	private SceneController controller;

	private GLCanvas drawArea;
	/**
	 * 
	 */
	public SceneViewer()
	{
		super();
		this.setSize(600,600);
		int port=6789;
		try {
			controller=new SceneController(port,this);
			controller.start();
		} catch (IOException e) {
			System.out.println("ERROR: creating socket");
			e.printStackTrace();
		}

		scene=Scene.emptyScene();
		init();
		this.setTitle(scene.getTitle());
	}

	/**
	 * 
	 * @param width
	 * @param height
	 */
	public SceneViewer( int width, int height)
	{
		super();
		this.setSize(width,height);
		int port=6789;
		try {
			controller=new SceneController(port,this);
			controller.start();
		} catch (IOException e) {
			System.out.println("ERROR: creating socket");
			e.printStackTrace();
		}

		scene=Scene.emptyScene();
		init();
		this.setTitle(scene.getTitle());
	}

	/**
	 * 
	 * @param port
	 */
	public SceneViewer( int port)
	{
		super();
		this.setSize(600,600);
		try {
			controller=new SceneController(port,this);
			controller.start();
		} catch (IOException e) {
			System.out.println("ERROR: creating socket");
			e.printStackTrace();
		}

		scene=Scene.emptyScene();
		init();
		this.setTitle(scene.getTitle());
	}
	/**
	 * 
	 * @param width
	 * @param height
	 * @param port
	 */
	public SceneViewer( int width, int height, int port)
	{
		super();
		this.setSize(width,height);
		try {
			controller=new SceneController(port,this);
			controller.start();
		} catch (IOException e) {
			System.out.println("ERROR: creating socket");
			e.printStackTrace();
		}

		scene=Scene.emptyScene();
		init();
		this.setTitle(scene.getTitle());
	}


	/**
	 * initializes the window and gl settings
	 */
	public void init()
	{
		GLCapabilities capabilities=new GLCapabilities();
		if(do3d){
			capabilities.setStereo(true);
		}
		drawArea=new GLCanvas(capabilities);

		animator=new FPSAnimator(drawArea,60);
		drawArea.addGLEventListener(new Refresher());
		this.add(drawArea);
		animator.start();

		this.setLocationRelativeTo(null); // Center the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	/**
	 * things to do when we are reinitializing
	 */
	public void reinit(){
		animator.stop();
		this.remove(drawArea);
		GLCapabilities capabilities=new GLCapabilities();
		if(do3d){
			capabilities.setStereo(true);
		}
		drawArea=new GLCanvas(capabilities);

		animator=new FPSAnimator(drawArea,60);
		drawArea.addGLEventListener(new Refresher());
		this.add(drawArea);
		animator.start();

		this.setVisible(true);
	}
	public void loadScene(String path){
		System.out.println("loading scene "+path);
		scene=SceneLoader.loadScene(path);
		this.setTitle(scene.getTitle());
		reinit();
	}
	public Scene getScene() {
		return scene;
	}

	public void quit(){
		controller.setQuit(true);
		try {
			//give the controller 2 seconds to close
			controller.join(1000);
			System.exit(NORMAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dispose();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length>0){
			if(args.length==1){
				SceneViewer viewer= new SceneViewer(Integer.parseInt(args[0]));
			}
			else if(args.length==3){
				SceneViewer viewer= new SceneViewer(Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2]));
			}
		}
		else{
			SceneViewer viewer= new SceneViewer();
		}
	}







	/**
	 * class that responds when an GLEvent is fired
	 * @author Jesse Fish
	 *
	 */
	class Refresher implements GLEventListener
	{
		private GLU glu;
		private GLUT glut;

		/**
		 * draws every object into the scene
		 */
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL gl = gLDrawable.getGL();
			scene.draw(gl, glu, glut);
		}

		public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
			// TODO Auto-generated method stub

		}

		public void init(GLAutoDrawable gLDrawable) {
			GL gl = gLDrawable.getGL();
			gl.glShadeModel(GL.GL_SMOOTH);              // Enable Smooth Shading
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);			// Set The Blending Function For Translucency
			Color4f temp=SceneLight.getClear_color();
			gl.glClearColor(temp.x,temp.y, temp.z,temp.w);
			glu = new GLU();
			glut = new GLUT();

			//Start lighting setup

			//lighting stuff
			gl.glEnable(GL.GL_LIGHTING);
			gl.glEnable(GL.GL_COLOR_MATERIAL);
			gl.glEnable(GL.GL_DEPTH_TEST);
			gl.glEnable(GL.GL_DEPTH_BUFFER_BIT);

			gl.glEnable (GL.GL_BLEND);

			// make sure that this line is copied into the change ambient lighting method
			gl.glLightModelfv( GL.GL_LIGHT_MODEL_AMBIENT,SceneLight.getGlobalLightingfv(), 0 );


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

			//end lighting setup

			scene.initModels(gl, glu);
		}

		public void reshape(GLAutoDrawable gLDrawable,int x, int y, int width, int height) {
			final GL gl = gLDrawable.getGL();
			if (height <= 0) // avoid a divide by zero error
			{height = 1;}
			final float h = (float) width / (float) height;
			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL.GL_PROJECTION);
			gl.glLoadIdentity();
			glu.gluPerspective(scene.getCamera().getField_of_view(), h, scene.getCamera().getZnear(), scene.getCamera().getZfar());
			gl.glMatrixMode(GL.GL_MODELVIEW);
			gl.glLoadIdentity();
		}
	}
}


