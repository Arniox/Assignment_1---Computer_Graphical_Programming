package itemCreation;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import mainFunctions.ButtonState;
import utilityFunctions.CreateButton;
import utilityFunctions.CreateQuad;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class Menu {
	private GL2 gl;
	private GLUT glut;
	
	//Set up private variables	
	private CreateButton pumpButton;
	private CreateButton cleanButton;
	private CreateQuad windowElements;
	
	//Data button
	private CreateButton showData;
	//Scaling stuff
	private float screenWidth;
	
	//Display list array list of integers
	private ArrayList<Integer> displayList;
	
	public Menu(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
		
		displayList = new ArrayList<Integer>();
		displayList.add(gl.glGenLists(1));
		
		this.setUpObject();
	}
	
	public void setUpObject() {
		//Create the window elements, pump button and clean button
		windowElements = new CreateQuad(gl);
		pumpButton = new CreateButton(gl);
		cleanButton = new CreateButton(gl);
		
		//Data button
		showData = new CreateButton(gl);
	}
	
	//Set the data for the object and then render
	public void drawMenu(ButtonState pumpButtonState, ButtonState cleanButtonState, ButtonState dataButtonState, float screenW) {
		this.screenWidth = screenW;
		
		this.renderMenu(pumpButtonState, cleanButtonState, dataButtonState);
	}
	
	//Render the objects them selves
	private void renderMenu(ButtonState pumpButtonState, ButtonState cleanButtonState, ButtonState dataButtonState){
		//Display list window element since it is static
		gl.glNewList(displayList.get(0), GL2.GL_COMPILE);
		windowElements.drawQuad(new float[]{-1.0f, 1.0f}, 2.0f, 0.10f, new float[]{0.0f,0.0f,0.0f,1.0f});
		gl.glEndList();
		
		//Call items in display list
		gl.glCallList(displayList.get(0));
		
		//Draw/animate the windows elements, pump button and clean button
		float previousScalingState = 0;
		
		previousScalingState = pumpButton.drawButton(pumpButtonState, glut, (new float[] {-0.99f, 0.99f}), "Pump", screenWidth, 0.07f, 0, previousScalingState);
		previousScalingState = cleanButton.drawButton(cleanButtonState, glut, (new float[] {-0.99f, 0.99f}), "Clean", screenWidth, 0.07f, 1, previousScalingState);
		
		//Data button
		previousScalingState = showData.drawButton(dataButtonState, glut, (new float[] {-0.99f, 0.99f}), "Display Data", screenWidth, 0.13f, 2, previousScalingState);
	}
	
	//Getters and setters
	public CreateButton getPumpButton() {
		return this.pumpButton;
	}
	public CreateButton getCleanButton() {
		return this.cleanButton;
	}
	public CreateButton getDataButton() {
		return this.showData;
	}
}
