package com.joglobj;

import java.util.ArrayList;

public class OBJMesh
{
	String name;
	OBJMaterial material;  
	ArrayList <Vector3>vertexList;
	ArrayList <Vector3>texcoordList;
	ArrayList <Vector3>normalList;
	ArrayList <OBJFace>faceList;

	public OBJMaterial getMaterial() {
		return material;
	}

	public void setMaterial(OBJMaterial material) {
		this.material = material;
	}

	OBJMesh()
	{
		vertexList = new ArrayList<Vector3>();
		texcoordList = new ArrayList<Vector3>();
		normalList = new ArrayList<Vector3>();
		faceList = new ArrayList<OBJFace>();
		material = new OBJMaterial();
	}

	void setName( String n )
	{
		name = n;
	}

	void addVertex( Vector3 v )
	{
		vertexList.add( v );
	}

	void addTexCoord( Vector3 v )
	{
		texcoordList.add( v );
	}

	void addNormal( Vector3 v )
	{
		normalList.add( v );
	}

	// Add faces
	void addFace( OBJFace f )
	{
		faceList.add( f );
	}
	void addFace( int a, int b, int c )
	{
		faceList.add( new OBJFace(a, b, c) );
	}

}
