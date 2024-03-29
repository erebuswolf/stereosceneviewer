package com.stereoviewer;

import java.io.IOException;
import java.util.LinkedList;

import javax.vecmath.Color4f;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SceneLoader {

	/**
	 * Parses the xlm file at the path parameter using the dom api and creates
	 * a scene object from the xlm file and returns it, also sets up lights and lighting
	 * appropriately
	 * 
	 * @param path the path to the scene file to be loaded
	 * @throws Exception 
	 */
	public static Scene loadScene(String path) throws Exception{
		Document dom=parseXmlFile(path);
		//get the root element of the file, which is the scene
		Element scene=dom.getDocumentElement();

		String title=getTextValue(scene,"Title");
		LinkedList <Object3D> allObjects=new LinkedList<Object3D>();
		LinkedList<SceneLight> lightList=new LinkedList<SceneLight>();
		Camera camera=null;

		String renderString=getTextValue(scene,"StereoRender");
		boolean renderStereo=false;
		if(renderString.equals(Scene.on)){
			renderStereo=true;
		}		
		else if(renderString.equals(Scene.off)){
			renderStereo=false;
		}
		else{
			throw new Exception("Invalid Scene File at StereoRender, must have on or off value, defaulting to off");	
		}

		//make a list of nodes of the objects in the file
		NodeList nl = scene.getElementsByTagName("Object");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				//get the Object3d element
				Element el = (Element)nl.item(i);
				//get the object and add it to the list
				allObjects.add(getObject(el));
			}
		}

		//grab the camera element
		NodeList camlist = scene.getElementsByTagName("Camera");
		if(camlist != null && camlist.getLength() > 0) {
			Element el=(Element)camlist.item(0);
			if(el!=null){
				camera=getCamera(el);}
		}

		//grab the global lighting element
		NodeList globalLight = scene.getElementsByTagName("GlobalLight");
		if(globalLight != null && globalLight.getLength() > 0) {
			Element el=(Element)globalLight.item(0);
			if(el!=null){
				try {
					getGlobalLighting(el);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		}

		//grab the global lighting element
		NodeList lightSources = scene.getElementsByTagName("LightSource");
		if(lightSources != null && lightSources.getLength() > 0) {
			for(int i = 0 ; i < lightSources.getLength();i++) {
				//get the Object3d element
				Element el = (Element)lightSources.item(i);
				//get the object and add it to the list
				lightList.add(getLight(el));
			}
		}
		return new Scene(title,renderStereo,camera,allObjects,lightList);
	}

	private static SceneLight getLight(Element obj){
		
		String name= getTextValue(obj,"Name");
		int light_number=getIntValue(obj,"Number");

		float AmbientColorR= (float) getDoubleValue(obj,"AmbientColorR");
		float AmbientColorG= (float) getDoubleValue(obj,"AmbientColorG");
		float AmbientColorB= (float) getDoubleValue(obj,"AmbientColorB");
		float AmbientColorA= (float) getDoubleValue(obj,"AmbientColorA");
		Color4f ambient=new Color4f(AmbientColorR,AmbientColorG,AmbientColorB,AmbientColorA);

		float DiffuseColorR= (float) getDoubleValue(obj,"DiffuseColorR");
		float DiffuseColorG= (float) getDoubleValue(obj,"DiffuseColorG");
		float DiffuseColorB= (float) getDoubleValue(obj,"DiffuseColorB");
		float DiffuseColorA= (float) getDoubleValue(obj,"DiffuseColorA");
		Color4f diffuse=new Color4f(DiffuseColorR,DiffuseColorG,DiffuseColorB,DiffuseColorA);

		float SpecularColorR= (float) getDoubleValue(obj,"SpecularColorR");
		float SpecularColorG= (float) getDoubleValue(obj,"SpecularColorG");
		float SpecularColorB= (float) getDoubleValue(obj,"SpecularColorB");
		float SpecularColorA= (float) getDoubleValue(obj,"SpecularColorA");
		Color4f specular=new Color4f(SpecularColorR,SpecularColorG,SpecularColorB,SpecularColorA);


		double posx= getDoubleValue(obj,"PositionX");
		double posy= getDoubleValue(obj,"PositionY");
		double posz= getDoubleValue(obj,"PositionZ");


		double directionx= getDoubleValue(obj,"DirectionX");
		double directiony= getDoubleValue(obj,"DirectionY");
		double directionz= getDoubleValue(obj,"DirectionZ");

		float spot_Cutoff=(float) getDoubleValue(obj,"Spot_Cutoff");
		float intensity=(float) getDoubleValue(obj,"Intensity");
		float constant_attenuation_constant=(float) getDoubleValue(obj,"ConstAtt");
		float linear_attenuation_constant=(float) getDoubleValue(obj,"LinearAtt");
		float quad_attenuation_constant=(float) getDoubleValue(obj,"QuadAtt");

		return new SceneLight(name, light_number, 
				ambient, diffuse, specular, 
				posx,posy,posz,
				directionx,directiony,directionz,
				spot_Cutoff,intensity, 
				constant_attenuation_constant, linear_attenuation_constant,
				quad_attenuation_constant);	
	}


	/**
	 * Sets up global lighting from the XML file
	 * @param obj the Element to parse the global lighting from
	 * @return
	 * @throws Exception 
	 */
	private static void getGlobalLighting(Element obj) throws Exception{
		Color4f lighting=new Color4f((float)getDoubleValue(obj,"ColorR"),(float)getDoubleValue(obj,"ColorG"),(float)getDoubleValue(obj,"ColorB"),(float)getDoubleValue(obj,"ColorA"));

		SceneLight.setGlobal_light_values(lighting);

		Color4f clear_color=new Color4f((float)getDoubleValue(obj,"ClearColorR"),(float)getDoubleValue(obj,"ClearColorG"),(float)getDoubleValue(obj,"ClearColorB"),(float)getDoubleValue(obj,"ClearColorA"));
		SceneLight.setClear_color(clear_color);

		String temp;

		temp= getTextValue(obj,"GL_LIGHT_MODEL_LOCAL_VIEWER");
		if(temp.equalsIgnoreCase(Scene.on)){
			SceneLight.setLocal_viewer(true);
		}
		else if(temp.equalsIgnoreCase(Scene.off)){
			SceneLight.setLocal_viewer(false);
		}else{
			throw new Exception("Invalid Scene File at GL_LIGHT_MODEL_LOCAL_VIEWER, must have on or off value, defaulting to off");
		}

		temp= getTextValue(obj,"GL_LIGHT_MODEL_TWO_SIDE");
		if(temp.equalsIgnoreCase(Scene.on)){
			SceneLight.setTwo_side(true);
		}
		else if(temp.equalsIgnoreCase(Scene.off)){
			SceneLight.setTwo_side(false);
		}else{
			throw new Exception("Invalid Scene File at GL_LIGHT_MODEL_TWO_SIDE, must have on or off value, defaulting to off");
		}
	}
	/**
	 * Creates a Camera object from an xlm Element
	 * @param obj the Element to parse the Camera from
	 * @return
	 */
	private static Camera getCamera(Element obj){
		double fov= getDoubleValue(obj,"FieldOfView");
		double nearz= getDoubleValue(obj,"NearClippingPlane");
		double farz= getDoubleValue(obj,"FarClippingPlane");
		double iod= getDoubleValue(obj,"IntraOcularDistance");

		double posx= getDoubleValue(obj,"PositionX");
		double posy= getDoubleValue(obj,"PositionY");
		double posz= getDoubleValue(obj,"PositionZ");

		double upx= getDoubleValue(obj,"UpX");
		double upy= getDoubleValue(obj,"UpY");
		double upz= getDoubleValue(obj,"UpZ");

		double targetx= getDoubleValue(obj,"TargetX");
		double targety= getDoubleValue(obj,"TargetY");
		double targetz= getDoubleValue(obj,"TargetZ");

		return new Camera(posx,posy,posz,targetx,targety,targetz,upx,upy,upz,fov,nearz,farz,iod);
	}

	/**
	 * Returns an Object3D object created from the xlm Element passed to the method
	 * @param obj the Element to create an Object3D from
	 * @return
	 */
	private static Object3D getObject(Element obj){
		String name= getTextValue(obj,"Name");
		String parent= getTextValue(obj,"Parent");
		String path= getTextValue(obj,"Model");

		double posx= getDoubleValue(obj,"PositionX");
		double posy= getDoubleValue(obj,"PositionY");
		double posz= getDoubleValue(obj,"PositionZ");

		double rotx= getDoubleValue(obj,"RotationX");
		double roty= getDoubleValue(obj,"RotationY");
		double rotz= getDoubleValue(obj,"RotationZ");
		double rotangle= getDoubleValue(obj,"RotationAngle");


		double scalex= getDoubleValue(obj,"ScaleX");
		double scaley= getDoubleValue(obj,"ScaleY");
		double scalez= getDoubleValue(obj,"ScaleZ");
		return new Object3D(name,path,parent,posx,posy,posz,rotx,roty,rotz,rotangle,scalex,scaley,scalez);
	}

	/**
	 * parses the xml file at the specified path and returns the Document object from
	 * that parse
	 * @param path the path to the file to be parsed
	 * @return
	 */
	private static Document parseXmlFile(String path){
		Document dom;
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			//parse using builder to get DOM representation of the XML file
			dom = db.parse(path);
			return dom;
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}	

	/**
	 * Takes a xml element and the tag name, look for the tag and get
	 * the text content 
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private static int getIntValue(Element ele, String tagName) {
		String temp=getTextValue(ele,tagName);
		return Integer.parseInt(temp);
	}

	/**
	 * Calls getTextValue and returns a double value
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private static double getDoubleValue(Element ele, String tagName) {
		String temp=getTextValue(ele,tagName);
		return Double.parseDouble(temp);
	}

}
