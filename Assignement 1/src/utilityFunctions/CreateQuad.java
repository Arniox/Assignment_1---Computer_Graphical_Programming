package utilityFunctions;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CreateQuad {
	
	//Set up private variables
	private GL2 gl;
	private float[] backTopCornerPos;
	private float length;
	private float height;
	
	private float[] color4;
	
	//Constructor
	public CreateQuad(GL2 gl) {
		this.gl = gl;
	}
	
	//Set data and then render
	public void drawQuad(float[] backTopCornerPos, float length, float height, float[] color4) {		
		//Set data for quad
		this.backTopCornerPos = backTopCornerPos;
		this.length = length;
		this.height = height;
		this.color4 = color4;
		
		//Render the quad itself
		this.renderQuad();
	}
	
	//Render the quad itself
	private void renderQuad() {
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4fv(this.color4, 0);
		
		gl.glVertex2f(backTopCornerPos[0], backTopCornerPos[1]);
		gl.glVertex2f(backTopCornerPos[0]+this.length, backTopCornerPos[1]);
		gl.glVertex2f(backTopCornerPos[0]+this.length, backTopCornerPos[1]-this.height);
		gl.glVertex2f(backTopCornerPos[0], backTopCornerPos[1]-this.height);
		gl.glEnd();
	}
	
	//Getters and setters
	public void setColor(float[] color4) {
		this.color4 = color4;
	}
	
	//Get quad area
	public float[] getQuadArea() {
		float[] quadArea = new float[4];
		//Set up empty variables
		float minY = backTopCornerPos[1]-this.height;
		float maxY = backTopCornerPos[1];
		float minX = backTopCornerPos[0];
		float maxX = backTopCornerPos[0]+this.length;
		
		quadArea[0] = minY;
		quadArea[1] = maxY;
		quadArea[2] = minX;
		quadArea[3] = maxX;
		
		return quadArea;
	}
}
