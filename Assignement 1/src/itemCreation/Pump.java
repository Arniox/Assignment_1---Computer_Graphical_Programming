package itemCreation;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import particleSystem.BubbleItem;
import utilityFunctions.CreateFilledCircle;
import utilityFunctions.CreateQuad;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class Pump {
	private GL2 gl;
	private GLUT glut;
	
	//Create elements of pump
	private CreateFilledCircle pumpTop;
	private CreateQuad pumpBase;
	private CreateQuad activeLight;
	private float ratioMultiplier;
	
	//Light variables
	public boolean currentlyActive;
	public float[] activeLightColor;
	public float[] deactivatedLightColor;
	
	//Particles
	private ArrayList<BubbleItem> bubbles;
	private int countOfDeadParticles;
	private int limit = 200;
	private int ageLimit = 30;
	private float[] globalItemPositon;
	private float waterHeight;
	
	//Display list array list of integers
	private ArrayList<Integer> displayList;
	private int baseDisplayID;
	
	public Pump(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
		
		this.setUpObject();
	}
	
	//Set up object
	public void setUpObject() {
		//Display list generation
		displayList = new ArrayList<Integer>();
		baseDisplayID = gl.glGenLists(2);
		
		displayList.add(baseDisplayID);
		displayList.add(baseDisplayID+1);
		
		//Set up pump
		pumpTop = new CreateFilledCircle(gl, glut);
		pumpBase = new CreateQuad(gl);
		activeLight = new CreateQuad(gl);
		
		//Object generation for bubbles
		bubbles = new ArrayList<BubbleItem>();
		
		//Variables
		activeLightColor = new float[]{0.0f,1.0f,0.0f,1.0f};
		deactivatedLightColor = new float[]{1.0f,0.0f,0.0f,1.0f};
	}
	
	//Set the data and then render
	public void drawPump(boolean displayData, boolean currentlyActive, float ratioMultiplier, float[] globalItemPosition, float waterH) {
		this.ratioMultiplier = ratioMultiplier;
		this.globalItemPositon = globalItemPosition;
		this.waterHeight = waterH;
		
		//Light variables
		this.currentlyActive = currentlyActive;
		
		this.renderPump(displayData);
	}
	
	//Render the object itself
	private void renderPump(boolean displayData) {
		
		float[] basePositon = {globalItemPositon[0]-(0.1215f/ratioMultiplier),globalItemPositon[1]};
		
		
		gl.glNewList(displayList.get(0), GL2.GL_COMPILE);
		pumpBase.drawQuad(basePositon, (0.243f/ratioMultiplier), 0.2f, new float[]{0.0f,0.0f,0.0f,1.0f});
		gl.glEndList();
		gl.glNewList(displayList.get(1), GL2.GL_COMPILE);
		pumpTop.drawCircle(false, false, 0.14, globalItemPositon, new float[]{0.5f,0.5f,0.5f,1.0f}, new float[]{0.0f,0.0f,0.0f,1.0f}, ratioMultiplier, 0.3f, 4f);
		gl.glEndList();
		
		gl.glCallList(displayList.get(0));
		gl.glCallList(displayList.get(1));
		
		//Draw light
		activeLight.drawQuad(new float[]{basePositon[0]+(0.109f/ratioMultiplier), basePositon[1]-0.1f}, (0.0243f/ratioMultiplier), 0.02f, this.isActive());
		
		for(BubbleItem b : bubbles) {
			b.drawBubble(displayData);
		}
	}
	
	//Add bubble particles to the ArrayList
	public void addBubbles() {
		if(bubbles.size() < limit) {
			for(int i = 0; i < Math.random()*10; i++) {
				float pos[] = {0.9f, -0.8f};
				bubbles.add(new BubbleItem(gl, glut, pos, ratioMultiplier, (int)(Math.random()*3), waterHeight));
			}
		}
	}
	
	//Animation computation
	public void animate(double time) {
		for(int i = 0; i<bubbles.size(); i++) {
			if(!(bubbles.get(i).age >= this.ageLimit) && !(bubbles.get(i).radiusOfBubble<=0.0f)) {
				bubbles.get(i).animate(time);
			}else {
				bubbles.remove(i);
				countOfDeadParticles++;
			}
		}
	}
	
	//Light color return based on pump activation or deactivation
	public float[] isActive() {
		if(currentlyActive) return this.activeLightColor;
		else return this.deactivatedLightColor;
	}
	
	//Count the number of particles currently alive
	public int countParticles() {
		if(bubbles == null) {
			return 0;
		}else {
			return bubbles.size();
		}
	}
	//Count the number of particles killed
	public int countDeadPariticles() {
		return this.countOfDeadParticles;
	}
	
	//Getters
	public ArrayList<BubbleItem> getBubbles() {
		return this.bubbles;
	}
}
