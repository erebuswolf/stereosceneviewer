
/**
 * 
 * @author Jesse Fish
 *
 */
public class Vector3d {
	private double i;
	private double j;
	private double k;

	public Vector3d(double i, double j, double k){
		this.i=i;
		this.j=j;
		this.k=k;
	}
	
	/**
	 * method to multiply the vector by a scalar
	 * @param a the scalar to multiply the vector by
	 */
	public void scale(double a){
		i*=a;
		j*=a;
		k*=a;
	}
	
	/**
	 * Normalizes the vector object
	 */
	public void normalize(){
		double magnitude=Math.sqrt(i*i+j*j+k*k);
		i/=magnitude;
		j/=magnitude;
		k/=magnitude;
	}

	public static Vector3d zeros(){
		return new Vector3d(0,0,0);
	}

	public static Vector3d ones(){
		return new Vector3d(1,1,1);
	}
	
	/**
	 * Takes the cross product of two Vector3d objects
	 * @param a a Vector3d object
	 * @param b a Vector3d object
	 * @return a new Vector3d object that is the cross product of a and b
	 */
	public static Vector3d crossProduct(Vector3d a, Vector3d b){

		return new Vector3d(a.j*b.k-a.k*b.j, a.k*b.i-a.i*b.k, a.i*b.j-a.j*b.i);
	}

	/**
	 * method to add two Vector3ds together returning a new Vector3d object
	 * @param a a Vector3d object
	 * @param b a Vector3d object
	 * @return a new Vector3d object that is the sum of the argument Vector3ds
	 */
	public static Vector3d add(Vector3d a, Vector3d b){
		return new Vector3d(a.i+b.i,a.j+b.j,a.k+b.k);
	}

	/**
	 * returns a new Vector3d object that is the Vector3d a multiplied by the scalar b
	 * @param a a Vector3d object
	 * @param b a scalar to multiply the Vector3d object by
	 * @return a new Vector3d object that is the result of multiplying the Vector3d a by the scalar b
	 */
	public static Vector3d scalarMult(Vector3d a, double b){
		return new Vector3d(a.i*b,a.j*b,a.k*b);
	}


	public double getI() {
		return i;
	}

	public void setI(double i) {
		this.i = i;
	}

	public double getJ() {
		return j;
	}

	public void setJ(double j) {
		this.j = j;
	}

	public double getK() {
		return k;
	}

	public void setK(double k) {
		this.k = k;
	}

}
