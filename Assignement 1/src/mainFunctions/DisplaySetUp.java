package mainFunctions;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import itemCreation.Algae;
import itemCreation.DataDisplay;
import itemCreation.Fish;
import itemCreation.Menu;
import itemCreation.Pump;
import itemCreation.SandBottom;
import itemCreation.Water;
import utilityFunctions.CheckMouseFunctions;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class DisplaySetUp {
	
	//Set up private variables
	private GL2 gl;
	private GLUT glut;
	private double time;
	public int frameCount;
	
	//Items in the scene
	private Menu menu;
	private Pump pump;
	private Water water;
	private Fish fish;
	private Algae algae;
	private SandBottom sandyBottom;
	
	//Data setup
	private DataDisplay dataDisplay;
	private ArrayList<String> dataItems;
	private ButtonState pumpButtonState;
	private ButtonState cleanButtonState;
	private ButtonState dataButtonState;
	
	//Mouse position stuff
	private float[] mousePosition = new float[2];
	private float[] openglCoords = new float[2];
	
	//Scaling data
	private float[] frameDim;
	private float frameRatio;
	
	//Static values
	static private float waterHeight = 0.55f;
	static private float sandHeight = -0.7f;
	
	//Functionality functions
	public CheckMouseFunctions checkMouseFunc;
	public boolean clicked = false;
	public boolean pumpActive = false;
	public boolean clean = false;
	
	//Constructor
	public DisplaySetUp(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
	}
	
	//Display emulator. Called from the main display function
	public void displayEm(double time, float[] frameDim, boolean displayData, float[] mousePos) {
		//Timing data for animation
		this.time = time;
		this.frameCount++;
		
		//Fram dim data
		this.frameDim = frameDim;
		this.frameRatio = (this.frameDim[0]/this.frameDim[1]);

		//Set base openGL options
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL2.GL_LINE_SMOOTH);
		
		//Variables for mouse position settings
		this.mousePosition = mousePos;
		this.openglCoords = this.convertJavaToOpenGlPos(mousePosition);
		
		//Draw all items in the scene
		menu.drawMenu(pumpButtonState, cleanButtonState, dataButtonState, this.frameDim[0]);
		water.drawWater(false);
		fish.drawFish(displayData, frameRatio, openglCoords);
		sandyBottom.drawSandBottom(true);
		algae.drawAlgae(new float[]{0.9f,-0.8f}, waterHeight);
		pump.drawPump(displayData, pumpActive, frameRatio, new float[]{0.9f, -0.8f}, waterHeight);
		
		//Create and animate
		if(pumpActive) this.addBubbles();
		water.animate(time);
		pump.animate(time);
		fish.animate(time, water);
		if(!clean) this.addAlgae();
		algae.animate(time, clean);
		
		this.updataAllData();
		dataDisplay.displayData(displayData, this.frameDim[0]);
		dataDisplay.updateText(dataItems);
		
		//Run Button Checks for hover
		this.checkAllButtons(this.openglCoords);
		
		gl.glFlush();
	}
	
	//Init emulator. Called from the main init function
	public void initiationEm() {
		//Set background color
		gl.glClearColor(0.50f, 0.909f, 1.0f, 1.0f);
		//Set functionality functions
		this.checkMouseFunc = new CheckMouseFunctions();
		
		//Set up menu states
		this.pumpButtonState = ButtonState.NOT_CLICKED;
		this.cleanButtonState = ButtonState.NOT_CLICKED;
		this.dataButtonState = ButtonState.NOT_CLICKED;
		
		//Set up all items in the scene
		menu = new Menu(gl, glut);
		pump = new Pump(gl, glut);
		fish = new Fish(gl, glut, new float[]{0.0f,0.0f});
		
		water = new Water(gl, waterHeight); //water height is a static value
		sandyBottom = new SandBottom(gl, sandHeight); //sand height is a static value
		algae = new Algae(gl);
		
		//Set items that are in the data Arraylist
		dataItems = new ArrayList<String>();
		this.addAllData();
		
		//Setup the DataDisplay object
		dataDisplay = new DataDisplay(gl, glut, new float[]{-0.99f, 0.85f});
		dataDisplay.updateText(dataItems);
	}
	
	//Add bubbles
	public void addBubbles() {
		if(this.frameCount %10 == 0) {
			pump.addBubbles();
		}
	}
	
	//Add algae
	public void addAlgae() {
		if(this.frameCount%100==0) {
			algae.addParticles(new float[]{(float)Math.random()*2.0f-1.0f, (float)Math.random()*(waterHeight-sandHeight)+sandHeight});
		}
	}
	
	//Add all data items
	public void addAllData() {
		dataItems.add(this.frameCount+" frames have passed"); //0
		dataItems.add(pump.countParticles()+" bubbles are currently alive"); //1
		dataItems.add(pump.countDeadPariticles()+" bubbles have been killed"); //2
		dataItems.add(algae.countParticles()+" algae dots are currently alive"); //3
		dataItems.add(algae.countDeadParticles()+" algae dots have been killed"); //4
		dataItems.add("Delta Time: "+String.format("%.3f", time)); //5
		dataItems.add("Frame Ratio Multiplier: "+frameRatio); //6
		dataItems.add("Water Height: "+water.getWater().getWaterHeight()); //7
		dataItems.add("Water Detail: "+water.getWater().getWaterDetail()); //8
		dataItems.add("Mouse Pos <Java Coords>: "+mousePosition[0]+", "+mousePosition[1]); //9
		dataItems.add("Mouse Pos <OpenGL Coords>: "+String.format("%.3f", openglCoords[0])+", "+String.format("%.3f", openglCoords[1])); //10
		dataItems.add("Fish is currently at: "+String.format("%.2f", this.fish.globalPosition[0])+"x, "+String.format("%.2f", this.fish.globalPosition[1])+"y"); //11
		dataItems.add("Fish velocity is: "+String.format("%.2f", this.fish.getFishVelocity())); //12
		dataItems.add("Pump is currently active: "+pumpActive); //13
		dataItems.add("Cleaning is currently active: "+clean); //14
		dataItems.add("Water object currently empty..."); //15
		dataItems.add("Fish object is currently empty..."); //16
	}
	
	//Update all data items
	public void updataAllData() {
		dataItems.set(0, this.frameCount+" frames have passed");
		dataItems.set(1, pump.countParticles()+" bubbles are currently alive");
		dataItems.set(2, pump.countDeadPariticles()+" bubbles have been killed");
		dataItems.set(3, algae.countParticles()+" algae dots are currently alive");
		dataItems.set(4, algae.countDeadParticles()+" algae dots have been killed");
		if(this.frameCount%10==0) { //Update time only every 10 frames for an easier to read value
			dataItems.set(5, "Delta Time: "+String.format("%.3f", time));
		}
		dataItems.set(6, "Frame Ratio Multiplier: "+frameRatio);
		dataItems.set(7, "Water Height: "+water.getWater().getWaterHeight());
		dataItems.set(8, "Water Detail: "+water.getWater().getWaterDetail());
		dataItems.set(9, "Mouse Pos <Java Coords>: "+mousePosition[0]+", "+mousePosition[1]);
		dataItems.set(10, "Mouse Pos <OpenGL Coords>: "+String.format("%.3f", openglCoords[0])+", "+String.format("%.3f", openglCoords[1]));
		dataItems.set(11, "Fish is currently at: "+String.format("%.2f", this.fish.globalPosition[0])+"x, "+String.format("%.2f", this.fish.globalPosition[1])+"y");
		dataItems.set(12, "Fish velocity is: "+String.format("%.2f", this.fish.getFishVelocity()));
		//Pump data
		if(pumpActive) dataItems.set(13, "Pump is currently ACTIVE");
		else dataItems.set(13, "Pump is currently DEACTIVATED");
		if(clean) dataItems.set(14, "Cleaning is currently ACTIVE");
		else dataItems.set(14, "Cleaning is currently DEACTIVATED");
		dataItems.set(15, "Water quad "+this.water.getWaterData());
		dataItems.set(16, "The mouse"+this.fish.mouseIsCurrentlyInWater(water));
	}
	
	//Convert Java position to openGl position
	public float[] convertJavaToOpenGlPos(float[] javaMousePos) {
		float[] openGlPos = new float[2];
		
		//If it's not null, send back
		if(javaMousePos!=null) {	
			openGlPos[0] = 2.0f * (javaMousePos[0]/frameDim[0]) - 1.0f;
			openGlPos[1] = (2.0f * (javaMousePos[1]/frameDim[1]) - 1.0f)*-1f;
		}
		
		return openGlPos;
	}
	
	//Check all buttons
	public void checkAllButtons(float[] mouseCoords) {
		this.pumpButtonState = this.checkMouseFunc.checkButtonsHovered(clicked, mouseCoords, this.menu.getPumpButton().getQuad().getQuadArea());
		this.cleanButtonState = this.checkMouseFunc.checkButtonsHovered(clicked, mouseCoords, this.menu.getCleanButton().getQuad().getQuadArea());
		this.dataButtonState = this.checkMouseFunc.checkButtonsHovered(clicked, mouseCoords, this.menu.getDataButton().getQuad().getQuadArea());
				
	}
	
	//Getters and setters
	public ButtonState getPumpButtonState() {
		return this.pumpButtonState;
	}
	public ButtonState getCleanButtonState() {
		return this.cleanButtonState;
	}
	public ButtonState getDataButtonState() {
		return this.dataButtonState;
	}
}
