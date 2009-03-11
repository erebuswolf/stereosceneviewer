package com.joglobj;


public class OBJMaterial
{
	Vector3 ambient;
	Vector3 diffuse;
	Vector3 specular;
	int texId;
	OBJMaterial()
	{
		texId = -1;
	}
	public Vector3 getAmbient() {
		return ambient;
	}
	public void setAmbient(Vector3 ambient) {
		this.ambient = ambient;
	}
	public Vector3 getDiffuse() {
		return diffuse;
	}
	public void setDiffuse(Vector3 diffuse) {
		this.diffuse = diffuse;
	}
	public Vector3 getSpecular() {
		return specular;
	}
	public void setSpecular(Vector3 specular) {
		this.specular = specular;
	}
	public int getTexId() {
		return texId;
	}
	public void setTexId(int texId) {
		this.texId = texId;
	}
	
}

