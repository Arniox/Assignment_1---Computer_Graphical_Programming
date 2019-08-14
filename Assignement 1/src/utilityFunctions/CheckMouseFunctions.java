package utilityFunctions;

import mainFunctions.ButtonState;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CheckMouseFunctions {
	//Variables
	float[] mouseOpenGLPosition = new float[2];
	float[] buttonAreaArray = new float[4];
	
	//Class dedicated to checking values against other values for clicking and so on
	public ButtonState checkButtonsHovered(boolean click, float[] mouseOpenGlPos, float[] buttonArea) {
		this.mouseOpenGLPosition = mouseOpenGlPos;
		this.buttonAreaArray = buttonArea;
		//If mouse position is inside buttonAreaArray
		//buttonAreaArray[0] = minY
		//buttonAreaArray[1] = maxY
		//buttonAreaArray[2] = minX
		//buttonAreaArray[3] = maxX
		
		if((mouseOpenGLPosition[0]>=buttonAreaArray[2] && mouseOpenGLPosition[0]<=buttonAreaArray[3]) && 
		   (mouseOpenGLPosition[1]>=buttonAreaArray[0] && mouseOpenGLPosition[1]<=buttonAreaArray[1]) &&
		   (!click)) {
			return ButtonState.HOVER;
		}else if((mouseOpenGLPosition[0]>=buttonAreaArray[2] && mouseOpenGLPosition[0]<=buttonAreaArray[3]) && 
				 (mouseOpenGLPosition[1]>=buttonAreaArray[0] && mouseOpenGLPosition[1]<=buttonAreaArray[1]) &&
				 (click)) {
			return ButtonState.CLICKED;
		}else {
			return ButtonState.NOT_CLICKED;
		}
	}
	
	//Check if mouse is in the quad at all
	//Class dedicated to checking values against other values for clicking and so on
	public boolean checkQuadHover(float[] mouseOpenGlPos, float[] quadArea) {
		this.mouseOpenGLPosition = mouseOpenGlPos;
		this.buttonAreaArray = quadArea;
		//If mouse position is inside buttonAreaArray
		//buttonAreaArray[0] = minY
		//buttonAreaArray[1] = maxY
		//buttonAreaArray[2] = minX
		//buttonAreaArray[3] = maxX
		
		if((mouseOpenGLPosition[0]>=buttonAreaArray[2] && mouseOpenGLPosition[0]<=buttonAreaArray[3]) && 
		   (mouseOpenGLPosition[1]>=buttonAreaArray[0] && mouseOpenGLPosition[1]<=buttonAreaArray[1])) {
			return true;
		}else {
			return false;
		}
	}
	
	//Check if two positions are close enough
	public boolean areValuesClose(float[] pos1, float[] pos2) {
		if((pos1[0]>= pos2[0]-0.1f)&&(pos1[0]<=pos2[0]+0.1f)) {
			if((pos1[1]>=pos2[1]-0.1f)&&(pos1[1]<=pos2[1]+0.1f)) {
				return true;
			}
		}
		
		return false;
	}
}
