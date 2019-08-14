package itemCreation;
import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import particleSystem.AlgaeDot;
import utilityFunctions.CheckMouseFunctions;

public class Algae {
	public GL2 gl;
	static public CheckMouseFunctions checkMouseFunc = new CheckMouseFunctions();
	
	//Pump position
	public float[] pumpPosition;
	
	//Particles
	private ArrayList<AlgaeDot> algaeDots;
	static private int limit = 10000;
	private int countOfDeadParticles;
	
	//Constructor
	public Algae(GL2 gl) {
		this.gl = gl;
		
		this.setUpOject();		
	}
	
	//Setup object
	public void setUpOject() {
		//Create particle arraylist
		algaeDots = new ArrayList<AlgaeDot>();
		countOfDeadParticles = 0;
	}
	
	//Set up and draw
	public void drawAlgae(float[] pumpPos, float waterHeight) {
		this.pumpPosition = pumpPos;
		
		for(AlgaeDot a : algaeDots) {
			a.drawAlgaeDot(waterHeight);
		}
	}
	
	//Add particles
	public void addParticles(float[] particlePos) {
		if(algaeDots.size() < limit) {
			for(int i=0;i<Math.random()*100;i++) {
				algaeDots.add(new AlgaeDot(gl, particlePos));
			}
		}
	}
	
	//Animation computation
	public void animate(double time, boolean goTo) {
		for(int i=0;i<algaeDots.size();i++) {
			//If go to, then go to pump
			if(goTo) {
				algaeDots.get(i).goTo(pumpPosition);
			}
			
			//Otherwise, animate normally
			//If algaeDot is not on pump, animate.
			if(!(checkMouseFunc.areValuesClose(algaeDots.get(i).position, pumpPosition) || algaeDots.get(i).pointSize<=-0.1f)) {
				algaeDots.get(i).animate(time);
			}else {
				countOfDeadParticles++;
				algaeDots.remove(i);
			}
		}
	}
	
	//Count particles
	public int countParticles() {
		if(algaeDots==null) {
			return 0;
		}else {
			return algaeDots.size();
		}
	}
	
	//Count dead particles
	public int countDeadParticles() {
		return this.countOfDeadParticles;
	}
}
