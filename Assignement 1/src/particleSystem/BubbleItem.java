package particleSystem;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.CreateEmptyCircle;
import utilityFunctions.CreateFilledCircle;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class BubbleItem {
	//Set up private variables
	private GL2 gl;
	private GLUT glut;
	private float ratioMultiplier;
	
	//Set up build parts
	private CreateEmptyCircle outerShine;
	private CreateFilledCircle bubbleInner;
	
	//Set up bubble ID as public ID
	public float[] position = new float[2];	
	public float dy;
	public float dx;
	public float dr;
	public float age;
	public float radiusOfBubble;
	public float waterHeight;
	
	//Constructor
	public BubbleItem(GL2 gl, GLUT glut, float[] startingPos, float ratioMultiplier, int startingAge, float waterH) {
		this.gl = gl;
		this.glut = glut;

		//Set up object origin data that can't be set up in the draw bubble function
		this.position = startingPos;
		this.ratioMultiplier = ratioMultiplier;
		this.radiusOfBubble = (float)(Math.random()*0.10f);
		this.age = startingAge;
		this.waterHeight = waterH;
		
		this.dy = (float)Math.random()*0.2f+0.05f; //randomly determine the speed at which the bubbles go upwards
		this.dx = (float)Math.random()*0.2f-0.1f; //randomly determine the movement left or right by the bubbles
		this.dr = (float)Math.random()*0.005f-0.01f; //randomly determine the radius reduction speed

		this.setUpObject();
	}
	
	//Set up the object to be rendered
	public void setUpObject() {		
		bubbleInner = new CreateFilledCircle(gl, glut);
		outerShine = new CreateEmptyCircle(gl);
	}
	
	//Set up the object data
	public void drawBubble(boolean displayData) {
		//Render the object itself
		this.renderBubble(displayData);
	}
	
	//Render the object itself
	private void renderBubble(boolean displayData) {
		bubbleInner.drawCircle(true, displayData, radiusOfBubble, position, new float[]{0.529f, 0.425f, 0.65f, 0.4f}, new float[]{1.0f,1.0f,1.0f,0.8f}, ratioMultiplier, 0.0f, 0.0f);
		outerShine.drawCircle(true, radiusOfBubble, position, new float[]{1.0f,1.0f,1.0f,0.8f}, 2, ratioMultiplier, 0.0f, 0.0f);
		
		bubbleInner.renderDataAll(displayData, age, dx, dy, dr);
	}
	
	//Animate the particle
	public void animate(double time) {
		//If the bubble hits the wall then bounce off and turn around with 85% energy
		if((this.position[0]+(this.radiusOfBubble/2))>=1.0f) {
			this.dx*=-1.0f; 
			this.dx*=0.85;
		}
		//If the bubble hits the top of the water, then stop going up and float on the surface and also use 96% energy
		if((this.position[1]+(this.radiusOfBubble))>=waterHeight) {
			this.dy=(this.dr)*-1;
			this.dx*=0.96f;
		}
		
		this.age += time;
		this.position[0] += this.dx * time;
		this.position[1] += this.dy * time;
		
		this.radiusOfBubble += this.dr * time;
	}
}
