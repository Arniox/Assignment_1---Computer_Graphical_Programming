package itemCreation;
import com.jogamp.opengl.GL2;

import utilityFunctions.CreateCustomQuad;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class Water {
	private GL2 gl;
	
	//Water
	private CreateCustomQuad water;
	private float waterHeight;
	private boolean blendOption;
	
	//Constructor
	public Water(GL2 gl, float waterHeight) {
		this.gl = gl;
		this.waterHeight = waterHeight;
		
		this.setUpObject();
	}
	
	//Set up the object
	public void setUpObject() {
		water = new CreateCustomQuad(gl, 50, waterHeight, 0.2f);
	}
	
	//Set up the object data and then render
	public void drawWater(boolean blend) {
		this.blendOption = blend;
		//Render the water
		this.renderWater();
	}
	
	//Render the actual object
	private void renderWater() {
		//draw the custom quad
		float[] color4Bottom = new float[]{0.0f, 0.0f, 0.1f, 1.0f};
		float[] color4Top = new float[]{0.239f, 0.427f, 1.0f, 1.0f};
		water.drawCustomQuad(blendOption, true, new float[]{-1.0f, -1.0f}, 2.0f, color4Bottom, color4Top);
	}
	
	//Get data
	public String getWaterData() {
		String s = "minY: "+water.getQuadArea()[0]+
				   ", maxY: "+String.format("%.3f",water.getQuadArea()[1])+
				   ", minX: "+water.getQuadArea()[2]+
				   ", maxX: "+water.getQuadArea()[3];
		
		return s;
	}
	
	//Animate the water:
	public void animate(double time) {
		//animate the water
		water.animate(time);
	}
	
	
	//Setters and getters
	public CreateCustomQuad getWater() {
		return this.water;
	}
}
