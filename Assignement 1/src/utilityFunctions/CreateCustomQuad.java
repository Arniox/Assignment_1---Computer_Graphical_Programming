package utilityFunctions;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CreateCustomQuad {
	//Set up private variables
	private GL2 gl;
	
	//Object variables
	private float[] backBottomCornerPos;
	private float length;
	private float[] color4Bottom;
	private float[] color4Top;
	private int waterDetail;
	
	private float blockLength;
	private float shift;
	
	//Animation stuffs
	private float height; //water height
	private float heightVariation; //How fucked the water is up and down
	private float[] heightShiftUp; //Top water vertexes
	
	//Animation speeds and so on
	private float dy[]; //dy is a float array. One new dy per point of water for more randomization
	//dx is not needed as the water is only moving up and down
	
	//Constructor
	public CreateCustomQuad(GL2 gl, int waterDetail, float height, float heightVariation) {
		this.gl = gl;
		this.height = height;
		this.heightVariation = heightVariation;
		
		this.waterDetail = waterDetail;
		
		this.setUpOjects();
	}
	
	//Set up object variables
	public void setUpOjects() {
		heightShiftUp = new float[waterDetail];
		this.dy = new float[waterDetail];
		
		for(int i=0; i<waterDetail; i++) {
			heightShiftUp[i] = heightVariation*(float)(Math.random()*heightVariation-(heightVariation/2));
			
			//Convert height variation into a pseudo animation height variation with some randomization
			float animationVariation = heightVariation/10f;
			
			//Set dy to a value between 0->animationVaiation minus animationVariation/2
			this.dy[i] = (float)(Math.random()*animationVariation-(animationVariation/2));
		}
		
	}
	
	//Set up the object data
	public void drawCustomQuad(boolean blendOption, boolean drawLine, float [] backBottomCornerPos, float length, float[] color4Bottom, float[] color4Top) {
		//Set main object variables
		this.backBottomCornerPos = backBottomCornerPos;
		this.length = length;
		this.color4Bottom = color4Bottom;
		this.color4Top = color4Top;
		
		
		//Set dynamic size variables
		this.blockLength = this.length/this.waterDetail;
		this.shift = this.blockLength;
		
		this.renderCustomQuad(blendOption, drawLine);
	}
	
	//Render the actual object
	public void renderCustomQuad(boolean blendOption, boolean drawLine) {
		//If blendOption, then turn on blending
		if(blendOption) {
			gl.glEnable(GL2.GL_BLEND);
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_CONSTANT_ALPHA);
		}
		
		gl.glBegin(GL2.GL_QUAD_STRIP);
		
		//Loop for vertexes
		for(int i=0;i<this.waterDetail; i++) {
			shift=blockLength*i; //shift the block along by i

			gl.glColor4fv(color4Bottom, 0);
			gl.glVertex2f(backBottomCornerPos[0]+shift, backBottomCornerPos[1]);
			//y = i-1 so that the water spikes connect rather than create blocks
			gl.glColor4fv(color4Top, 0);
			gl.glVertex2f(backBottomCornerPos[0]+shift, heightShiftUp[i-checkIfZero(i)]+height);
			gl.glColor4fv(color4Bottom, 0);
			gl.glVertex2f(backBottomCornerPos[0]+shift+blockLength, backBottomCornerPos[1]);
			gl.glColor4fv(color4Top, 0);
			gl.glVertex2f(backBottomCornerPos[0]+shift+blockLength, heightShiftUp[i]+height);
		}
		
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		
		//If drawLine is allowed, then draw a top water line
		if(drawLine) {
			gl.glLineWidth(3);
			gl.glBegin(GL2.GL_LINE_STRIP);
			gl.glColor4f(color4Top[0], color4Top[1], color4Top[2]-0.3f, color4Top[3]); //slightly darker than water
			//For line
			for(int i=0; i<this.waterDetail; i++) {
				shift=blockLength*i; //shift the line chunk along by i
				
				gl.glVertex2f(backBottomCornerPos[0]+shift, heightShiftUp[i-checkIfZero(i)]+height);
				gl.glVertex2f(backBottomCornerPos[0]+shift+blockLength, heightShiftUp[i]+height);
			}
			
			gl.glEnd();
		}
	}
	
	//Check if i is zero
	private int checkIfZero(int i) {
		if(i<=0) return 0;
		else return 1;
	}
	
	//Animate the water
	public void animate(double time) {
		
		for(int i=0;i<waterDetail; i++) {
			heightShiftUp[i]+=this.dy[i]*time;
			
			float hMax = heightVariation*0.1f;
			float hMin = (heightVariation*-1f)*0.1f;
			
			if(heightShiftUp[i] >= hMax || heightShiftUp[i] <= hMin) {
				this.dy[i]*=-1f;
				//if the water gets to the height max, or if drops to the height min, it will reverse
			}
		}
	}
	
	//Get quad area
	public float[] getQuadArea() {
		float[] quadArea = new float[4];
		//Set up empty variables
		float minY = backBottomCornerPos[1];
		float maxY = this.height+this.getAverageWaterHeight();
		float minX = backBottomCornerPos[0];
		float maxX = backBottomCornerPos[0]+this.length;
		
		quadArea[0] = minY;
		quadArea[1] = maxY;
		quadArea[2] = minX;
		quadArea[3] = maxX;
		
		return quadArea;
	}
	
	//Get average heightShift
	private float getAverageWaterHeight() {
		float average = 0;
		for(int i=0;i<waterDetail;i++) {
			average+=heightShiftUp[i];
		}
		average/=waterDetail;
		
		return average;
	}
	
	//Setters and getters
	public int getWaterDetail() {
		return this.waterDetail;
	}
	public float getWaterHeight() {
		return this.height;
	}
	public float[] getBackBottomCornerPos() {
		return this.backBottomCornerPos;
	}
}
