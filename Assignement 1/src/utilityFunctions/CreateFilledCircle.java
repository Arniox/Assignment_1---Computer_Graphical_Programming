package utilityFunctions;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CreateFilledCircle {
	
	//Set up private variables
	private GL2 gl;
	private GLUT glut;
	
	private double radius;
	private float centerPosition[];
	private float[] centerColour;
	private float[] outerColour;
	private float ratioMultiplier;
	
	//Alterations to object
	private float extensionHorizontally;
	private float extensionVertically;
	
	public CreateFilledCircle(GL2 gl, GLUT glut) {
		this.gl = gl;
		this.glut = glut;
	}
	
	//Set data and then render
	public void drawCircle(boolean blendOption, boolean bubbleData, double radius, float centerPos[], float[] centerColour4, float[] outerColour4, float ratioMult, float horz, float vert) {
		//Set data for the circle
		this.radius = radius;
		this.centerPosition = centerPos;
		this.centerColour = centerColour4;
		this.outerColour = outerColour4;
		
		this.ratioMultiplier = ratioMult;
		this.extensionHorizontally = horz;
		this.extensionVertically = vert;
		
		//Check if radius calculations ever equal 0
		checkIfZeo();
		//Render the circle itself
		this.renderCircle(blendOption, bubbleData);
	}
	
	//render the circle itself
	private void renderCircle(boolean blendOption, boolean bubbleData) {
		//If the blend option is on then blend. if not then don't blend
		if(blendOption) {
			gl.glEnable(GL2.GL_BLEND);
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_CONSTANT_ALPHA);
		}
		
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glColor4fv(centerColour, 0);
		
		gl.glVertex2fv(centerPosition, 0);
		
		for(int deg=0; deg<=360; deg+=10) {
			
			gl.glColor4fv(outerColour, 0);
			double rad = Math.toRadians(deg);
			double x = centerPosition[0] + radius*(float)Math.cos(rad)/(ratioMultiplier+extensionHorizontally);
			double y = centerPosition[1] + radius*(float)Math.sin(rad)/(extensionVertically);
			
			gl.glVertex2d(x, y);
		}
		
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
		
		this.renderData(bubbleData);
	}
	
	//Make sure that the extension values can not result in a division by 0 error
	public void checkIfZeo() {
		
		if(this.ratioMultiplier+this.extensionHorizontally==0f) {
			this.ratioMultiplier=1;
		}else if(this.extensionVertically==0) {
			this.extensionVertically=1;
		}
	}
	
	
	//Render All Data
	public void renderDataAll(boolean bubbleData, float age, float dx, float dy, float dr) {
		//If bubble data is on. Then generate text
		if(bubbleData) {
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glRasterPos2f(centerPosition[0]-0.046f, centerPosition[1]-0.031f);
			glut.glutBitmapString(GLUT.BITMAP_8_BY_13, "Radius: "+(String.format("%.2f", radius))+", Age: "+(String.format("%.2f", age)));
			
			gl.glRasterPos2f(centerPosition[0]-0.046f, centerPosition[1]-0.062f);
			glut.glutBitmapString(GLUT.BITMAP_8_BY_13, "dx: "+(String.format("%.2f", dx))+", dy: "+(String.format("%.2f", dy))+", dr: "+(String.format("%.2f", dr)));
		}
	}
	
	//Render Just Position Data
	private void renderData(boolean bubbleData) {
		if(bubbleData) {
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glRasterPos2f(centerPosition[0]-0.048f, centerPosition[1]);
			glut.glutBitmapString(GLUT.BITMAP_8_BY_13, (String.format("%.2f", centerPosition[0]))+"x, "+(String.format("%.2f", centerPosition[1]))+"y");
		}
	}
	
	//Change the radius of the circle
	public void changeRadius(float radius) {
		this.radius = radius;
	}
	//Change the color of the circle
	public void changeColor(float[] centerColor4, float[] outerColor4) {
		this.centerColour = centerColor4;
		this.outerColour = outerColor4;
	}
}
