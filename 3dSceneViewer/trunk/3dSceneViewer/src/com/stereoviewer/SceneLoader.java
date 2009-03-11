package com.stereoviewer;

import java.io.IOException;
import java.util.LinkedList;

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
	 * a scene object from the xlm file and returns it
	 * 
	 * @param path the path to the scene file to be loaded
	 */
	public static Scene loadScene(String path){
		Document dom=parseXmlFile(path);
		//get the root element of the file, which is the scene
		Element scene=dom.getDocumentElement();

		String title=getTextValue(scene,"Title");
		LinkedList <Object3D> allObjects=new LinkedList<Object3D>();
		Camera camera=null;


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

		return new Scene(title,camera,allObjects);
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
		return new Object3D(name,path,parent,posx,posy,posz,rotx,roty,rotz,rotangle);
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
