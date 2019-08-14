package utilityFunctions;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import mainFunctions.ButtonState;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CreateButton {
	
	//Set up private variables
	private GL2 gl;
	private ButtonState state;
	private GLUT glut;
	private float[] backTopCornerPos;
	private String buttonText;
	
	//Object creation
	private CreateQuad quad;
	//Scaling stuff
	private float screenWidth;
	private float length;
	private int buttonNumber;
	
	//Constructor
	public CreateButton(GL2 gl) {
		this.gl = gl;
		
		//Set up the object
		this.setUpObject();
	}
	
	//Setup Object
	public void setUpObject() {
		//Create one quad behind the text
		this.quad = new CreateQuad(this.gl);
	}
	
	//Set the data and then render
	public float drawButton(ButtonState state, GLUT glut, float[] backTopCornerPos, String buttonText, float screenW, float length, int buttonNum, float previousScaling) {
		//Set all data for the button
		this.state = state;
		this.glut = glut;
		this.backTopCornerPos = backTopCornerPos;
		this.buttonText = buttonText;
		this.screenWidth = screenW;
		this.length = length;
		this.buttonNumber = buttonNum;
		
		//Render object itself
		return this.renderButton(state, previousScaling);
	}
	
	//Render the button itself
	private float renderButton(ButtonState state, float previousScaling) {
		
		//Get scaling info from textLength
		float stringObjectLength = 0;
		for(int i=0; i<buttonText.length(); i++) {
			stringObjectLength+=(glut.glutBitmapWidth(GLUT.BITMAP_9_BY_15, buttonText.charAt(i)));
		}
		//Set scaling for button:
		float scalingLength = (stringObjectLength/screenWidth)+length;
		this.backTopCornerPos[0]+=((float)buttonNumber/8.33f)*(previousScaling*10f);
		
		//Draw the quad
		//Set button colour based on state
		if(this.state==ButtonState.NOT_CLICKED) {
			this.quad.drawQuad(this.backTopCornerPos, scalingLength, 0.05f, new float[]{0.0f, 0.79f, 1.0f, 1.0f});
		} else if(this.state==ButtonState.HOVER) {
			this.quad.drawQuad(this.backTopCornerPos, scalingLength, 0.05f, new float[]{0.0f, 0.396f, 0.498f, 1.0f});
		} else if(this.state==ButtonState.CLICKED) {
			this.quad.drawQuad(this.backTopCornerPos, scalingLength, 0.05f, new float[]{0.0f, 0.247f, 0.317f, 1.0f});
		}
		
		//Render the button text
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glRasterPos2f(backTopCornerPos[0]+0.018f, backTopCornerPos[1]-0.035f);
		glut.glutBitmapString(GLUT.BITMAP_9_BY_15, buttonText+"");
		gl.glEnd();
		
		//return previous button's scaling length
		return scalingLength;
	}
	
	//Getters and setters
	public CreateQuad getQuad() {
		return this.quad;
	}
}
