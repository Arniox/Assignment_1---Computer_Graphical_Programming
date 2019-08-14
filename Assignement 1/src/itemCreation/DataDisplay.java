package itemCreation;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.CreateQuad;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class DataDisplay {
	
	//Set up private variables
	private GL2 gl;
	private GLUT glut;
	
	private float bundleLocationPos[];
	private ArrayList<String> textBundle;
	private CreateQuad[] quads;
	
	//Constructor
	public DataDisplay(GL2 gl, GLUT glut, float bundleLocationPos[]) {
		this.gl = gl;
		this.glut = glut;
		
		this.bundleLocationPos = bundleLocationPos;
	}
	
	//Draw/display the given data
	public void displayData(boolean display, float screenWidth) {
		//If display is on
		if(display) {			
			float i = 0;
			//For each item in the text bundle make a new BITMAP and shift it down slightly to make a list of text items
			for(String s : textBundle) {
				//Draw white square behind them
				float stringObjectLength = 0;
				for(int k=0;k<s.length(); k++) {
					stringObjectLength+=(glut.glutBitmapWidth(GLUT.BITMAP_9_BY_15, s.charAt(k)));
				}
				//Set scaling for quad
				float scalingLength = (stringObjectLength/screenWidth)*2;
				
				//Draw quad
				this.quads[(int)i].drawQuad(new float[]{bundleLocationPos[0],(bundleLocationPos[1]-(i/25)+0.03f)}, scalingLength, 0.05f, new float[]{1.0f,1.0f,1.0f});
				
				//Draw text second
				gl.glColor3f(1.0f, 0.0f, 0.0f);
				gl.glRasterPos2f(bundleLocationPos[0], bundleLocationPos[1]-(i/25));
				glut.glutBitmapString(GLUT.BITMAP_9_BY_15, s+"");
				gl.glEnd();
				
				i++;
			}
		}
	}
	
	//Update the data
	public void updateText(ArrayList<String> textBun) {
		this.textBundle = textBun;
		
		this.quads = new CreateQuad[textBundle.size()];
		for(int i=0;i<textBundle.size();i++) {
			this.quads[i] = new CreateQuad(gl);
		}
	}
}
