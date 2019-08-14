package utilityFunctions;
import com.jogamp.opengl.GL2;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class SeirpinskiTriangle {

	private GL2 gl;
	
	public SeirpinskiTriangle(GL2 gl) {
		this.gl = gl;
	}
	
	public void drawTriangle(int level, double[] p1, double[] p2, double[] p3) {
		if(level <= 1) {
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glVertex2dv(p1, 0);
			gl.glVertex2dv(p2, 0);
			gl.glVertex2dv(p3, 0);
			gl.glEnd();
		}else {
			double[] m12 = {(p1[0]+p2[0])/2.0, (p1[1]+p2[1])/2.0};
			double[] m13 = {(p1[0]+p3[0])/2.0, (p1[1]+p3[1])/2.0};
			double[] m23 = {(p2[0]+p3[0])/2.0, (p2[1]+p3[1])/2.0};
			drawTriangle(level-1, p1, m12, m13);
			drawTriangle(level-1, m12, p2, m23);
			drawTriangle(level-1, m13, m23, p3);
		}
	}
}
