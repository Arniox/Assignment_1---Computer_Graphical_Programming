package particleSystem;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class AlgaeDot {
	//Set up private variables
	private GL2 gl;
	
	//Set up algae data
	public float[] position = new float[2];
	public float[] color;
	public float pointSize;
	public float dy;
	public float dx;
	public float ds;
	public float age;
	
	//Water height
	public float waterHeight;
	
	//Constructor
	public AlgaeDot(GL2 gl, float[] startingPos) {
		this.gl = gl;
		this.position = startingPos;
		
		this.setUpObject();
	}
	
	//Setup
	public void setUpObject() {
		this.dy = (float)Math.random()*0.02f-0.01f; //Speed of y movement
		this.dx = (float)Math.random()*0.02f-0.01f; //Speed of x movement
		this.ds = (float)Math.random()*0.2f; //Speed of size increase
		
		this.color = new float[]{0.0f, (float)Math.random()*0.157f+0.329f, (float)Math.random()*0.024f+0.054f};
		this.pointSize = 0;
	}
	
	//Set up object itself and the render
	public void drawAlgaeDot(float waterHeight) {
		this.waterHeight = waterHeight;
		
		//Render the algae itself
		this.renderAlgaeDot();
	}
	
	//Render the actual object
	private void renderAlgaeDot() {
		gl.glColor3fv(this.color, 0);
		gl.glPointSize(pointSize);
		
		gl.glBegin(GL2.GL_POINTS);
		gl.glVertex2fv(this.position, 0);
		
		gl.glEnd();
	}
	
	//Animate
	public void animate(double time) {
		//Randomly switch velocity
		if(age%60==0) {
			this.dx*=-1.0f;
			this.dy*=-1.0f;
		}
		//Hit walls
		if(this.position[0]>=1.0f || this.position[0]<=-1.0f) {
			this.dx*=-1.0f;
		}
		//If hit sand water top
		if(this.position[1]>=this.waterHeight || this.position[1]<=-1.0f) {
			this.dy*=-1.0f;
		}
		
		//Move
		this.age+=time;
		this.pointSize+=this.ds*time;
		this.position[0]+=this.dx*time;
		this.position[1]+=this.dy*time;
	}
	
	//Move towards pump
	public void goTo(float[] clickPosition) {
		float particleGoToSpeed = 0.01f;
		
		this.ds*=-1f;
		
		this.position[0] = lerp(this.position[0], clickPosition[0], cubic_scurve3(particleGoToSpeed));
		this.position[1] = lerp(this.position[1], clickPosition[1], cubic_scurve3(particleGoToSpeed));
	}
	
	//Functions
	private float cubic_scurve3(float alpha) {
		return alpha*alpha*(3.0f - 2.0f*alpha);
	}
	
	private float lerp(float point1, float point2, float alpha) {
		return point1+alpha*(point2-point1);
	}
	
	
}
