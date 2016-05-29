package mafscalermain;

/**
 * Class for the variables in the csv list
 * @author esanders
 *
 */
public class VariableRetriever {
	
	float rpm;
	float bfs;
	float mafVolts;
	float fc1;
	float fc2;
	float accelPos;
	float targAFR;
	float actualAFRB1;
	float actualAFRB2;
	
	public VariableRetriever(float rpm, float bfs, float mafVolts, float fc1, 
			float fc2, float accelPos, float targAFR, float actualAFRB1,
			float actualAFRB2) {	
		
		super();
		setRPM(rpm);
		setBFS(bfs);
		setMafVolts(mafVolts);
		setFC1(fc1);
		setFC2(fc2);
		setAccelPos(accelPos);
		setTargetAFR(targAFR);
		setActualAFRB1(actualAFRB1);
		setActualAFRB2(actualAFRB2);
		
	}
	
	public float getRPM() { return rpm; }
	public void setRPM(float rpm) {
		this.rpm = rpm;
	}
	public float getBFS() { return bfs; }
	public void setBFS(float bfs) {
		this.bfs = bfs;
	}
	public float getMafVolts() { return mafVolts; }
	public void setMafVolts(float mafVolts) {
		this.mafVolts = mafVolts;
	}
	public float getFC1() { return fc1; }
	public void setFC1(float fc1) {
		this.fc1 = fc1;
	}
	public float getFC2() { return fc2; }
	public void setFC2(float fc2) {
		this.fc2 = fc2;
	}
	public float getAccelPos() { return accelPos; }
	public void setAccelPos(float accelPos) {
		this.accelPos = accelPos;
	}
	public float getTargetAFR() { return targAFR; }
	public void setTargetAFR(float targAFR) {
		this.targAFR = targAFR;
	}
	public float getActualAFRB1() { return actualAFRB1; }
	public void setActualAFRB1(float actualAFRB1) {
		this.actualAFRB1 = actualAFRB1;
	}
	public float getActualAFRB2() { return actualAFRB2; }
	public void setActualAFRB2(float actualAFRB2) {
		this.actualAFRB2 = actualAFRB2;
	}
}