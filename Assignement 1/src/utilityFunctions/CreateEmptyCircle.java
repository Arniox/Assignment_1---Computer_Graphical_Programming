package utilityFunctions;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class CreateEmptyCircle {
	
	//Set up private variables
	private GL2 gl;
	
	private double radius;
	private float centerPosition[];
	private float[] color;
	private float ratioMultiplier;
	
	//Alterations to object
	private int thickness;
	private float extensionHorizontally;
	private float extensionVertically;
	
	public CreateEmptyCircle(GL2 gl) {
		this.gl = gl;
	}
	
	//Set data and then render
	public void drawCircle(boolean blendOption, double radius, float centerPos[], float[] color4, int thickness, float ratioMult, float horz, float vert) {
		//Set all data for circle
		this.radius = radius;
		this.centerPosition = centerPos;
		this.color = color4;
		this.thickness = thickness;
		
		this.ratioMultiplier = ratioMult;
		this.extensionHorizontally = horz;
		this.extensionVertically = vert;
		
		//Check if any radius calculations ever equal 0
		checkIfZeo();
		//Render the circle itself
		this.renderCircle(blendOption);
	}
	
	//Render the circle itself
	private void renderCircle(boolean blendOption) {
		//if blend option is on, then blend. If not then don't blend
		if(blendOption) {
			gl.glEnable(GL2.GL_BLEND); 
			gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_CONSTANT_ALPHA);
		}
		
		gl.glBegin(GL2.GL_POINTS);
		gl.glPointSize(this.thickness);
		gl.glColor4fv(this.color, 0);
		
		for(int deg=0; deg<=360; deg++) {
			double rad = Math.toRadians(deg);
			double x = centerPosition[0] + radius*Math.cos(rad)/(ratioMultiplier+extensionHorizontally);
			double y = centerPosition[1] + radius*Math.sin(rad)/(extensionVertically);
			
			gl.glVertex2d(x, y);
		}
		
		gl.glEnd();
		gl.glDisable(GL2.GL_BLEND);
	}
	
	//Make sure that the extension values can not result in a division by 0 error
	public void checkIfZeo() {
		
		if(this.ratioMultiplier+this.extensionHorizontally==0f) {
			this.ratioMultiplier=1;
		}else if(this.extensionVertically==0) {
			this.extensionVertically=1;
		}
	}
	
	//Change the radius of the circle
	public void changeRadius(float radius) {
		this.radius = radius;
	}
	//Change the color of the circle
	public void changeColor(float[] color4) {
		this.color = color4;
	}
}
