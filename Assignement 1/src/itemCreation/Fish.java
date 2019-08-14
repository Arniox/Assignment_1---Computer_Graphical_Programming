package itemCreation;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.CheckMouseFunctions;
import utilityFunctions.CreateFilledCircle;
import utilityFunctions.CreateTriangleFan;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class Fish {
	//Set up base elements
	private GL2 gl;
	private GLUT glut;
	private float frameRatio;
	static private CheckMouseFunctions checkMouseFunc = new CheckMouseFunctions();
	
	//Set up pieces
	private CreateTriangleFan fishFinSideAway;
	private CreateFilledCircle fishBody;
	private CreateTriangleFan fishFinSideFacing;
	private CreateFilledCircle[] fishEye;
	private CreateTriangleFan fishTail;
	private CreateTriangleFan fishFinTop;
	
	//Set up global position
	public float[] globalPosition;
	//Set up animation values
	private float[] mousePosition;
	
	//Non globally animated variables
	float[][] fin1Shift = new float[3][2];
	float[][] fin2Shift = new float[3][2];
	private float deg = 0;
	private float fishVelocity = 0.0f;
	
	//Constructor
	public Fish(GL2 gl, GLUT glut, float[] globalPos) {
		//Set up variables
		this.gl = gl;
		this.glut = glut;
		this.globalPosition = globalPos;
		
		this.setUpObject();
	}
	
	//Set up object
	public void setUpObject() {
		//Set all data
		this.fishFinSideAway = new CreateTriangleFan(gl, 3);
		this.fishBody = new CreateFilledCircle(gl, glut);
		this.fishEye = new CreateFilledCircle[]{new CreateFilledCircle(gl, glut), new CreateFilledCircle(gl, glut)};
		this.fishTail = new CreateTriangleFan(gl, 6);
		this.fishFinSideFacing = new CreateTriangleFan(gl, 3);
		this.fishFinTop = new CreateTriangleFan(gl, 3);
		
		//Set up some shift values that are animated
		fin1Shift[0] = new float[]{0.01f,0.12f};
		fin1Shift[1] = new float[]{0.02f,0.10f};
		fin1Shift[2] = new float[]{0.06f,0.12f};
		//Second fin
		fin2Shift[0] = new float[]{0.050f,0.11f};
		fin2Shift[1] = new float[]{0.020f,0.09f};
		fin2Shift[2] = new float[]{0.025f,0.13f};
	}
	
	//Set up object data
	public void drawFish(boolean displayData, float frameRat, float[] mousePosition) {
		//Set up data of fish
		this.mousePosition = mousePosition;	
		this.frameRatio = frameRat;
		
		//Render the object itself
		this.renderFish(displayData);
	}
	
	//render the object itself
	private void renderFish(boolean displayData) {
		//Set outer body fish color up here so that the fins and so on can follow the same pattern
		float[] fishOuterBodyColour = {1.0f,0.498f,0.137f,1.0f};
		
		//Draw back fin first since it will be positioned behind the body
		float[] fishFinsShift = {0.126f/frameRatio,0.04f};
		float[] fishFin1FirstVertex = {globalPosition[0]+fishFinsShift[0], globalPosition[1]-fishFinsShift[1]};
		float[][] fishFin1VertexArray = {{fishFin1FirstVertex[0]-fin1Shift[2][0],fishFin1FirstVertex[1]-fin1Shift[2][1]},
										 {fishFin1FirstVertex[0]-fin1Shift[1][0],fishFin1FirstVertex[1]-fin1Shift[1][1]}, //Middle of fin
										 {fishFin1FirstVertex[0]+fin1Shift[0][0],fishFin1FirstVertex[1]-fin1Shift[0][1]}};
		float[] fishFin1Color = {fishOuterBodyColour[0]-0.25f,fishOuterBodyColour[1]-0.25f,fishOuterBodyColour[2]-0.25f,fishOuterBodyColour[3]};
		this.fishFinSideAway.drawTriangleFan(fishFin1FirstVertex, fishFin1VertexArray, fishFin1Color);
		
		//Draw fish tails behind the body
		float[] fishTailShift = {0.234f/frameRatio, 0.01f};
		float[] fishTailFirstVertex = {globalPosition[0]-fishTailShift[0], globalPosition[1]+fishTailShift[1]};
		float[][] fishTailVertexArray = {{fishTailFirstVertex[0]-0.10f, fishTailFirstVertex[1]+0.09f},
										 {fishTailFirstVertex[0]-0.12f, fishTailFirstVertex[1]+0.04f},
										 {fishTailFirstVertex[0]-0.13f, fishTailFirstVertex[1]-0.01f}, //Middle of fin
										 {fishTailFirstVertex[0]-0.12f, fishTailFirstVertex[1]-0.06f},
										 {fishTailFirstVertex[0]-0.10f, fishTailFirstVertex[1]-0.11f},
										 {fishTailFirstVertex[0]-0.00f, fishTailFirstVertex[1]-0.02f}};
		float[] fishTailColor = {0.0f,0.635f,0.929f,1.0f};
		this.fishTail.drawTriangleFan(fishTailFirstVertex, fishTailVertexArray, fishTailColor);
		
		//Draw fish top fin behind the body
		float[] fishTopFinShift = {0.126f/frameRatio,0.07f};
		float[] fishTopFinFirstVertex = {globalPosition[0]+fishTopFinShift[0], globalPosition[1]+fishTopFinShift[1]};
		float[][] fishTopFinVertexArray = {{fishTopFinFirstVertex[0]-0.05f, fishTopFinFirstVertex[1]+0.1f},
										   {fishTopFinFirstVertex[0]-0.18f, fishTopFinFirstVertex[1]+0.015f}, //Middle of fin
										   {fishTopFinFirstVertex[0]-0.167f, fishTopFinFirstVertex[1]-0.01f}};
		this.fishFinTop.drawTriangleFan(fishTopFinFirstVertex, fishTopFinVertexArray, fishTailColor);
		
		//Draw body
		float[] fishCenterBodyColour = {1.0f,0.74f,0.137f,1.0f};
		this.fishBody.drawCircle(false, displayData, 0.3f, globalPosition, fishCenterBodyColour, fishOuterBodyColour, frameRatio, 0.0f, 3.5f);

		//Draw front fin third since it will be positioned infront of the body now
		float[] fishFin2FirstVertex = {globalPosition[0]+fishFinsShift[0], globalPosition[1]-fishFinsShift[1]};
		float[][] fishFin2VertexArray = {{fishFin2FirstVertex[0]-fin2Shift[2][0],fishFin2FirstVertex[1]-fin2Shift[2][1]},
										 {fishFin2FirstVertex[0]+fin2Shift[1][0],fishFin2FirstVertex[1]-fin2Shift[1][1]}, //Middle of fin
										 {fishFin2FirstVertex[0]+fin2Shift[0][0],fishFin2FirstVertex[1]-fin2Shift[0][1]}};
		float[] fishFin2Color = {fishOuterBodyColour[0]-0.1f,fishOuterBodyColour[1]-0.1f,fishOuterBodyColour[2]-0.1f,fishOuterBodyColour[3]};
		this.fishFinSideFacing.drawTriangleFan(fishFin2FirstVertex, fishFin2VertexArray, fishFin2Color);
		
		//Draw both sections of the eye without blending
		float[] fishEyeOuterColour = {1.0f,1.0f,1.0f,1.0f};
		float[] fishEyeInnerColour = {0.0f,0.0f,0.0f,1.0f};
		float[] fishEyeShift = {0.20f/frameRatio, 0.025f};
		this.fishEye[0].drawCircle(false, false, 0.03, new float[]{globalPosition[0]+fishEyeShift[0], globalPosition[1]+fishEyeShift[1]},
								   fishEyeOuterColour, fishEyeOuterColour, frameRatio, 0.0f, 0.0f);
		this.fishEye[1].drawCircle(false, false, 0.015, new float[]{globalPosition[0]+fishEyeShift[0], globalPosition[1]+fishEyeShift[1]},
				fishEyeInnerColour, fishEyeInnerColour, frameRatio, 0.0f, 0.0f);

	}
	
	//Animate the fish
	public void animate(double time, Water water) {
		//Set up degrees value
		if(deg>=360) {
			deg=0; //if deg gets to 360, reset it
		}
		
		//Animate the non global values (fins)
		for(int i=0;i<fin1Shift.length;i++) {
			fin1Shift[i][0] += 0.00016f * Math.cos(Math.toRadians(deg));
			fin1Shift[i][1] += 0.00016f * Math.cos(Math.toRadians(deg));
			
			fin2Shift[i][0] += 0.00014f * Math.cos(Math.toRadians(deg));
			fin2Shift[i][1] += 0.00014f * Math.cos(Math.toRadians(deg));
		}
		deg++;
		
		//Interpolate Fish
		//If mouse is in the water
		if(checkMouseFunc.checkQuadHover(mousePosition, water.getWater().getQuadArea())==true) {
			//Fish velocity
			fishVelocity=0.08f;
			
			//Move fish to mouse
			globalPosition[0] = this.lerp(this.globalPosition[0], mousePosition[0], cubic_scurve3(fishVelocity));
			globalPosition[1] = this.lerp(this.globalPosition[1], mousePosition[1], cubic_scurve3(fishVelocity));
		}
	}
	
	//Smooth interpolation
	private float cubic_scurve3(float alpha) {
		return alpha*alpha*(3.0f - 2.0f*alpha);
	}
	
	private float lerp(float point1, float point2, float alpha) {
		return point1+alpha*(point2-point1);
	}
	
	//Getters and setters
	public String mouseIsCurrentlyInWater(Water water) {
		if(checkMouseFunc.checkQuadHover(mousePosition, water.getWater().getQuadArea())==true) {
			return " IS CURRENTLY in the water";
		}else {
			return " IS NOT in the water";
		}
	}
	public float getFishVelocity() {
		return this.fishVelocity;
	}
}
