package utilityFunctions;

import com.jogamp.opengl.GL2;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CreateTriangleFan {
	//Set up private variable
	private GL2 gl;
	
	//Set up object data
	private int triangleFanCorners;
	private float[] firstVertex;
	private float[][] vertexes;
	private float[] color4;
	
	//Constructor
	public CreateTriangleFan(GL2 gl, int triangleDetail) {
		this.gl = gl;
		this.triangleFanCorners = triangleDetail;
		
		this.setUpObject();
	}
	
	//Set up the object
	public void setUpObject() {
		this.vertexes = new float[triangleFanCorners][2];
	}
	
	//Set data and then render
	public void drawTriangleFan(float[] firstVertex, float[][] vertexArray, float[] color4) {
		//Set data for triangle
		this.firstVertex = firstVertex;
		for(int i=0;i<triangleFanCorners;i++) {
			this.vertexes[i][0] = vertexArray[i][0];
			this.vertexes[i][1] = vertexArray[i][1];
		}
		
		this.color4 = color4;
		
		//Render the triangle itself
		this.renderTriangleFan();
	}
	
	//Render the triangle itself
	private void renderTriangleFan() {
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4fv(this.color4, 0);
		
		//Set first vertex which is center of triangle fan
		gl.glVertex2fv(firstVertex, 0);
		
		//For loop rest of the triangle points
		for(int i=0;i<triangleFanCorners;i++) {
			gl.glVertex2f(this.vertexes[i][0], this.vertexes[i][1]);
		}
		
		gl.glEnd();
	}
}
