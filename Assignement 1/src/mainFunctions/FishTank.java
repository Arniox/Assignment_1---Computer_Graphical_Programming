package mainFunctions;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

import utilityFunctions.Timing;

/**
 * 
 * @author Nikkolas Diehl - 16945724
 *
 */
public class FishTank implements GLEventListener, MouseListener {
	//Main variables
	public static GLCanvas canvas;
	
	//Set up public variables
	public GLUT glut;
	public DisplaySetUp setUp;
	public Timing time;
	
	//Display data
	public boolean displayData = false;
	
	//Set up scaling
	private float[] frameDimension;

	@Override
	public void display(GLAutoDrawable arg0) {
		time.count();
		
		//Set mouse position
		float[] mousePos = new float[2];
		if(canvas.getMousePosition() != null) {
			mousePos[0] = (float)canvas.getMousePosition().getX();
			mousePos[1] = (float)canvas.getMousePosition().getY();
		}
		
		//Setup everything. Takes in time.delta, frameDimension for scaling and a boolean to display data or not
		//Frame dimension is being sent in for updating screendim
		this.setUp.displayEm(time.delta, frameDimension, displayData, mousePos);
	}
	@Override
	public void init(GLAutoDrawable gld) {
		GL2 gl = gld.getGL().getGL2();
		this.glut = new GLUT();
		
		//Scaling SETUP
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.frameDimension = new float[]{(int)(screenSize.getWidth()/1.5), (int)(screenSize.getHeight()/1.5)};
		
		this.time = new Timing();
		this.setUp = new DisplaySetUp(gl, this.glut);
		this.setUp.initiationEm();
	}
	@Override
	public void reshape(GLAutoDrawable arg0, int xFramePos, int yFramePos, int frameWidth, int frameHeight) {
		frameDimension[0] = (float)frameWidth;
		frameDimension[1] = (float)frameHeight;		
	}
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		//Build GUIView which contains everything
		Frame frame = new Frame("Fish Tank");
		
		//Set up GL info
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		canvas = new GLCanvas(capabilities);
		
		//Canvas stuff
		FishTank fishTank = new FishTank();
		canvas.addGLEventListener(fishTank);
		canvas.addMouseListener(fishTank);
		frame.add(canvas);
		
		//Frame setup
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		frame.setSize((int)(screenSize.getWidth()/1.5), (int)(screenSize.getHeight()/1.5));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setAlwaysOnTop(false);
		frame.setResizable(true);
		
		//Animator set up
		final Animator animator = new Animator(canvas);
		
		//Window listener
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		//Last setup
		animator.start();
		canvas.requestFocusInWindow();
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent e) {
		//Run the check all buttons in the setup class (which changes button states); with the converted java positions of e.X and e.Y
		this.setUp.clicked = true;
		this.setUp.checkAllButtons(this.setUp.convertJavaToOpenGlPos(new float[]{e.getX(), e.getY()}));
		
		//Check all button states and change stuff based on it
		if(this.setUp.getDataButtonState() == ButtonState.CLICKED) {
			this.displayData = !this.displayData;
		}
		if(this.setUp.getPumpButtonState() == ButtonState.CLICKED) {
			this.setUp.pumpActive = !this.setUp.pumpActive;
		}
		if(this.setUp.getCleanButtonState() == ButtonState.CLICKED) {
			this.setUp.clean = !this.setUp.clean;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//Unclick the buttons
		this.setUp.clicked = false;
	}
}
