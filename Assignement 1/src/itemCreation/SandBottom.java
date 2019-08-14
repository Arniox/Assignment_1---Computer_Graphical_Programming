package itemCreation;
import com.jogamp.opengl.GL2;

import utilityFunctions.CreateCustomQuad;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class SandBottom {
	//Main variables
	private GL2 gl;
	
	//Sand bottom is just custom quad
	private CreateCustomQuad sandyBottom;
	
	//Sand data
	private float sandHeight;
	private boolean blendOption;
	
	//Constructor
	public SandBottom(GL2 gl, float sandBottomHeight) {
		this.gl = gl;
		this.sandHeight = sandBottomHeight;
		
		this.setUpObject();
	}
	
	//Set up the objects
	public void setUpObject() {
		//gl, custom quad detail, height, height variation
		sandyBottom = new CreateCustomQuad(gl, 20, sandHeight, 0.25f);
	}
	
	//Set up the object data and then render
	public void drawSandBottom(boolean blend) {
		this.blendOption = blend;
		
		//Render the object
		this.renderSandyBottom();
	}
	
	//Render the actual object
	private void renderSandyBottom() {
		//draw the custom quad
		float[] color4Bottom = new float[]{0.752f,0.615f,0.36f,1.0f};
		float[] color4Top = new float[]{0.752f,0.615f,0.36f,0.0f};
		sandyBottom.drawCustomQuad(blendOption, false, new float[]{-1.0f,-1.0f}, 2.0f, color4Bottom, color4Top);
	}
}
